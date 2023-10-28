package com.sustav;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class HealthCheckTest extends BaseFunctionalTest {

  @Test
  public void shouldCheckHealth() {
    given().port(managementPort)
           .when()
           .get("/actuator/health")
           .then()
           .log()
           .all()
           .statusCode(SC_OK)
           .body("status", is("UP"));
  }

}
