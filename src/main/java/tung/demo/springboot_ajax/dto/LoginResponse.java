package tung.demo.springboot_ajax.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String code;
    private String msg;
    private String token;
    private String session_id;
}
