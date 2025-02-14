package com.devsuperior.cwcdev.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.cwcdev.Exceptions.DatabaseException;
import com.devsuperior.cwcdev.Exceptions.ResourceNotFoundException;
import com.devsuperior.cwcdev.dto.CodigoDTO;
import com.devsuperior.cwcdev.entities.Codigo;
import com.devsuperior.cwcdev.repositories.CodigoRepository;
import com.devsuperior.cwcdev.tests.CodigoFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class CodigoServiceTest {
	
	@InjectMocks
	private CodigoService codigoService;

	@Mock
	private CodigoRepository codigoRepository;

	private long existingCodigoId, nonExistingCodigoId, dependingCodigoId;
	private String codigolinguagem;
	private Codigo codigoEntity;
	private CodigoDTO codigoDTO;
	private PageImpl<Codigo> page;


	@BeforeEach
	void setUp() throws Exception {
		existingCodigoId = 1L;
		nonExistingCodigoId = 2L;
		dependingCodigoId = 3L;
		codigolinguagem = "Java";

		codigoEntity = CodigoFactory.createCodigoEntity();
		codigoDTO = CodigoFactory.createCodigoDTO();
	

		

		Mockito.when(codigoRepository.findById(existingCodigoId)).thenReturn(Optional.of(codigoEntity));
		Mockito.when(codigoRepository.findById(nonExistingCodigoId)).thenReturn(Optional.empty());

		Mockito.when(codigoRepository.save(any())).thenReturn(codigoEntity);

		Mockito.when(codigoRepository.getReferenceById(existingCodigoId)).thenReturn(codigoEntity);
		Mockito.when(codigoRepository.getReferenceById(nonExistingCodigoId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(codigoRepository).deleteById(existingCodigoId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(codigoRepository).deleteById(dependingCodigoId);

		Mockito.when(codigoRepository.existsById(existingCodigoId)).thenReturn(true);
		Mockito.when(codigoRepository.existsById(dependingCodigoId)).thenReturn(true);
		Mockito.when(codigoRepository.existsById(nonExistingCodigoId)).thenReturn(false);
		 Mockito.when(codigoRepository.findById(existingCodigoId)).thenReturn(Optional.of(codigoEntity));
	}
	
	
	
	
	@Test
	void findById_DeveRetornarCodigoDTO_QuandoIdExistir() {
	    // Arrange
	    Long idExistente = 1L;
	    Codigo codigo = new Codigo();
	    codigo.setId(idExistente);
	   

	    // Act
	    CodigoDTO result = codigoService.findById(idExistente);

	    // Assert
	    Assertions.assertNotNull(result);
	    verify(codigoRepository, times(1)).findById(idExistente);
	}
	
	@Test
	void update_DeveAtualizarCodigo_QuandoIdExistir() {
	    // Arrange
	    Long idExistente = 1L;
	    Codigo entity = new Codigo();
	    entity.setId(idExistente);
	    entity.setCodigo("Antigo Código");

	    CodigoDTO dto = new CodigoDTO();
	    dto.setCodigo("Novo Código");

	    when(codigoRepository.getReferenceById(idExistente)).thenReturn(entity);
	    when(codigoRepository.save(any(Codigo.class))).thenReturn(entity);

	    // Act
	    CodigoDTO result = codigoService.update(idExistente, dto);

	    // Assert
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals("Novo Código", result.getCodigo());
	    verify(codigoRepository, times(1)).getReferenceById(idExistente);
	    verify(codigoRepository, times(1)).save(any(Codigo.class));
	}


	@Test
	void update_DeveLancarResourceNotFoundException_QuandoIdNaoExistir() {
	    // Arrange
	    Long idInexistente = 99L;
	    CodigoDTO dto = new CodigoDTO();
	    
	    when(codigoRepository.getReferenceById(idInexistente)).thenThrow(new EntityNotFoundException());

	    // Act & Assert
	    Assertions.assertThrows(ResourceNotFoundException.class, () -> codigoService.update(idInexistente, dto));

	    verify(codigoRepository, times(1)).getReferenceById(idInexistente);
	}

	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			codigoService.findById(nonExistingCodigoId);
		});
	}
	
	@Test
	public void insertShouldReturnCodigoDTO() {
		CodigoDTO result = codigoService.insert(codigoDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(codigoEntity.getId(), result.getId());
		Assertions.assertEquals(codigoEntity.getLinguagem(), result.getLinguagem());
	}
	
	
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			codigoService.update(nonExistingCodigoId, codigoDTO);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			codigoService.delete(existingCodigoId);
		});
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			codigoService.delete(nonExistingCodigoId);
		});
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			codigoService.delete(dependingCodigoId);
		});
	}
}