package br.com.m4rc310.budget.data.dto.local;

import java.io.Serializable;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.weather.dto.MDistrict;
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
@Entity(name = MConst.TYPE$district)
@Table(name = MConst.TYPE$district)
@GraphQLType(name = MConst.TYPE$district)
public class District implements MConst, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name =  CODE$ibge)
	@GraphQLQuery(name = CODE$ibge, description = DESC$code_ibge)
	private Long id;
	
	@Column(name = NAME$district)
	@GraphQLQuery(name = NAME$district, description = DESC$name_district)
	private String name;

	public static District from(MDistrict c) {
		District ts = new District();
		ts.setId(c.getId());
		ts.setName(c.getName());
		return ts;
	}

}
