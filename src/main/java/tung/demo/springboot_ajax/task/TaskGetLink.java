package tung.demo.springboot_ajax.task;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import tung.demo.springboot_ajax.common.Const;
import tung.demo.springboot_ajax.dto.RequestLink;
import tung.demo.springboot_ajax.service.FshareService;

import java.net.MalformedURLException;
import java.net.URL;

public class TaskGetLink implements Runnable {
    private String url;
    private SimpMessagingTemplate template;
    private String socketTopic;
    private FshareService fshareService;

    public TaskGetLink(RequestLink requestLink, SimpMessagingTemplate template, FshareService fshareService) {
        String url = requestLink.getRequestLink();
        int queryIndex = url.indexOf("?");
        this.url = queryIndex > 0 ? url.substring(0, queryIndex) : url;
        System.out.println(Const.getDate() + " " + this.url);
        this.socketTopic = requestLink.getRequestId();
        this.template = template;
        this.fshareService = fshareService;
    }

    void getLinkAndSendTopicSocket(String requestLink) {
        String tmpDownloadLink = fshareService.getLink(requestLink);
        String downloadLink = "error";
        try {
            URL result = new URL(tmpDownloadLink);
            downloadLink = tmpDownloadLink;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        template.convertAndSend("/topic/" + socketTopic, downloadLink);
    }

    @Override
    public void run() {
        getLinkAndSendTopicSocket(url);
    }
}
