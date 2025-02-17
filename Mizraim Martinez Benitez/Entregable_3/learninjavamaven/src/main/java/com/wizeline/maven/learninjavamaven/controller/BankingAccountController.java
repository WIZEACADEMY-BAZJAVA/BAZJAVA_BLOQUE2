package com.wizeline.maven.learninjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learninjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.model.Post;
import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.service.BankAccountService;
import com.wizeline.maven.learninjavamaven.utils.CommonServices;
import com.wizeline.maven.learninjavamaven.utils.exceptions.ExceptionGenerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learninjavamaven.utils.Utils.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BankingAccountController extends Thread{

    private static final String SUCCESS_CODE = "OK000";

    @Value("${server.ports}")
    private String port;

    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

    private static String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Autowired
    CommonServices commonServices;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private AccountsJSONClient accountsJSONClient;

    //@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user){
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando cursos...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        // Aqui implementaremos la programación funcional
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('GUEST')")
    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts(){
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
    }

    @PutMapping("/updateBankAccount/{user}")
    public ResponseEntity<Optional<BankAccountDTO>> updateBankAccount(@PathVariable(value = "user") String user, @Valid
        @RequestBody BankAccountDTO bankAccountDTODetails) throws ExceptionGenerica{

        BankAccountDTO bankAccountDTO = bankAccountService.findByUser(user);
        if(bankAccountDTO != null){
            bankAccountDTO.setUser(bankAccountDTODetails.getUser());
            BankAccountDTO bankAccountDTOUpdate = bankAccountService.save(bankAccountDTO);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<>(Optional.ofNullable(bankAccountDTOUpdate), responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createUsersThread")
    public ResponseEntity<String> createUsersThread(){
        String responseTextThread = "";
        StringBuilder text = new StringBuilder();
        try (Scanner scanner = new Scanner(responseTextThread)) {
            while (scanner.hasNext()) {
                text.append(scanner.next());
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ExceptionGenerica("Fallo al obtener el request del body");
        }
        String textThread = "";
        textThread = text.toString();
		LOGGER.info(textThread);
		//Se inicia el thread
        BankingAccountController thread = new BankingAccountController();
		thread.start();
		//Esperamos a que termine el thread
		while (thread.isAlive());
        return new ResponseEntity<>(responseTextThread, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts(){
        LOGGER.info("The port used is " + port);
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        LOGGER.info("Retrieving external endpoint ");

        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    //The usage of FeignClient for demo purposes
    //@PreAuthorize("hasRole('GUEST')")
    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId){
        Post postTest = accountsJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." + postTest.getUserId());
        LOGGER.info("Getting post body..." + postTest.getBody());
        LOGGER.info("Getting post title..." + postTest.getTitle());
        postTest.setUserId("External user "+ randomAcountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." + postTest.getUserId());
        LOGGER.info("Setting post body..." + postTest.getBody());
        LOGGER.info("Setting post title...."+ postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId){
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }

    @GetMapping("/sobrecarga")
    public BankAccountDTO sobrecarga(){
        return bankAccountService.getAccountDetails("usuario", "lastUsage", Country.FR);
    }

    @GetMapping("/getEncryptedAccounts")
    public ResponseEntity<List<BankAccountDTO>> encryptedAccounts(){
        LOGGER.info("The port used is "+ port);
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("Procesando peticion HTTP de tipo GET");
        LOGGER.info("Retrieving external endpoint ");

        List<BankAccountDTO> accounts = bankAccountService.encryptedAccounts();
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            // Valida el password del usuario (password)
            if (isPasswordValid(password)) {
                response = commonServices.login(user, password);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
                    Instant finalDeEjecucion = Instant.now();
                    LOGGER.info("LearningJava - Cerrando recursos ...");
                    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
                    LOGGER.info("Tiempo de respuesta: ".concat(total));
                    return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
                }
            } else {
                Instant finalDeEjecucion = Instant.now();
                LOGGER.info("LearningJava - Cerrando recursos ...");
                String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                responseText = "Password Incorrecto";
                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
            }
        } else {
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

}
