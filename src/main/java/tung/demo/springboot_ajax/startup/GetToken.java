package tung.demo.springboot_ajax.startup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tung.demo.springboot_ajax.common.Const;
import tung.demo.springboot_ajax.dto.LoginRequest;
import tung.demo.springboot_ajax.dto.LoginResponse;

@Component
public class GetToken implements CommandLineRunner {
    @Value("${fshare.user_email}")
    private String userMail;

    @Value("${fshare.password}")
    private String password;

    @Value("${fshare.app_key}")
    private String appKey;

    @Override
    public void run(String... strings) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_email(userMail);
        loginRequest.setPassword(password);
        loginRequest.setApp_key(appKey);
        RestTemplate restTemplate = new RestTemplate();
        LoginResponse loginResponse = restTemplate.postForObject(Const.FSHARE_ENDPOINT_LOGIN, loginRequest, LoginResponse.class);
        assert loginResponse != null;
        Const.FSHARE_SESSION_ID = loginResponse.getSession_id();
        Const.FSHARE_TOKEN = loginResponse.getToken();
    }
}
