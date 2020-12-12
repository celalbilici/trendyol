package steps;

import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    static Map<String,Object> data=new HashMap<>();
    static Object elementData;

    public void setData(String key,String value){
        this.data.put(key,removeQuotes(value));
        System.out.println("save the "+key+": "+value);
    }

    public String getData(String key){
       return (String) this.data.get(key);
    }


    protected void setData(Object object) {
        this.elementData=object;
    }

    public String getElement(String elementKey){
        String value=JsonPath.read(this.elementData,"$."+elementKey);
        if(value.contains("{the")){
            String key=value.split("\\{the")[1].split("}")[0];
            String keyValue=getData(key);
            value=value.replaceAll("\\{the"+key+"}",keyValue);
        }
        return removeQuotes(value);
    }

    public String removeQuotes(String text){
        return text.replace("\"\"","\"");
    }
}
