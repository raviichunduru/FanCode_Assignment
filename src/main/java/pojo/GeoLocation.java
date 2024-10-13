package pojo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class GeoLocation {

  @JsonAlias("lat")
  private String latitude;

  @JsonAlias("lng")
  private String longitude;
}
