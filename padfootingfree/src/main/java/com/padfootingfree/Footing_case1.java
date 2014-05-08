package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import static com.padfootingfree.MyDouble.Unit.ft;
import static com.padfootingfree.MyDouble.Unit.kN;
import static com.padfootingfree.MyDouble.Unit.kNm;
import static com.padfootingfree.MyDouble.Unit.kPa;
import static com.padfootingfree.MyDouble.Unit.kip;
import static com.padfootingfree.MyDouble.Unit.kipft;
import static com.padfootingfree.MyDouble.Unit.m;
import static com.padfootingfree.MyDouble.Unit.psf;
import static com.padfootingfree.MyDouble.UnitType;
import static java.lang.Math.pow;

public class Footing_case1 extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    String report;
    double qR, qL, qmax;


    Footing_case1(
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
/*

        if (ex.v() < 1.d) {
            this.ex = 0.003d;
        } else {
            this.ex = ex.dblVal(m);
        }

        if (ey.v() < 1.d) {
            this.ex = 0.003d;
        } else {
            this.ey = ey.dblVal(m);
        }
*/

        this.ex = ex.dblVal(m);
        this.ey = ey.dblVal(m);
        this.cx = cx.dblVal(m);
        this.cy = cy.dblVal(m);
        this.d = d.dblVal(m);
        qmax = getqmax();
        qR = getqR();
        qL = getqL();

    }


    public double getqL() {
        double yl;
        yl = V * (-6.d * Bx * ey + 6.d * By * ex + By * Bx) * pow(By, (-2.d)) * pow(Bx, (-2.d));

        return (yl);
    }

    public double getqR() {
        double tmp;
        tmp = V * (By * Bx - 6.d * By * ex - 6.d * Bx * ey) * pow(By, (-2.d)) * pow(Bx, (-2.d));
        return tmp;
    }

    public double getqmax() {
        double tmp;

        tmp = V * (6.d * By * ex + By * Bx + 6.d * Bx * ey) * pow(By, (-2.d)) * pow(Bx, (-2.d));

        return tmp;
    }

    public double getMx() {

        return -pow(By - cy, 0.2e1) * Bx * (0.2e1 * qL * By + qL * cy - 0.5e1 * qmax * By - cy
                * qmax - 0.3e1 * By * qR) / By / 0.48e2;

    }

    public double getVyz() {
        double yv = (By - cy) / 2.d - d;
        if (yv > 0.d) {
            return -((By - cy - 2.d * d) * Bx * (qL * By + qL * cy + 2.d * qL * d - 3.d * qmax * By - cy * qmax
                    - 2.d * qmax * d - 2.d * By * qR) / By) / 0.8e1;
        } else return 0.d;
    }

    public double getMy() {
        return pow(Bx - cx, 0.2e1) * By * (qR * Bx - qR * cx + 0.2e1 * qL * Bx + qL * cx + 0.3e1 * qmax * Bx)
                / Bx / 0.48e2;
    }

    public double getVxz() {
        double xv = (Bx - cx) / 2.d;
        if (xv > 0.d) {
            return ((Bx - cx - 2 * d) * By * (qR * Bx - qR * cx - 2 * qR * d + qL * Bx + qL * cx + 2 * qL * d
                    + 2 * qmax * Bx) / Bx) / 0.8e1;
        } else return 0.d;
    }


    public String getDesignReport(UnitType unitType) {


        //MyDouble rA, rC, rVyz, rVxz, rMabtY, rMabtX;
        MyDouble rqR = new MyDouble(qR, kPa);
        MyDouble rqL = new MyDouble(qL, kPa);
        MyDouble rqmax = new MyDouble(getqmax(), kPa);
        MyDouble rVyz = new MyDouble(getVyz(), kN);
        MyDouble rMy = new MyDouble(getMy(), kNm);
        MyDouble rVxz = new MyDouble(getVxz(), kN);
        MyDouble rMx = new MyDouble(getMx(), kNm);


        if (unitType.equals(UnitType.SI)) {
            report = "Parameter qR = " + rqR.toString() + "\r\n" +
                    "Parameter qL = " + rqL.toString() + "\r\n" +
                    "Maximum bearing, qmax = " + rqmax.toString() + "\r\n" +
                    "Shear force, Vyz = " + rVyz.toString() + "\r\n" +
                    "Moment, My = " + rMy.toString() + "\r\n" +
                    "Shear force, Vxz = " + rVxz.toString() + "\r\n" +
                    "Moment, Mx = " + rMx.toString() + "\r\n";

        } else {
            report = "Parameter xb = " + rqR.toUnit(psf).toString() + "\r\n" +
                    "Parameter yL = " + rqL.toUnit(psf).toString() + "\r\n" +
                    "Maximum bearing, qmax = " + rqmax.toUnit(psf).toString() + "\r\n" +
                    "Shear force, Vyz = " + rVyz.toUnit(kip).toString() + "\r\n" +
                    "Moment, My = " + rMy.toUnit(kipft).toString() + "\r\n" +
                    "Shear force, Vxz = " + rVxz.toUnit(kip).toString() + "\r\n" +
                    "Moment, Mx = " + rMx.toUnit(kipft).toString() + "\r\n";
        }


        return report;
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

