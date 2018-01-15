package com.yryz.generate.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.yryz.generate.service.DataService;
import com.yryz.generate.service.DbService;
import com.yryz.generate.util.freeMaker.FreeMakerUtil;

public class DataServiceImpl implements DataService {

	public void generate(Map<String, Object> templateData) {
		//======================包名配置======================
		{
			String packageNamePre=templateData.get("packageNamePre").toString();
			
			templateData.put("entityPackageName", String.format(packageNamePre + ".entity",
					templateData.get("entityName").toString().toLowerCase()));


			templateData.put("dtoPackageName", String.format(packageNamePre + ".dto",
					templateData.get("entityName").toString().toLowerCase()));

			templateData.put("voPackageName", String.format(packageNamePre + ".vo",
					templateData.get("entityName").toString().toLowerCase()));

			templateData.put("daoPackageName", String.format(packageNamePre + ".dao.persistence",
					templateData.get("entityName").toString().toLowerCase()));
			templateData.put("mapperPackageName", packageNamePre + ".mapper");


			templateData.put("servicePackageName", String.format(packageNamePre + ".service",
					templateData.get("entityName").toString().toLowerCase()));

			templateData.put("serviceImplPackageName", String.format(packageNamePre + ".service.impl",
					templateData.get("entityName").toString().toLowerCase()));

			templateData.put("apiPackageName", String.format(packageNamePre + "",
					templateData.get("entityName").toString().toLowerCase()));

			templateData.put("providerPackageName", String.format(packageNamePre  + ".provider",
					templateData.get("entityName").toString().toLowerCase()));


			templateData.put("controllerPackageName", String.format(packageNamePre  + ".controller",
			templateData.get("entityName").toString().toLowerCase()));
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			templateData.put("currentTime", myFmt.format(new Date()));
		}
		
		//======================生成文件配置======================
		try {
			// 生成Entity  
			String entityName=String.format("%s", templateData.get("entityName").toString());
			this.generateFile("entity.ftl", templateData, templateData.get("entityPackageName").toString(), entityName+".java");

			// 生成DTO
			String dtoName = String.format("%sDto", templateData.get("entityName").toString());
			templateData.put("dtoName", dtoName);
			this.generateFile("dto.ftl", templateData, templateData.get("dtoPackageName").toString(),
					dtoName + ".java");

			// 生成VO
			String voName = String.format("%sVo", templateData.get("entityName").toString());
			templateData.put("voName", voName);
			this.generateFile("vo.ftl", templateData, templateData.get("voPackageName").toString(),
					voName + ".java");

			// 生成DAO
			String daoName = String.format("%sDao", templateData.get("entityName").toString());
			templateData.put("daoName", daoName);
			this.generateFile("dao.ftl", templateData, templateData.get("daoPackageName").toString(),
					daoName + ".java");

			// 生成Mapper
			String mapperName = String.format("%sMapper", templateData.get("entityName").toString());
			this.generateFile("mapper.ftl", templateData, templateData.get("mapperPackageName").toString(),
					mapperName+".xml");


			// 生成Service
			String serviceName = String.format("%sService", templateData.get("entityName").toString());
			templateData.put("serviceName", serviceName);
			this.generateFile("service.ftl", templateData, templateData.get("servicePackageName").toString(),
					serviceName + ".java");

			// 生成Service实现类
			String serviceImplName = String.format("%sServiceImpl", templateData.get("entityName").toString());
			templateData.put("serviceImplName", serviceImplName);
			this.generateFile("serviceImpl.ftl", templateData, templateData.get("serviceImplPackageName").toString(),
					serviceImplName + ".java");


			// 生成API
			String apiName = String.format("%sApi", templateData.get("entityName").toString());
			templateData.put("apiName", apiName);
			this.generateFile("api.ftl", templateData, templateData.get("apiPackageName").toString(),
					apiName + ".java");

			// 生成Provider实现类
			String providerName = String.format("%sProvider", templateData.get("entityName").toString());
			templateData.put("providerName", providerName);
			this.generateFile("provider.ftl", templateData, templateData.get("providerPackageName").toString(),
					providerName + ".java");


			// 生成Controller实现类  
			String controllerName = String.format("%sController", templateData.get("entityName").toString());
			templateData.put("controllerName", controllerName);
			this.generateFile("controller.ftl", templateData, templateData.get("controllerPackageName").toString(),
					controllerName + ".java");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成文件
	 * 
	 * @param templateName
	 *            模板名称
	 * @param packageName
	 *            包名
	 * @param templateData
	 *            参数名
	 * @param fileName
	 *            文件名
	 */
	public void generateFile(String templateName, Map<String, Object> templateData, String packageName,
			String fileName) {
		templateData.put("fileName", fileName);
		
		DbService dbService = new DbServiceImpl();
		switch (templateName) {
		case "entity.ftl":
			// 数据库参数
			dbService.getAllColums(templateData);
			break;
		case "mapper.ftl":
			dbService.getAllColums(templateData);
			break;
		default:
			break;
		}
		
		try {
			// 默认生成文件的路径
			FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
			freeMakerUtil.generateFile(templateName, templateData, packageName, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
