package com.example.administrator.daygram;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener ,View.OnClickListener,
        DialogInterface.OnClickListener{

    private ListView lv;
    ABAdapter abAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*获取当天日期*/
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int today = calendar.get(Calendar.DATE);
        int week = calendar.get(Calendar.DAY_OF_WEEK);


        Object o=getObject("object.dat");   //试图从object.dat文件读取数据

        if(o!=null&&((ArrayList<Object>)o).size()<=today){  //访问存储文件成功且月份没跳转

            /*将读出的对象赋值应用给Class_Parent的静态成员（ArrayList<Object>）data*/
            Class_Parent.data=(ArrayList<Object>)o;
            for(int i=Class_Parent.data.size()+1;i<=today;i++){
                calendar.set(year,month-1,i);
                int realWeek=calendar.get(Calendar.DAY_OF_WEEK);
                Class_Parent class_parent = new Class_Parent(year, month, i+1, realWeek);
                Class_Parent.data.add(class_parent);
            }
        }

        /*若为初次启动或月份跳转到下一个月*/
        else{
            /*创建行的ArrayList对象*/
            Class_Parent.data=new ArrayList<>();
            /*当前月份的第一天到当天所有日记为未编辑*/
            for (int i = 0; i < today; i++) {
                calendar.set(year,month-1,i+1);
                int realWeek=calendar.get(Calendar.DAY_OF_WEEK);
                Class_Parent class_parent = new Class_Parent(year, month, i+1, realWeek);
                Class_Parent.data.add(class_parent);
            }
        }
        saveObject("object.dat");   //保存数据到文件object.dat
  /*      if(o==null){
            Class_Parent.data=new ArrayList<>();
            for (int i = 0; i < today; i++) {
                calendar.set(year,month-1,i+1);
                int realWeek=calendar.get(Calendar.DAY_OF_WEEK);
                Class_Parent class_parent = new Class_Parent(year, month, i+1, realWeek);
                Class_Parent.data.add(class_parent);
            }
            saveObject("object.dat");
        }
        else {
            Class_Parent.data = (ArrayList<Object>) o;
            if(Class_Parent.data.size()<=today){
                for(int i=Class_Parent.data.size()+1;i<=today;i++){
                    calendar.set(year,month-1,i);
                    int realWeek=calendar.get(Calendar.DAY_OF_WEEK);
                    Class_Parent class_parent = new Class_Parent(year, month, i+1, realWeek);
                    Class_Parent.data.add(class_parent);
                }
            }
        }*/

        lv = (ListView) findViewById(R.id.lv);      //ListView对象

        String m;
        TextView monthView=(TextView)findViewById(R.id.monthView);
        switch (month){
            case 1:m="Jan";break;
            case 2:m="Feb";break;
            case 3:m="Mar";break;
            case 4:m="Apr";break;
            case 5:m="May";break;
            case 6:m="Jun";break;
            case 7:m="Jul";break;
            case 8:m="Aug";break;
            case 9:m="Sep";break;
            case 10:m="Oct";break;
            case 11:m="Nov";break;
            case 12:m="Dec";break;
            default:m="";break;
        }
        monthView.setText(m);   //设置月份信息

        TextView yearView=(TextView)findViewById(R.id.yearView);
        yearView.setText(String.valueOf(year)); //设置年份信息

        abAdapter = new ABAdapter(this, Class_Parent.data);//绑定自定义的ABAdapter

        lv.setAdapter(abAdapter);//为ListView绑定适配器
        lv.setOnItemClickListener(this);//将MainActivity登录为ListView的Item的单击监听器
        lv.setOnItemLongClickListener(this);//登录为长按监听器

        ImageView editToday=(ImageView)findViewById(R.id.editToday);
        editToday.setOnClickListener(this);//登录为添加当天日记按钮的单击监听器
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(this, Ac.class); //创建意图，
        it.putExtra(getString(R.string.array_list_index), position);//夹带当前Item的位置
        startActivityForResult(it, 0);  //跳转活动
    }


    private int deletePostion;  //记录长按删除的item位置
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Object object=Class_Parent.data.get(position);
        if(object instanceof Class_A){      //若为已编辑的日记
            deletePostion=position;
            new AlertDialog.Builder(this).setMessage("是否删除日记？")
                    .setTitle("提示").setNegativeButton("否",this)
                    .setPositiveButton("是",this).show();    //弹出删除提醒对话框
            return true;
        }
        return true;
    }

/*从编辑日记的Activity跳转回MainActivity后的处理*/
    protected void onActivityResult(int retquestCode, int resultCode, Intent it) {
        if (resultCode == RESULT_OK) {
            if (retquestCode == 0) {
                int index = it.getIntExtra(getString(R.string.array_list_index), -1);
                if (index != -1) {
                    saveObject("object.dat");   //保存已改变的数据
                    abAdapter.notifyDataSetChanged();   //提醒ListView更新显示内容
                }
            }
        }
    }


    public int daysNumOf(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    return 29;
                else return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(v==findViewById(R.id.editToday)){    //添加当天日记的单击处理
            Intent it = new Intent(this, Ac.class);
            it.putExtra(getString(R.string.array_list_index),//向新的Activity夹带当前Item的位置
                    Class_Parent.data.size()-1);
            startActivityForResult(it, 0);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){//点击对话框肯定按钮
            Class_A object=(Class_A)Class_Parent.data.get(deletePostion);
            int y= object.getDate(Class_Parent.YEAR);
            int m=object.getDate(Class_Parent.MONTH);
            int d=object.getDate(Class_Parent.DAY);
            int w=object.getDate(Class_Parent.WEEK);
            Class_Parent newItem=new Class_Parent(y, m, d, w);
            Class_Parent.data.set(deletePostion,newItem);//改变删除位置的类
            saveObject("object.dat");//保存数据
            abAdapter.notifyDataSetChanged();//刷新ListView
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE){//点击对话框否定按钮，不做处理

        }
    }




    class SaveObjectDemo implements Serializable {
        ArrayList<Object> arrayList;
    }

    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Class_Parent.data);//将Class_Parent的静态成员data作为数据保持到文件
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }

}
