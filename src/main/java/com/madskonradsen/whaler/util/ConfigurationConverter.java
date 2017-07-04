package com.madskonradsen.whaler.util;

import com.google.gson.Gson;
import com.madskonradsen.whaler.configuration.Configuration;

public class ConfigurationConverter {

    public Configuration convert(String json) {
        return new Gson().fromJson(json, Configuration.class);
    }
}
