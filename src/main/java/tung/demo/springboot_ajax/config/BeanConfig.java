package tung.demo.springboot_ajax.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class BeanConfig {
    private RestTemplate restTemplate;

    @Bean
    @Primary
    TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(500);
        t.setAllowCoreThreadTimeOut(true);
        t.setKeepAliveSeconds(120);
        return t;
    }

    @Bean
    @Primary
    RestTemplate restTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            return restTemplate;
        }
        return restTemplate;
    }
}
