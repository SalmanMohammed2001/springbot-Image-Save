package com.image.save.com.image.save.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.save.com.image.save.dto.UserDto;
import com.image.save.com.image.save.service.UserService;
import com.image.save.com.image.save.util.StandResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<StandResponse> saveUser(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String address,
         //   @RequestParam MultipartFile file
           @RequestPart byte[] fontimage
    ) throws IOException {
//        userService.saveUser(dto,file);


     /*   UserDto data= objectMapper.convertValue(,UserDto.class);*/
        System.out.println(id);
        System.out.println(name);
        System.out.println(address);
      // System.out.println(file.getOriginalFilename());
       //System.out.println(file.getOriginalFilename());
   //     System.out.println(fontimage);

//   userService.saveUser(new UserDto(id,name,address,file.getBytes()));
    userService.saveUser(new UserDto(id,name,address,fontimage));
        return new ResponseEntity<>(new StandResponse(
                2000,"Saved",null
        ), HttpStatus.CREATED);
    }

@GetMapping(path = "{id}")
        public ResponseEntity<StandResponse> findImg(@PathVariable String id){
    UserDto image = userService.findImage(id);
    return new ResponseEntity<>(new StandResponse(
            200,"ok",image
    ),HttpStatus.CREATED);
}

@GetMapping()
    public ResponseEntity<StandResponse> getAll() {
        List<UserDto> all = userService.findAll();

        return new ResponseEntity<>(
                new StandResponse(200, "ok", all), HttpStatus.CREATED
        );
    }

}
