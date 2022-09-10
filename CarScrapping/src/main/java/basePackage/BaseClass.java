package basePackage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;


public class BaseClass 
{

	static WebDriver driver;
	public static WebDriverWait wait;	
	
	@Test
	public static void CarScrapping() throws Exception {

		 
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "\\src\\test\\java\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://losangeles.craigslist.org/d/cars-trucks/search/cta?auto_title_status=1&srchType=T");
		
		String totalPages = driver.findElement(By.xpath("//span[@class=\"totalcount\"]")).getText();
		System.out.println(totalPages);
		int tps = Integer.parseInt(totalPages);
		
		for (int i=0; i<tps; i++) {
			
			List<WebElement> allProduct = driver.findElements(By.xpath("//ul[@class=\"rows\"][@id=\"search-results\"]/li/a"));
			int totalProduct = allProduct.size();
			System.out.println(totalProduct);
			
			for (int j=1; j<=totalProduct; j++) {
			
				String clicklnk = Keys.chord(Keys.CONTROL, Keys.ENTER);
				driver.findElement(By.xpath("//ul[@class=\"rows\"][@id=\"search-results\"]//li["+j+"]/a")).sendKeys(clicklnk);

			    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			    driver.switchTo().window(tabs.get(1)); //switches to new tab
			    
			    // CarName
				String CarName = driver.findElement(By.xpath("//*[@id=\"titletextonly\"]")).getText();
				System.out.println(CarName);
				
				//CarPrice
				String CarPrice = driver.findElement(By.xpath("/html/body/section/section/h1/span/span[2]")).getText();
				System.out.println(CarPrice);
				Thread.sleep(5000);
				
				String[] productDetails = {CarName, CarPrice};
				
/*				WriteExcel objExcelFile = new WriteExcel();

				objExcelFile.writeExcel(System.getProperty("user.dir")+ "\\src\\test\\java", "ExportExcel.xlsx",
						"CarScrapping", productDetails);
*/						
				driver.close();
				
			    driver.switchTo().window(tabs.get(0)); // switch back to main screen 
			   
			}	
			
			
			//next button click
			driver.findElement(By.xpath("//a[@class=\"button next\"][@title=\"next page\"]")).click();
		}
		
		
		driver.close();
	}
	
}
