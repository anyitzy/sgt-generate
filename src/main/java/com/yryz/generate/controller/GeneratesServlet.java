package com.yryz.generate.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yryz.generate.service.DataService;
import com.yryz.generate.service.impl.DataServiceImpl;

/**
 * Servlet implementation class GeneratesServlet
 */
@WebServlet("/GeneratesServlet")
public class GeneratesServlet extends HttpServlet {
       
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * 生成代码方法
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> mapResult = new HashMap<String, Object>();
		
		//模板数据参数设值
		Map<String,Object> templateData=new HashMap<String,Object>();
		templateData.put("tableName", request.getParameter("tableName"));
		templateData.put("entityName", request.getParameter("entityName"));
		templateData.put("packageNamePre", request.getParameter("packageNamePre"));
		templateData.put("outUrl", request.getParameter("outUrl"));
		templateData.put("authorName", request.getParameter("authorName"));
		
		//数据库参数设值
		templateData.put("databaseName", request.getParameter("databaseName"));
		templateData.put("ipName", request.getParameter("ipName"));
		templateData.put("portName", request.getParameter("portName"));
		templateData.put("userName", request.getParameter("userName"));
		templateData.put("passWord", request.getParameter("passWord"));
		
		DataService dataService = new DataServiceImpl();
		
		try {
			dataService.generate(templateData);
			mapResult.put("code", "200");
		} catch (Exception e) {
			mapResult.put("message", "生成失败！");
		}
		
		String userJson = JSON.toJSONString(mapResult);
		OutputStream out = response.getOutputStream();
		out.write(userJson.getBytes("UTF-8"));
		out.flush();

	}
	
	
	
}
