package br.com.sek.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String access_token;
    private String id_token;
    private String scope;
    private int expires_in;
    private String token_type;
}