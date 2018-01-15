package ${daoPackageName};

import ${entityPackageName}.${entityName};
import ${dtoPackageName}.${dtoName};
import com.yryz.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
  * @ClassName: ${daoName}
  * @Description: ${entityName}数据访问接口
  * @author ${authorName}
  * @date ${currentTime}
  *
 */
@Repository
public interface ${daoName} extends BaseDao {

    List<${entityName}> selectList(${dtoName} ${dtoName?uncap_first});

}