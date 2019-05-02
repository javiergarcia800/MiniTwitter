package com.example.minitwitter.ui.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.data.ProfileViewModel;
import com.example.minitwitter.R;
import com.example.minitwitter.retrofit.request.RequestUserProfile;
import com.example.minitwitter.retrofit.response.ResponseUserProfile;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    ImageView ivAvatar;
    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etWebsite;
    EditText etDescripcion;
    Button btnSave;
    Button btnChangePassword;

    boolean loadingData = true;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        ivAvatar = v.findViewById(R.id.imageViewAvatar);
        etUsername = v.findViewById(R.id.editTextUsername);
        etEmail = v.findViewById(R.id.editTextEmail);
        etPassword = v.findViewById(R.id.editTextCurrentPassword);
        etWebsite = v.findViewById(R.id.editTextWebsite);
        etDescripcion = v.findViewById(R.id.editTextDescripcion);

        btnSave = v.findViewById(R.id.buttonSave);
        btnChangePassword = v.findViewById(R.id.buttonChangePassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String descripcion = etDescripcion.getText().toString();
                String website = etWebsite.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty()) {
                    etUsername.setError("El nombre de usuario es requerido");
                } else if (email.isEmpty()) {
                    etEmail.setError("El email de usuario es requerido");
                } else if (password.isEmpty()) {
                    etPassword.setError("La contraseña es requerida");
                } else {
                    RequestUserProfile requestUserProfile = new RequestUserProfile(username, email, descripcion, website, password);
                    profileViewModel.updateProfile(requestUserProfile);
                    Toast.makeText(getActivity(), "Enviando información al servidor", Toast.LENGTH_SHORT);
                    btnSave.setEnabled(false);
                }


            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click on save", Toast.LENGTH_SHORT);
            }
        });

        ivAvatar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                
            }
        });

        // ViewModel
        profileViewModel.userProfile.observe(getActivity(), new Observer<ResponseUserProfile>(){

            @Override
            public void onChanged(@Nullable ResponseUserProfile responseUserProfile) {
                loadingData = false;
                etUsername.setText(responseUserProfile.getUsername());
                etEmail.setText(responseUserProfile.getEmail());
                etWebsite.setText(responseUserProfile.getWebsite());
                etDescripcion.setText(responseUserProfile.getDescripcion());

                if (!responseUserProfile.getPhotoUrl().isEmpty()) {
                    Glide.with(getActivity())
                            .load(Constantes.API_MINITWITTER_FILES_URL + responseUserProfile.getPhotoUrl())
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .skipMemoryCache(true)
                            .into(ivAvatar);
                }

                if (!loadingData) {
                    btnSave.setEnabled(true);
                    Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_SHORT);
                }
            }
        });

        return v;
    }



}
