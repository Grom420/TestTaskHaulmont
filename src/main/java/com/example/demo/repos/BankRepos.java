package com.example.demo.repos;

import com.example.demo.entitys.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BankRepos extends JpaRepository<Bank, UUID> {
}