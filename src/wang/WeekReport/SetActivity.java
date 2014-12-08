package wang.WeekReport;

import java.util.List;
import java.util.Map;

import wang.WeekReport.alarm.AlarmSettingsActivity;
import wang.WeekReport.mydata.DBUtil;
import wang.WeekReport.mydata.GlobalVal;
import wang.WeekReport.mydata.Report;
import wang.WeekReport.mydata.UserPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SetActivity extends Activity{
	 private RelativeLayout mrlSubject = null;
	 private RelativeLayout mrlUserPassword = null;
	 private RelativeLayout mrlServerSetup = null;
	 private RelativeLayout mrlNetSetup = null;
	 private RelativeLayout mrlRecvemail=null;
	 private RelativeLayout mrlAlarm = null;
	 private RelativeLayout mrlAbout = null;
	 private EditText metsubject = null;
	 private EditText metUser = null;
	 private EditText metPassword = null;
	 private EditText IP=null;
	 private EditText Port=null;
	 private  UserPreferences userValue;


	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_setup);
			userValue=new UserPreferences(this);
	    	userValue.get();  	
	     //DBUtil.loadValue();
	     mrlSubject= (RelativeLayout) SetActivity.this.findViewById(R.id.reportSubject) ;
	     mrlSubject.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View v) {
					  LayoutInflater factory = LayoutInflater.from(SetActivity.this);
	           		   View textEntryView = factory.inflate(R.layout.dialog, null);
	           		metsubject = (EditText) textEntryView.findViewById(R.id.subject);
	           		metsubject.setText(GlobalVal.gSubject);
	            		AlertDialog.Builder builder = new Builder(SetActivity.this);
	            		builder
	            		.setIcon(android.R.drawable.ic_dialog_info)
	            		.setView(textEntryView);
	            		
	            		builder.setPositiveButton("确定",new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
							
								GlobalVal.gSubject=metsubject.getText().toString();
								//DBUtil.updateValue(SetActivity.this, 0);
						    	userValue.save(); 	
								dialog.dismiss();
							}
						});
	            		builder.setNegativeButton("取消", new  OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
	            		builder.create().show();
				}
  
	        });
	     mrlUserPassword= (RelativeLayout) SetActivity.this.findViewById(R.id.userpassword) ;
	     mrlUserPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  LayoutInflater factory = LayoutInflater.from(SetActivity.this);
          		   View textEntryView = factory.inflate(R.layout.email_user_password , null);
          		 metUser = (EditText) textEntryView.findViewById(R.id.txt_username);
          		 metPassword = (EditText) textEntryView.findViewById(R.id.txt_password);
          		 metUser.setText(GlobalVal.gUserEmail);
          		 metPassword.setText(GlobalVal.gPassword);
           		AlertDialog.Builder builder = new Builder(SetActivity.this);
           		builder
           		.setIcon(android.R.drawable.ic_dialog_info)
           		.setView(textEntryView);
           		
           		builder.setPositiveButton("确定",new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						   
							GlobalVal.gUserEmail=metUser.getText().toString().trim();
							GlobalVal.gPassword=metPassword.getText().toString().trim();
							userValue.save();
							dialog.dismiss();
						}
					});
           		builder.setNegativeButton("取消", new  OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							dialog.dismiss();
						}
					});
           		builder.create().show();
				
			}
		});
	     mrlRecvemail=(RelativeLayout) SetActivity.this.findViewById(R.id.recvemail) ;
	     mrlRecvemail.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View v) {
					  LayoutInflater factory = LayoutInflater.from(SetActivity.this);
	           		   View textEntryView = factory.inflate(R.layout.dialog, null);
	           		metsubject = (EditText) textEntryView.findViewById(R.id.subject);
	           		metsubject.setText(GlobalVal.gRecvEmail);
	            		AlertDialog.Builder builder = new Builder(SetActivity.this);
	            		builder
	            		.setIcon(android.R.drawable.ic_dialog_info)
	            		.setView(textEntryView);
	            		
	            		builder.setPositiveButton("确定",new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
							
								GlobalVal.gRecvEmail=metsubject.getText().toString();
						    	userValue.save();	
								dialog.dismiss();
							}
						});
	            		builder.setNegativeButton("取消", new  OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
	            		builder.create().show();
				}

	        });
	     mrlNetSetup= (RelativeLayout) SetActivity.this.findViewById(R.id.netsetup) ;
	     mrlNetSetup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					     LayoutInflater factory = LayoutInflater.from(SetActivity.this);                
		                final View view = factory.inflate(R.layout.activity_net_setup, null);// 获得自定义对话框
		                 IP = (EditText) view.findViewById(R.id.etIPaddress);
                         Port = (EditText) view.findViewById(R.id.etPort);
                        IP.setText(GlobalVal.gIP);
                        Port.setText(GlobalVal.gPort);
		                AlertDialog.Builder dialog = new AlertDialog.Builder(SetActivity.this);
		                dialog.setIcon(R.drawable.net);
		                dialog.setTitle("网络设置");
		                dialog.setView(view);
		                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
		                {
		                    public void onClick(DialogInterface dialog, int whichButton)
		                    {

		                        
		                       GlobalVal.gIP=IP.getText().toString();
		                       GlobalVal.gPort=Port.getText().toString();
		                   	   userValue.save(); 	
		                   	   dialog.dismiss();
		                       
		                    }
		                });
		                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
		                {
		                    public void onClick(DialogInterface dialog, int whichButton)
		                    {
		                    	dialog.dismiss();
		                        
		                    }
		                });
		                dialog.create().show();
		                return;
		            }
			});
	     mrlServerSetup= (RelativeLayout) SetActivity.this.findViewById(R.id.serversetup) ;
	     mrlServerSetup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    Intent intent = new Intent();  
	                intent.setClass(getApplicationContext(),ServerSetupActivity.class); // 设置Intent的源地址和目标地址      
	                startActivity(intent);
				}
			});
	     mrlAlarm= (RelativeLayout) SetActivity.this.findViewById(R.id.alarm) ;
	     mrlAlarm.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();  
	                intent.setClass(getApplicationContext(),AlarmSettingsActivity.class); // 设置Intent的源地址和目标地址      
	                startActivity(intent);
				}
			});
	     mrlAbout= (RelativeLayout) SetActivity.this.findViewById(R.id.about) ;
	     mrlAbout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();  
	                intent.setClass(getApplicationContext(),AboutActivity.class); // 设置Intent的源地址和目标地址      
	                startActivity(intent);
				}
			});
	     
	        
	 }
}
