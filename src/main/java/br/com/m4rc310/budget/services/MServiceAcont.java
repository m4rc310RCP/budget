package br.com.m4rc310.budget.services;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.m4rc310.budget.data.dto.local.City;
import br.com.m4rc310.budget.data.dto.local.CityData;
import br.com.m4rc310.gql.mappers.annotations.MAuth;
import br.com.m4rc310.gql.mappers.annotations.MDate;
import br.com.m4rc310.weather.dto.MCity;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceAcont extends MService {

	@Autowired
	private MServiceCache cache;

//	@Autowired
//	private MWeatherService weatherService;

	@MAuth(roles = "basic")
	@GraphQLQuery(name = QUERY$number_version, description = DESC$query_number_version)
	public String getVersion() {
		return getVersionFromPom();
	}

	@GraphQLQuery(name = QUERY$city, description = DESC$query_city)
	public CityData getCityData(
			@GraphQLArgument(name = NAME$city, description = DESC$name_city) String name,
			@GraphQLArgument(name = ACRONYM$state, description = DESC$acronym_state) String state

	) {
//		return cache.getCityData(name);
		return null;
	}

	@GraphQLQuery(name = CODE$ibge, description = DESC$code_ibge)
	public Long getCityCode(@GraphQLContext CityData data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return City.from(mapper.readValue(data.getJson(), MCity.class)).getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GraphQLQuery(name = NAME$city, description = DESC$name_city)
	public String getCityName(@GraphQLContext CityData data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return City.from(mapper.readValue(data.getJson(), MCity.class)).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GraphQLQuery(name = ACRONYM$state, description = DESC$acronym_state)
	public String getAcromnState(@GraphQLContext CityData data) {
		return cache.getAcromnState(data);
	}

	@GraphQLQuery(name = NUMBER$lat, description = DESC$number_lat)
	public BigDecimal lat(@GraphQLContext CityData data) throws Exception {
		data = cache.updateLocation(data);
		JSONObject jsono = new JSONObject(data.getJson());
		return jsono.getBigDecimal("lat");
	}

	@GraphQLQuery(name = NUMBER$lon, description = DESC$number_lon)
	public BigDecimal lon(@GraphQLContext CityData data) throws Exception {
		data = cache.updateLocation(data);
		JSONObject jsono = new JSONObject(data.getJson());
		return jsono.getBigDecimal("lon");
	}

	@GraphQLQuery(name = QUANTITY$temp)
	public BigDecimal getTempNow(@GraphQLContext CityData data) throws Exception {
		try {
			return cache.getWeather(data).getCurrent().getTemperature();
		} catch (Exception e) {
			return null;
		}
	}

	@GraphQLQuery(name = QUANTITY$precipitation)
	public BigDecimal getRainNow(@GraphQLContext CityData data) throws Exception {
		try {
			return cache.getWeather(data).getCurrent().getRain().getPrecipitation();
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	@MDate(unixFormat = true)
	@GraphQLQuery(name = DATE$sunrise)
	public Long getDateSunRise(@GraphQLContext CityData data) throws Exception {
		try {
			return cache.getWeather(data).getCurrent().getDateSunRise();
		} catch (Exception e) {
			return null;
		}
	}

	@MDate(unixFormat = true)
	@GraphQLQuery(name = DATE$sunset)
	public Long getDateSunSet(@GraphQLContext CityData data) throws Exception {
		try {
			return cache.getWeather(data).getCurrent().getDateSunSet();
		} catch (Exception e) {
			return null;
		}
	}

////	@MAuth(roles = "basic")
////	@GraphQLQuery(name = QUERY$locations, description = DESC$query_locations)
//	public List<Location> findLocationByDescriptionAndState(
//			@GraphQLArgument(name = "${description.location}", description="${desc.description.location}") String description, 
//			@GraphQLArgument(name = "${acronym.state}", description="${desc.acronym.state}") String state){
//		return cache.findLocationByDescriptionAndState(description, state);
//	}
//	
//	@GraphQLQuery(name = QUERY$city, description = DESC$query_city)
//	public City findCityFromName(String name){
//		try {
//			return City.from(weatherService.findCity(name));
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	@GraphQLQuery(name = LIST$district, description = DESC$list_district)
//	public List<District> listDistrictFromCity(@GraphQLContext City city){
//		List<District> lst = new ArrayList<>();
//		
//		 MDistrict[] districts = weatherService.listDistrictsFromCity(city.getId());
//		for (MDistrict dis : districts) {
//			lst.add(District.from(dis));
//		}
//		return lst;
//	}
//	public Location getLocationFrom(City city) {
//		weatherService.
//	}

}
