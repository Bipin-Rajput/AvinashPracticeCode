package listeners;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import Generics.Generics;
import excel.utils.ExcelReadPack;
import report.logs.Logs;
import send.mail.ReportAttacher;
import specifics.SpecificType;
import xpath.hub.XpathHub;

public class ListenEvents implements ITestListener
{
	static Logs log;
	static Generics gen;
	static SpecificType spec;
	static XpathHub xpath;
	static ExcelReadPack ob;
	static ReportAttacher report;
	public static String Pass_Fail;

	@Override
	public void onStart(ITestContext context) 
	{
		log = new Logs();
		gen = new Generics();
		spec = new SpecificType();
		xpath = new XpathHub();
		ob = new ExcelReadPack();
		report = new ReportAttacher();

		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------HEY, HI! IAM STARTING EXECUTION-----------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");

		log.extentInitailizer(); // Extent Report Initializer
		
		log._INFO("Extent Report Initailized At The Location:"+System.getProperty("user.dir")+"\\"+"Reports\\TestReport.html");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		try
		{
			String workbooks[] = {"dataFile.xlsx","DataSheet\\AllCarsData.xlsx"}; // All Workbooks Initializations

			ob.OpenAWBooks(workbooks);

			log._INFO("All Workbooks are Initialized:");

			log._INFO("[");

			for(int i=0;i<workbooks.length;i++)
			{
				log._INFO("==>"+workbooks[i]);
			}

			log._INFO("]");
			log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");	

		}
		catch (IOException e1) 
		{

			e1.printStackTrace();
		}
		try
		{
			log._INFO("X-Path Book Is Initialized: WebUIMap.xlsx");


			log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");

			xpath.XpathHubStore("WebUIMap.xlsx","WebUIMaps"); // XPath Initializer

			log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");	

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}


	}
	
	@Override
	public void onTestStart(ITestResult result) 
	{
		
		/*try {
			gen.LanchBrowser("Chrome","https://www.facebook.com/");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{

		gen.CloseBrowser();
		
	}

	@Override
	public void onTestFailure(ITestResult result)
	{

		try 
		{
			String Location = gen.captureScreenShot();

			log._INFO("Adding The Error Snapshot To The Report...!");

			log.EXTENT_FAIL("Reason For Failure: ["+result.getThrowable()+"]",Location);
			
			log._INFO("Please Open The Report Available at "+System.getProperty("user.dir")+"\\"+"Reports\\TestReport.html"+ "to Check The Exception Occurred");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gen.CloseBrowser();

	}

	@Override
	public void onTestSkipped(ITestResult result) 
	{

		if(gen.driver!=null)
		{
			gen.CloseBrowser();
		}
	}
	@Override
	public void onFinish(ITestContext context) 
	{
		try 
		{
			ob.closeAllBooks();
			
			xpath.closeXpathBook();

			log._INFO("All Books Are Closed");

		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

		//log.CreateTestCase("Close_Intializations","Close All Test Related Files Intializations","All");

		//log.EXTENT_INFO("All Books Are Closed");

		log.storeInReport();
		
		log._INFO("Extent Report Generated and available In Reports");

		/*log._INFO("Attaching The Report to the mail");

		report.addReportToMail("Reports//TestReport.html","Automation Result.html"); // Location : Name of the report
		log._INFO("-------------------------------------------------------------------------------------------------");
		log._INFO("-------------------------------------------------------------------------------------------------");
		log._INFO(":::::::::::::::::::::::::::::::::::::::::::::::Sending The Mail::::::::::::::::::::::::::::::::::");
		log._INFO("-------------------------------------------------------------------------------------------------");
		
		String toUsers[]= {"gkatakam@sigmasolve.net"}; // To users

		String ccUsers[]= {"gchary21@gmail.com"}; // cc users

		try {
			report.sendReport(toUsers, ccUsers);
			log._INFO("Report has been sent successfully..");
			log._INFO("-------------------------------------------------------------------------------------------------");
			log._INFO("-------------------------------------------------------------------------------------------------");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			log._INFO("Report Sending Failed....");
			log._INFO("-------------------------------------------------------------------------------------------------");
			log._INFO("-------------------------------------------------------------------------------------------------");
			e.printStackTrace();

		}*/

		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------HEY, HI! IAM DONE WITH EXECUTION----------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

}
