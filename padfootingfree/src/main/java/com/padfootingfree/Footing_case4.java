package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import static java.lang.Math.*;
import static com.padfootingfree.MyDouble.Unit.*;
import static com.padfootingfree.MyDouble.*;

/**
 * Created by j0sua3 on 19/04/2014.
 */
public class Footing_case4 extends PadfootingbitmapGeometry implements Padfooting {

    double Bx, By, ex, ey, V, cx, cy, d;
    double A, C;
    String report;
    final int numparam = 3;


    Footing_case4(
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

        A = -4 * ex.dblVal(m) + 2 * Bx.dblVal(m);
        C = 2 * By.dblVal(m) - 4 * ey.dblVal(m);

    }


    public double[] get_a_type4() {
        double[] a = new double[numparam];
        double tgalpha;
        tgalpha = C / A;
        a[0] = (C - By) / tgalpha;
        a[1] = Bx - a[0];
        a[2] = Bx - a[0];

        return a;
    }

    public double[] get_c_type4() {
        double[] c = new double[numparam];
        double tgalpha;
        tgalpha = C / A;
        c[0] = By;
        c[1] = (A - Bx) * tgalpha;
        c[2] = c[0] - c[1];
        return c;
    }

    public double[] get_F() {
        double[] a = get_a_type4();
        double[] c = get_c_type4();
        double[] F = new double[numparam];
        F[0] = a[0] * c[0];
        F[1] = a[1] * c[1];
        F[2] = a[2] * c[2] / 0.2e1;
        return F;
    }

    public double get_ug() {
        double Ug;
        double[] a = get_a_type4();
        double[] F = get_F();
        Ug = (a[0] * F[0] / 0.2e1 + (a[0] + a[1] / 0.2e1) * F[1] + (a[0] + a[2] / 0.3e1) * F[2]) / (F[0] + F[1] + F[2]);
        return (Ug);
    }

    public double get_vg() {
        double Vg;
        double[] c = get_c_type4();
        double[] F = get_F();
        Vg = (c[0] * F[0] / 0.2e1 + c[1] * F[1] / 0.2e1 + (c[1] + c[2] / 0.3e1) * F[2]) / (F[0] + F[1] + F[2]);
        return (Vg);
    }

    public double[] get_e() {
        double[] a = get_a_type4();
        double[] e = new double[numparam];
        double Ug;
        Ug = get_ug();
        e[0] = -Ug + a[0] / 0.2e1;
        e[1] = a[0] + a[1] / 0.2e1 - Ug;
        e[2] = a[0] + a[2] / 0.3e1 - Ug;
        return e;
    }

    public double[] get_f() {
        double[] c = get_c_type4();
        double[] f = new double[numparam];
        double Vg = get_vg();
        f[0] = c[0] / 0.2e1 - Vg;
        f[1] = -Vg + c[1] / 0.2e1;
        f[2] = c[1] + c[2] / 0.3e1 - Vg;
        return f;
    }

    public double get_Ix() {
        double sumIs_i;
        double sumFf;
        double[] a = get_a_type4();
        double[] c = get_c_type4();
        double[] F = get_F();
        double[] f = get_f();
        sumIs_i = a[0] * pow(c[0], 0.3e1) / 0.12e2 + a[1] * pow(c[1], 0.3e1) / 0.12e2 + a[2] * pow(c[2], 0.3e1) / 0.36e2;
        sumFf = F[0] * pow(f[0], 0.2e1) + F[1] * pow(f[1], 0.2e1) + F[2] * pow(f[2], 0.2e1);
        return (sumIs_i + sumFf);
    }

    public double get_Iy() {
        double sumIt_i;
        double sumFe;
        double[] a = get_a_type4();
        double[] c = get_c_type4();
        double[] F = get_F();
        double[] e = get_e();
        sumIt_i = pow(a[0], 0.3e1) * c[0] / 0.12e2 + pow(a[1], 0.3e1) * c[1] / 0.12e2 + pow(a[2], 0.3e1) * c[2] / 0.36e2;
        sumFe = F[0] * pow(e[0], 0.2e1) + F[1] * pow(e[1], 0.2e1) + F[2] * pow(e[2], 0.2e1);
        return (sumIt_i + sumFe);
    }

