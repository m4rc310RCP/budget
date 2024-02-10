 package br.com.m4rc310.budget.services;


import org.springframework.stereotype.Service;

import br.com.m4rc310.gql.dto.MUser;
import br.com.m4rc310.gql.mappers.annotations.MAuth;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@GraphQLApi
public class MServiceTest extends MService{

	@GraphQLQuery(name = QUERY$test, description = DESC$query_test)
	public String queryTest() {
		log.info("#query test");
		return "OK";
	}
	
	@MAuth(roles = "admin")
	@GraphQLQuery
	public String makeBasicToken() throws Exception {
		String token = "%s:%s";
		token = String.format(token, "basic-user", encoder.encode("1234"));
		return jwt.encrypt(token);
	}
	
	@MAuth(roles = "test")
	@GraphQLQuery
	public String makeBearerToken() throws Exception {
		MUser user = new MUser();
		user.setUsername("bearer-user");
		user.setPassword("1234");
		user.setRoles("admin".split(";"));
		return jwt.generateToken(user);
	}
	
} 
