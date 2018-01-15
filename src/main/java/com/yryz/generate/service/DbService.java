package com.yryz.generate.service;

import java.util.Map;

public interface DbService {
	
	/**根据表名获取所有的列信息
	 * @param tableName
	 * @return
	 */
	public void getAllColums(Map<String, Object> templateData);
	
}
