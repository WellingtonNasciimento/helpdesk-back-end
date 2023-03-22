package com.wdsystems.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wdsystems.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
