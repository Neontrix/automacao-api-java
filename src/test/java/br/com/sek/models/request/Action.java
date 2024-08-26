package br.com.sek.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Action {
    private String scope;
    private String type;
}
