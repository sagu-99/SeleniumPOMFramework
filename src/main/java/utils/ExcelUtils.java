package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    // Load Excel File (internal helper)
    private static void openWorkbook(String filePath, String sheetName) throws Exception {
        FileInputStream file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet(sheetName);
    }

    // Close workbook
    private static void closeWorkbook() {
        try {
            if (workbook != null) workbook.close();
        } catch (Exception ignored) { }
    }

    // Read sheet and return list of maps (header -> cellValue)
    public static List<Map<String, String>> getDataAsMap(String filePath, String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();
        try {
            openWorkbook(filePath, sheetName);
            if (sheet == null) return rows;

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return rows;

            int lastRow = sheet.getPhysicalNumberOfRows();
            int lastCell = headerRow.getLastCellNum();

            for (int r = 1; r < lastRow; r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Map<String, String> map = new HashMap<>();
                for (int c = 0; c < lastCell; c++) {
                    String key = getCellStringValue(headerRow.getCell(c));
                    String value = getCellStringValue(row.getCell(c));
                    map.put(key != null && !key.isEmpty() ? key : "col" + c, value != null ? value : "");
                }
                rows.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeWorkbook();
        }
        return rows;
    }

    // Helper to return DataProvider-compatible Object[][] where each row contains a Map
    public static Object[][] getDataProviderFromExcel(String filePath, String sheetName) {
        List<Map<String, String>> list = getDataAsMap(filePath, sheetName);
        Object[][] data = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i);
        }
        return data;
    }

    // existing helper preserved for single-cell reads (if needed)
    public static String getCellData(int rowNum, int colNum) {
        try {
            if (sheet == null) return "";
            Cell cell = sheet.getRow(rowNum).getCell(colNum);
            return getCellStringValue(cell);
        } catch (Exception e) {
            return "";
        }
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        CellType type = cell.getCellType();
        if (type == CellType.NUMERIC) {
            double d = cell.getNumericCellValue();
            if (d == (int) d) return String.valueOf((int) d);
            return String.valueOf(d);
        } else if (type == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (type == CellType.FORMULA) {
            try {
                return cell.getStringCellValue();
            } catch (Exception e) {
                try { return String.valueOf(cell.getNumericCellValue()); } catch (Exception ex) { return ""; }
            }
        }
        return "";
    }
}