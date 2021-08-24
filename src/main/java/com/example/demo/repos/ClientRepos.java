package com.example.demo.repos;

import com.example.demo.entitys.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ClientRepos extends JpaRepository<Client, UUID> {
    Client findByClientUUID(String clientUUID);
    Client findByName(String name);
    Client findByPassport(String passport);
}
