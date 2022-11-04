package com.digi.banksystem.service;

import com.digi.banksystem.exeptions.BadRequestException;
import com.digi.banksystem.exeptions.NotFoundException;
import com.digi.banksystem.exeptions.ValidationException;
import com.digi.banksystem.model.User;
import com.digi.banksystem.model.requestdto.UserDTO;

import javax.mail.MessagingException;

public interface UserService {
    void create(UserDTO userDTO);

    User getByEmail(String email);

    void verifyUser(String email,String code) throws NotFoundException;

    void updateUser(Integer id,UserDTO userDTO) throws NotFoundException, BadRequestException;

    void changePassword(String email,String oldPassword,String newPassword,String confirmPassword) throws ValidationException, NotFoundException;

    //void forgotPassword(String email,String forgotPassword,String confirmPassword);

    void getEmail(String email) throws NotFoundException;

    Boolean getToken(String email,String token) throws ValidationException;

    void forgotPassword(String email,String newPassword,String confirmPassword) throws ValidationException;
}
