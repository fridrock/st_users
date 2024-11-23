package ru.evanemo.st_user.dto.response.groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class GroupCreatedDto {
  private UUID groupId;
  private String name;
  private UUID teacherId;
}
