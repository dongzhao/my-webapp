package com.mysimplework.core.codegen;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dzhao on 22/02/2016.
 */
public class PojoConvertor {

    private PojoConvertor(){}

    public static String convertTo(String value, String type){
        if(boolean.class.getName().equals(type) || Boolean.class.getName().equals(type) ||
                int.class.getName().equals(type) || Integer.class.getName().equals(type) ||
                float.class.getName().equals(type) || Float.class.getName().equals(type) ||
                double.class.getName().equals(type) || Double.class.getName().equals(type) ||
                char.class.getName().equals(type) || Character.class.getName().equals(type) ){
            return value;
        }else if(String.class.getName().equals(type)){
            return "\"" + value + "\"";
        }else if(Date.class.getName().equals(type)){
            return "MyStringUtil.toDate(\"" + value + "\")";
        }else if(BigDecimal.class.getName().equals(type)){
            return "MyStringUtil.toDecimal(\"" + value + "\")";
        }

        throw new RuntimeException("Unsupported field type [" + type + "]");
    }
}
