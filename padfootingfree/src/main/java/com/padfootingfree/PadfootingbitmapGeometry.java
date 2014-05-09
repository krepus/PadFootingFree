package com.padfootingfree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Typeface;

import static java.lang.Math.max;

public class PadfootingbitmapGeometry {

    public Canvas canvas;
    public Bitmap mbitmap_final;

    public float mfBx, mfBy, mfex, mfey;
    public int mbitmapWidth, mbitmapHeight;

    public double mscale_geom;
    public float mx0, my0, mtxtht;
    public PointF[] mPtsPF;
    public Paint mpaint;

    MyDouble Bx, By, ex, ey;


    //constructor
    public PadfootingbitmapGeometry(
            Bitmap bitmap,
            int txtht,
            MyDouble Bx,
            MyDouble By,
            MyDouble ex,
            MyDouble ey) {
        //set fields for actual dim input by user
        mbitmap_final = bitmap;
        this.Bx = Bx;
        this.By = By;
        this.ex = ex;
        this.ey = ey;

        mbitmapWidth = bitmap.getWidth();
        mbitmapHeight = mbitmapWidth;
        mscale_geom = mbitmapWidth * 0.7d / max(Bx.v(), By.v());
        mtxtht = txtht;  //20 for hdpi


        //set class fields value scaled to bitmap size
        mfBx = (float) (Bx.v() * mscale_geom);
        mfBy = (float) (By.v() * mscale_geom);
        mfex = (float) (ex.v() * mscale_geom);
        mfey = (float) (ey.v() * mscale_geom);

        //adjust bitmap width

        //init following
        mx0 = mbitmapWidth / 2.f;
        my0 = mbitmapHeight / 2.f;

        //set padfooting corner coord, in clockwise order
        mPtsPF = new PointF[4];
        mPtsPF[0] = new PointF(0.f, 0.f);
        mPtsPF[1] = new PointF(mfBx, 0.f);
        mPtsPF[2] = new PointF(mPtsPF[1].x, mfBy);
        mPtsPF[3] = new PointF(mPtsPF[0].x, mPtsPF[2].y);


    }


    public void drawPFplan(Paint paint) {
        //4 corner points

        float[] pts = {mPtsPF[0].x, mPtsPF[0].y,
                mPtsPF[2].x, mPtsPF[2].y};


        //create matrix to translate
        Matrix matrix = new Matrix();
        //translate points using matrix object
        matrix.setTranslate(mx0 - (mfBx / 2.f), my0 - (mfBy / 2.f));
        matrix.mapPoints(pts);  //apply translation matrix to pts!!!
        //draw actual lines
        canvas.drawRect(pts[0], pts[1], pts[2], pts[3], paint);


    }


    public void drawCenterLine(Paint paint) {
        //draw centerlines
        //Points
        PointF[] endpoint = new PointF[4];
        endpoint[0] = new PointF(0, 0.f); //left end, horizontal line
        endpoint[1] = new PointF(mfBx, 0.f);
        endpoint[2] = new PointF(0.f, 0.f);//vertical
        endpoint[3] = new PointF(0.f, mfBy);


        //lines
        float[] horline = {endpoint[0].x, endpoint[0].y, endpoint[1].x, endpoint[1].y};
        float[] verline = {endpoint[2].x, endpoint[2].y, endpoint[3].x, endpoint[3].y};

        Matrix matrix = new Matrix();
        matrix.setTranslate(mx0, my0 - mfBy / 2.f);
        matrix.mapPoints(verline);
        matrix.reset();
        matrix.setTranslate(mx0 - mfBx / 2.f, my0);
        matrix.mapPoints(horline);

        //draw lines
        canvas.drawLines(horline, paint);
        canvas.drawLines(verline, paint);

        //draw labels X & Y
        canvas.drawText("Y", verline[0], verline[1] - mtxtht, paint);
        canvas.drawText("X", horline[2] + 0.5f * mtxtht, horline[3] + 0.5f * mtxtht, paint);


    }

