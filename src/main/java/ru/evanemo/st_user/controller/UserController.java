package ru.evanemo.st_user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.evanemo.st_user.dto.request.user.AuthUserDto;
import ru.evanemo.st_user.dto.request.user.CreateUserDto;
import ru.evanemo.st_user.dto.response.token.TokenDto;
import ru.evanemo.st_user.dto.response.user.GetUserDto;
import ru.evanemo.st_user.service.UserService;
import ru.evanemo.st_user.utils.JwtTokenUtils;
import ru.evanemo.st_user.utils.SecurityContextHolderUtils;
import ru.evanemo.st_user.utils.UserDetailsAdapter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserController {
  private final UserService userService;
  private final JwtTokenUtils tokenUtils;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @PostMapping("/reg")
  public ResponseEntity<TokenDto> createUser(@Valid @RequestBody CreateUserDto dto) {
    log.info("Creating user");
    var user = userService.createUser(dto);
    String token = tokenUtils.generateToken(new UserDetailsAdapter(user));
    return ResponseEntity.ok(new TokenDto(token, user.getRole().getName()));
  }

  @PostMapping("/auth")
  public ResponseEntity<TokenDto> authUser(@RequestBody @Valid AuthUserDto dto) {
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    //TODO handle exception in controller advice (BadCredentialsException)
    authenticationManager.authenticate(authReq);
    UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
    String roleName = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
    return ResponseEntity.ok(new TokenDto(tokenUtils.generateToken(userDetails), roleName));
  }
  @PreAuthorize("hasAuthority('TEACHER')")
  @GetMapping("/byEmail")
  public ResponseEntity<List<GetUserDto>> getByEmail(@RequestParam("email") String email){
    return ResponseEntity.ok(userService.findByEmailPart(email));
  }
  @PreAuthorize("hasAuthority('TEACHER')")
  @GetMapping("/byGroup")
  public ResponseEntity<List<GetUserDto>> getByGroup(@RequestParam("groupId") UUID groupId){
    return ResponseEntity.ok(userService.getStudentsByGroup(groupId));
  }
  @PreAuthorize("hasAuthority('STUDENT')")
  @GetMapping("/userInfo")
  public ResponseEntity<GetUserDto> getUserInfo(){
    return ResponseEntity.ok(GetUserDto.fromUser(userService.findById(SecurityContextHolderUtils.getUserId())));
  }
}
