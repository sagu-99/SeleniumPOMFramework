package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    //Load Excel File
    public static void loadExcelFile(String filePath, String sheetName) {
        try {
            FileInputStream file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        Cell cell = sheet.getRow(rowNum).getCell(colNum);
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return "";
    }

    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public static void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
