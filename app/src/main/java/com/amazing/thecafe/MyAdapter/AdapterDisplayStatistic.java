package com.amazing.thecafe.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amazing.thecafe.DAO.BanDAO;
import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.DTO.HoaDonDTO;
import com.amazing.thecafe.DTO.StaffDTO;
import com.amazing.thecafe.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<HoaDonDTO> donDatDTOS;
    ViewHolder viewHolder;
    StaffDAO nhanVienDAO;
    BanDAO banAnDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<HoaDonDTO> donDatDTOS){
        this.context = context;
        this.layout = layout;
        this.donDatDTOS = donDatDTOS;
        nhanVienDAO = new StaffDAO(context);
        banAnDAO = new BanDAO(context);
    }

    @Override
    public int getCount() {
        return donDatDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatDTOS.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HoaDonDTO donDatDTO = donDatDTOS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTO.getTongTien()+" VNĐ");
        if (donDatDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        StaffDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        viewHolder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDatDTO.getMaBan()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}
