package member.dto;

public class Member2DTO {
	private String userid;
	private String passwd;
	private String name;
	//기본생성자
	public Member2DTO() {
		// TODO Auto-generated constructor stub
	}
	//매개변수 생성자
	public Member2DTO(String userid, String passwd, String name) {
		super();
		this.userid = userid;
		this.passwd = passwd;
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Member2DTO [userid=" + userid + ", passwd=" + passwd + ", name=" + name + "]";
	}
	
}
