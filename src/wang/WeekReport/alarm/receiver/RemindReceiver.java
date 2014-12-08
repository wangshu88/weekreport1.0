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
		
		//�Ӹ�context���������ѵ�activity������SDK�ĵ���˵������Ҫ����addFlags()һ��
		Intent remind_intent = new Intent(arg0, TimerArriveActivity.class);
		remind_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		//��ȡ��ǰ���ѵ�ʱ��ֵ,���û�л�ȡ����ȡĬ��ֵ30����
//		int advance_time = arg1.getIntExtra("anvance_remindtime", 20);
		//����ģʽһ��Ҫ����ΪMODE_MULTI_PROCESS������ʹ��Ӧ��xml�ļ��������и��£�RemindReceiver��Ҳ���ܻ�ȡ���º�����ݣ�����һֱ��ȡ�ϴε����ݣ� ������ջ���
		SharedPreferences pre = arg0.getSharedPreferences("user", Context.MODE_MULTI_PROCESS);
		advance_time = pre.getInt("RemindTime", 0);
		int hourOfDay= advance_time/100;
		int minute=advance_time%100;
        
		Calendar c = Calendar.getInstance(); 
		int current_hourOfDay = c.get(Calendar.HOUR_OF_DAY); 
		int current_minute = c.get(Calendar.MINUTE);
		//����һ����־λ�������ų����ظ�������
		//ѭ���жϵ���Ŀ�ǰ����
	    //��������趨������ʱ�䣬���������ѵ�activity
		if(hourOfDay==current_hourOfDay && minute==current_minute){
				arg0.startActivity(remind_intent);										
			}
		}
}
