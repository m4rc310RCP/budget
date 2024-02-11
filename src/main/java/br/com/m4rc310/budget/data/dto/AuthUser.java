package br.com.m4rc310.budget.data.dto;

import org.hibernate.annotations.ColumnDefault;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.gql.dto.MUser;
import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = MConst.TYPE$auth_user)
@Table(name = MConst.TYPE$auth_user)
@GraphQLType(name = MConst.TYPE$auth_user, description = MConst.DESC$type_auth_user)
public class AuthUser implements MConst {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = CODE$auth_user)
	@GraphQLQuery(name = CODE$auth_user, description = DESC$code_auth_user)
	private Long code;
	
	@Column(name = NAME$username, unique = true)
	@GraphQLQuery(name = NAME$username, description = DESC$name_username)
	private String username;
	
	@Column(name = LIST$roles)
	@GraphQLQuery(name = LIST$roles, description = DESC$list_roles)
	private String roles;
	
	@Column(name = VALUE$password)
	@GraphQLIgnore
	private String password;
	@Column(name = INDICATOR$non_expired)
	@GraphQLIgnore
	private boolean accountNonExpired;
	@GraphQLIgnore
	@Column(name = INDICATOR$account_non_locked)
	private boolean accountNonLocked;
	@GraphQLIgnore
	@Column(name = INDICATOR$credentials_non_locked)
	private boolean credentialsNonExpired;
	@GraphQLIgnore
	@Column(name = INDICATOR$enabled)
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
	
	public static MUser toMUser(AuthUser user) {
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
