package com.vedanshtechnologies.swipetouch.Activities;

import java.util.ArrayList;

public class Util {
    float array_f[];
    int i;
    float y;
  /*  public float randomFloatBetween(int minYValue, int maxYValue) {

        return minYValue;
    }*/

    public float randomFloatBetween(ArrayList<String>percentage) {

       for (i = 0;i <percentage.size(); i++){
         y = Float.parseFloat(percentage.get(i));
        array_f[i] = y;
        }


        return y;
    }
}
/*
for (int i = 0; i < fdata.length; i++){
        float y = Float.parseFloat(fdata[i]);
        array_f[i] = y;
        }*/
