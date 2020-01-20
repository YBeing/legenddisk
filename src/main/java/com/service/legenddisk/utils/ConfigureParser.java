package com.service.legenddisk.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件Configure.properties中的值
* @ClassName: ConfigureParser 
* @Description: 
* @author hjz
* @date 2015年11月1日 上午10:49:37 
*
 */
public class ConfigureParser {
	private static Properties properties;


	static {
		if (properties == null) {
			Resource res  =new ClassPathResource("config.properties");
			EncodedResource encRes = new EncodedResource(res,"utf-8");

			properties = new Properties();
			try {
				properties.load(encRes.getReader());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//静态工厂方法   
    public static Properties getInstance() {  

        return properties;  
    } 

    /**
     * 根据名称获取值
    * @Description: 
    * @param @param propertname
    * @param @return    
    * @return String   
    * @throws 
    * @author hjz 
    * @date 2015年11月1日 上午10:53:24
     */
    public static String getPropert(String propertname) {
    	return getPropert(propertname, null);
	}
    /**
     * 根据名称获取值
    * @Description: 
    * @param @param propertname
    * @param @param defalutvalue   默认值
    * @param @return    
    * @return String   
    * @throws 
    * @author hjz 
    * @date 2015年11月1日 上午10:54:30
     */
    public static String getPropert(String propertname,String defalutvalue) {
		String value = getInstance().getProperty(propertname);
		if(value==null){
		  return defalutvalue;	
		}else{
		  return value.trim();
		}
	}

}
