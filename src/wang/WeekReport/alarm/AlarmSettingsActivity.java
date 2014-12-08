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
	//����һ��AlarmManager��������������ǰ���ѷ���
	private AlarmManager alarmManager = null;
	//����һ��PendingIntent��������ָ��alarmManagerҪ���������
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
		//����activity���뵽MyApplication����ʵ��������
		//MyApplication.getInstance().addActivity(this);
		
		//����һ����ȡϵͳ��Ƶ�������Ķ���
		final AudioManager audioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
		//��MainAcivity�л�ȡԭʼ���õ�����ģʽ
		Intent intent = getIntent();
		final int orgRingerMode = intent.getIntExtra("mode_ringer", AudioManager.RINGER_MODE_NORMAL);
		//��ȡϵͳ�����Ӷ�ʱ����
		alarmManager = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
		
		//ָ��alarmManagerҪ���������
		alarm_receiver = new Intent(AlarmSettingsActivity.this,ResultReceiver.class);
//		alarm_receiver.putExtra("anvance_remindtime", time_choice);
		pi = PendingIntent.getBroadcast(AlarmSettingsActivity.this, 0, alarm_receiver, 0);
		
		//ȡ�������
		 backButton=(Button)findViewById(R.id.btn_back);
		 tvTime= (TextView)findViewById(R.id.tvtime);
		 userValue=new UserPreferences(AlarmSettingsActivity.this);
		 userValue.get();
	     tvTime.setText(getTime(GlobalVal.gRemindTime));
	     
		switch_remindButton = (ToggleButton)findViewById(R.id.toggle);
		
 
		
		
		//ÿ�δ�����activityʱ����preferences�ж�ȡswitch_quietButton��switch_remindButton�Ŀ�����Ϣ������
 
	//	GlobalVal.gRemind =preferences.getBoolean("Remind", false);
		switch_remindButton.setChecked(GlobalVal.gRemind);		
		
		//Ϊ���ذ�ť�󶨼�����
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		//Ϊ��ǰ���ѿ��ذ�ť�󶨼�����
		switch_remindButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
				if(isChecked){
					//showDialog(SINGLE_DIALOG);
					Calendar c = Calendar.getInstance();
					// ����һ��TimePickerDialogʵ������������ʾ������
					new TimePickerDialog(AlarmSettingsActivity.this,
						// �󶨼�����
						new TimePickerDialog.OnTimeSetListener()
						{
							@Override
							public void onTimeSet(TimePicker tp, int hourOfDay,int minute){
								//��ȡ������ʱ�䣬��ֻ��һλ������ǰ���0
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
								//�������ʾ��edit��
								tvTime.setText(s_hour.toString() + ":" + s_minute.toString());
								String string= tvTime.getText().toString();
								String[] strs=string.split(":");
								int hour= Integer.parseInt(strs[0]); 
								int minutes= Integer.parseInt(strs[1]); 
								time_choice=hour*100+minutes;
								//��������Ϣ���ݱ����preferences��
								GlobalVal.gRemind=isChecked;
								GlobalVal.gRemindTime=time_choice;
								userValue.save();
							}
						}
					//���ó�ʼʱ��
					, c.get(Calendar.HOUR_OF_DAY)
					, c.get(Calendar.MINUTE)
					//true��ʾ����24Сʱ��
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
