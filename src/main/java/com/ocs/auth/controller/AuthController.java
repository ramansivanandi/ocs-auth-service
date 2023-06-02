package com.ocs.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final Logger log = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public Mono<Map<String, Object>> healhCheck(ServerHttpRequest request, ServerHttpResponse respons) {

		System.out.println("Inside auth healhCheck method");
		HttpHeaders headers = request.getHeaders();
		Map<String, Object> headerData = new HashMap<String, Object>();
		headers.forEach((a, b) -> {
			// System.out.println(a+":"+b);
			headerData.put(a, b);
		});

		Mono<Map<String, Object>> data = Mono.just(headerData);
		System.out.println("Exit auth healhCheck method");
		return data;

	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)

	public Mono<Map<String, Object>> postServiceStatus(ServerHttpRequest request, ServerHttpResponse respons,
			@RequestBody String body) {

		System.out.println("Inside auth status method");

		Map<String, Object> mapData = new HashMap<String, Object>();

		mapData.put("body", body);
		Mono<Map<String, Object>> monoMapdata = Mono.just(mapData);

		Gson gsonData = new Gson();

		String json = gsonData.toJson(mapData);

		String jsonData = "";
		try {
			// jsonData = new ObjectMapper().writeValueAsString(mapData);

			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			jsonData = mapper.writeValueAsString(mapper.readTree(json));
			log.info("gsonData:{}", jsonData);

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		System.out.println("Exit auth status method");

		return monoMapdata;

	}

	// ,produces = MediaType.TEXT_EVENT_STREAM_VALUE

	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public Flux<String> findByName(@PathVariable("name") String name) {

		Flux<String> flux = Flux.fromArray(new String[] { "A", "B", name });
		return flux;

	}
	@GetMapping("/print")
    public ResponseEntity<String> downloadExcel(@RequestParam("testParam")String param){
        if(param.equals("badRequest")){
            return new ResponseEntity<>("bad request",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
	
	@RequestMapping(value = "/statusFlux", method = RequestMethod.POST)

	public Flux<Map<String, Object>> postFluxServiceStatus(ServerHttpRequest request, ServerHttpResponse respons,
			@RequestBody String body) {

		System.out.println("Inside auth status method");

		Map<String, Object> mapData = new HashMap<String, Object>();

		mapData.put("body", body);
		Flux<Map<String, Object>> fluxdata = Flux.just(mapData);

		Gson gsonData = new Gson();

		String json = gsonData.toJson(mapData);

		String jsonData = "";
		try {
			// jsonData = new ObjectMapper().writeValueAsString(mapData);

			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			jsonData = mapper.writeValueAsString(mapper.readTree(json));
			log.info("gsonData:{}", jsonData);

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		System.out.println("Exit auth status method");

		return fluxdata;

	}

}
