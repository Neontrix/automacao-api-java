package br.com.sek.services;

import br.com.sek.models.request.product.Addon;
import br.com.sek.models.request.product.Product;
import br.com.sek.request.PlatformSek;
import br.com.sek.utils.mass.Products;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.qameta.allure.Allure.step;

public class PortfolioService {
    private final PlatformSek platformSek;
    private final Products products;
    @Getter
    private Response response;
    private Product product;
    private String productId;
    private String validationStatus;
    private String storedId;
   //private String invalidId;
    private List<Map<String, Object>> content;
    private static final Set<String> VALID_FIELDS = new HashSet<>(Set.of(
            "name", "code", "description", "addons"
    ));

    public PortfolioService(PlatformSek platformSek, Products products) {
        this.platformSek = platformSek;
        this.products = products;
    }


    // region Portfolio Methods
    public void sendCreatePortfolioRequest(String productCode) {
        // Obter a lista de addons diretamente
        List<Addon> addons = this.products.getAddons("with");

        // Enviar a requisição passando diretamente a lista de addons
        this.response = this.platformSek.postPortfolio(productCode, addons);
    }



}
