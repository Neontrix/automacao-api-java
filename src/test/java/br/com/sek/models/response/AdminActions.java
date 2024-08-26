package br.com.sek.models.response;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminActions {
    private String scope;
    private String type;
    @JsonProperty("default")
    private Boolean defaultAction;
}
