package com.wdsystems.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdsystems.helpdesk.domain.Tecnico;
import com.wdsystems.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findByID(Integer Id) {
		
		Optional<Tecnico> obj = tecnicoRepository.findById(Id);
		return obj.orElse(null);
	}
}
