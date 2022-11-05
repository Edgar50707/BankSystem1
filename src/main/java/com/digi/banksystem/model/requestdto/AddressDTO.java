package com.digi.banksystem.model.requestdto;

import lombok.Data;

@Data
public class AddressDTO {
    private String country;
    private String city;
    private String street;
    private String home;

    public AddressDTO(){

    }
}
