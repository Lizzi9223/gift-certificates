package com.epam.esm.mappers;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.Role;
import com.epam.esm.utils.Mapper;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper between {@code RoleDto} and {@code Role}<br>
 * and vice verse
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class RoleMapper {
  private final ModelMapper modelMapper;

  @Autowired
  public RoleMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Converts {@code Role} object to {@code RoleDto} object
   *
   * @param role model object to convert
   * @return converted dto object
   */
  public RoleDto convertToDto(Role role) {
    return modelMapper.map(role, RoleDto.class);
  }

  /**
   * Converts {@code Role} list to {@code RoleDto} list
   *
   * @param roles model objects to convert
   * @return converted dto objects
   */
  public List<RoleDto> convertToDto(List<Role> roles) {
    return Mapper.convertList(roles, this::convertToDto);
  }

  /**
   * Converts {@code RoleDto} object to {@code Role} object
   *
   * @param roleDto dto object to convert
   * @return converted model object
   */
  public Role convertToEntity(RoleDto roleDto) {
    return modelMapper.map(roleDto, Role.class);
  }

  /**
   * Converts {@code RoleDto} list to {@code Role} list
   *
   * @param roleDtos dto objects to convert
   * @return converted model objects
   */
  public List<Role> convertToEntity(List<RoleDto> roleDtos) {
    return Mapper.convertList(roleDtos, this::convertToEntity);
  }
}
