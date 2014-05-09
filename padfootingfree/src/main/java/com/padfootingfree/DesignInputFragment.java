package com.padfootingfree;

import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import static com.padfootingfree.MyDouble.Unit;

public class DesignInputFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.inputlayout, container, false);

        // removeAds();//ads removed if premium
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveDesignInput();
    }

    @Override
    public void onResume() {
        super.onResume();
        //removeAds();
        updateInputUI();

    }

    private void saveDesignInput() {


        //save data in sharedpref
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = sp.edit();


        TextView v = (TextView) view.findViewById(R.id.Bx_in);
        ed.putString(getString(R.string.BX_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.By_in);
        ed.putString(getString(R.string.BY_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.V_in);
        ed.putString(getString(R.string.V_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.ex_in);
        ed.putString(getString(R.string.EX_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.ey_in);
        ed.putString(getString(R.string.EY_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.cx_in);
        ed.putString(getString(R.string.CX_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.cy_in);
        ed.putString(getString(R.string.CY_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.d_in);
        ed.putString(getString(R.string.D_PREF), v.getText().toString());

        ed.commit();


        //pass the data to activity
        //saveDesigInputData.SaveDesigInput(bundle);
        //then return the bundled data
        //return bundle;
    }


    private void updateInputUI() {
        /**
         * populate spinner_minDB
         */
        //read preferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //update unit of prompt string
        String SIUnit = getString(R.string.SI);
        String unit = sharedPref.getString(getString(R.string.UNIT), SIUnit);
        String DIA = "\u00D8";

        if (unit.equals(SIUnit)) {
            TextView v = (TextView) view.findViewById(R.id.Bx_textview_ID);
            v.setText(getString(R.string.Bx_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.By_textview_ID);
            v.setText(getString(R.string.By_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.V_textview_ID);
            v.setText(getString(R.string.V_str_ui) + "(" + Unit.kN.toString() + ")");


            v = (TextView) view.findViewById(R.id.ex_textview_ID);
            v.setText(getString(R.string.ex_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.ey_textview_ID);
            v.setText(getString(R.string.ey_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.cx_textview_ID);
            v.setText(getString(R.string.cx_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.cy_textview_ID);
            v.setText(getString(R.string.cy_str_ui) + "(" + Unit.mm.toString() + ")");

            v = (TextView) view.findViewById(R.id.d_textview_ID);
            v.setText(getString(R.string.d_str_ui) + "(" + Unit.mm.toString() + ")");

        } else {
            TextView v = (TextView) view.findViewById(R.id.Bx_textview_ID);
            v.setText(getString(R.string.Bx_str_ui) + "(" + Unit.ft.toString() + ")");

            v = (TextView) view.findViewById(R.id.By_textview_ID);
            v.setText(getString(R.string.By_str_ui) + "(" + Unit.ft.toString() + ")");

            v = (TextView) view.findViewById(R.id.V_textview_ID);
            v.setText(getString(R.string.V_str_ui) + "(" + Unit.kip.toString() + ")");


            v = (TextView) view.findViewById(R.id.ex_textview_ID);
            v.setText(getString(R.string.ex_str_ui) + "(" + Unit.in.toString() + ")");

            v = (TextView) view.findViewById(R.id.ey_textview_ID);
            v.setText(getString(R.string.ey_str_ui) + "(" + Unit.in.toString() + ")");

            v = (TextView) view.findViewById(R.id.cx_textview_ID);
            v.setText(getString(R.string.cx_str_ui) + "(" + Unit.in.toString() + ")");

            v = (TextView) view.findViewById(R.id.cy_textview_ID);
            v.setText(getString(R.string.cy_str_ui) + "(" + Unit.in.toString() + ")");

            v = (TextView) view.findViewById(R.id.d_textview_ID);
            v.setText(getString(R.string.d_str_ui) + "(" + Unit.in.toString() + ")");
        }


        //edittexts
        EditText v = (EditText) view.findViewById(R.id.Bx_in);
        v.setText(sharedPref.getString(getString(R.string.BX_PREF), "2500"));


        v = (EditText) view.findViewById(R.id.By_in);
        v.setText(sharedPref.getString(getString(R.string.BY_PREF), "1500"));

        v = (EditText) view.findViewById(R.id.V_in);
        v.setText(sharedPref.getString(getString(R.string.V_PREF), "400"));

        v = (EditText) view.findViewById(R.id.ex_in);
        v.setText(sharedPref.getString(getString(R.string.EX_PREF), "375"));

        v = (EditText) view.findViewById(R.id.ey_in);
        v.setText(sharedPref.getString(getString(R.string.EY_PREF), "300"));

        v = (EditText) view.findViewById(R.id.cx_in);
        v.setText(sharedPref.getString(getString(R.string.CX_PREF), "300"));

        v = (EditText) view.findViewById(R.id.cy_in);
        v.setText(sharedPref.getString(getString(R.string.CY_PREF), "300"));

        v = (EditText) view.findViewById(R.id.d_in);
        v.setText(sharedPref.getString(getString(R.string.D_PREF), "410"));

        SharedPreferences.Editor ed = sharedPref.edit();
        ed.apply();
    }

}
