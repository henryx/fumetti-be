package com.application;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.responses.ErrorResponse;
import com.application.fumetti.mappers.responses.IndexResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, IndexResponse.class);

        resp.then().assertThat().statusCode(200);
        Assertions.assertEquals(res.getOperation(), Operations.INDEX.getOperation());
        Assertions.assertEquals(res.getResult(), Results.OK.getResult());
    }

    @Test
    public void getNotFound() throws JsonProcessingException {
        final String BASE_PATH = "/notfound";

        var resp = RestAssured.given()
                .header("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(BASE_PATH + "/notfound").then().contentType(ContentType.JSON).extract().response();

        var body = resp.body().asString();
        var res = this.mapper.readValue(body, ErrorResponse.class);

        resp.then().assertThat().statusCode(404);
        Assertions.assertEquals(res.getResult(), Results.KO.getResult());
    }
}