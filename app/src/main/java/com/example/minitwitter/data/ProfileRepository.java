package com.example.minitwitter.data;

import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.common.MyApp;
import com.example.minitwitter.common.SharedPreferenceManager;
import com.example.minitwitter.retrofit.AuthTwitterClient;
import com.example.minitwitter.retrofit.AuthTwitterService;
import com.example.minitwitter.retrofit.request.RequestCreateTweet;
import com.example.minitwitter.retrofit.request.RequestUserProfile;
import com.example.minitwitter.retrofit.response.Like;
import com.example.minitwitter.retrofit.response.ResponseUploadPhoto;
import com.example.minitwitter.retrofit.response.ResponseUserProfile;
import com.example.minitwitter.retrofit.response.Tweet;
import com.example.minitwitter.retrofit.response.TweetDeleted;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;

    MutableLiveData<ResponseUserProfile> userProfile;
    MutableLiveData<String> photoProfile;

    String userName;

    public ProfileRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        userProfile = getProfile();
        if (photoProfile == null) {
            photoProfile = new MutableLiveData<>();
        }
    }

    public MutableLiveData<String> getPhotoProfile() {
        return photoProfile;
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



    public void updateProfile(RequestUserProfile requestUserProfile) {
        Call<ResponseUserProfile> call = authTwitterService.updateProfile(requestUserProfile);
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

    }

    public void uploadPhoto(String photoPath) {
        File file = new File(photoPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        Call<ResponseUploadPhoto> call = authTwitterService.uploadProfilePhoto(requestBody);

        call.enqueue(new Callback<ResponseUploadPhoto>() {
            @Override
            public void onResponse(Call<ResponseUploadPhoto> call, Response<ResponseUploadPhoto> response) {
                if (response.isSuccessful()) {
                    SharedPreferenceManager.setSomeStringValue(Constantes.PREF_PHOTOURL, response.body().getFilename());
                    photoProfile.setValue(response.body().getFilename());

                } else {
                    Toast.makeText(MyApp.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseUploadPhoto> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT);
            }
        });
    }



}
