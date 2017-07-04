package com.madskonradsen.whaler;

import com.madskonradsen.whaler.configuration.Configuration;
import com.madskonradsen.whaler.configuration.Machine;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendMail {

    private static final Logger LOGGER = LogManager.getLogger(SendMail.class);

    public SendMail(Configuration configuration, Machine machine, String containerName) {
        Email email = new SimpleEmail();
        email.setHostName(configuration.getEmail().getSmtpHost());
        email.setSmtpPort(configuration.getEmail().getSmtpPort());
        email.setAuthenticator(new DefaultAuthenticator(configuration.getEmail().getUsername(), configuration.getEmail().getPassword()));
        email.setSSLOnConnect(configuration.getEmail().getUseSSL());
        try {
            email.setFrom(configuration.getEmail().getFromEmail());
            email.setSubject(containerName + " appears to be down!");
            email.setMsg(containerName + " on the host '"+ machine.getHost() +"' appears to be down!");
            email.addTo(configuration.getEmail().getToEmail());
            email.send();
        } catch (EmailException e) {
            LOGGER.error("Couldn't send email " + e);
        }
    }
}
