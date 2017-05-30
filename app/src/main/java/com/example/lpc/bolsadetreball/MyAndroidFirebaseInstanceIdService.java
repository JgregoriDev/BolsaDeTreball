package com.example.lpc.bolsadetreball;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

/**
 * Created by lpc on 2/05/17.
 */

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "IDToken";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Jack","Token "+refreshedToken);
        InsertarTokenFirebase(refreshedToken);
    }
    public void InsertarTokenFirebase(String Token){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Tokens");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Token", Token);
        databaseReference.push().setValue(hashMap);
    }

}
