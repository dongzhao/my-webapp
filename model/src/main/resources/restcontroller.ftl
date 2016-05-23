package ${controllerPackageName};

import ${modelPackageName}.${modelClassName};
import ${repositoryPackageName}.${repositoryClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;

import javax.validation.Valid;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

/**
* Auto-gen class
*/
@RestController
@RequestMapping(value = "${urlPathName}/${modelClassName?uncap_first}")
public class ${controllerClassName}{

    @Autowired
    private ${repositoryClassName} ${repositoryClassName?uncap_first};

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName(){
        return ${repositoryClassName}.class.getSimpleName();
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<${modelClassName}> create(@RequestBody @Valid final ${modelClassName} ${modelClassName?uncap_first}){
        ${modelClassName} result = ${repositoryClassName?uncap_first}.save(${modelClassName?uncap_first});
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<${modelClassName}> update(@RequestBody @Valid final ${modelClassName} ${modelClassName?uncap_first}){
        ${modelClassName} result = ${repositoryClassName?uncap_first}.findOne(${modelClassName?uncap_first}.getId());
<#if mySearchTemplates??>
    <#list mySearchTemplates as mySearchTemplate>
        <#if mySearchTemplate.referenceName??>
        <#elseif mySearchTemplate.type=="int">
        if(result.get${mySearchTemplate.name?cap_first}() != ${modelClassName?uncap_first}.get${mySearchTemplate.name?cap_first}()){
            result.set${mySearchTemplate.name?cap_first}(${modelClassName?uncap_first}.get${mySearchTemplate.name?cap_first}());
        }
        <#else>
        if(!result.get${mySearchTemplate.name?cap_first}().equals(${modelClassName?uncap_first}.get${mySearchTemplate.name?cap_first}())){
            result.set${mySearchTemplate.name?cap_first}(${modelClassName?uncap_first}.get${mySearchTemplate.name?cap_first}());
        }
        </#if>
    </#list>
</#if>
        ${modelClassName} updResult = ${repositoryClassName?uncap_first}.save(result);
        return new ResponseEntity<${modelClassName}>(updResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public void delete(@PathVariable("id") String id) {
        ${repositoryClassName?uncap_first}.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<${modelClassName}> findById(@PathVariable("id") String id) {
        ${modelClassName} result = ${repositoryClassName?uncap_first}.findOne(id);
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> findAll() {
        List<${modelClassName}> results = ${repositoryClassName?uncap_first}.findAll();
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<PagedResources<${modelClassName}>> findAll(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<${modelClassName}> results = ${repositoryClassName?uncap_first}.findAll(pageable);
        return new ResponseEntity<PagedResources<${modelClassName}>>(assembler.toResource(results), HttpStatus.OK);
    }

<#if mySearchTemplates??>
    <#list mySearchTemplates as mySearchTemplate>
        <#if mySearchTemplate.referenceName??>

    @RequestMapping(value = "/${mySearchTemplate.referenceName}/${mySearchTemplate.name}/{${mySearchTemplate.name}}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> searchBy${mySearchTemplate.referenceName?cap_first}${mySearchTemplate.name?cap_first}(@PathVariable("${mySearchTemplate.name}") ${mySearchTemplate.type} ${mySearchTemplate.name}) {
        List<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${mySearchTemplate.referenceName?cap_first}${mySearchTemplate.name?cap_first}(${mySearchTemplate.name});
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }
        <#else>

    @RequestMapping(value = "/${mySearchTemplate.name}/{${mySearchTemplate.name}}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> searchBy${mySearchTemplate.name}(@PathVariable("${mySearchTemplate.name}") ${mySearchTemplate.type} ${mySearchTemplate.name}) {
        List<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${mySearchTemplate.name?cap_first}(${mySearchTemplate.name});
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }
            <#if mySearchTemplate.pageable=true>

    @RequestMapping(value = "/${mySearchTemplate.name}/{${mySearchTemplate.name}}/page", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    ResponseEntity<PagedResources<${modelClassName}>> searchBy${mySearchTemplate.name?cap_first}(@PathVariable("${mySearchTemplate.name}") ${mySearchTemplate.type} ${mySearchTemplate.name}, Pageable pageable, PagedResourcesAssembler assembler){
        Page<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${mySearchTemplate.name?cap_first}(${mySearchTemplate.name}, pageable);
        return new ResponseEntity<PagedResources<${modelClassName}>>(assembler.toResources(results), HttpStatus.OK);
    }
            </#if>
        </#if>
    </#list>
</#if>
}
