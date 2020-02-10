package tung.demo.springboot_ajax.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String user_email;
    private String password;
    private String app_key;
}
