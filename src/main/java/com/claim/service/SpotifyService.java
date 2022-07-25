package com.claim.service;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.*;

import net.bytebuddy.utility.RandomString;
import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import com.claim.entity.UserSpotify;

public class SpotifyService
{
	private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/profile");
	private static String code;
	private static final String scope = "user-read-private,user-read-email";
	private static final String state = RandomString.make(18).toUpperCase();
	
	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(clientId)
			.setRedirectUri(redirectUri)
			.build();
	private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(UserSpotify.getCodeChallenge())
			.state(state)
			.scope(scope)
			.show_dialog(true)
			.build();
	
	public static void authorizationCodeUriSync()
	{
		final URI uri = authorizationCodeUriRequest.execute();
		code = uri.toString();
		System.out.println("URI = " + code);
	}
	
	public static void authorizationCodeUriAsync()
	{
		try
		{
			final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();
			//
			final URI uri = uriFuture.join();
			code = uri.toString();
			System.out.println("URI = " + code);
		}
		catch(CompletionException e)
		{
			System.out.println("Error : " + e.getCause().getMessage());
		}
		catch(CancellationException r)
		{
			System.out.println("ASYNC operation cancelled");
		}
	}
	
	private static final AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(code, UserSpotify.getCodeVerifier())
            .build();
	
	public static void authorizationCodeSync()
	{
		try
		{
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		    spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		    System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
		}
		catch (IOException | SpotifyWebApiException | ParseException e)
		{
		    System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void authorizationCodeAsync()
	{
		try
		{
			final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodePKCERequest.executeAsync();
			//
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		    spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		    System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
		}
		catch(CompletionException e)
		{
			System.out.println("Error : " + e.getCause().getMessage());
		}
		catch(CancellationException r)
		{
			System.out.println("ASYNC operation cancelled");
		}
	}
	
	private static final AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh()
		    .build();
	
	public static void authorizationCodeRefreshSync()
	{
		try
		{
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		    spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		    System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
		}
		catch (IOException | SpotifyWebApiException | ParseException e)
		{
		    System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void authorizationCodeRefreshAsync()
	{
		try
		{
			final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodePKCERefreshRequest.executeAsync();
			//
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		    System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
		}
		catch(CompletionException e)
		{
			System.out.println("Error : " + e.getCause().getMessage());
		}
		catch(CancellationException r)
		{
			System.out.println("ASYNC operation cancelled");
		}
	}
}