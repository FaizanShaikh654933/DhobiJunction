package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.CategoryAdapter;
import com.example.admin.adapter.DeliveryboyAdapter;
import com.example.admin.adapter.OffersAdapter;
import com.example.admin.adapter.SubCategoryAdapter;
import com.example.admin.databinding.ActivityOffersBinding;
import com.example.admin.model.CategoryModel;
import com.example.admin.model.DeliveryModel;
import com.example.admin.model.OffersModel;
import com.example.admin.model.SubCategoryModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffersActivity extends AppCompatActivity {
    ActivityOffersBinding binding;
    OffersModel model;
    OffersAdapter adapter;
    List<OffersModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.promeExdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popclander();
            }

        });

        /* binding.btnPromcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map=new HashMap<>();
                map.put("title",binding.promeTitle.getText().toString());
                map.put("price",binding.promePrice.getText().toString());
                map.put("code",binding.promeCode.getText().toString());
                map.put("date",binding.promeExdate.getText().toString());
                //map.put("offerId",model.getOfferId());
                FirebaseFirestore.getInstance().collection("OFFERS").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(OffersActivity.this, "Offer Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Query query = FirebaseFirestore.getInstance().collectionGroup("OFFERS");
        FirestoreRecyclerOptions<OffersModel> rvOptions = new FirestoreRecyclerOptions.Builder<OffersModel>()
                .setQuery(query, OffersModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.offersRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new OffersAdapter(this, rvOptions, this);
        binding.offersRecyclerview.setAdapter(adapter);

*/
        binding.btnPromcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Code = binding.promeCode.getText().toString().trim();
                String Date = binding.promeExdate.getText().toString().trim();
                String Price = binding.promePrice.getText().toString().trim();
                String Title = binding.promeTitle.getText().toString().trim();

                if (TextUtils.isEmpty(Code)) {
                    binding.promeCode.setError("Enter Code");

                }
                if (TextUtils.isEmpty(Date)) {
                    binding.promeExdate.setError("Enter Date");

                }
                if (TextUtils.isEmpty(Price)) {
                    binding.promePrice.setError("Enter Price");

                }
                if (TextUtils.isEmpty(Title)) {
                    binding.promeTitle.setError("Enter Title");

                }
                else {
                    model = new OffersModel();
                    model.setCode("code", binding.promeCode.getText().toString());
                    model.setDate("date", binding.promeExdate.getText().toString());
                    model.setPrice("price", binding.promePrice.getText().toString());
                    model.setTitle("title", binding.promeTitle.getText().toString());

                /*FirebaseFirestore.getInstance().collection("OFFERS").add(model)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map map=new HashMap();
                        map.put("offerId",documentReference.getId());
                        documentReference.update(map).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(OffersActivity.this, "Add Offer", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });*/
                    FirebaseFirestore.getInstance().collection("OFFERS").add(model)
                            .addOnSuccessListener(documentReference -> {
                                String docId = documentReference.getId();
                                Map<String, Object> map = new HashMap<>();
                                map.put("offerId", docId);
                                documentReference.update(map).addOnSuccessListener(aVoid -> {
                                    Toast.makeText(OffersActivity.this, "Offers Added", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(OffersActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }).addOnFailureListener(e -> {
                        Toast.makeText(OffersActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
        FirebaseFirestore.getInstance().collection("OFFERS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    list = value.toObjects(OffersModel.class);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OffersActivity.this);
                    binding.offersRecyclerview.setLayoutManager(linearLayoutManager);
                    adapter = new OffersAdapter(OffersActivity.this, list);
                    binding.offersRecyclerview.setAdapter(adapter);
                }
            }
        });

    }

    private void popclander() {
        Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int mMonth=c.get(Calendar.MONTH);
        int mDay=c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(OffersActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat format=new DecimalFormat("00");
                String pDay=format.format(dayOfMonth);
                String pMonth=format.format(monthOfYear);
                String pYear=""+(year);
                String pDate=pDay+"/"+pMonth+"/"+pYear;
                binding.promeExdate.setText(pDate);


            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
