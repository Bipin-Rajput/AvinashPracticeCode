package pack.test.cases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.*;

import StepsBase.StepBase;
import excel.utils.ExcelReadPack;
import specifics.SpecificType;

public class Task1 extends StepBase
{
	String[][] testData;
	
	@Test
	@Parameters({"url","browser"})
	public void TC020_Phoenix_Getting_All_Cars_Data_Test(String url,String browser) throws Exception
	{
		
		TestCaseName("TC020_PLC","Getting All Car Required Details","Sanity");
		
		Step("Lanching The Browser","LanchBrowser","YES",browser,url);
		
		keyword("Getting All Cars Data From All The Pages", "keyword","getCarsData"); 
		
	}
	
	@AfterMethod
	public void storeData() throws InterruptedException
	{
		new StepBase().log._INFO("Creating The Data Report!....");
		
		Thread.sleep(2000);
		
		new StepBase().log._INFO("Processing....");
		
		String dateMap = new SpecificType().DateSet();
		
		try {
			 new ExcelReadPack().createNewSheetInBook(SpecificType.Lables, SpecificType.carsData,"CarsDataSheet","AllCarsData"+dateMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new StepBase().log._INFO("Report Is Generated at "+System.getProperty("user.dir")+"/DataSheet/AllCarsData"+dateMap+".xlsx");
	}

}
