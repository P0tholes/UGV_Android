package com.example.ugv2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ugv2.databinding.ActivityMainBinding;
import com.longdo.mjpegviewer.MjpegView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public int videoMode;
    GlobalVariables globalVars = GlobalVariables.getInstance();
    int[] dataChunk = new int[11];

    private Handler handler = new Handler();
    private Runnable runnable;


    public void onJoystickMoved(float xPercent, float yPercent, int id){
        Log.d("Main Method", "X percent: " + xPercent + " Y percent: " + yPercent);
        Float xCalibrated = xPercent*127;
        Float yCalibrated = yPercent*127;

        dataChunk[0] = xCalibrated.intValue();
        dataChunk[1] = yCalibrated.intValue();
        dataChunk[2] = globalVars.getVideoMode();
        dataChunk[3] = 0;
        dataChunk[4] = 0;
        dataChunk[5] = 0;
        dataChunk[6] = 0;
        dataChunk[7] = 0;
        dataChunk[8] = 0;
        dataChunk[9] = 0;
        dataChunk[10] = 0;
        System.out.println(globalVars.getVideoMode());


    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();




        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                // Update code here
                new SendCommandTask().execute(dataChunk);

                // Repeat this runnable every 200 milliseconds
                handler.postDelayed(this, 10);
            }
        }, 10);//old was binding.getRoot()



        //setSupportActionBar(binding.toolbar);
        //getSupportActionBar().hide();
        globalVars.setVideoMode(0);
        Button videoModeButton = findViewById(R.id.videoMode);
        videoModeButton.setText("CLR");
        videoModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (globalVars.getVideoMode() == 2){

                    globalVars.setVideoMode(0);
                }
                else{
                    globalVars.setVideoMode(globalVars.getVideoMode()+1);
                }
                switch (globalVars.getVideoMode()){
                    case 0: videoModeButton.setText("CLR");
                        break;
                    case 1: videoModeButton.setText("IR");
                        break;
                    case 2: videoModeButton.setText("DPTH");
                        break;
                    default: videoModeButton.setText("CLR");
                }
                System.out.println("Changed Video Mode To: "+ globalVars.getVideoMode());

            }
        });
        String ip = "0.0.0.0";
        if (extras != null) {
            ip = extras.getString("ip");
            //The key argument here must match that used in the other activity
        }

        TextView ipText = findViewById(R.id.textIPView);
        ipText.setText(ip);
        System.out.println("received ip: "+ip);
        TextView ipErrorText = findViewById(R.id.networkErrorText);

        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        Button turretButton = findViewById(R.id.trtButton);
        turretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TurretActivity.class);
                startActivity(intent);
            }
        });





        MjpegView viewer = (MjpegView) findViewById(R.id.mjpegview);
        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        viewer.setAdjustHeight(true);
        viewer.setSupportPinchZoomAndPan(true);
        viewer.setUrl("http://"+globalVars.getUgvIp()+":"+globalVars.getVideoPort()+"/video_feed");
        viewer.startStream();


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        ConstraintLayout constraintLayout = findViewById(R.id.myConstraintLayout);
        //JoystickView joySurfaceView = new JoystickView(this);
        //JoystickView joySurfaceView = findViewById(R.id.rightJoyStick);
        //joySurfaceView.setVisibility(View.INVISIBLE);


        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        //constraintLayout.addView(joySurfaceView, params);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   // @Override
   // public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
            //    || super.onSupportNavigateUp();
   // }
}