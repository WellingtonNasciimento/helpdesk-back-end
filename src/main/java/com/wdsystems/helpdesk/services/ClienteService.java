package com.wdsystems.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdsystems.helpdesk.domain.Cliente;
import com.wdsystems.helpdesk.domain.Pessoa;
import com.wdsystems.helpdesk.domain.dtos.ClienteDTO;
import com.wdsystems.helpdesk.repositories.ClienteRepository;
import com.wdsystems.helpdesk.repositories.PessoaRepository;
import com.wdsystems.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.wdsystems.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findByID(Integer id) {
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente Not Found - ID: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		
		objDTO.setId(null);
		validateByCpfAndEmail(objDTO);
		Cliente newOBJ = new Cliente(objDTO);
		return clienteRepository.save(newOBJ);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		
		objDTO.setId(id);
		Cliente oldOBJ = findByID(id);
		validateByCpfAndEmail(objDTO);
		oldOBJ = new Cliente(objDTO);
		return clienteRepository.save(oldOBJ);
	}

	public void delete(Integer id) {
		
		Cliente obj = findByID(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}

	private void validateByCpfAndEmail(ClienteDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
				
	}

}
