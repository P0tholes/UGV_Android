package com.example.ugv2;

import android.os.AsyncTask;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Looper;
import android.os.Handler;

public class SendCommandTask extends AsyncTask<int[], Void, Void> {

    private static final long THROTTLE_INTERVAL_MS = 10; // Adjust the interval as needed
    private long lastRequestTime = 0;

    private HttpURLConnection connection;
    //private String response
    GlobalVariables globalVars = GlobalVariables.getInstance();

    @Override
    protected Void doInBackground(int[]... params) {
        int[] command = params[0];

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTime < THROTTLE_INTERVAL_MS) {
            return null; // Skip the request if it's too soon
        }
        try {
            // Reuse the same connection object
            if (connection == null) {
                URL url = new URL("http://"+globalVars.getUgvIp()+":"+globalVars.getCommandPort()+"/control_robot");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                //System.out.println("connectionOpened");
                connection.setDoOutput(true);
            }

            sendCommand(command);
            lastRequestTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendCommand(int[] command) {
        try {
            // Create the JSON payload
            String jsonInputString = String.format("{\"command\": [%d, %d, %d, %d, %d, %d, %d, %d, %d]}",
                    command[0],
                    command[1],
                    command[3],
                    command[4],
                    command[5],
                    command[6],
                    command[7],
                    command[8],
                    command[9],
                    command[10]

            );

            //0: robot drive x (-127 - 127)
            //1: robot drive y (-127 - 127)
            //2: video mode
            //3: energize turret
            //4: laser
            //5: turret yaw set point (0 - 255)
            //6: turret pitch set point (0 - 255)
            //7: turret lock mode
            //8: cycle lock
            //9: slew rate yaw (overrides set point)
            //10: slew rate pitch (overrides set point)



            // Write the JSON payload to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
                //System.out.println(command[0]);
            }

            // Get the response code (optional)
            int responseCode = connection.getResponseCode();

            // Handle the response if needed

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}