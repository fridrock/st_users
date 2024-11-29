package ru.evanemo.st_user.dto.request.groups;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentToGroupDto {
  private UUID groupId;
  private UUID studentId;
}
