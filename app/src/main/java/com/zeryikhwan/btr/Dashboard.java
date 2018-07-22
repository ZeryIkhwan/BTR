package com.zeryikhwan.btr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeryikhwan.btr.Action.PrefManager;

/**
 * Created by Zery Ikhwan on 6/25/2018.
 */

public class Dashboard extends Fragment {

    CardView scan;
    TextView jumlah;
    PrefManager manager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("BTR");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);

        manager = new PrefManager(getActivity());

        jumlah = (TextView) view.findViewById(R.id.jumlah);
        scan = (CardView) view.findViewById(R.id.btnScan);

        jumlah.setText(manager.getDataJumlah());



        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Scanner.class);
                startActivity(i);
                System.exit(0);

            }
        });

        return view;
    }
}
