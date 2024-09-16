package com.wizeline.maven.LearningJava.config;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    public String value;

    private DatabaseConfig(String value) {
        this.value = value;
    }

    public static DatabaseConfig getInstance(String value) {
        if (instance == null) {
            instance = new DatabaseConfig(value);
        }
        return instance;
    }

    public String connect(){
        String Message = "Connecting to " + value + " database ...";
        System.out.println();
        return Message;
    }
}
