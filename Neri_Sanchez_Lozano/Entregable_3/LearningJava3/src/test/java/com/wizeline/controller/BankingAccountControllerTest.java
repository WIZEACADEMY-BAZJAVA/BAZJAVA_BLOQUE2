package com.wizeline.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.bean.BankAccountBean;
import com.wizeline.enums.Country;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.service.BankAccountService;
import com.wizeline.utils.Utils;

@ExtendWith(MockitoExtension.class)
class BankingAccountControllerTest {
	
	private static final Logger log = Logger.getLogger(BankingAccountControllerTest.class);
	
	@InjectMocks
	BankingAccountController bankingAccountController;
	
	@Mock
	BankAccountService bankAccountService;
	
	@InjectMocks
	Utils utils;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	
	
	@Test
	@DisplayName("Prueba para el borrado de todas la cuentas bancarias en BD")
	void deleteAccounts() {
		log.info("Se prueba Happy del service /api/deleteAccounts");
		
		final ResponseEntity<?> respuesta = bankingAccountController.deleteAccounts();
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		
		log.info("Respuesta del servicio: " + respuesta.getStatusCode());		
	}
	
	
	@Test
	void DadoUnClienteBancario_ActualizaElPais_RegresaExito() {
		log.info("Prueba unitaria del servicio /changeAccountCountry");
		//Configura
		BankAccountBean bankAccountBean = new BankAccountBean();
		bankAccountBean.setUser("Neri Sanchez Lozano");
		bankAccountBean.setCountry(Country.US);
		//Procesa
		when(bankAccountService.putAccount(bankAccountBean)).thenReturn(1L);
		final ResponseEntity<?> respuesta = bankingAccountController.changeStatusAccount(bankAccountBean);
		//Valida
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());	
		log.info("Resultado de la prueba /changeAccountCountry: " + respuesta.getStatusCode());
		
	}
	
	
	
	
	@Test
	void DadaUnaNuevaCuentaBancaria_SalvarlaenBD_RegresaExito() {
		log.info("Prueba unitaria del servicio /changeAccountCountry");
		//Configura
		BankAccountBean bankAccountBean = new BankAccountBean();
		bankAccountBean.setUser("Neri Sanchez Lozano");
		bankAccountBean.setCountry(Country.FR);
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setUser("Neri Sanchez Lozano");
		bankAccountDTO.setCountry("FRANCIA");
		
		//Procesa
		when(bankAccountService.postAccount(bankAccountBean)).thenReturn(bankAccountDTO);
		final ResponseEntity<?> respuesta = bankingAccountController.postAccount(bankAccountBean);
		//Valida
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());	
		log.info("Resultado de la prueba /changeAccountCountry: " + respuesta.getStatusCode());
		
	}
	
	
	
	@Test
	void dadoUsuarioConCuenta_BuscaEnBDCuentasDelUsuario_RegresaListaDeCuentas() {
		log.info(
				"Se prueba Happy Path de endpoint /getAccountByUser con user y contraseña user1@wizeline.com, conTr@53na");
		String user = "user1@wizeline.com";
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountNumber(utils.randomAcountNumber());
		bankAccountDTO.setAccountName("Dummy Account ".concat(utils.randomInt()));
		bankAccountDTO.setUser(user);
		bankAccountDTO.setAccountBalance(utils.randomBalance());
		bankAccountDTO.setAccountType(utils.pickRandomAccountType());
		bankAccountDTO.setCountry(utils.getCountry(Country.MX));
		bankAccountDTO.getLastUsage();
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate usage = LocalDate.parse("09-10-2022", dateformatter);
		bankAccountDTO.setCreationDate(usage.atStartOfDay());
		bankAccountDTO.setAccountActive(true);
		List<BankAccountDTO> listaDeCuentas = new ArrayList<>();
		listaDeCuentas.add(bankAccountDTO);
		when(bankAccountService.getAccountByUser(user)).thenReturn(listaDeCuentas);
		final ResponseEntity<?> respuesta = bankingAccountController.getAccountByUser(user);
		assertAll(
				() -> assertNotNull(respuesta),
				() -> assertEquals(HttpStatus.OK, respuesta.getStatusCode()),
				() -> assertEquals(listaDeCuentas, respuesta.getBody()));
		log.info("Resultado de la prueba /getAccountByUser: " + respuesta.getStatusCode());
	}
	
	
	
	//Prueba Unitaria Edge Case  de endpoint /getAccountByUser
	@Test
	void DadoUsuarioConCuenta_BuscaEnBDCuentasDelUsuario_RegresaNotFound() {
		log.info("Se prueba Edge Case de endpoint /getAccountByUser con user user59@wizeline.com");
		String user = "user59@wizeline.com";
		when(bankAccountService.getAccountByUser(user)).thenReturn(null);
		final ResponseEntity<?> respuesta = bankingAccountController.getAccountByUser(user);
		assertAll(
				() -> assertNotNull(respuesta),
				() -> assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode()));
		log.info("Resultado de la prueba /getAccountByUser: " + respuesta.getStatusCode());
		}
	

}
