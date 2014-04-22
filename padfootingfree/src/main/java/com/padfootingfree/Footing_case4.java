package com.padfootingfree;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by j0sua3 on 19/04/2014.
 */
public class Footing_case4 implements Padfooting {

    double Bx, By, ex, ey, V;
    double A, C;
    String report;

    Footing_case4(double V,
                  double Bx,
                  double By,
                  double ex,
                  double ey) {
        this.V = V;
        this.Bx = Bx;
        this.By = By;
        this.ex = ex;
        this.ey = ey;
        A = -4 * ex + 2 * Bx;
        C = 2 * By - 4 * ey;

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
        report = "A = " + Double.toString(A) + " and C = " + Double.toString(C);

    }

    public String getReport() {
        return report;
    }

    public double[] get_a_type4() {
        double[] a = new double[2];
        double tgalpha;
        tgalpha = C / A;
        a[0] = (C - By) / tgalpha;
        a[1] = Bx - a[0];
        a[2] = Bx - a[0];

        return a;
    }

    public double[] get_c_type4() {
        double[] c = new double[2];
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
        double[] F = new double[2];
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
        double[] e = new double[3];
        double Ug;
        Ug = get_ug();
        e[0] = -Ug + a[0] / 0.2e1;
        e[1] = a[0] + a[1] / 0.2e1 - Ug;
        e[2] = a[0] + a[2] / 0.3e1 - Ug;
        return e;
    }

    public double[] get_f() {
        double[] c = get_c_type4();
        double[] f = new double[3];
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
        Ist = -Math.pow(a[2], 0.2e1) * Math.pow(c[2], 0.2e1) / 0.72e2;
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

}
