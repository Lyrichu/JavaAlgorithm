package pers.lyrichu.java.util.scripts;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;

public class SplitPDF {
    private static Logger LOGGER = LoggerFactory.getLogger(SplitPDF.class);

    public static void main(String[] args) {
        String pdfPath = "src/main/resources/HBase实战.pdf";
        int beginPage = 5;
        int endPage = 10;
        String savePath = String.format("src/main/resources/HBase 实战_%d_%d.pdf"
                ,beginPage,endPage);
        splitPDF(pdfPath,savePath,beginPage,endPage);
    }

    public static void splitPDF(String pdfPath,String savePath,int beginPage,int endPage) {
        try {
            PdfReader reader = new PdfReader(pdfPath);
            int totalPages = getPdfTotalPages(reader);
            if (beginPage > totalPages || beginPage < 0 || endPage <=0 || beginPage > endPage) {
                throw new Exception("Illegal beginPage or endPage!");
            }
            Document document = new Document(reader.getPageSize(1));
            PdfCopy pdfCopy = new PdfCopy(document,new FileOutputStream(savePath));
            document.open();
            for (int i = beginPage;i<=endPage;i++) {
                document.newPage();
                PdfImportedPage page = pdfCopy.getImportedPage(reader,i);
                pdfCopy.addPage(page);
            }
            document.close();
            LOGGER.info("Split {} of pages from {} to {} page successfully!",
                    pdfPath,beginPage,endPage);
        } catch (Exception e) {
           LOGGER.error("SplitPDF error:{}",e);
        }
    }

    // 返回pdf的总页数
    public static int getPdfTotalPages(String pdfPath) throws Exception{
        PdfReader reader = new PdfReader(pdfPath);
        return reader.getNumberOfPages();
    }

    public static int getPdfTotalPages(PdfReader reader) {
        return reader.getNumberOfPages();
    }
}