    /**
     * @param length,           actual length of dim
     * @param extL1             offset of dim line from measure face
     * @param dx,               translation along x
     * @param dy,               translation along y
     * @param dtheta,           rotation, 0=left to right, and dim line at bottom of object
     * @param scale_dimoverall, overall scale of dimstyle
     */

    public void drawDim(MyDouble length, float extL1, float dx, float dy, float dtheta, float scale_dimoverall) {
        //basic dimensions
        float dimLength = (float) (length.v() * mscale_geom);
        float txtheight = scale_dimoverall * mtxtht;
        //float extL1 = 2.f * txtheight;
        float extL2 = txtheight / 2.f;
        float arrbase = txtheight / 2.5f;
        float gap_ext = txtheight / 4.5f;
        float arrheight = txtheight;

        //Points
        PointF[] ptdim = new PointF[13];
        ptdim[0] = new PointF(0.f, 0.f);
        ptdim[1] = new PointF(dimLength, 0.f);
        ptdim[2] = new PointF(0.f, gap_ext);
        ptdim[3] = new PointF(0.f, extL1);
        ptdim[4] = new PointF(0.f, ptdim[3].y + extL2);
        ptdim[5] = new PointF(arrheight, ptdim[3].y - arrbase / 2.f);
        ptdim[6] = new PointF(arrheight, ptdim[5].y + arrbase);
        ptdim[7] = new PointF(ptdim[1].x - arrheight, ptdim[5].y);
        ptdim[8] = new PointF(ptdim[7].x, ptdim[6].y);
        ptdim[9] = new PointF(ptdim[1].x, ptdim[3].y);
        ptdim[10] = new PointF(ptdim[1].x, ptdim[4].y);
        ptdim[11] = new PointF(ptdim[1].x, ptdim[2].y);


        //lines
        float[] pts = {ptdim[2].x, ptdim[2].y, ptdim[4].x, ptdim[4].y,
                ptdim[10].x, ptdim[10].y, ptdim[11].x, ptdim[11].y,
                ptdim[3].x, ptdim[3].y, ptdim[9].x, ptdim[9].y,
                ptdim[3].x, ptdim[3].y, ptdim[5].x, ptdim[5].y,
                ptdim[3].x, ptdim[3].y, ptdim[6].x, ptdim[6].y,
                ptdim[7].x, ptdim[7].y, ptdim[9].x, ptdim[9].y,
                ptdim[8].x, ptdim[8].y, ptdim[9].x, ptdim[9].y};

        // dim label
        Paint paint_label = new Paint(mpaint);
        paint_label.setTextSize(mtxtht);
        paint_label.setTextAlign(Paint.Align.CENTER);
        paint_label.setStyle(Paint.Style.FILL);
        String strdim = length.toString();
        float[] pts_path = {ptdim[3].x, ptdim[3].y, ptdim[9].x, ptdim[9].y};

        //extended dim line incase pts_path is less than text width
        float dimtxtlength = paint_label.measureText(strdim);
        //PointF ptdim12 = new PointF(ptdim[9].x + dimtxtlength, ptdim[9].y);
        ptdim[12] = new PointF(ptdim[9].x + dimtxtlength, ptdim[9].y);
        float[] pts_path2 = {ptdim[9].x, ptdim[9].y, ptdim[12].x + txtheight, ptdim[12].y};

        //rotate about pt 0
        Matrix matrix = new Matrix();
        matrix.setRotate(dtheta);
        matrix.mapPoints(pts);
        matrix.mapPoints(pts_path);
        matrix.mapPoints(pts_path2);

        //translate by dx, dy
        matrix.reset();
        matrix.setTranslate(dx, dy);
        matrix.mapPoints(pts);
        matrix.mapPoints(pts_path);
        matrix.mapPoints(pts_path2);

        //path of text
        Path path = new Path();
        float gaplabel = 7.5f / 18.f * txtheight;
        float voffset = -gaplabel;
        if (dtheta > 89.f && dtheta < 269.f) {
            //voffset = -gaplabel;
            if (dimLength > dimtxtlength) {  //textwidth fits in teh space
                path.moveTo(pts_path[2], pts_path[3]);
                path.lineTo(pts_path[0], pts_path[1]);
            } else {
                path.moveTo(pts_path2[2], pts_path2[3]);
                path.lineTo(pts_path2[0], pts_path2[1]);
                //draw dim line extension
                canvas.drawLines(pts_path2, mpaint);
            }

        } else {
            //voffset = gaplabel + txtheight - mtxtht / 5.f * scale_dimoverall;


            if (dimLength > dimtxtlength) {  //textwidth fits in teh space
                path.moveTo(pts_path[0], pts_path[1]);
                path.lineTo(pts_path[2], pts_path[3]);
            } else {
                path.moveTo(pts_path2[0], pts_path2[1]);
                path.lineTo(pts_path2[2], pts_path2[3]);
                //draw dim line extension
                canvas.drawLines(pts_path2, mpaint);
            }
        }

        //draw lines
        canvas.drawLines(pts, mpaint);

        //draw label on path
        canvas.drawTextOnPath(length.toString(), path, 0.f, voffset, paint_label);
    }

