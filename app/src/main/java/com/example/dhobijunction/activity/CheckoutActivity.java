package com.example.dhobijunction.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.CartAdapter;
import com.example.dhobijunction.adapter.CheckoutAdapter;
import com.example.dhobijunction.databinding.ActivityCheckoutBinding;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.CheckoutModel;
import com.example.dhobijunction.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    SharedPreferences pref;
    ActivityCheckoutBinding screen;
    String mobile = "";
    String total = "";
    CheckoutAdapter adapter;
    List<CheckoutModel> list;
    FusedLocationProviderClient fusedLocationProviderClient;

    String[] time = new String[]{"9:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-1:00PM","1:00PM-2:00PM","2:00PM-3:00PM","3:00PM-4:00PM","4:00PM-5:00PM","5:00PM-6:00PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        pref = getSharedPreferences("Users", 0);
        mobile = pref.getString("userMobile", "");
        List<String> timelist = new ArrayList<>(Arrays.asList(time));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, timelist);
        screen.checkoutSpinner.setAdapter(spinnerArrayAdapter);
        screen.tvCurrentLoction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                } else {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            Geocoder geocoder = new Geocoder(CheckoutActivity.this);
                            try {
                                screen.checkoutAddress.setText(geocoder.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1)
                                        .get(0).getAddressLine(0));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        screen.checkoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = screen.checkoutName.getText().toString().trim();
                String Email = screen.checkoutEmail.getText().toString().trim();
                String Phone = screen.checkoutMobilenumber.getText().toString().trim();
                String Address = screen.checkoutAddress.getText().toString().trim();

                if (TextUtils.isEmpty(Name)) {
                    screen.checkoutName.setError("Enter Your Name");

                }
                if (TextUtils.isEmpty(Email)) {
                    screen.checkoutEmail.setError("Enter Your Email");

                }
                if (TextUtils.isEmpty(Phone)) {
                    screen.checkoutMobilenumber.setError("Enter Your PhoneNumber");

                }
                if (TextUtils.isEmpty(Address)) {
                    screen.checkoutAddress.setError("Enter Your Address");

                }
                if (Phone.length() < 10) {
                    screen.checkoutMobilenumber.setError("Phone Number Must be 10 number ");


                } else {
                    OrderModel model = new OrderModel();
                    model.setName(screen.checkoutName.getText().toString());
                    model.setEmail(screen.checkoutEmail.getText().toString());
                    model.setNumber(screen.checkoutMobilenumber.getText().toString());
                    model.setAddress(screen.checkoutAddress.getText().toString());
                    model.setTotal(total);
                    model.setDeliverTime(screen.checkoutSpinner.getSelectedItem().toString());
                    model.setTimestamp(null);
                    if (screen.radiogroup1.getCheckedRadioButtonId()== R.id.cash) {
                        FirebaseFirestore.getInstance().collection("USERS")
                                .document(mobile).collection("USERCART").addSnapshotListener((value, error) -> {
                            if (value != null && !value.isEmpty()) {
                                list = value.toObjects(CheckoutModel.class);
                                model.setModelList(list);
                                model.setPayMentMethod("Cash On Dlivery");
                                FirebaseFirestore.getInstance().collection("USERS").document(mobile).collection("ORDERS")
                                        .add(model).addOnSuccessListener(doc -> {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("orderId", doc.getId());
                                    doc.update(map).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            map.clear();
                                            Toast.makeText(CheckoutActivity.this, "Order Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CheckoutActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                });
                            }
                            if (error != null) {
                                Toast.makeText(CheckoutActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (screen.radiogroup1.getCheckedRadioButtonId()==R.id.online) {


                        FirebaseFirestore.getInstance().collection("USERS")
                                .document(mobile).collection("USERCART").addSnapshotListener((value, error) -> {
                            if (value != null && !value.isEmpty()) {
                                list = value.toObjects(CheckoutModel.class);
                                model.setModelList(list);
                                model.setPayMentMethod("OnlinePayment");

                                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                                intent.putExtra("total", total);
                                intent.putExtra("order", model);
//                                intent.putExtra("name",  screen.checkoutName.getText());
//                                intent.putExtra("email", screen.checkoutEmail.getText());
//                                intent.putExtra("number", screen.checkoutMobilenumber.getText());
//                                intent.putExtra("Address",screen.checkoutAddress);
//
                                startActivity(intent);
                            }
                            if (error != null) {
                                Toast.makeText(CheckoutActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
//                FirebaseFirestore.getInstance().collection("USERS").document(pref.getString("userMobile", "")).collection("Order").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("cartItemId", documentReference.getId());
//                        FirebaseFirestore.getInstance().collection("USERS")
//                                .document(pref.getString("userMobile", ""))
//                                .collection("Order").document(documentReference.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Check_Out_Activity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


            }
        });

        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART");
        FirestoreRecyclerOptions<CheckoutModel> rvOptions = new FirestoreRecyclerOptions.Builder<CheckoutModel>()
                .setQuery(query, CheckoutModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        screen.checkoutRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new CheckoutAdapter(this, rvOptions, this);
        screen.checkoutRecyclerview.setAdapter(adapter);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}