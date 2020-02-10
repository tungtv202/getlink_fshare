package tung.demo.springboot_ajax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tung.demo.springboot_ajax.dto.RequestLink;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class GetLinkController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TaskExecutor task;

    @PostMapping("/api/get-link")
    public ResponseEntity<?> triggerTaskGetLinkViaAjax(@Valid @RequestBody RequestLink requestLink, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("error");
        }
        String validate = validate(requestLink.getRequestLink());
        if (!"valid".equals(validate)) {
            return ResponseEntity.ok(validate);
        }
        task.execute(new TaskGetLink(requestLink, template));
        return ResponseEntity.ok("ok");
    }

    String validate(String url) {
        try {
            URL urlRequst = new URL(url);
            if (!"www.fshare.vn".equals(urlRequst.getHost())) {
                return "only fshare link";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "your link invalid";
        }
        return "valid";
    }
}
