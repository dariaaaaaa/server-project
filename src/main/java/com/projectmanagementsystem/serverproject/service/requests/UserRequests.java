package com.projectmanagementsystem.serverproject.service.requests;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public final class UserRequests {
    private static final String userBaseUrl = "http://server-user:8081/users";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void checkForExisting(long userId) throws IllegalArgumentException{
        final String userUrl = String.format( userBaseUrl + "/exist?id=%d", userId);

        try {
            final int userResponse = restTemplate.exchange(URI.create(userUrl), HttpMethod.HEAD, null, Void.class ).getStatusCodeValue();
        } catch (HttpClientErrorException e) {throw new IllegalArgumentException("User not found");}
    }

}
