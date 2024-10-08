package helpermethods;

import pojo.User;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

  public static List<User> filterUsers_From_FanCodeCity(List<User> users) {
    return users.stream()
      .filter(user -> {
        double latitude = Double.parseDouble(user.getAddress().getGeo().getLat());
        double longitude = Double.parseDouble(user.getAddress().getGeo().getLng());
        return (latitude >= -40 && latitude <= 5) && (longitude >= 5 && longitude <= 100);
      })
      .collect(Collectors.toList());
  }
}
