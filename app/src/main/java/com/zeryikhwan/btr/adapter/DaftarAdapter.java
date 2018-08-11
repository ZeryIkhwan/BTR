package com.zeryikhwan.btr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeryikhwan.btr.R;
import com.zeryikhwan.btr.model.Daftar;

import java.util.List;

public class DaftarAdapter extends RecyclerView.Adapter<DaftarAdapter.MyViewHolder> {

    private List<Daftar> daftarList;

    public DaftarAdapter(List<Daftar> daftarList) {
        this.daftarList = daftarList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Daftar daftar = daftarList.get(position);
        holder.nama.setText(daftar.getNama());
        holder.hp.setText(daftar.getHp());
        holder.gol.setText(daftar.getGolDarah());
    }

    @Override
    public int getItemCount() {
        return daftarList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, gol, hp;

        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.nama);
            hp = (TextView) view.findViewById(R.id.hp);
            gol = (TextView) view.findViewById(R.id.goldarah);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView txNama, txHp, txGol;

        public MyViewHolder2(View view) {
            super(view);
            txNama = (TextView) view.findViewById(R.id.nama);
            txHp = (TextView) view.findViewById(R.id.hp);
            txGol = (TextView) view.findViewById(R.id.goldarah);
        }
    }
}
