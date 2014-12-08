package wang.WeekReport.mydata;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeData {
	public static List<WeekDay> dataAnalyze(Report report) {
		
		List<WeekDay> list = new ArrayList<WeekDay>();
		list.add(0,getWeekReport(report.getMonday())) ;
		list.add(1,getWeekReport(report.getTuesday())) ;
		list.add(2,getWeekReport(report.getWednesday())) ;
		list.add(3,getWeekReport(report.getThursday())) ;
		list.add(4,getWeekReport(report.getFriday())) ;
		list.add(5,getWeekReport(report.getSaturday())) ;
		list.add(6,getWeekReport(report.getSunday())) ;
		list.add(7,getWeekReport(report.getSummary())) ;
		return list;

	}
	public static WeekDay  getWeekReport(String weekString) { //对数据拆解
		
		String[]  strings=weekString.split("@"); 
		return new WeekDay(Integer.parseInt(strings[0].trim()),strings[1],strings[2],strings[3],strings[5],strings[4]);
	}
	public static String setWeekReport(WeekDay week) {//对数据封装
		String weekString=null;
		try {
			weekString=
					   String.valueOf(week.getWeekno())+"@"+
					   week.getTitle()+"@"+
					   week.getDate()+"@"+
					   week.getContenttext()+"@"+
					   week.getTimelength()+"@"+
					   week.getPercentage();
					  
		} catch (Exception e) {
			return null;
		}
		
		return weekString;
	}
}
