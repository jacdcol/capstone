package com.claim.service;

import com.claim.entity.User;
import com.claim.entity.UserSpotify;
import com.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.net.URI;
import java.util.*;

@Service
public class SpotifyService
{
	@Autowired
	UserRepository userRepository;
	/*private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify-api/callback");
	private static final String scope = "user-read-email,user-top-read,user-library-modify";
	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(clientId)
			.setRedirectUri(redirectUri)
			.build();*/

	/*public static void authCodeUri(UserSpotify userSpotify)
	{
		AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(userSpotify.getCodeChallenge())
				.state(userSpotify.getState())
				.scope(scope)
				.show_dialog(true)
				.build();
		userSpotify.setAuthCode(AuthService.authorizationCodeUriSync(authorizationCodeUriRequest));
		//userSpotify.setAuthCode(AuthService.authorizationCodeUriAsync(authorizationCodeUriRequest));
	}*/


	public static void updateTokens(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodeCredentials authorizationCodeCredentials)
	{
		spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		userSpotify.setAccessToken(authorizationCodeCredentials.getAccessToken());
		spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		userSpotify.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
	}

	/*public static void accessToken(UserSpotify userSpotify, String code)
	{

		AuthService.authorizationCodeAsync(userSpotify, spotifyApi, authorizationCodePKCERequest);
	}

	public static void authCodeRefresh(UserSpotify userSpotify)
	{
		AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
		AuthService.authorizationCodeRefreshSync(userSpotify, spotifyApi, authorizationCodePKCERefreshRequest);
		AuthService.authorizationCodeRefreshAsync(userSpotify, spotifyApi, authorizationCodePKCERefreshRequest);
	}*/
}