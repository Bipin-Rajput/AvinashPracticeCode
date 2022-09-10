package StepsBase;

import Generics.Generics;
import excel.utils.StringClsUtil;
import report.logs.Logs;
import specifics.SpecificType;
import xpath.hub.XpathHub;

public class StepBase 
{
	public String testData[][],
	appCreationData[][],
	addControlsData[][],
	loginData[][],
	RecordsData[][];

	public static Logs log;
	String Location,Location1;
	Generics gen;

	String status,path;


	String TestData1[][]={ {"testData","NO"}};
	
	public void TestCaseName(String testCaseId,String TestDesc,String testingType)
	{
		log = new Logs();
		log._INFO("**********************************************************-S-T-A-R-T-****************************************************************************************");
		log._INFO("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
		log._INFO(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log._INFO("::::::::::::::::::::::::::::::::::"+testCaseId+"-"+TestDesc+"::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log._INFO(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log._INFO("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
		log.CreateTestCase(testCaseId, TestDesc,testingType);
	}

	public void Step(String Des,String action) throws Exception
	{

		MainLooper(Des,action,"YES","NO","NO","NO","click",TestData1);
	}
	public void Step(String Des,String action,String elementName) throws Exception
	{
		MainLooper(Des,action,elementName,"NO","NO","NO","click",TestData1);
	}
	public void Step(String Des,String action,String elementName,String TestData) throws Exception
	{
		MainLooper(Des,action,elementName,"NO","NO","NO",TestData,TestData1);
	}

	public void Step(String Des,String action,String elementName,String elementName1,String TestData) throws Exception
	{
		MainLooper(Des,action,elementName,elementName1,"NO","NO",TestData,TestData1);
	}
	public void keyword(String Des, String keyword, String KeywordName,String TestData[][]) throws Exception
	{
		MainLooper(Des,"NO","NO","NO",keyword,KeywordName,"NO",TestData);
	}
	
	public void keyword(String Des, String keyword, String KeywordName) throws Exception
	{
		MainLooper(Des,"NO","NO","NO",keyword,KeywordName,"NO",TestData1);
	}

	void MainLooper(String Des,String action,String elementName,String elementName1,String keyword, String KeywordName,String TestData,String testData[][]) throws Exception
	{
		log._INFO(":::::====>> "+Des);
		gen = new Generics();

		if(keyword=="NO")
		{
			if(elementName!="YES")
			{
				if(action!="dragAndDrop")
				{
					if(new XpathHub().xpathGetter(elementName).contains("[$]"))
					{
						Location = new StringClsUtil().ReplaceString(new XpathHub().xpathGetter(elementName),TestData);
					}
					else
					{
						Location = new XpathHub().xpathGetter(elementName);
					}
				}
				else
				{
					Location = new StringClsUtil().ReplaceString(new XpathHub().xpathGetter(elementName),TestData);
					Location1 = new XpathHub().xpathGetter(elementName1);
				}
			}
			else
			{
				Location = elementName1;
			}
		}
		else
		{
			log._INFO("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
			log._INFO("**************************************"+"Keyword"+"-"+KeywordName+" Execution Started**********************************");
			log._INFO(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			log.createKeyword(KeywordName);
		} 

		if(action!=null && elementName!=null && testData!=null && keyword=="NO" )
		{

			if(elementName1.contains(";//")||elementName1.contains(";(//"))
			{
				String data[] =  new StringClsUtil().SplitData(elementName1);
				elementName = data[0];
				Location = data[1];
			} 

			log._INFO("-------------------------------------------------------------------------------------------------------------------------------------------------------------");

			switch(action)
			{
			case "input": log.EXTENT_INFO("-- "+Des+"["+elementName+"]"+" With Test Data "+"["+TestData+"]");
			gen.input(elementName, Location, TestData); 
			break;
			
			case "JSsetData": log.EXTENT_INFO("-- "+Des+"["+elementName+"]"+" With Test Data "+"["+TestData+"]");
			gen.JSsetData(elementName, Location, TestData); 
			break;
			
			case "click": log.EXTENT_INFO("-- "+Des+"["+elementName+"]");
			gen.click(elementName, Location);
			break;
			
			case "JSclick": log.EXTENT_INFO("-- "+Des+"["+elementName+"]");
			gen.JSclick(elementName, Location);
			break;
			
			case "moveToTheElementThenClickInVisibleElement": log.EXTENT_INFO("-- "+Des+"["+elementName+"]");
			gen.moveToTheElementThenClickInVisibleElement(elementName, Location);
			break;
			
			case "verify": log.EXTENT_INFO("-- "+Des+"["+elementName+"]");
			gen.verify(elementName, Location);
			break;

			case "select":log.EXTENT_INFO("-- "+Des+"["+elementName+"]"+" With The Value "+"["+TestData+"]"); 
			gen.select(elementName,Location, TestData);
			break;

			case "dragAndDrop": log.EXTENT_INFO("-- "+"Dragging An Element ["+elementName+"] To "+ "["+elementName1+"]");
			gen.dropAndDrop(elementName, Location, Location1);
			break;

			case "attachTheImage": log.EXTENT_INFO("-- "+Des+" From The Location"+ System.getProperty("user.dir")+"\\"+TestData);
			gen.attachTheImage(TestData);
			break;

			case "softAssert" : gen.softAssert(Location, TestData);
			break;
			case "JSinput": log.EXTENT_INFO("-- "+Des+"["+elementName+"]"+" With Test Data "+"["+TestData+"]");
			gen.JSinput(elementName, Location, TestData);
			break;
			case "copyPaste": log.EXTENT_INFO("-- "+Des+":"+TestData);
			gen.copyPaste(TestData);
			break;
			case "scrollDown": gen.scrollDown();
			break;
			case "alertValidateThenCancel": gen.alertValidateThenCancel(TestData);
			break;
			case "actionsinput":log.EXTENT_INFO("-- "+Des+"["+elementName+"]"+" With Test Data "+"["+TestData+"]");
			gen.actionsinput(elementName, Location, TestData); 
			break;
			case "getURLS": log.EXTENT_INFO("Getting All Urls Using "+Location+" At Current Page!....");gen.getURLS(Location);
			break;
			case "openNewTab": log.EXTENT_INFO("Opening New Tabs to Grab The Required Data!....."); gen.openNewTab();
			break;
			case "navigateTo":log.EXTENT_INFO("Navigating To Vehical Using Url["+TestData+"]"); gen.navigateTo(TestData);
			break;
			case "GetCurrentWindow":log.EXTENT_INFO("Getting Current Window to Back and Forth!...."); gen.GetCurrentWindow();
			break;
			case "closeCurrentTab":log.EXTENT_INFO("Closing the current Tab!...."); gen.closeCurrentTab();
			break;
			case "switchBackToMainWindiow":log.EXTENT_INFO("Switching Back to The Main Window!...."); gen.switchBackToMainWindiow();
			break;
			case "getText":log.EXTENT_INFO("Getting The Data From The Page!"); gen.getText(elementName, Location);
			break;
			case "LanchBrowser":gen.LanchBrowser(elementName1,TestData);
			break;
			default: {
				log._ERROR("Given Action Is In Out Of Scope");
				log.EXTENT_FAIL("Given Action Is In Out Of Scope");
			}
			}
			
		}
		else
		{
			log.EXTENT_INFO("|"+Des+"|");
			SpecificType spec = new SpecificType();

			switch(KeywordName)
			{ 
				case "getCarsData": spec.getCarDetails();
							 break;
				case "getOfferDetails": spec.getOfferDetails();
							 break;
			default: { log._ERROR("Given Specific Keyword Is In Out Of Scope");
						log.EXTENT_FAIL("Given Specific Keyword Is In Out Of Scope"); } 
			} 

		}
		log._INFO("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

}
