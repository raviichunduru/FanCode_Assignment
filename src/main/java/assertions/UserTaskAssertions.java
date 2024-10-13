package assertions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTaskAssertions {

  public static void assertCompletionPercentage(
      long userId, long totalTaskCount, long completedTaskCount) {

    double completionPercentage = (completedTaskCount * 100.0) / totalTaskCount;
    assertThat(completionPercentage)
        .as("Completion percentage for User ID " + userId)
        .isGreaterThan(50.0);
  }
}
