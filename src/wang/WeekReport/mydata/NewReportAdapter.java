package wang.WeekReport.mydata;

import android.R.integer;
import android.content.Context;
import android.database.DataSetObserver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wang.WeekReport.NewActivity;
import wang.WeekReport.R;


public class NewReportAdapter extends BaseAdapter {
	
	
	private Context context;
	// �������������б�������ť��������֡�
	public final static int CLICK_INDEX_SUBCONTEXT = 0;
	public final static int CLICK_INDEX_SUBPERSENTAGE = 1;
	public final static int CLICK_INDEX_SUBTITLE = 2;
	public final static int CLICK_INDEX_SUBTIMELENTH = 3;
	public final static String BUNDLE_KEY_DATA = "data";
    private List<WeekDay> coll;
    private Context ctx;
    private LayoutInflater mInflater;
    private Handler mHandle;
	public static interface IsEvenViewType
	{
		int YES = 1;
		int NO = 0;
	}
	
   // private static final String TAG = NewReportAdapter.class.getSimpleName();



    public NewReportAdapter(Context context,Handler handle, List<WeekDay> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        mHandle = handle;
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    


	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	WeekDay entity = coll.get(position);
	 	
	 	if (entity.getEven().equals("N"))
	 	{
	 		return IsEvenViewType.NO;
	 	}else{
	 		return IsEvenViewType.YES;
	 	}
	 	
	}


	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	final WeekDay entity = coll.get(position);
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (entity.getEven().equals("N"))
			  {
				  convertView = mInflater.inflate(R.layout.report_item_text_left, null);
			  }else{
				  convertView = mInflater.inflate(R.layout.report_item_text_right, null);
			  }

	    	  viewHolder = new ViewHolder();
			  viewHolder.tvAddWeek = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			 viewHolder.tvPersentage= (TextView) convertView.findViewById(R.id.tv_persentage);
			 viewHolder.tvTimelength= (TextView) convertView.findViewById(R.id.tv_timelength);
			
			
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
     
	    
	    viewHolder.tvAddWeek.setText(entity.getDate()+" "+entity.getName());
	    viewHolder.tvContent.setText(entity.getContenttext());
	    if (position>=7) {
	    	 
	    	   viewHolder.tvTitle.setText("");
	 		  viewHolder.tvPersentage.setText("");
	 		  viewHolder.tvTimelength.setText("");
		}else {
			  viewHolder.tvTitle.setText("��Ŀ��"+entity.getTitle());
			  viewHolder.tvPersentage.setText(" �����:"+entity.getPercentage());
			  viewHolder.tvTimelength.setText("����:"+entity.getTimelength()+"h");
			  
			 viewHolder.tvTitle.setOnClickListener(new OnItemChildClickListener(CLICK_INDEX_SUBTITLE, position));
			 viewHolder.tvPersentage.setOnClickListener(new OnItemChildClickListener(CLICK_INDEX_SUBPERSENTAGE, position));
			 viewHolder.tvTimelength.setOnClickListener(new OnItemChildClickListener(CLICK_INDEX_SUBTIMELENTH, position));
			
		}
		 viewHolder.tvContent.setOnClickListener(new OnItemChildClickListener(CLICK_INDEX_SUBCONTEXT, position));
	
	   

		
	    return convertView;
    }
    

    static class ViewHolder { 
        public TextView  tvTimelength;
		public TextView tvAddWeek;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvPersentage;
        public boolean isEven  = true;//�Ƿ�Ϊż��
    }
    private class OnItemChildClickListener implements View.OnClickListener {
    	// ���������������Ӧǰ���CLICK_INDEX_xxx
    	private int clickIndex;
    	// ����б�λ��
    	private int position;
    	public OnItemChildClickListener(int clickIndex, int position) {
    		this.clickIndex = clickIndex;
    		this.position = position;
    	}
    	@Override
    	public void onClick(View v) {
    		// ����Message��������ݣ�ͨ��mHandle��ϵActivity���մ���
    		Message msg = new Message();
    		msg.what = clickIndex;
    		msg.arg1 = position;
    		Bundle b = new Bundle();
    		b.putSerializable(BUNDLE_KEY_DATA, (Serializable) coll.get(position));
    		msg.setData(b);
    		mHandle.sendMessage(msg);
    	}
    	
    }

}
