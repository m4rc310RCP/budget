package br.com.m4rc310.budget.data.dto.local;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.m4rc310.budget.services.MConst;
import br.com.m4rc310.weather.dto.MWeatherLocation;
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
@Entity(name = MConst.TYPE$location)
@Table(name = MConst.TYPE$location)
@GraphQLType(name = MConst.TYPE$location, description = MConst.DESC$type_location)
public class Location implements MConst, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = CODE$local)
	@GraphQLQuery(name = CODE$local, description=DESC$code_local)
	private Long id;
	
//	@Column(name = CODE$ibge)
//	@GraphQLQuery(name = CODE$ibge, description=DESC$code_ibge)
//	private Long codeIbge;
	
	@Column(name = DESCRIPTION$location)
	@GraphQLQuery(name = DESCRIPTION$location, description=DESC$description_location)
	private String description;

	@Column(name = ACRONYM$state)
	@GraphQLQuery(name = ACRONYM$state, description=DESC$acronym_state)
	private String acromymState;
	
	@Column(name = NUMBER$lon)
	@GraphQLQuery(name = NUMBER$lon, description=DESC$number_lon)
	private BigDecimal lon;
	
	@Column(name = NUMBER$lat)
	@GraphQLQuery(name = NUMBER$lat, description=DESC$number_lat)
	private BigDecimal lat;
	
	public static Location from(MWeatherLocation wl) {
		Location ts = new Location();
		ts.setDescription(wl.getName());
		ts.setAcromymState(wl.getState());
		ts.setLat(wl.getLatitude());
		ts.setLon(wl.getLongitude());
		return ts;
	} 
	
	
}
