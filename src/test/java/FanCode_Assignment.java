import com.fasterxml.jackson.databind.ObjectMapper;
import fancode.FanCodeAPI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.UserTasks;
import pojo.User;
import java.util.List;
import java.util.Map;

public class FanCode_Assignment {

  @SneakyThrows
  @Test
  void assertThat_AllUsersFrom_FanCodeCity_Completed_FiftyPercentTasks() {

    FanCodeAPI.getInstance()
              .getAllUsers()
              .filterUsers_Of_FanCodeCity()
              .getTasksOfAllUsers()
              .filterTasksOf_UsersFrom_FanCodeCity()
              .assertThat_AllUsersFrom_FanCodeCity_Completed_FiftyPercentTasks();
  }
}
