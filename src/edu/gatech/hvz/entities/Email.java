package edu.gatech.hvz.entities;

public class Email {
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

}
