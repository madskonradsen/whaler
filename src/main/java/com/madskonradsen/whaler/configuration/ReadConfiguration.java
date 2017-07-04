package com.madskonradsen.whaler.configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadConfiguration {

    public static String byPath(String path) throws IOException {
        try(
                FileInputStream fis = new FileInputStream(path);
                BufferedReader br = new BufferedReader( new InputStreamReader(fis, "UTF-8")))
        {
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }
            return sb.toString();
        }
    }
}
