package br.com.m4rc310.budget.data.dto.local;

import io.leangen.graphql.annotations.GraphQLIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "to")
public class CityData {
	@GraphQLIgnore
	private String json;
}
