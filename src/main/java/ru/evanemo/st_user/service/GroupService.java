package ru.evanemo.st_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.evanemo.st_user.dto.request.groups.AddStudentToGroupDto;
import ru.evanemo.st_user.dto.request.groups.CreateGroupDto;
import ru.evanemo.st_user.dto.response.groups.GetGroupDto;
import ru.evanemo.st_user.dto.response.groups.GroupCreatedDto;
import ru.evanemo.st_user.dto.response.user.GetUserDto;
import ru.evanemo.st_user.exception.NotFoundException;
import ru.evanemo.st_user.model.Group;
import ru.evanemo.st_user.repository.GroupRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final UserService userService;
  private final GroupRepository groupRepository;

  public GroupCreatedDto createGroup(CreateGroupDto dto){
    var group = Group.builder()
        .name(dto.getName())
        .teacher(userService.findById(dto.getTeacherId()))
        .build();
    group = groupRepository.save(group);
    return GroupCreatedDto.builder()
        .groupId(group.getId())
        .name(group.getName())
        .teacherId(group.getTeacher().getId()).build();
  }

  public void addStudent(AddStudentToGroupDto dto){
    var student = userService.findById(dto.getStudentId());
    var group = groupRepository.findById(dto.getGroupId()).orElseThrow(
        ()->new NotFoundException(String.format(NotFoundException.GROUP_BY_ID, dto.getGroupId()))
    );
    group.getStudents().add(student);
    student.setGroup(group);
    userService.saveUser(student);
    groupRepository.save(group);
  }
  public void deleteStudent(AddStudentToGroupDto dto){
    var student = userService.findById(dto.getStudentId());
    var group = groupRepository.findById(dto.getGroupId()).orElseThrow(
        ()->new NotFoundException(String.format(NotFoundException.GROUP_BY_ID, dto.getGroupId()))
    );
    group.getStudents().remove(student);
    student.setGroup(null);
    userService.saveUser(student);
    groupRepository.save(group);
  }

  public List<GetGroupDto> getGroupsByTeacherId(UUID teacherId){
    return groupRepository.findByTeacherId(teacherId).stream().map(GetGroupDto::fromGroup).collect(Collectors.toList());
  }
  public GetGroupDto getById(UUID groupId){
    var group = groupRepository.findById(groupId).orElseThrow(
        ()->new NotFoundException(String.format(NotFoundException.GROUP_BY_ID, groupId))
    );
    return GetGroupDto.fromGroup(group);
  }
}
