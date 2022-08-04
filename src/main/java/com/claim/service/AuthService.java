package com.claim.service;

import com.claim.entity.User;
import com.claim.entity.UserSpotify;
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
    public static String authorizationCodeUriSync(AuthorizationCodeUriRequest authorizationCodeUriRequest)
    {

        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI = " + uri.toString());
        return uri.toString();
    }
    public static String authorizationCodeUriAsync(AuthorizationCodeUriRequest authorizationCodeUriRequest)
    {
        try
        {
            final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();
            //
            final URI uri = uriFuture.join();
            System.out.println("URI = " + uri.toString());
            return uri.toString();
        }
        catch(CompletionException e)
        {
            System.out.println("Error : " + e.getCause().getMessage());
            return "";
        }
        catch(CancellationException r)
        {
            System.out.println("ASYNC operation cancelled");
            return "";
        }
    }
    public static void authorizationCodeSync(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodePKCERequest authorizationCodePKCERequest)
    {
        try
        {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();
            //
            updateTokens(userSpotify, spotifyApi, authorizationCodeCredentials);
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
    public static void authorizationCodeAsync(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodePKCERequest authorizationCodePKCERequest)
    {
        try
        {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodePKCERequest.executeAsync();

            //
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();
            updateTokens(userSpotify, spotifyApi, authorizationCodeCredentials);
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
    public static void authorizationCodeRefreshSync(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest)
    {
        try
        {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();
            //
            updateTokens(userSpotify, spotifyApi, authorizationCodeCredentials);
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void authorizationCodeRefreshAsync(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest)
    {
        try
        {
            authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodePKCERefreshRequest.executeAsync();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();
            updateTokens(userSpotify, spotifyApi, authorizationCodeCredentials);
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
    public static void updateTokens(UserSpotify userSpotify, SpotifyApi spotifyApi, AuthorizationCodeCredentials authorizationCodeCredentials)
    {
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        userSpotify.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        userSpotify.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        System.out.println("Expires in : " + authorizationCodeCredentials.getExpiresIn());
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