    public double get_Ixy() {
        double Ist;
        double[] a = get_a_type4();
        double[] c = get_c_type4();
        double[] F = get_F();
        double[] e = get_e();
        double[] f = get_f();
        Ist = -pow(a[2], 0.2e1) * pow(c[2], 0.2e1) / 0.72e2;
        return (Ist + F[0] * e[0] * f[0] + F[1] * e[1] * f[1] + F[2] * e[2] * f[2]);
    }

    public double[] get_AC() {
        double[] AC = new double[2];
        double Xv;
        double Yv;
        double Ug;
        double Vg;
        double tgbeta;
        double tgalpha_new;
        double Ix;
        double Iy;
        double Ixy;
        double[] F = get_F();
        double X0;
        Ug = get_ug();
        Vg = get_vg();
        Xv = -ex + Bx / 0.2e1 - Ug;
        Yv = -ey + By / 0.2e1 - Vg;
        tgbeta = Yv / Xv;
        Ix = get_Ix();
        Iy = get_Iy();
        Ixy = get_Ixy();
        tgalpha_new = (Ix - Ixy * tgbeta) / (Iy * tgbeta - Ixy);
        X0 = -(Iy + Ixy / tgalpha_new) / Xv / (F[0] + F[1] + F[2]);
        AC[0] = Ug + X0 + Vg / tgalpha_new;
        AC[1] = AC[0] * tgalpha_new;
        return AC;
    }

    public double getqmax() {
        double Xv;
        double Yv;
        double Ug;
        double Vg;
        double tgbeta;
        double tgalpha_new;
        double Ix;
        double Iy;
        double Ixy;
        double[] F = get_F();
        double X0;
        Ug = get_ug();
        Vg = get_vg();
        Xv = -ex + Bx / 0.2e1 - Ug;
        Yv = -ey + By / 0.2e1 - Vg;
        tgbeta = Yv / Xv;
        Ix = get_Ix();
        Iy = get_Iy();
        Ixy = get_Ixy();
        tgalpha_new = (Ix - Ixy * tgbeta) / (Iy * tgbeta - Ixy);
        X0 = -(Iy + Ixy / tgalpha_new) / Xv / (F[0] + F[1] + F[2]);
        return A * V / (X0 * (F[0] + F[1] + F[2]));
    }

    public double getShearVyz() {

        double qmax = getqmax();
        double Xv = Bx / 0.2e1 - cx / 0.2e1 - d;
        double bxo = A * (-By + C) / C;


        if (Xv > 0.d) {
            if (bxo > Xv) {

                double Vyz_Xv = -(qmax * Xv * By * (C * Xv - 2.d * C * A + A * By) / C / A) / 0.2e1;
                return Vyz_Xv;
            } else {
                double Vyz_bxo = qmax * pow(C, -0.2e1) * (-0.3e1 * pow(A, 0.3e1) * By * By * C
                        + 0.3e1 * pow(A, 0.3e1) * By * C * C + pow(Xv, 0.3e1) * pow(C, 0.3e1)
                        + pow(A, 0.3e1) * pow(By, 0.3e1) - pow(A, 0.3e1) * pow(C, 0.3e1)
                        - 0.3e1 * pow(C, 0.3e1) * A * Xv * Xv + 0.3e1 * A * A * pow(C, 0.3e1) * Xv)
                        * pow(A, -0.2e1) / 0.6e1;
                return Vyz_bxo;
            }
        } else {
            return 0.d;
        }
    }

