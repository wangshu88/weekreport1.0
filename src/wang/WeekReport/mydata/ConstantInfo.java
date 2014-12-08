package wang.WeekReport.mydata;

public class ConstantInfo {
	public final static String[] cn_weeks={"星期一","星期二","星期三","星期四","星期五","星期六","星期日","周总结"};  
	public final static String[] en_weeks={"monday","tuesday","wednesday","thursday","friday","saturday","sunday","summary"};
		
	public final static String[]  security={"无","SSL/TLS","SSL/TLS(接收所有证书)","STARTTLS","STARTTLS(接收所有证书)"};  
	public final static String dbName="/data/data/wang.WeekReport/myDb.db";
	public final static String tab_weekreport="tab_weekreport";
	public final static String sqlcreatetable="create table if not exists "+ 
									tab_weekreport +"(" +
									"_id integer primary key AUTOINCREMENT," +
									"newdate char(10)," + 
									"title varchar2(30)," +
									"monday varchar2(120)," +
									"tuesday varchar2(120)," +
									"wednesday varchar2(120)," +
									"thursday varchar2(120)," +
									"friday varchar2(120)," +
									"saturday varchar2(120)," +
									"sunday varchar2(120)," +
									"summary varchar2(200)" +
									")";
    public final static String NEWDATE="newdate";
    public final static String TITLE="title";
    public final static String MONDAY="monday";
    public final static String TUESDAY="tuesday";
    public final static String WEDNESDAY="wednesday";
    public final static String THURSDAY="thursday";
    public final static String FRIDAY="friday";
    public final static String SATURDAY="saturday";
    public final static String SUNDAY="sunday";
    public final static String SUMMARY="summary";
    
	public final static String tab_datavalue="tab_datavalue";
	public final static String sqlcreatetab="create table if not exists "+ 
			tab_datavalue +"(" +
			"_id integer primary key ," +
			"useremail varchar2(30)," + 
			"password varchar2(20)," +
			"secrutyno varchar2(20)," +
			"smtpserver varchar2(20)," +
			"smtpport varchar2(4) , " +
			"imapserver varchar2(20)," +
			"imapport varchar2(4) , " +
			"textsubject varchar2(30), "+
			"recvemail varchar2(30)"+
			")";
	public final static String insertinit= "INSERT INTO tab_datavalue VALUES " +
			"(1, 'xxxx@xxxx.com', 'xxx', 1, 'smtp.xxxx.com', '465', 'imap.xxxx.com','993','xxxx的周报','xxxx@xxxx.com') ";
	
   
}
