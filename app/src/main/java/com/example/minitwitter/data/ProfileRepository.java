package com.example.minitwitter.data;

import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.common.MyApp;
import com.example.minitwitter.common.SharedPreferenceManager;
import com.example.minitwitter.retrofit.AuthTwitterClient;
import com.example.minitwitter.retrofit.AuthTwitterService;
import com.example.minitwitter.retrofit.request.RequestCreateTweet;
import com.example.minitwitter.retrofit.response.Like;
import com.example.minitwitter.retrofit.response.ResponseUserProfile;
import com.example.minitwitter.retrofit.response.Tweet;
import com.example.minitwitter.retrofit.response.TweetDeleted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;

    MutableLiveData<ResponseUserProfile> userProfile;

    String userName;

    public ProfileRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        userProfile = getProfile();
    }

    public MutableLiveData<ResponseUserProfile> getProfile() {
        if (userProfile == null) {
            userProfile = new MutableLiveData<>();
        }

        Call<ResponseUserProfile> call = authTwitterService.getProfile();
        call.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (response.isSuccessful()) {
                    userProfile.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT);
            }
        });


        return userProfile;
    }







}
