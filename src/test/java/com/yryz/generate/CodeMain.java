package com.yryz.generate;

import java.util.HashMap;
import java.util.Map;

import com.yryz.generate.service.DataService;
import com.yryz.generate.service.impl.DataServiceImpl;
import com.yryz.generate.util.SystemConstants;
/**
 * 
  * @ClassName: CodeMain
  * @Description: 注意：该程序读取的是application.properties文件的配置信息
  * @author zhongying
  * @date 2017年8月31日 下午6:01:54
  *
 */
public class CodeMain {

	public static void main(String[] args) {
		System.out.println("生成代码start......");

		//获取页面或者配置文件的参数
		Map<String, Object> templateData = new HashMap<String, Object>();
		templateData.put("tableName", SystemConstants.tableName);
		System.out.println("表名=="+SystemConstants.tableName);
		
		templateData.put("entityName", SystemConstants.entityName);
		System.out.println("实体类名称=="+SystemConstants.entityName);
		
		templateData.put("packageNamePre", SystemConstants.packageNamePre);
		System.out.println("包名前缀=="+SystemConstants.packageNamePre);
		
		templateData.put("outUrl", SystemConstants.outUrl);
		System.out.println("生成文件路径为=="+SystemConstants.outUrl);
		
		templateData.put("authorName", SystemConstants.authorName);
		System.out.println("以后代码出问题找=="+SystemConstants.authorName);
		
				
		templateData.put("databaseName", SystemConstants.databaseName);
		templateData.put("ipName", SystemConstants.ipName);
		templateData.put("portName", SystemConstants.portName);
		templateData.put("userName", SystemConstants.userName);
		templateData.put("passWord", SystemConstants.passWord);

		templateData.put("primaryId", SystemConstants.primaryId);
		templateData.put("businessId", SystemConstants.businessId);

		DataService dataService = new DataServiceImpl();

		try {
			//生成代码文件
			dataService.generate(templateData);
		} catch (Exception e) {
		}

		System.out.println("生成代码end......");
	}

}
