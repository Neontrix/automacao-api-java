package br.com.sek.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Policy {
    private String sid;
    private List<Action> actions;
    private List<String> resources;
}
