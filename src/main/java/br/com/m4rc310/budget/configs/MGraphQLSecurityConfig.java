package br.com.m4rc310.budget.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import br.com.m4rc310.budget.services.MServiceCache;
import br.com.m4rc310.gql.MUserProvider;
import br.com.m4rc310.gql.dto.MAuthToken;
import br.com.m4rc310.gql.dto.MUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MGraphQLSecurityConfig {
	
	@Autowired
	@Lazy
	private MServiceCache cache; 

	@Bean
	@Lazy
	MUserProvider loadMAuthUserProvider() {
		return new MUserProvider() {
			@Override
			public MUser authUser(String username, Object password) throws Exception {
				return cache.login(username, password);
			}

			@Override
			public MUser getUserFromUsername(String username) {
				try {
					return cache.fromUsername(username);
				} catch (Exception e) {
					log.debug(e.getMessage(), e);
					return null;
				}
			}

			@Override
			public boolean isValidUser(MUser user) {
				return true;
			}};
	}
}
