package com.wizeline.maven.LearningJava.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.LearningJava.LearningJavaApplication;
import com.wizeline.maven.LearningJava.client.AccountsJSONClient;
import com.wizeline.maven.LearningJava.model.BankAccountDTO;
import com.wizeline.maven.LearningJava.model.Post;
import com.wizeline.maven.LearningJava.model.ResponseDTO;
import com.wizeline.maven.LearningJava.service.BankAccountService;
import com.wizeline.maven.LearningJava.service.UserService;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.LearningJava.utils.Utils.*;

@RequestMapping("/api")
@RestController
public class BankingAccountController {

    private static final String SUCCESS_CODE = "OK000";

    @Value("${server.port}")
    private String port;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    UserService userService;

    @Autowired
    AccountsJSONClient accountsJSONClient;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) throws JsonProcessingException {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            // Valida el password del usuario (password)
            if (isPasswordValid(password)) {
                response = userService.login(user, password);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
                    Instant finalDeEjecucion = Instant.now();
                    LOGGER.info("LearningJava - Cerrando recursos ...");
                    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
                    LOGGER.info("Tiempo de respuesta: ".concat(total));
                    return new ResponseEntity(Json.mapper().writeValueAsString(bankAccountDTO), responseHeaders, HttpStatus.OK);
                }
            } else {
                Instant finalDeEjecucion = Instant.now();
                LOGGER.info("LearningJava - Cerrando recursos ...");
                String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                responseText = "Password Incorrecto";
                return new ResponseEntity(Json.mapper().writeValueAsString(responseText), responseHeaders, HttpStatus.OK);
            }
        } else {
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity(Json.mapper().writeValueAsString(responseText), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts() throws JsonProcessingException {
        LOGGER.info("The port used is "+ port);
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        accounts = cypherAccounts(accounts);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(Json.mapper().writeValueAsString(accounts), responseHeaders, HttpStatus.OK);
    }
    @PutMapping("/updateUserAccounts")
    public ResponseEntity<List<BankAccountDTO>> updateUserAccounts(@RequestParam String oldUser, @RequestParam String newUser) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo PUT");
        List<BankAccountDTO> accounts = bankAccountService.updateUserAccounts(oldUser, newUser);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam Optional<String> user) throws JsonProcessingException {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(Json.mapper().writeValueAsString(accounts), responseHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
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
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity(Json.mapper().writeValueAsString(groupedAccounts), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts() {
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
    }

    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        ArrayList<String> mensaje = new ArrayList<String>() {{
            add("Hola");
            add(" ");
            add("invitado");
            add("!");
            add("!");
        }};

        muestraMensajeConsola(mensaje);
        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
    }

    //The usage of FeignClient for demo purposes
    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

        Post postTest = accountsJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." +postTest.getUserId());
        LOGGER.info("Getting post body..." +postTest.getBody());
        LOGGER.info("Getting post title..." +postTest.getTitle());
        postTest.setUserId("External user "+randomAcountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." +postTest.getUserId());
        LOGGER.info("Setting post body..." +postTest.getBody());
        LOGGER.info("Setting post title...."+postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        LOGGER.info("Username:" + account.getUserName());
        this.template.send("useraccount-topic3", account);
    }

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

}