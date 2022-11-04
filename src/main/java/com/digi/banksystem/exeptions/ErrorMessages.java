package com.digi.banksystem.exeptions;

public interface ErrorMessages {
    String NOT_FOUND_USER = "user not found with given email";
    String NOT_MACH = "passwords don't match";
    String NOT_FOUND_PASSWORD = "user not found with given password";

    String TOKENS_NOT_MATCH = "tokens dont match";
}
