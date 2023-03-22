package com.wdsystems.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wdsystems.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
