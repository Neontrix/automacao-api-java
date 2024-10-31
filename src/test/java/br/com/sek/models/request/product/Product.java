package br.com.sek.models.request.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString

public class Product {
    private String id;
    private List<String> features;

    private String name;
    private String code;
    private String description;
    private List<Addon> addons;

}

