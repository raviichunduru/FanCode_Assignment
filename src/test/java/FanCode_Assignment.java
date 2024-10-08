import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fancode.FanCodeAPI;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.UserTasks;
import pojo.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static helpermethods.Helper.filterUsers_From_FanCodeCity;

public class FanCode_Assignment {

  ObjectMapper objectMapper = new ObjectMapper();
  List<User> allUsers;
  List<User> fanCodeUsers;
  List<UserTasks> allTasks;
  Map<Long, List<UserTasks>> tasksOf_FanCodeCity_Users;

  @SneakyThrows
  @Test
  void assertThat_AllUsersFromFanCodeCity_Completed_FiftyPercentTasks() {

    // get users from users API and Filter who are from FanCode City
    Response usersResponse = FanCodeAPI.getUsers();
    allUsers = objectMapper.readValue(usersResponse.asString(), new TypeReference<List<User>>() {});
    fanCodeUsers = filterUsers_From_FanCodeCity(allUsers);

    // get tasks from todo API
    Response tasksResponse = FanCodeAPI.getTasks();
    allTasks = objectMapper.readValue(tasksResponse.asString(), new TypeReference<List<UserTasks>>() {});

    // Filter tasks of FanCode City users
    tasksOf_FanCodeCity_Users = allTasks.stream()
                                 .filter(task -> fanCodeUsers.stream().anyMatch(user -> user.getId() == task.getUserId()))
                                 .collect(Collectors.groupingBy(UserTasks::getUserId));


    // Assert that for all FanCode users task completion percentage is more than 50, also printing for reference
    System.out.println("User ID | Total Tasks | Completed Tasks | Completion Percentage");
    System.out.println("---------------------------------------------------------------");

    tasksOf_FanCodeCity_Users.forEach((userId, tasks) -> {
      
                  long totalTasks = tasks.size();
                  long completedTasks = tasks.stream().filter(UserTasks::isCompleted).count();
                  double completionPercentage = (completedTasks * 100.0) / totalTasks;

                  Assertions.assertTrue(completionPercentage > 50.0,"Completion percentage for User ID " + userId + " not above 50%");
                  if (completionPercentage > 50.0) {
                    System.out.printf("%7d | %11d | %14d | %.2f%%%n", userId, totalTasks, completedTasks, completionPercentage);
                  }
    });
  }
}
