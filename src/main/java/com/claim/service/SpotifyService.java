package com.claim.service;

import com.claim.entity.UserSpotify;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.net.URI;

@Service
public class SpotifyService
{
	private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/dashboard");
	private static final String scope = "user-read-email,user-top-read,user-library-modify";
	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(clientId)
			.setRedirectUri(redirectUri)
			.build();

	public static void authCodeUri(UserSpotify userSpotify)
	{
		AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(userSpotify.getCodeChallenge())
				.state(userSpotify.getState())
				.scope(scope)
				.show_dialog(true)
				.build();
		userSpotify.setAuthCode(AuthService.authorizationCodeUriSync(authorizationCodeUriRequest));
		userSpotify.setAuthCode(AuthService.authorizationCodeUriAsync(authorizationCodeUriRequest));
	}

	public static void authCode(UserSpotify userSpotify)
	{
		AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(userSpotify.getAuthCode(), userSpotify.getCodeVerifier()).build();
		AuthService.authorizationCodeSync(userSpotify, spotifyApi, authorizationCodePKCERequest);
		AuthService.authorizationCodeAsync(userSpotify, spotifyApi, authorizationCodePKCERequest);
	}

	public static void authCodeRefresh(UserSpotify userSpotify)
	{
		AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
		AuthService.authorizationCodeRefreshSync(userSpotify, spotifyApi, authorizationCodePKCERefreshRequest);
		AuthService.authorizationCodeRefreshAsync(userSpotify, spotifyApi, authorizationCodePKCERefreshRequest);
	}
}