package com.security.oauth2springboot;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service(value="clientDetailsService")
public class CustomClientDetailsService implements ClientDetailsService{

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		return new CustomClientDetails();
	}

}
