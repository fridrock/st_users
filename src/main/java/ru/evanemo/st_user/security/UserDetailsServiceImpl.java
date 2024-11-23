package ru.evanemo.st_user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.evanemo.st_user.service.UserService;
import ru.evanemo.st_user.utils.UserDetailsAdapter;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return new UserDetailsAdapter(userService.findByEmail(username));
  }
}
