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
	public  static ArrayList<Report> allReports = new ArrayList<Report>();//存储所有报告对象的ArrayList
	private static ImageButton bEdit=null;
	private static ImageButton bDel=null;
	private static ImageButton bNew=null;
	private static ImageButton bSearch=null;
	private static ImageButton bSettings=null;
	 private ProgressBar mProgressBar;  
	private ListView listView=null;
	private List<String>  data =null;
	private ArrayList<HashMap<String, Object>> listItem=null;//生成动态数组，加入数据  
	private ArrayAdapter<String> adapter =null;
	protected int longClick=0;
	private AllReportAdapter myAdapter=null;
	private String content=null; 
	private int request=100;
	private  UserPreferences userValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
		getWindow().setFlags
    	(//非全屏
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
    	);	
		setContentView(R.layout.activity_main);

        bNew=(ImageButton)findViewById(R.id.btn_new);      //新建日程按钮
    	bSettings=(ImageButton)findViewById(R.id.btn_settings);
    	listView=(ListView)findViewById(R.id.lvmainSchedule);//日程列表
    	mProgressBar = (ProgressBar) findViewById(R.id.progressBar);  
    	allReports.clear();
    	DBUtil.loadReport(this);///从数据库读取值
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
    	
        bNew.setOnClickListener//bNew设置
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
		               // startActivity(intent);  // 调用startActivity方法发送意图给系统 
						startActivityForResult(intent, request);
					}        	
		        }
        );       
        
    
        bSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();  
				  // 设置Intent的源地址和目标地址  
                 intent.setClass(MainActivity.this, SetActivity.class);  
                //Intent可以通过Bundle进行数据的传递  
                // 调用startActivity方法发送意图给系统  
                 startActivity(intent);  
				
			}
		});
        //添加点击  
		listView.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	    Intent intent = new Intent();  
	                intent.setClass(MainActivity.this, NewActivity.class); // 设置Intent的源地址和目标地址      
	                Report obj = allReports.get(arg2);	
	                intent.putExtra("Report",obj);
	                GlobalVal.gListsn=arg2;
	                startActivityForResult(intent, request);
            }  
        });  
		//添加长按
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
					Toast.makeText(MainActivity.this,  arg2+"", Toast.LENGTH_SHORT).show();
				    String[] arrayItem ={"邮箱发送","网络发送","删除"};
				    Dialog alertDialog = new AlertDialog.Builder(MainActivity.this)
						    .setTitle("请选择")
						    .setItems(arrayItem, new DialogInterface.OnClickListener() {
							 @Override
						     public void onClick(DialogInterface dialog, int which) {
								   content =senddata(allReports.get(arg2));
								    if(which==0) {//邮箱发送
								    	if (isConnect(MainActivity.this)) {
								    		emailSendTask emailTask =new emailSendTask(GlobalVal.gUserEmail, GlobalVal.gPassword,GlobalVal.gRecvEmail,	 											GlobalVal.gSmtpServer,GlobalVal.gSmtpPort,GlobalVal.gSubject);
											 emailTask.execute(content);
										}
										 
									}
									if (which==1) {//网络发送
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
						    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
	 //长按菜单响应函数
    public boolean isDel( final int what ) {
    	  AlertDialog.Builder builder = new Builder(MainActivity.this);
	  		builder.setMessage("确认删除吗？");
	  		builder.setTitle("提示");
	  		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					DBUtil.deleteReport(MainActivity.this, allReports.get(what).getSn());
					Toast.makeText(MainActivity.this, what+"", Toast.LENGTH_SHORT).show();
	            	allReports.remove(what);
	            	myAdapter.notifyDataSetChanged();  
					dialog.dismiss();
				}
	  		});
    	  		
	  		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
    				Toast.makeText(MainActivity.this, "新建成功了", Toast.LENGTH_SHORT).show();
    				
				}else {
					allReports.set(GlobalVal.gListsn, report);
					Toast.makeText(MainActivity.this, "修改了", Toast.LENGTH_SHORT).show();
	    			
				}
         	   myAdapter.notifyDataSetInvalidated();
         	   myAdapter.notifyDataSetChanged();
             }
           
		} 
		
 }
	public String senddata(Report report) {
			List<WeekDay> listweek=AnalyzeData.dataAnalyze(report);
			String string=" 星期      内容                                                       工作时间  完成率\n";
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
		       mailInfo.setPassword(password);//您的邮箱密码 
		       mailInfo.setFromAddress(user); 
			   mailInfo.setToAddress(sendTo); 
			   mailInfo.setSubject(subject);   
		}
		 /**
		  * 1、准备运行：onPreExecute(),该回调函数在任务被执行之后立即由UI线程调用。
		  * 这个步骤通常用来建立任务，在用户接口（UI）上显示进度条
		  */
		 @Override
		 protected void onPreExecute(){
			 mProgressBar.setProgress(0);//进度条复位
			 
		 }
		 /**
		  * 2、正在后台运行：doInBackground(Params...),
		  * 该回调函数由后台线程在onPreExecute()方法执行结束后立即调用。
		  * 通常在这里执行耗时的后台计算。
		  * 计算的结果必须由该函数返回，
		  * 并被传递到onPostExecute()中。
		  * 在该函数内也可以使用publish Progress(Progress...)来发布一个或多个进度单位(unitsof progress)。
		  * 这些值将会在onProgressUpdate(Progress...)中被发布到UI线程。
		  * @param params
		  * @return
		  */
		 @Override
			protected String doInBackground(String... params) {
			 	String sendState="flase";
			// 	publishProgress(0);//将会调用onProgressUpdate(Integer... progress)方法  
			 	mailInfo.setContent(params[0]);
			 	publishProgress(30);  
				try {
				      //这个类主要来发送邮件 
				      MultiMailsender sms = new MultiMailsender(); 
				      sms.sendTextMail(mailInfo);//发送文体格式 
				      //MultiMailsender.sendHtmlMail(mailInfo);//发送html格式 
				      //MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送
					} catch (Exception e) {
						e.printStackTrace();
						 return sendState;
					}
			 	publishProgress(100);  
			 	sendState="ok";
				return sendState;
			}
		/**
		  * 3. 进度更新：onProgressUpdate(Progress...),
		  * 该函数由UI线程在publish Progress(Progress...)方法调用完后被调用。
		  * 一般用于动态地显示一个进度条
		  * @param values
		  */
		 @Override
		 protected void onProgressUpdate(Integer... values){
			 mProgressBar.setProgress(values[0]);//更新进度条的进度
		 }
		 /**
		  * 4. 完成后台任务：onPostExecute(Result),
		  * 当后台计算结束后调用。后台计算的结果会被作为参数传递给这一函数。
		  * @param result
		  */
		 @Override
		 protected void onPostExecute(String result){
			 if (result=="ok") {
				 Toast.makeText(MainActivity.this, "发送完成", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_LONG).show();
			}
			  mProgressBar.setProgress(0);//进度条复位 
		 }
		/**
		 * 5、取消任务：onCancelled ()，在调用AsyncTask的cancel()方法时调用
		 */
		@Override
		protected void onCancelled(){
			 mProgressBar.setProgress(0);//进度条复位  
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
		  * 1、准备运行：onPreExecute(),该回调函数在任务被执行之后立即由UI线程调用。
		  * 这个步骤通常用来建立任务，在用户接口（UI）上显示进度条
		  */
		 @Override
		 protected void onPreExecute(){
			 mProgressBar.setProgress(0);//进度条复位 
		 }
		 /**
		  * 2、正在后台运行：doInBackground(Params...),
		  * 该回调函数由后台线程在onPreExecute()方法执行结束后立即调用。
		  * 通常在这里执行耗时的后台计算。
		  * 计算的结果必须由该函数返回，
		  * 并被传递到onPostExecute()中。
		  * 在该函数内也可以使用publish Progress(Progress...)来发布一个或多个进度单位(unitsof progress)。
		  * 这些值将会在onProgressUpdate(Progress...)中被发布到UI线程。
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
		  * 3. 进度更新：onProgressUpdate(Progress...),
		  * 该函数由UI线程在publish Progress(Progress...)方法调用完后被调用。
		  * 一般用于动态地显示一个进度条
		  * @param values
		  */
		 @Override
		 protected void onProgressUpdate(Integer... values){
			 mProgressBar.setProgress(values[0]);//更新进度条的进度
		 }
		 /**
		  * 4. 完成后台任务：onPostExecute(Result),
		  * 当后台计算结束后调用。后台计算的结果会被作为参数传递给这一函数。
		  * @param result
		  */
		 @Override
		 protected void onPostExecute(String result){
			 if (result=="ok") {
				 Toast.makeText(MainActivity.this, "发送完成", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_LONG).show();
			}
			  mProgressBar.setProgress(0);//进度条复位 
		 }
		/**
		 * 5、取消任务：onCancelled ()，在调用AsyncTask的cancel()方法时调用
		 */
		@Override
		protected void onCancelled(){
			 mProgressBar.setProgress(0);//进度条复位  
		}
		
	}
    public  boolean isConnect(Context context) { 
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
    	boolean result=false;
	    try { 
	        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
	        if (connectivity != null) { 
	            // 获取网络连接管理的对象 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if (info != null&& info.isConnected()) { 
	                // 判断当前网络是否已经连接 
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    result=true; 
	                } 
	            } 
	        } 
	    } catch (Exception e) { 
			 Toast.makeText(context, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
	    } 
      return result; 
    } 
}

