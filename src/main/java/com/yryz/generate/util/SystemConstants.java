package com.yryz.generate.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description: [系统通用常量:模板目录、编码、主题等]
 * @Author: [zhongying]
 * @CreateDate: [2016年6月2日 下午6:22:39]
 * @UpdateUser: [zhongying]
 * @UpdateDate: [2016年6月2日 下午6:22:39]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class SystemConstants {

	private static Properties properties = new Properties();

	static {
		try {
			// 加载上传文件设置参数：配置文件
			properties.load(SystemConstants.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String tableName = properties.getProperty("tableName");
	public static final String entityName = properties.getProperty("entityName");
	public static final String packageNamePre = properties.getProperty("packageNamePre");
	public static final String packageNamePre2 = properties.getProperty("packageNamePre2");
	public static final String outUrl = properties.getProperty("outUrl");
	public static final String databaseName = properties.getProperty("databaseName");
	public static final String ipName = properties.getProperty("ipName");
	public static final String portName = properties.getProperty("portName");
	public static final String userName = properties.getProperty("userName");
	public static final String passWord = properties.getProperty("passWord");
	public static final String authorName = properties.getProperty("authorName");

	public static final String primaryId = properties.getProperty("primaryId");
	public static final String businessId = properties.getProperty("businessId");

}
