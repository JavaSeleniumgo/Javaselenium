package Commonfuntionlibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertiFileUtil;



public class FunctionalLibrary 
{
	public static WebDriver startBrowser(WebDriver driver) throws Exception, Throwable 
	{
		if(PropertiFileUtil.getValueForkey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver= new FirefoxDriver();
			
		}else
			if(PropertiFileUtil.getValueForkey("Browser").equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver","C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\ExecutableFiles\\chromedriver.exechromedriver.exe");
				driver= new ChromeDriver();
			}else
				if(PropertiFileUtil.getValueForkey("Browser").equalsIgnoreCase("IE"))
				{
					System.setProperty("webdriver.IE.driver","C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\ExecutableFiles\\IEDriverServer.exe");
					driver= new InternetExplorerDriver();
				}
		return driver;
	}
	       
	public static void openApplication(WebDriver driver) throws Exception, Throwable
	{
		driver.manage().window().maximize();
		driver.get(PropertiFileUtil.getValueForkey("URL"));
		
	}
	public static void  clickAction(WebDriver driver,String locatorType,String locatorvalue)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).click();
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorvalue)).click();
			}else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorvalue)).click();
				}
	}
		public static void typeAction(WebDriver driver,String locatorType,String locatorvalue,String data)
		{
			if(locatorType.equalsIgnoreCase("id"))
			{
				driver.findElement(By.id(locatorvalue)).clear();
				driver.findElement(By.id(locatorvalue)).sendKeys(data);
			}else
				if(locatorType.equalsIgnoreCase("name"))
				{
					driver.findElement(By.name(locatorvalue)).clear();
					driver.findElement(By.name(locatorvalue)).sendKeys(data);
				}else
					if(locatorType.equalsIgnoreCase("xpath"))
					{
						driver.findElement(By.xpath(locatorvalue)).clear();
						driver.findElement(By.xpath(locatorvalue)).sendKeys(data);
					}
		}
		public static void closeBrowser(WebDriver driver)
		 {
			 driver.close();
		 }
		public static void waitForElement(WebDriver driver,String locatorType,String locatorvalue,String waittime)
		{
			WebDriverWait myWait=new WebDriverWait(driver,Integer.parseInt(waittime));
			if(locatorType.equalsIgnoreCase("id"))
			{
				myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
			}else
				if(locatorType.equalsIgnoreCase("name"))
				{
					myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
				}else
					if(locatorType.equalsIgnoreCase("xpath"))
					{
						myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
					}
		   }
		public static void titleValidation(WebDriver driver,String exp_data)
		{
			String act_data= driver.getTitle();
			if(exp_data.equalsIgnoreCase(act_data))
			{
				System.out.println("title matched");
		     }else
		     {
		    	 System.out.println("title mismatched");
		     }
		}
		
		public static String generateDate()
		{	
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		return sdf.format(d);
		}
		public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable
		{
			String data="";
			if (locatortype.equalsIgnoreCase("id"))
			{
				data=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			}else
				if (locatortype.equalsIgnoreCase("xpath"))
				{
				
				data=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
				}
			FileWriter fw = new FileWriter("C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\CaptureData\\Data.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.flush();
			bw.close();
		}
			
	
	public static void tableValidation(WebDriver driver,String column) throws Throwable
		{
			FileReader fr = new FileReader("C:\\Users\\govardhan.c\\govardhan\\StockAccounting\\CaptureData\\Data.txt");
			BufferedReader br = new BufferedReader(fr);
			String exp_data = br.readLine();
			
			int column1 = Integer.parseInt(column);
			if (driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box"))).isDisplayed()) 
			{
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box"))).clear();
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box"))).sendKeys(exp_data);
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.btn"))).click();
			}else
			{
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.panel"))).click();
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box"))).clear();
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box"))).sendKeys(exp_data);
				driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.btn"))).click();
				//driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
			Thread.sleep(2000);
			}
			
			WebElement table=driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("webtable.path")));
			List<WebElement> rows=table.findElements(By.tagName("tr"));
			
			for (int i=1; i<=rows.size(); i++)
			{
				String act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column1+"]/div/span/span")).getText();
				System.out.println(act_data);
				Assert.assertEquals(act_data, exp_data);
				break;
			}
		
		}
	public 	static void mouseClickTest(WebDriver driver) 
	{
		WebElement stockItem=driver.findElement(By.xpath("//*[@id='mi_a_stock_items']/a"));
		WebElement stockCategory=driver.findElement(By.xpath("//*[@id='mi_a_stock_categories']/a"));
		Actions mouse=new Actions(driver);
		
		mouse.moveToElement(stockItem).build().perform();
		mouse.moveToElement(stockCategory).click().build().perform();
	}
	
	public static void tableValidation1(WebDriver driver,String exp_data) throws Throwable
	{
		
		if (driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box1"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box1"))).clear();
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box1"))).sendKeys(exp_data);
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.btn1"))).click();
		}else
		{
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.panel1"))).click();
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box1"))).clear();
			driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("search.box1"))).sendKeys(exp_data);
			//driver.findElement(By.xpath(PropertiFileUtil.getValueForKey("search.btn1"))).click();
			driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
		Thread.sleep(2000);
		}
		
		WebElement table=driver.findElement(By.xpath(PropertiFileUtil.getValueForkey("webtable.path")));
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		
		for (int i=1; i<=rows.size();i++)
		{
			String act_data=driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			System.out.println(act_data);
			Assert.assertEquals(act_data, exp_data);
			break;
		}
	
	}	
}

	
	

    /*WebDriver driver;
	String res;
	
	public String launchapp(String url)
	{
		//driver=new ChromeDriver();
		driver=new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();	
		if(driver.findElement(By.id("btnsubmit")).isDisplayed())
		{
			res="pass";
		}else
		{
			res="fail";
		}
		return res;
	}
	
	public String login(String username, String password) throws Throwable
	{
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
	    driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(2000);
	    driver.findElement(By.id("btnsubmit")).click();
	    if(driver.findElement(By.id("logout")).isDisplayed())
		{
			res="pass";
		}else
		{
			res="fail";
		}
		return res;
	}
	
	public String supplier() throws Throwable
	{
		driver.findElement(By.xpath("//*[@id='mi_a_suppliers']/a")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/div[1]/div[1]/div[1]/div/a/span")).click();
		Thread.sleep(5000);
		String exp_data=driver.findElement(By.xpath("//*[@id='x_Supplier_Number']")).getAttribute("value");
		driver.findElement(By.id("x_Supplier_Name")).sendKeys("iphone");
		Thread.sleep(3000);
		driver.findElement(By.id("x_Address")).sendKeys("vasanth nagar");
		driver.findElement(By.id("x_City")).sendKeys("hyd");
		driver.findElement(By.id("x_Country")).sendKeys("india");
		driver.findElement(By.id("x_Contact_Person")).sendKeys("ravi");
		driver.findElement(By.id("x_Phone_Number")).sendKeys("8456127812");
		driver.findElement(By.id("x__Email")).sendKeys("gfdghf@gmail.com");
		driver.findElement(By.id("x_Mobile_Number")).sendKeys("8456127816");
		driver.findElement(By.id("x_Notes")).sendKeys("hvcjhewcwejh");
		driver.findElement(By.xpath("//*[@id='btnAction']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='OK!']")).click();
		driver.findElement(By.xpath("//*[text()='OK']")).click();
		
		driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[2]/div[2]/div/button")).click();
		driver.findElement(By.xpath("//*[@id='psearch']")).sendKeys(exp_data);
		driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
		
		if(driver.findElement(By.id("logout")).isDisplayed())
		{
			res="pass";
		}else
		{
			res="fail";
		}
		return res;
	}
		
	public String logout() throws Throwable 
	{
		driver.findElement(By.id("logout")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='logout']"));
		if(driver.findElement(By.id("btnsubmit")).isDisplayed())
		{
			res="pass";
		}else
		{
			res="fail";
		}
		return res;
	}
	
	public void close()
	 {
		 driver.close();
	 }
	
	public static void main(String[] args) throws Throwable 
	{
		Launchapplication exe=new Launchapplication();
		System.out.println(exe.launchapp("http://webapp.qedge.com/dashboard.php"));
		System.out.println(exe.login("admin", "master"));
		System.out.println(exe.supplier());
		//System.out.println(exe.logout());
		// exe.close();
	}*/


	


