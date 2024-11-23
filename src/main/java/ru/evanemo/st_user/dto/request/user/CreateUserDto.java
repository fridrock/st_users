package ru.evanemo.st_user.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto {
  @NotBlank
  private String email;
  @NotBlank
  private String name;
  @NotBlank
  private String surname;
  @NotBlank
  private String thirdName;
  @NotBlank
  private String roleName;
  @NotBlank
  private String password;
}
