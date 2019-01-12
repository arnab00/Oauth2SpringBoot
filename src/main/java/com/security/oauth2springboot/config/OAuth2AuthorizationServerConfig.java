package com.security.oauth2springboot.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * ClientDetailsServiceConfigurer: a configurer that defines the client details
	 * service. Client details can be initialized, or you can just refer to an
	 * existing store.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("test").secret("{noop}test").authorizedGrantTypes("authorization_code").scopes("read").accessTokenValiditySeconds(300)
				.redirectUris("http://localhost:8090/").authorities("CLIENT");
	}

	/**
	 * AuthorizationServerSecurityConfigurer: defines the security constraints on
	 * the token endpoint.
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	/**
	 * AuthorizationServerEndpointsConfigurer: defines the authorization and token
	 * endpoints and the token services.
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.accessTokenConverter(accessTokenConverter()).tokenStore(tokenStore()).approvalStoreDisabled();
	}

	@Value("classpath:keys/pvt.key")
	Resource pvtKeyResource;
	@Value("classpath:keys/pub.key")
	Resource pubKeyResource;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() throws IOException {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(IOUtils.toString(pvtKeyResource.getInputStream(), "UTF-8"));
		accessTokenConverter.setVerifierKey(IOUtils.toString(pubKeyResource.getInputStream(), "UTF-8"));
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() throws IOException {
		return new JwtTokenStore(accessTokenConverter());
	}

}
