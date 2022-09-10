package pack.test.cases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import StepsBase.StepBase;
import excel.utils.ExcelReadPack;
import specifics.SpecificType;

public class Task2 extends StepBase
{
	@Test
	@Parameters({"url","browser"})
	public void TC01_Carvana_Get_All_OfferDetails(String url,String browser) throws Exception
	{
		
		TestCaseName("TC01","Getting All Offer Details as per the VIN","Sanity");
		
		Step("Lanching The Browser","LanchBrowser","YES",browser,url);
		
		keyword("Getting All Offer Details As Per The VIN", "keyword","getOfferDetails"); 
		
	}
	
	@AfterMethod
	public void storeData() throws InterruptedException
	{
		new StepBase().log._INFO("Creating The Data Report!....");
		
		Thread.sleep(2000);
		
		new StepBase().log._INFO("Processing....");
		
		String dateMap = new SpecificType().DateSet();
		
		try {
			 new ExcelReadPack().createNewSheetInBook(SpecificType.Labels2, SpecificType.carsData2,"OfferValueDataSheet","CarOfferData"+dateMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new StepBase().log._INFO("Report Is Generated at "+System.getProperty("user.dir")+"/DataSheet/CarOfferData"+dateMap+".xlsx");
	}


}
