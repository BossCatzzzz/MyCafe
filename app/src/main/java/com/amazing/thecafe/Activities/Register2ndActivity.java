package com.amazing.thecafe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amazing.thecafe.DAO.PermissionDAO;
import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.DTO.StaffDTO;
import com.amazing.thecafe.R;

import java.util.Calendar;

public class Register2ndActivity extends AppCompatActivity {

    RadioGroup RG_signup_GioiTinh;
    DatePicker DT_signup_NgaySinh;
    Button BTN_signup_next;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh;
    StaffDAO nhanVienDAO;
    PermissionDAO quyenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2nd);

        RG_signup_GioiTinh = findViewById(R.id.rg_signup_GioiTinh);
        DT_signup_NgaySinh = findViewById(R.id.dt_signup_NgaySinh);
        BTN_signup_next = findViewById(R.id.btn_signup_next);

        //lấy dữ liệu từ bundle của register1
        Bundle bundle = getIntent().getBundleExtra(RegisterActivity.BUNDLE);
        if(bundle != null) {
            hoTen = bundle.getString("hoten");
            tenDN = bundle.getString("tendn");
            eMail = bundle.getString("email");
            sDT = bundle.getString("sdt");
            matKhau = bundle.getString("matkhau");
        }
        //khởi tạo kết nối csdl
        nhanVienDAO = new StaffDAO(this);
        quyenDAO = new PermissionDAO(this);

        BTN_signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAge() | !validateGender()){
                    return;
                }

                //lấy các thông tin còn lại
                switch (RG_signup_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_signup_Nam:
                        gioiTinh = "Nam"; break;
                    case R.id.rd_signup_Nu:
                        gioiTinh = "Nữ"; break;
                    case R.id.rd_signup_Khac:
                        gioiTinh = "Khác"; break;
                }
                String ngaySinh = DT_signup_NgaySinh.getDayOfMonth() + "/" + (DT_signup_NgaySinh.getMonth() + 1)
                        +"/"+DT_signup_NgaySinh.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                StaffDTO nhanVienDTO = new StaffDTO();
                nhanVienDTO.setHOTENNV(hoTen);
                nhanVienDTO.setTENDN(tenDN);
                nhanVienDTO.setEMAIL(eMail);
                nhanVienDTO.setSDT(sDT);
                nhanVienDTO.setMATKHAU(matKhau);
                nhanVienDTO.setGIOITINH(gioiTinh);
                nhanVienDTO.setNGAYSINH(ngaySinh);

                //nếu nhân viên đầu tiên đăng ký sẽ có quyền quản lý
                if(!nhanVienDAO.KtraTonTaiNV()){// true là đã có tồn tại nhân viên
                    quyenDAO.ThemQuyen("Quản lý");
                    quyenDAO.ThemQuyen("Nhân viên");
                    nhanVienDTO.setMAQUYEN(1);
                }else {
                    nhanVienDTO.setMAQUYEN(2);
                }

                //Thêm nv dựa theo obj nhanvienDTO
                long ktra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
                if(ktra != 0){
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();//gọi qua hàm chuyển màn hình đăng nhập
                }else{
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay về màn hình trước khi nhấn nút quay lại -> viết theo kiểu event công khai *****************************************
    public void backFromRegister2nd(View view){
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.trai_in,R.anim.phai_out);
    }

    //Hàm chuyển màn hình đăng nhập khi hoàn thành đăng ký
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private boolean validateGender(){
        if(RG_signup_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_signup_NgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}