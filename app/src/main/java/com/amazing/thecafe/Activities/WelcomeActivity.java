package com.amazing.thecafe.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.DTO.StaffDTO;
import com.amazing.thecafe.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //gic...
//        StaffDAO nhanVienDAO = new StaffDAO(this);
//        StaffDTO nhanVienDTO = new StaffDTO();
//        nhanVienDTO.setHOTENNV("admin");
//        nhanVienDTO.setTENDN("admin");
//        nhanVienDTO.setEMAIL("admin@hjfsd.svho");
//        nhanVienDTO.setSDT("1234567890");
//        nhanVienDTO.setMATKHAU("123456");
//        nhanVienDTO.setGIOITINH("Nam");
//        nhanVienDTO.setNGAYSINH("01/01/2000");
//
//        nhanVienDAO.ThemNhanVien(nhanVienDTO);
        //gic end...... =========================================================================================================

    }

    //login
    public void callLoginFromWel(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_login), "transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    //signin
    public void callSignUpFromWel(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_signup), "transition_signup");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}