package com.devsuperior.cwcdev.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwcdev.projetctions.ProductProjection;
import com.devsuperior.cwcdev.dto.CodigoDTO;
import com.devsuperior.cwcdev.entities.Codigo;

public interface CodigoRepository extends JpaRepository<Codigo, Long> {

	
    
}
