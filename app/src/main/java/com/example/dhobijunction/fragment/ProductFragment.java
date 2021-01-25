package com.example.dhobijunction.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhobijunction.adapter.OnAddToCartListner;
import com.example.dhobijunction.adapter.ProductAdapter;
import com.example.dhobijunction.adapter.ProductListAdapter;
import com.example.dhobijunction.R;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.ProModel;
import com.example.dhobijunction.model.ProductModel;
import com.example.dhobijunction.model.SubCategoryModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ProductFragment extends Fragment implements OnAddToCartListner {
    String url = "http://dhobijunction.biz/api/product.php";
    RecyclerView recyclerView;
    List<ProductModel> list;
    SubCategoryModel model;
    ProductAdapter adapter;

    public ProductFragment(SubCategoryModel subCategoryModel) {
        this.model = subCategoryModel;
    }

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        preferences = getActivity().getSharedPreferences("Users", 0);

        Query query = FirebaseFirestore.getInstance().collection("PRODUCTS")
                .whereEqualTo("sId", model.getsId());

        FirestoreRecyclerOptions<ProModel> rvOptions = new FirestoreRecyclerOptions.Builder<ProModel>()
                .setQuery(query, ProModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(this, rvOptions, this);
        recyclerView.setAdapter(adapter);
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("product");
                    list = new Gson().fromJson(jsonArray.toString(),new TypeToken<List<ProductModel>>(){}.getType());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    ProductListAdapter productListAdapter = new ProductListAdapter(getActivity(),list);
                    recyclerView.setAdapter(productListAdapter);

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
                Map<String,String> params = new HashMap<>();
                params.put("subcat",model.getsId());
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);

         */
        return view;
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

    @Override
    public void addToCart(ProModel product, String proQty) {
        CartModel model = new CartModel();
        model.setQty(proQty);
        model.setTitle(product.getTitle());
        model.setPrice(product.getPrice());
        model.setpId(product.getpId());
        model.setImage(product.getImage());
        model.setsId(product.getsId());
        model.setTotal(String.valueOf(Integer.parseInt(product.getPrice()) * Integer.parseInt(proQty)));

        FirebaseFirestore.getInstance().collection("USERS")
                .document(preferences.getString("userMobile", ""))
                .collection("USERCART")
                .add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Map<String, Object> map = new HashMap<>();
                map.put("cartItemId", documentReference.getId());
                FirebaseFirestore.getInstance().collection("USERS")
                        .document(preferences.getString("userMobile", ""))
                        .collection("USERCART").document(documentReference.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
                Toast.makeText(getActivity(), "Added to cart.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
