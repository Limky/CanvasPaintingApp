package com.example.sqisoft.moldcreateapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.manager.ColorManager;
import com.example.sqisoft.moldcreateapp.manager.DataManager;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by SQISOFT on 2017-04-06.
 */

public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //circle bitmap
    private Bitmap circleBitmap;

    private Bitmap mBackgroundBitmap;

    private int width,height;

    private Stack stack_draw_x = new Stack();
    private Stack stack_draw_y = new Stack();
    private Stack stack_x = new Stack();
    private Stack stack_y = new Stack();

    private String seletedColor;

    public DrawingView(Context context) {
        super(context);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupDrawing();
    }

    @Override
    public void setBackgroundResource(int resid) {
        Log.w("setBackgroundResource ======","");
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), resid);
        super.setBackgroundResource(resid);
    }
    private int strokeRadius;
    private ShapeDrawable mBrush;
    private Paint mBitmapPaint;
    public void setupDrawing(){
        Log.w("setupDrawing ======","");
        drawPath = new Path();
        drawPaint = new Paint();

        // BlurMaskFilter blur = new BlurMaskFilter( 10, BlurMaskFilter.Blur.NORMAL );
        // drawPaint.setMaskFilter(blur);

        seletedColor =  ColorManager.getInstance().getmSeletedColor();


        if(seletedColor.equals("#311B92")) {
            isEraser = false;
            drawPaint.setColor(Color.TRANSPARENT);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(55);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            System.out.println("setupDrawing.init color = "+seletedColor);
        }else {
            isEraser = false;
            drawPaint.setColor(Color.parseColor(seletedColor));
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(55);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            drawPaint.setAlpha(0x70);
            System.out.println("setupDrawing. color = "+seletedColor);
        }



    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        Log.w("onSizeChanged ======","");
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); //result

    }

    Bitmap mask;
    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        Log.w("onDraw ======","");

        switch (DataManager.getInstance().getSeletedMoldIndex()){

            case  1 :
                //   mDrawingView.setBackgroundResource(R.drawable.mold_01b);
                mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_01b_mask);
                break;

            case  2 :
                //   mDrawingView.setBackgroundResource(R.drawable.mold_02b);
                mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_02b_mask);
                break;

            case  3 :
                //      mDrawingView.setBackgroundResource(R.drawable.mold_03b);
                mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_03b_mask);
                break;

            case  4 :
                //  mDrawingView.setBackgroundResource(R.drawable.mold_04b);
                mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_04b_mask);
                break;

        }


        drawCanvas = new Canvas(canvasBitmap);

        drawPaint.setFilterBitmap(false);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) ); // DST_OUT DST_IN

        drawCanvas.drawBitmap(mask,0,0,drawPaint);

        if(!isEraser) {
            drawPaint.setXfermode(null);
            canvas.drawBitmap(canvasBitmap, 0, 0, null);
            canvas.drawPath(drawPath, drawPaint);
        }else{
            canvas.drawBitmap(canvasBitmap, 0, 0, null);
            canvas.drawPath(drawPath, drawPaint);
        }


        invalidate();
    }

    Boolean isEraser = false;
    Paint eraserPaint = new Paint();
    public void eraser() {
        isEraser = true;
        //eraserPaint.setColor(Color.TRANSPARENT);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(55);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setAlpha(0x35);

        //drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)); // 실질적인


    }

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Paint> paints = new ArrayList<>();
    private ArrayList<Paint> undonepaints = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }


    public void onClickUndo () {
        if (paths.size() > 0)  {
            undonepaints.add(paints.remove(paints.size()-1));
            undonePaths.add(paths.remove(paths.size()-1));

            Toast.makeText(getContext(),"페인트 size : "+paths.size(),Toast.LENGTH_SHORT).show();
            invalidate();
        } else  {
            Toast.makeText(getContext(),"더이상 페인트통에 페인트가 없다.",Toast.LENGTH_SHORT).show();
        }
    }


    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private void touch_start(float x, float y) {
        drawPath.reset();
        drawPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            drawPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }

    }

    private void touch_up() {
        drawPath.lineTo(mX, mY);
        if(!isEraser) {
            drawCanvas.drawPath(drawPath, drawPaint);
        }else{
            drawCanvas.drawPath(drawPath, drawPaint);
        }

        drawPath.reset();

    }



}
