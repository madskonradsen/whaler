package com.madskonradsen.whaler.configuration;

import java.util.List;

public class Configuration {

    private List<Machine> machines;
    private Email email;

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
