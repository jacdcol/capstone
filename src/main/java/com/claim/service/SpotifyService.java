package com.claim.service;

import org.springframework.stereotype.Service;

@Service
public class SpotifyService
{
	public static void authCodeUri()
	{
		AuthService.authorizationCodeUriSync();
		AuthService.authorizationCodeUriAsync();
	}

	public static void authCode()
	{
		AuthService.authorizationCodeSync(0);
		AuthService.authorizationCodeAsync(0);
	}

	public static void authCodeRefresh()
	{
		AuthService.authorizationCodeSync(1);
		AuthService.authorizationCodeAsync(1);
	}
}