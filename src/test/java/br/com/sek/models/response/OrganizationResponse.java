package br.com.sek.models.response;

import lombok.Getter;

@Getter
public class OrganizationResponse {
    private String id;
    private String name;
    private String code;

    @Override
    public String toString() {
        return "OrganizationResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
