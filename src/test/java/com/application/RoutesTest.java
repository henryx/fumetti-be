package com.application;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;
import com.application.fumetti.mappers.data.EditorData;
import com.application.fumetti.mappers.data.NationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.HashMap;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoutesTest {
    protected final ObjectMapper mapper;

    public RoutesTest() {

        this.mapper = new ObjectMapper();
    }

    @Test
    public void getRoot() throws JsonProcessingException {
        final String BASE_PATH = "/";

        var resp = RestAssured.given()
                .header("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(BASE_PATH).then().contentType(ContentType.JSON).extract().response();

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(res.getOperation(), Operations.INDEX.getOperation());
        Assertions.assertEquals(res.getResult(), Results.OK.getResult());
    }

    @Test
    public void getNotFound() throws JsonProcessingException {
        final String BASE_PATH = "/notfound";

        var resp = RestAssured.given()
                .header("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(BASE_PATH + "/notfound").then().contentType(ContentType.JSON).extract().response();

        resp.then().assertThat().statusCode(404);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(res.getResult(), Results.KO.getResult());
    }

    @Test
    @Order(1)
    public void postCurrencies() throws JsonProcessingException {
        final String BASE_PATH = "/currencies";
        var req = new CurrencyData(null, "Euro", "€", new BigDecimal("1936.27"), new BigDecimal("1.00"));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(Operations.LOOKUP.getOperation(), res.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getCurrencies() throws JsonProcessingException {
        final String BASE_PATH = "/currencies";

        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        // Because Response class doesn't have logic to map subclasses, we need to verify data with manual mapping
        for (var item : res.getData()) {
            @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) item;
            var converted = CurrencyData.map(map);
            Assertions.assertInstanceOf(CurrencyData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    @Order(2)
    public void postNations() throws JsonProcessingException {
        final String BASE_PATH = "/nations";
        var req = new NationData(null, "Italia", "IT",
                new CurrencyData(1L, "Euro", "€", new BigDecimal("1936.27"),
                        new BigDecimal("1.00")));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(Operations.LOOKUP.getOperation(), res.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getNations() throws JsonProcessingException {
        final String BASE_PATH = "/nations";

        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        // Because Response class doesn't have logic to map subclasses, we need to verify data with manual mapping
        for (var item : res.getData()) {
            @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) item;
            var converted = NationData.map(map);

            Assertions.assertInstanceOf(NationData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    @Order(3)
    public void postEditors() throws JsonProcessingException {
        final String BASE_PATH = "/editors";

        var req = new EditorData(null, "test editore", "test sede", "https://sito",
                new NationData(1L, "Italia", "IT",
                        new CurrencyData(1L, "Euro", "€", new BigDecimal("1936.27"),
                                new BigDecimal("1.00"))));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(Operations.EDITORS.getOperation(), res.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getEditors() throws JsonProcessingException {
        final String BASE_PATH = "/editors";

        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, Response.class);

        // Because Response class doesn't have logic to map subclasses, we need to verify data with manual mapping
        for (var item : res.getData()) {
            @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) item;
            var converted = EditorData.map(map);

            Assertions.assertInstanceOf(EditorData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.EDITORS.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }
}