    public void drawPointLoadLocation(Paint paint) {

        //center coordinate of load point
        final float mfDB = 10.f; //diamter of circle to indicate locatioin of point load

        //points array, coor of center of each circles
        // float[] pts = {cx1, cy1, cx2, cy2, cx3, cy3, cx4, cy4};
        float[] pts = new float[2];
        pts[0] = 0.f;
        pts[1] = 0.f;

        //create matrix to translate
        Matrix matrix = new Matrix();
        //translate points using matrix object
        matrix.setTranslate(mx0 - mfex, my0 + mfey);
        matrix.mapPoints(pts);  //apply translation matrix to pts!!!

        //draw actual circles
        canvas.drawCircle(pts[0], pts[1], mfDB / 2.f, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(pts[0], pts[1], mfDB, paint);

    }

    public void drawPFGeometryAndLoadPoint(Canvas canvas) {

        //mbitmap_final = bitmap;

        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        //draw filled rectangle
        mpaint.setColor(Color.WHITE);
        mpaint.setStyle(Paint.Style.FILL);
        drawPFplan(mpaint);

        //draw boundary
        mpaint.setColor(Color.BLUE);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(2.f);
        drawPFplan(mpaint);

        //draw dimension at bottom of plan
        mpaint.setStrokeWidth(1.f);
        drawDim(Bx, 2 * mtxtht, mx0 - mfBx / 2.f, my0 + mfBy / 2.f, 0.f, 1.f);
        //draw dim at left side of plan
        drawDim(By, 1.5f * mtxtht, mx0 - mfBx / 2.f, my0 - mfBy / 2.f, 90.f, 1.f);
        //draw dim ex
        drawDim(ex, 1.5f * mtxtht, mx0 - mfex, my0 + mfey, 0.f, 1.f);
        //draw dim ex
        drawDim(ey, 1.5f * mtxtht, mx0 - mfex, my0 + 0.f, 90.f, 1.f);

        //draw point load location
        Paint reopaint = mpaint;
        reopaint.setStyle(Paint.Style.FILL);
        reopaint.setColor(Color.DKGRAY);
        reopaint.setStrokeWidth(1.f);
        drawPointLoadLocation(reopaint);


        //draw CL and global axes labels
        Paint CLpaint = new Paint();
        CLpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        CLpaint.setPathEffect(new DashPathEffect(new float[]{40, 10, 10, 10}, 0));
        CLpaint.setColor(Color.RED);
        CLpaint.setTextSize(1.2f * mtxtht);
        CLpaint.setTypeface(Typeface.create("Helvetica", Typeface.BOLD));
        drawCenterLine(CLpaint);
    }

}
