package com.wizeline.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.bean.BankAccountBean;
import com.wizeline.client.AccountsJSONClient;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.service.BankAccountService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@RestController
@Tag(name="BankingAccount",description="Manejo de las cuentas de un usuario")
public class BankingAccountController {
	
	private static final Logger log = Logger.getLogger(BankingAccountController.class.getName());
	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
	
	
	private final Bucket bucket;

    public BankingAccountController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
	
	@Autowired
	BankAccountService bankAccountService;
	
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	@Value("${server.port}")
    private String port;
	
	@Autowired
	AccountsJSONClient accountsJSONClient;
	
	//Delete Controller ok
	@DeleteMapping("/deleteAccounts")
	public ResponseEntity<String> deleteAccounts() {
		bankAccountService.deleteAccounts();
		return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	}
	
	//ok
	@GetMapping(value= "/getAccountByUser")
	   public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
		
		if (bucket.tryConsume(1)) {
			log.info(msgProcPeticion);
		    Instant inicioDeEjecucion = Instant.now();
		    log.info("LearningJava - Procesando peticion HTTP de tipo GET");
		    List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

		    Instant finalDeEjecucion = Instant.now();

		    log.info("LearningJava - Cerrando recursos ...");
		    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		    log.info("Tiempo de respuesta: ".concat(total));

		    HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		    if(accounts == null)
		    	return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
		    return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
        }
		
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	   
	}
	
	
	//ok
	@PostMapping(value="/postAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postAccount(@RequestBody BankAccountBean account) {
		bankAccountService.postAccount(account);
		return new ResponseEntity<>("Cuenta creada",HttpStatus.OK);
	}
	
	//ok
	@PutMapping(value="/changeAccountCountry", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changeStatusAccount(@RequestBody BankAccountBean account) {
		long updatedAccounts = bankAccountService.putAccount(account);
		return new ResponseEntity<>("Cuentas actualizadas".concat(": "+updatedAccounts), HttpStatus.OK);
	}
	

}