    public double getMy() {

        double qmax = getqmax();
        double Xm = Bx / 0.2e1 - cx / 0.2e1;
        double bxo = A * (-By + C) / C;

        if (Xm > 0.d) {
            if (bxo > Xm) {
                double My_Xm = -(double) (qmax * Xm * Xm * By * (3 * A * By + 2 * C * Xm - 6 * C * A) / C / A) / 0.12e2;
                return My_Xm;
            } else {
                double My_bxo = qmax * pow(C, -0.3e1) * (pow(A, 0.4e1) * pow(By, 0.4e1) + 0.6e1 * pow(A, 0.4e1)
                        * By * By * C * C - 0.12e2 * Xm * pow(A, 0.3e1) * By * By * C * C - 0.4e1 * pow(A, 0.4e1)
                        * By * pow(C, 0.3e1) + 0.12e2 * Xm * pow(A, 0.3e1) * By * pow(C, 0.3e1) + pow(Xm, 0.4e1)
                        * pow(C, 0.4e1) + pow(A, 0.4e1) * pow(C, 0.4e1) + 0.4e1 * C * Xm * pow(A, 0.3e1)
                        * pow(By, 0.3e1) - 0.4e1 * Xm * pow(A, 0.3e1) * pow(C, 0.4e1) - 0.4e1 * A
                        * pow(Xm, 0.3e1) * pow(C, 0.4e1) + 0.6e1 * pow(C, 0.4e1) * A * A * Xm * Xm
                        - 0.4e1 * pow(A, 0.4e1) * pow(By, 0.3e1) * C) * pow(A, -0.2e1) / 0.24e2;


                        /*= -qmax * pow(C, -0.3e1) * (0.5e1 * pow(A, 0.4e1) * pow(By, 0.4e1)
                        - 0.6e1 * pow(A, 0.4e1) * By * By * C * C + 0.12e2 * pow(A, 0.3e1) * By * By
                        * Xm * C * C + 0.4e1 * pow(A, 0.4e1) * By * pow(C, 0.3e1) - 0.12e2 * pow(A, 0.3e1)
                        * By * Xm * pow(C, 0.3e1) - 0.3e1 * pow(Xm, 0.4e1) * pow(C, 0.4e1) - 0.4e1
                        * pow(A, 0.4e1) * pow(By, 0.3e1) * C + pow(A, 0.4e1) * pow(C, 0.4e1) + 0.8e1
                        * pow(C, 0.4e1) * A * pow(Xm, 0.3e1) - 0.6e1 * A * A * pow(C, 0.4e1) * Xm * Xm)
                        * pow(A, -0.2e1) / 0.24e2;
*/
                return My_bxo;
            }
        } else {
            return 0.d;
        }
    }

    public double getShearVxz() {

        double qmax = getqmax();
        double Xv = By / 0.2e1 - cy / 0.2e1 - d;
        double byo = C * (-Bx + A) / A;


        if (Xv > 0.d) {
            if (byo > Xv) {

                double Vxz_Xv = (qmax * Bx * Xv * (-C * Bx + 2 * C * A - A * Xv) / C / A) / 0.2e1;
                return Vxz_Xv;
            } else {
                double Vxz_byo = -qmax * pow(A, -0.2e1) * (0.3e1 * pow(C, 0.3e1) * A * Bx * Bx
                        - 0.3e1 * pow(C, 0.3e1) * Bx * A * A - pow(Xv, 0.3e1) * pow(A, 0.3e1)
                        - pow(C, 0.3e1) * pow(Bx, 0.3e1) + pow(A, 0.3e1) * pow(C, 0.3e1)
                        + 0.3e1 * C * pow(A, 0.3e1) * Xv * Xv - 0.3e1 * pow(A, 0.3e1) * C * C * Xv)
                        * pow(C, -0.2e1) / 0.6e1;

                return Vxz_byo;
            }
        } else {
            return 0.d;
        }
    }

