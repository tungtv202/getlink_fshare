package tung.demo.springboot_ajax.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadRequest {
    private String token;
    private String url;
}
