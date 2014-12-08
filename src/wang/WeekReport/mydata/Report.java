package wang.WeekReport.mydata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable{
	private int Sn=0;//ÿһ���ճ̶�Ӧһ����һ�޶���sn�룬�����ݿ���Ϊ����
	private String NewDate=" ";//�ճ�����

	private String Title=" ";//�ճ�ʱ��
	private String Monday=" ";//����һ
	private String Tuesday=" ";//���ڶ�
	private String Wednesday=" ";//������
	private String Thursday=" ";//������
	private String Friday=" ";//������
	private String Saturday=" ";//������
	private String Sunday=" ";//������
	private String Summary=" ";//�ܽ�


	public Report(int sn, String date1, String title, String monday,
			String tuesday, String wednesday, String thursday, String friday,
			String saturday, String sunday, String summary) {
		this.Sn=sn;
		this.NewDate=date1;
		this.Title=title;
		this.Monday=monday;
		this.Tuesday=tuesday;
		this.Wednesday=wednesday;
		this.Thursday=thursday;
		this.Friday=friday;
		this.Saturday=saturday;
		this.Sunday=sunday;
		this.Summary=summary;
		
	}


	public Report(int y,int m,int d) {
		
		this.NewDate=toDateString(y,m,d);
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}



	public static String toDateString(int y,int m,int d)//��̬��������int�͵�������ת����YYYY/MM/DD
	{
		StringBuffer sb = new StringBuffer();
		sb.append(y);
		sb.append("/");
		sb.append(m<10?"0"+m:""+m);
		sb.append("/");
		sb.append(d<10?"0"+d:""+d);
		return sb.toString();
	}
	
	public String toTimeString(int h,int m)//��int�͵�ʱ��ת����HH:MM
	{
		StringBuffer sb = new StringBuffer();
		sb.append(h<10?"0"+h:""+h);
		sb.append(":");
		sb.append(m<10?"0"+m:""+m);
		return sb.toString();
	}

	public int getSn() {
		return Sn;
	}

	public void setSn(int sn) {
		this.Sn = sn;
	}

	public String getNewDate() {
		return NewDate;
	}

	public void setNewDate(String newDate) {
		NewDate = newDate;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getMonday() {
		return Monday;
	}

	public void setMonday(String monday) {
		Monday = monday;
	}

	public String getTuesday() {
		return Tuesday;
	}

	public void setTuesday(String tuesday) {
		Tuesday = tuesday;
	}

	public String getWednesday() {
		return Wednesday;
	}

	public void setWednesday(String wednesday) {
		Wednesday = wednesday;
	}

	public String getThursday() {
		return Thursday;
	}

	public void setThursday(String thursday) {
		Thursday = thursday;
	}

	public String getFriday() {
		return Friday;
	}

	public void setFriday(String friday) {
		Friday = friday;
	}

	public String getSaturday() {
		return Saturday;
	}

	public void setSaturday(String saturday) {
		Saturday = saturday;
	}

	public String getSunday() {
		return Sunday;
	}

	public void setSunday(String sunday) {
		Sunday = sunday;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String summary) {
		this.Summary = summary;
	}

}
