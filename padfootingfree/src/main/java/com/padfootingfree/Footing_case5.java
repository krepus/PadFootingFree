package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import static com.padfootingfree.MyDouble.Unit.*;
import static com.padfootingfree.MyDouble.UnitType;
import static java.lang.Math.*;

public class Footing_case5 extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    String report;
    double Z, xb, yL, qmax;


    Footing_case5(
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

        if (ex.v() < 1.d) {
            this.ex = 0.01d;
        } else {
            this.ex = ex.dblVal(m);
        }

        this.ey = ey.dblVal(m);

        this.cx = cx.dblVal(m);
        this.cy = cy.dblVal(m);
        this.d = d.dblVal(m);
        qmax = getqmax();
        xb = getxb();
        yL = getYL();

    }


    public double getYL() {
        double yl;
        yl = -4.d * ey + 2.d * By;

        return (yl);
    }

    public double getxb() {
        double tmp;
        tmp = -4.d * ex + 2.d * Bx;
        return tmp;
    }

    public double getqmax() {
        double tmp;

        tmp = 0.3e1 / 0.2e1 * V / (-2.d * ey + By) / (-2.d * ex + Bx);

        return tmp;
    }

    public double getMx() {

        double ym = (By - cy) / 2.d;

        if (yL > ym) {
            double M1 = qmax * xb * pow(By - cy, 0.2e1) * (By * By - 0.2e1 * By * cy + cy * cy
                    - 0.8e1 * yL * By + 0.8e1 * yL * cy + 0.24e2 * yL * yL) * pow(yL, -0.2e1) / 0.384e3;
            return M1;
        } else {
            double M3 = (qmax * yL * xb * (-yL + 2.d * By - 2.d * cy)) / 0.24e2;
            return M3;
        }
    }

    public double getVyz() {

        double yv = (By - cy) / 2.d - d;
        if (yv > 0.d) {
            if (yL >= yv) {
                double V1 = (qmax * xb * (By - cy - 2 * d) * (By * By - 2 * By * cy - 4 * By * d + cy * cy + 4 * cy * d + 4 * d * d - 6 * yL * By + 6 * yL * cy + 12 * yL * d + 12 * yL * yL) * (int) pow(yL, (-2))) / 0.48e2;
                return V1;
            } else {
                double V2 = yL * qmax * xb / 0.6e1;
                return V2;
            }
        } else return 0.d;


    }

    public double getMy() {
        double xm = (Bx - cx) / 2.d;
        if (xm < xb) {
            double My1 = qmax * yL * pow(Bx - cx, 0.2e1) * (Bx * Bx - 0.2e1 * Bx * cx + cx * cx - 0.8e1
                    * xb * Bx + 0.8e1 * xb * cx + 0.24e2 * xb * xb) * pow(xb, -0.2e1) / 0.384e3;
            return My1;
        } else {
            double My2 = (qmax * yL * xb * (-xb + 2.d * Bx - 2.d * cx)) / 0.24e2;
            return My2;
        }
    }

    public double getVxz() {
        double xv = (Bx - cx) / 2.d - d;
        if (xv > 0.d) {
            if (xv < xb) {
                double V = (qmax * (Bx - cx - 2.d * d) * (yL * yL * cx * cx + xb * xb * Bx * Bx + xb * xb
                        * cx * cx + 4.d * yL * yL * Bx * cx + 4.d * xb * yL * Bx * Bx - 2.d * xb * yL
                        * cx * cx - 2.d * xb * xb * Bx * cx + 7.d * yL * yL * Bx * Bx - 2.d * xb * yL
                        * Bx * cx - 4.d * xb * yL * Bx * d - 8.d * xb * yL * cx * d + 4.d * yL * yL * d * d
                        + 4.d * xb * xb * d * d + 8.d * yL * yL * Bx * d + 4.d * yL * yL * cx * d - 8.d
                        * xb * yL * d * d - 4.d * xb * xb * Bx * d + 4.d * xb * xb * cx * d) * pow(Bx, (-2.d))
                        / yL) / 0.48e2;

                return V;
            } else {
                return yL * qmax * xb / 0.6e1;
            }

        } else return 0;
    }


    public String getDesignReport(UnitType unitType) {


        //MyDouble rA, rC, rVyz, rVxz, rMabtY, rMabtX;
        MyDouble ryR = new MyDouble(xb, m);
        MyDouble ryL = new MyDouble(yL, m);
        MyDouble rqmax = new MyDouble(getqmax(), kPa);
        MyDouble rVyz = new MyDouble(getVyz(), kN);
        MyDouble rMy = new MyDouble(getMy(), kNm);
        MyDouble rVxz = new MyDouble(getVxz(), kN);
        MyDouble rMx = new MyDouble(getMx(), kNm);


        if (unitType.equals(UnitType.SI)) {
            report = "Parameter xb = " + ryR.toString() + "\r\n" +
                    "Parameter yL = " + ryL.toString() + "\r\n" +
                    "Maximum bearing, qmax = " + rqmax.toString() + "\r\n" +
                    "Shear force, Vyz = " + rVyz.toString() + "\r\n" +
                    "Moment, My = " + rMy.toString() + "\r\n" +
                    "Shear force, Vxz = " + rVxz.toString() + "\r\n" +
                    "Moment, Mx = " + rMx.toString() + "\r\n";

        } else {
            report = "Parameter xb = " + ryR.toUnit(ft).toString() + "\r\n" +
                    "Parameter yL = " + ryL.toUnit(ft).toString() + "\r\n" +
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

        MyDouble rxb = new MyDouble(xb, m);
        MyDouble ryL = new MyDouble(yL, m);

        float fxb = (float) (rxb.v() * mscale_geom);
        float fyL = (float) (ryL.v() * mscale_geom);

        PointF[] pointFs = new PointF[3];
        pointFs[0] = new PointF(0.f, mfBy);
        pointFs[1] = new PointF(fxb, mfBy);
        pointFs[2] = new PointF(0.f, mfBy - fyL);

        float[] boundaryPts = {pointFs[0].x, pointFs[0].y,
                pointFs[1].x, pointFs[1].y,
                pointFs[2].x, pointFs[2].y,
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

