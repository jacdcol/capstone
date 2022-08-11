package com.claim.service;

import com.claim.entity.UserSpotify;
import com.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;

import java.io.IOException;import org.apache.hc.core5.http.ParseException;
@Service
public class SpotifyService
{
	@Autowired
	UserRepository userRepository;

	public static void refreshInstance(UserSpotify userSpotify)
	{
		userSpotify.resetState();
		userSpotify.resetCodeVerifier();
	}

	public static void updateTokens(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodeCredentials authorizationCodeCredentials)
	{
		spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		userSpotify.setAccessToken(authorizationCodeCredentials.getAccessToken());
		spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		userSpotify.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
	}

	public static void refreshAccessToken(UserSpotify userSpotify, SpotifyApi spotifyApi)
	{
		AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
		try
		{
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();
			//
			updateTokens(userSpotify, spotifyApi, authorizationCodeCredentials);
			System.out.println("token refreshed");

		}
		catch (IOException | SpotifyWebApiException | ParseException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
	}
}