package specifics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

import Generics.Generics;
import StepsBase.StepBase;
import excel.utils.ExcelReadPack;
import excel.utils.StringClsUtil;
import report.logs.Logs;
import xpath.hub.XpathHub;



public class SpecificType extends StepBase
{
	String xpathConversion,testCh,data1[],data2[];

	static public String Lables[]={"S.NO","Description","Year","Make","Model","Trim",
			"Odometer/Mailage","Price","VIN",
			"Color","link to online posting","City","State","Phone Number"},carsData[][],carsData2[][],lonpost,Labels2[]= {"S.NO","VIN","Offer Value"};

	static int sno=0,dataSetter,counter=0;



	public String DateSet()
	{
		long mills = System.currentTimeMillis();
		Date date = new Date(mills);

		return new excel.utils.StringClsUtil().Convert_Customized_Format(date);
	}


	public void getCarDetails() throws Exception
	{
		int itemsPerPage,totalItimes, iterationsToRun;
		float dup;

		Step("selecting the owner","click","select_owner");

		Thread.sleep(5000);

		Step("clicking on title status","JSclick","select_title");

		Step("selecting title status","JSclick","title_status");

		new StepBase().log._INFO("Getting the Itemes Count Per Page....");

		testCh = new Generics().getText("pageItemsCount",new XpathHub().xpathGetter("pageItemsCount"));

		data1 = new StringClsUtil().SplitAnyData(testCh," - ");

		itemsPerPage=Integer.parseInt(data1[1]);

		new StepBase().log._INFO("Getting the Total Items Count....");

		testCh = new Generics().getText("totalItemsCount",new XpathHub().xpathGetter("totalItemsCount"));

		totalItimes =Integer.parseInt(testCh);

		dup = totalItimes/itemsPerPage;

		iterationsToRun = totalItimes/itemsPerPage;

		if(dup > iterationsToRun) iterationsToRun = iterationsToRun + 1;

		carsData = new String [totalItimes][20];

		for(int j=0;j<iterationsToRun;j++)
		{
			new StepBase().log._INFO("||||||||--------------------------------");
			new StepBase().log._INFO("||||||||Processing The Page: ["+(j+1)+"]");
			new StepBase().log._INFO("||||||||--------------------------------");

			new Logs().LoopIndex("Page"+(j+1), "Reading The Data In Page"+(j+1));

			Step("Getting All URLS","getURLS","getAllCarsLinks");

			Step("Getting Current Window Id","GetCurrentWindow");

			Step("Opening New Tab to process all the cars data one by one","openNewTab");

			ArrayList<String> allUrls = Generics.links;

			for(int i=0;i<allUrls.size();i++)
			{
				new Logs().InnerLoopIndex("Vehical"+(i+1),"Getting Vehical"+(i+1)+"Details");

				new StepBase().log._INFO("|::::Processing The Item: ["+(i+1)+"] at Page ["+(j+1)+"]");

				Step("Navigating to Cars Details page as per the url"+"["+allUrls.get(i)+"]","navigateTo","YES",allUrls.get(i));

				lonpost=allUrls.get(i);

				dataSetter = dataSetter + 1;

				if(j==0)
					carsData[i] = retrunCarData();
				else
				{
					carsData[dataSetter-1] = retrunCarData();
				}

				new Logs().EXTENT_INFO("Data Collected At Vehical"+(i+1)+":"+Arrays.toString(carsData[counter]));

				counter++;
			}

			Step("Closing The Current Tab","closeCurrentTab");

			Step("Swicthing Back to the main window to get another page car details..","switchBackToMainWindiow");

			if(j!=iterationsToRun-1) Step("Clicking On The Next >","click","nextLink");
		}

	}

