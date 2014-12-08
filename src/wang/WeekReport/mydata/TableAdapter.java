package wang.WeekReport.mydata;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TableAdapter extends SimpleAdapter {
	 private int[] to;
	 private String[] title;
	 private View titleView;

	public TableAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.to = to;
	    this.title = title;
	    titleView = LayoutInflater.from(context).inflate(resource, null);
	    createTitleView();

	}

	private void createTitleView() {
		int count = to.length;
        for (int i = 0; i < count; i++) {
         View v = titleView.findViewById(to[i]);
         if( v != null ){
	          if(v instanceof TextView){
		          ((TextView) v).setText(title[i]);
		          ((TextView) v).setTextColor(Color.WHITE);
		          ((TextView) v).setTextSize(16);
		          ((TextView) v).setGravity(Gravity.CENTER);
		          }
	         }
        }
        setTitleView(titleView);
	}

	public View getTitleView( ) {
		// TODO Auto-generated method stub
		return titleView;

	}
	public void setTitleView(View titleView) {
		 this.titleView = titleView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}

}
