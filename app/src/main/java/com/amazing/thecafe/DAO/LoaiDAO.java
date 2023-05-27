package com.amazing.thecafe.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amazing.thecafe.DTO.LoaiDTO;
import com.amazing.thecafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiDAO {
    SQLiteDatabase database;
    public LoaiDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaiDTO loaiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH,loaiMonDTO.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAIMON,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIMON,CreateDatabase.TBL_LOAIMON_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(LoaiDTO loaiMonDTO,int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH,loaiMonDTO.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAIMON,contentValues
                ,CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public List<LoaiDTO> LayDSLoaiMon(){
        List<LoaiDTO> loaiMonDTOList = new ArrayList<LoaiDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiDTO loaiMonDTO = new LoaiDTO();
            loaiMonDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH)));
            loaiMonDTOList.add(loaiMonDTO);

            cursor.moveToNext();
        }
        return loaiMonDTOList;
    }

    @SuppressLint("Range")
    public LoaiDTO LayLoaiMonTheoMa(int maloai){
        LoaiDTO loaiMonDTO = new LoaiDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON+" WHERE "+CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiMonDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH)));

            cursor.moveToNext();
        }
        return loaiMonDTO;
    }
}
