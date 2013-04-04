package edu.gatech.hvz.entities;

public class Email 
{
	private String subject;
	private String body;
	private String name;
	private String toEmail;
	private String hvzemail;
	
	public Email(String subject, String body, String name, String email)
	{
		this.subject = subject;
		this.body = body;
		this.name = name;
		this.toEmail = email;
		this.hvzemail = "hvzgatech@gmail.com";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getHvzemail() {
		return hvzemail;
	}

	public void setHvzemail(String hvzemail) {
		this.hvzemail = hvzemail;
	}

	
}
