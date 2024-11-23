package ru.evanemo.st_user.dto.request.groups;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AddStudentToGroupDto {
  private UUID groupId;
  private UUID studentId;
}
