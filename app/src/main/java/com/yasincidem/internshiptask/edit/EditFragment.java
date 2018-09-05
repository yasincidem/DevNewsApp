package com.yasincidem.internshiptask.edit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.profile.ProfileActivity;

import java.util.Objects;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditFragment extends Fragment implements EditContract.View {

    private static final String ID_PARAM = "id";
    private static final String NAME_PARAM = "name";
    private static final String EMAIL_PARAM = "email";
    private static final String IMG_URL_PARAM = "img_url";
    private EditContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private String id;
    private String name;
    private String email;
    private String img_url;
    private Button saveButton;
    private Button goProfileButton;
    private String idField;
    private String nameField;
    private String emailField;
    private String professionField;
    private String birthdateField;
    private String phoneField;
    private String imageUrlField;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText professionEditText;
    private EditText birthdateEditText;
    private EditText phoneEditText;
    private EditText imageUrlEditText;


    public EditFragment() {

    }

    public static EditFragment newInstance() {
        return new EditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit, container, false);
        nameEditText = view.findViewById(R.id.name);
        emailEditText = view.findViewById(R.id.email);
        professionEditText = view.findViewById(R.id.profession);
        birthdateEditText = view.findViewById(R.id.birthdate);
        phoneEditText = view.findViewById(R.id.phone);
        imageUrlEditText = view.findViewById(R.id.image_url);



        saveButton = view.findViewById(R.id.save_button);
        goProfileButton = view.findViewById(R.id.go_profile_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUrlField = imageUrlEditText.getText().toString();
                nameField = nameEditText.getText().toString();
                emailField = emailEditText.getText().toString();
                professionField = professionEditText.getText().toString();
                birthdateField = birthdateEditText.getText().toString();
                phoneField = phoneEditText.getText().toString();
                mPresenter.addDataToFirebase( EditFragment.this.getContext(), imageUrlField, idField, nameField, emailField, professionField, birthdateField, phoneField);

            }
        });
        goProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfileActivity();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.getDataFromFirebase(EditFragment.this.getContext());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getAppContext(), message ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startProfileActivity() {
        startActivity(new Intent(this.getActivity(), ProfileActivity.class));
    }

    @Override
    public Context getAppContext() {
        return Objects.requireNonNull(this.getActivity()).getApplicationContext();
    }

    @Override
    public void invokeImageUrl(String url) {
        imageUrlEditText.setText(url);
    }

    @Override
    public void invokeName(String name) {
        nameEditText.setText(name);
    }

    @Override
    public void invokeEmail(String email) {
        emailEditText.setText(email);
    }

    @Override
    public void invokeProfession(String profession) {
        professionEditText.setText(profession);
    }

    @Override
    public void invokeBirthdate(String birthdate) {
        birthdateEditText.setText(birthdate);
    }

    @Override
    public void invokePhone(String phone) {
        phoneEditText.setText(phone);
    }

    @Override
    public void setDataFromProvider() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {

            nameEditText.setText(extras.getString("name"));
            emailEditText.setText(extras.getString("email"));
            imageUrlEditText.setText(Objects.equals(extras.getString("img_url"), "") ? "https://www.mautic.org/media/images/default_avatar.png" : extras.getString("img_url"));
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
