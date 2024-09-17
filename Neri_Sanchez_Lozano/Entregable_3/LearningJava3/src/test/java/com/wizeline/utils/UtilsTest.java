package com.wizeline.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UtilsTest {
	
	public static final Logger log = Logger.getLogger(UtilsTest.class);
	
	static String passInvalid;
	static String passValid;
	
	//String passInvalid;
	//String passValid;
	
	/*@BeforeEach
	public void setUp() {
		//valores calculados
		passInvalid = "nerione";
		passValid = "01neR@.9";
	}*/
	
	@BeforeAll
	public static void setUp() {
		log.info("Se inicializan componentes transversales");
		passInvalid = "nerione";
		passValid = "01neR@.9";
	}
	
	@Nested
	class validPasswordTest{
		
		@Test
		void DadoUnPassword_ValidaPasswordPorExpresionRegular_DevuelvePasswordInvalido() {
			
			assertFalse(Utils.isPasswordValid(passInvalid), "El password enviado no debe cumplir el formato solicitado");
		}
		
		@Test
		void DadoUnPassword_ValidaPasswordPorExpresionRegular_DevuelvePasswordValido() {
			
			assertTrue(Utils.isPasswordValid(passValid), "El password enviado debe cumplir el formato solicitado");
		}
		
		@Test
		@DisplayName("Renombre de metodo TEST - Regresa una exception")
		void DadoUnPasswordNulo_ValidaPasswordPorExpresionRegular_RegresaUnaException() {
			assertThrows(NullPointerException.class, () -> Utils.isPasswordValid(null));
		}
		
	}
	
	@AfterAll
	public static void afterAllTests() {
		//valores calculados
		passInvalid = null;
		passValid = null;
		log.info("Se liberan todos los recursos.");
	}

}
