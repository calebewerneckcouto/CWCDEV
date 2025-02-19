package com.devsuperior.cwcdev.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.cwcdev.entities.Codigo;

public interface CodigoRepository extends JpaRepository<Codigo, Long> {

	
	

	// Método para buscar por linguagem ou descrição de forma ignorante a
	// maiúsculas/minúsculas
	List<Codigo> findByLinguagemContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String linguagem, String descricao);

	 @Query("SELECT c FROM Codigo c ORDER BY c.id")
	    Page<Codigo> encontrarComPaginacao(Pageable pageable);
}
