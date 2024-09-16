package com.wizeline.maven.LearningJava.controller;

import com.wizeline.maven.LearningJava.command.CopyCommand;
import com.wizeline.maven.LearningJava.command.Invoker;
import com.wizeline.maven.LearningJava.command.Receiver;
import com.wizeline.maven.LearningJava.config.DatabaseConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatternDesignController {

    @GetMapping("/patterns/creation")
    public ResponseEntity<String> getDatabaseConnection(){
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance("MongoDB");
        String connection = databaseConfig.connect();
        return new ResponseEntity<>(connection, HttpStatus.OK);
    }

    @GetMapping("/patterns/behavioral")
    public ResponseEntity<String> copyCommand(){
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        invoker.executeOperation(new CopyCommand(new Receiver()));
        invoker.executeOperation(() -> receiver.paste());
        invoker.executeOperation(() -> receiver.cut());
        return new ResponseEntity<>("BehavioralPattern Sucessfull", HttpStatus.OK);
    }

}
