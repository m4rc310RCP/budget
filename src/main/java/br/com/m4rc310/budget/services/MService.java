package br.com.m4rc310.budget.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tools.ant.Main;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.budget.repositories.AuthUserRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MService extends br.com.m4rc310.gql.services.MService implements MConst {
	
	@Autowired
	protected AuthUserRepository authUserRepository;

	@GraphQLQuery(name = QUERY$auth_user, description = DESC$query_auth_user)
	public AuthUser authUser() {
		try {
			log.debug("converting user from authUser...");	
			return AuthUser.from(flux.getUser());
		} catch (Exception e) {
			return null;
		}
	}
	
	protected static String getVersionFromPom() {
        Properties properties = new Properties();
        String version = null;
        try (InputStream inputStream = Main.class.getResourceAsStream("/META-INF/maven/br-com-m4rc310/budget/pom.properties")) {
            properties.load(inputStream);
            version = properties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }
}
