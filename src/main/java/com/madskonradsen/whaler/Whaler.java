package com.madskonradsen.whaler;

import com.madskonradsen.whaler.configuration.Configuration;
import com.madskonradsen.whaler.configuration.Machine;
import com.madskonradsen.whaler.configuration.ReadConfiguration;
import com.madskonradsen.whaler.util.ConfigurationConverter;
import com.madskonradsen.whaler.util.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Whaler {

    private static final Logger LOGGER = LogManager.getLogger(Whaler.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Whaler...");
        String configPath;

        if(args[0] != null && args[0].equals("--help")) {
            printHelpMessage();
        }

        if(args[0] != null) {
            LOGGER.info("Using configuration file located at: " + args[0]);
            configPath = args[0];
        } else {
            LOGGER.info("Using default configuration file named 'config.json' and located right next to the jar");
            configPath = "./config.json";
        }

        String jsonContent = null;
        try {
            jsonContent = ReadConfiguration.byPath(configPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't load a config-file: " + new File(configPath).getAbsolutePath());
            printHelpMessage();
            System.exit(0);
        }

        Configuration configuration = new ConfigurationConverter().convert(jsonContent);

        new FailFast(configuration);

        while(true) {
            for(Machine machine : configuration.getMachines()) {
                Credentials credentials = new Credentials(machine.getCredentials());
                new Check(configuration, machine, credentials);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }

    }

    private static void printHelpMessage() {
        LOGGER.info("The config.json file should have the following structure: " +
                "{\n" +
                "  \"machines\": [\n" +
                "    {\n" +
                "      \"host\": \"my-machine-dns\",\n" +
                "      \"credentials\": \"username:password\",\n" +
                "      \"containers\": [\n" +
                "        \"dockerslave1\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"email\": {\n" +
                "    \"smtpHost\": \"smtp.googlemail.com\",\n" +
                "    \"smtpPort\": 465,\n" +
                "    \"username\": \"username\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"fromEmail\": \"from@from.com\",\n" +
                "    \"toEmail\": \"to@to.com\",\n" +
                "    \"useSSL\": true\n" +
                "  }\n" +
                "}" +
                "");
    }

}