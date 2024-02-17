package br.com.m4rc310.budget.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.jfree.util.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.budget.data.dto.local.CityData;
import br.com.m4rc310.budget.data.dto.local.Location;
import br.com.m4rc310.budget.repositories.AuthUserRepository;
import br.com.m4rc310.gql.dto.MUser;
import br.com.m4rc310.weather.dto.MCity;
import br.com.m4rc310.weather.dto.MCityData;
import br.com.m4rc310.weather.dto.MWeather;
import br.com.m4rc310.weather.dto.MWeatherLocation;
import br.com.m4rc310.weather.services.MWeatherService;
import io.leangen.graphql.annotations.GraphQLContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableCaching
public class MServiceCache {
	public static final String CACHE_FIRST_USER_MODE = "cache.first.user.mode";
	public static final String CACHE_USER = "cache.user";
	public static final String CACHE_MUSER = "cache.muser";
	public static final String CACHE_AUTH_USER = "cache.auth.user";
	public static final String CACHE_HASH_USERNAME = "cache.hash.username";
	public static final String CACHE_AUTHENTICATE = "cache.authenticate";
	public static final String CACHE_CITY = "cache.city";
	public static final String CACHE_LOCATION = "cache.location";
	public static final String CACHE_WEATHER = "cache.weather";

	@Autowired
	protected AuthUserRepository authUserRepository;

	@Autowired
	protected MWeatherService weatherService;

	@Autowired
	protected PasswordEncoder encoder;

	@Cacheable(value = CACHE_AUTHENTICATE, key = "T(java.util.Objects).hash(#username, #password)")
	public MUser login(String username, Object password) throws Exception {
		String psw = String.valueOf(password);

		MUser user = fromUsername(username);
		if (encoder.matches(psw, user.getPassword())) {
			return user;
		}
		return null;
	}

	@Cacheable(value = CACHE_MUSER, key = "#username")
	public MUser fromUsername(String username) throws Exception {
		AuthUser user = authUserRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));
		return AuthUser.toMUser(user);
	}

	public List<Location> findLocationByDescriptionAndState(String description, String state) {
		List<Location> list = new ArrayList<>();
		try {
			List<MWeatherLocation> wlocations = weatherService.findLocationByNameAndState(description, state);
			wlocations.forEach(wl -> {
				list.add(Location.from(wl));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public MUser fromCode(Long code) throws Exception {
		AuthUser user = authUserRepository.findById(code).orElse(null);
		if (Objects.isNull(user)) {
			return null;
		}
		return AuthUser.toMUser(user);
	}

	public AuthUser store(AuthUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return authUserRepository.saveAndFlush(user);
	}

	@Cacheable(value = CACHE_CITY, key = "#name")
	public CityData getCityData(String name, String state) throws Exception {
		
//		MCityData[] list = weatherService.listCityData(name);a
		
		ObjectMapper mapper = new ObjectMapper();
		for(MCityData city : weatherService.listCityData(name)) {
			
		}
		
		MCityData wcdata = weatherService.getMCityData(name);
		if (wcdata != null) {
			return CityData.to(wcdata.getJson());
		}
		return null;
	}

	@Cacheable(value = CACHE_LOCATION)
	public CityData updateLocation(CityData data) throws Exception {
		JSONObject jsono = new JSONObject(data.getJson());

		String cityName = jsono.getString("nome");
		String acromnState = getAcromnState(data);

		List<MWeatherLocation> locations = weatherService.findLocationByNameAndState(cityName, acromnState);
		if (!locations.isEmpty()) {
			MWeatherLocation loc = locations.get(0);
			jsono.put("lat", loc.getLatitude());
			jsono.put("lon", loc.getLongitude());

			data = CityData.to(jsono.toString());
		}

		return data;
	}

	public CityData updateWeather(CityData data) throws Exception {
		data = updateLocation(data);
		JSONObject jsono = new JSONObject(data.getJson());
		String json = weatherService.getMWeatherJSON(jsono.getBigDecimal("lat"), jsono.getBigDecimal("lon"));
		if (json != null) {
			jsono.put("weacher", json);
			data = CityData.to(jsono.toString());
		}
		return data;
	}

	@Cacheable(value = CACHE_WEATHER)
	public MWeather getWeather(CityData data) throws Exception {
		data = updateLocation(data);
		JSONObject jsono = new JSONObject(data.getJson());
		return weatherService.getMWeather(jsono.getBigDecimal("lat"), jsono.getBigDecimal("lon"));
	}

	@CacheEvict(allEntries = true)
	public void evictAllCache() {
		log.info("Clear all cache!");
	}

	public String getAcromnState(@GraphQLContext CityData data) {
		try {
			JSONObject jsono = new JSONObject(data.getJson());
			jsono = jsono.getJSONObject("microrregiao").getJSONObject("mesorregiao").getJSONObject("UF");
			return jsono.getString("sigla");
		} catch (Exception e) {
			return null;
		}
	}

}
