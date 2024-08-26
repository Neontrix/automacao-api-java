package br.com.sek.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Phone {
    private String countryCode;
    private String areaCode;
    private String phoneNumber;

    @Override
    public String toString() {
        return countryCode + areaCode + phoneNumber;
    }
}
