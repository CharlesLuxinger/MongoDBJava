package br.com.mongodb.escola.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import br.com.mongodb.escola.model.Contato;

@Service
public class GeolocalizacaoService {
	public List<Double> getLateLongBy(Contato contato) throws ApiException, InterruptedException, IOException {
		GeoApiContext apiContext = new GeoApiContext().setApiKey("AIzaSyC4sfJSl5-OQ5unxXohyd2WDXw_kbIOK7A");

		GeocodingApiRequest request = GeocodingApi.newRequest(apiContext).address(contato.getEndereco());

		GeocodingResult[] results = request.await();

		GeocodingResult result = results[0];

		Geometry geometry = result.geometry;

		LatLng location = geometry.location;

		return Arrays.asList(location.lat, location.lng);
	}
}
