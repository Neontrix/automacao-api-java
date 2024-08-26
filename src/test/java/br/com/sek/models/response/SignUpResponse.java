package br.com.sek.models.response;

import br.com.sek.models.request.Phone;
import lombok.Getter;

@Getter
public class SignUpResponse {
    private String id;
    private String createdTime;
    private String updatedTime;
    private String activatedTime;
    private String deactivatedTime;
    private String name;
    private String title;
    private String company;
    private String phone;
    private String email;
    private String invitedBy;
    private String locale;
    private String timezone;
}
