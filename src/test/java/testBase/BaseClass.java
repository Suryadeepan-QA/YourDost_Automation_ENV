package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import pageObjects.Login_Popup;
import pageObjects.Logout_Page;
import pageObjects.NonLoggedin_HomePage;

public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties p;

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeClass
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {

        FileReader file =
                new FileReader(
                        "./src//test//resources//config.properties"
                );

        p = new Properties();

        p.load(file);

        file.close();

        logger =
                LogManager.getLogger(
                        this.getClass()
                );

        switch (br.toLowerCase()) {

            case "chrome":

                driver = new ChromeDriver();

                break;

            case "edge":

                EdgeOptions options =
                        new EdgeOptions();

                options.addArguments(
                        "--window-size=1920,1080"
                );

                options.addArguments(
                        "--start-maximized"
                );

                driver =
                        new EdgeDriver(options);

                break;

            case "firefox":

                driver =
                        new FirefoxDriver();

                break;

            default:

                System.out.println(
                        "Invalid browser: " + br
                );

                return;
        }

        /*
         * Delete existing cookies before starting.
         */
        driver.manage().deleteAllCookies();

        /*
         * Use a fixed size so local and GitHub Actions
         * behave more consistently.
         */
        driver.manage().window().setSize(
                new Dimension(1920, 1080)
        );

        /*
         * Implicit wait.
         */
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(10)
        );

        /*
         * Open application.
         */
        driver.get(
                p.getProperty("url")
        );
    }

    // =========================================================
    // TEARDOWN
    // =========================================================

    @AfterClass
    public void teardown() {

        System.out.println(
                "Starting browser teardown..."
        );

        try {

            if (driver != null) {

                driver.quit();

                System.out.println(
                        "Browser closed successfully."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while closing browser: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // RANDOM STRING
    // =========================================================

    public String randomstring() {

        String generatedstring =
                RandomStringUtils.randomAlphabetic(5);

        return generatedstring;
    }

    // =========================================================
    // RANDOM NUMBER
    // =========================================================

    public String randomnumber() {

        String generatednumber =
                RandomStringUtils.randomNumeric(5);

        return generatednumber;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public void login()
            throws InterruptedException, IOException {

        NonLoggedin_HomePage hp =
                new NonLoggedin_HomePage(driver);

        hp.clickloginbtn();

        Login_Popup lp =
                new Login_Popup(driver);

        lp.setusername(
                p.getProperty("username")
        );

        Thread.sleep(1000);

        lp.setpassword(
                p.getProperty("password")
        );

        Thread.sleep(1000);

        lp.clickloginbtn();
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    public void logout() {

        System.out.println(
                "Calling logout cleanup..."
        );

        try {

            if (driver == null) {

                System.out.println(
                        "Driver is null. Logout skipped."
                );

                return;
            }

            Logout_Page lp =
                    new Logout_Page(driver);

            lp.logout_session();

        } catch (Exception e) {

            /*
             * Logout is cleanup.
             *
             * Do not allow a cleanup problem to hide
             * the actual test result.
             */
            System.out.println(
                    "Logout cleanup failed: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // SCREENSHOT
    // =========================================================

    public static String CaptureScreen(
            String tname
    ) throws IOException {

        String timeStamp =
                new SimpleDateFormat(
                        "yyyyMMddHHmmss"
                ).format(new Date());

        TakesScreenshot ts =
                (TakesScreenshot) driver;

        File sourceFile =
                ts.getScreenshotAs(
                        OutputType.FILE
                );

        String targetFilePath =
                System.getProperty("user.dir")
                        + "\\screenshots\\"
                        + tname
                        + "_"
                        + timeStamp
                        + ".png";

        File targetFile =
                new File(targetFilePath);

        FileUtils.copyFile(
                sourceFile,
                targetFile
        );

        return targetFilePath;
    }
}
