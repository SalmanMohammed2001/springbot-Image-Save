package com.image.save.com.image.save.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.image.save.com.image.save.dto.UserDto;
import com.image.save.com.image.save.entity.User;
import com.image.save.com.image.save.repo.UserRepo;
import com.image.save.com.image.save.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    private final Gson gson;

    public UserServiceImpl(Gson gson) {
        this.gson = gson;
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





 /*   @Override
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
    }*/








    public void exPortImage(UserDto dto,User user){
        ArrayList<byte[]> image = dto.getImage();
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        ArrayList<String> pathList = new ArrayList<>();


        for (int i = 0; i <image.size() ; i++) {
            {
                try {
                    InputStream is = new ByteArrayInputStream(image.get(i));
                    BufferedImage bi = ImageIO.read(is);
                    File outputfile = new File("images/hotel/" + dt + "_" + i + ".jpg");
                    pathList.add(outputfile.getAbsolutePath());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        user.setImage(gson.toJson(pathList));
    }


    public void  importImages(UserDto dto,User user) throws IOException {
        String image = user.getImage();
        dto.setImage(new ArrayList<>());
        if(image!=null){
            ArrayList<String> imageList= gson.fromJson(image,new TypeToken<ArrayList<String>>(){}.getType());
            for (int i = 0; i <imageList.size() ; i++) {

                BufferedImage r = ImageIO.read(new File(imageList.get(i)));
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(r,"jpg",b);
                byte[] imageData = b.toByteArray();
                dto.getImage().add(imageData);

            }
        }
    }

}

