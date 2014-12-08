package wang.WeekReport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wang.WeekReport.mydata.AnalyzeData;
import wang.WeekReport.mydata.ConstantInfo;
import wang.WeekReport.mydata.DBUtil;
import wang.WeekReport.mydata.GlobalVal;
import wang.WeekReport.mydata.NewReportAdapter;
import wang.WeekReport.mydata.Report;
import wang.WeekReport.mydata.WeekDay;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewActivity extends Activity {
	
	

	
	 //////////////////////////////////////
	private long sn;
	private Button mBtnSend;
	private Button mBtnBack;
	public static EditText mEditTextContent;
	private ListView mListView;
	private Report gReport=null;
	private NewReportAdapter mAdapter;
	private List<WeekDay> mDataArrays = new ArrayList<WeekDay>();
	private WeekDay  data =null;
	private Message gMessage=null;
	private int location=-1;
	public int position=-1;
	 ////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
		getWindow().setFlags
    	(//非全屏
    		WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, 
    		WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
    	);	
		setContentView(R.layout.activity_newreport);
		
		//启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initView();
        initData();
		////////////////////////////////////
       
       setButtonListener();
       
		
	}
	private void initView() {
		
		mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	//mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	//mBtnBack.setOnClickListener(this);
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}
	private void initData() {

		gReport= (Report) this.getIntent().getSerializableExtra("Report");
         if (gReport.getSn()==-1) {//新建一个表，插入日期数据
				Calendar c=Calendar.getInstance();
				int y=c.get(Calendar.YEAR);
				int m=c.get(Calendar.MONTH)+1;
				int d=c.get(Calendar.DAY_OF_MONTH);
				List<WeekDay> weeks=new ArrayList<WeekDay>();
				for (int i = 0; i <8; i++) {
					weeks.add(i,new WeekDay(i, " ", " ", " ", " ", " "));
				}
				
				gReport.setMonday(AnalyzeData.setWeekReport(weeks.get(0)));
				gReport.setTuesday(AnalyzeData.setWeekReport(weeks.get(1)));
				gReport.setWednesday(AnalyzeData.setWeekReport(weeks.get(2)));
				gReport.setThursday(AnalyzeData.setWeekReport(weeks.get(3)));
				gReport.setFriday(AnalyzeData.setWeekReport(weeks.get(4)));
				gReport.setSaturday(AnalyzeData.setWeekReport(weeks.get(5)));
				gReport.setSunday(AnalyzeData.setWeekReport(weeks.get(6)));
				gReport.setSummary(AnalyzeData.setWeekReport(weeks.get(7)));
				gReport.setNewDate(Report.toDateString(y, m, d));
				gReport.setTitle(" ");

				sn=DBUtil.insertReport(NewActivity.this, gReport);
				gReport.setSn((int)sn);
			}
         else {
        	  sn=gReport.getSn();
         }

			mDataArrays=AnalyzeData.dataAnalyze(gReport);
			mAdapter = new NewReportAdapter(this, mHandle,mDataArrays);
			mListView.setAdapter(mAdapter);

	}
		@Override
	 public void onBackPressed(){
			setResultBack();
			NewActivity.this.finish();
	 }

	public void setButtonListener(){
		

		mBtnBack.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				setResultBack();
				NewActivity.this.finish();
				
			}
		});

		mBtnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (location) {
				case NewReportAdapter.CLICK_INDEX_SUBCONTEXT:
						data.setContenttext(mEditTextContent.getText().toString().trim());
					break;
				case NewReportAdapter.CLICK_INDEX_SUBTITLE:
					   data.setTitle(mEditTextContent.getText().toString().trim());
					break;
				case NewReportAdapter.CLICK_INDEX_SUBPERSENTAGE:
					   data.setPercentage( mEditTextContent.getText().toString().trim());
					break;
				case NewReportAdapter.CLICK_INDEX_SUBTIMELENTH:
					   data.setTimelength(mEditTextContent.getText().toString().trim());
					break;
				default:
					break;
				}
				
				mAdapter.notifyDataSetChanged(); 
				switch (position) {
				case 0:gReport.setMonday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 1:gReport.setTuesday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 2:gReport.setWednesday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 3:gReport.setThursday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 4:gReport.setFriday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 5:gReport.setSaturday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 6:gReport.setSunday(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				case 7:gReport.setSummary(AnalyzeData.setWeekReport(mDataArrays.get(position))); break;
				default:break;
				}
				
				DBUtil.updateReport(NewActivity.this, sn+"", ConstantInfo.en_weeks[position], AnalyzeData.setWeekReport(mDataArrays.get(position)));
			
			}
		});
		
	}
	private void setResultBack(){
		Intent intent = new Intent();
		Bundle b = new Bundle();
		Report obj = gReport;	
        intent.putExtra("Report",obj);
		setResult(RESULT_OK, intent);
	}
	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			location=msg.what; //一天中各个内容
			position=msg.arg1;//一星期的第几天
			 data = (WeekDay) msg.getData().getSerializable(NewReportAdapter.BUNDLE_KEY_DATA);
			 Toast.makeText(NewActivity.this, "可以输入", Toast.LENGTH_SHORT).show();
			 switch(location) {
				case NewReportAdapter.CLICK_INDEX_SUBCONTEXT:{
					String string=data.getContenttext();
					NewActivity.mEditTextContent.setText(string) ;
				}break;
				case NewReportAdapter.CLICK_INDEX_SUBTITLE:{
					String string=data.getTitle();
					NewActivity.mEditTextContent.setText(string) ;
				}break;
				case NewReportAdapter.CLICK_INDEX_SUBPERSENTAGE:{
					String string=data.getPercentage();
					NewActivity.mEditTextContent.setText(string) ;
				}break;
				case NewReportAdapter.CLICK_INDEX_SUBTIMELENTH:{
					String string=data.getTimelength();
					NewActivity.mEditTextContent.setText(string) ;
				}break;
			}
		}
		
	};

}

