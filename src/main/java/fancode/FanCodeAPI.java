package fancode;

import static assertions.UserTaskAssertions.assertCompletionPercentage;
import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import config.TestConfig;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import pojo.User;
import pojo.UserTask;

public class FanCodeAPI {

  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  private final ObjectMapper objectMapper = new ObjectMapper();
  private List<User> allUsers;
  private List<User> fanCodeUsers;
  private List<UserTask> allTasks;
  private Map<Long, List<UserTask>> fanCodeCityUserTasks;

  private static FanCodeAPI fanCodeAPI = null;

  private FanCodeAPI() {}

  public static synchronized FanCodeAPI getInstance() {
    if (Objects.isNull(fanCodeAPI)) {
      fanCodeAPI = new FanCodeAPI();
    }
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

  public FanCodeAPI filterUsersOfFanCodeCity() {
    fanCodeUsers =
        allUsers.stream()
            .filter(
                user -> {
                  double latitude = Double.parseDouble(user.getAddress().getGeo().getLatitude());
                  double longitude = Double.parseDouble(user.getAddress().getGeo().getLongitude());
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
    allTasks =
        objectMapper.readValue(tasksResponse.asString(), new TypeReference<List<UserTask>>() {});
    return fanCodeAPI;
  }

  @SneakyThrows
  public FanCodeAPI filterTasksOfUsersFromFanCodeCity() {
    fanCodeCityUserTasks =
        allTasks.stream()
            .filter(
                task -> fanCodeUsers.stream().anyMatch(user -> user.getId() == task.getUserId()))
            .collect(Collectors.groupingBy(UserTask::getUserId));
    return fanCodeAPI;
  }

  public void assertCompletionPercentageForUsers() {

    System.out.println("User ID | Total Tasks | Completed Tasks | Completion Percentage");
    System.out.println("---------------------------------------------------------------");

    fanCodeCityUserTasks.forEach(
        (userId, tasks) -> {
          long totalTaskCount = tasks.size();
          long completedTaskCount = tasks.stream().filter(UserTask::isCompleted).count();
          double completionPercentage = (completedTaskCount * 100.0) / totalTaskCount;

          assertCompletionPercentage(userId, totalTaskCount, completedTaskCount);

          System.out.printf(
              "%7d | %11d | %14d | %.2f%%%n",
              userId, totalTaskCount, completedTaskCount, completionPercentage);
        });
  }
}
