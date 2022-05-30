package com.bringtheaction.manager;


import static android.graphics.PorterDuff.Mode.CLEAR;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class RecyclerSwipeHelper extends ItemTouchHelper.SimpleCallback {

    private int intrinsicRightHeight;
    private int intrinsicRightWidth;

    private int intrinsicLeftHeight;
    private int intrinsicLeftWidth;

    private final int swipeLeftColor;
    private final int swipeRightColor;

    private final Paint clearPaint;
    private final Drawable swipeRightIcon;
    private final Drawable swipeLeftIcon;
    private final ColorDrawable background = new ColorDrawable();


    public RecyclerSwipeHelper(@ColorInt int swipeRightColor, @ColorInt int swipeLeftColor, @DrawableRes int swipeRightIconResource, @DrawableRes int swipeLeftIconResource, Context context) {
        super(0, LEFT|RIGHT);

        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(CLEAR));



        this.swipeLeftColor = swipeLeftColor;
        this.swipeRightColor = swipeRightColor;

        this.swipeRightIcon = ContextCompat.getDrawable(context, swipeRightIconResource);

        this.swipeLeftIcon = ContextCompat.getDrawable(context, swipeLeftIconResource);


        intrinsicRightHeight = swipeRightIcon.getIntrinsicHeight();
        intrinsicRightWidth = swipeRightIcon.getIntrinsicWidth();

        intrinsicLeftHeight = swipeLeftIcon.getIntrinsicHeight();
        intrinsicLeftWidth = swipeLeftIcon.getIntrinsicWidth();

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCancled = (dX == 0f) && !isCurrentlyActive;

        if (isCancled) {
            clearCanvas(c, itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }
        if(dX < 0) {

            // on swipe from right ----> left


            background.setColor(swipeLeftColor);
            background.setBounds((int) (itemView.getRight() + dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
            background.setAlpha(50);
            background.draw(c);

            // Calculations for icon bounds
            int itemTop = itemView.getTop() + (itemHeight - intrinsicLeftHeight) / 2;
            int itemMargin = (int) ((itemHeight - (intrinsicLeftHeight)*1.75) / 2);
            int itemLeft = itemView.getRight() - itemMargin - intrinsicLeftWidth;
            int itemRight = itemView.getRight() - itemMargin;
            int itemBottom = itemTop + intrinsicLeftHeight;

            int alpha = ((int)((-itemView.getTranslationX()/itemView.getWidth())*510));
            if(alpha > 255) alpha = 255;

            swipeLeftIcon.setAlpha(alpha);
            swipeLeftIcon.setBounds(itemLeft, itemTop, itemRight, itemBottom);
            swipeLeftIcon.draw(c);

        }
        else {

            // On swipe from left ---> Right

            // calculation for icon bounds
            int itemTop = itemView.getTop() + (itemHeight - intrinsicRightHeight) / 2;
            int itemMargin = (int) ((itemHeight - (intrinsicRightHeight)*1.75) / 2);
            int itemLeft = itemView.getLeft() + itemMargin;
            int itemRight = itemView.getLeft() + itemMargin + intrinsicRightWidth;
            int itemBottom = itemTop + intrinsicRightHeight;

            background.setColor(swipeRightColor);
            background.setBounds((int) (itemView.getLeft() + dX), itemView.getTop(), itemView.getLeft(), itemView.getBottom());
            background.setAlpha(50);
            background.draw(c);


            int alpha = ((int)((itemView.getTranslationX()/itemView.getWidth())*510));
            if(alpha > 255) alpha = 255;

            swipeRightIcon.setAlpha(alpha);
            swipeRightIcon.setBounds(itemLeft, itemTop, itemRight, itemBottom);
            swipeRightIcon.draw(c);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        if(c != null) c.drawRect(left, top, right, bottom, clearPaint);
    }
}
