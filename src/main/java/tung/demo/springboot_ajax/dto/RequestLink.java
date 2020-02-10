package tung.demo.springboot_ajax.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RequestLink {

    @NotEmpty
    String requestLink;

    @NotEmpty
    String requestId;
}