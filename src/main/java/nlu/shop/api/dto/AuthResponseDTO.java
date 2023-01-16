package nlu.shop.api.dto;

import lombok.Data;
import nlu.shop.api.security.SecurityConstants;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private int expirationTime = (int) SecurityConstants.JWT_EXPIRATION;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
