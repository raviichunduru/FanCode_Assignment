package pojo;

import lombok.Data;

@Data
public class UserTasks {
  private long userId;
  private long id;
  private String title;
  private boolean completed;
}
