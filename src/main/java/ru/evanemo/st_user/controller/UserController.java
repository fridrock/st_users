package ru.evanemo.st_user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evanemo.st_user.dto.request.user.AuthUserDto;
import ru.evanemo.st_user.dto.request.user.CreateUserDto;
import ru.evanemo.st_user.dto.response.token.TokenDto;
import ru.evanemo.st_user.service.UserService;
import ru.evanemo.st_user.utils.JwtTokenUtils;
import ru.evanemo.st_user.utils.UserDetailsAdapter;

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
  public TokenDto createUser(@Valid @RequestBody CreateUserDto dto) {
    log.info("Creating user");
    var user = userService.createUser(dto);
    String token = tokenUtils.generateToken(new UserDetailsAdapter(user));
    return new TokenDto(token);
  }

  @PostMapping("/auth")
  public TokenDto authUser(@RequestBody @Valid AuthUserDto dto) {
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    //TODO handle exception in controller advice (BadCredentialsException)
    authenticationManager.authenticate(authReq);
    UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
    return new TokenDto(tokenUtils.generateToken(userDetails));
  }
  @GetMapping("/fioPart")
  public String mock(){
    return null;
  }
}
