package br.com.m4rc310.budget.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.m4rc310.budget.data.dto.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

	Optional<AuthUser> findByUsername(String username);
}
