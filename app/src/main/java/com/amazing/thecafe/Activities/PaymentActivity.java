package com.amazing.thecafe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazing.thecafe.DAO.BanDAO;
import com.amazing.thecafe.DAO.HoaDonDAO;
import com.amazing.thecafe.DAO.ThanhToanDAO;
import com.amazing.thecafe.DTO.ThanhToanDTO;
import com.amazing.thecafe.MyAdapter.AdapterDisplayPayment;
import com.amazing.thecafe.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    HoaDonDAO donDatDAO;
    BanDAO banAnDAO;
    ThanhToanDAO thanhToanDAO;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int maban, madondat;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = (TextView)findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayDat = (TextView)findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donDatDAO = new HoaDonDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        banAnDAO = new BanDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        TXT_payment_TenBan.setText(tenban);
        TXT_payment_NgayDat.setText(ngaydat);

        //ktra mã bàn tồn tại thì hiển thị
        if(maban !=0 ){
            HienThiThanhToan();

            for (int i=0;i<thanhToanDTOS.size();i++){
                int soluong = thanhToanDTOS.get(i).getSoLuong();
                int giatien = thanhToanDTOS.get(i).getGiaTien();

                tongtien += (soluong * giatien);
            }
            TXT_payment_TongTien.setText(String.valueOf(tongtien) +" VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                boolean ktraban = banAnDAO.CapNhatTinhTrangBan(maban,"false");
                boolean ktradondat = donDatDAO.UpdateTThaiDonTheoMaBan(maban,"true");
                boolean ktratongtien = donDatDAO.UpdateTongTienDonDat(madondat,String.valueOf(tongtien));
                if(ktraban && ktradondat && ktratongtien){
                    HienThiThanhToan();
                    TXT_payment_TongTien.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan(){
        madondat = (int) donDatDAO.LayMaDonTheoMaBan(maban,"false");
        thanhToanDTOS = thanhToanDAO.LayDSMonTheoMaDon(madondat);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}