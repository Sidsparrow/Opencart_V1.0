package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelutility {

	public  FileInputStream fi;
	public  FileOutputStream fo;
	public  XSSFWorkbook wb;
	public  XSSFSheet ws;
	public  XSSFRow row;
	public  XSSFCell cell;
	public  CellStyle style;
	public  String path;

	public Excelutility(String path) {
		this.path = path;
	}

	public  int getrowcount(String sheetname) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetname);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	public  int getcolcount(String sheetname, int rownum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetname);
		int colcount = ws.getRow(rownum).getLastCellNum();
		wb.close();
		fi.close();
		return colcount;
	}

	public  String getcelldata(String sheetname, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetname);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		// as cell is in XSSfcell we need to convert it in string
		String data;
		try {
			DataFormatter formatterr = new DataFormatter();
			data = formatterr.formatCellValue(cell);

		} catch (Exception e) {
			// TODO: handle exception
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public  void setcelldata(String sheetname, int rownum, int colnum, String data) throws IOException {
		File xlfile = new File(path);
		if (!xlfile.exists()) // if file does not exist ten create new one 
			{
				wb = new XSSFWorkbook();
				fo = new FileOutputStream(path);
				wb.write(fo);
			}
		

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		
		if(wb.getSheetIndex(sheetname)==-1) //if sheet does not exsist then create new one 
			wb.createSheet(sheetname);		
		ws = wb.getSheet(sheetname);
		
		if(ws.getRow(rownum)==null) //if row does not exist then create new one 
			ws.createRow(rownum);
		row = ws.getRow(rownum);
		
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fi.close();

	}

	public  void fillgreencolor(String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}

	public  void fillRedcolor(String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}

	public  void fillyellow(String xlsheet, int rownum, int column) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(column);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
	}

}
