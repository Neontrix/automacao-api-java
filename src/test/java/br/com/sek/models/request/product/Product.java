package br.com.sek.models.request.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Product {
    private String id;
    private List<String> features;

    private String name;
    private String code;
    private String description;
    private List<Addon> addons;

    @Override
    public String toString() {
        return "Product{" +
                "name: '" + name + '\'' +
                ", code: '" + code + '\'' +
                ", description:'" + description + '\'' +
                ", addons: " + addons +
                '}';
    }
}

