package wang.WeekReport.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sun.mail.imap.protocol.INTERNALDATE;


import wang.WeekReport.MainActivity;
import wang.WeekReport.NewActivity;
import wang.WeekReport.R;
import wang.WeekReport.mydata.AllReportAdapter;
import wang.WeekReport.mydata.GlobalVal;
import wang.WeekReport.mydata.NewReportAdapter;
import wang.WeekReport.mydata.Report;
import wang.WeekReport.mydata.UserPreferences;
import wang.WeekReport.mydata.WeekDay;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmSettingsActivity extends Activity {
	
	private  UserPreferences userValue;
	//声明一个AlarmManager对象，用来开启课前提醒服务
	private AlarmManager alarmManager = null;
	//声明一个PendingIntent对象，用来指定alarmManager要启动的组件
	private PendingIntent pi = null;
	private Intent alarm_receiver = null;
	 
	
	private int time_choice = 0;
	
	private ToggleButton switch_remindButton;
	private TextView tvTime;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		//将该activity加入到MyApplication对象实例容器中
		//MyApplication.getInstance().addActivity(this);
		
		//声明一个获取系统音频服务的类的对象
		final AudioManager audioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
		//从MainAcivity中获取原始设置的铃声模式
		Intent intent = getIntent();
		final int orgRingerMode = intent.getIntExtra("mode_ringer", AudioManager.RINGER_MODE_NORMAL);
		//获取系统的闹钟定时服务
		alarmManager = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
		
		//指定alarmManager要启动的组件
		alarm_receiver = new Intent(AlarmSettingsActivity.this,ResultReceiver.class);
//		alarm_receiver.putExtra("anvance_remindtime", time_choice);
		pi = PendingIntent.getBroadcast(AlarmSettingsActivity.this, 0, alarm_receiver, 0);
		
		//取出各组件
		 backButton=(Button)findViewById(R.id.btn_back);
		 tvTime= (TextView)findViewById(R.id.tvtime);
		 userValue=new UserPreferences(AlarmSettingsActivity.this);
		 userValue.get();
	     tvTime.setText(getTime(GlobalVal.gRemindTime));
	     
		switch_remindButton = (ToggleButton)findViewById(R.id.toggle);
		
 
		
		
		//每次创建该activity时，从preferences中读取switch_quietButton和switch_remindButton的开关信息的数据
 
	//	GlobalVal.gRemind =preferences.getBoolean("Remind", false);
		switch_remindButton.setChecked(GlobalVal.gRemind);		
		
		//为返回按钮绑定监听器
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		//为课前提醒开关按钮绑定监听器
		switch_remindButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
				if(isChecked){
					//showDialog(SINGLE_DIALOG);
					Calendar c = Calendar.getInstance();
					// 创建一个TimePickerDialog实例，并把它显示出来。
					new TimePickerDialog(AlarmSettingsActivity.this,
						// 绑定监听器
						new TimePickerDialog.OnTimeSetListener()
						{
							@Override
							public void onTimeSet(TimePicker tp, int hourOfDay,int minute){
								//获取完整的时间，在只有一位的数字前面加0
								StringBuffer s_hour = new StringBuffer();
								StringBuffer s_minute = new StringBuffer();
								s_hour.append(hourOfDay);
								s_minute.append(minute);
								if(hourOfDay<10){
									s_hour.insert(0,"0");
								}
								if(minute<10){
									s_minute.insert(0,"0");
								}
								//将结果显示在edit中
								tvTime.setText(s_hour.toString() + ":" + s_minute.toString());
								String string= tvTime.getText().toString();
								String[] strs=string.split(":");
								int hour= Integer.parseInt(strs[0]); 
								int minutes= Integer.parseInt(strs[1]); 
								time_choice=hour*100+minutes;
								//将开关信息数据保存进preferences中
								GlobalVal.gRemind=isChecked;
								GlobalVal.gRemindTime=time_choice;
								userValue.save();
							}
						}
					//设置初始时间
					, c.get(Calendar.HOUR_OF_DAY)
					, c.get(Calendar.MINUTE)
					//true表示采用24小时制
					, true).show();
					
					
				}
				else{
					alarmManager.cancel(pi);
				}
				
			}
		});
		
	}
	public String getTime(int Time) {
		
		int hour=Time/100;
		int minute=Time%100;
		return hour+":"+minute;
	}			


}
