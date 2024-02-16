package com.learnmicroserive.employeeservice.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public XStream xStream(){
        try {
            XStream xStream = new XStream();
            xStream.allowTypesByWildcard(new String[]{
                    "com.learnmicroserive.**"
            });
            return xStream;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
