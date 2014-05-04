package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static com.padfootingfree.MyDouble.Unit.*;
import static com.padfootingfree.MyDouble.*;

/**
 * Created by j0sua3 on 19/04/2014.
 */
public class Footing_case2 extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    double A, C;
    String report;
    double z1, xt, xb, qmax;
    final int numparam = 3;


    Footing_case2(
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
        this.ex = ex.dblVal(m);
        this.ey = ey.dblVal(m);
        this.cx = cx.dblVal(m);
        this.cy = cy.dblVal(m);
        this.d = d.dblVal(m);
        z1 = fz();
        qmax = getqmax();
        xb = fxb();
        xt = fxt();

    }

    public double fz() {
        double z1;
        double z2;
        double xb1;
        double xb2;
        double a;
        double b;
        double c;
        a = 0.16e2 * Math.pow(ey, 0.3e1) + 0.4e1 * By * By * ey;
        b = 0.2e1 * By * By + 0.24e2 * ey * ey - 0.8e1 * By * ey;
        c = 0.12e2 * ey - 0.3e1 * By;
        z1 = (-b + Math.sqrt(b * b - 0.4e1 * a * c)) / a / 0.2e1;
        z2 = (-b - Math.sqrt(b * b - 0.4e1 * a * c)) / a / 0.2e1;
        xb1 = ((-0.80e2 * pow(ey, 0.3e1) * Bx * By + 0.2e1 * pow(By, 0.4e1) * Bx - 0.4e1 * pow(By, 0.3e1) * ey * Bx + 0.64e2 * pow(ey, 0.4e1) * Bx + 0.24e2 * ey * ey * Bx * By * By + 0.160e3 * ex * By * pow(ey, 0.3e1) - 0.4e1 * pow(By, 0.4e1) * ex + 0.8e1 * ey * ex * pow(By, 0.3e1) - 0.128e3 * ex * pow(ey, 0.4e1) - 0.48e2 * ey * ey * ex * By * By) * z1 - 0.3e1 * pow(By, 0.3e1) * Bx + 0.18e2 * ey * Bx * By * By - 0.48e2 * ey * ey * Bx * By + 0.96e2 * pow(ey, 0.3e1) * Bx + 0.6e1 * pow(By, 0.3e1) * ex - 0.36e2 * ey * ex * By * By + 0.96e2 * ex * By * ey * ey - 0.192e3 * ex * pow(ey, 0.3e1)) * By / ((-0.2e1 * pow(By, 0.3e1) * ey - 0.8e1 * By * pow(ey, 0.3e1) + pow(By, 0.4e1) + 0.4e1 * By * By * ey * ey) * z1 + 0.4e1 * By * By * ey - pow(By, 0.3e1) + 0.16e2 * pow(ey, 0.3e1) - 0.4e1 * By * ey * ey) / ey / 0.4e1;
        xb2 = ((-0.80e2 * pow(ey, 0.3e1) * Bx * By + 0.2e1 * pow(By, 0.4e1) * Bx - 0.4e1 * pow(By, 0.3e1) * ey * Bx + 0.64e2 * pow(ey, 0.4e1) * Bx + 0.24e2 * ey * ey * Bx * By * By + 0.160e3 * ex * By * pow(ey, 0.3e1) - 0.4e1 * pow(By, 0.4e1) * ex + 0.8e1 * ey * ex * pow(By, 0.3e1) - 0.128e3 * ex * pow(ey, 0.4e1) - 0.48e2 * ey * ey * ex * By * By) * z2 - 0.3e1 * pow(By, 0.3e1) * Bx + 0.18e2 * ey * Bx * By * By - 0.48e2 * ey * ey * Bx * By + 0.96e2 * pow(ey, 0.3e1) * Bx + 0.6e1 * pow(By, 0.3e1) * ex - 0.36e2 * ey * ex * By * By + 0.96e2 * ex * By * ey * ey - 0.192e3 * ex * pow(ey, 0.3e1)) * By / ((-0.2e1 * pow(By, 0.3e1) * ey - 0.8e1 * By * pow(ey, 0.3e1) + pow(By, 0.4e1) + 0.4e1 * By * By * ey * ey) * z2 + 0.4e1 * By * By * ey - pow(By, 0.3e1) + 0.16e2 * pow(ey, 0.3e1) - 0.4e1 * By * ey * ey) / ey / 0.4e1;
        if (0.0e0 <= xb1 && xb1 <= Bx)
            return (z1);
        else if (0.0e0 <= xb2 && xb2 <= Bx)
            return (z2);
        else
            return (-0.1e1);
    }


    public double fxb() {
        double xb;
        xb = ((-0.80e2 * pow(ey, 0.3e1) * Bx * By + 0.2e1 * pow(By, 0.4e1) * Bx - 0.4e1 * pow(By, 0.3e1) * ey * Bx + 0.64e2 * pow(ey, 0.4e1) * Bx + 0.24e2 * ey * ey * Bx * By * By + 0.160e3 * ex * By * pow(ey, 0.3e1) - 0.4e1 * pow(By, 0.4e1) * ex + 0.8e1 * ey * ex * pow(By, 0.3e1) - 0.128e3 * ex * pow(ey, 0.4e1) - 0.48e2 * ey * ey * ex * By * By) * z1 - 0.3e1 * pow(By, 0.3e1) * Bx + 0.18e2 * ey * Bx * By * By - 0.48e2 * ey * ey * Bx * By + 0.96e2 * pow(ey, 0.3e1) * Bx + 0.6e1 * pow(By, 0.3e1) * ex - 0.36e2 * ey * ex * By * By + 0.96e2 * ex * By * ey * ey - 0.192e3 * ex * pow(ey, 0.3e1)) * By / ((-0.2e1 * pow(By, 0.3e1) * ey - 0.8e1 * By * pow(ey, 0.3e1) + pow(By, 0.4e1) + 0.4e1 * By * By * ey * ey) * z1 + 0.4e1 * By * By * ey - pow(By, 0.3e1) + 0.16e2 * pow(ey, 0.3e1) - 0.4e1 * By * ey * ey) / ey / 0.4e1;
        return (xb);
    }


    public double fxt() {
        double xt;
        xt = z1 * By * (Bx - 2.d * ex);
        return (xt);
    }


    public double getqmax() {
        double qmax;
        qmax = (0.12e2 * (0.2e1 * pow(By, 0.9e1) - 0.4096e4 * pow(ey, 0.9e1) - 0.176e3 * pow(By, 0.6e1)
                * pow(ey, 0.3e1) + 0.512e3 * pow(By, 0.5e1) * pow(ey, 0.4e1) - 0.1696e4 * pow(ey, 0.5e1)
                * pow(By, 0.4e1) + 0.3520e4 * pow(By, 0.3e1) * pow(ey, 0.6e1) - 0.5632e4
                * pow(ey, 0.7e1) * By * By + 0.8192e4 * By * pow(ey, 0.8e1) - 0.10e2 * pow(By, 0.8e1)
                * ey + 0.44e2 * pow(By, 0.7e1) * ey * ey) * z1 - 0.14400e5 * pow(ey, 0.4e1)
                * pow(By, 0.4e1) - 0.1152e4 * ey * ey * pow(By, 0.6e1) + 0.288e3 * ey
                * pow(By, 0.7e1) + 0.4608e4 * pow(ey, 0.3e1) * pow(By, 0.5e1) + 0.32256e5
                * pow(ey, 0.5e1) * pow(By, 0.3e1) - 0.59904e5 * pow(ey, 0.6e1) * By
                * By + 0.73728e5 * pow(ey, 0.7e1) * By - 0.36e2 * pow(By, 0.8e1) - 0.73728e5
                * pow(ey, 0.8e1)) * V * ey * pow(By, -0.3e1) / ((0.768e3 * pow(By, 0.4e1)
                * Bx * pow(ey, 0.4e1) + 0.60e2 * pow(By, 0.6e1) * Bx * ey * ey - 0.12e2 * pow(By, 0.7e1)
                * Bx * ey - 0.256e3 * pow(By, 0.5e1) * Bx * pow(ey, 0.3e1) - 0.1728e4 * pow(By, 0.3e1)
                * Bx * pow(ey, 0.5e1) - 0.1408e4 * pow(ey, 0.6e1) * By * By * ex - 0.1536e4
                * pow(By, 0.4e1) * ex * pow(ey, 0.4e1) + 0.512e3 * pow(By, 0.5e1) * ex
                * pow(ey, 0.3e1) + 0.3456e4 * pow(By, 0.3e1) * ex * pow(ey, 0.5e1)
                - 0.120e3 * pow(By, 0.6e1) * ex * ey * ey - 0.9216e4 * ex * By * pow(ey, 0.7e1)
                + 0.704e3 * pow(ey, 0.6e1) * Bx * By * By + 0.4608e4 * pow(ey, 0.7e1) * Bx * By
                + 0.24e2 * pow(By, 0.7e1) * ex * ey - 0.6144e4 * pow(ey, 0.8e1) * Bx + 0.12288e5
                * pow(ey, 0.8e1) * ex + 0.2e1 * pow(By, 0.8e1) * Bx - 0.4e1 * pow(By, 0.8e1) * ex)
                * z1 - 0.4608e4 * pow(ey, 0.7e1) * Bx + 0.9216e4 * pow(ey, 0.7e1) * ex + 0.492e3
                * pow(By, 0.4e1) * Bx * pow(ey, 0.3e1) - 0.126e3 * pow(By, 0.5e1) * Bx * ey * ey
                + 0.27e2 * pow(By, 0.6e1) * Bx * ey - 0.1224e4 * pow(By, 0.3e1) * Bx
                * pow(ey, 0.4e1) - 0.3072e4 * ex * By * pow(ey, 0.6e1) + 0.1152e4
                * pow(ey, 0.5e1) * Bx * By * By + 0.1536e4 * pow(ey, 0.6e1) * Bx * By - 0.2304e4
                * pow(ey, 0.5e1) * By * By * ex - 0.984e3 * pow(By, 0.4e1) * ex * pow(ey, 0.3e1)
                + 0.252e3 * pow(By, 0.5e1) * ex * ey * ey - 0.54e2 * pow(By, 0.6e1) * ex * ey
                + 0.2448e4 * pow(By, 0.3e1) * ex * pow(ey, 0.4e1) + 0.6e1 * pow(By, 0.7e1) * ex - 0.3e1
                * pow(By, 0.7e1) * Bx);
        return (qmax);
    }


    public double getVyz() {


        double Vy1 = (0.16e2 * Math.pow(By, 0.3e1) * xb * xb + 0.16e2 * xb * Math.pow(By, 0.3e1) * xt
                + 0.8e1 * xb * Math.pow(By, 0.3e1) * xt * cy - 0.12e2 * xb * By * By * cy * cy * xt
                + 0.8e1 * xb * By * Math.pow(cy, 0.3e1) * xt - 0.32e2 * By * Math.pow(d, 0.3e1) * xt
                * xt + 0.8e1 * Math.pow(cy, 0.3e1) * d * xt * xt + 0.24e2 * cy * cy * d * d * xt * xt
                + 0.32e2 * cy * Math.pow(d, 0.3e1) * xt * xt - 0.16e2 * xb * xb * By * cy * cy + 0.32e2
                * xb * xb * cy * Math.pow(d, 0.3e1) + 0.24e2 * xb * xb * By * By * d * d - 0.8e1 * xb
                * xb * Math.pow(By, 0.3e1) * d - 0.32e2 * xb * xb * By * Math.pow(d, 0.3e1) - 0.64e2
                * xb * xb * By * d * d + 0.8e1 * xb * xb * Math.pow(cy, 0.3e1) * d + 0.24e2 * xb
                * xb * cy * cy * d * d - 0.32e2 * xb * Math.pow(d, 0.4e1) * xt - 0.8e1 * Math.pow(By, 0.3e1)
                * d * xt * xt + 0.24e2 * By * By * d * d * xt * xt - 0.4e1 * xb * xb * Math.pow(By, 0.3e1)
                * cy - 0.2e1 * xb * Math.pow(By, 0.4e1) * xt + 0.6e1 * xb * xb * By * By * cy
                * cy - 0.4e1 * xb * xb * By * Math.pow(cy, 0.3e1) - 0.2e1 * xb * Math.pow(cy, 0.4e1)
                * xt - 0.4e1 * Math.pow(By, 0.3e1) * xt * xt * cy + 0.6e1 * By * By * xt * xt
                * cy * cy - 0.4e1 * By * xt * xt * Math.pow(cy, 0.3e1) - 0.64e2 * xb * d * By
                * By * xt + 0.64e2 * xb * By * d * d * xt + 0.16e2 * xb * Math.pow(By, 0.3e1)
                * d * xt - 0.48e2 * xb * By * By * d * d * xt + 0.64e2 * xb * By * Math.pow(d, 0.3e1)
                * xt + 0.16e2 * xb * By * xt * cy * cy - 0.32e2 * xb * By * By * xt * cy
                + 0.24e2 * By * By * cy * d * xt * xt - 0.24e2 * By * cy * cy * d * xt
                * xt - 0.48e2 * By * cy * d * d * xt * xt - 0.64e2 * xb * xb * By * cy * d - 0.24e2
                * xb * xb * By * cy * cy * d - 0.48e2 * xb * xb * By * cy * d * d + 0.24e2 * xb * xb
                * By * By * cy * d - 0.16e2 * xb * Math.pow(cy, 0.3e1) * d * xt - 0.48e2 * xb * cy
                * cy * d * d * xt - 0.64e2 * xb * cy * Math.pow(d, 0.3e1) * xt + 0.16e2 * xb * xb
                * Math.pow(d, 0.4e1) + 0.16e2 * Math.pow(d, 0.4e1) * xt * xt - 0.48e2 * xb * By
                * By * cy * d * xt + 0.48e2 * xb * By * cy * cy * d * xt + 0.96e2 * xb * By * cy
                * d * d * xt + 0.64e2 * xb * By * cy * xt * d + xb * xb * Math.pow(By, 0.4e1)
                + xb * xb * Math.pow(cy, 0.4e1) + Math.pow(By, 0.4e1) * xt * xt + xt * xt
                * Math.pow(cy, 0.4e1)) * Math.pow(By, -0.2e1) * qmax / xb / 0.128e3;
        return Vy1;
    }

    public double getMy() {

        double xm;
        double My1;
        double My2;
        double My3;
        xm = (Bx - cx) / 0.20e1;
        My1 = qmax * By * xm * xm * (0.30e1 * xb + 0.30e1 * xt + (-0.1e1) * 0.20e1 * xm) / xb / 0.12e2;
        My2 = qmax * By * (Math.pow(xt, 0.4e1) + Math.pow(xm, 0.4e1) + (-0.1e1) * 0.40e1 * xm * Math.pow(xt, 0.3e1)
                + (-0.1e1) * 0.40e1 * xb * Math.pow(xm, 0.3e1) + 0.60e1 * xm * xm * xb * xb) / xb / (xb - xt) / 0.24e2;
        My3 = -(Math.pow(xb, 0.3e1) + (-0.1e1) * 0.40e1 * xm * xb * xb + xt * xb * xb + (-0.1e1) * 0.40e1
                * xm * xt * xb + xt * xt * xb + (-0.1e1) * 0.40e1 * xm * xt * xt + Math.pow(xt, 0.3e1))
                * qmax * By / xb / 0.24e2;
        if (xm <= xt && xm <= xb)
            return (My1);
        else if (xt < xm && xm <= xb)
            return (My2);
        else if (xt < xm && xb < xm)
            return (My3);
        else
            return (-0.1e1);


    }


    public double getVxz() {

        double xv;
        double Vx1;
        double Vx2;
        double Vx3;
        xv = Bx / 0.2e1 - cx / 0.2e1 - d;
        Vx1 = qmax * By * xv * (xb + xt - xv) / xb / 0.2e1;
        Vx2 = qmax * By * (0.12e2 * xt * xb * xb - 0.12e2 * xt * xt * xb + Math.pow(xv, 0.4e1) + 0.3e1
                * Math.pow(xt, 0.4e1) - 0.4e1 * xv * Math.pow(xt, 0.3e1) - 0.4e1 * xb
                * Math.pow(xv, 0.3e1) - 0.8e1 * xb * Math.pow(xt, 0.3e1) + 0.12e2 * xb * xv * xt
                * xt + 0.6e1 * xb * xb * xv * xv + 0.6e1 * xt * xt * xb * xb - 0.12e2 * xv * xb
                * xb * xt) / xb / (xb - xt) / 0.24e2;
        Vx3 = -(Math.pow(xb, 0.3e1) - 0.4e1 * xv * xb * xb + xt * xb * xb - 0.12e2 * xt * xb - 0.5e1
                * xt * xt * xb + 0.8e1 * xt * xb * xv + 0.3e1 * Math.pow(xt, 0.3e1) - 0.4e1 * xv
                * xt * xt) * qmax * By / xb / 0.24e2;
        if (xv <= xt && xv <= xb)
            return (Vx1);
        else if (xt < xv && xv <= xb)
            return (Vx2);
        else if (xt < xv && xb < xv)
            return (Vx3);
        else
            return (-0.1e1);
    }


    public double getMx() {

        double Mx1 = (-0.28e2 * xb * xb * Math.pow(By, 0.3e1) * cy + 0.4e1 * xb * xb * By
                * Math.pow(cy, 0.3e1) + 0.17e2 * xb * xb * Math.pow(By, 0.4e1) + xb * xb
                * Math.pow(cy, 0.4e1) + 0.6e1 * xb * xb * By * By * cy * cy - 0.2e1 * xb
                * Math.pow(cy, 0.4e1) * xt - 0.16e2 * xb * Math.pow(By, 0.3e1) * xt * cy
                + 0.6e1 * xb * Math.pow(By, 0.4e1) * xt + 0.12e2 * xb * By * By * cy * cy * xt
                + 0.6e1 * By * By * xt * xt * cy * cy + Math.pow(By, 0.4e1) * xt * xt + xt * xt
                * Math.pow(cy, 0.4e1) - 0.4e1 * Math.pow(By, 0.3e1) * xt * xt * cy - 0.4e1 * By
                * xt * xt * Math.pow(cy, 0.3e1)) * Math.pow(By, -0.2e1) * qmax / xb / 0.384e3;
        return Mx1;
    }


    public String getDesignReport(UnitType unitType) {


        //MyDouble rA, rC, rVyz, rVxz, rMabtY, rMabtX;
        MyDouble rxb = new MyDouble(fxb(), m);
        MyDouble rxt = new MyDouble(fxt(), m);
        MyDouble rqmax = new MyDouble(getqmax(), kPa);
        MyDouble rVyz = new MyDouble(getVyz(), kN);
        MyDouble rMy = new MyDouble(getMy(), kNm);
        MyDouble rVxz = new MyDouble(getVxz(), kN);
        MyDouble rMx = new MyDouble(getMx(), kNm);


        if (unitType.equals(UnitType.SI)) {
            report = "Parameter xt = " + rxt.toString() + "\r\n" +
                    "Parameter xb = " + rxb.toString() + "\r\n" +
                    "Maximum bearing, qmax = " + rqmax.toString() + "\r\n" +
                    "Shear force, Vyz = " + rVyz.toString() + "\r\n" +
                    "Moment, My = " + rMy.toString() + "\r\n" +
                    "Shear force, Vxz = " + rVxz.toString() + "\r\n" +
                    "Moment, Mx = " + rMx.toString() + "\r\n";

        } else {
            report = "Parameter xt = " + rxt.toString() + "\r\n" +
                    "Parameter xb = " + rxb.toString() + "\r\n" +
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

        MyDouble rxb = new MyDouble(fxb(), m);
        MyDouble rxt = new MyDouble(fxt(), m);

        float xxt = (float) (rxt.v() * mscale_geom);
        float xxb = (float) (rxb.v() * mscale_geom);

        PointF[] pointFs = new PointF[4];
        pointFs[0] = new PointF(0.f, 0.f);
        pointFs[1] = new PointF(xxt, 0.f);
        pointFs[2] = new PointF(xxb, mfBy);
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

