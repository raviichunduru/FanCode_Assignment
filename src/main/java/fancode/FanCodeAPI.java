package fancode;

import com.typesafe.config.Config;
import config.TestConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class FanCodeAPI {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  public static Response getUsers() {
    return given()
              .baseUri(CONFIG.getString("BASE_URL"))
              .log()
              .ifValidationFails()
              .when()
              .get(CONFIG.getString("USERS_ENDPOINT"))
              .then()
              .log()
              .ifError()
              .extract()
              .response();
  }

  public static Response getTasks() {
    return given()
      .baseUri(CONFIG.getString("BASE_URL"))
      .log()
      .ifValidationFails()
      .when()
      .get(CONFIG.getString("TODO_ENDPOINT"))
      .then()
      .log()
      .ifError()
      .extract()
      .response();
  }
}
