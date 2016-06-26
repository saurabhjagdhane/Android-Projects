package com.example.saurabh.accelerometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

/**
 * GraphView creates a scaled line or bar graph with x and y axis labels.
 * @author Arno den Hond
 *
 */

public class GraphView extends View {

    public static boolean BAR = false;
    public static boolean LINE = true;
    public static boolean flag = false;

    private Paint paint;
    private float[] xAxis;
    private float[] yAxis;
    private float[] zAxis;
    float border;
    float horstart;
    float height;
    float width;
    float max;
    float min;
    float diff;
    float graphheight;
    float graphwidth;
    private String[] horlabels;
    private String[] verlabels;
    private String title;
    private boolean type;

    public GraphView(Context context, float[] values, String title, String[] horlabels, String[] verlabels, boolean type) {
        super(context);
        if (values == null)
            values = new float[0];
        else
            this.zAxis = values;
        if (title == null)
            title = "";
        else
            this.title = title;
        if (horlabels == null)
            this.horlabels = new String[0];
        else
            this.horlabels = horlabels;
        if (verlabels == null)
            this.verlabels = new String[0];
        else
            this.verlabels = verlabels;
        this.type = type;
        paint = new Paint();
    }

    public void setValues(float[] x_array,float[] y_array, float[] z_array)
    {
        this.xAxis = x_array;
        this.yAxis = y_array;
        this.zAxis = z_array;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        border = 20;
        horstart = border * 2;
        height = getHeight();
        width = getWidth() - 1;
        max = getMax();
        min = getMin();
        diff = max - min;
        graphheight = height - (2 * border);
        graphwidth = width - (2 * border);


        paint.setTextAlign(Align.LEFT);
        int vers = verlabels.length - 1;
        for (int i = 0; i < verlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(20.0f);
            canvas.drawText(verlabels[i], 0, y, paint);
        }
        int hors = horlabels.length - 1;
        for (int i = 0; i < horlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float x = ((graphwidth / hors) * i) + horstart;
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Align.CENTER);
            if (i==horlabels.length-1)
                paint.setTextAlign(Align.RIGHT);
            if (i==0)
                paint.setTextAlign(Align.LEFT);
            paint.setColor(Color.WHITE);
            paint.setTextSize(20.0f);
            canvas.drawText(horlabels[i], x, height - 4, paint);
        }

        paint.setTextAlign(Align.CENTER);
        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

        if (max != min) {
             if (type==LINE) {
                linePlot(xAxis,canvas, "green");
                linePlot(yAxis,canvas, "yellow");
                linePlot(zAxis, canvas, "red");
            }
        }
    }

    private void linePlot(float[] values, Canvas canvas, String color) {
        paint.setColor(Color.parseColor(color));
        float datalength = values.length; //10
        float colwidth = (width - (2 * border)) / datalength;
        float halfcol = colwidth / 2;
        float lasth = 0;
        for (int i = 0; i < values.length; i++) {
            float val = values[i] - min;
            float rat = val / diff;
            float h = graphheight * rat;
            if (i > 0)
                paint.setColor(Color.parseColor(color));
            paint.setStrokeWidth(2.0f);

            if (flag == true)
            canvas.drawLine(((i - 1) * colwidth) + (horstart + 1) + halfcol, (border - lasth) + graphheight, (i * colwidth) + (horstart + 1) + halfcol, (border - h) + graphheight, paint);

            lasth = h;
        }

    }

    private float getMax() {
        float largest = Integer.MIN_VALUE;
        for (int i = 0; i < xAxis.length; i++)
            if (xAxis[i] > largest)
                largest = xAxis[i];


       largest = 10;
        return largest;
    }

    private float getMin() {
        float smallest = Integer.MAX_VALUE;
        for (int i = 0; i < xAxis.length; i++)
            if (xAxis[i] < smallest)
                smallest = xAxis[i];

        smallest = -10;
        return smallest;
    }

}