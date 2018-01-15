<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${daoPackageName}.${daoName}">

	<!--BaseResultMap-->
    <resultMap id="BaseResultMap" type="${entityPackageName}.${entityName}">
    <#list columns as pro>
	<#if pro.proName == primaryId>
		<id column="${primaryId}" property="${primaryId}" jdbcType="BIGINT"/>
	<#else>	
		<result column="${pro.fieldName}" property="${pro.proName}" jdbcType="${pro.fieldType}"/>
	</#if>
    </#list>
    </resultMap>
	
	<!--Base_Column_List-->
	<sql id="Base_Column_List"><#list columns as pro><#if pro_index == 0>${pro.fieldName}<#else>,${pro.fieldName}</#if></#list>
    </sql>

     <!-- 查询${entityName}详情 -->
    <select id="selectByKid" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where del_flag = 0
		and ${businessId} = ${r"#{" + "${businessId}" + r",jdbcType=BIGINT}"}
    </select>
    
    <!-- 删除${entityName}-->
    <delete id="delete" parameterType="java.lang.Long">
        update ${tableName}
        set del_flag = 1
        where ${businessId} = ${r"#{" + "${businessId}" + r",jdbcType=BIGINT}"}
    </delete>
  
  
  	<!-- 新增${entityName}-->
    <insert id="insert"  parameterType="${entityPackageName}.${entityName}">
	    insert into ${tableName} (<#list columns as pro><#if pro_index == 0><#elseif pro_index == 1>${pro.fieldName}<#else>,${pro.fieldName}</#if></#list>)
	    values (
	    <#list columns as pro>
	    <#if pro_index == 0>
	    <#elseif pro_index == 1>
	    	${r"#{" + pro.proName + r",jdbcType=" + pro.fieldType +r"}"}
	    <#else>
	    	<#if pro.fieldName == "create_date" || pro.fieldName == "last_update_date">
			,NOW()
			<#else>
				,${r"#{" + pro.proName + r",jdbcType=" + pro.fieldType +r"}"}
			</#if>
	    </#if>
    	</#list>	
	    )
	</insert>
	
	<!--按需新增${entityName}-->
    <insert id="insertByPrimaryKeySelective"   parameterType="${entityPackageName}.${entityName}">
	    insert into ${tableName} 
	    <trim prefix="(" suffix=")" suffixOverrides=",">
	    <#list columns as pro>
	    <#if pro.fieldName != primaryId>
	    <#if pro.fieldType == 'VARCHAR'>
        <if test="${pro.proName} != null and ${pro.proName} != ''">
	    <#else>
        <if test="${pro.proName} != null">
        </#if>
			${pro.fieldName},
	    </if>
	    </#if>
    	</#list>	
	    </trim>
	    <trim prefix="values (" suffix=")" suffixOverrides=",">
	    <#list columns as pro>
	    <#if pro.fieldName != primaryId>
	    <#if pro.fieldType == 'VARCHAR'>
        <if test="${pro.proName} != null and ${pro.proName} != ''">
	    <#else>
        <if test="${pro.proName} != null">
        </#if>
			${r"#{" + pro.proName + r",jdbcType=" + pro.fieldType +r"}"},
	    </if>
	    </#if>
    	</#list>	
	    </trim>
	</insert>
	
	
	<!-- 按需修改${entityName}-->   
    <update id="update" parameterType="${entityPackageName}.${entityName}">
        update ${tableName}
        <set>
           <#list columns as pro>
           <#if pro.fieldName != primaryId && pro.fieldName != businessId>
           	<#if pro.fieldType == 'VARCHAR'>
            <if test="${pro.proName} != null and ${pro.proName} != ''">
            <#else>
             <if test="${pro.proName} != null">
            </#if>
    			${pro.fieldName} = ${r"#{" + pro.proName + r",jdbcType=" + pro.fieldType +r"}"},
    		</if>
    		</#if>
    	</#list>
        </set>
        where ${businessId} = ${r"#{" + "${businessId}" + r",jdbcType=BIGINT}"}
    </update>
    
    <!-- 查询${entityName}列表 -->
    <select id="selectList" parameterType="${dtoPackageName}.${dtoName}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName} where del_flag = 0

    </select>
    
</mapper>