package br.com.sek.models.request;

import br.com.sek.models.request.product.Product;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class User {
    private String name;
    private String email;
    private Phone phone;
    private List<Product> products;
    private String company;
    private List<Role> roles;
    private String title;
    private Settings settings;

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
