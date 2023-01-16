package nlu.shop.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RegisterDto {
    @NotBlank(message = "username là bắt buộc")
    private String username;
    @NotBlank(message = "Mật khẩu là bắt buộc")
    private String password;
}