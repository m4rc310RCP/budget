package br.com.m4rc310.budget.configs;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;

import br.com.m4rc310.gql.dto.MAuthToken;
import br.com.m4rc310.gql.dto.MUser;
import br.com.m4rc310.gql.security.IMAuthUserProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MGraphQLSecurityConfig implements IMAuthUserProvider{

	@Override
	public MUser authUser(String username, Object password) throws Exception {
		
		if ("test".equals(username)) {
			MUser user = new MUser();
			user.setCode(1L);
			user.setUsername(username);
			user.setPassword(password + "");
			user.setRoles("Admin".split(";"));
			return user;
		}
		
		return null;
	}

	@Override
	public MUser getUserFromUsername(String username) {
		return null;
	}
 
//	@Override
//	public MUser loadUser(MGraphQLJwtService jwt, MEnumToken type, String token) throws Exception {
//		
//		log.info("{}", type );
//		MUser u = null ;
//		
//		switch (type) {
//		case TEST:
//			int i = token.indexOf(":");
//			String username = token.substring(0, i);
//			String password = token.substring(i+1);
//			
//			log.info("{} -> {}", username, password);
//			
//			if ("test".equalsIgnoreCase(username)) {
//				u = new MUser();
//				u.setUsername(username);
//				u.setPassword(password);
//				u.setRoles("Admin".split(";"));
//				return u;
//			}else {
//				throw new Exception("Invalid credentials");
//			}
//		case BASIC:
//		case BEARER:
//		default:
//			throw new Exception("Invalid credentials");
//		}
//		
//		
////		MUser u = new MUser();
////		u.setUsername("test");
////		u.setPassword("test");
////		u.setRoles("Admin".split(";"));
////		
////		return u;
//	}

	@Override
	public boolean isValidUser(MUser user) {
		if (Objects.isNull(user)) {
			return false;
		}
		return true;
	}

	@Override
	public void validUserAccess(MAuthToken authToken, String[] roles) throws Exception {
		
	}

}
