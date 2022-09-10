package Generics;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import report.logs.Logs;
import specifics.SpecificType;

public class Generics {
	public static WebDriver driver;
	public static WebElement element;
	public static Logs log;
	public static int PAGE_LOAD_TIME_OUT = 240;
	public static int IMPLICITY_TIME_OUT = 10;
	public static SoftAssert asert;
	public static int i;
	public static String windowHandle;
	public static ArrayList<String> links;

	public void LanchBrowser(String browserName, String URL) throws IOException, AWTException {
		asert = new SoftAssert();

		log = new Logs();

		switch (browserName) {
		case "Chrome":
			log._INFO(
					"*************************************************************************************************************************************************************");
			log._INFO(
					"**********************************************************INITIALIZING CHROME BROWSER************************************************************************");
			log._INFO(
					"*************************************************************************************************************************************************************");
			WebDriverManager.chromedriver().setup();
			ChromeOptions co = new ChromeOptions();
			co.addArguments("start-maximized");
			// co.addArguments("headless");
			driver = new ChromeDriver(co);
			break;
		case "Firefox":
			log._INFO(
					"*************************************************************************************************************************************************************");
			log._INFO(
					"**********************************************************INITIALIZING FIREFOX BROWSER************************************************************************");
			log._INFO(
					"*************************************************************************************************************************************************************");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		default:
			log._ERROR("Browser Is In Out Of Scope");
		}
		if (driver != null) {
			ZoomOut();

			log._INFO("Browser successfully Started..");

			log._INFO("Deleting All The Coockies");
			driver.manage().deleteAllCookies();

			log._INFO("Setting PageLoadTimeOut To " + PAGE_LOAD_TIME_OUT);
			driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME_OUT, TimeUnit.SECONDS);

			log._INFO("Setting ImplicityTimeOut To " + IMPLICITY_TIME_OUT);
			driver.manage().timeouts().implicitlyWait(IMPLICITY_TIME_OUT, TimeUnit.SECONDS);

			long time;
			time = System.currentTimeMillis();
			log._INFO("Navigating To " + URL);
			driver.get(URL);
			log._INFO("Navigated To " + URL + " Successfully");
			log._INFO("Time Taken To Load The URL[" + URL + "] Is: " + (System.currentTimeMillis() - time) / 1000
					+ "Seconds");
		}
	}

	public void CloseBrowser() {
		log._INFO(
				"*************************************************************************************************************************************************************");
		log._INFO(
				"**********************************************************-C-L-O-S-E-****************************************************************************************");
		log._INFO(
				"*************************************************************************************************************************************************************");
		log._INFO(
				"**********************************************************CLOSING BROWSER************************************************************************************");
		log._INFO(
				"*************************************************************************************************************************************************************");
		driver.quit();
		log._INFO(
				"*****************************************************BROWSER CLOSED SUCCESSFULLY*****************************************************************************");
		log._INFO(
				"*************************************************************************************************************************************************************");
	}

	public WebElement UIElement(String Location) {
		/*
		 * driver.findElement(new ByAll()); driver.findElement(new ByIdOrName(""));
		 * driver.findElement(new ByChained());
		 */
		return locatorSelection(Location);
	}

	public boolean VisibiltyOfElementLocated(WebElement element) {
		return element.isDisplayed();
	}

	public boolean ElementIsEnabled(WebElement element) {
		return element.isEnabled();
	}

	public void HighLightElement(WebElement element, WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='2px dashed Red';", element);
		Thread.sleep(412);
		js.executeScript("arguments[0].style.border='2px dashed Red';", element);
		js.executeScript("arguments[0].style.border='';", element);
	}

	public void HighLightElement1(WebElement element, WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px dashed lime;');", element);
		Thread.sleep(412);
		js.executeScript("arguments[0].setAttribute('style', '');", element);
	}

	public void ZoonIn() throws AWTException {
		driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
	}

	public void ZoomOut() {
		String zoomOut = "document.body.style.zoom='80%'";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(zoomOut);
	}

	public void input(String ElementName, String Location, String TestData) throws InterruptedException {

		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);

		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {

			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {

				HighLightElement(UIElement(Location), driver);

				/*
				 * log._INFO("Clearing The element ["+ElementName+"] at Location ="+Location);
				 * UIElement(Location).clear(); JavascriptExecutor
				 * js=((JavascriptExecutor)driver);
				 * js.executeScript("arguments[0].value='';",UIElement(Location));
				 */

				log._INFO("Entering The Data [" + TestData + "] In The Element [" + ElementName + "] at Location ="
						+ Location);
				// UIElement(Location).click();
				UIElement(Location).sendKeys(TestData);
				// js.executeScript("arguments[0].value=arguments[1];",UIElement(Location),TestData);

				log._INFO("Succussfully Data Is Entered For An Element [" + ElementName + "] at Location =" + Location
						+ " With Test Data:" + TestData);
			} else {

				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}
	}

	public void actionsinput(String ElementName, String Location, String TestData) throws InterruptedException {
		new Actions(driver).click(UIElement(Location)).sendKeys(UIElement(Location), TestData).build().perform();
		;
	}

	public void click(String ElementName, String Location) throws InterruptedException {
		HighLightElement(UIElement(Location), driver);

		log._INFO("Clicking An Element [" + ElementName + "] at Location =" + Location);
		UIElement(Location).click();
		log._INFO("Succussfully Element Is Clicked [" + ElementName + "] at Location =" + Location);

	}

	public void JSclick(String ElementName, String Location) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				log._INFO("Clicking An Element [" + ElementName + "] at Location =" + Location);
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("arguments[0].click();", UIElement(Location));
				log._INFO("Succussfully Element Is Clicked [" + ElementName + "] at Location =" + Location);
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}

	}

	public void JSsetData(String ElementName, String Location, String TestData) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('text()',arguments[1]);", UIElement(Location), TestData);
	}

	public void JSinput(String ElementName, String Location, String TestData) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				log._INFO("Clearing The element [" + ElementName + "] at Location =" + Location);
				UIElement(Location).clear();
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("arguments[0].value='';", UIElement(Location));
				js.executeScript("arguments[0].value=" + TestData, UIElement(Location));
				log._INFO("Succussfully Element Is Clicked [" + ElementName + "] at Location =" + Location);
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}

	}

	public void doubleClick(String ElementName, String Location) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				log._INFO("Double Clicking An Element [" + ElementName + "] at Location =" + Location);
				new Actions(driver).doubleClick(UIElement(Location)).build().perform();
				log._INFO("Succussfully Element Is Double Clicked [" + ElementName + "] at Location =" + Location);
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}

	}

	public void moveToTheElementThenClickInVisibleElement(String ElementName, String Location)
			throws InterruptedException {

		log._INFO("Moving To The Element And clicking The Element [" + ElementName + "] at Location =" + Location);
		new Actions(driver).moveToElement(UIElement(Location)).click().build().perform();
		log._INFO("Succussfully Moved To The Element [" + ElementName + "] at Location =" + Location);

	}

	public void Sleep(int sec) throws InterruptedException {

		Thread.sleep(sec);
		log._INFO("Spent " + (sec / 1000) + " Seconds");
	}

	public void verify(String ElementName, String Location) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}

	}

	public List<WebElement> elementCount(String xpath, String name) {
		log._INFO("Web Elements Found:" + driver.findElements(By.xpath(xpath)).size() + name);
		return driver.findElements(By.xpath(xpath));
	}

	public void specificWait(String xpath, int timeInSeconds) {
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public void select(String ElementName, String Location, String data) throws InterruptedException {
		/*
		 * log._INFO("Verifying The Element ["+ElementName+"] at Location ="+Location);
		 * if(VisibiltyOfElementLocated(U IElement(Location)) == true) {
		 * log._INFO("Element ["+ElementName+"] Is Found"+" at Location ="+Location);
		 * if(ElementIsEnabled(UIElement(Location))== true) {
		 */
		HighLightElement(UIElement(Location), driver);

		log._INFO("Selecting An Element [" + ElementName + "] with Option " + data + " at Location =" + Location);
		new Select(UIElement(Location)).selectByVisibleText(data);
		log._INFO("Succussfully Element Is Selected [" + ElementName + "] with Option " + data + " at Location ="
				+ Location);
		/*
		 * } else {
		 * log._ERROR("Element ["+ElementName+"] Is In Disable Mode at Location ="
		 * +Location); } } else {
		 * log._ERROR("Element ["+ElementName+"] Is Not Found at Location ="+Location);
		 * }
		 */

	}

	public void dropAndDrop(String ElementName, String Location, String Location1) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				log._INFO("Dragging An Element [" + ElementName + "] From " + Location + " To =" + Location1);
				new Actions(driver).dragAndDrop(UIElement(Location), UIElement(Location1)).build().perform();
				log._INFO(
						"Succussfully Element Is Dragged [" + ElementName + "] From " + Location + " To =" + Location1);
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}
	}

	public void dropAndDropByOffeset(String ElementName, String Location, int xOffset, int yOffset)
			throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				new Actions(driver).dragAndDropBy(UIElement(Location), xOffset * 5, yOffset).build().perform();
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}
	}

	public void DragEsign(String ElementName, String Location, int xOffset, int yOffset) throws InterruptedException {
		log._INFO("Verifying The Element [" + ElementName + "] at Location =" + Location);
		if (VisibiltyOfElementLocated(UIElement(Location)) == true) {
			log._INFO("Element [" + ElementName + "] Is Found" + " at Location =" + Location);
			if (ElementIsEnabled(UIElement(Location)) == true) {
				HighLightElement(UIElement(Location), driver);

				new Actions(driver).moveToElement(UIElement(Location), 1, -2).clickAndHold().moveByOffset(-50, -70)
						.moveByOffset(100, -100).release().build().perform();
				new Actions(driver).moveToElement(UIElement(Location), 20, 40).clickAndHold().moveByOffset(-50, -70)
						.moveByOffset(100, -100).release().build().perform();
			} else {
				log._ERROR("Element [" + ElementName + "] Is In Disable Mode at Location =" + Location);
			}
		} else {
			log._ERROR("Element [" + ElementName + "] Is Not Found at Location =" + Location);
		}
	}

	public boolean dataValidation(String Location, String data) {
		String response = UIElement(Location).getText();

		log._INFO("Validating The Data [" + data + "] Is Available At Location [" + Location + " ]");

		if (response.contains(data)) {
			log._INFO("Data [" + data + "] Matched...with [" + response + "] at Location [" + Location + "]");
			return true;
		} else {
			log._INFO("Data [" + data + "] did not Match...with [" + response + "] at Location [" + Location + "]");
			return false;
		}

	}

	public boolean dataValidation1(String Location, String data) {
		String response = UIElement(Location).getAttribute("value");

		log._INFO("Validating The Data [" + data + "] Is Available At Location [" + Location + " ]");

		if (response.contains(data)) {
			log._INFO("Data [" + data + "] Matched...with [" + response + "] at Location [" + Location + "]");
			return true;
		} else {
			log._INFO("Data [" + data + "] did not Match...with [" + response + "] at Location [" + Location + "]");
			return false;
		}

	}

	public void attachTheImage(String Location) throws InterruptedException, AWTException {
		StringSelection stringSelection = new StringSelection(System.getProperty("user.dir") + "\\" + Location);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot robot = new Robot();

		log._INFO("Inputting The Location[" + System.getProperty("user.dir") + "\\" + Location + "]In Window");
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Release key
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(5000);
		log._INFO("Clicking On Open in Window");
		robot.keyPress(KeyEvent.VK_ENTER);
		log._INFO("Image Is Selected Succussfully");
	}

	public void copyPaste(String TestData) throws AWTException, InterruptedException {
		StringSelection stringSelection = new StringSelection(TestData);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot robot = new Robot();

		log._INFO("Inputting The data");
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Release key
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public void softAssert(String Location, String data) throws IOException {

		String response = UIElement(Location).getText();

		log._INFO("Validating The Data [" + data + "] Is Available At Location [" + Location + " ]");

		// asert.assertEquals(response, data);
		if (response.contains(data)) {
			log._INFO("Data [" + data + "] Matched...with [" + response + "] at Location [" + Location + "]");
		} else {
			log._INFO("Data [" + data + "] did not Match...with [" + response + "] at Location [" + Location + "]");
			captureScreenShot();
		}
	}

	public void assertClose() {
		asert.assertAll();
	}

	public String captureScreenShot() throws IOException {
		String imageName = new SpecificType().DateSet();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(src, new File("Reports\\ScreenShots\\" + imageName + ".png"));

		String finaldestination = System.getProperty("user.dir") + "\\Reports\\ScreenShots\\" + imageName + ".png";

		byte[] bytes = FileUtils.readFileToByteArray(src);

		String encodedBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);

		return encodedBase64;
	}

	public String getData(String xpath) {
		return UIElement(xpath).getText();
	}

	public void elementToBeClickable(String xpath, int timeInSeconds) {
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public void alertValidateThenCancel(String alertData) {
		Alert alert = driver.switchTo().alert();

		alert.getText().contains(alertData);

		// alert.dismiss();

	}

	public WebElement locatorSelection(String locator) {
		WebElement element = null;

		if (locator.contains("//")) {
			element = driver.findElement(By.xpath(locator));
		} else {
			if (locator.contains("[")) {
				element = driver.findElement(By.cssSelector(locator));
			} else {
				String customized = "//*[@id='replace' or @name='replace' or @class='replace' or contains(text(),'replace')]";

				element = driver.findElement(By.xpath(customized.replace("replace", locator)));
			}
		}

		return element;
	}

	public void fluentWaitFor(final String location) {
		FluentWait time = new FluentWait<WebDriver>(driver);

		time.withTimeout(Duration.ofSeconds(20));

		time.pollingEvery(Duration.ofSeconds(2));

		time.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

		WebElement element = (WebElement) time.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver input) {

				return UIElement(location);
			}

		});

		new Actions(driver).sendKeys("//input", "Vivek");

	}

	public void scrollDown() throws AWTException, InterruptedException {
		Robot robo = new Robot();
		robo.keyPress(KeyEvent.VK_DOWN);

		robo.keyRelease(KeyEvent.VK_DOWN);
	}

	public void getURLS(String path) {
		List<WebElement> webObjects = driver.findElements(By.xpath(path));

		i = 0;

		links = new ArrayList<String>();

		for (i = 0; i < webObjects.size(); i++) {
			links.add(i, webObjects.get(i).getAttribute("href"));
		}
	}

	public void openNewTab() {
		driver.switchTo().newWindow(WindowType.TAB);
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	public void closeCurrentTab() {
		driver.close();
	}

	public void GetCurrentWindow() {
		windowHandle = driver.getWindowHandle();
	}

	public void switchBackToMainWindiow() {
		driver.switchTo().window(windowHandle);
	}

	public String getText(String elementName, String Path) {
		log._INFO("Getting the Data of an Object[" + elementName + "] at the Location =" + "[" + Path + "]");

		String data = UIElement(Path).getText();

		return data;

	}

	public Boolean ObjectVerify(String elementName, String Path) {
		log._INFO("Verifying an Object[" + elementName + "] at the Location =" + "[" + Path + "]");

		return UIElement(Path).isDisplayed();
	}

}
