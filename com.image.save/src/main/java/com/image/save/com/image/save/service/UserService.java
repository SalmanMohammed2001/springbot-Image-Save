package com.image.save.com.image.save.service;

import com.image.save.com.image.save.dto.UserDto;
import com.image.save.com.image.save.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public void saveUser(UserDto dto) throws IOException;
  /*  public List<UserDto> findAll();

    public UserDto findImage(String id);*/
}
