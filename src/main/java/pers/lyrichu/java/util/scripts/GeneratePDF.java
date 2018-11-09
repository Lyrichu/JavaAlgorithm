package pers.lyrichu.java.util.scripts;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;


public class GeneratePDF {
    private static String outputPath = "src/main/resources/test.pdf";

    public static void main(String[] args) {
        try {
            OutputStream out = new FileOutputStream(new File(outputPath));
            Document document = new Document();
            PdfWriter.getInstance(document,out);
            document.open();
            document.add(new Paragraph("I love you!"));
            document.add(new Paragraph(new Date().toString()));
            document.close();
            out.close();
        } catch (Exception e) {
            System.err.println("error:"+e);
        }
    }
}
