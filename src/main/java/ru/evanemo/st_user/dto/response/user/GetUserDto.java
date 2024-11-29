package ru.evanemo.st_user.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.evanemo.st_user.model.User;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto {
  private UUID id;
  private String email;
  private String name;
  private String surname;
  private String thirdName;
  private UUID groupId;

  public static GetUserDto fromUser(User user) {
    return new GetUserDto(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getSurname(),
        user.getThirdName(),
        user.getGroup() == null? null: user.getGroup().getId());
  }
}
