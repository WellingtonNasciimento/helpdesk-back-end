package com.wdsystems.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdsystems.helpdesk.domain.Chamado;
import com.wdsystems.helpdesk.domain.Cliente;
import com.wdsystems.helpdesk.domain.Tecnico;
import com.wdsystems.helpdesk.domain.dtos.ChamadoDTO;
import com.wdsystems.helpdesk.domain.enums.Prioridade;
import com.wdsystems.helpdesk.domain.enums.Status;
import com.wdsystems.helpdesk.repositories.ChamadoRepository;
import com.wdsystems.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {

		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado not found - ID: " + id));
	}

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	public Chamado create(ChamadoDTO objDTO) {
		
		objDTO.setId(null);
		return chamadoRepository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		
		Tecnico tecnico = tecnicoService.findByID(obj.getTecnico());
		if(tecnico.getId() == null)
			throw new ObjectNotFoundException("O técnico informado não existe na base de dados!");
		
		Cliente cliente = clienteService.findByID(obj.getCliente());
		if(cliente.getId() == null)
			throw new ObjectNotFoundException("O cliente informado não existe na base de dados!");
		
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}
}
