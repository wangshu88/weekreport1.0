package wang.WeekReport.mydata;

import java.util.Map;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.ContextMenu;

public class UserPreferences {
	private Context context;

	public UserPreferences(Context context) {
		super();
		this.context = context;
	}
	public void save(){
		SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putString("UserEmail", GlobalVal.gUserEmail);
		editor.putString("Password", GlobalVal.gPassword);
		editor.putString("RecvEmail", GlobalVal.gRecvEmail);
		editor.putString("SmtpServer", GlobalVal.gSmtpServer);
		editor.putString("SmtpPort", GlobalVal.gSmtpPort);
		editor.putString("ImapServer", GlobalVal.gImapServer);
		editor.putString("ImapPort", GlobalVal.gImapPort);
		editor.putString("Subject", GlobalVal.gSubject);
		editor.putInt("Securityno", GlobalVal.gSecurityno);
		editor.putBoolean("Remind", GlobalVal.gRemind);
		editor.putInt("RemindTime", GlobalVal.gRemindTime);
		editor.putString("NetIP", GlobalVal.gIP);
		editor.putString("NetPort", GlobalVal.gPort);
		editor.commit();
	}
	public void get() {
		SharedPreferences sharedPreferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
		GlobalVal.gUserEmail=sharedPreferences.getString("UserEmail", " ");
		GlobalVal.gPassword=sharedPreferences.getString("Password", " ");
		GlobalVal.gRecvEmail=sharedPreferences.getString("RecvEmail", " ");
		GlobalVal.gSmtpServer=sharedPreferences.getString("SmtpServer", " ");
		GlobalVal.gSmtpPort=sharedPreferences.getString("SmtpPort", " ");
		GlobalVal.gImapServer=sharedPreferences.getString("ImapServer", " ");
		GlobalVal.gImapPort=sharedPreferences.getString("ImapPort", " ");
		GlobalVal.gSubject=sharedPreferences.getString("Subject", " ");
		GlobalVal.gSecurityno=sharedPreferences.getInt("Securityno", 0);
		GlobalVal.gRemind=sharedPreferences.getBoolean("Remind",false);
		GlobalVal.gRemindTime=sharedPreferences.getInt("RemindTime",0);
		GlobalVal.gIP=sharedPreferences.getString("NetIP", "");
		GlobalVal.gPort=sharedPreferences.getString("NetPort", " ");
	}
     
}
