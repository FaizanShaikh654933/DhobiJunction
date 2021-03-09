package com.example.admin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.ProductAdapter;
import com.example.admin.adapter.SubCategoryAdapter;
import com.example.admin.model.ProductModel;
import com.example.admin.model.SubCategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity {
    Button b1,b2;
    ImageView imageView;
    EditText e1,e2;
    Spinner spinner;
    Uri filepath;
    ProductModel model;
    private final int PICK_IMAGE_REQUEST = 71;
    List<ProductModel> modelList;
    List<SubCategoryModel> list;
    FirebaseStorage storage;
    StorageReference storageReference;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<String> SubCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        b1 = findViewById(R.id.pr_bt1);
        b2 = findViewById(R.id.pr_bt2);
        e1 = findViewById(R.id.pr_e1);
        e2 = findViewById(R.id.pr_e2);
        spinner = findViewById(R.id.pr_sp);
        recyclerView = findViewById(R.id.pr_rv);
        imageView = findViewById(R.id.pr_imageView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }

            private void chooseImage() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        FirebaseFirestore.getInstance().collection("SUB_CATEGORY").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && !value.isEmpty()) {
                    list =value.toObjects(SubCategoryModel.class);
                    for (SubCategoryModel subCategoryModel:list){
                        SubCategoryList.add(subCategoryModel.getTitle());
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ProductActivity.this,R.layout.support_simple_spinner_dropdown_item,SubCategoryList);
                    spinner.setAdapter(arrayAdapter);

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                model=new ProductModel();

                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTitle()==spinner.getSelectedItem().toString()){
                        model.setsId(list.get(i).getsId());
                        model.setTitle(e1.getText().toString());
                        model.setPrice(e2.getText().toString());
                        final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();


                        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                        ref.putFile(filepath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                model.setImage(  task.getResult().toString());

                                                FirebaseFirestore.getInstance().collection("PRODUCTS").add(model).addOnSuccessListener(documentReference -> {
                                                    String docId = documentReference.getId();
                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("pId", docId);
                                                    documentReference.update(map).addOnSuccessListener(aVoid -> {
                                                        if(filepath != null)
                                                        {
                                                            final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                                                            progressDialog.setTitle("Uploading...");
                                                            progressDialog.show();
                                                            progressDialog.dismiss();

                                                        }
                                                    });
                                                });
                                            }
                                        });

                                        // Continue with the task to get the download URL

                                        progressDialog.dismiss();

                                        Toast.makeText(ProductActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(ProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ProductActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                .getTotalByteCount());

                                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                    }
                                });
                    }
                }
            }
        });

        FirebaseFirestore.getInstance().collection("PRODUCTS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    modelList=value.toObjects(ProductModel.class);
                    adapter=new ProductAdapter(ProductActivity.this,modelList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ProductActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.getData() != null )
            {
                filepath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    imageView.setImageBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
