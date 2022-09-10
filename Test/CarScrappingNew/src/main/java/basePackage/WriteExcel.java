package basePackage;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {

	public void writeExcel(String filePath, String fileName, String[] dataToWrite)throws Exception {
		String excelPath = "E:\\Test\\CarScrapping\\src\\test\\java\\ExportExcel.xlsx";
		File file = new File(excelPath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
//		XSSFSheet spreadsheet= wb.createSheet("ExportExcel");
		XSSFRow row;
		XSSFSheet sheet = wb.getSheet("ExportExcel");

		int totalColumn = dataToWrite.length;

//        int rowid = 0;
        // writing the data into the sheets...
        for (int i =1 ; i<= 1 ; i++) {
        	
            row = sheet.createRow(i);
           
            Object[] objectArr = dataToWrite;
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
//                System.out.println(cellid +" "+cell);
            }  
            objectArr = null;
        }
        
//		sheet.getRow(row).createCell(col).setCellValue(cellValue);
		FileOutputStream fos = new FileOutputStream(new File(excelPath));
		wb.write(fos);
		wb.close();
		fos.close();
		
		
	}
}
