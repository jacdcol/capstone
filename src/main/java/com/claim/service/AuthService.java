package com.claim.service;

import net.bytebuddy.utility.RandomString;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class AuthService
{
    private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/profile");
    private static String code;
    private static final String scope = "user-read-email,user-top-read,user-library-modify";
    private static final String state = RandomString.make(18).toUpperCase();
    private static final String codeVerifier = AuthService.generateCodeVerifier();
    private static final String codeChallenge = AuthService.generateCodeChallenge(codeVerifier);
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(codeChallenge)
            .state(state)
            .scope(scope)
            .show_dialog(true)
            .build();
    private static final AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(code, codeVerifier)
            .build();
    private static final AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh()
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
    public static void authorizationCodeSync(int mode)
    {
        try
        {
            AuthorizationCodeCredentials authorizationCodeCredentials;
            switch (mode)
            {
                case 0 -> {authorizationCodeCredentials = authorizationCodePKCERequest.execute();}
                case 1 -> {authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();}
                default -> throw new IOException();
            }

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        catch (NullPointerException n)
        {
            System.out.println("Error : failed to execute auth code request");
        }
    }
    public static void authorizationCodeAsync(int mode)
    {
        try
        {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture;
            switch (mode)
            {
                case 0 -> authorizationCodeCredentialsFuture = authorizationCodePKCERequest.executeAsync();
                case 1 -> authorizationCodeCredentialsFuture = authorizationCodePKCERefreshRequest.executeAsync();
                default -> throw new IOException();
            }
            //
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
        }
        catch(CompletionException | IOException e)
        {
            System.out.println("Error : " + e.getCause().getMessage());
        }
        catch(CancellationException r)
        {
            System.out.println("ASYNC operation cancelled");
        }
    }
	public static String generateCodeVerifier()
    {
    	SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }
    public static String generateCodeChallenge(String codeVerifier)
    {
    	try
    	{
    		byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
    		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
            byte[] digest = messageDigest.digest();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    	}
        catch(NoSuchAlgorithmException r)
    	{
        	return null;
    	}  
    }
}