package com.example.demo.repos;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CreditRepos extends JpaRepository<Credit, UUID> {
    Credit findByCreditUUID(String creditUUID);
    Credit findByCreditName(String creditName);
}

