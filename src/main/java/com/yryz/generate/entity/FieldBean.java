package com.yryz.generate.entity;

/**
 * 列对象
 * 
 * @author Xiaobo
 *
 */
public class FieldBean {
	/**
	 * 数据库原字段名
	 */
	private String fieldName; 
	
	/**
	 * 数据库原字段类型
	 */
	private String fieldType; 
	
	/**
	 * 转换成实体类的-变量名
	 */
	private String proName; 
	
	/**
	 * 转换成实体类的-变量类型
	 */
	private String proType; 
	
	/**
	 * 转换成实体类的变量-变量注释
	 */
	private String proDes;
	
	
	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getProDes() {
		return proDes;
	}

	public void setProDes(String proDes) {
		this.proDes = proDes;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
