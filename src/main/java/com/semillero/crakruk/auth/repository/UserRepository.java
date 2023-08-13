package com.semillero.crakruk.auth.repository;


import com.semillero.crakruk.auth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {

    UserModel findByEmailEquals(String email);

    UserModel findByUserEquals(String user);



}
