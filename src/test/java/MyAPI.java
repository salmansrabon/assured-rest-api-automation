import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class MyAPI {
    public void callingGetAPI()
    {
        RestAssured.baseURI  = Config.baseURL;
        Response res =
                given()
                        .contentType("application/json").auth().basic("","").
                        when()
                            .get("posts/100").
                        then()
                            .assertThat().statusCode( 200 ).extract().response();


        JsonPath jsonpath = res.jsonPath();
        Integer userid = jsonpath.get("userId");
        String title = jsonpath.get("title");
        String body = jsonpath.get("body");
        System.out.println( "Id: "+userid );
        System.out.println( "Title: "+title );
        System.out.println( "Body: "+body );
        Assert.assertEquals( userid.toString(),"10" );

        System.out.println( res.asString() );
        //Allure.addDescription(res.asString());

    }
    public void callingPostAPI() throws IOException, BiffException {
        RestAssured.baseURI  = Config.baseURL;

        getExcelValue();

        Response res =
                given()
                .contentType("application/json").
                        body("{\"title\":\""+title+"\",\"body\":\""+body+"\",\"userId\":\""+userid+"\"}").
                        auth().basic("",""). //if noAuth
                when().
                        post("posts").then().extract().response();

        JsonPath jsonpath = res.jsonPath();
        System.out.println(res.asString());
        userid=jsonpath.get("userId");
        title=jsonpath.get("title");
        body=jsonpath.get("body");

        System.out.println("User Id: "+userid);
        System.out.println("Title: "+title);
        System.out.println("Body: "+body);

        Assert.assertEquals(userid,""+userid+"");
        Assert.assertEquals( res.getStatusCode(),201 );
        //Allure.addDescription(response);
    }
    String title="";
    String body="";
    String userid="";
    public void getExcelValue() throws IOException, BiffException {
        String SAMPLE_XLSX_FILE_PATH = "./src/test/resources/testdata.xls";
        Workbook workbook;
        FileInputStream fs= new FileInputStream(SAMPLE_XLSX_FILE_PATH);
        Workbook wb=Workbook.getWorkbook(fs);
        Sheet s=wb.getSheet(0);
        for ( int r=1; r<s.getRows(); r++)
        {
            title=s.getCell(0, r).getContents();
            body=s.getCell(1, r).getContents();
            userid=s.getCell(2, r).getContents();
            //System.out.println( s.getCell(0, r).getContents()+"\t"+s.getCell(1, r).getContents()+"\t"+s.getCell(2, r).getContents() );
            RestAssured.baseURI  = Config.baseURL;
            Response res =
                    given()
                            .contentType("application/json").
                            body("{\"title\":\""+title+"\",\"body\":\""+body+"\",\"userId\":\""+userid+"\"}").
                            auth().basic("",""). //if noAuth
                            when().
                            post("posts").
                            then().extract().response();

            JsonPath jsonpath = res.jsonPath();
            System.out.println(res.asString());

        }

    }




}
