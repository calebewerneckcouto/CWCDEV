package com.devsuperior.cwcdev.tests;

import com.devsuperior.cwcdev.dto.CodigoDTO;
import com.devsuperior.cwcdev.entities.Codigo;

public class CodigoFactory {
	
	public static Codigo createCodigoEntity() {
		Codigo codigo = new Codigo("Java", "Java teste", "codigojava", "http://imagemjava.com");
		return codigo;
	}
	
	public static CodigoDTO createCodigoDTO() {
		Codigo codigo = createCodigoEntity();
		return new CodigoDTO(codigo);
	}
}
