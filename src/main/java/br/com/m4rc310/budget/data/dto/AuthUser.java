package br.com.m4rc310.budget.data.dto;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.gql.dto.MUser;
import io.leangen.graphql.annotations.GraphQLIgnore;
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
	
	@GraphQLQuery(name = LIST$roles, description = DESC$list_roles)
	private String roles;
	
	@GraphQLIgnore
	private String password;
	@GraphQLIgnore
	private boolean accountNonExpired;
	@GraphQLIgnore
	private boolean accountNonLocked;
	@GraphQLIgnore
	private boolean credentialsNonExpired;
	@GraphQLIgnore
	private boolean enabled;
	
	
	public static AuthUser from(MUser user) {
		AuthUser u = new AuthUser();
		u.setCode(user.getCode());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setAccountNonExpired(user.isAccountNonExpired());
		u.setAccountNonLocked(user.isAccountNonLocked());
		u.setCredentialsNonExpired(user.isCredentialsNonExpired());
		u.setEnabled(user.isEnabled());
		u.setRoles(String.join(";", user.getRoles()));
		return u;
	}
	
	public static MUser from(AuthUser user) {
		MUser u = new MUser();
		u.setCode(user.getCode());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setAccountNonExpired(user.isAccountNonExpired());
		u.setAccountNonLocked(user.isAccountNonLocked());
		u.setCredentialsNonExpired(user.isCredentialsNonExpired());
		u.setEnabled(user.isEnabled());
		u.setRoles(user.roles != null? user.roles.split(";"): null);
		return u;
	}
	
	
}
