package com.example.ugv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.ugv2.databinding.ActivityTurretBinding;
import com.longdo.mjpegviewer.MjpegView;

public class TurretActivity extends AppCompatActivity implements JoystickView.JoystickListener {
    private AppBarConfiguration appBarConfiguration;
    private ActivityTurretBinding binding;
    GlobalVariables globalVars = GlobalVariables.getInstance();

    private Handler handler = new Handler();
    private Runnable runnable;
    int[] dataChunk = new int[11];

    public void onJoystickMoved(float xPercent, float yPercent, int id){
        Log.d("Main Method", "X percent: " + xPercent + " Y percent: " + yPercent);
        Float xCalibrated = (xPercent*127)+127;
        Float yCalibrated = (yPercent*127)+127;



        dataChunk[0] = 0;
        dataChunk[1] = 0;
        dataChunk[2] = globalVars.getVideoMode();
        dataChunk[3] = globalVars.getTurretState();
        dataChunk[4] = globalVars.getLaserState();
        dataChunk[5] = globalVars.getTurretYawSetPoint();
        dataChunk[6] = globalVars.getTurretPitchSetPoint();
        dataChunk[7] = globalVars.getLockMode();
        dataChunk[8] = globalVars.isLockTargetCycled();
        dataChunk[9] = xCalibrated.intValue();
        dataChunk[10] = yCalibrated.intValue();
        System.out.println(xCalibrated);


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turret);

        Bundle extras = getIntent().getExtras();




        binding = ActivityTurretBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                // Update code here
                new SendCommandTask().execute(dataChunk);

                // Repeat this runnable every 200 milliseconds
                handler.postDelayed(this, 10);
            }
        }, 10);//old was binding.getRoot()

        globalVars.setVideoMode(3);

        MjpegView viewer = (MjpegView) findViewById(R.id.mjpegview);
        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        viewer.setAdjustHeight(true);
        viewer.setSupportPinchZoomAndPan(true);
        viewer.setUrl("http://"+globalVars.getUgvIp()+":"+globalVars.getVideoPort()+"/video_feed");
        viewer.startStream();

        ConstraintLayout constraintLayout = findViewById(R.id.turretConstraintLayout);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
    }
}