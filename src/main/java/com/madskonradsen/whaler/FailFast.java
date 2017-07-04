package com.madskonradsen.whaler;

import com.madskonradsen.whaler.configuration.Configuration;
import com.madskonradsen.whaler.configuration.Machine;
import com.madskonradsen.whaler.util.Credentials;
import com.madskonradsen.whaler.util.MalformedCredentialsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FailFast {

    private static final Logger LOGGER = LogManager.getLogger(FailFast.class);

    public FailFast(Configuration configuration) {

        for(Machine machine : configuration.getMachines()) {
            try {
                Credentials.validate(machine.getCredentials());
            } catch(MalformedCredentialsException e) {
                LOGGER.error("Malformed credentials '" + machine.getCredentials() + "' for machine '" + machine.getHost() +"' " + e);
                System.exit(0);
            }
        }
    }
}
