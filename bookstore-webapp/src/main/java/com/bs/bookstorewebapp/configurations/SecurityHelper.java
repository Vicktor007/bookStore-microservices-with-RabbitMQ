package com.bs.bookstorewebapp.configurations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {
    private final OAuth2AuthorizedClientService authorizedClientService;
    public SecurityHelper(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    public String getAccesToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken)){
            return null;
        }
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),oAuth2AuthenticationToken
                        .getName());
        return client.getAccessToken().getTokenValue();
    }
}
