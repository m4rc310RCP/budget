package br.com.m4rc310.budget.services;

import br.com.m4rc310.budget.data.dto.AuthUser;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MService extends br.com.m4rc310.gql.services.MService implements MConst {

	@GraphQLQuery(name = QUERY$auth_user, description = DESC$query_auth_user)
	public AuthUser authUser() {
		try {
			log.debug("converting user from authUser...");	
			return AuthUser.from(flux.getUser());
		} catch (Exception e) {
			return null;
		}
	}
}
