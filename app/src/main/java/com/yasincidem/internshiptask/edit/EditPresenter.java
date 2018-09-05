package com.yasincidem.internshiptask.edit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class EditPresenter implements EditContract.Presenter {

    private final EditContract.View mEditView;

    @Nullable
    private String mEditId;

    public EditPresenter(@NonNull EditContract.View editView, @Nullable String editId) {
        this.mEditView = checkNotNull(editView, "editView cannot be null!");
        this.mEditId = editId;
        mEditView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addDataToFirebase(Context context, String... strings) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null){
            try {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String userId = account.getId();
                DatabaseReference myRef = database.getReference().child("users").child(userId);
                myRef.child("img_url").setValue(strings[0]);
                myRef.child("id").setValue(strings[1]);
                myRef.child("name").setValue(strings[2]);
                myRef.child("email").setValue(strings[3]);
                myRef.child("profession").setValue(strings[4]);
                myRef.child("birthdate").setValue(strings[5]);
                myRef.child("phone").setValue(strings[6]);
                mEditView.showToast("Your data is written to Firebase successfully");

            }catch (Exception e) {
                mEditView.showToast("An error occured. Please Try again later");
            }

        }
        else
            mEditView.showToast("An error occured. Please Try again later");
    }

    @Override
    public void getDataFromFirebase(Context context) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = account.getId();
        DatabaseReference myRef = database.getReference().child("users").child(userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mEditView.invokeImageUrl(dataSnapshot.child("img_url").getValue(String.class));
                mEditView.invokeName(dataSnapshot.child("name").getValue(String.class));
                mEditView.invokeEmail(dataSnapshot.child("email").getValue(String.class));
                mEditView.invokeProfession(dataSnapshot.child("profession").getValue(String.class));
                mEditView.invokeBirthdate(dataSnapshot.child("birthdate").getValue(String.class));
                mEditView.invokePhone(dataSnapshot.child("phone").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                mEditView.setDataFromProvider();
            }
        });
    }
}
