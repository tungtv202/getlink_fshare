package tung.demo.springboot_ajax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tung.demo.springboot_ajax.common.Const;
import tung.demo.springboot_ajax.dto.DownloadRequest;
import tung.demo.springboot_ajax.dto.DownloadResponse;
import tung.demo.springboot_ajax.dto.LoginRequest;
import tung.demo.springboot_ajax.dto.LoginResponse;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FshareService {

    @Value("${fshare.user_email}")
    private String userMail;

    @Value("${fshare.password}")
    private String password;

    @Value("${fshare.app_key}")
    private String appKey;

    @Autowired
    private RestTemplate restTemplate;

    public void setToken() {
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

    public String getLink(String requestLink) {
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setToken(Const.FSHARE_TOKEN);
        downloadRequest.setUrl(requestLink);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "session_id=" + Const.FSHARE_SESSION_ID);
        HttpEntity<DownloadRequest> requestHttpEntity = new HttpEntity<>(downloadRequest, headers);
        DownloadResponse result = restTemplate.postForObject(Const.FSHARE_ENDPOINT_GETLINK, requestHttpEntity, DownloadResponse.class);

        try {
            // remakeLink
            URL myUrl = new URL(result.getLocation());
            String subPrefixServer = myUrl.getHost().replace(".fshare.vn", "");
            return String.format("http://www.%s%s", subPrefixServer + ".tungexplorer.me", myUrl.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "fshare error";
        }
    }
}
