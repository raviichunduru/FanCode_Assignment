import fancode.FanCodeAPI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class FanCode_Assignment {

  @SneakyThrows
  @Test
  void assertThat_AllUsersFrom_FanCodeCity_Completed_FiftyPercentTasks() {

    FanCodeAPI.getInstance()
        .getAllUsers()
        .filterUsers_Of_FanCodeCity()
        .getTasksOfAllUsers()
        .filterTasksOf_UsersFrom_FanCodeCity()
        .assertCompletionPercentageForUsers();
  }
}
