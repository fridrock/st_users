package ru.evanemo.st_user.dto.response.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.evanemo.st_user.model.Group;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupDto {
  private UUID id;
  private String name;
  private UUID teacherId;
  public static GetGroupDto fromGroup(Group group){
    return new GetGroupDto(group.getId(), group.getName(), group.getTeacher().getId());
  }
}
