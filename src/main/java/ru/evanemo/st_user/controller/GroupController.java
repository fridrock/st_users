package ru.evanemo.st_user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.evanemo.st_user.dto.request.groups.AddStudentToGroupDto;
import ru.evanemo.st_user.dto.request.groups.CreateGroupDto;
import ru.evanemo.st_user.dto.response.groups.GetGroupDto;
import ru.evanemo.st_user.dto.response.groups.GroupCreatedDto;
import ru.evanemo.st_user.service.GroupService;
import ru.evanemo.st_user.utils.SecurityContextHolderUtils;

import java.util.List;


@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
  private final GroupService groupService;

  @PostMapping
  public ResponseEntity<GroupCreatedDto> createGroup(@Valid @RequestBody CreateGroupDto dto) {
    dto.setTeacherId(SecurityContextHolderUtils.getUserId());
    return ResponseEntity.ok(groupService.createGroup(dto));
  }

  @PostMapping("/add-student")
  public ResponseEntity<String> addStudent(@RequestBody AddStudentToGroupDto dto) {
    groupService.addStudent(dto);
    return ResponseEntity.ok("ok");
  }
  @PreAuthorize("hasAuthority('TEACHER')")
  @GetMapping("/byTeacher")
  public ResponseEntity<List<GetGroupDto>> getGroupsByTeacher(){
    return ResponseEntity.ok(groupService.getGroupsByTeacherId(SecurityContextHolderUtils.getUserId()));
  }

}