	public String[] retrunCarData() throws IOException
	{
		sno++;

		String sample="Not Available";

		StringBuffer buff = new StringBuffer();

		String S_NO =Integer.toString(sno),Make = null,Year,Color,VIN = null,Odometer,Price,data,spilter[],model=null,
				trim,link2OnlineProcessing=lonpost,address,phoneNumber,vehicalDes = null,city="Phoenix",state="Arizona";

		//Description
		try
		{
			new Generics().ObjectVerify("postingBody",new XpathHub().xpathGetter("postingBody"));

			data= new Generics().getText("postingBody",new XpathHub().xpathGetter("postingBody"));
			
			vehicalDes = data;
			
		}
		catch (NoSuchElementException ob)
		{
			vehicalDes =sample;

			new StepBase().log._INFO("Data Not Found:For Object[postingBody]");
		}
		// YEAR & Make
		try
		{
			new Generics().ObjectVerify("vehicalDetails",new XpathHub().xpathGetter("vehicalDetails"));

			data= new Generics().getText("vehicalDetails",new XpathHub().xpathGetter("vehicalDetails"));

			if(!(data.isEmpty()))
			{
				spilter = new StringClsUtil().SplitAnyData(data," ");

				try
				{
					Integer.parseInt(spilter[0]);
					Year = spilter[0];
				}

				catch(NumberFormatException e)
				{
					Year = sample;

					Make = spilter[0];
				}

				if(Make==null) Make = spilter[1];

			}
			else
			{
				Year = sample;

				Make = sample;
			}
		}
		catch (NoSuchElementException ob)
		{
			new StepBase().log._INFO("Data Not Found:For Object[vehicalDetails]");

			Year = sample;

			Make = sample;
		}

		// MODEL
		try
		{
			new Generics().ObjectVerify("carModel",new XpathHub().xpathGetter("carModel"));

			data= new Generics().getText("carModel",new XpathHub().xpathGetter("carModel"));

			if(!(data.isEmpty())) model = data; else model =sample;
		}
		catch (NoSuchElementException ob)
		{
			model =sample;

			new StepBase().log._INFO("Data Not Found:For Object[carModel]");
		}

		//TRIM
		try
		{
			new Generics().ObjectVerify("carTrim",new XpathHub().xpathGetter("carTrim"));

			data= new Generics().getText("carTrim",new XpathHub().xpathGetter("carTrim"));

			if(!(data.isEmpty())) trim = data; else trim =sample;
		}
		catch (NoSuchElementException ob)
		{
			trim =sample;

			new StepBase().log._INFO("Data Not Found:For Object[carTrim]");
		}
		//COLOR
		try
		{
			new Generics().ObjectVerify("vehicalColor",new XpathHub().xpathGetter("vehicalColor"));

			data= new Generics().getText("vehicalColor",new XpathHub().xpathGetter("vehicalColor"));

			if(!(data.isEmpty())) Color = data; else Color =sample;
		}
		catch (NoSuchElementException ob)
		{
			Color =sample;

			new StepBase().log._INFO("Data Not Found:For Object[vehicalColor]");
		}

		//VIN
		try
		{
			new Generics().ObjectVerify("Vin",new XpathHub().xpathGetter("Vin"));

			data= new Generics().getText("Vin",new XpathHub().xpathGetter("Vin"));

			if(!(data.isEmpty())) VIN = data; else VIN = sample;
		}
		catch (NoSuchElementException ob)
		{

			new StepBase().log._INFO("Data Not Found:For Object[Vin]");

			VIN = sample;

			/*try
			{
				 new Generics().ObjectVerify("Vin1",new XpathHub().xpathGetter("Vin1"));

				data= new Generics().getText("Vin1",new XpathHub().xpathGetter("Vin1"));

				if(!(data.isEmpty())) VIN = data; else VIN = sample;
			}
			catch (NoSuchElementException ob1)
			{
				VIN = sample;

				new StepBase().log._INFO("Data Not Found:For Object[Vin1]");

			}*/

		}
		//ODOMETER/MAILAGE
		try
		{
			new Generics().ObjectVerify("odometer",new XpathHub().xpathGetter("odometer"));

			data= new Generics().getText("odometer",new XpathHub().xpathGetter("odometer"));

			if(!(data.isEmpty())) Odometer = data; else Odometer = sample;
		}
		catch (NoSuchElementException ob)
		{
			Odometer = sample;

			new StepBase().log._INFO("Data Not Found:For Object[odometer]");
		}
		//PRICE
		try
		{
			new Generics().ObjectVerify("price",new XpathHub().xpathGetter("price"));

			data= new Generics().getText("price",new XpathHub().xpathGetter("price"));

			if(!(data.isEmpty())) Price = data; else Price = sample;

		}
		catch (NoSuchElementException ob)
		{
			Price = sample;

			new StepBase().log._INFO("Data Not Found:For Object[price]");
		}
		//ADDRESS
		try
		{
			new Generics().ObjectVerify("mapAddress",new XpathHub().xpathGetter("mapAddress"));

			data= new Generics().getText("mapAddress",new XpathHub().xpathGetter("mapAddress"));

			if(!(data.isEmpty())) address = data; else address = sample;

		}
		catch (NoSuchElementException ob)
		{
			address = sample;

			new StepBase().log._INFO("Data Not Found:For Object[mapAddress]");
		}
		//PHONE NUMBER
		try
		{
			new Generics().ObjectVerify("phoneNumber",new XpathHub().xpathGetter("phoneNumber"));

			data= new Generics().getText("phoneNumber",new XpathHub().xpathGetter("phoneNumber"));

			if(!(data.isEmpty())) phoneNumber = data; else phoneNumber = sample;

		}
		catch (NoSuchElementException ob)
		{
			phoneNumber = sample;

			new StepBase().log._INFO("Data Not Found:For Object[phoneNumber]");
		}

		String[] dataRow= {S_NO,vehicalDes,Year,Make,model,trim,Odometer,Price,VIN,Color,link2OnlineProcessing,city,state,phoneNumber};

		return dataRow;
	}

