package com.example.minitwitter.ui.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitwitter.R;
import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.common.SharedPreferenceManager;
import com.example.minitwitter.retrofit.MiniTwitterClient;
import com.example.minitwitter.retrofit.MiniTwitterService;
import com.example.minitwitter.retrofit.request.RequestLogin;
import com.example.minitwitter.retrofit.response.ResponseAuth;
import com.example.minitwitter.ui.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView tvgoSignUp;

    EditText etEmail;
    EditText etPassword;

    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        retrofitInit();
        findViews();
        events();

    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }


    private void findViews() {
        btnLogin = findViewById(R.id.buttonLogin);
        tvgoSignUp = findViewById(R.id.textViewGoSignUp);

        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        btnLogin.setOnClickListener(this);
        tvgoSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.buttonLogin:
                doToLogin();
                break;
            case R.id.textViewGoSignUp:
                goToSignUp();
                break;
        }
    }

    private void doToLogin() {

        Log.i("MINITWITTER", "Entra a doToLogin");

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        Log.i("MINITWITTER", "Antes de if...");

        if (email.isEmpty()) {
            etEmail.setError("El email es requerido.");
        } else if (password.isEmpty()) {
            etPassword.setError("La contraseña es requerida");
        } else {

            Log.i("MINITWITTER", "Entra al else");

            RequestLogin requestLogin = new RequestLogin(email, password);

            Log.i("MINITWITTER", "Amtes de login");

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);

            Log.i("MINITWITTER", "Despues de login");

            call.enqueue(new Callback<ResponseAuth>(){

                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {

                    Log.i("MINITWITTER", "Entra onResponse");

                    if (response.isSuccessful()) {

                        Log.i("MINITWITTER", "response.isSuccessful()");

                        Toast.makeText(MainActivity.this, "Sesión iniciada correctamente.", Toast.LENGTH_SHORT);


                        SharedPreferenceManager.setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                        SharedPreferenceManager.setSomeStringValue(Constantes.PREF_USERNAME, response.body().getUsername());
                        SharedPreferenceManager.setSomeStringValue(Constantes.PREF_EMAIL, response.body().getEmail());
                        SharedPreferenceManager.setSomeStringValue(Constantes.PREF_PHOTOURL, response.body().getPhotoUrl());
                        SharedPreferenceManager.setSomeStringValue(Constantes.PREF_CREATED, response.body().getCreated());
                        SharedPreferenceManager.setSomeBooleanValue(Constantes.PREF_ACTIVE, response.body().getActive());

                        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(i);
                        // Se destruye este activity para que no se pueda volver.
                        finish();
                    } else {

                        Log.i("MINITWITTER", "else");

                        Toast.makeText(MainActivity.this, "Algo fue mal, revise sus datos de acceso", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {

                    Log.i("MINITWITTER", "Entra a onFailure");

                    Toast.makeText(MainActivity.this, "Problemas de conexión. Inténtelo de nuevo.", Toast.LENGTH_SHORT);
                }
            });
        }


    }

    private void goToSignUp() {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
    }
}
