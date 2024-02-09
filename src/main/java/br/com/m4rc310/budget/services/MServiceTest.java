package br.com.m4rc310.budget.services;


import org.springframework.stereotype.Service;

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
}
