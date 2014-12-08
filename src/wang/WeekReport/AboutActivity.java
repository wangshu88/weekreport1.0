package wang.WeekReport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity{
	private Button mBtnBack=null;
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_about);
		 mBtnBack = (Button) findViewById(R.id.btn_back);
		 mBtnBack.setOnClickListener(new  OnClickListener() {
			
			@Override 
			public void onClick(View v) {
				
				finish();
			}
		});
	 }
}
