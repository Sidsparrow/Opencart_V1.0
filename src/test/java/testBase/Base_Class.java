package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager; //log4j
import org.apache.logging.log4j.Logger; //log4j
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import freemarker.template.SimpleDate;

public class Base_Class {

	public static WebDriver driver;
	public Logger logger; // implement log4j2
	public Properties p;

	@BeforeClass(groups = { "master", "sanity", "regression" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws Exception {
		// loading Config propertes files
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass()); // (Base_Class.class);
		// After this statement we can add log statement in Test Cases !!!!!!

		// This is all for Grid environment
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			/*
			 * capabilities.setPlatform(Platform.LINUX);
			 * capabilities.setPlatform(Platform.MAC);
			 * capabilities.setPlatform(Platform.WIN11);
			 * capabilities.setBrowserName("chrome");
			 */

			// For Grid environment OS
			// OS
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No Matching OS");
			}

			// For Grid environment Browser
			// Browser
			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MircosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;

			default:
				System.out.println("No Matching Browser");

				return;
			}
			
			//Selenium Grid ke URLs, /ui/ aur /wd/hub, ka purpose alag hai. /ui/ web-based dashboard ke liye hota hai,
			/*
			 * jo automation ke liye nahi, sirf monitoring aur visualization ke liye use kiya jata hai. 
			/wd/hub WebDriver commands aur test execution ke liye hai aur hamesha RemoteWebDriver configuration ke liye use karna chahiye.
			Selenium 4 ke latest version mein /ui/ aur /wd/hub dono exist karte hain,
			lekin WebDriver sessions ke liye /wd/hub recommended hai. Selenium 3 mein sirf /wd/hub support karta hai. 
			Isliye, aap apne tests aur automation setup ke liye http://localhost:4444/wd/hub ka use karein.
			*/
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			//application URL prop file for Docker selenium grid  mai hai 
			
			// driver.get("http://localhost/opencart/upload/");
			driver.get(p.getProperty("appUrlgrid")); // reading URL from config file
		}

		// for Local Environment
		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {	
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
			default:
				System.out.println("Invalid Browser name...");
				return; // here it is use to return agr na use karu toh neecha ka code pura chalega jo
						// mai chata ni

			}
			//application URL prop file mai hai 
			
			// driver.get("http://localhost/opencart/upload/");
			driver.get(p.getProperty("appUrl")); // reading URL from config file
			
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

		
		
		
		driver.manage().window().maximize();

	}

	@AfterClass(groups = { "master", "sanity", "regression" })
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {

		String randdatastr = RandomStringUtils.randomAlphabetic(6);
		return randdatastr;

	}

	public String randomNumber() {
		String randatanum = RandomStringUtils.randomNumeric(6);
		return randatanum;
	}

	public String randomAlphanumberic() {
		String randomalphanum = RandomStringUtils.randomAlphanumeric(6);
		return randomalphanum;
	}

	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.HH.mm.ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		// source file
		File sourcefile = ts.getScreenshotAs(OutputType.FILE);

		// Set target File path
		String TargetfilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
		// Creating opbject for target file
		File targetFile = new File(TargetfilePath);

		// Copys
		sourcefile.renameTo(targetFile);

		return TargetfilePath;

	}

}
