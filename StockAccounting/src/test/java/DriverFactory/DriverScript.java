package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Commonfuntionlibrary.FunctionalLibrary;
import Utilities.Excelfileutil;

public class DriverScript
{
    WebDriver driver;
    ExtentReports report;
    ExtentTest logger;
    
	public  void startTest() throws Throwable
	{
		Excelfileutil excel =new Excelfileutil();
		
		for (int i = 1; i <= excel.rowCount("MasterTestCases"); i++) 
		{
			String moduleStatus="";
		 if(excel.getData("MasterTestCases",i,2).equalsIgnoreCase("Y"))
				 {
			 String TCModule = excel.getData("MasterTestCases", i, 1);
			 report = new ExtentReports("C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\Reports\\"+TCModule+".html"+FunctionalLibrary.generateDate());
			 logger=report.startTest(TCModule);
		     int rowcount= excel.rowCount(TCModule);
					
					for (int j = 1; j <= rowcount ;j++) 
					 {
						String Description =excel.getData(TCModule, j, 0);
						String Object_Type =excel.getData(TCModule, j, 1);
						String Locator_Type =excel.getData(TCModule, j, 2);
						String Locator_Value =excel.getData(TCModule, j, 3);
						String Test_Data =excel.getData(TCModule, j, 4);
						try{
						
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionalLibrary.startBrowser(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("openApplication"))
						{
							FunctionalLibrary.openApplication(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionalLibrary.clickAction(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionalLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionalLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionalLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("titleValidation"))
						{
							FunctionalLibrary.titleValidation(driver, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("captureData")) 
						{
							FunctionalLibrary.captureData(driver, Locator_Type, Locator_Value);	
						logger.log(LogStatus.INFO,Description);
						}
						if (Object_Type.equalsIgnoreCase("tableValidation"))
						{
							FunctionalLibrary.tableValidation(driver, Test_Data);	
						logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("mouseClickTest"))
						{
							FunctionalLibrary.mouseClickTest(driver);	
							logger.log(LogStatus.INFO,Description);
						}
						if(Object_Type.equalsIgnoreCase("tableValidation1"))
						{
							FunctionalLibrary.tableValidation1(driver, Test_Data);	
							logger.log(LogStatus.INFO,Description);
						}
						
						
						
				excel.setData(TCModule, j, 5, "pass");
			moduleStatus="true";
				logger.log(LogStatus.PASS,Description);
					}catch(Exception e)
					{
						excel.setData(TCModule, j, 5, "fail");
						moduleStatus="false";
						logger.log(LogStatus.FAIL,Description);
			File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
   FileUtils.copyFile(srcFile, new File("C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\Screenshots\\"+Description+FunctionalLibrary.generateDate()+".png"));
							break;
						}
					}
					 if (moduleStatus.equalsIgnoreCase("true")) 
					 {
						excel.setData("MasterTestCases", i, 3, "pass");
					}else
					 if (moduleStatus.equalsIgnoreCase("false")) 
					 {
						excel.setData("MasterTestCases", i, 3, "fail");
				 }
					 report.endTest(logger);
					 report.flush();
		}
		 else
		 {
			 excel.setData("MasterTestCases", i, 3, "Not executed");
		 }
	}

}
}
