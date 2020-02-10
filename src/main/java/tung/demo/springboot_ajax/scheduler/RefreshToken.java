package tung.demo.springboot_ajax.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tung.demo.springboot_ajax.service.FshareService;

@Component
public class RefreshToken {

    @Autowired
    private FshareService fshareService;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void run() {
        fshareService.setToken();
    }
}
