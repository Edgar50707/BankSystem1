package com.digi.banksystem.conroller;

import com.digi.banksystem.exeptions.BadRequestException;
import com.digi.banksystem.exeptions.ErrorMessages;
import com.digi.banksystem.exeptions.NotFoundException;
import com.digi.banksystem.exeptions.ValidationException;
import com.digi.banksystem.model.User;
import com.digi.banksystem.model.requestdto.AddressDTO;
import com.digi.banksystem.model.requestdto.UserDTO;
import com.digi.banksystem.repasitory.UserRepository;
import com.digi.banksystem.service.UserService;
import com.digi.banksystem.util.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {
    User user = new User();
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        service.create(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @PatchMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String verifyCode) throws NotFoundException {
        service.verifyUser(email, verifyCode);
        return ResponseEntity.ok().build();
    }

//    @PutMapping
//    public void updateUser(@RequestParam int id, @RequestBody UserDTO userDTO) throws NotFoundException {
//        service.updateUser(id, userDTO);
//
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestBody UserDTO userDTO) throws NotFoundException, BadRequestException {
        service.updateUser(id, userDTO);
        return ResponseEntity.ok().build();

    }
    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword,
                                            @RequestParam String newPassword,
                                            @RequestParam String confirmPassword,
                                            Principal principal) throws ValidationException, NotFoundException {
        String email = principal.getName();
        service.changePassword(email, oldPassword, newPassword, confirmPassword);
        return ResponseEntity.ok().build();
    }
//    @PutMapping("/forgot_password")
//    public void forgotPassword(@RequestParam String email,@RequestParam String forgotPassword,@RequestParam String confirmPassword){
//
//        service.forgotPassword(email,forgotPassword,confirmPassword);
//    }
    @GetMapping("/get-email")
    public ResponseEntity<?>getEmail(@RequestParam String email) throws NotFoundException {
        service.getEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-token")
    public ResponseEntity<?> getToken(@RequestParam String email, @RequestParam String token) throws ValidationException {
        return ResponseEntity.ok(service.getToken(email,token));
    }
    @PatchMapping("/forgot-password")
    public void forgotPassword(@RequestParam String email,
                                            @RequestParam String newPassword,
                                            @RequestParam String confirmPassword) throws ValidationException {
        service.forgotPassword(email,newPassword,confirmPassword);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-address")
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {
        service.createAddress(addressDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

}
