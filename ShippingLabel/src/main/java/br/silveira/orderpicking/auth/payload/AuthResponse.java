package br.silveira.orderpicking.auth.payload;

import lombok.Data;

@Data
public class AuthResponse {

    private Long companyId;
    private String username;
    private String token;

    public AuthResponse( Long companyId, String username, String token) {
        this.companyId = companyId;
        this.username = username;
        this.token = token;
    }
}
