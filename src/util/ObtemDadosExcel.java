package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * Read data in an Excel spreadsheet and return it as a collection of objects.
 * This is designed to facilitate for parameterized tests in JUnit that
 * get data from an excel spreadsheet.
 * @author johnsmart
 *
 */
public class ObtemDadosExcel {

    private transient Collection<Object[]> data = null;
    private String nomeFolha ="";
    private boolean ignoraPrimeiraLinha=false;

    public ObtemDadosExcel(final InputStream excelInputStream) throws IOException {
        this.data = loadFromSpreadsheet(excelInputStream);
    }
    public ObtemDadosExcel(final InputStream excelInputStream, 
    					   final String nomeFolha, 
    					   final boolean ignorarLinha1) throws IOException {
    	this.nomeFolha = nomeFolha;
        this.ignoraPrimeiraLinha = ignorarLinha1;
        this.data = loadFromSpreadsheet(excelInputStream); 
    }

    public Collection<Object[]> getData() {
        return data;
    }

    private Collection<Object[]> loadFromSpreadsheet(final InputStream excelFile)
            throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet;
        data = new ArrayList<Object[]>();

        if (!this.nomeFolha.isEmpty()){
        	sheet = workbook.getSheet(this.nomeFolha);
        }else{
        	sheet = workbook.getSheetAt(0);
        }

        int numberOfColumns = countNonEmptyColumns(sheet);
        List<Object[]> rows = new ArrayList<Object[]>();
        List<Object> rowData = new ArrayList<Object>();

        for (Row row : sheet) { 
        	if (isEmpty(row)) {
                break;
            } else {
                rowData.clear();
                for (int column = 0; column < numberOfColumns; column++) {
                    Cell cell = row.getCell(column);
                    rowData.add(objectFrom(workbook, cell));
                }
                rows.add(rowData.toArray());
            }
        }
        if (this.ignoraPrimeiraLinha) {
        	rows.remove(0);
        }
        return rows;
    }

    private boolean isEmpty(final Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
        return rowIsEmpty;
    }

    /**
     * Count the number of columns, using the number of non-empty cells in the
     * first row.
     */
    private int countNonEmptyColumns(final Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(final Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;
    }

    private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
        Object cellValue = null;
        cellValue = cell.toString();
        
	        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	            cellValue = cell.getRichStringCellValue().getString();
	        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	        	cellValue = getNumericCellValue(cell);
	        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	            cellValue = cell.getBooleanCellValue();
	        } else if (cell.getCellType()  ==Cell.CELL_TYPE_FORMULA) {
	            cellValue = evaluateCellFormula(workbook, cell);
	        }
	        //verificar nulo/vazio
	        if (cellValue.toString().equals("vazio")){
	        	cellValue = "";
	        } if (cellValue.toString().endsWith(".0")){
	        	// tratamento de números inteiros
	        	cellValue = cellValue.toString().replace(".0", "");
	        }
	        //System.out.println(cellValue);
        return cellValue;
        
    }

    private Object getNumericCellValue(final Cell cell) {
        Object cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
            cellValue = new Date(cell.getDateCellValue().getTime());
        } else {
            cellValue = cell.getNumericCellValue();
        } 
        
        return cellValue;
    }

    private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        Object result = null;
        
        if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result = cellValue.getBooleanValue();
        } else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            result = cellValue.getNumberValue();
        } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
            result = cellValue.getStringValue();   
        }
        
        return result;
    }
}
