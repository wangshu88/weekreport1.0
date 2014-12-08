package wang.WeekReport.mydata;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import wang.WeekReport.MainActivity;
import wang.WeekReport.NewActivity;
import wang.WeekReport.SetActivity;
import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputFilter.AllCaps;
import android.util.Log;
import android.widget.Toast;

public class DBUtil 
{
	

	static SQLiteDatabase dbWeekReport;
	/**
	 * 现在不用了
	 */
    public static void loadValue(){
    	try {
    		
    		openDb(SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);//打开数据库
    		dbWeekReport.execSQL(ConstantInfo.sqlcreatetab);
    		//dbWeekReport.execSQL(ConstantInfo.insertinit);
    		Cursor cursor=dbWeekReport.query(ConstantInfo.tab_datavalue, null, null, null, null, null, null);//按时间输出
    		while(cursor.moveToNext())
			{
				GlobalVal.id=cursor.getInt(0);
				GlobalVal.gUserEmail = cursor.getString(1);
				GlobalVal.gPassword = cursor.getString(2);
				GlobalVal.gSecurityno = cursor.getInt(3);
				GlobalVal.gSmtpServer = cursor.getString(4);
				GlobalVal.gSmtpPort = cursor.getString(5);
				GlobalVal.gImapServer = cursor.getString(6);
				GlobalVal.gImapPort = cursor.getString(7);
				GlobalVal.gSubject = cursor.getString(8);
				GlobalVal.gRecvEmail=cursor.getString(9);
			}
    		dbWeekReport.close();
			cursor.close();
    		
		} catch (Exception e) {
			Log.i("e", "读取失败！"+e.toString());
			 
		}
    }
    public static void updateValue(Activity father, int what)//
	{
		try
		{
			openDb(SQLiteDatabase.OPEN_READWRITE);//打开数据库
			
			ContentValues values= new ContentValues();
			switch (what) {
			case 0:
				values.put("textsubject", GlobalVal.gSubject);
				break;
			case 1:
				values.put("useremail", GlobalVal.gUserEmail);
				values.put("password", GlobalVal.gPassword);
				break;
			case 2:
				values.put("recvemail", GlobalVal.gRecvEmail);
				
				break;
			case 3:
				values.put("secruty", GlobalVal.gSecurityno);
				values.put("smtpserver", GlobalVal.gSmtpServer);
				values.put("smtpport", GlobalVal.gSmtpPort);
				values.put("imapserver", GlobalVal.gImapServer);
				values.put("imapport", GlobalVal.gImapPort);
				break;
			default:
				break;
			}
			if (dbWeekReport.update(ConstantInfo.tab_datavalue, values, "_id=?", new String[]{"1"})==1) {
				Toast.makeText(father, "保存成功", Toast.LENGTH_SHORT).show();
			};
			dbWeekReport.close();
			
		}
		catch(Exception e)
		{
			Toast.makeText(father, " "+"保存失败", Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
	}
	//============================start load table==============================
	public static void  openDb(int flags) {
		dbWeekReport=SQLiteDatabase.openDatabase
				(
					ConstantInfo.dbName,  
					null, 
					flags				
				);
	}

	public static void loadReport(MainActivity father)
	{
		try
		{
			openDb(SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);//打开数据库
			dbWeekReport.execSQL(ConstantInfo.sqlcreatetable); //打开表
			Cursor cursor=dbWeekReport.query(ConstantInfo.tab_weekreport, null, null, null, null, null, "_id desc");//按ID输出

			while(cursor.moveToNext())
			{
				int sn=cursor.getInt(0);
				String Date1=cursor!=null?cursor.getString(1):null;
				String Title=cursor!=null?cursor.getString(2):null;
				String Monday=cursor!=null?cursor.getString(3):null;
				String Tuesday=cursor!=null?cursor.getString(4):null;
				String Wednesday=cursor!=null?cursor.getString(5):null;
				String Thursday=cursor!=null?cursor.getString(6):null;
				String Friday =cursor!=null?cursor.getString(7):null;
				String Saturday =cursor!=null?cursor.getString(8):null;
				String Sunday=cursor!=null?cursor.getString(9):null;
				String Summary=cursor!=null?cursor.getString(10):null;
				Report reportTemp=new Report(sn,Date1,Title,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,Summary);
				
				father.allReports.add(reportTemp); //加到列表中去
			}
			dbWeekReport.close();
			cursor.close();
		}
		catch(Exception e)
		{
			Log.d("exception",e.toString());
		}
	}
	
	public static  long insertReport(Activity father,Report report )//
	{
		try
		{
			openDb(SQLiteDatabase.OPEN_READWRITE);//打开数据库
            ContentValues values= new ContentValues();
            
            values.put("newdate",(String)report.getNewDate());
            
            values.put("title", (String)report.getTitle());
            values.put("monday", (String)report.getMonday());
            values.put("tuesday", (String)report.getTuesday());
            values.put("wednesday",(String)report.getWednesday());
            values.put("thursday",(String) report.getThursday());
            values.put("friday",(String) report.getFriday());
            values.put("saturday",(String) report.getSaturday());
            values.put("sunday",(String) report.getSunday());           
            values.put("summary",(String)report.getSummary());
            long l=dbWeekReport.insert(ConstantInfo.tab_weekreport, null, values);
			if (l!=-1) {
				Toast.makeText(father, "保存成功", Toast.LENGTH_SHORT).show();
			}; //插入信息 
			dbWeekReport.close();
			return l;

		}
		catch(Exception e)
		{
			Toast.makeText(father, "保存失败"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
		return 0;
	}
	
	public static void updateReport(Activity father, String sn ,String key, String value)//锟斤拷锟斤拷锟秸筹拷
	{
		try
		{
			openDb(SQLiteDatabase.OPEN_READWRITE);//打开数据库
			
			ContentValues values= new ContentValues();
			values.put(key, value);
			dbWeekReport.update(ConstantInfo.tab_weekreport, values, "_id=?", new String[]{sn});
			dbWeekReport.close();
			Toast.makeText(father, "保存成功", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(father, " "+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
	}
	
	public static void deleteReport(Activity father ,int  i)//删除表格
	{
		try
		{
			openDb(SQLiteDatabase.OPEN_READWRITE);//打开数据库
			
			dbWeekReport.delete(ConstantInfo.tab_weekreport, "_id=?", new String[]{String.valueOf(i)});
			dbWeekReport.close();
			Toast.makeText(father, "删除成功", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "删除失败"+e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	public static Report searchReport(Activity father,int sn)//
	{
		Report report=new Report();
		try
		{
			openDb(SQLiteDatabase.OPEN_READONLY);//打开数据库
			
			String[] week={"*"};
			Cursor cursor=dbWeekReport.query(ConstantInfo.tab_weekreport, week, "_id=?", new String[]{sn+""}, null, null, null);

			while(cursor.moveToNext())
			{
				
				report.setNewDate(cursor!=null?cursor.getString(1):null);
				report.setTitle(cursor!=null?cursor.getString(2):null);
				report.setMonday(cursor!=null?cursor.getString(3):null);
				report.setTuesday(cursor!=null?cursor.getString(4):null);
				report.setWednesday(cursor!=null?cursor.getString(5):null);
				report.setThursday(cursor!=null?cursor.getString(6):null);
				report.setFriday(cursor!=null?cursor.getString(7):null);
				report.setSaturday(cursor!=null?cursor.getString(8):null);
				report.setSunday(cursor!=null?cursor.getString(9):null);
				report.setSummary(cursor!=null?cursor.getString(10):null);
				
			}
			
			dbWeekReport.close();
			cursor.close();
		}
		catch(Exception e)
		{
			Toast.makeText(father, e.toString(), Toast.LENGTH_SHORT).show();
		}
		return report;
	}


}