	public void getOfferDetails() throws Exception
	{
		carsData2 =new String[720][20];

		carsData = new ExcelReadPack().testDataReaderInRow_CellRange("CarsDataSheet",2,40,1,13);

		dataSetter = 0;

		for(int i=0;i<carsData.length;i++)
		{
			
			try
			{

				if(!(carsData[i][5].contains("Not Available")) && carsData[i][5]!=null)
				{
					if(!(carsData[i][7].contains("Not Available")) && carsData[i][7]!=null)
					{
						new StepBase().log._INFO("-------------------------------------------------------------------------");
						new StepBase().log._INFO("|||>>>>Processing The Item ["+(i+1)+"-"+carsData[i][7]+"]");
						new StepBase().log._INFO("-------------------------------------------------------------------------");

						new Logs().LoopIndex("VIN:"+carsData[i][7],"Processing The VIN");
						
						Step("Select VIN Option","click","Car_vin");

						Step("Inputting The VIN","input","Car_vin_txt",carsData[i][7]);

						Step("Clicking On Get My Offer Button","click","getMyOffer");

						Thread.sleep(4000);

						new Generics().driver.navigate().refresh();
						try
						{	
							Step("Clicking On Trim","click","selectTrim");

							Thread.sleep(4000);

							new Actions(new Generics().driver).sendKeys(Keys.ENTER).build().perform();

							Thread.sleep(4000);

							Step("Clicking On Next Button","click","carNxt1");
						}
						catch (NoSuchElementException ob)
						{
							//Do Nothing
							new StepBase().log._INFO("No Trim Button Is Available");
						}
						catch(ElementClickInterceptedException ob)
						{
							new StepBase().log._INFO("No Trim Button Is Available");
						
						}
						try
						{
							Thread.sleep(2000);
							
							Step("Select Sell Option","JSclick","sellOption");

							Step("Inputting The Car Mailage","input","Car_Mailage","200000"/*carsData[i][5]*/);

							/*try
							{
								new Generics().specificWait(new XpathHub().xpathGetter("mailageExe"),3); continue;
							}
							catch(TimeoutException ob)
							{

							}*/

							Step("Inputting The Zipcode","input","Car_zipcode","85005");

							try
							{
								if(!(carsData[i][8].contains("Not Available")))
								{
									xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("vehColor",carsData[i][8],"","");

									Step("Selecting The Color","click","YES","vehColor;"+xpathConversion,"NO");
								}
								else
								{
									xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("vehColor","Other","","");

									Step("Selecting The Color","click","YES","vehColor;"+xpathConversion,"NO");
								}
							}
							catch(NoSuchElementException ob)
							{
								new StepBase().log._INFO("Vehical Color Is Not Found So Setting Up The Default Color");

								xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("vehColor","Other","","");

								Step("Selecting The Color","click","YES","vehColor;"+xpathConversion,"NO");
							}

							Thread.sleep(4000);
							try {

							Step("Clicking On Next Button","click","carNxt");
							}
							catch(ElementClickInterceptedException ob)
							{
								new StepBase().log._INFO("No Trim Button Is Available");
							
							}

							Thread.sleep(4000);
							try {

							Step("Clicking On Next Button","click","carNxt");
							}
							catch(ElementClickInterceptedException ob)
							{
								new StepBase().log._INFO("No Trim Button Is Available");
							
							}
							
							Thread.sleep(2000);

							Step("Select No Accidents Option","click","noAccidnets_opt");

							xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("selectNo_opt","1","","");

							Step("Selecting The No Option1","click","YES","selectNo_opt;"+xpathConversion,"NO");

							xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("selectNo_opt","2","","");

							Step("Selecting The No Option2","click","YES","selectNo_opt;"+xpathConversion,"NO");

							xpathConversion = new StringClsUtil().XpathconversionUpToThreeParameeters("selectNo_opt","3","","");

							Step("Selecting The No Option3","click","YES","selectNo_opt;"+xpathConversion,"NO");

							Step("Select prettyGreat Option","click","prettyGreat_opt");

							Step("Select Keys Option","click","key_opt");

							Step("Select Neither Option","click","neither_opt");

							Step("Inputting The Email text","input","email_txt","g12@upmail.com");

							Thread.sleep(2000);

							Step("Clicking On Get Your Offer Button","click","getYourOffer");

							String data;

							try
							{	
								testCh= new Generics().getText("getOfferValue",new XpathHub().xpathGetter("getOfferValue"));

								String value[] = {Integer.toString(dataSetter+1),carsData[i][7],testCh};

								carsData2[dataSetter] = value;
								
								new Logs().EXTENT_INFO("Data Collected:"+Arrays.toString(value));

								new StepBase().log._INFO("Got Offer Vlaue:"+testCh);

								dataSetter = dataSetter + 1;
							}
							catch (NoSuchElementException ob)
							{
								new StepBase().log._INFO("No Offer Is Found For This Vehical");
							}


						}
						catch(NoSuchElementException ob)
						{

						}

					}
				}
				Step("Navigating to Carvana Home Page","navigateTo","YES","https://www.carvana.com/sell-my-car/");
			}
			catch(NullPointerException ob)
			{

				Step("Navigating to Carvana Home Page","navigateTo","YES","https://www.carvana.com/sell-my-car/");
			}
			
		}

	}

}
