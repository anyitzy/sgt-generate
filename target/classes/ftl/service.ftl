package ${servicePackageName};

import com.yryz.common.service.BaseService;
import ${dtoPackageName}.${dtoName};
import ${entityPackageName}.${entityName};
import ${voPackageName}.${voName};
import org.springframework.stereotype.Repository;

import com.yryz.component.rpc.dto.PageList;

/**
 * 
  * @ClassName: ${serviceName}
  * @Description: ${entityName}业务访问接口
  * @author ${authorName}
  * @date ${currentTime}
  *
 */
@Repository
public interface ${serviceName} extends BaseService {

   PageList<${voName}> selectList(${dtoName} ${dtoName?uncap_first});

   ${voName} detail(Long ${entityName?uncap_first}Id);

}