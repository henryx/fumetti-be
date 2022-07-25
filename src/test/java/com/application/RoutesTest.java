package com.application;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.*;
import com.application.fumetti.mappers.data.lookup.books.BindingsData;
import com.application.fumetti.mappers.data.lookup.series.FrequencyData;
import com.application.fumetti.mappers.data.lookup.series.GenreData;
import com.application.fumetti.mappers.data.lookup.series.StatusData;
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
        String body;
        Response res;

        body = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/currencies").body().asString();
        res = this.mapper.readValue(body, Response.class);
        @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) res.getData().get(0);

        var req = new NationData(null, "Italia", "IT",
                CurrencyData.map(map));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        body = resp.body().asString();
        res = this.mapper.readValue(body, Response.class);

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
        String body;
        Response res;

        body = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/nations").body().asString();
        res = this.mapper.readValue(body, Response.class);
        @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) res.getData().get(0);

        var req = new EditorData(null, "test editore", "test sede",
                "https://sito", NationData.map(map));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        body = resp.body().asString();
        res = this.mapper.readValue(body, Response.class);

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

    @Test
    @Order(4)
    public void postCollections() throws JsonProcessingException {
        final String BASE_PATH = "/collections";
        String body;
        Response res;

        body = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/editors").body().asString();
        res = this.mapper.readValue(body, Response.class);
        @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) res.getData().get(0);

        var req = new CollectionData(null, "test collana", EditorData.map(map));

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        body = resp.body().asString();
        res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(Operations.COLLECTIONS.getOperation(), res.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getCollections() throws JsonProcessingException {
        final String BASE_PATH = "/collections";

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
            var converted = CollectionData.map(map);

            Assertions.assertInstanceOf(CollectionData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.COLLECTIONS.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    @Order(5)
    public void postSeries() throws JsonProcessingException {
        final String BASE_PATH = "/series";
        String body;
        Response res;

        body = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/collections").body().asString();
        res = this.mapper.readValue(body, Response.class);
        @SuppressWarnings("unchecked") var map = (HashMap<String, Object>) res.getData().get(0);

        var req = new SeriesData(null, "test serie", CollectionData.map(map),
                new GenreData(null, "Horror"),
                new FrequencyData(null, "Settimanale"),
                new StatusData(null, "In corso"), "test nota");

        var json = this.mapper.writeValueAsString(req);
        var resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(json)
                .post(BASE_PATH);

        resp.then().assertThat().statusCode(200);

        body = resp.body().asString();
        res = this.mapper.readValue(body, Response.class);

        Assertions.assertEquals(Operations.SERIES.getOperation(), res.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getSeries() throws JsonProcessingException {
        final String BASE_PATH = "/series";

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
            var converted = SeriesData.map(map);

            Assertions.assertInstanceOf(SeriesData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.SERIES.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getSeriesFrequency() throws JsonProcessingException {
        final String BASE_PATH = "/series/frequencies";

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
            var converted = FrequencyData.map(map);

            Assertions.assertInstanceOf(FrequencyData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getSeriesStatus() throws JsonProcessingException {
        final String BASE_PATH = "/series/status";

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
            var converted = StatusData.map(map);

            Assertions.assertInstanceOf(StatusData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getSeriesGenre() throws JsonProcessingException {
        final String BASE_PATH = "/series/genre";

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
            var converted = GenreData.map(map);

            Assertions.assertInstanceOf(GenreData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }

    @Test
    public void getBooksBindings() throws JsonProcessingException {
        final String BASE_PATH = "/books/bindings";

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
            var converted = BindingsData.map(map);

            Assertions.assertInstanceOf(BindingsData.class, converted); // TODO: useless?
        }

        Assertions.assertEquals(res.getOperation(), Operations.LOOKUP.getOperation());
        Assertions.assertEquals(Results.OK.getResult(), res.getResult());
    }
}