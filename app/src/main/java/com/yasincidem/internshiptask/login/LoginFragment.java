package com.yasincidem.internshiptask.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.edit.EditActivity;

import java.util.Objects;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;


public class LoginFragment extends Fragment implements LoginContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LoginContract.Presenter mPresenter;
    private ProgressBar progressBar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SignInButton signInButtonGoogle;
    private OnFragmentInteractionListener mListener;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signInButtonGoogle = view.findViewById(R.id.google_sign_in_button);
//        logInButtonFacebook = view.findViewById(R.id.facebook_login);
        signInButtonGoogle.setSize(SignInButton.SIZE_WIDE);

        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signInGoogle(LoginFragment.this);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStartGoogle(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResultGoogle(this, requestCode, resultCode, data);
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
    public void hideLoginOptions() {

    }


    @Override
    public void startEditActivity(GoogleSignInAccount account) {
        Intent i = new Intent(this.getActivity(), EditActivity.class);
        i.putExtra("id", account.getId());
        i.putExtra("name", account.getDisplayName());
        i.putExtra("email", account.getEmail());
        i.putExtra("img_url", account.getPhotoUrl() == null ? "" : account.getPhotoUrl().toString());
        Objects.requireNonNull(this.getActivity()).finish();
        startActivity(i);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getAppContext(), message ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getAppContext() {
        return Objects.requireNonNull(this.getActivity()).getApplicationContext();
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        if (mPresenter.isSignedInWithGoogle(LoginFragment.this.getContext())) {
            Objects.requireNonNull(this.getActivity()).finish();
            startActivity(new Intent(this.getActivity(), EditActivity.class));
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
