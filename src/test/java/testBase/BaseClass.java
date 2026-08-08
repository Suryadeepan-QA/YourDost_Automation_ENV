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
import org.openqa.selenium.chrome.ChromeOptions;
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

        // -----------------------------------------------------
        // Load config.properties
        // -----------------------------------------------------

        FileReader file = new FileReader(
                "./src//test//resources//config.properties"
        );

        p = new Properties();

        p.load(file);

        file.close();

        // -----------------------------------------------------
        // Logger
        // -----------------------------------------------------

        logger = LogManager.getLogger(
                this.getClass()
        );

        // -----------------------------------------------------
        // Browser
        // -----------------------------------------------------

        switch (br.toLowerCase()) {

            // =================================================
            // CHROME
            // =================================================

            case "chrome":

                ChromeOptions chromeOptions =
                        new ChromeOptions();

                // Force desktop resolution
                chromeOptions.addArguments(
                        "--window-size=1920,1080"
                );

                chromeOptions.addArguments(
                        "--force-device-scale-factor=1"
                );

                chromeOptions.addArguments(
                        "--high-dpi-support=1"
                );

                chromeOptions.addArguments(
                        "--disable-extensions"
                );

                driver = new ChromeDriver(
                        chromeOptions
                );

                break;

            // =================================================
            // EDGE
            // =================================================

            case "edge":

                EdgeOptions edgeOptions =
                        new EdgeOptions();

                // IMPORTANT:
                // Do NOT use --start-maximized here.
                edgeOptions.addArguments(
                        "--window-size=1920,1080"
                );

                edgeOptions.addArguments(
                        "--force-device-scale-factor=1"
                );

                edgeOptions.addArguments(
                        "--high-dpi-support=1"
                );

                edgeOptions.addArguments(
                        "--disable-extensions"
                );

                driver = new EdgeDriver(
                        edgeOptions
                );

                break;

            // =================================================
            // FIREFOX
            // =================================================

            case "firefox":

                driver = new FirefoxDriver();

                break;

            // =================================================
            // INVALID BROWSER
            // =================================================

            default:

                throw new IllegalArgumentException(
                        "Invalid browser: " + br
                );
        }

        // -----------------------------------------------------
        // Delete cookies
        // -----------------------------------------------------

        driver.manage().deleteAllCookies();

        // -----------------------------------------------------
        // Set desktop resolution
        // -----------------------------------------------------

        driver.manage().window().setSize(
                new Dimension(1920, 1080)
        );

        // -----------------------------------------------------
        // Print actual browser size
        // -----------------------------------------------------

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "Requested browser size: 1920 x 1080"
        );

        System.out.println(
                "Actual browser size: "
                + driver.manage().window().getSize()
        );

        System.out.println(
                "==============================================\n"
        );

        // -----------------------------------------------------
        // Timeout
        // -----------------------------------------------------

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(5)
        );

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(60)
        );

        // -----------------------------------------------------
        // Open application
        // -----------------------------------------------------

        driver.get(
                p.getProperty("url")
        );

        System.out.println(
                "Application opened: "
                + driver.getCurrentUrl()
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

        return RandomStringUtils.randomAlphabetic(5);
    }

    // =========================================================
    // RANDOM NUMBER
    // =========================================================

    public String randomnumber() {

        return RandomStringUtils.randomNumeric(5);
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public void login()
            throws InterruptedException, IOException {

        System.out.println(
                "\n# STARTING LOGIN"
        );

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

        System.out.println(
                "# LOGIN SUBMITTED"
        );
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
             * Do not allow logout failure to hide
             * the original test result.
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
                        "yyyyMMdd_HHmmss"
                ).format(new Date());

        TakesScreenshot ts =
                (TakesScreenshot) driver;

        File sourceFile =
                ts.getScreenshotAs(
                        OutputType.FILE
                );

        // -----------------------------------------------------
        // Store screenshots inside target/screenshots
        // -----------------------------------------------------

        String targetDirectory =
                System.getProperty("user.dir")
                + File.separator
                + "target"
                + File.separator
                + "screenshots";

        File directory =
                new File(targetDirectory);

        if (!directory.exists()) {

            directory.mkdirs();
        }

        String targetFilePath =
                targetDirectory
                + File.separator
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

        System.out.println(
                "Screenshot saved: "
                + targetFilePath
        );

        return targetFilePath;
    }
}