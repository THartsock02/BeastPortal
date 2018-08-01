package com.thdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.simple.JSONObject;

@Entity
@Table(name="contact")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="intContactID", unique= true, nullable = false)
	private Long intContactID;
	@Column(name="strFirstName", unique=false, nullable=false, length=50)
	private String strFirstName;
	@Column(name="strLastName", unique=false, nullable=false, length=50)
	private String strLastName;
	@Column(name="strUserID", unique=false, nullable=false, length=7)
	private String strUserID;
	@Column(name="strPassword", unique=false, nullable=false, length=256)
	private String strPassword;
	
	public Long getIntContactID() {
		return intContactID;
	}
	public void setIntContactID(Long intContactID) {
		this.intContactID = intContactID;
	}
	public String getStrFirstName() {
		return strFirstName;
	}
	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}
	public String getStrLastName() {
		return strLastName;
	}
	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}
	public String getStrUserID() {
		return strUserID;
	}
	public void setStrUserID(String strUserID) {
		this.strUserID = strUserID;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		
		if(this.getIntContactID() != null){
			json.put("intContactID", this.getIntContactID());
		}
		if(this.getStrFirstName() != null){
			json.put("strFirstName", this.getStrFirstName());
		}
		if(this.getStrLastName() != null){
			json.put("strLastName", this.getStrLastName());
		}
		if(this.getStrUserID() != null){
			json.put("strUserID", this.getStrUserID());
		}
		if(this.getStrPassword() != null){
			json.put("strPassword", this.getStrPassword());
		}
		return json;
	}
}
