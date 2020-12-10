package org.adityachandel.libraryapispring.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SimpleAuthenticationService {

    Map<String, String> users = new HashMap<>();

    public SimpleAuthenticationService() {
        users.put("aditya", "GWMDSsPNZX6N9R6mBnZstwLA457cmPMf");
    }

    public boolean areCredentialsValid(String username, String password) {
        String pass = users.get(username);
        return pass != null && pass.equals(password);
    }

}
