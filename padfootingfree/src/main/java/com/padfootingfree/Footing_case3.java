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
import static java.lang.Math.*;


public class Footing_case3 extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    String report;
    double Z, yR, yL, qmax;


    Footing_case3(
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
        Z = fz();
        qmax = getqmax();
        yR = getYR();
        yL = getYL();

    }

    public double fz() {
        double z1;
        double z2;
        double a;
        double b;
        double c;
        double tyr1;
        double tyr2;
        a = 0.40e1 * Bx * Bx * ex + 0.16e2 * pow(ex, 0.3e1);
        b = 0.240e2 * ex * ex - 0.8e1 * Bx * ex + 0.2e1 * Bx * Bx;
        c = 0.120e2 * ex - 0.3e1 * Bx;
        z1 = (-b + sqrt(b * b - 0.4e1 * a * c)) / 0.20e1 / a;
        z2 = (-b - sqrt(b * b - 0.4e1 * a * c)) / 0.20e1 / a;
        tyr1 = Bx * (-0.20e1 * ey + By) * z1;
        tyr2 = Bx * (-0.20e1 * ey + By) * z2;
        if (0.0e0 <= tyr1 && tyr1 <= By)
            return (z1);
        else if (0.0e0 <= tyr2 && tyr2 <= By)
            return (z2);
        else
            return (-0.10e1);
    }

    public double getYL() {
        double yl;
        yl = ((24.d * Z * Bx * Bx * ex * ex * By - 4.d * Z * pow(Bx, 3.d)
                * ex * By + 96.d * pow(ex, 3.d) * By + 18.d * Bx * Bx * ex * By - 3.d * pow(Bx, 3.d)
                * By + 64.d * Z * pow(ex, 4.d) * By - 48.d * ex * ex * By * Bx + 2.d * Z * pow(Bx, 4.d)
                * By - 80.d * Z * Bx * pow(ex, 3.d) * By - 48.d * Z * Bx * Bx * ex * ex * ey + 8.d
                * Z * pow(Bx, 3.d) * ey * ex - 192.d * ey * pow(ex, 3.d) - 36.d * ey * Bx * Bx * ex
                + 6.d * ey * pow(Bx, 3.d) - 128.d * Z * ey * pow(ex, 4.d) + 96.d * ey * Bx * ex * ex
                - 4.d * Z * pow(Bx, 4.d) * ey + 160.d * Z * Bx * pow(ex, 3.d) * ey) * Bx / (-8.d * Z
                * Bx * pow(ex, 3.d) + Z * pow(Bx, 4.d) - 2.d * Z * pow(Bx, 3.d) * ex + 4.d * Bx * Bx
                * ex - pow(Bx, 3.d) + 16.d * pow(ex, 3.d) - 4.d * Bx * ex * ex + 4.d * Z * Bx * Bx
                * ex * ex) / ex) / 0.4e1;

        return (yl);
    }

    public double getYR() {
        double tmp;
        tmp = Z * Bx * (-2.d * ey + By);
        return tmp;
    }

    public double getqmax() {
        double tmp;

        tmp = 0.12e2 * (-0.3e1 * pow(Bx, 0.8e1) - 0.6144e4 * pow(ex, 0.8e1) - 0.96e2 * pow(Bx, 0.6e1)
                * ex * ex + 0.2688e4 * pow(ex, 0.5e1) * pow(Bx, 0.3e1) - 0.1200e4 * pow(ex, 0.4e1)
                * pow(Bx, 0.4e1) - 0.4992e4 * pow(ex, 0.6e1) * Bx * Bx + 0.6144e4 * pow(ex, 0.7e1)
                * Bx + 0.24e2 * pow(Bx, 0.7e1) * ex + 0.384e3 * pow(Bx, 0.5e1) * pow(ex, 0.3e1)
                + 0.44e2 * pow(Bx, 0.7e1) * Z * ex * ex - 0.5632e4 * Z * Bx * Bx * pow(ex, 0.7e1)
                + 0.8192e4 * pow(ex, 0.8e1) * Z * Bx - 0.10e2 * pow(Bx, 0.8e1) * Z * ex + 0.3520e4
                * Z * pow(Bx, 0.3e1) * pow(ex, 0.6e1) - 0.1696e4 * Z * pow(Bx, 0.4e1) * pow(ex, 0.5e1)
                + 0.512e3 * pow(Bx, 0.5e1) * Z * pow(ex, 0.4e1) - 0.176e3 * pow(Bx, 0.6e1) * Z
                * pow(ex, 0.3e1) - 0.4096e4 * pow(ex, 0.9e1) * Z + 0.2e1 * pow(Bx, 0.9e1) * Z) * V
                * ex * pow(Bx, -0.3e1) / (0.6e1 * pow(Bx, 0.7e1) * ey - 0.3e1 * pow(Bx, 0.7e1) * By
                + 0.9216e4 * pow(ex, 0.7e1) * ey - 0.4608e4 * pow(ex, 0.7e1) * By - 0.126e3
                * pow(Bx, 0.5e1) * ex * ex * By - 0.54e2 * ey * pow(Bx, 0.6e1) * ex + 0.27e2
                * pow(Bx, 0.6e1) * By * ex - 0.984e3 * ey * pow(Bx, 0.4e1) * pow(ex, 0.3e1)
                + 0.1152e4 * pow(ex, 0.5e1) * By * Bx * Bx - 0.1224e4 * pow(ex, 0.4e1) * By
                * pow(Bx, 0.3e1) + 0.1536e4 * pow(ex, 0.6e1) * By * Bx + 0.2448e4 * ey * pow(Bx, 0.3e1)
                * pow(ex, 0.4e1) + 0.252e3 * ey * pow(Bx, 0.5e1) * ex * ex + 0.492e3 * pow(Bx, 0.4e1)
                * pow(ex, 0.3e1) * By - 0.2304e4 * ey * Bx * Bx * pow(ex, 0.5e1) - 0.3072e4 * ey
                * Bx * pow(ex, 0.6e1) - 0.256e3 * pow(Bx, 0.5e1) * Z * pow(ex, 0.3e1) * By - 0.1728e4
                * Z * pow(Bx, 0.3e1) * pow(ex, 0.5e1) * By + 0.768e3 * Z * pow(Bx, 0.4e1) * pow(ex, 0.4e1)
                * By + 0.704e3 * Z * Bx * Bx * pow(ex, 0.6e1) * By + 0.4608e4 * Z * Bx * pow(ex, 0.7e1)
                * By - 0.9216e4 * Z * Bx * ey * pow(ex, 0.7e1) - 0.12e2 * pow(Bx, 0.7e1) * Z * ex * By
                + 0.60e2 * pow(Bx, 0.6e1) * Z * ex * ex * By - 0.120e3 * pow(Bx, 0.6e1) * Z * ex * ex
                * ey - 0.1536e4 * Z * pow(Bx, 0.4e1) * ey * pow(ex, 0.4e1) + 0.512e3 * pow(Bx, 0.5e1)
                * Z * ey * pow(ex, 0.3e1) + 0.3456e4 * Z * pow(Bx, 0.3e1) * ey * pow(ex, 0.5e1) + 0.24e2
                * pow(Bx, 0.7e1) * Z * ey * ex - 0.1408e4 * Z * Bx * Bx * ey * pow(ex, 0.6e1) + 0.2e1
                * pow(Bx, 0.8e1) * Z * By - 0.4e1 * pow(Bx, 0.8e1) * Z * ey + 0.12288e5 * pow(ex, 0.8e1)
                * Z * ey - 0.6144e4 * pow(ex, 0.8e1) * Z * By);

        return tmp;
    }

    public double getMx() {

        double ym = (By - cy) / 2.d;

        if (yR > ym) {
            double M1 = -(qmax * Bx * (-3.d * yL * By * By + 6.d * yL * By * cy - 3.d * yL * cy * cy - 3.d
                    * yR * By * By + 6.d * yR * By * cy - 3.d * yR * cy * cy + pow(By, 3.d) - 3.d * By * By * cy
                    + 3.d * By * cy * cy - pow(cy, 3.d)) / yL) / 0.48e2;
            return M1;
        } else if (yL <= ym) {
            double M3 = qmax * Bx * (-pow(yR, 0.3e1) - pow(yL, 0.3e1) - yR * yL * yL - yL * yR * yR + 0.2e1
                    * yR * yR * By + 0.2e1 * yL * yL * By + 0.2e1 * yR * yL * By - 0.2e1 * yL * yL * cy - 0.2e1
                    * yR * yR * cy - 0.2e1 * yR * yL * cy) / yL / 0.24e2;
            return M3;
        } else {
            double M2 = ((24.d * yL * yL * By * By) - (8.d * yL * pow(By, 3.d))
                    + (24.d * yL * By * By * cy) + pow(By, 4.d) - (4.d * pow(By, 3.d) * cy) + (6.d * By
                    * By * cy * cy) - (48.d * yL * yL * By * cy) - (24.d * yL * By * cy * cy) - (4.d * By
                    * pow(cy, 3.d)) + (24.d * yL * yL * cy * cy) + (8.d * yL * pow(cy, 3.d)) + pow(cy, 4.d)
                    + 0.16e2 * pow(yR, 0.4e1) - 0.32e2 * By * pow(yR, 0.3e1) + 0.32e2 * cy * pow(yR, 0.3e1))
                    * Bx * qmax / yL / (yL - yR) / 0.384e3;
            return M2;
        }
    }

    public double getVyz() {

        double yv = (By - cy) / 2.d - d;
        if (yR >= yv) {
            double V1 = -(qmax * Bx * (By - cy - 2.d * d) * (-2.d * yL - 2.d * yR + By - cy - 2.d * d)
                    / yL) / 0.8e1;
            return V1;
        } else if (yL <= yv) {
            double V2 = (-(6.d * yL * By * By) + (12.d * yL * By * cy) + (24.d * By * d * yL) + (12.d
                    * yL * yL * By) - (6.d * yL * cy * cy) - (24.d * cy * d * yL) - (12.d * yL * yL * cy)
                    - (24.d * d * d * yL) - (24.d * d * yL * yL) - (3.d * By * By * cy) + (3.d * By
                    * cy * cy) + pow(By, 3.d) - pow(cy, 3.d) - 0.8e1 * Math.pow(yR, 0.3e1) - (6.d * By
                    * By * d) + (12.d * By * d * d) - (6.d * cy * cy * d) - (12.d * cy * d * d) + (12.d
                    * By * cy * d) - (8.d * pow(d, 3.d))) * Bx * qmax / yL / (yL - yR) / 0.48e2;
            return V2;
        } else {
            double V3 = qmax * Bx * (yL * yL + yR * yL + yR * yR) / yL / 0.6e1;
            return V3;

        }
    }

    public double getMy() {
        double My = qmax * pow(Bx - cx, 0.2e1) * (0.17e2 * yL * yL * Bx * Bx + 0.6e1 * yL * yL * Bx
                * cx + yL * yL * cx * cx + 0.6e1 * yR * yL * Bx * Bx - 0.4e1 * yR * yL * Bx * cx - 0.2e1
                * yR * yL * cx * cx + yR * yR * Bx * Bx - 0.2e1 * yR * yR * Bx * cx + yR * yR * cx * cx)
                * pow(Bx, -0.2e1) / yL / 0.384e3;
        return My;
    }

    public double getVxz() {
        double xv = (Bx - cx) / 2.d - d;
        if (xv > 0.d) {
            double V = (qmax * (Bx - cx - 2.d * d) * (yL * yL * cx * cx + yR * yR * Bx * Bx + yR * yR
                    * cx * cx + 4.d * yL * yL * Bx * cx + 4.d * yR * yL * Bx * Bx - 2.d * yR * yL
                    * cx * cx - 2.d * yR * yR * Bx * cx + 7.d * yL * yL * Bx * Bx - 2.d * yR * yL
                    * Bx * cx - 4.d * yR * yL * Bx * d - 8.d * yR * yL * cx * d + 4.d * yL * yL * d * d
                    + 4.d * yR * yR * d * d + 8.d * yL * yL * Bx * d + 4.d * yL * yL * cx * d - 8.d
                    * yR * yL * d * d - 4.d * yR * yR * Bx * d + 4.d * yR * yR * cx * d) * pow(Bx, (-2.d))
                    / yL) / 0.48e2;

            return V;
        } else return 0;
    }


    public String getDesignReport(UnitType unitType) {


        //MyDouble rA, rC, rVyz, rVxz, rMabtY, rMabtX;
        MyDouble ryR = new MyDouble(yR, m);
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

        MyDouble ryR = new MyDouble(yR, m);
        MyDouble ryL = new MyDouble(yL, m);

        float fyR = (float) (ryR.v() * mscale_geom);
        float fyL = (float) (ryL.v() * mscale_geom);

        PointF[] pointFs = new PointF[4];
        pointFs[0] = new PointF(0.f, mfBy - fyL);
        pointFs[1] = new PointF(mfBx, mfBy - fyR);
        pointFs[2] = new PointF(mfBx, mfBy);
        pointFs[3] = new PointF(0.f, mfBy);

        float[] boundaryPts = {pointFs[0].x, pointFs[0].y,
                pointFs[1].x, pointFs[1].y,
                pointFs[2].x, pointFs[2].y,
                pointFs[3].x, pointFs[3].y
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

