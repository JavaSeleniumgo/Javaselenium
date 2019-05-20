package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiFileUtil 
{
        public static String getValueForkey(String key) throws Throwable, Exception 
        {
			Properties configProperties =new Properties();
			configProperties.load(new FileInputStream(new File("C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\PropertyFile\\Enviroment.properties")));
			return configProperties.getProperty(key);
			
        }
}
