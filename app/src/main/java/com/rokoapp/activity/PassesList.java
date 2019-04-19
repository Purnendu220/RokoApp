package com.rokoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.rokoapp.R;
import com.rokoapp.adapter.PassesAdapter;
import com.rokoapp.manageApi.ApiManager;
import com.rokoapp.manageApi.FailureCodes;
import com.rokoapp.manageApi.ResponseProgressListener;
import com.rokoapp.model.response.PassModel;

import java.util.ArrayList;
import java.util.List;

public class PassesList extends AppCompatActivity implements ResponseProgressListener {

    private Toolbar toolbar;
    private RecyclerView passesRecycler;
    private List<PassModel> passListing = new ArrayList<>();
    private PassesAdapter passesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passes_list);

        initViews();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Roko Passes");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        passesRecycler.setHasFixedSize(true);
        passesAdapter = new PassesAdapter(PassesList.this, passListing);
        passesRecycler.setLayoutManager(new LinearLayoutManager(PassesList.this, LinearLayoutManager.VERTICAL, false));
        passesRecycler.setItemAnimator(new DefaultItemAnimator());
        passesRecycler  .setAdapter(passesAdapter);

        ApiManager.getPassesData(PassesList.this, passListing, PassesList.this);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        passesRecycler = findViewById(R.id.passesRecycler);

    }

    @Override
    public void onResponseInProgress() {

    }

    @Override
    public void onResponseCompleted(int i) {
        if (i==1){
            passesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseCompletedWithData(int i, Object data) {

    }

    @Override
    public void onResponseFailed(FailureCodes code) {
        switch (code) {
            case SERVER_ERROR:
                Toast.makeText(this, "Server Busy, Try Again Later.", Toast.LENGTH_SHORT).show();
                break;
            case NO_INTERNET:
                Toast.makeText(this, "No internet ", Toast.LENGTH_SHORT).show();
                break;
            case RESPONSE_NULL:
                Toast.makeText(this, "Unable to connect; please Try Again.", Toast.LENGTH_SHORT).show();
                break;
            case STATUS_0:
                Toast.makeText(this, "Kindly Try Again.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(EditProfile.this, Home.class));
        startActivity(new Intent(PassesList.this, MapsActivity.class));
        PassesList.this.finish();
    }
}
