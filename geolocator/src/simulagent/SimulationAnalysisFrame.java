package simulagent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import sensordataset.AnalyzedData;
import javax.swing.JMenuBar;

/**
 * @author tiagoportela <tiagoporteladesouza@gmail.com>
 * 
 */
public class SimulationAnalysisFrame extends JFrame {
    public SimulationAnalysisFrame() {
    }
    /**
     * 
     */
    private static final long serialVersionUID = 4883114787870739317L;

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;

    /**
     * <p>
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param analyzedData 
     * @param
     * @return
     */
    public static void showAnalysis(List<AnalyzedData> analyzedData) {
        SimulationResults results = new SimulationResults();
        
        for (AnalyzedData data : analyzedData) {
            if(data.isPredictionCorrect()) {
                results.increaseNumberOfCorrectPredictions();
            } else if(data.isPredictionUndefined()) {
                results.increaseNumberOfWrongPredictions();
            } else {
                results.increaseNumberOfWrongPredictions();
            }
        }
        
        
        
//        try {
//            SimulationAnalysisFrame test = new SimulationAnalysisFrame(); 
//            test.setOutputFile("test.xls");
//            test.write(analyzedData);
//        } catch (WriteException | IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write(List<AnalyzedData> analyzedData) throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        createContent(excelSheet, analyzedData);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "Agents Real Class");
        addCaption(sheet, 1, 0, "Agents Predicted Class");

    }

    private void createContent(WritableSheet sheet, List<AnalyzedData> analyzedData) throws WriteException, RowsExceededException {
        // Write a few number
        for (int i = 1; i < analyzedData.size(); i++) {
            // First column
            final AnalyzedData data = analyzedData.get(i);
            
            addLabel(sheet, 0, i, data.getRealMobility().toString());
            addLabel(sheet, 1, i, data.getPredictedMobility().toString());
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }
    
//    public static void main(String[] args) throws WriteException, IOException {
//        SimulationAnalysisFrame test = new SimulationAnalysisFrame(); 
//        test.setOutputFile("test.xls");
//        test.write(null);
//        System.out.println("Please check the result file under c:/temp/lars.xls ");
//    }
}
