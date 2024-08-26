package br.com.sek.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientDetails {
    private String client_id;
    private String client_secret;
    private String username;
    private String password;
    private String grant_type;
}