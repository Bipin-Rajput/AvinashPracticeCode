 package excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadPack 
{
	public static FileInputStream FIS1;
	public static FileOutputStream FOS;
	public static XSSFWorkbook book[];
	public static XSSFWorkbook book1;
	static int books;

	public XSSFSheet sheet;
	XSSFSheet sheet2;
	
	static XSSFSheet sheet1;

	XSSFRow row,row1;
	XSSFCell cell;
	XSSFCell cell2=null;

	String TestDataGetter[][];
	String TestDataGetter1[];
	String data = null;
	Hashtable<String,String> XpathCatcher;
	String returner;
	String[]headers = {"Description","Actions","WebElementName","TestData"};
	
	static HashMap<String,ArrayList<String[]>> dataTest;


	public void OpenAWBooks(String workbooks[]) throws IOException
	{
		books = workbooks.length; 

		book = new XSSFWorkbook[workbooks.length];

		for(int i=0;i<workbooks.length;i++)
		{
			FIS1 = new FileInputStream(System.getProperty("user.dir")+"\\"+workbooks[i]);

			book[i] = new XSSFWorkbook(FIS1);
		}
	}

	public void closeAllBooks() throws IOException

	{
		for(int i=0;i<books;i++)
		{
			book[i].close();
		}
	}


	public String[][] testDataReaderInCellRange(String SheetName,int RowNumber,int startcell,int lastCell) throws IOException
	{ 
		sheet = sheetObjectReturn(SheetName);

		TestDataGetter = new String[1][lastCell-startcell+1];
		int rep = lastCell - startcell +1;

		for(int j=startcell,x=0;x<rep;j++,x++)
		{
			if(sheet.getRow(RowNumber-1).getCell(j-1)!=null)
			{

				TestDataGetter[0][x]= readCellDataAsPerTheType(sheet.getRow(RowNumber-1).getCell(j-1));

			}
		}

		return TestDataGetter;
	} 

	public String[] testDataReaderInCellRange1(String SheetName,int RowNumber,int startcell,int lastCell) throws IOException
	{ 
		sheet = sheetObjectReturn(SheetName);

		TestDataGetter1 = new String[lastCell-startcell+1];
		
		int rep = lastCell - startcell +1;

		for(int j=startcell,x=0;x<rep;j++,x++)
		{
			if(sheet.getRow(RowNumber-1).getCell(j-1)!=null)
			{

				TestDataGetter1[x]= readCellDataAsPerTheType(sheet.getRow(RowNumber-1).getCell(j-1));

			}
		}

		return TestDataGetter1;
	} 

	public String readCellDataAsPerTheType(XSSFCell celltype)
	{
		String str=null;
		
		CellType type = celltype.getCellType();

		if(type==CellType.NUMERIC)
		{
			str = NumberToTextConverter.toText(celltype.getNumericCellValue()).trim();

		}

		if(type==CellType.STRING)
		{
			str= celltype.getStringCellValue().trim();
		}

		return str;

	}


	public XSSFSheet sheetObjectReturn(String SheetName)
	{	
		for(int i=0;i<books;i++)
		{
			if(book[i].getSheet(SheetName)!=null)
			{
				sheet2 = book[i].getSheet(SheetName);
			}
		}

		return sheet2;

	}

	public String[][] testDataReaderInRow_CellRange(String SheetName,int startRowNumber,int lastRowNumber,int startcell, int endcell) throws IOException
	{
		sheet = sheetObjectReturn(SheetName);

		if(startRowNumber==lastRowNumber) TestDataGetter = new String[1][endcell-startcell+1];

		else TestDataGetter = new String[lastRowNumber-startRowNumber+1][endcell-startcell+1];

		for(int i=startRowNumber-1,x=0;i<lastRowNumber;i++,x++)
		{
			for(int j = startcell-1,y=0;j<endcell;j++,y++)
			{
				if(sheet.getRow(i).getCell(j)!=null)
				{
					TestDataGetter[x][y] = readCellDataAsPerTheType(sheet.getRow(i).getCell(j));
				}
			}
		}
		return TestDataGetter;

	} 
	
	
	public HashMap<String,ArrayList<String[]>> AdvanceReader(String SheetName,int labelRowNumber,int startRowNumber,int lastRowNumber,int startcell, int endcell) throws IOException
	{	
		String[][]testData= testDataReaderInRow_CellRange(SheetName,startRowNumber,lastRowNumber,startcell,endcell);
		
		String[] labelNames= testDataReaderInCellRange1(SheetName, labelRowNumber, startcell, endcell);
		

		for(int i=0;i<labelNames.length;i++)
		{
			ArrayList<String[]> data1=new ArrayList<String[]>();
			
			String[] datas = new String[testData.length];
			
			for(int j=0;j<testData.length;j++)
			{
				datas[j]= testData[j][i];
			}
			data1.add(datas);
			
			dataTest.put(labelNames[i],data1);
		}
		
		
		return dataTest;
	}
	
	public static String getDataFromMap(String key,int listNum,int arrayNum)
	{
		return dataTest.get(key).get(listNum)[arrayNum];
	}
	
	public void createNewSheetInBook(String[] labels,String[][] data,String SheetName,String bookName) throws IOException
	{
		XSSFWorkbook book = new XSSFWorkbook();
		
		XSSFSheet sheet = book.createSheet(SheetName);
		
		XSSFRow row = sheet.createRow(0);
		
		for(int i=0;i<labels.length;i++)
		{
			row.createCell(i).setCellValue(labels[i]);
		}
		
		for(int i=0;i<data.length;i++)
		{
			row = sheet.createRow(i+1);
			
			for(int j=0;j<data[i].length;j++) 
			{
				if(data[i][j]!="" && data[i][j]!=null ) row.createCell(j).setCellValue(data[i][j]);
			}
		}
		
		FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir"))+"/DataSheet/"+bookName+".xlsx");
		
		book.write(fos);
	}
	
	public void StroreData(String[][] data)
	{
		
	}
}
