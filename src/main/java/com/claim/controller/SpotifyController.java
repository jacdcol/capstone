package com.claim.controller;

import com.claim.entity.User;
import com.claim.entity.UserSpotify;
import com.claim.repository.UserRepository;
import com.claim.service.AuthService;
import com.claim.service.SpotifyService;
import com.claim.service.UserService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(value="/spotify-api")
public class SpotifyController
{
    @Autowired
    UserService userService;
    private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify-api/callback");
    private static final String scope = "user-read-email,user-top-read,user-library-modify";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();

    @RequestMapping(value="/{auth-code-uri}", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getSpotifyAuthCodeUri(User user)
    {
        try
        {
            user.setUserSpotify(new UserSpotify());
            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(user.getUserSpotify().getCodeChallenge())
                    .state(user.getUserSpotify().getState())
                    .scope(scope)
                    .show_dialog(true)
                    .build();
            final URI uri = authorizationCodeUriRequest.execute();
            System.out.println("URI = " + uri.toString());
            user.getUserSpotify().setAuthCode(uri.toString());
            System.out.println(user.getUserSpotify().getAuthCode());
            return new ResponseEntity<>(user.getUserSpotify().getAuthCode(), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="/callback", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getToken(@RequestParam("code") String code, @RequestParam("state") String state)
    {
        User usr = userService.findUserByState(state);
        try
        {
            AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(usr.getUserSpotify().getAuthCode(),
                    usr.getUserSpotify().getCodeVerifier()).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();
            //
            SpotifyService.updateTokens(usr.getUserSpotify(), spotifyApi, authorizationCodeCredentials);
            System.out.println(code);
            System.out.println(state);
            return new ResponseEntity<>(code, HttpStatus.OK);
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (NullPointerException n)
        {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /*@RequestMapping(value="/spotify-refresh", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,
            method=RequestMethod.POST)
    public void spotifyAuthRefresh(@RequestBody User user)
    {
        SpotifyService.authCodeRefresh(user.getUserSpotify());
    }*/
}
