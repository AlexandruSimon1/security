package com.example.security.repositories;

import com.example.security.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
	List<User> findAll();
	User findUserById(Long id);
	User findByActivationCode(UUID activationCode);
}
