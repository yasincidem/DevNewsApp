package com.yasincidem.internshiptask.edit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yasincidem.internshiptask.R;
import com.yasincidem.internshiptask.util.ActivityUtils;

public class EditActivity extends AppCompatActivity implements EditFragment.OnFragmentInteractionListener{


    private String id;
    private String name;
    private String email;
    private String img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditFragment editFragment = (EditFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_fragment);

        if (editFragment == null) {
            editFragment = EditFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    editFragment, R.id.edit_fragment);
        }

        new EditPresenter(
                editFragment,
                "edit"
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
