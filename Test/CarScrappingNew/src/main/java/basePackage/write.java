package basePackage;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class write {
	public Svoid writeExcel () throws IOException{

		FileInputStream fis = new FileInputStream("E:\\Test\\CarScrapping\\src\\test\\java\\ExportExcel.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		XSSFSheet sheet = workbook.getSheet("ExportExcel");
		
        Row row = sheet.createRow(1);
		Cell cell = row.createCell(1);
		
//		cell.setCellType(cell.CELL_TYPE_STRING);
		cell.setCellValue("SoftwareTestingMaterial.com");
		FileOutputStream fos = new FileOutputStream("E:\\Test\\CarScrapping\\src\\test\\java\\ExportExcel.xlsx");
		workbook.write(fos);
		fos.close();
		System.out.println("END OF WRITING DATA IN EXCEL");
	}
}