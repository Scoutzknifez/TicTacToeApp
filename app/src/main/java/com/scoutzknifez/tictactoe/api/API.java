package com.scoutzknifez.tictactoe.api;

import com.scoutzknifez.tictactoe.structures.api.LobbyPacket;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @POST("/API/joinLobby")
    @Headers("Accept: application/json")
    Call<LobbyPacket> getLogin();
}