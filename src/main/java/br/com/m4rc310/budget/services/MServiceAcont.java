package br.com.m4rc310.budget.services;

import org.springframework.stereotype.Service;

import br.com.m4rc310.gql.mappers.annotations.MAuth;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceAcont extends MService {
	@MAuth(roles = "basic")
	@GraphQLQuery(name = QUERY$number_version, description = DESC$query_number_version)
	public String getVersion() {
		return getVersionFromPom();
	}
}
