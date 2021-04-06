package com.example.dhobijunction.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.CategoryPagerAdapter;
import com.example.dhobijunction.model.CategoryModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    String url = "http://dhobijunction.biz/api/category.php";

    List<CategoryModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    TabLayout tabLayout;
    ViewPager viewPager;
    CategoryModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_product);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        FirebaseFirestore.getInstance().collection("CATEGORY")
                .orderBy("cId", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(ProductActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    list = queryDocumentSnapshots.toObjects(CategoryModel.class);
                    CategoryPagerAdapter adapter = new CategoryPagerAdapter(getSupportFragmentManager(), list);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                } else {
                    Toast.makeText(ProductActivity.this, "Category Not Found", Toast.LENGTH_SHORT).show();

                }
            }
        });

/*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    list=new Gson().fromJson(jsonArray.toString(),new TypeToken<List<CategoryModel>>(){}.getType());
                    CategoryPagerAdapter adapter=new CategoryPagerAdapter(getSupportFragmentManager(),list);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
        queue.add(stringRequest);

 */
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}