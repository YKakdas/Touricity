package com.squadro.touricity.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squadro.touricity.converter.LocationConverter;
import com.squadro.touricity.message.types.Location;
import com.squadro.touricity.retrofit.RestAPI;
import com.squadro.touricity.retrofit.RetrofitCreate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationRequests {

    public LocationRequests() {

    }

    public void getLocationInfo(String location_id, ILocationRequest iLocationRequest) {
        final LocationConverter locationConverter = new LocationConverter();

        RetrofitCreate retrofitCreate = new RetrofitCreate();
        Retrofit retrofit = retrofitCreate.createRetrofit();
        RestAPI restAPI = retrofit.create(RestAPI.class);

        JsonObject obj = new JsonObject();
        obj.addProperty("location_id", location_id);

        Call<JsonObject> jsonObjectCall = restAPI.locationInfo(obj);

        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject body = response.body();
                if (body != null) {
                    iLocationRequest.onResponseLocationInfo((Location) locationConverter.jsonToObject(response.body()));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void createLocation(Location location) {

        RetrofitCreate retrofitCreate = new RetrofitCreate();
        Retrofit retrofit = retrofitCreate.createRetrofit();
        RestAPI restAPI = retrofit.create(RestAPI.class);

        Gson gson = new Gson();
        String s = gson.toJson(location);
        JsonParser jsonParser = new JsonParser();
        JsonObject asJsonObject = jsonParser.parse(s).getAsJsonObject();

        Call<JsonObject> jsonObjectCall = restAPI.createLocation(asJsonObject);

        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String message = response.message();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                String message = t.getMessage();
            }
        });
    }
}
