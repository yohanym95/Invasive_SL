package com.example.yohan.invasiveplantcounter;

import java.security.PublicKey;

public class Datadetails {

    public String plantnAME;
    public String location;
    public double latitude;
    public double lonigtude;
    public String distributionCode;
    public String densityCode;
    public String remark;
    public String datetime;

    public Datadetails(){

    }

    public Datadetails(String plantnAME, String location, double latitude, double lonigtude, String distributionCode, String densityCode, String remark, String datetime) {
        this.plantnAME = plantnAME;
        this.location = location;
        this.latitude = latitude;
        this.lonigtude = lonigtude;
        this.distributionCode = distributionCode;
        this.densityCode = densityCode;
        this.remark = remark;
        this.datetime = datetime;
    }

}
