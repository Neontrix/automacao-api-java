package br.com.sek.models.request.organization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter

public class Organization {
    private String name;
    private String code;
    private Boolean mfaRequired;
    private List<Members> members;
    private List<Products> products;
    private List<Bindings> bindings;
    private List<AcceptDomains> acceptDomains;

    @Override
    public String toString() {
        return "Product{" +
                "name: '" + name + '\'' +
                ", code: '" + code + '\'' +
                ", mfaRequired:'" + mfaRequired + '\'' +
                ", members: " + members +
                ", products: " + products +
                ", bindings: " + bindings +
                ", acceptDomains: " + acceptDomains +
                '}';
    }

}
