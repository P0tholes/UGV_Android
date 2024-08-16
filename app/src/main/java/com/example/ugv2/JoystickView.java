package com.example.ugv2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    float centerX;

    float centerY;

    float baseRadius;

    float hatRadius;

    private JoystickListener joystickCallback;

    private void setupDimensions(){
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 4;
        hatRadius = Math.min(getWidth(), getHeight()) / 6;
    }

    public void setVisibility(int visibility){

    }


    public JoystickView(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes, int style){
        super(context, attributes,style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes){
        super(context,attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener){
            joystickCallback = (JoystickListener) context;
        }
    }

    private void drawJoystick(float newX, float newY, boolean isPressed){
        int isPressedColor = 0;
        if(getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            colors.setARGB(255,  0,  250,  0);
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setARGB(255,  0,  0,  0);
            myCanvas.drawCircle(centerX, centerY, baseRadius-5, colors);
            colors.setARGB(255,  0,  255,  0);
            myCanvas.drawCircle(newX, newY, hatRadius, colors);
            if (isPressed){
                isPressedColor = 255;
            }
            else {
                isPressedColor = 0;
            }
            colors.setARGB(255,  0,  isPressedColor,  0);
            myCanvas.drawCircle(newX, newY, hatRadius-5, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setupDimensions();


    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    public boolean onTouch(View v, MotionEvent e){
        if(v.equals(this)){
            if(e.getAction() != e.ACTION_UP){
                float displacement = (float) Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY, 2));
                if(displacement < baseRadius){
                    drawJoystick(e.getX(),e.getY(),true);
                    joystickCallback.onJoystickMoved((e.getX() - centerX)/baseRadius, (e.getY() - centerY)/baseRadius, getId());

                }
                else{
                    float ratio = baseRadius / displacement;
                    float constraintX = centerX + (e.getX() - centerX) * ratio;
                    float constraintY = centerY + (e.getY() - centerY) * ratio;
                    drawJoystick(constraintX,constraintY,true);
                    joystickCallback.onJoystickMoved((constraintX-centerX)/baseRadius, (constraintY-centerY)/baseRadius, getId());
                }


            }
            else{

                drawJoystick(centerX,centerY,false);
                joystickCallback.onJoystickMoved(0,0,getId());
            }

        }
        return true;
    }

    public interface JoystickListener

    {

        void onJoystickMoved(float xPercent, float yPercent, int source);

    }

}
