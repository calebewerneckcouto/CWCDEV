package com.devsuperior.cwcdev.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.cwcdev.entities.Codigo;

public interface CodigoRepository extends JpaRepository<Codigo, Long> {

	List<Codigo> findByLinguagemContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String linguagem, String descricao);
    
}
