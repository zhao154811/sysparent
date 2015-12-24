package test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/24.
 */
public class IndexOutPut {
    @Test
    public void getIndex() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://211.155.80.62:33466/oasis?user=shucang_r&password=2Ar3QzE19Ug9uFRPvMHPo1";
        Connection con = DriverManager.getConnection(url);
        String showtables = "show tables";
        List<String> tables = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement(showtables);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("sheet1");
        int rownum = 0;
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        for (String table : tables) {

            PreparedStatement pst1 = con.prepareStatement("show index from " + table);
            ResultSet rs1 = pst1.executeQuery();

            rs1.last(); //结果集指针知道最后一行数据
            int n = rs1.getRow();
            if (n > 1) {
                rs1.close();
                pst1.close();
                continue;
            }
            rs1.beforeFirst();//将结果集
            Row tablerow = sheet1.createRow(rownum++);
            Cell tableNamecell = tablerow.createCell(0);
            tableNamecell.setCellStyle(style);
            tableNamecell.setCellValue(table);
            tablerow.setRowStyle(style);
            while (rs1.next()) {
                Row valuerow = sheet1.createRow(rownum++);
                valuerow.createCell(0).setCellValue(rs1.getString("Key_name"));
                valuerow.createCell(1).setCellValue(rs1.getString("Column_name"));
                valuerow.createCell(2).setCellValue(rs1.getString("Index_type"));
            }
            rs1.close();
            pst1.close();
        }
        OutputStream os = new FileOutputStream(new File("d://indexExport1.xls"));
        wb.write(os);
        os.close();

        rs.close();
        pst.close();

        con.close();
    }
}
