package com.androidapp.hashkey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHashKey();
    }

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(packageInfo == null) {
            Log.e("HashKey", "HashKey:null");
        }
        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("HashKey", Base64.encodeToString(md.digest(),Base64.DEFAULT ));
            } catch (NoSuchAlgorithmException e) {
                Log.e("HashKey", "Hash Error.signature=" + signature, e);
            }
        }
    }
}