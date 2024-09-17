package com.wizeline.maven.LearningJava.controller;

import com.wizeline.maven.LearningJava.LearningJavaApplication;
import com.wizeline.maven.LearningJava.model.ResponseDTO;
import com.wizeline.maven.LearningJava.model.UserDTO;
import com.wizeline.maven.LearningJava.service.ThreadService;
import com.wizeline.maven.LearningJava.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;


@Tag(name = "User",
        description = "Contiene operaciones comunes realizadas para usuarios.")
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ThreadService threadService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
    private static String responseTextThread = "";
    private static String textThread = "";

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password) {
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = new ResponseDTO();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        UserDTO userName = new UserDTO();
        StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
        builder.append("?user=" + user);
        builder.append("&password=" + password);
        URI uri = URI.create(builder.toString());
        userName = userName.getParameters(splitQuery(uri));
        response = userService.login(userName.getUser(), userName.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = new ResponseDTO();

        response = createUser(userDTO.getUser(), userDTO.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info(msgProcPeticion);
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach(userDTO -> {
            String user = userDTO.getUser();
            String password = userDTO.getPassword();
            response.set(createUser(user, password));
            responseList.add(response.get());

                }
        );
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createDefaultUsers")
    public ResponseEntity<List<ResponseDTO>> createDefaultUsers() {
        LOGGER.info(msgProcPeticion);
        List<ResponseDTO> responses = new ArrayList<>();
        ResponseDTO user1 = null, user2 = null, user3 = null;

        try{
            LOGGER.info(String.format("Llamando createUserThread user: %s, active count: %s, Pool size: %s, Queue Size: %s", "user4@wizeline.com", taskExecutor.getActiveCount(), taskExecutor.getPoolSize(), taskExecutor.getThreadPoolExecutor().getQueue().size()));
            user1 =  threadService.createUserThread("user4@wizeline.com", "Pass4$");

            LOGGER.info(String.format("Llamando createUserThread user: %s, active count: %s, Pool size: %s, Queue Size: %s", "user5@wizeline.com", taskExecutor.getActiveCount(), taskExecutor.getPoolSize(), taskExecutor.getThreadPoolExecutor().getQueue().size()));
            user2 = threadService.createUserThread("user5@wizeline.com", "Pass5$");

            LOGGER.info(String.format("Llamando createUserThread user: %s, active count: %s, Pool size: %s, Queue Size: %s", "user6@wizeline.com", taskExecutor.getActiveCount(), taskExecutor.getPoolSize(), taskExecutor.getThreadPoolExecutor().getQueue().size()));
            user3 = threadService.createUserThread("user6@wizeline.com", "Pass6$");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responses.add(user1);
        responses.add(user2);
        responses.add(user3);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(responses, responseHeaders, HttpStatus.OK);
    }


    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

    private ResponseDTO createUser(String user, String password) {
        return userService.createUser(user, password);
    }
}