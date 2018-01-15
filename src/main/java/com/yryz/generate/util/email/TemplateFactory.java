/*******************************************************************************
 * Licensed to the OKChem
 *
 * http://www.okchem.com
 *
 *******************************************************************************/
package com.yryz.generate.util.email;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * @Desc: Mail content template production factory class
 * @author eric.zhong
 */

public class TemplateFactory {
	// Log object
	private static Logger log = LoggerFactory.getLogger(TemplateFactory.class);
	// Template file path
	private static String templatePath = "/templates";
	// Template file content coding
	private static final String ENCODING = "utf-8";
	// Template generation configuration
	private static Configuration conf = new Configuration();
	// Mail template cache pool
	private static Map<String, Template> tempMap = new HashMap<String, Template>();

	static {
		// Set template file path
		conf.setClassForTemplateLoading(TemplateFactory.class, templatePath);
	}

	/**
	 * Obtain the specified template by using the template file name
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param name
	 *           TemplateName
	 * @return Template TemplateObject
	 * @throws IOException
	 * @Description:
	 * @return Template
	 */
	private static Template getTemplateByName(String name) throws IOException {
		if (tempMap.containsKey(name)) {
			log.debug("the template is already exist in the map,template name :" + name);
			// The template is returned directly in the cache.
			return tempMap.get(name);
		}
		// When the template is not in the cache, a new template is generated
		// and put into the cache.
		Template temp = conf.getTemplate(name);
		tempMap.put(name, temp);
		log.debug("the template is not found  in the map,template name :" + name);
		return temp;
	}

	/**
	 * Output the content to the console based on the specified template
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param name
	 *           template file name
	 * @param map
	 *           与模板内容转换对象
	 * @Description:
	 * @return void
	 */
	public static void outputToConsole(String name, Map<String, String> map) {
		try {
			// The template file can be exported to the corresponding stream by
			// Template.
			Template temp = getTemplateByName(name);
			temp.setEncoding(ENCODING);
			temp.process(map, new PrintWriter(System.out));
		}
		catch (TemplateException e) {
			log.error(e.toString(), e);
		}
		catch (IOException e) {
			log.error(e.toString(), e);
		}
	}

	/**
	 * Output content directly to a file based on the specified template
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param name
	 *           template fiel name
	 * @param map
	 *           Template content conversion object
	 * @param outFile
	 *           Absolute path of the output file
	 * @Description:
	 * @return void
	 */
	public static void outputToFile(String name, Map<String, String> map, String outFile) {
		FileWriter out = null;
		try {
			out = new FileWriter(new File(outFile));
			Template temp = getTemplateByName(name);
			temp.setEncoding(ENCODING);
			temp.process(map, out);
		}
		catch (IOException e) {
			log.error(e.toString(), e);
		}
		catch (TemplateException e) {
			log.error(e.toString(), e);
		}
		finally {
			try {
				if (out != null)
					out.close();
			}
			catch (IOException e) {
				log.error(e.toString(), e);
			}
		}
	}

	/**
	 * 
	 * @Date:2016/10/14
	 * @author eric.zhong
	 * @param name
	 *           template file name
	 * @param map
	 *           Template content conversion object
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 * @Description:
	 * @return String
	 */
	public static String generateHtmlFromFtl(String name, Map<String, String> map) throws IOException, TemplateException {
		Writer out = new StringWriter(2048);
		Template temp = getTemplateByName(name);
		temp.setEncoding(ENCODING);
		temp.process(map, out);
		return out.toString();
	}
}
