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
        ${modelClassName} result = ${repositoryClassName?uncap_first}.save(${modelClassName?uncap_first});
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
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
    <#list mySearchTemplates as method>
        <#if method.referenceName??>

    @RequestMapping(value = "/${method.referenceName}/${method.name}/{${method.name}}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> searchBy${method.referenceName?cap_first}${method.name?cap_first}(@PathVariable("${method.name}") ${method.type} ${method.name}) {
        List<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${method.referenceName?cap_first}${method.name?cap_first}(${method.name});
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }
        <#else>

    @RequestMapping(value = "/${method.name}/{${method.name}}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> searchBy${method.name}(@PathVariable("${method.name}") ${method.type} ${method.name}) {
        List<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${method.name}(${method.name});
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }
            <#if method.pageable=true>

    @RequestMapping(value = "/${method.name}/{${method.name}}/page", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    ResponseEntity<PagedResources<${modelClassName}>> searchBy${method.name?cap_first}(@PathVariable("${method.name}") ${method.type} ${method.name}, Pageable pageable, PagedResourcesAssembler assembler){
        Page<${modelClassName}> results = ${repositoryClassName?uncap_first}.findBy${method.name?cap_first}(${method.name}, pageable);
        return new ResponseEntity<PagedResources<${modelClassName}>>(assembler.toResources(results), HttpStatus.OK);
    }
            </#if>
        </#if>
    </#list>
</#if>
}
