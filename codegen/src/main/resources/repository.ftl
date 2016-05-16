package ${repositoryPackageName};

import ${modelPackageName}.${modelSimpleName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

@Repository
public interface ${repositorySimpleName} extends JpaRepository<${modelSimpleName}, String>, JpaSpecificationExecutor<${modelSimpleName}> {
<#if mySearchTemplates??>
    <#list mySearchTemplates as method>

    List<${modelSimpleName}> findBy${method.name?cap_first}(${method.type} ${method.name});

    </#list>
</#if>
}