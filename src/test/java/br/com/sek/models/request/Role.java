package br.com.sek.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Role {
    private String name;
    private List<Policy> policies;
}
