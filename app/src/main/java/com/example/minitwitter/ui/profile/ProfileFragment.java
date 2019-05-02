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
import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.data.ProfileViewModel;
import com.example.minitwitter.R;
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
                Toast.makeText(getActivity(), "Click on save", Toast.LENGTH_SHORT);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click on save", Toast.LENGTH_SHORT);
            }
        });

        // ViewModel
        profileViewModel.userProfile.observe(getActivity(), new Observer<ResponseUserProfile>(){

            @Override
            public void onChanged(@Nullable ResponseUserProfile responseUserProfile) {
                etUsername.setText(responseUserProfile.getUsername());
                etEmail.setText(responseUserProfile.getEmail());
                etWebsite.setText(responseUserProfile.getWebsite());
                etDescripcion.setText(responseUserProfile.getDescripcion());
                if (!responseUserProfile.getPhotoUrl().isEmpty()) {
                    Glide.with(getActivity())
                            .load(Constantes.API_MINITWITTER_FILES_URL + responseUserProfile.getPhotoUrl())
                            .into(ivAvatar);
                }
            }
        });

        return v;
    }



}
