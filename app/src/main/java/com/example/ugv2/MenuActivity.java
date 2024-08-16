package com.example.ugv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView ipText = findViewById(R.id.editTextIP);
        TextView videoPortText = findViewById(R.id.editTextVIDPRT);
        TextView commandPortText = findViewById(R.id.editTextCMDPRT);

        GlobalVariables globalVar = GlobalVariables.getInstance();
        ipText.setText(globalVar.getUgvIp());
        globalVar.setVideoPort(videoPortText.getText().toString());
        globalVar.setCommandPort(commandPortText.getText().toString());
        globalVar.setUgvIp(ipText.getText().toString());
        Button menuButton = findViewById(R.id.mainButtonMenu);
        Button turretButton = findViewById(R.id.trtButtonMenu);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVar.setUgvIp(ipText.getText().toString());
                ip = ipText.getText().toString();
                System.out.println("text ip: "+ ipText.getText().toString());
                System.out.println(" global variable ip: "+ globalVar.getUgvIp());
                System.out.println(" local variable ip: "+ ip);
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("ip", ip);
                startActivity(intent);
            }
        });

        turretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVar.setUgvIp(ipText.getText().toString());
                ip = ipText.getText().toString();
                System.out.println("text ip: "+ ipText.getText().toString());
                System.out.println(" global variable ip: "+ globalVar.getUgvIp());
                System.out.println(" local variable ip: "+ ip);
                Intent intent = new Intent(MenuActivity.this, TurretActivity.class);
                intent.putExtra("ip", ip);
                startActivity(intent);
            }
        });


    }
}