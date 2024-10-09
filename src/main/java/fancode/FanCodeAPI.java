package fancode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import config.TestConfig;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import pojo.User;
import pojo.UserTasks;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class FanCodeAPI {

  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  private final ObjectMapper objectMapper = new ObjectMapper();
  private List<User> allUsers;
  private List<User> fanCodeUsers;
  private List<UserTasks> allTasks;
  private Map<Long, List<UserTasks>> tasksOf_FanCodeCity_Users;

  private static FanCodeAPI fanCodeAPI = new FanCodeAPI();

  private FanCodeAPI () {
  }

  public static FanCodeAPI getInstance() {
    return fanCodeAPI;
  }

  private Response getUsers() {
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

  @SneakyThrows
  public FanCodeAPI getAllUsers() {
    Response usersResponse = getUsers();
    allUsers = objectMapper.readValue(usersResponse.asString(), new TypeReference<List<User>>() {});
    return fanCodeAPI;
  }

  public FanCodeAPI filterUsers_Of_FanCodeCity() {
    fanCodeUsers =  allUsers.stream()
      .filter(user -> {
        double latitude = Double.parseDouble(user.getAddress().getGeo().getLat());
        double longitude = Double.parseDouble(user.getAddress().getGeo().getLng());
        return (latitude >= -40 && latitude <= 5) && (longitude >= 5 && longitude <= 100);
      })
      .collect(Collectors.toList());
    return fanCodeAPI;
  }

  private Response getTasks() {
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

  @SneakyThrows
  public FanCodeAPI getTasksOfAllUsers() {
    Response tasksResponse = getTasks();
    allTasks = objectMapper.readValue(tasksResponse.asString(), new TypeReference<List<UserTasks>>() {});
    return fanCodeAPI;
  }

  @SneakyThrows
  public FanCodeAPI filterTasksOf_UsersFrom_FanCodeCity() {
    tasksOf_FanCodeCity_Users = allTasks.stream()
      .filter(task -> fanCodeUsers.stream().anyMatch(user -> user.getId() == task.getUserId()))
      .collect(Collectors.groupingBy(UserTasks::getUserId));
    return fanCodeAPI;
  }

  public void assertThat_AllUsersFrom_FanCodeCity_Completed_FiftyPercentTasks () {

    System.out.println("User ID | Total Tasks | Completed Tasks | Completion Percentage");
    System.out.println("---------------------------------------------------------------");

    tasksOf_FanCodeCity_Users.forEach((userId, tasks) -> {

      long totalTasks = tasks.size();
      long completedTasks = tasks.stream().filter(UserTasks::isCompleted).count();
      double completionPercentage = (completedTasks * 100.0) / totalTasks;

      assertThat(completionPercentage)
                .as("Completion percentage for User ID " + userId)
                .isGreaterThan(50.0);
      System.out.printf("%7d | %11d | %14d | %.2f%%%n", userId, totalTasks, completedTasks, completionPercentage);
    });
  }
}
