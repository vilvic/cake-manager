package com.waracle.cakemanagerserver.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * Cakes rest controller test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestCakesControllerTest {

    @LocalServerPort
    private int port;

    @Sql(scripts = {"/db/schema.sql", "/db/test-data.sql"})
    @Test
    void GetCakes_CakesExist_OkRecordsReturned() {
        final var response = given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%d/rest/cakes", port))
                .then()
                .statusCode(OK.value())
                .extract()
                .response();

        response.prettyPrint();

        response.then()
                .body("find{it.id=='b397e6f2-bfe2-4ba5-bd8c-3c5b4a1a6522'}.title", equalTo("Madeira"))
                .body("find{it.id=='34f5b68f-6a78-4f3e-b3f9-384e49b5ee34'}.title", equalTo("Victoria Sandwich"))
                .body("find{it.id=='592255af-feba-4cf2-b22c-4bb3abdbfac9'}.title", equalTo("Cheesecake"));
    }

    @Sql(scripts = {"/db/schema.sql"})
    @Test
    void GetCakes_CakesDoNotExist_NoContent() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%d/rest/cakes", port))
                .then()
                .statusCode(NO_CONTENT.value());
    }

}
