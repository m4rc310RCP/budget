package br.com.m4rc310.budget.configs;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;

import br.com.m4rc310.gql.dto.MUser;
import br.com.m4rc310.gql.security.IMAuthUserProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MGraphQLSecurityConfig implements IMAuthUserProvider{
	
//	@Autowired
//	private PasswordEncoder encoder;

	@Override
	public MUser authUser(String username, Object password) throws Exception {
		
//		if ("test".equals(username)) {
			MUser user = new MUser();
			user.setCode(1L);
			user.setUsername(username);
			user.setPassword(password + "");
			user.setRoles("Admin".split(";"));
			
			log.info("Password: {}", password);
//			log.info("Validate: {}", encoder.matches("1234", password+""));
//			log.info("Validate: {}", encoder.matches("123", password+""));
			
			return user;
//		}
		
//		return null;
	}

	@Override
	public MUser getUserFromUsername(String username) {
		MUser user = new MUser();
		user.setUsername(username);
		user.setRoles("admin".split(";"));
		return user;
	}
	
	@Override
	public boolean isValidUser(MUser user) {
		if (Objects.isNull(user)) {
			return false;
		}
		return true;
	}

}
