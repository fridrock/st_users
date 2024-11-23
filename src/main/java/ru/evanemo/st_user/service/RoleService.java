package ru.evanemo.st_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.evanemo.st_user.exception.NotFoundException;
import ru.evanemo.st_user.model.Role;
import ru.evanemo.st_user.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;
  private static final String ROLE_NOT_FOUND = "Role with name: %s was not found";
  public Role findByName(String name) throws NotFoundException {
    return roleRepository.findByName(name).orElseThrow(
        ()->new NotFoundException(String.format(ROLE_NOT_FOUND, name)));
  }
}
