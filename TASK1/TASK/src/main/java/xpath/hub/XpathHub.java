package xpath.hub;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import report.logs.Logs;

public class XpathHub 
{
	public static HashMap<String,String> xpath_DB; // It will Store all X-paths
	FileInputStream FIS;
	FileOutputStream FOS;
	XSSFWorkbook book;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	XSSFCell cell2=null;

	// This Method will get and store all those data
	public  void XpathHubStore(String Location,String SheetName) throws IOException
	{
		Logs log = new Logs();
		
		xpath_DB = new HashMap<String, String>();

		FIS = new FileInputStream(System.getProperty("user.dir")+"\\"+Location);

		book = new XSSFWorkbook(FIS);

		sheet = book.getSheet(SheetName);

		int LastRowNumber = sheet.getLastRowNum(); 
		sheet.getFirstRowNum();
		log._INFO(":::::::::::::::::::::::::::::::::::::::::::::::::::: READING WEB ELEMENT LOCATIONS INTO XPATH HUB:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(int i=1,j=2,k=1;k<=LastRowNumber;k++)
		{

			row = sheet.getRow(k);
			cell = row.getCell(i);
			cell2 = row.getCell(j);
			if(row!=null && cell!=null && cell2!=null)
			{
				if(cell.getStringCellValue()!="" && cell2.getStringCellValue()!="")
				{
				log._INFO("["+cell.getStringCellValue()+"]"+" = "+"["+cell2.getStringCellValue()+"]");
				log._INFO("----------------------------------------------------------------------------------------------------------------------------------------------------------");
				xpath_DB.put(cell.getStringCellValue(), cell2.getStringCellValue());
				}
			}

		}
	}

	// Using This Method, Required Xpath Will be Returned by matching element name
	public String xpathGetter(String ElementName) throws IOException
	{
		return xpath_DB.get(ElementName);
	}

	public void closeXpathBook() throws IOException
	{
		book.close();
	}
}
