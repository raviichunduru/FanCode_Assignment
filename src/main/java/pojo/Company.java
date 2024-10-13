package pojo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Company {
  private String name;
  private String catchPhrase;

  @JsonAlias("bs")
  private String businessSegment;
}
