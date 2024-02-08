package br.com.m4rc310.budget.configs;

import org.jfree.util.Log;
import org.springframework.context.annotation.Configuration;

import br.com.m4rc310.gql.dto.MAuthToken;
import br.com.m4rc310.gql.dto.MEnumToken;
import br.com.m4rc310.gql.dto.MUser;
import br.com.m4rc310.gql.jwt.MGraphQLJwtService;
import br.com.m4rc310.gql.security.IMAuthUserProvider;

@Configuration
public class MGraphQLSecurityConfig implements IMAuthUserProvider{

	@Override
	public MUser authUser(String username, Object password) throws Exception {
		return null;
	}

	@Override
	public MUser getUserFromUsername(String username) {
		return null;
	}

	@Override
	public MUser loadUser(MGraphQLJwtService jwt, MEnumToken type, String token) throws Exception {
		
		MUser u = null ;
		
		switch (type) {
		case TEST:
			Log.info(token);
			int i = token.indexOf(":");
			String username = token.substring(0, i);
			String password = token.substring(i+1);
			
			
			if ("text".equalsIgnoreCase(username)) {
				u = new MUser();
				u.setUsername(username);
				u.setPassword(password);
				u.setRoles("Admin".split(";"));
				return u;
			}else {
				throw new Exception("Invalid credentials");
			}
		case BASIC:
		case BEARER:
			return u;
		default:
			return u;
		}
		
		
//		MUser u = new MUser();
//		u.setUsername("test");
//		u.setPassword("test");
//		u.setRoles("Admin".split(";"));
//		
//		return u;
	}

	@Override
	public boolean isValidUser(MUser user) {
		return true;
	}

	@Override
	public void validUserAccess(MAuthToken authToken, String[] roles) throws Exception {
		
	}

}
