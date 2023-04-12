package com.wdsystems.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wdsystems.helpdesk.domain.Pessoa;
import com.wdsystems.helpdesk.domain.Tecnico;
import com.wdsystems.helpdesk.domain.dtos.TecnicoDTO;
import com.wdsystems.helpdesk.repositories.PessoaRepository;
import com.wdsystems.helpdesk.repositories.TecnicoRepository;
import com.wdsystems.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.wdsystems.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findByID(Integer id) {
		
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico Not Found - ID: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validateByCpfAndEmail(objDTO);
		Tecnico newOBJ = new Tecnico(objDTO);
		return tecnicoRepository.save(newOBJ);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		
		objDTO.setId(id);
		Tecnico oldOBJ = findByID(id);
		
		if(!objDTO.getSenha().equals(oldOBJ.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validateByCpfAndEmail(objDTO);
		oldOBJ = new Tecnico(objDTO);
		return tecnicoRepository.save(oldOBJ);
	}

	public void delete(Integer id) {
		
		Tecnico obj = findByID(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

	private void validateByCpfAndEmail(TecnicoDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
	}
}
