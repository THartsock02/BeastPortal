package com.thdc.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.thdc.model.Contact;

import com.thdc.util.HibernateUtil;

@Path("/contact")
@Produces({ "application/json" })
public class EmployeeService {
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	public String handleEmpoyeeLogin(@Context HttpServletRequest request, @Context HttpServletResponse response, String data){
		String json = null;
		Object obj = null;
		String stringValue = null;
		String username = null;
		String password = null;
		Contact tblContact = null;
		JSONObject jsonResponse = null;
		
		SessionFactory sessionFactory = null;
		Session session = null;
		Query query = null;

		JSONObject inputData = (JSONObject) JSONValue.parse(data);
		
		obj = inputData.get("username");
		
		if(obj != null && obj instanceof String){
			stringValue = (String) obj;
			username = stringValue;
		}
		
		obj = inputData.get("password");
		
		if(obj != null && obj instanceof String){
			stringValue = (String) obj;
			password = stringValue;
		}
		
		System.out.println("U: " + username);
		System.out.println("P: " + password);
		
		sessionFactory = HibernateUtil.getSessionFactory();
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createQuery("from Employee where username = :username and password = :password");
		query.setString("username", username);
		query.setString("password", password);
		
		tblContact = (Contact) query.uniqueResult();
		
		if(tblContact != null){
			jsonResponse = new JSONObject();
			
			jsonResponse.put("Response", "Success");
			jsonResponse.put("Employee", tblContact.toJSON());
		}
		else{
			jsonResponse = new JSONObject();
			
			jsonResponse.put("Response", "ERROR");
			jsonResponse.put("MESSAGE", "INVALID USERNAME/PASSWORD");
		}
		
		session.getTransaction().commit();
		session.close();
		
		json = jsonResponse.toJSONString();
		
		return json;
	}
	
	@GET
	@Path("/lookup/{ContactID}")
	@SuppressWarnings("unchecked")
	public String handleSearchByID(@PathParam ("ContactID") Long contactID, @Context HttpServletRequest request, @Context HttpServletResponse response){
		String json = null;
		
		SessionFactory sessionFactory = null;
		Session session = null;
		Query query = null;
		Contact tblContact = null;
		
		JSONObject jsonContact = null;
		
		JSONObject jsonResponse = null;
		
		System.out.println("HERE");
		
		sessionFactory = HibernateUtil.getSessionFactory();
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createQuery("FROM Contact where intContactID = :contactID");
		query.setLong("contactID", contactID);
		tblContact = (Contact) query.uniqueResult();
		
		System.out.println(tblContact.getStrFirstName());
		
		if(tblContact != null){
			System.out.println(":HERE");
			jsonResponse = new JSONObject();
			jsonContact = tblContact.toJSON();
			
			jsonResponse.put("Response", "Success");
			jsonResponse.put("Contact", jsonContact);
		}
		
		
		jsonResponse = new JSONObject();
		jsonResponse.put("Response", "Success");
		
		json = jsonResponse.toJSONString();
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/test")
	public String handleUserLogin(@Context HttpServletRequest request, @Context HttpServletResponse response, String data)
	{
		//System.out.println("in handleEmployeesLogin..");
		String username = null;
		String password = null;
		String stringValue = null;
		JSONObject loginCriteria = null;
		Object obj = null;
		JSONObject jsonUser = null;
		JSONObject jsonResponse = null;
		String json = null;
		Contact contact = null;
		String ePassword = null;
		Session session = null;
		Query query = null;
		SessionFactory sessionFactory = null;
		
		String token = null;

		loginCriteria = (JSONObject) JSONValue.parse(data);

		obj = loginCriteria.get("username");

		if (obj != null && obj instanceof String)
		{
			stringValue = (String) obj;
			username = stringValue;
		}

		obj = loginCriteria.get("password");

		if (obj != null && obj instanceof String)
		{
			stringValue = (String) obj;
			password = stringValue;
		}

		//ePassword = CryptographyUtils.generateHash(password);

		sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createQuery("from Contact where strUserID = :login and strPassword = :password");
		query.setString("login", username);
		query.setString("password", password);

		contact = (Contact) query.uniqueResult();

		if (contact == null)
		{
			jsonResponse = new JSONObject();
			jsonResponse.put("response", "Error");
			jsonResponse.put("message", "Invalid username/password");
		}
		else
		{
			//token = JWTUtil.createToken("blah");
			//System.out.println(token);
			jsonUser = contact.toJSON();

			jsonResponse = new JSONObject();
			jsonResponse.put("response", "Success");
			//jsonResponse.put("token", token);
			jsonResponse.put("user", jsonUser);
		}

		session.getTransaction().commit();
		session.close();

		json = jsonResponse.toJSONString();

		return json;
	}
}
