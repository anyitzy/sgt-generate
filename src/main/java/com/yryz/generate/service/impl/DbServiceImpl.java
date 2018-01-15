package com.yryz.generate.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import com.yryz.generate.entity.FieldBean;
import com.yryz.generate.service.DbService;
import com.yryz.generate.util.db.DbConn;

public class DbServiceImpl implements DbService {

	Connection conn = null;

	/**
	 * 根据表名获取所有的列信息
	 * 
	 * @param tableName
	 * @return
	 */
	public void getAllColums(Map<String, Object> templateData) {
		String databaseName = templateData.get("databaseName").toString();
		String tableName = templateData.get("tableName").toString();
		
		DbConn dbConn = new DbConn(templateData);
		this.conn = dbConn.getConnection();
		
		ArrayList<FieldBean> returnList = new ArrayList<FieldBean>();

		// 获取表字段描述
		{
			Statement st = null;
			ResultSet rs = null;
			try {
				String sqlstr = "select * from " + tableName;
				st = conn.createStatement();
				rs = st.executeQuery(sqlstr);
				ResultSetMetaData resultSetMetaData = rs.getMetaData();

				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					String columName = resultSetMetaData.getColumnName(i);
					String proName = convertField(columName);
					String dataType = getTypeName(resultSetMetaData.getColumnType(i));
					FieldBean fieldBean = new FieldBean();
					fieldBean.setFieldName(columName.toLowerCase());
					String fieldType=resultSetMetaData.getColumnTypeName(i).toUpperCase();
					if(fieldType.indexOf(" ")!=-1){
						fieldType=fieldType.substring(0, fieldType.indexOf(" "));
					}
					
					if(fieldType.contains("DATETIME")){
						fieldType="TIMESTAMP";
					}
					
					if(fieldType.equals("INT")){
						fieldType="INTEGER";
					}
					
					fieldBean.setFieldType(fieldType);
					
					fieldBean.setProName(proName);
					fieldBean.setProType(dataType);
					returnList.add(fieldBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbConn.closeALL(null, st, rs, null);
			}
		}

		// 获取表字段名称和类型
		{
			Statement st = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT column_name, column_comment FROM information_schema.columns WHERE table_schema = '"
						+ databaseName + "' AND table_name = '" + tableName + "'";
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					for(FieldBean fieldBean:returnList){
						if(rs.getString(1).equals(fieldBean.getFieldName())){
							fieldBean.setProDes(rs.getString(2));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbConn.closeALL(null, st, rs, null);
			}
		}
		
		// 获取表字段名称和类型
		{
			Statement st = null;
			ResultSet rs = null;
			try {
				String sql="SELECT TABLE_COMMENT   FROM INFORMATION_SCHEMA.TABLES   WHERE table_schema = '"+databaseName+"' AND table_name = '"+tableName+"'";
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				if (rs.next()) {
					templateData.put("tableDes", rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbConn.closeALL(null, st, rs, null);
			}
		}
		templateData.put("columns",returnList );		
	}

	/**
	 * 把数据库中的字段转换为变量类型 如（user_id ----> userId）
	 * 
	 * @param field
	 * @return
	 */
	public String convertField(String field) {
		// 分隔符
		char separator = '_';
		// 转化为小写
		String variable = field.toLowerCase();

		if (variable.indexOf(separator) > -1) {
			char[] varArray = variable.toCharArray();
			for (int i = 0; i < varArray.length; i++) {
				if (varArray[i] == separator && i < varArray.length - 1) {
					varArray[i + 1] = Character.toUpperCase(varArray[i + 1]);
				}
			}
			variable = new String(varArray).replaceAll("_", "");
		}

		return variable;

	}

	/**
	 * 获取字符串型的类型名
	 * 
	 * @param type
	 * @return
	 */
	private String getTypeName(int columnType) {
		String propertyType = "";
		if ( Types.VARCHAR == columnType || Types.CHAR == columnType ) {
			propertyType = "String";
		} else if ( Types.BIGINT == columnType ) {
			propertyType = "Long";
		} else if ( Types.DECIMAL == columnType ) {
			propertyType = "BigDecimal";	
		} else if ( Types.INTEGER == columnType || Types.TINYINT == columnType ||  Types.SMALLINT == columnType) {
			propertyType = "Integer";
		} else if ( Types.DOUBLE == columnType ) {
			propertyType = "Double";
		} else if ( Types.FLOAT == columnType ) {
			propertyType = "Float";
		} else if ( Types.BOOLEAN == columnType ) {
			propertyType = "Boolean";
		}else if ( Types.TIME == columnType || Types.DATE == columnType ||  Types.TIMESTAMP == columnType) {
			propertyType = "Date";
		}else{
			propertyType = "String";
		}
		return propertyType;

	}

}
