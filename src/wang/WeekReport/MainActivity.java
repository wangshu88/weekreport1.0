package wang.WeekReport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.HeaderTokenizer.Token;

import wang.WeekReport.email.MultiMailsender;
import wang.WeekReport.email.MultiMailsender.MultiMailSenderInfo;
import wang.WeekReport.mydata.AllReportAdapter;
import wang.WeekReport.mydata.AnalyzeData;
import wang.WeekReport.mydata.DBUtil;
import wang.WeekReport.mydata.GlobalVal;
import wang.WeekReport.mydata.NewReportAdapter;
import wang.WeekReport.mydata.Report;
import wang.WeekReport.mydata.AllReportAdapter;
import wang.WeekReport.mydata.UserPreferences;
import wang.WeekReport.mydata.WeekDay;

import android.R.integer;
import android.R.string;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public  static ArrayList<Report> allReports = new ArrayList<Report>();//�洢���б�������ArrayList
	private static ImageButton bEdit=null;
	private static ImageButton bDel=null;
	private static ImageButton bNew=null;
	private static ImageButton bSearch=null;
	private static ImageButton bSettings=null;
	 private ProgressBar mProgressBar;  
	private ListView listView=null;
	private List<String>  data =null;
	private ArrayList<HashMap<String, Object>> listItem=null;//���ɶ�̬���飬��������  
	private ArrayAdapter<String> adapter =null;
	protected int longClick=0;
	private AllReportAdapter myAdapter=null;
	private String content=null; 
	private int request=100;
	private  UserPreferences userValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//�ޱ���
		getWindow().setFlags
    	(//��ȫ��
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
    	);	
		setContentView(R.layout.activity_main);

        bNew=(ImageButton)findViewById(R.id.btn_new);      //�½��ճ̰�ť
    	bSettings=(ImageButton)findViewById(R.id.btn_settings);
    	listView=(ListView)findViewById(R.id.lvmainSchedule);//�ճ��б�
    	mProgressBar = (ProgressBar) findViewById(R.id.progressBar);  
    	allReports.clear();
    	DBUtil.loadReport(this);///�����ݿ��ȡֵ
    	userValue=new UserPreferences(this);
    	userValue.get();  	
    	myAdapter=new AllReportAdapter(this);
    	listView.setAdapter(myAdapter);	
    	setButtonListener();
	}
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void setButtonListener(){
    	
        bNew.setOnClickListener//bNew����
        (
        		new OnClickListener()
		        {
		
					@Override
					public void onClick(View v) {

					    Intent intent = new Intent();  
						Report obj =new Report();	
						obj.setSn(-1);
						GlobalVal.gListsn=-1;
						intent.putExtra("Report", obj);
		                intent.setClass(MainActivity.this, NewActivity.class); 
		               // startActivity(intent);  // ����startActivity����������ͼ��ϵͳ 
						startActivityForResult(intent, request);
					}        	
		        }
        );       
        
    
        bSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();  
				  // ����Intent��Դ��ַ��Ŀ���ַ  
                 intent.setClass(MainActivity.this, SetActivity.class);  
                //Intent����ͨ��Bundle�������ݵĴ���  
                // ����startActivity����������ͼ��ϵͳ  
                 startActivity(intent);  
				
			}
		});
        //��ӵ��  
		listView.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	    Intent intent = new Intent();  
	                intent.setClass(MainActivity.this, NewActivity.class); // ����Intent��Դ��ַ��Ŀ���ַ      
	                Report obj = allReports.get(arg2);	
	                intent.putExtra("Report",obj);
	                GlobalVal.gListsn=arg2;
	                startActivityForResult(intent, request);
            }  
        });  
		//��ӳ���
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
					Toast.makeText(MainActivity.this,  arg2+"", Toast.LENGTH_SHORT).show();
				    String[] arrayItem ={"���䷢��","���緢��","ɾ��"};
				    Dialog alertDialog = new AlertDialog.Builder(MainActivity.this)
						    .setTitle("��ѡ��")
						    .setItems(arrayItem, new DialogInterface.OnClickListener() {
							 @Override
						     public void onClick(DialogInterface dialog, int which) {
								   content =senddata(allReports.get(arg2));
								    if(which==0) {//���䷢��
								    	if (isConnect(MainActivity.this)) {
								    		emailSendTask emailTask =new emailSendTask(GlobalVal.gUserEmail, GlobalVal.gPassword,GlobalVal.gRecvEmail,	 											GlobalVal.gSmtpServer,GlobalVal.gSmtpPort,GlobalVal.gSubject);
											 emailTask.execute(content);
										}
										 
									}
									if (which==1) {//���緢��
										if (isConnect(MainActivity.this)) {
											netSendTask netTask=new netSendTask(GlobalVal.gIP, GlobalVal.gPort);
											netTask.execute(content);
										}
									}
									if (which==2) {
										isDel(arg2);
									}
									dialog.dismiss();
								}
						    })
						    .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						     @Override
						     public void onClick(DialogInterface dialog, int which) {
						    	 dialog.dismiss();
						     }
						    })
						    .create();
						    alertDialog.show();
				    
				return false;
			}
		});

	}
	 //�����˵���Ӧ����
    public boolean isDel( final int what ) {
    	  AlertDialog.Builder builder = new Builder(MainActivity.this);
	  		builder.setMessage("ȷ��ɾ����");
	  		builder.setTitle("��ʾ");
	  		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					DBUtil.deleteReport(MainActivity.this, allReports.get(what).getSn());
					Toast.makeText(MainActivity.this, what+"", Toast.LENGTH_SHORT).show();
	            	allReports.remove(what);
	            	myAdapter.notifyDataSetChanged();  
					dialog.dismiss();
				}
	  		});
    	  		
	  		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	  		@Override
	  		public void onClick(DialogInterface dialog, int which) {
	  			dialog.dismiss();
	  			}
	  		});
    	  builder.create().show();

        return false;
    }
	@Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        if (requestCode==request) {
        	if(resultCode==RESULT_OK){
         	   Report report=(Report) data.getSerializableExtra("Report");
         	   if (GlobalVal.gListsn==-1) {
         		   	allReports.add(0, report);
    				Toast.makeText(MainActivity.this, "�½��ɹ���", Toast.LENGTH_SHORT).show();
    				
				}else {
					allReports.set(GlobalVal.gListsn, report);
					Toast.makeText(MainActivity.this, "�޸���", Toast.LENGTH_SHORT).show();
	    			
				}
         	   myAdapter.notifyDataSetInvalidated();
         	   myAdapter.notifyDataSetChanged();
             }
           
		} 
		
 }
	public String senddata(Report report) {
			List<WeekDay> listweek=AnalyzeData.dataAnalyze(report);
			String string=" ����      ����                                                       ����ʱ��  �����\n";
			for (int i = 0; i < 7; i++) {
				 string+=listweek.get(i).getName()+" : "+listweek.get(i).getContenttext()+" : "+listweek.get(i).getTimelength()+" : "+listweek.get(i).getPercentage()+"\n";
			}
			 string+=listweek.get(7).getName()+" : "+listweek.get(7).getContenttext()+"\n";
			return string;	
	}
	class emailSendTask extends AsyncTask<String, Integer, String>{
		
		private String user;
		private String password;
        private String sendTo;
        private String smtpServer;
        private String smtpPort;
        private String subject;
		private MultiMailSenderInfo mailInfo;
		
		 public  emailSendTask(String user, String password ,String sendTo, String  smtpServer, String smtpPort,String subject) {
			 this.user = user;
		     this.password = password;
		     this.sendTo=sendTo;
		     this.smtpServer=smtpServer;
		     this.smtpPort=smtpPort;
		     this.subject=subject;
			   mailInfo = new MultiMailSenderInfo(); 
		       mailInfo.setMailServerHost(smtpServer); 
		       mailInfo.setMailServerPort(smtpPort); 
		       mailInfo.setValidate(true); 
		       mailInfo.setUserName(user); 
		       mailInfo.setPassword(password);//������������ 
		       mailInfo.setFromAddress(user); 
			   mailInfo.setToAddress(sendTo); 
			   mailInfo.setSubject(subject);   
		}
		 /**
		  * 1��׼�����У�onPreExecute(),�ûص�����������ִ��֮��������UI�̵߳��á�
		  * �������ͨ�����������������û��ӿڣ�UI������ʾ������
		  */
		 @Override
		 protected void onPreExecute(){
			 mProgressBar.setProgress(0);//��������λ
			 
		 }
		 /**
		  * 2�����ں�̨���У�doInBackground(Params...),
		  * �ûص������ɺ�̨�߳���onPreExecute()����ִ�н������������á�
		  * ͨ��������ִ�к�ʱ�ĺ�̨���㡣
		  * ����Ľ�������ɸú������أ�
		  * �������ݵ�onPostExecute()�С�
		  * �ڸú�����Ҳ����ʹ��publish Progress(Progress...)������һ���������ȵ�λ(unitsof progress)��
		  * ��Щֵ������onProgressUpdate(Progress...)�б�������UI�̡߳�
		  * @param params
		  * @return
		  */
		 @Override
			protected String doInBackground(String... params) {
			 	String sendState="flase";
			// 	publishProgress(0);//�������onProgressUpdate(Integer... progress)����  
			 	mailInfo.setContent(params[0]);
			 	publishProgress(30);  
				try {
				      //�������Ҫ�������ʼ� 
				      MultiMailsender sms = new MultiMailsender(); 
				      sms.sendTextMail(mailInfo);//���������ʽ 
				      //MultiMailsender.sendHtmlMail(mailInfo);//����html��ʽ 
				      //MultiMailsender.sendMailtoMultiCC(mailInfo);//���ͳ���
					} catch (Exception e) {
						e.printStackTrace();
						 return sendState;
					}
			 	publishProgress(100);  
			 	sendState="ok";
				return sendState;
			}
		/**
		  * 3. ���ȸ��£�onProgressUpdate(Progress...),
		  * �ú�����UI�߳���publish Progress(Progress...)����������󱻵��á�
		  * һ�����ڶ�̬����ʾһ��������
		  * @param values
		  */
		 @Override
		 protected void onProgressUpdate(Integer... values){
			 mProgressBar.setProgress(values[0]);//���½������Ľ���
		 }
		 /**
		  * 4. ��ɺ�̨����onPostExecute(Result),
		  * ����̨�����������á���̨����Ľ���ᱻ��Ϊ�������ݸ���һ������
		  * @param result
		  */
		 @Override
		 protected void onPostExecute(String result){
			 if (result=="ok") {
				 Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(MainActivity.this, "����ʧ��", Toast.LENGTH_LONG).show();
			}
			  mProgressBar.setProgress(0);//��������λ 
		 }
		/**
		 * 5��ȡ������onCancelled ()���ڵ���AsyncTask��cancel()����ʱ����
		 */
		@Override
		protected void onCancelled(){
			 mProgressBar.setProgress(0);//��������λ  
		}
		
	}
    class netSendTask extends AsyncTask<String, Integer, String>{
		
		private String IP;
		private String Port;
		private InetAddress serverAddr;
		private Socket socket;
		 public  netSendTask(String IP,String Port) {
			 this.IP = IP;
		     this.Port = Port;
		}
		 /**
		  * 1��׼�����У�onPreExecute(),�ûص�����������ִ��֮��������UI�̵߳��á�
		  * �������ͨ�����������������û��ӿڣ�UI������ʾ������
		  */
		 @Override
		 protected void onPreExecute(){
			 mProgressBar.setProgress(0);//��������λ 
		 }
		 /**
		  * 2�����ں�̨���У�doInBackground(Params...),
		  * �ûص������ɺ�̨�߳���onPreExecute()����ִ�н������������á�
		  * ͨ��������ִ�к�ʱ�ĺ�̨���㡣
		  * ����Ľ�������ɸú������أ�
		  * �������ݵ�onPostExecute()�С�
		  * �ڸú�����Ҳ����ʹ��publish Progress(Progress...)������һ���������ȵ�λ(unitsof progress)��
		  * ��Щֵ������onProgressUpdate(Progress...)�б�������UI�̡߳�
		  * @param params
		  * @return
		  */
		 @Override
			protected String doInBackground(String... params) {
			 	String sendState="flase";
				String conString=params[0];
				
			 	publishProgress(30);  
				try {
					 serverAddr = InetAddress.getByName(this.IP);
					 socket = new Socket(serverAddr, Integer.parseInt(this.Port)); 
					  PrintWriter out = new PrintWriter(new BufferedWriter(  
							  new OutputStreamWriter(socket.getOutputStream())), 
							  true); 
					  out.println(conString); 
					  out.flush(); 
					  sendState="ok";
					
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}finally {
						try {  
							socket.close();
						} catch(Exception e) {
							 e.printStackTrace(); 
						}
						
					}
			 	publishProgress(100);  
				return sendState;
			}
		/**
		  * 3. ���ȸ��£�onProgressUpdate(Progress...),
		  * �ú�����UI�߳���publish Progress(Progress...)����������󱻵��á�
		  * һ�����ڶ�̬����ʾһ��������
		  * @param values
		  */
		 @Override
		 protected void onProgressUpdate(Integer... values){
			 mProgressBar.setProgress(values[0]);//���½������Ľ���
		 }
		 /**
		  * 4. ��ɺ�̨����onPostExecute(Result),
		  * ����̨�����������á���̨����Ľ���ᱻ��Ϊ�������ݸ���һ������
		  * @param result
		  */
		 @Override
		 protected void onPostExecute(String result){
			 if (result=="ok") {
				 Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(MainActivity.this, "����ʧ��", Toast.LENGTH_LONG).show();
			}
			  mProgressBar.setProgress(0);//��������λ 
		 }
		/**
		 * 5��ȡ������onCancelled ()���ڵ���AsyncTask��cancel()����ʱ����
		 */
		@Override
		protected void onCancelled(){
			 mProgressBar.setProgress(0);//��������λ  
		}
		
	}
    public  boolean isConnect(Context context) { 
        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
    	boolean result=false;
	    try { 
	        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
	        if (connectivity != null) { 
	            // ��ȡ�������ӹ���Ķ��� 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if (info != null&& info.isConnected()) { 
	                // �жϵ�ǰ�����Ƿ��Ѿ����� 
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    result=true; 
	                } 
	            } 
	        } 
	    } catch (Exception e) { 
			 Toast.makeText(context, "�����쳣����������", Toast.LENGTH_LONG).show();
	    } 
      return result; 
    } 
}

