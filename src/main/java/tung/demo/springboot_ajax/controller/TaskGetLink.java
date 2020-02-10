package tung.demo.springboot_ajax.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestTemplate;
import tung.demo.springboot_ajax.common.Const;
import tung.demo.springboot_ajax.dto.DownloadRequest;
import tung.demo.springboot_ajax.dto.DownloadResponse;
import tung.demo.springboot_ajax.dto.RequestLink;

import java.net.MalformedURLException;
import java.net.URL;


public class TaskGetLink implements Runnable {
    private RestTemplate restTemplate;
    private String url;
    private SimpMessagingTemplate template;
    private String socketTopic;

    public TaskGetLink(RequestLink requestLink, SimpMessagingTemplate template) {
        String url = requestLink.getRequestLink();
        int queryIndex = url.indexOf("?");
        this.url = queryIndex > 0 ? url.substring(0, queryIndex) : url;
        System.out.println(Const.getDate() + " " + this.url);
        this.socketTopic = requestLink.getRequestId();
        this.restTemplate = new RestTemplate();
        this.template = template;
    }

    void getLinkAndSendTopicSocket(String requestLink) {
        String tmpDownloadLink = getLink(requestLink);
        String downloadLink = "error";
        try {
            URL result = new URL(tmpDownloadLink);
            downloadLink = tmpDownloadLink;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        template.convertAndSend("/topic/" + socketTopic, downloadLink);
    }

    String getLink(String requestLink) {
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setToken(Const.FSHARE_TOKEN);
        downloadRequest.setUrl(requestLink);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "session_id=" + Const.FSHARE_SESSION_ID);
        HttpEntity<DownloadRequest> requestHttpEntity = new HttpEntity<>(downloadRequest, headers);
        DownloadResponse result = restTemplate.postForObject(Const.FSHARE_ENDPOINT_GETLINK, requestHttpEntity, DownloadResponse.class);
        assert result != null;

        // remakeLink
        try {
            URL myUrl = new URL(result.getLocation());
            String subPrefixServer = myUrl.getHost().replace(".fshare.vn", "");
            return String.format("http://www.%s%s", subPrefixServer + ".tungexplorer.me", myUrl.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        }
    }


    @Override
    public void run() {
        getLinkAndSendTopicSocket(url);
    }
}
