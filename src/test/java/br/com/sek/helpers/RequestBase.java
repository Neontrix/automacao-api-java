package br.com.sek.helpers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.qameta.allure.Allure.step;

public abstract class RequestBase {
    protected RequestSpecification request;
    protected String url;

    protected Response executeGet(String path){
        Response response = request.when()
                .get(path);
        step("GET: "+ url.concat(path));
        step("Response: " + response.body().prettyPrint());
        return response;
    }

    protected Response executePost(String path, Object body){
        Response response = request.when()
                .body(body != null ? body : "{}")  // Se o body for nulo, use um corpo vazio
                .with()
                .contentType("application/json")
                .post(path);
        step("POST: "+ url.concat(path));
        step("Response: " + response.body().prettyPrint());
        return response;
    }

    protected Response executePut(String path, Object body){
        Response response = request.when().body(body).put(path);
        step("PUT: "+ url.concat(path));
        step("Response: " + response.body().prettyPrint());
        return response;
    }

    protected Response executePatch(String path, Object body){
        Response response = request.when().body(body).patch(path);
        step("PATCH: "+ url.concat(path));
        step("Response: " + response.body().prettyPrint());
        return response;
    }

    protected Response executeDelete(String path){
        Response response = request.when().delete(path);
        step("DELETE: "+ url.concat(path));
        step("Response: " + response.body().prettyPrint());
        return response;
    }
}
