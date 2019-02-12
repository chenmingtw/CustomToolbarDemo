package org.chenming.customtoolbardemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ArcBarView extends View {

    private int barColor = Color.DKGRAY;
    private int barWidth = 0;
    private int barHeight = 0;
    private int expandHeight = 0;

    public ArcBarView(Context context) {
        super(context);
    }

    public ArcBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArcBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Path path = new Path();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(barColor);
        paint.setAntiAlias(true);

        path.addRect(0, -barHeight * 2 + expandHeight, barWidth, expandHeight, Path.Direction.CW);
        path.arcTo(0, -barHeight + expandHeight, barWidth, barHeight + expandHeight, 0, 180, true);

        canvas.drawPath(path, paint);
    }

    public int getBarColor() {
        return barColor;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public int getExpandHeight() {
        return expandHeight;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        invalidate();
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public void setBarWidthAndHeight(int barWidth, int barHeight) {
        setBarWidth(barWidth);
        setBarHeight(barHeight);
    }

    public void setExpandHeight(int expandHeight) {
        this.expandHeight = expandHeight;
        invalidate();
    }
}
