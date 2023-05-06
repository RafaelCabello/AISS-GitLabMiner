package aiss.gitlabminer.service;


import aiss.gitlabminer.model.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    public List<UserSearch> findAllUsers() {

        HttpHeaders headers = new HttpHeaders();
        //headers.add("x-api-key", "glpat-NTBWo4LMEze4yssZBuzH");
        headers.set("Authorization", "Bearer "+"glpat-NTBWo4LMEze4yssZBuzH");
        //headers.add("Authorization", " " + "glpat-NTBWo4LMEze4yssZBuzH");
        String uri = "https://gitlab.com/api/v4/users";
        HttpEntity<UserSearch[]> request = new HttpEntity<UserSearch[]>(headers);
        ResponseEntity<UserSearch[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, UserSearch[].class);
        UserSearch[] users = response.getBody();
        //UserSearch[] userSearch = restTemplate.getForObject(uri, UserSearch[].class);
        return Arrays.stream(users).toList();
    }

}
