package com.waracle.cakemanagerserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanagerserver.controller.model.AddCake;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * Cakes controller test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UiCakesControllerTest {

    @LocalServerPort
    private int port;

    @Sql(scripts = {"/db/schema.sql", "/db/test-data.sql"})
    @Test
    void GetCakes_CakesExist_OkRecordsReturned() {
        final var response = given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("payload", notNullValue())
                .body("payload.size()", is(3))
                .body("payload.find{it.id=='b397e6f2-bfe2-4ba5-bd8c-3c5b4a1a6522'}.title", equalTo("Madeira"))
                .body("payload.find{it.id=='34f5b68f-6a78-4f3e-b3f9-384e49b5ee34'}.title", equalTo("Victoria Sandwich"))
                .body("payload.find{it.id=='592255af-feba-4cf2-b22c-4bb3abdbfac9'}.title", equalTo("Cheesecake"))
                .body("errors", notNullValue())
                .body("errors.size()", is(0));
    }

    @Sql(scripts = {"/db/schema.sql"})
    @Test
    void GetCakes_CakesDoNotExist_NoContent() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @Sql(scripts = {"/db/schema.sql"})
    @Test
    void AddCake_NoData_BadRequest() throws JsonProcessingException {

        final var objectMapper = new ObjectMapper();
        final var addCake = new AddCake();
        final var json = objectMapper.writeValueAsString(addCake);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors.find{it.field=='title'}.message", equalTo("Title is required"))
                .body("errors.find{it.field=='description'}.message", equalTo("Description is required"))
                .body("errors.find{it.field=='imageUrl'}.message", equalTo("Image url is required"));
    }

    @Sql(scripts = {"/db/schema.sql"})
    @Test
    void AddCake_DataTooLong_BadRequest() throws JsonProcessingException {

        final var objectMapper = new ObjectMapper();
        final var addCake = new AddCake();
        addCake.setTitle(StringUtils.repeat("A", 101));
        addCake.setDescription(StringUtils.repeat("B", 501));
        addCake.setImageUrl(StringUtils.repeat("C", 301));
        final var json = objectMapper.writeValueAsString(addCake);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(BAD_REQUEST.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors.size()", equalTo(3))
                .body("errors.find{it.field=='title'}.message", equalTo("Maximum length of title is 100"))
                .body("errors.find{it.field=='description'}.message", equalTo("Maximum length of description is 500"))
                .body("errors.find{it.field=='imageUrl'}.message", equalTo("Maximum length of image url is 300"));
    }

    @Sql(scripts = {"/db/schema.sql", "/db/test-data.sql"})
    @Test
    void AddCake_CakeAlreadyExists_BadRequest() throws JsonProcessingException {

        final var objectMapper = new ObjectMapper();
        final var addCake = new AddCake();
        addCake.setTitle("Madeira");
        addCake.setDescription("A The cake has a firm yet light texture. It is eaten with tea or (occasionally) for breakfast and is traditionally flavoured with lemon");
        addCake.setImageUrl("http://");
        final var json = objectMapper.writeValueAsString(addCake);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("payload", nullValue())
                .body("errors.size()", equalTo(1))
                .body("errors.find{it.field=='title'}.message", equalTo("Cake already exists"));
    }

    @Sql(scripts = {"/db/schema.sql"})
    @Test
    void AddCake_ValidData_Ok() throws JsonProcessingException {

        final var objectMapper = new ObjectMapper();
        final var addCake = new AddCake();
        addCake.setTitle("Lemon drizzle");
        addCake.setDescription("A lemon drizzle Cake is an easy tea cake with a distinctive crunchy lemon glaze");
        addCake.setImageUrl("http://");
        final var json = objectMapper.writeValueAsString(addCake);

        final var response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(String.format("http://localhost:%d/cakes", port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.then()
                .body("payload", notNullValue())
                .body("payload.id", notNullValue())
                .body("payload.title", equalTo("Lemon drizzle"))
                .body("payload.description", equalTo("A lemon drizzle Cake is an easy tea cake with a distinctive crunchy lemon glaze"))
                .body("payload.imageUrl", equalTo("http://"))
                .body("errors", notNullValue())
                .body("errors.size()", is(0));
    }

}
