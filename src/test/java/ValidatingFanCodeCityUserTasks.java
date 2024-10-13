import fancode.FanCodeAPI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ValidatingFanCodeCityUserTasks {

  @SneakyThrows
  @Test
  void assertThatAllUsersFromFanCodeCityCompletedFiftyPercentTasks() {

    FanCodeAPI.getInstance()
        .getAllUsers()
        .filterUsersOfFanCodeCity()
        .getTasksOfAllUsers()
        .filterTasksOfUsersFromFanCodeCity()
        .assertCompletionPercentageForUsers();
  }
}
