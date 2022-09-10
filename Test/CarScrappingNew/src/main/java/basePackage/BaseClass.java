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

public class BaseClass {

	static WebDriver driver;
	public static WebDriverWait wait;

	@Test
	public static void CarScrapping() throws Exception {
		WriteExcel obj = new WriteExcel();
		String[] productDetail;
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\java\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://losangeles.craigslist.org/d/cars-trucks/search/cta?auto_title_status=1&srchType=T");

		String totalPages = driver.findElement(By.xpath("//span[@class=\"totalcount\"]")).getText();
		int tps = Integer.parseInt(totalPages);

		for (int i = 1; i <= 2; i++) {

			List<WebElement> allProduct = driver
					.findElements(By.xpath("//ul[@class=\"rows\"][@id=\"search-results\"]/li/a"));
			int totalProduct = allProduct.size();
			
//			String[][] productDetail2D = new String[2][2];
			for (int j = 1; j <= 2; j++) {

				String clicklnk = Keys.chord(Keys.CONTROL, Keys.ENTER);
				driver.findElement(By.xpath("//ul[@class=\"rows\"][@id=\"search-results\"]//li[" + j + "]/a"))
						.sendKeys(clicklnk);

				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

				driver.switchTo().window(tabs.get(1)); // switches to new tab

				String VIN1 = null;
				String odometer1 = null;

				String VIN = null;
				String odometer = null;
				
				
				List<WebElement> attributeGroup = driver
						.findElements(By.xpath("/html/body/section/section/section/div[1]/p[2]/span"));

				int totalAttributes = attributeGroup.size();
				String[][] productDetail2D = new String[2][2];

				for (int k = 1; k <= totalAttributes; k++) {

					String[] at = new String[totalAttributes];
					String attribut = driver
							.findElement(By.xpath("/html/body/section/section/section/div[1]/p[2]/span[" + k + "]"))
							.getText();

					at[k - 1] = attribut;

					if (at[k - 1].contains("VIN")) {

						VIN1 = at[k - 1];

						String[] VIN2 = VIN1.split(":", 2);
						VIN = VIN2[1].trim();

					} else if (at[k - 1].contains("odometer")) {

						odometer1 = at[k - 1];

						String[] odometer2 = odometer1.split(":", 2);
						odometer = odometer2[1].trim();

					} else {

					}

				}
				String[] productDetail = { VIN, odometer };

				System.out.println("VIN numnber = " + productDetail[0]);

				System.out.println("ODOmeter number = " + productDetail[1]);
				
				driver.close();

				driver.switchTo().window(tabs.get(0));

			}
			
//			String[][ pd = new String pd[][];
			
			WriteExcel objExcelFile = new WriteExcel();

			objExcelFile.writeExcel(System.getProperty("user.dir")+ "\\src\\test\\java", "ExportExcel.xlsx",
					productDetail);

			// next button click
			driver.findElement(By.xpath("//a[@class=\"button next\"][@title=\"next page\"]")).click();
			
		}

		driver.close();
	}

}
