package testcases;

import org.testng.annotations.Test;

import basePackage.WriteExcel;

public class writeExcelTest {
	WriteExcel obj = new WriteExcel();
	
	@Test
	public void writeExcelTest() throws Exception {
		obj.writeExcel("ExportExcel", "VIN", 0, 0);
		
	}
	

}
