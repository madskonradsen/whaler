package com.madskonradsen.whaler.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Credentials {

    private static final Logger LOGGER = LogManager.getLogger(Credentials.class);

    private final String username;
    private final String password;

    public Credentials(String credentials) {
        String[] parts = credentials.split(":");
        username = parts[0];
        password = parts[1];
    }

    public static void validate(String credentials) throws MalformedCredentialsException {
        int occurences = 0;
        int index = -1;
        while((index = credentials.indexOf(":", index+1)) > 0) occurences++;

        String[] parts = credentials.split(":");

        if(occurences != 1 || "".equals(parts[0]) || "".equals(parts[1])) {
            throw new MalformedCredentialsException();
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return username + ":" + password;
    }

}
