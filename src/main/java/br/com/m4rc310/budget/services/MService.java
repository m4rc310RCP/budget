package br.com.m4rc310.budget.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.gql.dto.MUser;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MService extends br.com.m4rc310.gql.services.MService implements MConst {

	@GraphQLQuery(name = QUERY$auth_user, description = DESC$query_auth_user)
	public AuthUser authUser() {
		try {
			MUser user = (MUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("User: {}", user);
			return AuthUser.from(user);
		} catch (Exception e) {
			return null;
		}
	}
}