    public double getMx() {

        double qmax = getqmax();
        double Xm = By / 0.2e1 - cy / 0.2e1;
        double byo = C * (-Bx + A) / A;


        if (Xm > 0.d) {
            if (byo > Xm) {

                double Mx_Xm = (double) (qmax * Xm * Xm * Bx * (-2 * A * Xm - 3 * C * Bx + 6 * C * A) / C / A) / 0.12e2;
                return Mx_Xm;
            } else {
                double Mx_byo = qmax * pow(A, -0.3e1) * (pow(C, 0.4e1) * pow(Bx, 0.4e1) + 0.6e1
                        * pow(C, 0.4e1) * Bx * Bx * A * A - 0.12e2 * Xm * pow(C, 0.3e1) * Bx * Bx
                        * A * A - 0.4e1 * pow(C, 0.4e1) * Bx * pow(A, 0.3e1) + 0.12e2 * Xm * pow(C, 0.3e1)
                        * Bx * pow(A, 0.3e1) + pow(A, 0.4e1) * pow(C, 0.4e1) + 0.4e1 * A * Xm * pow(C, 0.3e1)
                        * pow(Bx, 0.3e1) - 0.4e1 * Xm * pow(A, 0.4e1) * pow(C, 0.3e1) - 0.4e1 * C * pow(Xm, 0.3e1)
                        * pow(A, 0.4e1) + 0.6e1 * C * C * pow(A, 0.4e1) * Xm * Xm - 0.4e1 * pow(C, 0.4e1)
                        * pow(Bx, 0.3e1) * A + pow(Xm, 0.4e1) * pow(A, 0.4e1)) * pow(C, -0.2e1) / 0.24e2;


                return Mx_byo;
            }
        } else {
            return 0.d;
        }
    }


    public String getDesignReport(UnitType unitType) {

        double accuracy = 1e-6;
        double[] AC = get_AC();
        double A2 = AC[0];
        double C2 = AC[1];
        while (abs(A2 - A) > accuracy && abs(C2 - C) > accuracy) {
            AC = get_AC();
            A = A2;
            C = C2;
            A2 = AC[0];
            C2 = AC[1];
        }


        //MyDouble rA, rC, rVyz, rVxz, rMabtY, rMabtX;
        MyDouble rA = new MyDouble(A, m);
        MyDouble rC = new MyDouble(C, m);
        MyDouble rqmax = new MyDouble(getqmax(), kPa);
        MyDouble rVyz = new MyDouble(getShearVyz(), kN);
        MyDouble rMy = new MyDouble(getMy(), kNm);
        MyDouble rVxz = new MyDouble(getShearVxz(), kN);
        MyDouble rMx = new MyDouble(getMx(), kNm);


        if (unitType.equals(UnitType.SI)) {
            report = "Parameter A = " + rA.toString() + "\r\n" +
                    "Parameter C = " + rC.toString() + "\r\n" +
                    "Maximum bearing, qmax = " + rqmax.toString() + "\r\n" +
                    "Shear force, Vyz = " + rVyz.toString() + "\r\n" +
                    "Moment, My = " + rMy.toString() + "\r\n" +
                    "Shear force, Vxz = " + rVxz.toString() + "\r\n" +
                    "Moment, Mx = " + rMx.toString() + "\r\n";

        } else {
            report = "Parameter A = " + rA.toUnit(ft).toString() + "\r\n" +
                    "Parameter C = " + rC.toUnit(ft).toString() + "\r\n" +
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
        //4 corner points
        //draw boundary of bearing area

        MyDouble bA, bC;
        bA = new MyDouble(A, m);
        bC = new MyDouble(C, m);

        //intersection of line AC at y = mfBy
        //dimensions in mm
        float xfAC = (float) (bA.v() / bC.v() * (bC.v() - By * 1000.f) * mscale_geom);
        float yfAC = (float) (bC.v() / bA.v() * (bA.v() - Bx * 1000.f) * mscale_geom);//taken from bottom of footing

        PointF[] pointFs = new PointF[5];
        pointFs[0] = new PointF(0.f, 0.f);
        pointFs[1] = new PointF(xfAC, 0.f);
        pointFs[2] = new PointF(mfBx, mfBy - yfAC);
        pointFs[3] = new PointF(mfBx, mfBy);
        pointFs[4] = new PointF(0.f, mfBy);

        float[] boundaryPts = {pointFs[0].x, pointFs[0].y,
                pointFs[1].x, pointFs[1].y,
                pointFs[2].x, pointFs[2].y,
                pointFs[3].x, pointFs[3].y,
                pointFs[4].x, pointFs[4].y,
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
        boundary.lineTo(boundaryPts[8], boundaryPts[9]);
        boundary.lineTo(boundaryPts[0], boundaryPts[1]);
        //draw actual lines
        canvas.drawPath(boundary, paint);

    }


}

