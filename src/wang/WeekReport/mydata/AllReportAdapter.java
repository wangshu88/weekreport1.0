package wang.WeekReport.mydata;

import wang.WeekReport.MainActivity;
import wang.WeekReport.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AllReportAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	public AllReportAdapter(Context context){
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MainActivity.allReports.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			
			holder=new ViewHolder();  
			convertView = mInflater.inflate(R.layout.list_items, null);
			//holder.img = (ImageView)convertView.findViewById(R.id.img);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.info = (TextView)convertView.findViewById(R.id.info);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		//º”‘ÿ ˝æ›
		
		holder.title.setText((String)MainActivity.allReports.get(position).getNewDate());
		holder.info.setText((String)AnalyzeData.dataAnalyze(MainActivity.allReports.get(position)).get(7).getContenttext());
		return convertView;
	}
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		//public Button viewBtn;
	}
}