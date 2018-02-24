/*  -> Designed for testing and development purposes.
 *  -> Project to design a small places prototype app.
 *  -> Development Phase -- Premature.
 *  -> Project Type -- Educational.
 *  -> Institute -- University Institute Of Technology, Burdwan University.
 *  -> Owner/Code file Designer :
 *             @ Name - Palash Sarkar.
 *             @ Department - Computer Science And Engineering.
 *             @ Roll.Number - 2014_1038.
 *             @ Email - palashsarkar0007@gmail.com.
 *  -> Copyright Norms - Every piece of code given below 
 *                       has been written by 'Palash Sarkar (Tj07)'©,
 *                       and he holds the rights to the file. Not meant to be
 *                       copied or tampered without prior permission
 *                       from the author. 
 *  -> Guide - Asst.Proff. Dr. Sumit Gupta.            
 */

package com.aps.coudapp.servlets;

//import com.aps.datahandling.Email;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.aps.coudapp.datahandling.DbopProducts;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet(description = "Core Servlet For Begining all Operations", urlPatterns = { "/ProductsServlet" })
public final class ProductsServlet extends HttpServlet
{
         private static final long serialVersionUID = 1L;
         private static  InetAddress ip ;
         private static  String ipadd  ;
         
             @Override
	public void init()
	{
                    //Email.buildEmail((getServletContext().getRealPath("/WEB-INF"))+File.separator+"Credentials");
                    try{
	                System.setProperty("java.net.preferIPv4Stack", "true");
                              ip = InetAddress.getLocalHost();
	                ipadd = ip.getHostAddress();
	          }catch (UnknownHostException e)
		{
		       e.printStackTrace();
		}
	}
          
               @Override
	 protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
               {
                     response.setContentType("text/json");
	       response.setCharacterEncoding("UTF-8");
	       response.setBufferSize(8192);
                     PrintWriter out = response.getWriter();
                     BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
                     StringBuilder req = new StringBuilder();
                     String line = "";
                     DbopProducts ins = new DbopProducts ();
		 
                     while ((line = rd.readLine()) != null) 
                       {
		 req.append(line);
                       }
                       rd.close();
                       
                       String result = "";
                       
	         try 
                        {
	                JSONObject jo = ((JSONObject)((new JSONParser ()).parse(req.toString())));
	                JSONArray jarr = (JSONArray)jo.get("PROD_ARR");
                              
                              for (int i=0;i<jarr.size();i++)
                              {
                                   result += (ins).insert( (String)((JSONObject)(jarr.get(i))).get("PROD_CAT_ID"),
                                                           (String)((JSONObject)(jarr.get(i))).get("PROD_NAME"),
                                                           (String)((JSONObject)(jarr.get(i))).get("PROD_PRICE"),
                                                           (String)((JSONObject)(jarr.get(i))).get("PROD_TYPE"),
                                                           (String)((JSONObject)(jarr.get(i))).get("PROD_DIR") ) + "<br/>";
                              }
                              
                        }catch(ParseException pe){
	                                     System.out.println(pe);
                                                   result = "{\"STATUS\":\"Error!!\",\"RESPONSE\":\"Json Parse Error!!\"}";
	                                   }
                        out.print (result);
                        out.flush ();
                        out.close ();
               }
           
               @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
               {
                     response.setContentType ("text/json");
	       response.setCharacterEncoding ("UTF-8");
	       response.setBufferSize (8192);
                     PrintWriter out = response.getWriter ();
                     DbopProducts ret = new DbopProducts ();
                     System.out.println(request.getParameter ("cat_code"));
                     
                    if ((Integer.parseInt (request.getParameter ("cat_code")))==0)
                    {
                                out.print (ret.retrieve ()); 
                    }
                    else if ((Integer.parseInt (request.getParameter ("cat_code")))>0)
                          {
                                 out.print (ret.retrieve (request.getParameter ("cat_code"))); 
                          }
                    else
                    {
                         out.print ("{\"STATUS\":\"Error!!\",\"RESPONSE\":\"Incorrect Category/Category doesnot Exist!!\"}");
                    }
                    out.flush ();
                    out.close ();
               }
}
