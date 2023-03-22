package com.wdsystems.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wdsystems.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
