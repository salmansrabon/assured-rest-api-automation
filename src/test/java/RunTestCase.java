import io.qameta.allure.Description;
import javafx.scene.layout.Priority;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketPermission;

import jxl.Sheet;
import jxl.Workbook;
import org.junit.Test;


public class RunTestCase {
    @Test()
    public void getAPI()
    {
        MyAPI m=new MyAPI();
        m.callingGetAPI();
    }
    @Test()
    public void postAPI() throws IOException, BiffException {
        MyAPI m=new MyAPI();
        m.callingPostAPI();
    }
    @Test()
    public void testReadExcelFile() throws IOException, BiffException {
        MyAPI m=new MyAPI();
        m.getExcelValue();
    }

    public static void main(String[] args) throws IOException, InvalidFormatException, BiffException {


    }}
