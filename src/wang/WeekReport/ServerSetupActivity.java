package wang.WeekReport;

import wang.WeekReport.mydata.ConstantInfo;
import wang.WeekReport.mydata.DBUtil;
import wang.WeekReport.mydata.GlobalVal;
import wang.WeekReport.mydata.UserPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ServerSetupActivity extends Activity {
	
	private EditText metSMTPServer=null;
	private EditText metSMTPPort=null;
	private Spinner  mspSecurity=null;
	private EditText metIMAPServer=null;
	private EditText metIMAPPort=null;
	private Button btnOK=null;
	private Button btnCancle=null;
	private ArrayAdapter<String> adapter=null;  
	private UserPreferences userValue;
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_server_settings);
		 
		 metSMTPServer =(EditText)this.findViewById(R.id.smtpserver);
		 metSMTPPort =(EditText)this.findViewById(R.id.smtpport);
		 mspSecurity=(Spinner)this.findViewById(R.id.secuty);
		 metIMAPServer =(EditText)this.findViewById(R.id.imapserver);
		 metIMAPPort =(EditText)this.findViewById(R.id.imapport);
		 btnOK =(Button)this.findViewById(R.id.btnok);
		 btnCancle=(Button)this.findViewById(R.id.btncancle);
		 
		 ///从数据库读取值
	  //  DBUtil.loadValue();
		 userValue=new UserPreferences(this);
	     userValue.get();  	
		   //将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ConstantInfo.security);  
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        //将adapter 添加到spinner中  
        mspSecurity.setAdapter(adapter);
        mspSecurity.setVisibility(View.VISIBLE); 
        
	    mspSecurity.setSelection(GlobalVal.gSecurityno,true);
	    metSMTPServer.setText(GlobalVal.gSmtpServer);
	    metSMTPPort.setText(GlobalVal.gSmtpPort);
	    metIMAPServer.setText(GlobalVal.gImapServer);
	    metIMAPPort.setText(GlobalVal.gImapPort);
	    
	    btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (metSMTPServer.getText().toString().trim()==""
						||metSMTPPort.getText().toString().trim()==""
						||metIMAPServer.getText().toString().trim()==""
						||metIMAPPort.getText().toString().trim()=="") {
					Toast.makeText(getApplicationContext(), "有内容为空！请更正！", Toast.LENGTH_SHORT);
				}else{
						GlobalVal.gSmtpServer=metSMTPServer.getText().toString().trim();
						GlobalVal.gSmtpPort=metSMTPPort.getText().toString().trim();
						GlobalVal.gSecurityno=mspSecurity.getSelectedItemPosition();
						GlobalVal.gImapServer=metIMAPServer.getText().toString().trim();
						GlobalVal.gImapPort=metIMAPPort.getText().toString().trim();
					//	DBUtil.updateValue(ServerSetupActivity.this, 3);
				    	userValue.save();  	
						Toast.makeText(getApplicationContext(), "保存完成", Toast.LENGTH_SHORT);
						finish();
				}
			}
		});
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	 }
}
