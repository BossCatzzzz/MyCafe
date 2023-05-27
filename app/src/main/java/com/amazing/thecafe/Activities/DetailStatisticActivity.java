package com.amazing.thecafe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazing.thecafe.DAO.BanDAO;
import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.DAO.ThanhToanDAO;
import com.amazing.thecafe.DTO.StaffDTO;
import com.amazing.thecafe.DTO.ThanhToanDTO;
import com.amazing.thecafe.MyAdapter.AdapterDisplayPayment;
import com.amazing.thecafe.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayDat,txt_detailstatistic_TenBan
            ,txt_detailstatistic_TenNV,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, maban;
    String ngaydat, tongtien;
    StaffDAO nhanVienDAO;
    BanDAO banAnDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    ThanhToanDAO thanhToanDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_statistic);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        manv = intent.getIntExtra("manv",0);
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayDat = (TextView)findViewById(R.id.txt_detailstatistic_NgayDat);
        txt_detailstatistic_TenBan = (TextView)findViewById(R.id.txt_detailstatistic_TenBan);
        txt_detailstatistic_TenNV = (TextView)findViewById(R.id.txt_detailstatistic_TenNV);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        nhanVienDAO = new StaffDAO(this);
        banAnDAO = new BanDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayDat.setText(ngaydat);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            StaffDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(manv);
            txt_detailstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
            txt_detailstatistic_TenBan.setText(banAnDAO.LayTenBanTheoMa(maban));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.trai_in,R.anim.phai_out);
            }
        });
    }

    private void HienThiDSCTDD(){
        thanhToanDTOList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}