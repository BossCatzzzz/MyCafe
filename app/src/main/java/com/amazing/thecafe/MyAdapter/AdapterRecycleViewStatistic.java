package com.amazing.thecafe.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazing.thecafe.DAO.BanDAO;
import com.amazing.thecafe.DAO.StaffDAO;
import com.amazing.thecafe.DTO.HoaDonDTO;
import com.amazing.thecafe.DTO.StaffDTO;
import com.amazing.thecafe.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<HoaDonDTO> donDatDTOList;
    StaffDAO nhanVienDAO;
    BanDAO banAnDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<HoaDonDTO> donDatDTOList){

        this.context =context;
        this.layout = layout;
        this.donDatDTOList = donDatDTOList;
        nhanVienDAO = new StaffDAO(context);
        banAnDAO = new BanDAO(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        HoaDonDTO donDatDTO = donDatDTOList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        holder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        if(donDatDTO.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
       StaffDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        holder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        holder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDatDTO.getMaBan()));
    }

    @Override
    public int getItemCount() {
        return donDatDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV,
                txt_customstatistic_BanDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat);
            txt_customstatistic_TenNV = itemView.findViewById(R.id.txt_customstatistic_TenNV);
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
