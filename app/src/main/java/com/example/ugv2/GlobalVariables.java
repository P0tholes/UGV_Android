package com.example.ugv2;

public class GlobalVariables {
    private static GlobalVariables instance;
    private Integer videoMode = 0;
    private String ugvIP;
    private String videoPort;
    private String commandPort;

    private Integer laserState = 0;

    private Integer turretState = 0;

    private Integer turretYawSetPoint = 0;

    private Integer turretPitchSetPoint = 0;

    private Integer lockMode = 0;

    private Integer cycleTarget = 0;

    private GlobalVariables() {}

    public static synchronized GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public Integer getLaserState(){ return laserState;}
    public void setLaserState(Integer state){this.laserState = state;}

    public Integer getTurretState(){ return laserState;}
    public void setTurretState(Integer state){this.turretState = state;}

    public Integer getTurretYawSetPoint(){ return turretYawSetPoint;}
    public void setTurretYawSetPoint(Integer turretYawSetPoint){this.turretYawSetPoint = turretYawSetPoint;}

    public Integer getTurretPitchSetPoint(){ return turretPitchSetPoint;}
    public void setTurretPitchSetPoint(Integer turretPitchSetPoint){this.turretPitchSetPoint = turretPitchSetPoint;}

    public Integer getLockMode(){ return lockMode;}
    public void setLockMode(Integer lockMode){this.lockMode = lockMode;}

    public Integer isLockTargetCycled(){ return cycleTarget;}
    public void cycleLockTarget(Integer state){this.cycleTarget = state;}

    public String getUgvIp() {
        return ugvIP;
    }

    public String getVideoPort() {
        return videoPort;
    }

    public String getCommandPort() {
        return commandPort;
    }

    public Integer getVideoMode() {
        return videoMode;
    }

    public void setVideoMode(Integer value) {
        this.videoMode = value;
    }

    public void setVideoPort(String value) {
        this.videoPort = value;
    }

    public void setUgvIp(String value) {
        this.ugvIP = value;
    }

    public void setCommandPort(String value) {
        this.commandPort = value;
    }
}
