package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import static com.padfootingfree.MyDouble.Unit.kN;
import static com.padfootingfree.MyDouble.Unit.kNm;
import static com.padfootingfree.MyDouble.Unit.kPa;
import static com.padfootingfree.MyDouble.Unit.kip;
import static com.padfootingfree.MyDouble.Unit.kipft;
import static com.padfootingfree.MyDouble.Unit.m;
import static com.padfootingfree.MyDouble.Unit.psf;
import static com.padfootingfree.MyDouble.UnitType;
import static java.lang.Math.pow;

public class Footing_case_error extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    String report;
    double qR, qL, qmax;


    Footing_case_error(
            Bitmap bitmap,
            int txtht,
            MyDouble Bx,
            MyDouble By,
            MyDouble ex,
            MyDouble ey,
            MyDouble V,
            MyDouble cx,
            MyDouble cy,
            MyDouble d

    ) {

        super(bitmap, txtht, Bx, By, ex, ey);


        this.V = V.dblVal(kN);
        this.Bx = Bx.dblVal(m);
        this.By = By.dblVal(m);


    }


    public String getDesignReport(UnitType unitType) {

        return "Oops, please check eccentricity, it might be outside \r\n" +
                "of foundation area already. Foundation is unstable";
    }


    public Bitmap getSketch() {

        canvas = new Canvas(mbitmap_final);
        drawPFGeometryAndLoadPoint(canvas); //pad geom drawn to canvas

        //draw bearing area on the same canvas specific to this footing case
        Paint paint_bearing_area = new Paint();
        paint_bearing_area.setColor(Color.BLUE);
        paint_bearing_area.setStyle(Paint.Style.FILL);
        paint_bearing_area.setAntiAlias(true);
        paint_bearing_area.setAlpha(20);

        drawBearingArea(paint_bearing_area);


        return mbitmap_final;
    }


    public void drawBearingArea(Paint paint) {


        PointF[] pointFs = new PointF[4];
        pointFs[0] = new PointF(0.f, 0.f);
        pointFs[1] = new PointF(mfBx, 0.f);
        pointFs[2] = new PointF(mfBx, mfBy);
        pointFs[3] = new PointF(0.f, mfBy);

        float[] boundaryPts = {pointFs[0].x, pointFs[0].y,
                pointFs[1].x, pointFs[1].y,
                pointFs[2].x, pointFs[2].y,
                pointFs[3].x, pointFs[3].y,
                pointFs[0].x, pointFs[0].y
        };
        //create matrix to translate
        Matrix matrix = new Matrix();
        //translate points using matrix object
        matrix.setTranslate(mx0 - (mfBx / 2.f), my0 - (mfBy / 2.f));
        matrix.mapPoints(boundaryPts);  //apply translation matrix to pts!!!


        //build path from translated points
        Path boundary = new Path();
        boundary.reset();
        boundary.moveTo(boundaryPts[0], boundaryPts[1]);
        boundary.lineTo(boundaryPts[2], boundaryPts[3]);
        boundary.lineTo(boundaryPts[4], boundaryPts[5]);
        boundary.lineTo(boundaryPts[6], boundaryPts[7]);
        boundary.lineTo(boundaryPts[0], boundaryPts[1]);
        //draw actual lines
        canvas.drawPath(boundary, paint);

    }


}

