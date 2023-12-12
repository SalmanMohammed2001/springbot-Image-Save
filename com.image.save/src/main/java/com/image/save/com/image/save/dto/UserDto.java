package com.image.save.com.image.save.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class UserDto {

    private String id;
    private String name;
    private String address;
  //  private byte[] imag;
    private ArrayList<byte[]> images;


}
