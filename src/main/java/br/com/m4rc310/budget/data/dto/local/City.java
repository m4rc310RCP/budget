package br.com.m4rc310.budget.data.dto.local;

import java.io.Serializable;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.weather.dto.MCity;
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
@Entity(name = MConst.TYPE$city)
@Table(name = MConst.TYPE$city)
@GraphQLType(name = MConst.TYPE$city)
public class City implements MConst, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = CODE$ibge)
	@GraphQLQuery(name = CODE$ibge, description = DESC$code_ibge)
	private Long id;
	
	@Column(name = NAME$city)
	@GraphQLQuery(name = NAME$city, description = DESC$name_city)
	private String name;

	public static City from(MCity c) {
		City ts = new City();
		ts.setId(c.getId());
		ts.setName(c.getName());
		return ts;
	}

}
