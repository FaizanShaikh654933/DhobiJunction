package com.example.dhobijunction.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.SubCategoryPageAdapter;
import com.example.dhobijunction.model.CategoryModel;
import com.example.dhobijunction.model.SubCategoryModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryFragment extends Fragment {

    String url = "http://dhobijunction.biz/api/sub_category.php";
    List<SubCategoryModel> list=new ArrayList<>();
    CategoryModel model;
    TabLayout tabLayout;
    ViewPager viewPager;

    public SubCategoryFragment(CategoryModel categoryModel) {
        this.model=categoryModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sub_category_fragment,container,false);

        viewPager=view.findViewById(R.id.vp);
        tabLayout=view.findViewById(R.id.tl);

        FirebaseFirestore.getInstance().collection("SUB_CATEGORY")
                .whereEqualTo("cId",model.getcId()).orderBy("sId", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e!=null){
                    Toast.makeText(getActivity(),e.getMessage()+ "", Toast.LENGTH_SHORT).show();
                }
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    list = queryDocumentSnapshots.toObjects(SubCategoryModel.class);
                    SubCategoryPageAdapter subCategoryPageAdapter = new SubCategoryPageAdapter(getChildFragmentManager(), list);
                    viewPager.setAdapter(subCategoryPageAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                }
                else{
                    Toast.makeText(getActivity(),"Sub Category Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
/*
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("subcat");

                    list=new Gson().fromJson(jsonArray.toString(),new TypeToken<List<SubCategoryModel>>(){}.getType());

                    SubCategoryPageAdapter subCategoryPageAdapter = new SubCategoryPageAdapter(getChildFragmentManager(),list);
                    viewPager.setAdapter(subCategoryPageAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("category_id",model.getcId());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);

 */
        return view;
    }
}
