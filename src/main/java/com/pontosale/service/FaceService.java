package com.pontosale.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class FaceService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.face-service-endpoint}")
    private String baseUrl;

    public boolean validarFace(byte[] dbImage, byte[] requestImage) {
        String url = baseUrl + "/validate-faces";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("dbImage", new ByteArrayResource(dbImage) {
            @Override
            public String getFilename() {
                return "databaseImage.jpg";
            }
        });

        body.add("requestImage", new ByteArrayResource(requestImage) {
            @Override
            public String getFilename() {
                return "requestImage.jpg";
            }
        });

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        System.out.println("chamou");

        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);

        System.out.println(response.getBody());

        return response.getBody() != null && response.getBody();
    }

}
