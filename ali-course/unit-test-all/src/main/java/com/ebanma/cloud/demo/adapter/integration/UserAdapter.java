package com.ebanma.cloud.demo.adapter.integration;

import com.ebanma.cloud.demo.adapter.integration.model.UserDTO;
import com.ebanma.cloud.demo.exception.IntegrateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserAdapter {
    private final String url;
    private final RestTemplate restTemplate;

    @Autowired
    public UserAdapter(@Value("${external.user.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public UserDTO getExternalUserInfo(Long id) {
        String path = String.format("/users/%d", id);
        ResponseEntity<UserDTO> response;
        try {
            response = restTemplate.getForEntity(url + path, UserDTO.class);
        } catch (RestClientException e) {
            throw new IntegrateException("UserAdapter.getExternalUserInfo call outer rest api failed, errMsg:%s", e.getMessage());
        }
        if (!Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
            return null;
        }
        return response.getBody();
    }
}