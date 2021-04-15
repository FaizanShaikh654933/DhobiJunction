package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OfferAdapter;
import com.example.dhobijunction.databinding.ActivityOffersBinding;
import com.example.dhobijunction.model.OfferModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OffersActivity extends AppCompatActivity {
    ActivityOffersBinding activityOffersBinding;
    OfferAdapter offerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activityOffersBinding= ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(activityOffersBinding.getRoot());
        Query query = FirebaseFirestore.getInstance().collection("OFFERS");
        FirestoreRecyclerOptions<OfferModel> rvOptions = new FirestoreRecyclerOptions.Builder<OfferModel>()
                .setQuery(query, OfferModel.class).build();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        activityOffersBinding.offerRv.setLayoutManager(linearLayoutManager);
        offerAdapter =new OfferAdapter(this,rvOptions,this);
        activityOffersBinding.offerRv.setAdapter(offerAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        offerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        offerAdapter.stopListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}