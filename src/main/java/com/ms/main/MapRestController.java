package com.ms.main;

import java.net.URI;
import java.nio.charset.Charset;

import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ms.common.TransCoord;
import com.ms.main.bo.VetBO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MapRestController {
	
	@Autowired
	private TransCoord transCoord;
	
	@Autowired
	private VetBO vetBO;
	
	@GetMapping("/naver")
	public String naver() {

        // 공식 문서의 [파라미터] 부분 참고
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", "동물병원")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("--- naver ---");
        log.info("uri : {}", uri);

        // Header를 위함
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "KxhYATkbflu3pEkt7KIJ")
                .header("X-Naver-Client-Secret", "ICH7I7TwMi")
                .build();

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
	}
}
