package ru.evanemo.st_user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evanemo.st_user.dto.request.groups.AddStudentToGroupDto;
import ru.evanemo.st_user.dto.request.groups.CreateGroupDto;
import ru.evanemo.st_user.dto.response.groups.GroupCreatedDto;
import ru.evanemo.st_user.service.GroupService;
import ru.evanemo.st_user.utils.SecurityContextHolderUtils;


@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
  private final GroupService groupService;

  @PostMapping
  public GroupCreatedDto createGroup(@Valid @RequestBody CreateGroupDto dto){
    dto.setTeacherId(SecurityContextHolderUtils.getUserId());
    return groupService.createGroup(dto);
  }
  @PostMapping("/add-student")
  public String addStudent(@RequestBody AddStudentToGroupDto dto){
    groupService.addStudent(dto);
    return "ok";
  }
}
