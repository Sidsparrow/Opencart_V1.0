package utilities;

import java.io.IOException;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

public class Dataproviders {
	//previously hamne hardcoded kiya tha data provider ko ab hm proper integrtion karenge excel file ke saath
	
	//Data provider 1
	
	@DataProvider(name="loginData")
	public String[][] getdata() throws IOException {
		
		//1st Need to find where is excel 
		String path = System.getProperty("user.dir")+"/testData/LoginData.xlsx";
		
		//2. need to invoke the excel utility 
		Excelutility xlutil = new Excelutility(path);
		
		int totalrows = xlutil.getrowcount("Sheet1");
		
		int totalcols = xlutil.getcolcount("Sheet1", 1);
		
		//Now Excel stores data in 2d array 
		//Hence need to create 2d array 
		
		String logindata[][] = new String[totalrows][totalcols] ;
		
		for (int i = 1; i <=totalrows; i++) {
			for(int j =0 ; j < totalcols ; j++)
			{
				logindata[i-1][j] = xlutil.getcelldata("Sheet1", i, j);
				// dekho jb hm 2d array mai save krte hai toh 0,0 0,1 ... aaese krna hota 
				// logindata[0][0] = xlutil.getcelldata("Sheet1",1,0) ye aaesa krne ke liye 
			}
			
		}
		return logindata;
		
	}
		
	//data provider 2 
	
	
	//	//data provider 3

	//data provider 4

}
