package com.toukubo.e2h.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.toukubo.e2h.Conversion;
import com.toukubo.e2h.Notes;

/**
 * Servlet implementation class SearchNotesServlet
 */
@WebServlet("/SearchNotesServlet")
public class SearchNotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchNotesServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	response.setHeader("Access-Control-Allow-Origin", "*");
//        	String query = "reminderOrder:* -reminderTime:day+1 -reminderDoneTime:day+1";
        	String query = request.getParameter("query");
    		Collection<com.toukubo.e2h.Note> notes = new Notes(query).getOurNotes();
    		Map<String, Object> map = new HashMap<String,Object>();
            map.put("notes", notes);


//    		Conversion conversion = new Conversion();
    		System.err.println(map);
    		
    		
    		//json
    		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
    		JSON.encode(map,writer);
    		writer.flush();
    		writer.close();
    		
    		
    		//mustache 
//            MustacheFactory mf = new DefaultMustacheFactory();
//            Mustache mustache = mf.compile("views/index.html");
//
//        	StringWriter stringWriter = new StringWriter();
        	
//        	mustache.execute(new PrintWriter(stringWriter), map).flush();
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//		response.getWriter().write(conversion.getResult());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
