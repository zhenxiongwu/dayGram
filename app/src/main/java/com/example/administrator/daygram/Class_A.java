package com.example.administrator.daygram;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/25.
 */
public class Class_A extends Class_Parent implements Serializable{

    private String dailyContent;

    public Class_A(int y, int m, int d, int w){
       super(y,m,d,w);
    }

    public void writeDaily(String dailyContent){
        this.dailyContent=dailyContent;
    }

    public String getDailyContent() {
        return dailyContent;
    }

    public String getDailyContent_Pre(){
        int textSize=20<=dailyContent.length()?20:dailyContent.length();
        return dailyContent.substring(0,textSize)+"...";
    }

}
