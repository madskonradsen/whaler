package com.madskonradsen.whaler;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.madskonradsen.whaler.configuration.Configuration;
import com.madskonradsen.whaler.configuration.Machine;
import com.madskonradsen.whaler.util.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Check {

    private static final Logger LOGGER = LogManager.getLogger(Check.class);

    public Check(Configuration configuration, Machine machine, Credentials credentials) {
        Session session = null;
        try {
            session = new JSch().getSession(credentials.getUsername(), machine.getHost(), 22);
        } catch (JSchException e) {
            LOGGER.error("Couldn't establish connection to host '" + machine.getHost() + "' " + e);
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(credentials.getPassword());
        try {
            session.connect();
        } catch (JSchException e) {
            LOGGER.error("Couldn't login with credentials '"+ credentials.toString() +"' on host '" + machine.getHost() + "' " + e);
        }

        ChannelExec channelExec = null;
        try {
            channelExec = (ChannelExec)session.openChannel("exec");
        } catch (JSchException e) {
            LOGGER.error("Error opening channel on host '" + machine.getHost() + "' " + e);
        }

        InputStream in = null;
        try {
            in = channelExec.getInputStream();
        } catch (IOException e) {
            LOGGER.error("Error getting input stream on host '" + machine.getHost() + "' " + e);
        }

        channelExec.setCommand("docker ps");
        try {
            channelExec.connect();
        } catch (JSchException e) {
            LOGGER.error("Error executing command on host '" + machine.getHost() + "' " + e);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        String response = "";
        try {
            while ((line = reader.readLine()) != null)
            {
                response += line;
            }
        } catch (IOException e) {
            LOGGER.error("Error reading lines " + e);
        }

        int exitStatus = channelExec.getExitStatus();
        channelExec.disconnect();
        session.disconnect();
        if(exitStatus < 0){
            LOGGER.warn("Done, but exit status not set!");
        }
        else if(exitStatus > 0){
            LOGGER.warn("Done, but with error!");
        }
        else{
            LOGGER.info("Done!");
        }

        for(String containerName : machine.getContainers()) {
            if (!response.contains(containerName)) {
                new SendMail(configuration, machine, containerName);
            }
        }
    }
}
