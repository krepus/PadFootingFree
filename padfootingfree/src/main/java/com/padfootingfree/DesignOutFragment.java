package com.padfootingfree;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.padfootingfree.MyDouble.Unit.*;

public class DesignOutFragment extends Fragment {
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


            A = -4 * ex.v() + 2 * Bx.v(); //in mm
            C = 2 * By.v() - 4 * ey.v();
            //get display parameters and pass to sketch method


            int mbitmapWidth = getResources().getDisplayMetrics().widthPixels;
            int mbitmapHeight = mbitmapWidth;
            Bitmap bitmap = Bitmap.createBitmap(mbitmapWidth, mbitmapHeight, Bitmap.Config.ARGB_8888);
            int txtht = 15 * (int) getResources().getDisplayMetrics().density;

            switch (getCase()) {
                case 2:
                    padfooting = new Footing_case2(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                case 4:
                    padfooting = new Footing_case4(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
                    break;
                default:
                    padfooting = new Footing_case4(bitmap, txtht, Bx, By, ex, ey, V, cx, cy, d);
            }

            Report = padfooting.getDesignReport(MyDouble.UnitType.valueOf(Unit));

            ImageView imageView = (ImageView) view.findViewById(R.id.sketch_img);
            imageView.setImageBitmap(padfooting.getSketch());

            TextView textView = (TextView) view.findViewById(R.id.report_textview_id);
            textView.setText(Report);
        }


        return view;


    }

    private int getCase() {

        if (A < Bx.v() && C > By.v()) {
            return 2;
        } else if (A > Bx.v() & C > By.v() & Bx.v() / A + By.v() / C > 1.d) {
            return 4;
        } else return 1;

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

    private void showSketch(int bearing_case) {


        PadfootingbitmapGeometry padfootingbitmap = null;
        switch (bearing_case) {
            case 4:


                return;
            default:
                return;
        }


    }
}
