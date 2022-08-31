package com.epam.esm.service;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.Role;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.handling.RoleExceptions;
import com.epam.esm.mappers.RoleMapper;
import com.epam.esm.repos.RoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for role operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class RoleService {
  private final RoleExceptions exceptionHandling;
  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Autowired
  public RoleService(
      RoleExceptions exceptionHandling, RoleRepository roleRepository, RoleMapper roleMapper) {
    this.exceptionHandling = exceptionHandling;
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }

  /**
   * Searches for role by name
   *
   * @param name name of the role to find
   * @return founded userDto
   * @throws ServiceException when role not found
   */
  public RoleDto find(String name) {
    Optional<Role> role = roleRepository.findByName(name);
    if (role.isPresent()) return roleMapper.convertToDto(role.get());
    else throw exceptionHandling.getExceptionForRoleNameNotExist(name);
  }
}
