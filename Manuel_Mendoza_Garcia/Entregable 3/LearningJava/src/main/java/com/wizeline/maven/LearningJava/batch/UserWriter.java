package com.wizeline.maven.LearningJava.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * ItemWriter for UserJob.
 */
public class UserWriter implements ItemWriter<String> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserWriter.class);

    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String data: list) {
            LOGGER.info("UserWriter: Writing data: " + data);
        }
        LOGGER.info("UserWriter: Writing data completed!!");
    }
}
