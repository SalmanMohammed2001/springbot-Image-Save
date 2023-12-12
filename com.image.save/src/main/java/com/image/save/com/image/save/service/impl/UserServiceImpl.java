package com.image.save.com.image.save.service.impl;

import com.image.save.com.image.save.dto.UserDto;
import com.image.save.com.image.save.entity.User;
import com.image.save.com.image.save.repo.UserRepo;
import com.image.save.com.image.save.service.UserService;
import com.image.save.com.image.save.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Override
    public void saveUser( UserDto dto) throws IOException {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
      // user.setImage(Util.compressImage(file.getBytes()));
      user.setImage(Base64.getEncoder().encodeToString(dto.getImag()).getBytes());
       userRepo.save(user);
    }

    @Override
    public UserDto findImage(String id) {
        var byId = userRepo.findById(id);
//        byte[] bytes = Base64.getDecoder().decode(byId.getImage());
        return new UserDto(
                byId.get().getId(),byId.get().getName(),byId.get().getAddress(),Base64.getDecoder().decode(byId.get().getImage()));
    }


       public List<UserDto> findAll() {
        List<User> all = userRepo.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for(User u:all){
            userDtos.add(new UserDto(
                  u.getId(),u.getName(),u.getAddress(),Base64.getDecoder().decode(u.getImage())));
               //    u.getId(),u.getName(),u.getAddress(),Util.decompressImage(u.getImage())));
        }
        return userDtos;
    }
}
