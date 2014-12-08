package wang.WeekReport.mydata;
import java.io.Serializable;

public class WeekDay implements Serializable {
	private int  weekno;
   
	private String title=" ";

    private String name=" ";
     
    private String date=" ";
    
    private String contenttext=" ";
    
    private String percentage=" ";
    
    private String timelength=" ";
    
    private String isEven =null;
    
    public int getWeekno() {
		return weekno;
	}

	public void setWeekno(int weekno) {
		this.weekno = weekno;
	}
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContenttext() {
		return contenttext;
	}

	public void setContenttext(String contenttext) {
		this.contenttext = contenttext;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getTimelength() {
		return timelength;
	}

	public void setTimelength(String timelength) {
		this.timelength = timelength;
	}

	public String getEven() {
		return isEven;
	}




	public WeekDay(int weekno , String title, String date, String contenttext,
			String percentage, String timelength) {
		super();
		this.title = title;
		this.weekno=weekno;
		this.date = date;
		this.contenttext = contenttext;
		this.percentage = percentage;
		this.timelength = timelength;
		this.name=ConstantInfo.cn_weeks[weekno];
		if (weekno%2==1) {
			this.isEven="N";
		}
		else {
			this.isEven="Y";
		}

	}

}
