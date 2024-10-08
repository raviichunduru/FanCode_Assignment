package fancode;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class FanCodeAPI {

  public static Response getUsers() {
    return given()
              .baseUri("http://jsonplaceholder.typicode.com/")
              .log()
              .ifValidationFails()
              .when()
              .get("/users")
              .then()
              .log()
              .ifError()
              .extract()
              .response();
  }

  public static Response getTasks() {
    return given()
      .baseUri("http://jsonplaceholder.typicode.com/")
      .log()
      .ifValidationFails()
      .when()
      .get("/todos")
      .then()
      .log()
      .ifError()
      .extract()
      .response();
  }
}
