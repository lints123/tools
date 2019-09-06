package com.lints.tools.comment;

import com.lints.tools.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;


import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;

public class ExeclUtil {

    //
    public static void exportExecl(String title, String[] headers,String[] childCells, Collection<Object> dataset, OutputStream out){

        // 定义一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 定义一个表格
        XSSFSheet sheet = workbook.createSheet(title);

        //如果这行没有了，整个公式都不会有自动计算的效果的
        sheet.setForceFormulaRecalculation(true);

        // 设置表格宽度
        sheet.setDefaultColumnWidth(20);

        // 创建标题样式
        XSSFCellStyle cellTitleStyle = workbook.createCellStyle();
        // 垂直居中
        cellTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        cellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellTitleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 标题字体
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setFontName("宋体");
        // 粗体显示
        fontTitle.setBold(true);
        fontTitle.setFontHeightInPoints((short) 20);  //字体大小
        cellTitleStyle.setFont(fontTitle);


        // 创建副标题样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(Color.WHITE));
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 生成字体
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontName("宋体");
        // font.setColor(new XSSFColor(Color.BLACK));
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);


        // 标题行的样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(new XSSFColor(java.awt.Color.WHITE));
        style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        XSSFFont font2 = workbook.createFont();
        font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 产生表格标题行
        int index = 0;
        XSSFRow row = sheet.createRow(index++);
        XSSFCell row0cell0 = row.createCell(0, 1);
        row0cell0.setCellValue("12312312");
        row0cell0.setCellStyle(cellTitleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));

        //合并，参数说明：1：开始行 2：结束行  3：开始列 4：结束列
        CellRangeAddress titleRow1 = new CellRangeAddress(1, 2, 0, 1);
        sheet.addMergedRegion(titleRow1);

        CellRangeAddress titleRow2 = new CellRangeAddress(1, 2, 2, 3);
        sheet.addMergedRegion(titleRow2);

       XSSFRow row1= sheet.createRow(index++);
        XSSFCell cellHeader = null;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row1.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
        }
        RegionUtil.setBorderRight(XSSFCellStyle.BORDER_THIN,titleRow1,sheet,workbook);
        RegionUtil.setBorderRight(XSSFCellStyle.BORDER_THIN,titleRow2,sheet,workbook);


        // 添加子标题
        XSSFRow row2 = sheet.createRow(++index);
        XSSFCell childCell;
        for (int i = 0; i < childCells.length; i++) {
            childCell = row2.createCell(i);
            childCell.setCellStyle(style);
            childCell.setCellValue(new XSSFRichTextString(childCells[i]));
        }

        // 遍历集合数据，产生数据行
        Iterator<Object> it = dataset.iterator();
        Object t;
        Field[] fields;
        Field field;
        HSSFRichTextString richString;
        // Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        String fieldName;
        String getMethodName;
        XSSFCell cell;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        // SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            t = (Object) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        // textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    /*if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }*/
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
