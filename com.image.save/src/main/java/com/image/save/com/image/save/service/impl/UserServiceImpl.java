package com.image.save.com.image.save.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.image.save.com.image.save.dto.UserDto;
import com.image.save.com.image.save.entity.User;
import com.image.save.com.image.save.repo.UserRepo;
import com.image.save.com.image.save.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    private final Gson gson;

    private final ModelMapper modelMapper;

    public UserServiceImpl(Gson gson, ModelMapper modelMapper) {
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveUser( UserDto dto) throws IOException {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
      // user.setImage(Util.compressImage(file.getBytes()));
   //   user.setImage(Base64.getEncoder().encodeToString(dto.getImag()).getBytes());
        exPortImage(dto,user);
       userRepo.save(user);
    }





    @Override
    public UserDto findImage(String id) throws IOException {
        User byId = userRepo.findById(id).get();
        System.out.println(byId.getImages());
//        byte[] bytes = Base64.getDecoder().decode(byId.getImage());
       /* return new UserDto(
                byId.get().getId(),byId.get().getName(),byId.get().getAddress(),Base64.getDecoder().decode(byId.get().getImage()));*/
        UserDto userDto = modelMapper.map(byId, UserDto.class);
        importImages(userDto,byId);
        return userDto;
    }


       public List<UserDto> findAll() throws IOException {
           List<User> user = userRepo.findAll();
           ArrayList<UserDto> userDtos = new ArrayList<>();
      /*  for(User u:user){
            userDtos.add(new UserDto(
                  u.getId(),u.getName(),u.getAddress(),Base64.getDecoder().decode(u.getImage())));
               //    u.getId(),u.getName(),u.getAddress(),Util.decompressImage(u.getImage())));
        }*/
      List<UserDto> userDto = modelMapper.map(user,new TypeToken<List<UserDto>>(){}.getType());
           List<UserDto> userDtoList = importImagesAll(userDto, user);
           return userDtoList;
    }








    public void exPortImage(UserDto dto,User user){
        ArrayList<byte[]> images = dto.getImages();
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        ArrayList<String> pathList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            {
                try {
                    InputStream is = new ByteArrayInputStream(images.get(i));
                    BufferedImage bi = ImageIO.read(is);
                    File outputfile = new File("images/user/" + dt + "_" + i + ".jpg");
                    ImageIO.write(bi, "jpg", outputfile);
                    pathList.add(outputfile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        user.setImages(gson.toJson(pathList));


    }


    public void importImages(UserDto hotelDTO,User hotel) throws IOException {
        String images = hotel.getImages();
        hotelDTO.setImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            hotelDTO.getImages().add(imgData);
        }
    }

    public List<UserDto> importImagesAll(List<UserDto> userDtos, List<User> users) throws IOException {
        User user = new User();

        users.forEach(data -> {
            user.setImages(data.getImages());
        });
        String images = user.getImages();
        System.out.println(images);

        UserDto  userDto = new UserDto();
        userDto.setImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
           userDto.getImages().add(imgData);

        }
        List<UserDto> userDtoList=new ArrayList<>();
        for (UserDto dto:userDtos) {
            userDtoList.add(new UserDto(dto.getId(),dto.getName(),dto.getAddress(),userDto.getImages()));
        }

        return userDtoList;
    }

}

