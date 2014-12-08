package wang.WeekReport.alarm;

import java.util.ArrayList;
import java.util.List;

import wang.WeekReport.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TimerArriveActivity extends Activity {
	
	private Vibrator vibrator;//�𶯵�
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pre = getSharedPreferences("user", Context.MODE_MULTI_PROCESS);
		int advance_time = pre.getInt("RemindTime", 30);
				
		//��ȡϵͳ��vibrator���񣬲������ֻ���2��
		vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(5000);

		// ����һ���Ի���
		final AlertDialog.Builder builder= new AlertDialog.Builder(TimerArriveActivity.this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("��ܰ��ʾ");
			builder.setMessage("���Ƿ���Ҫд�ܱ���");
			builder.setPositiveButton(
				"ȷ��" ,
				new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog , int which)
					{
						// ������Activity
						TimerArriveActivity.this.finish();
					}
				}
			)
			.show();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// ������Activity
		TimerArriveActivity.this.finish();	
	}

}
