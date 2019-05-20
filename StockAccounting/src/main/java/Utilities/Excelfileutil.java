package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class Excelfileutil {
	
	Workbook wb;

	public Excelfileutil() throws Throwable 
	{
		FileInputStream fis = new FileInputStream("./TestInputs/InputSheet.xlsx");
//FileInputStream fis = new FileInputStream("C:\\Users\\govardhan.c\\govardhan\\Seleniumgo\\TestInputs");
		wb = WorkbookFactory.create(fis);

	}

	public int rowCount(String sheetname) 
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}

	public int colCount(String sheetname, int row) 
	{
		return wb.getSheet(sheetname).getRow(row).getLastCellNum();
	}

public String getData(String sheetname, int row, int column) 
	{
		String data = "";
		if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC) 
		{
		int celldata = (int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		data=String.valueOf(celldata);
        }
		else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	
     }
public void setData(String sheetname,int row,int column,String status) throws Throwable
{
	Sheet sh=wb.getSheet(sheetname);
	Row rownum=sh.getRow(row);
	Cell cell=rownum.createCell(column);
	cell.setCellValue(status);
	
	if(status.equalsIgnoreCase("pass"))
	{
		CellStyle style=wb.createCellStyle();
		Font font=wb.createFont();
		
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);
	}
	else
		
		if(status.equalsIgnoreCase("fail"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		}
		else
			
			if(status.equalsIgnoreCase("not executed"))
			{
				CellStyle style=wb.createCellStyle();
				Font font=wb.createFont();
				
				font.setColor(IndexedColors.BLUE.getIndex());
				font.setBold(true);
				style.setFont(font);
				rownum.getCell(column).setCellStyle(style);
			}
	FileOutputStream fos = new FileOutputStream("./TestOutputs/TestOutputs.xlsx");
	wb.write(fos);
	fos.close();
   }
}
