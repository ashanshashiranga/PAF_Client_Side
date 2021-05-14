package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;

/**
 * Servlet implementation class PaymentsAPI
 */
@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Payment paymentObj = new Payment();
	
    /**
     * Default constructor
     */
    public PaymentsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("inserted successfully");
		
		String output = paymentObj.insertPayments(request.getParameter("cardNumber"), 
				request.getParameter("expireDate"), 
				request.getParameter("cvv"), 
				request.getParameter("paymentAmount")); 
		
				response.getWriter().write(output);
		
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Retreive data successfully");
		
		Map paras = getParasMap(request); 
		 String output = paymentObj.updatePayments(paras.get("hidPaymentIDSave").toString(), 
		 paras.get("cardNumber").toString(), 
		 paras.get("expireDate").toString(), 
		 paras.get("cvv").toString(), 
		 paras.get("paymentAmount").toString()); 
		 
		response.getWriter().write(output); 
		
		
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map paras = getParasMap(request); 
		 String output = paymentObj.deletePayments(paras.get("paymentId").toString()); 
		response.getWriter().write(output); 
			
	}

	
	private static Map getParasMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			
			Scanner scanner= new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext()?
			scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			for (String param : params) {
			
			String[] p = param.split("=");	
			map.put(p[0], p[1]);
			
		}
	
	}
	catch (Exception e) {
		
	}
	
	return map;
	
	}
	
}
