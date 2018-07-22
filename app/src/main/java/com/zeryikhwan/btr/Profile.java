package com.zeryikhwan.btr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeryikhwan.btr.Action.PrefManager;

public class Profile extends Fragment {

    TextView prnama, pralamat, prgender, prhp, prjumlah, premail, tgl;
    PrefManager manager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        manager = new PrefManager(getActivity());
        prnama = (TextView) view.findViewById(R.id.pfnama);
        prjumlah = (TextView) view.findViewById(R.id.pfjumlah);
        pralamat = (TextView) view.findViewById(R.id.pfalamat);
        premail = (TextView) view.findViewById(R.id.pfemail);
        prgender = (TextView) view.findViewById(R.id.pfgender);
        prhp = (TextView) view.findViewById(R.id.pfhp);
        tgl = (TextView) view.findViewById(R.id.tgldonor);

        prnama.setText(manager.getDataNama());
        prjumlah.setText(manager.getDataJumlah());
        pralamat.setText(manager.getDataAlamat());
        premail.setText(manager.getDataEmail());
        prgender.setText(manager.getDataGender());
        prhp.setText(manager.getDataHp());
        tgl.setText(manager.getDataTanggal());

        return view;
    }

}
