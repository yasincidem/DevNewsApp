package com.yasincidem.internshiptask.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.edit.EditActivity;
import com.yasincidem.internshiptask.login.LoginActivity;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;


public class ProfileFragment extends Fragment implements ProfileContract.View{


    private ProfileContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView professionTextView;
    private TextView birthdateTextView;
    private TextView phoneTextView;
    private ImageView imageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.profile_name);
        emailTextView = view.findViewById(R.id.profile_email);
        professionTextView = view.findViewById(R.id.profile_profession);
        birthdateTextView = view.findViewById(R.id.profile_birthdate);
        phoneTextView = view.findViewById(R.id.profile_phone);
        imageView = view.findViewById(R.id.profile_image);
        view.findViewById(R.id.profile_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity();
            }
        });

        view.findViewById(R.id.profile_logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPresenter.logout(ProfileFragment.this.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("An error occured");
                }
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
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.getDataFromFirebase(ProfileFragment.this.getContext());
    }

    @Override
    public void fillImageView(String url) {
        if (url != null)
            if (!url.equals(""))
                Picasso.get().load(url).fit().into(imageView);
    }

    @Override
    public void fillNameField(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void fillEmailField(String email) {
        emailTextView.setText(email);
    }

    @Override
    public void fillProfessionField(String profession) {
        professionTextView.setText(profession);
    }

    @Override
    public void fillBirthdateField(String birthdate) {
        birthdateTextView.setText(birthdate);
    }

    @Override
    public void fillPhoneField(String phone) {
        phoneTextView.setText(phone);
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(ProfileFragment.this.getActivity(), LoginActivity.class));
    }

    @Override
    public void startEditActivity() {
        Intent intent = new Intent(ProfileFragment.this.getActivity(), EditActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getContext(), message ,Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
