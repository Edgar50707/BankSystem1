package com.digi.banksystem.model;

import com.digi.banksystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    @NotBlank
    private String name;
    @Column(name = "last_name")
    @NotBlank
    private String surname;
    private int age;
    @Email
    private String email;
    @NotBlank
    private String password;
    @Column(name = "verifikation_code")
    private String verifyCode;

    @Enumerated(value = EnumType.ORDINAL)
    private Status status;


    @Column(name = "reset_token")
    private String resetToken;


}
