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
		 
		 ///�����ݿ��ȡֵ
	  //  DBUtil.loadValue();
		 userValue=new UserPreferences(this);
	     userValue.get();  	
		   //����ѡ������ArrayAdapter��������  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ConstantInfo.security);  
        //���������б�ķ��  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        //��adapter ��ӵ�spinner��  
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
					Toast.makeText(getApplicationContext(), "������Ϊ�գ��������", Toast.LENGTH_SHORT);
				}else{
						GlobalVal.gSmtpServer=metSMTPServer.getText().toString().trim();
						GlobalVal.gSmtpPort=metSMTPPort.getText().toString().trim();
						GlobalVal.gSecurityno=mspSecurity.getSelectedItemPosition();
						GlobalVal.gImapServer=metIMAPServer.getText().toString().trim();
						GlobalVal.gImapPort=metIMAPPort.getText().toString().trim();
					//	DBUtil.updateValue(ServerSetupActivity.this, 3);
				    	userValue.save();  	
						Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT);
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
