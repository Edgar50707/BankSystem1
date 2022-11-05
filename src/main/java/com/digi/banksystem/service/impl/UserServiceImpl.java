package com.digi.banksystem.service.impl;
import com.digi.banksystem.exeptions.BadRequestException;
import com.digi.banksystem.exeptions.ErrorMessages;
import com.digi.banksystem.exeptions.NotFoundException;
import com.digi.banksystem.exeptions.ValidationException;
import com.digi.banksystem.model.Address;
import com.digi.banksystem.model.User;
import com.digi.banksystem.model.enums.Status;
import com.digi.banksystem.model.requestdto.AddressDTO;
import com.digi.banksystem.model.requestdto.UserDTO;
import com.digi.banksystem.repasitory.AddressRepository;
import com.digi.banksystem.repasitory.UserRepository;
import com.digi.banksystem.service.UserService;
import com.digi.banksystem.util.EmailUtil;
import com.digi.banksystem.util.GenerateToken;
import com.digi.banksystem.util.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtil emailUtil;
    @Override
    public void create(UserDTO userDTO){
        User user = new User();
        user.setId(0);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        String verifyCode = GenerateToken.generateVerifyCode();
        user.setVerifyCode(verifyCode);
        user.setStatus(Status.INACTIVE);

        emailUtil.sendEmail(userDTO.getEmail(),"your verify code",verifyCode);

        userRepository.save(user);



    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("not found");
    }

    @Override
    public void verifyUser(String email, String code) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if(user == null){
            throw new NotFoundException(ErrorMessages.NOT_FOUND_USER);

        }
        if(user.getVerifyCode().equals(code)){
            user.setStatus(Status.ACTIVE);
            user.setVerifyCode(null);
            userRepository.save(user);
        }
    }



    @Override
    public void updateUser(Integer id,UserDTO userDTO) throws BadRequestException, NotFoundException {
        if(id == null){
            throw new BadRequestException("User id must not be null");
        }
        Optional<User> user = null;
        try {
            user = userRepository.findById(id);

        }catch (Exception e){
            throw new RuntimeException();
        }
        if(user.isEmpty()){
            throw new NotFoundException("User not found with given ID");
        }
        User updateUser = user.get();
        updateUser.setName(userDTO.getName() == null ? updateUser.getName() : userDTO.getName());
        updateUser.setSurname(userDTO.getSurname() == null ? updateUser.getSurname(): userDTO.getSurname());
        updateUser.setAge(userDTO.getAge() == 0 ? updateUser.getAge() : userDTO.getAge());
        userRepository.save(updateUser);
        //Optional<User> byId = userRepository.findById(id);
        //byId.get();

        //Optional<User> byId = userRepository.findById(id);
        //byId.orElseThrow();

//        userRepository.findById(id).orElseThrow(()-> new UserException("User with id " + id + " not found"));
//
//        User user = new User();
//        user.setId(id);
//        user.setName(userDTO.getName());
//        user.setSurname(userDTO.getSurname());
//        user.setAge(userDTO.getAge());
//
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//
//        String verifyCode = GenerateToken.generateVerifyCode();
//        user.setVerifyCode(verifyCode);
//        user.setStatus(Status.INACTIVE);
//
//        emailUtil.sendEmail(userDTO.getEmail(),"your verify code",verifyCode);
//        userRepository.save(user);



    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String confirmPassword) throws ValidationException, NotFoundException {
        if(!newPassword.equals(confirmPassword)){
            throw new ValidationException(ErrorMessages.NOT_MACH);
        }
        User user = userRepository.getByEmail(email);
        if(!user.getPassword().equals(passwordEncoder.encode(oldPassword))){
            throw new NotFoundException(ErrorMessages.NOT_FOUND_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

//    @Override
//    public void forgotPassword(String email, String forgotPassword,String confirmPassword) {
//        User user = userRepository.getByEmail(email);
//        if(Objects.equals(forgotPassword, "") || Objects.equals(confirmPassword, "")){
//            throw new UserException("you did not fill in all the fields");
//        }
//        if (!forgotPassword.equals(confirmPassword)){
//            throw new UserException("password does not match");
//        }
//        user.setPassword(passwordEncoder.encode(forgotPassword));
//        userRepository.save(user);
//
//    }

    @Override
    public void getEmail(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if(user == null){
            throw new NotFoundException(ErrorMessages.NOT_FOUND_USER);
        }
        String token = GenerateToken.generateToken();
        user.setResetToken(token);
        userRepository.save(user);
        //emailUtil.sendEmail(email,"Your generated token",token);

    }

    @Override
    public Boolean getToken(String email, String token) throws ValidationException {
        User user = userRepository.getByEmail(email);
        if(!user.getResetToken().equals(token)){
            throw new ValidationException(ErrorMessages.TOKENS_NOT_MATCH);

        }
        return true;

    }

    @Override
    public void forgotPassword(String email, String newPassword, String confirmPassword) throws ValidationException {
        if(!newPassword.equals(confirmPassword)){
            throw new ValidationException(ErrorMessages.NOT_MACH);
        }
        User user = userRepository.getByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);


    }

    @Override
    public void createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddress_id(0);
        address.setCountry(addressDTO.getCountry());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setHome(addressDTO.getHome());


        addressRepository.save(address);


    }
}

