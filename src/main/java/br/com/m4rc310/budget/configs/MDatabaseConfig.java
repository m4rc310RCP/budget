package br.com.m4rc310.budget.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.budget.repositories.AuthUserRepository;
import br.com.m4rc310.budget.services.MServiceCache;
import jakarta.annotation.PostConstruct;

@Configuration
public class MDatabaseConfig {
	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected AuthUserRepository authUserRepository;

	@PostConstruct
	public void init() throws Exception {

		
		if (authUserRepository.findByUsername("client").isEmpty()) {
			AuthUser u = new AuthUser();
			u.setUsername("client");
			u.setPassword(encoder.encode("client"));
			u.setRoles("basic");
			authUserRepository.save(u);
		}

//		if (authUserRepository.findById(1L).isEmpty()) {
//			AuthUser au = new AuthUser();
//			au.setCode(1L);
//			au.setUsername("admin");
//			au.setPassword("admin");
//			au.setRoles("register");
//			authUserRepository.saveAndFlush(au);
//		}
	}
}
