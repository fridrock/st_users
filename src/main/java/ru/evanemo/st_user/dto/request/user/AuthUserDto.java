package ru.evanemo.st_user.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthUserDto {
  @NotBlank
  private String email;
  @NotBlank
  private String password;
}
