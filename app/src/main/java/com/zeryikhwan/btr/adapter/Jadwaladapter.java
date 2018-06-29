package com.zeryikhwan.btr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeryikhwan.btr.R;
import com.zeryikhwan.btr.model.Jadwal;

import java.util.List;

public class Jadwaladapter extends RecyclerView.Adapter<Jadwaladapter.MyViewHolder> {

    private List<Jadwal> jadwalList;

    public Jadwaladapter(List<Jadwal> jadwalList) {
        this.jadwalList = jadwalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jadwal_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Jadwal jadwal = jadwalList.get(position);
        holder.txTempat.setText(jadwal.getTempat());
        holder.txAlamat.setText(jadwal.getAlamat());
        holder.txTanggal.setText(jadwal.getTanggal());
        holder.txWaktu.setText(jadwal.getWaktu());
    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txTempat, txAlamat, txTanggal, txWaktu;

        public MyViewHolder(View view) {
            super(view);
            txTempat = (TextView) view.findViewById(R.id.tempat);
            txAlamat = (TextView) view.findViewById(R.id.alamat);
            txTanggal = (TextView) view.findViewById(R.id.tanggal);
            txWaktu = (TextView) view.findViewById(R.id.waktu);
        }
    }

}
