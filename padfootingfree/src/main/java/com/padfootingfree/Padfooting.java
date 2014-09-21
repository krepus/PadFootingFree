package com.padfootingfree;

import android.graphics.Bitmap;

/**
 * Created by j0sua3 on 19/04/2014.
 */
public interface Padfooting {

    public String getDesignReport(MyDouble.UnitType unitType);

    //  public int getFootingCase();
    public double getMx();
    public double getVyz();
    public double getMy();
    public double getVxz();

    public Bitmap getSketch();

}
