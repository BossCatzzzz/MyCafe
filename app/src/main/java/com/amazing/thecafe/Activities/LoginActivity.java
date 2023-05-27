package com.amazing.thecafe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button BTN_login_DangNhap, BTN_login_DangKy;
    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
    StaffDAO nhanVienDAO;
    CheckBox savelogin;
    String tenDN = "";
    String matKhau = "";
    SharedPreferences sharedPreferences_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TXTL_login_TenDN = findViewById(R.id.txtl_login_TenDN);
        TXTL_login_TenDN.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    TXTL_login_MatKhau.getEditText().setText("");
                    TXTL_login_TenDN.getEditText().setText("");
                }
            }
        });

        TXTL_login_MatKhau = findViewById(R.id.txtl_login_MatKhau);
        TXTL_login_MatKhau.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    TXTL_login_MatKhau.getEditText().setText("");
                }
            }
        });

        BTN_login_DangNhap = findViewById(R.id.btn_login_DangNhap);
        BTN_login_DangKy = findViewById(R.id.btn_login_DangKy);

        savelogin = findViewById(R.id.cb_savelogin);
        savelogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    savelogin.setTextColor(getResources().getColor(R.color.black));
                else
                    savelogin.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        sharedPreferences_get = getSharedPreferences("info", Context.MODE_PRIVATE);
        tenDN = sharedPreferences_get.getString("tendn", null);
        matKhau = sharedPreferences_get.getString("matkhau", null);

        if (tenDN != null)//nếu tên dn khác rỗng (hoặc mật khẩu khác rỗng), tức là lần đăng nhập trước đã tích lưu đăng nhập...
        {
            TXTL_login_TenDN.getEditText().setText(tenDN);
            TXTL_login_MatKhau.getEditText().setText(matKhau);
        }


        nhanVienDAO = new StaffDAO(this);    //khởi tạo kết nối csdl

        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName() | !validatePassWord()) {
                    return;
                }

                tenDN = TXTL_login_TenDN.getEditText().getText().toString();
                matKhau = TXTL_login_MatKhau.getEditText().getText().toString();

                int manv = nhanVienDAO.KiemTraDN(tenDN, matKhau);//check đăng nhập

                String tennv = nhanVienDAO.LayTenNV(manv);

                int maquyen = nhanVienDAO.LayQuyenNV(manv);// lấy mã quyền của user vừa login ra

                if (manv != 0) {
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", maquyen);

                    if (savelogin.isChecked()) {
                        editor.putString("tendn", tenDN);
                        editor.putString("matkhau", matKhau);
                    } else {
                        editor.putString("tendn", null);
                        editor.putString("matkhau", null);
                    }

                    editor.commit();

                    //gửi dữ liệu user đã đăng nhập qua trang chủ
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("tendn", tenDN);
                    intent.putExtra("manv", manv);
                    intent.putExtra("tennv", tennv);
                    startActivity(intent);// đăng nhập thành công -> chuyển qua trang chủ *****************************************
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);

        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin), "transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private boolean validateUserName() {
        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            TXTL_login_TenDN.setError(null);
            TXTL_login_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord() {
        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            TXTL_login_MatKhau.setError(null);
            TXTL_login_MatKhau.setErrorEnabled(false);
            return true;
        }
    }
}