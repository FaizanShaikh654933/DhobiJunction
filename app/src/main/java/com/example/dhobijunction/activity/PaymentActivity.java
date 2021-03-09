package com.example.dhobijunction.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dhobijunction.R;
import com.example.dhobijunction.model.OrderModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    String key = "ub4zyyCk";
    String txnid = "1";
    String amount = "";
    String productinfo = "Dhobi Junction";
    String firstname = "Faizan";
    String email = "faizanshaikh654933@gmail.com";
    String salt = "lmBlYqpa5L";
    String merchantId = "7380570";
    String serverCalculatedHash;
    String userMobile;
    OrderModel orderModel;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();
        amount = getIntent().getStringExtra("total");
        preferences = getSharedPreferences("Users", 0);
        userMobile = preferences.getString("userMobile", "");
        orderModel = (OrderModel) getIntent().getSerializableExtra("order");
        String hashSequence = key + "|" + txnid + "|" + amount + "|" + productinfo + "|" + firstname + "|" + email + "|||||||||||" + salt;
        serverCalculatedHash = hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone("7405719399")                                           // User Phone number
                .setProductName("Dhobi Junction")                   // Product Name or description
                .setFirstName("Faizan")                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")                    // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(key)                        // Merchant key
                .setMerchantId(merchantId);

        PayUmoneySdkInitializer.PaymentParam paymentParam = null;
        try {
            paymentParam = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
//set the hash
        paymentParam.setMerchantHash(serverCalculatedHash);
        // Invoke the following function to open the checkout page.
        PayUmoneyFlowManager.startPayUMoneyFlow(
                paymentParam,
                this,
                R.style.AppTheme_default,
                true);
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    FirebaseFirestore.getInstance().collection("USERS").document(userMobile).collection("ORDERS")
                            .add(orderModel).addOnSuccessListener(doc -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("orderId", doc.getId());
                        doc.update(map).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                map.clear();
                                Toast.makeText(this, "Order Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    });

                } else{
                    Toast.makeText(this, "TRansaction Failed", Toast.LENGTH_SHORT).show();
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            } else {
                Log.d("TAG", "Both objects are null!");
            }
        }
    }
}