package com.pasha.cardviewwithjson;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pasha on 18/07/2017.
 */

public interface GetInterface {

    @GET("newcardviewapp/8cc9e83bff6b128d4cf6324d1985f47e/CardViewDemo.json")
    Call<List<JSONInfo>> getJSON();

}
