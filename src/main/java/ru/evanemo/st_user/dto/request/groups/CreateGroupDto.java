package ru.evanemo.st_user.dto.request.groups;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CreateGroupDto {
  @NotBlank
  private String name;
  private UUID teacherId;
}
