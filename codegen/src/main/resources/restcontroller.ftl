package ${controllerPackageName};

import ${modelPackageName};
import ${repositoryPackageName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

/**
* Auto-gen class
*/
@RestController
@RequestMapping(value = "${urlPathName}")
public class ${controllerClassName}{

    @Autowired
    private ${repositoryClassName} ${repositoryInstanceName};

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName(){
        return ${repositoryClassName}.class.getSimpleName();
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<${modelClassName}> create(@RequestBody @Valid final ${modelClassName} ${modelInstanceName}){
        ${modelClassName} result = ${repositoryInstanceName}.save(${modelInstanceName});
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<${modelClassName}> update(@RequestBody @Valid final ${modelClassName} ${modelInstanceName}){
        ${modelClassName} result = ${repositoryInstanceName}.save(${modelInstanceName});
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public void delete(@PathVariable("id") String id) {
        ${repositoryInstanceName}.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<${modelClassName}> findById(@PathVariable("id") String id) {
        ${modelClassName} result = ${repositoryInstanceName}.findOne(id);
        return new ResponseEntity<${modelClassName}>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<List<${modelClassName}>> findAll() {
        List<${modelClassName}> results = ${repositoryInstanceName}.findAll();
        return new ResponseEntity<List<${modelClassName}>>(results, HttpStatus.OK);
    }

<#if methods??>
    <#list methods as method>
    @RequestMapping(value = "/${method.name}/{${method.name}}", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public List<${modelClassName}> ${method.method}(@PathVariable("${method.name}") ${method.type} ${method.name}) {
        return ${repositoryInstanceName}.${method.method}(${method.name});
    }
    </#list>
</#if>
}
