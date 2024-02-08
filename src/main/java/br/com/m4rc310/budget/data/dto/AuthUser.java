package br.com.m4rc310.budget.data.dto;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.gql.dto.MUser;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Data;

@Data
@GraphQLType(name = MConst.TYPE$auth_user, description = MConst.DESC$type_auth_user)
public class AuthUser implements MConst {
	
	@GraphQLQuery(name = CODE$auth_user, description = DESC$code_auth_user)
	private Long code;
	
	@GraphQLQuery(name = NAME$username, description = DESC$name_username)
	private String username;
	
	
	public static AuthUser from(MUser user) {
		AuthUser u = new AuthUser();
		u.setCode(user.getCode());
		u.setUsername(user.getUsername());
		return u;
	}
	
}
