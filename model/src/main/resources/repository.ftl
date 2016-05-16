package ${repositoryPackageName};

import ${modelPackageName}.${modelSimpleName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* Auto-gen class
*/
@Repository
public interface ${repositorySimpleName} extends JpaRepository<${modelSimpleName}, String>, JpaSpecificationExecutor<${modelSimpleName}> {
<#if mySearchTemplates??>
<#list mySearchTemplates as method>
<#if method.referenceName??>

    List<${modelSimpleName}> findBy${method.referenceName?cap_first}${method.name?cap_first}(${method.type} ${method.name});
    <#if method.pageable=true>

    Page<${modelSimpleName}> findBy${method.referenceName?cap_first}${method.name?cap_first}(${method.type} ${method.name}, Pageable pageable);
    </#if>
    <#if method.sortable=true>

    List<${modelSimpleName}> findBy${method.referenceName?cap_first}${method.name?cap_first}(${method.type} ${method.name}, Sort sort);
    </#if>
<#else>

    List<${modelSimpleName}> findBy${method.name?cap_first}(${method.type} ${method.name});
    <#if method.pageable=true>

    Page<${modelSimpleName}> findBy${method.name?cap_first}(${method.type} ${method.name}, Pageable pageable);
    </#if>
    <#if method.sortable=true>

    List<${modelSimpleName}> findBy${method.name?cap_first}(${method.type} ${method.name}, Sort sort);
    </#if>
</#if>
</#list>
</#if>
}