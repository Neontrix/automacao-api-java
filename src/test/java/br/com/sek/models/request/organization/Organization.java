package br.com.sek.models.request.organization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString

public class Organization {
    private UUID id;
    private String name;
    private Integer industry;
    private String code;
    private String countryCode;
    private List<Products> products;
    private List<Bindings> bindings;
    private List<AcceptDomains> acceptDomains;
}
