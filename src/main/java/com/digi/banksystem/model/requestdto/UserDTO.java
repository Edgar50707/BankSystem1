package com.digi.banksystem.model.requestdto;

import lombok.Data;

@Data
public class UserDTO {
   private String name;
   private String surname;
   private int age;
   private String email;
   private String password;

   public UserDTO(){

   }


}
