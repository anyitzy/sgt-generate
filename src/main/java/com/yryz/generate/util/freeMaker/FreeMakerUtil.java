package com.yryz.generate.util.freeMaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {

	private static final Logger logger = LoggerFactory.getLogger(FreeMakerUtil.class);

	/**
	 * 
	 * 获取模板文件
	 * 
	 * @param name
	 * @return
	 */
	public Template getTemplate(String name) {
		try {
			Configuration cfg = new Configuration();
			cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
			Template template = cfg.getTemplate(name);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 控制台输出
	 * 
	 * @param name
	 * @param root
	 */
	public void print(String templateName, Map<String, Object> root) {

		try {
			Template template = this.getTemplate(templateName);
			template.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据Freemark模板，生成文件
	 * 
	 * @param templateName:模板名
	 * @param root：数据原型
	 * @throws Exception 
	 */
	public void generateFile(String templateName, Map<String, Object> root, String packageName,String fileName) throws Exception {
		FileOutputStream fos=null;
		Writer out =null;
		try {
			// 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
			String entityName = (String) root.get("entityName");
			String fileFullName = String.format(fileName, entityName);
			packageName=String.format(packageName,entityName.toLowerCase());
			
			String fileStylePackageName = packageName.replaceAll("\\.", "/");
			File file = new File(root.get("outUrl").toString() + "/" + fileStylePackageName + "/" + fileFullName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

			Template template = this.getTemplate(templateName);
			fos = new FileOutputStream(file); 
			out = new OutputStreamWriter(fos);
			template.process(root, out);
			out.flush();
		} catch (Exception e) {
			logger.error("生成文件失败!", e);
			throw new  Exception("生成文件失败!");
		} finally {
			try {
				if (out != null)
					fos.close();
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
