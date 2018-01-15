package com.yryz.generate.service;

import java.util.Map;

public interface DataService {

	/**
	 * 代码生成
	 * @param params
	 */
	public void generate(Map<String, Object> params);
	

	/**
	 * 根据模板生成具体某个文件
	 * @param templateName
	 * @param templateData
	 * @param packageName
	 * @param fileName
	 */
	public void generateFile(String templateName, Map<String, Object> templateData,
			String packageName, String fileName);
	
}
