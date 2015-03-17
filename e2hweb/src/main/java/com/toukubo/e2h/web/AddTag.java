package com.toukubo.e2h.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import com.toukubo.e2h.Note;
import com.toukubo.e2h.Notes;
import com.toukubo.e2h.Tagging;

@WebServlet("/AddTag")
public class AddTag extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	response.setHeader("Access-Control-Allow-Origin", "*");
    		String guid = request.getParameter("guid");
    		String tag =  request.getParameter("tag");
    		Note note = new Note(guid);
    		note.addTag(tag);
    		response.getWriter().write("status:success");
    		response.flushBuffer();
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
