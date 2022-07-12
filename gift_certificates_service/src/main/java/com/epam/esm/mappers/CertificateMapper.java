package com.epam.esm.mappers;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.utils.Mapper;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Mapper between {@code CertificateDto} and {@code Certificate}
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
@ComponentScan("com.epam.esm")
public class CertificateMapper {
  private final ModelMapper modelMapper;

  @Autowired
  public CertificateMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Converts {@code Certificate} object to {@code CertificateDto} object
   *
   * @param certificate model object to convert
   * @return converted dto object
   */
  public CertificateDto convertToDto(Certificate certificate) {
    return modelMapper.map(certificate, CertificateDto.class);
  }

  /**
   * Converts {@code Certificate} list to {@code CertificateDto} list
   *
   * @param certificateList model objects to convert
   * @return converted dto objects
   */
  public List<CertificateDto> convertToDto(List<Certificate> certificateList) {
    return Mapper.convertList(certificateList, this::convertToDto);
  }

  /**
   * Converts {@code CertificateDto} object to {@code Certificate} object
   *
   * @param certificateDto dto object to convert
   * @return converted model object
   */
  public Certificate convertToEntity(CertificateDto certificateDto) {
    return modelMapper.map(certificateDto, Certificate.class);
  }

  /**
   * Converts {@code CertificateDto} list to {@code Certificate} list
   *
   * @param certificateDtos dto objects to convert
   * @return converted model objects
   */
  public List<Certificate> convertToEntity(List<CertificateDto> certificateDtos) {
    return Mapper.convertList(certificateDtos, this::convertToEntity);
  }
}
