package com.devsuperior.cwcdev.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.cwcdev.Exceptions.DatabaseException;
import com.devsuperior.cwcdev.Exceptions.ResourceNotFoundException;
import com.devsuperior.cwcdev.dto.CodigoDTO;
import com.devsuperior.cwcdev.entities.Codigo;
import com.devsuperior.cwcdev.repositories.CodigoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CodigoService {

	@Autowired
	private CodigoRepository repository;

	@Transactional(readOnly = true)
	public CodigoDTO findById(Long id) {
		Codigo result = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new CodigoDTO(result);
		
	}

	
	@Transactional(readOnly = true)
	public List<CodigoDTO> findAll() {
		List<Codigo> result = repository.findAll(); // Retorna a lista de entidades
		return result.stream().map(CodigoDTO::new).collect(Collectors.toList()); // Converte para DTO
	}

	@Transactional(readOnly = true)
	public List<CodigoDTO> searchCodes(String keyword) {
		List<Codigo> result = repository.findByLinguagemContainingIgnoreCaseOrDescricaoContainingIgnoreCase(keyword,
				keyword);
		return result.stream().map(CodigoDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public CodigoDTO insert(CodigoDTO dto) {
		Codigo entity = new Codigo();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CodigoDTO(entity);
	}

	@Transactional
	public CodigoDTO update(Long id, CodigoDTO dto) {
		try {
			Codigo entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new CodigoDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	public void delete(Long id) {
		if (!repository.existsById(id))
			throw new ResourceNotFoundException("Recurso não encontrado");
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(CodigoDTO dto, Codigo entity) {
		entity.setCodigo(dto.getCodigo());
		entity.setDescricao(dto.getDescricao());
		entity.setImgUrl(dto.getImgUrl());
		entity.setLinguagem(dto.getLinguagem());
	}


	
}