package br.com.sek.models.request.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Addon {
    private String name;
    private String description;

    @Override
    public String toString() {
        return "Addon{" +
                "name: '" + name + '\'' +
                ", description: '" + description + '\'' +
                '}';
    }
}
