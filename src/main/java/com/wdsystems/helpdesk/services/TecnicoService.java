package com.wdsystems.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Tecnico findByID(Integer id) {
		
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico Not Found - ID: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		
		objDTO.setId(null);
		validateByCpfAndEmail(objDTO);
		Tecnico newOBJ = new Tecnico(objDTO);
		return tecnicoRepository.save(newOBJ);
	}

	private void validateByCpfAndEmail(TecnicoDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}		
	}
}
