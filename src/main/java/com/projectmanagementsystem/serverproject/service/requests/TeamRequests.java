package com.projectmanagementsystem.serverproject.service.requests;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public final class TeamRequests {
    private static final String teamBaseUrl = "http://server-team:8082/team";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final int defaultRoleIndx = 1;

    public static void insertOwner(long ownerId, long projectId) throws IllegalArgumentException {
        final JSONObject teamRequest = new JSONObject();
        teamRequest.put("userId", ownerId);
        teamRequest.put("userRole", defaultRoleIndx);
        teamRequest.put("projectId", projectId);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> request = new HttpEntity<String>(teamRequest.toString(), headers);
        try {
            restTemplate.exchange(teamBaseUrl, HttpMethod.POST, request, Void.class);
        } catch (HttpClientErrorException e) {throw new IllegalArgumentException("User not found");}
    }

}
