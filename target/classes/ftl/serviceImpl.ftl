package ${serviceImplPackageName};

import com.yryz.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;
import com.yryz.common.dao.BaseDao;
import com.yryz.common.service.BaseServiceImpl;
import com.yryz.common.web.PageModel;
import com.yryz.component.rpc.dto.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${voPackageName}.${voName};
import ${entityPackageName}.${entityName};
import ${dtoPackageName}.${dtoName};
import ${daoPackageName}.${daoName};
import ${servicePackageName}.${serviceName};
import java.util.ArrayList;
import java.util.List;


@Service
public class ${serviceImplName} extends BaseServiceImpl implements ${serviceName} {

    @Autowired
    private ${daoName} ${daoName?uncap_first};

    protected BaseDao getDao() {
        return ${daoName?uncap_first};
    }

    public PageList<${voName}> selectList(${dtoName} ${dtoName?uncap_first}){
        PageUtils.startPage(${dtoName?uncap_first}.getCurrentPage(), ${dtoName?uncap_first}.getPageSize());
        List<${entityName}> list = ${daoName?uncap_first}.selectList(${dtoName?uncap_first});
        List<${voName}> ${voName?uncap_first}List = new ArrayList <${voName}>();
        if(list != null && list.size() > 0) {
            for(${entityName} ${entityName?uncap_first} : list){
                ${voName} ${voName?uncap_first} = new ${voName}();
                //${entityName} to ${voName}
                ${voName?uncap_first}List.add(${voName?uncap_first});
            }
        }
        return new PageModel<${voName}>().getPageList(${voName?uncap_first}List);
    }


    public ${voName} detail(Long ${entityName?uncap_first}Id) {
        ${entityName} ${entityName?uncap_first} = ${daoName?uncap_first};.selectByKid(${entityName}.class,${entityName?uncap_first}Id);
        ${voName} ${voName?uncap_first} = new ${voName}();
        if (${voName?uncap_first} != null) {
            //${entityName} to ${voName}
        }
        return ${voName?uncap_first};
    }
 }
