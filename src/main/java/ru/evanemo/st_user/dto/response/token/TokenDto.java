package ru.evanemo.st_user.dto.response.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenDto{
  private String accessToken;
  private String roleName;
}
