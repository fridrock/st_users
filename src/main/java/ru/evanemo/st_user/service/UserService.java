package ru.evanemo.st_user.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.evanemo.st_user.dto.request.user.CreateUserDto;
import ru.evanemo.st_user.dto.response.user.GetUserDto;
import ru.evanemo.st_user.exception.AlreadyExistsException;
import ru.evanemo.st_user.exception.NotFoundException;
import ru.evanemo.st_user.exception.UserNotFoundException;
import ru.evanemo.st_user.model.User;
import ru.evanemo.st_user.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public User saveUser(User user){
    return userRepository.save(user);
  }
  public User createUser(CreateUserDto dto) {
    checkUserExistsByEmail(dto.getEmail());
    var user = User.builder()
        .name(dto.getName())
        .surname(dto.getSurname())
        .thirdName(dto.getThirdName())
        .email(dto.getEmail())
        .role(roleService.findByName(dto.getRoleName()))
        .passwordHash(passwordEncoder.encode(dto.getPassword()))
        .build();
    return saveUser(user);
  }
  public User findByEmail(String email){
    return userRepository.findByEmail(email).orElseThrow(
        ()->new NotFoundException(String.format(NotFoundException.USR_BY_EMAIL, email))
    );
  }
  public User findById(UUID id){
    return userRepository.findById(id).orElseThrow(
        ()->new NotFoundException(String.format(NotFoundException.USR_BY_ID, id))
    );
  }

  public List<GetUserDto> findByEmailPart(String emailPart){
    return userRepository.findAll().stream()
        .filter(u -> u.getRole().getName().equals("STUDENT") && u.getEmail().contains(emailPart))
        .map(GetUserDto::fromUser).collect(Collectors.toList());
  }

  public List<GetUserDto> getStudentsByGroup(UUID groupId){
    return userRepository.findByGroupId(groupId).stream().map(GetUserDto::fromUser).collect(Collectors.toList());
  }

  private void checkUserExistsByEmail(String email) throws AlreadyExistsException{
    var user = userRepository.findByEmail(email);
    if(user.isPresent()){
      throw new AlreadyExistsException("user with such email already exists: "+ email);
    }
  }
}
