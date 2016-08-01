package io.bvzx.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lss on 16-8-1.
 */
public class Configure {

    private static final Properties properties;


    static {
        properties=new Properties();
        try {
            properties.load(Configure.class.getClassLoader().getResourceAsStream("config.prop"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getProp(String key){
        return properties.get(key).toString();
    }





}
