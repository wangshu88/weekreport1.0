package wang.WeekReport.alarm.receiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import wang.WeekReport.alarm.TimerArriveActivity;
import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

public class RemindReceiver extends BroadcastReceiver {


	private int advance_time;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) { //
		
		//从该context中启动提醒的activity，根据SDK文档的说明，需要加上addFlags()一句
		Intent remind_intent = new Intent(arg0, TimerArriveActivity.class);
		remind_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		//获取提前提醒的时间值,如果没有获取到则取默认值30分钟
//		int advance_time = arg1.getIntExtra("anvance_remindtime", 20);
		//这里模式一定要设置为MODE_MULTI_PROCESS，否则即使相应的xml文件中数据有更新，RemindReceiver中也不能获取更新后的数据，而是一直获取上次的数据， 除非清空缓存
		SharedPreferences pre = arg0.getSharedPreferences("user", Context.MODE_MULTI_PROCESS);
		advance_time = pre.getInt("RemindTime", 0);
		int hourOfDay= advance_time/100;
		int minute=advance_time%100;
        
		Calendar c = Calendar.getInstance(); 
		int current_hourOfDay = c.get(Calendar.HOUR_OF_DAY); 
		int current_minute = c.get(Calendar.MINUTE);
		//定义一个标志位，用来排除掉重复的提醒
		//循环判断当天的课前提醒
	    //如果到了设定的提醒时间，就启动提醒的activity
		if(hourOfDay==current_hourOfDay && minute==current_minute){
				arg0.startActivity(remind_intent);										
			}
		}
}
