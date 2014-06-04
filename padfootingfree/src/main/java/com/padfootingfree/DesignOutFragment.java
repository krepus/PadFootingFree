package com.padfootingfree;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static com.padfootingfree.MyDouble.Unit.*;

public class DesignOutFragment extends Fragment {

    //logging
    public static final String mDebugTag = "DesignOutFragment";
    public static final boolean mDebugLog = true;

    View view;
    String Unit, Report;
    MyDouble Bx, By, ex, ey, V, cx, cy, d;
    double A, C;
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


            int mbitmapWidth = getResources().getDisplayMetrics().widthPixels;
            int mbitmapHeight = mbitmapWidth;
            Bitmap bitmap = Bitmap.createBitmap(mbitmapWidth, mbitmapHeight, Bitmap.Config.ARGB_8888);
            int txtht = 15 * (int) getResources().getDisplayMetrics().density;

            switch (getCase()) {
                case 1:
                    logDebug("case 2");
                    padfooting = new Footing_case1(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                case 2:
                    logDebug("case 2");
                    padfooting = new Footing_case2(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                case 3:
                    logDebug("case 3");
                    padfooting = new Footing_case3(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                case 4:
                    logDebug("case 4");
                    padfooting = new Footing_case4(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                case 5:
                    logDebug("case 5");
                    padfooting = new Footing_case5(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                default:
                    logDebug("case error:");
                    /*Toast.makeText(getActivity(), "Ooops..something is wrong, please ..you may contact dev " +
                            "and provide the inputs when this message pop up", Toast.LENGTH_LONG).show();*/
                    padfooting = new Footing_case_error(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
            }

            Report = padfooting.getDesignReport(MyDouble.UnitType.valueOf(Unit));

            ImageView imageView = (ImageView) view.findViewById(R.id.sketch_img);
            imageView.setImageBitmap(padfooting.getSketch());

            TextView textView = (TextView) view.findViewById(R.id.report_textview_id);
            textView.setText(Report);
        } else {

        }


        return view;


    }

    private int getCase() {

        if (ex.v() <= Bx.v() / 6.d && ey.v() <= By.v() / 6.d) {
            return 1;
        } else if (isEccentricityValid()) {
            A = -4.d * ex.v() + 2.d * Bx.v(); //in mm
            C = 2.d * By.v() - 4.d * ey.v();

            if (A < Bx.v() && C > By.v()) {
                return 2;
            } else if (A > Bx.v() && C > By.v() && Bx.v() / A + By.v() / C > 1.d) {
                return 4;
            } else if (A > Bx.v() && C < By.v()) {
                return 3;
            } else if (A < Bx.v() && C < By.v()) {
                return 5;
            } else

                return -1;


        } else {
            return -1;
        }
    }

    private boolean isEccentricityValid() {
        if (ex.v() > Bx.v() / 2.d || ey.v() > By.v() / 2.d) {
            return false;
        } else return true;
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
                Bx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BX_PREF), "")), MyDouble.Unit.mm);
                By = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BY_PREF), "")), MyDouble.Unit.mm);
                ex = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EX_PREF), "")), MyDouble.Unit.mm);
                ey = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EY_PREF), "")), MyDouble.Unit.mm);
                V = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.V_PREF), "")), MyDouble.Unit.kN);
                cx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.CX_PREF), "")), MyDouble.Unit.mm);
                cy = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.CY_PREF), "")), MyDouble.Unit.mm);
                d = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.D_PREF), "")), MyDouble.Unit.mm);

            } else {
                Bx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BX_PREF), "")), MyDouble.Unit.ft);
                By = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.BY_PREF), "")), MyDouble.Unit.ft);
                ex = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EX_PREF), "")), MyDouble.Unit.in);
                ey = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.EY_PREF), "")), MyDouble.Unit.in);
                V = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.V_PREF), "")), MyDouble.Unit.kip);
                cx = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.CX_PREF), "")), MyDouble.Unit.in);
                cy = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.CY_PREF), "")), MyDouble.Unit.in);
                d = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.D_PREF), "")), MyDouble.Unit.in);

            }

            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Check input for invalid entries..", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.attach:
                saveReportText(mColumn.getDesignReport());
                saveReportPng(mColumn.getInteractionSketch());

                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                ArrayList<Uri> fileUris = new ArrayList<Uri>();
                File file_to_share = new File(getActivity().getFilesDir(), getString(R.string.filename));
                Uri contentUri = FileProvider.getUriForFile(getActivity(), "com.column.interaction.design.fileprovider", file_to_share);
                fileUris.add(contentUri);

                file_to_share = new File(getActivity().getFilesDir(), getString(R.string.filename_png));
                contentUri = FileProvider.getUriForFile(getActivity(), "com.column.interaction.design.fileprovider", file_to_share);
                fileUris.add(contentUri);

                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris);
                intent.setType("image/*");
                intent.setType("text/*");
                startActivity(Intent.createChooser(intent, "Share files to.."));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSketch(int bearing_case) {


        PadfootingbitmapGeometry padfootingbitmap = null;
        switch (bearing_case) {
            case 4:


                return;
            default:
                return;
        }


    }

    void logDebug(String msg) {
        if (mDebugLog) Log.d(mDebugTag, msg);
    }


}
