package dev.ngdangkietswe.swetimetrackingservice.config;

import dev.ngdangkietswe.swejavacommonshared.utils.RestTemplateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ngdangkietswe
 * @since 2/27/2025
 */

@Configuration
public class AppConfig {

    @Bean
    public RestTemplateUtil restTemplateUtil() {
        RestTemplate restTemplate = new RestTemplate();
        return new RestTemplateUtil(restTemplate);
    }
}
