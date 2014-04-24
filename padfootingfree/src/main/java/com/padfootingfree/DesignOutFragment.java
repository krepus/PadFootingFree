package com.padfootingfree;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.padfootingfree.MyDouble.Unit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DesignOutFragment extends Fragment {
    View view;
    String Unit, Report;
    MyDouble Bx, By, ex, ey, V;
    Padfooting padfooting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.outputlayout, container, false);

        /*
        if (mDataCallback.isPremium()) {
            Button button = (Button) view.findViewById(R.id.exportButton_id);
            button.setVisibility(View.VISIBLE);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    log("onclick...");
                    saveReportText(mReport);
                    saveReportPng(mSketchBitmap);

                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);


                    ArrayList<Uri> fileUris = new ArrayList<Uri>();

                    File file_to_share = new File(getActivity().getFilesDir(), getString(R.string.filename));
                    Uri contentUri = FileProvider.getUriForFile(getActivity(), "com.BeamDesign.fileprovider", file_to_share);
                    fileUris.add(contentUri);

                    file_to_share = new File(getActivity().getFilesDir(), getString(R.string.filename_png));
                    contentUri = FileProvider.getUriForFile(getActivity(), "com.BeamDesign.fileprovider", file_to_share);
                    fileUris.add(contentUri);

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris);
                    intent.setType("image*//*");
                    intent.setType("text*//*");
                    startActivity(Intent.createChooser(intent, "Share files to.."));

                }
            });

        }
        */


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (getDesignInput()) {

            padfooting = new Footing_case4(V.dblVal(kN), Bx.dblVal(m), By.dblVal(m), ex.dblVal(m), ey.dblVal(m));

            if (padfooting.getFootingCase() == 4) {
                Report = padfooting.getReport();
            }

            //showSketch();

            TextView textView = (TextView) view.findViewById(R.id.report_textview_id);
            textView.setText(Report);
        }


        return view;


    }

    private boolean getDesignInput() throws NumberFormatException {
        //get  sharedpref
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());


        //throw numberformatexception if entry is not a number
        try {


            //get unit
            Unit = sp.getString(getString(R.string.UNIT), getString(R.string.SI));
            //for SI unit
            if (Unit.equals(getString(R.string.SI))) {
                Bx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BX_PREF), "")), MyDouble.Unit.m);
                By = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BY_PREF), "")), MyDouble.Unit.m);
                ex = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EX_PREF), "")), MyDouble.Unit.m);
                ey = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EY_PREF), "")), MyDouble.Unit.m);
                V = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.V_PREF), "")), MyDouble.Unit.kN);

            } else {
                Bx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BX_PREF), "")), MyDouble.Unit.ft);
                By = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BY_PREF), "")), MyDouble.Unit.ft);
                ex = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EX_PREF), "")), MyDouble.Unit.ft);
                ey = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EY_PREF), "")), MyDouble.Unit.ft);
                V = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.V_PREF), "")), MyDouble.Unit.kip);

            }

            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Check input for invalid entries..", Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
