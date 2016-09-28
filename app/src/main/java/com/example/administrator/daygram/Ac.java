package com.example.administrator.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Ac extends Activity implements View.OnClickListener {

    private Intent it;
    private int itemIndex;
    private Object object;

    private int year;
    private int month;
    private int day;
    private int week;

    private TextView title;
    private EditText dailyContent;
    private ImageView done;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        done = (ImageView)findViewById(R.id.done);

        done.setOnClickListener(this);

        it = getIntent();
        itemIndex = it.getIntExtra(getString(R.string.array_list_index), 0);
        object = Class_Parent.data.get(itemIndex);

        year=((Class_Parent) object).getDate(Class_Parent.YEAR);
        month=((Class_Parent)object).getDate(Class_Parent.MONTH);
        day=((Class_Parent)object).getDate(Class_Parent.DAY);
        week=((Class_Parent)object).getDate(Class_Parent.WEEK);

        title=(TextView)findViewById(R.id.title);
        String titleInfo;
        switch (week){
            case 1:titleInfo="SUNDAY/";
                break;
            case 2:titleInfo="MONDAY/";
                break;
            case 3:titleInfo="TUESDAY/";
                break;
            case 4:titleInfo="WEDNESDAY/";
                break;
            case 5:titleInfo="THURSDAY/";
                break;
            case 6:titleInfo="FRIDAY/";
                break;
            case 7:titleInfo="SATURDAY/";
                default:titleInfo="";
                    break;
        }
        switch (month){
            case 1:titleInfo+="JANUARY";
                break;
            case 2:titleInfo+="FEBRUARY";
                break;
            case 3:titleInfo+="MARCH";
                break;
            case 4:titleInfo+="APRIL";
                break;
            case 5:titleInfo+="MAY";
                break;
            case 6:titleInfo+="JUNE";
                break;
            case 7:titleInfo+="JULY";
                break;
            case 8:titleInfo+="AUGUST";
                break;
            case 9:titleInfo+="SEPTEMBER";
                break;
            case 10:titleInfo+="OCTOBER";
                break;
            case 11:titleInfo+="NOVEMBER";
                break;
            case 12:titleInfo+="DECEMBER";
                break;
            default:titleInfo+="";
                break;
        }
        titleInfo+=(String.valueOf(day)+"/"+String.valueOf(year));

        title.setText(titleInfo);


        dailyContent = (EditText)findViewById(R.id.edit_daily);
        object=Class_Parent.data.get(itemIndex);
        if(object instanceof Class_A){
            dailyContent.setText(((Class_A) object).getDailyContent());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.done)) {
            String editContent=dailyContent.getText().toString();

            if(object instanceof Class_Parent){
                Class_A newItem=new Class_A(year, month, day, week);
                newItem.writeDaily(editContent);
                Class_Parent.data.set(itemIndex,newItem);
            }
            else{
                ((Class_A)object).writeDaily(editContent);
            }
            
         /*
            int stringSize=20<=editContent.length()?20:editContent.length();
            it.putExtra(getString(R.string.daily_content),
                   editContent.substring(0,stringSize)+"...");
*/
            setResult(RESULT_OK,it);
            finish();
        }
    }
}
