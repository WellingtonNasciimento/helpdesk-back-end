package com.wdsystems.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdsystems.helpdesk.domain.Chamado;
import com.wdsystems.helpdesk.domain.Cliente;
import com.wdsystems.helpdesk.domain.Tecnico;
import com.wdsystems.helpdesk.domain.enums.Perfil;
import com.wdsystems.helpdesk.domain.enums.Prioridade;
import com.wdsystems.helpdesk.domain.enums.Status;
import com.wdsystems.helpdesk.repositories.ChamadoRepository;
import com.wdsystems.helpdesk.repositories.ClienteRepository;
import com.wdsystems.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	// dependences injection
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {
		
		Tecnico tec1 = new Tecnico(null, "Wellington Nascimento", "19955753056", "wellington@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "63778933027", "torvaldis@mail.com", "123");
		
		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01",
				"Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(chamado1));

	}
}
