package com.digi.banksystem.repasitory;


import com.digi.banksystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getByEmail(String email);

}
