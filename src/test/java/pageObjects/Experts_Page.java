package pageObjects;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Experts_Page extends BasePage {

    private final WebDriverWait wait;

    public Experts_Page(WebDriver driver) {
        super(driver);

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    // =========================================================
    // CATEGORY
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='category']")
    private WebElement clk_category;

    private final By careerOption =
            By.xpath(
                    "//mat-option[.//span[normalize-space()='Career']]"
            );


    // =========================================================
    // LANGUAGE
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='language']")
    private WebElement clk_language;

    private final By hindiOption =
            By.xpath(
                    "//mat-option[.//span[normalize-space()='Hindi']]"
            );


    // =========================================================
    // MODE
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='mode']")
    private WebElement clk_mode;

    private final By videoOption =
            By.xpath(
                    "//mat-option[.//span[normalize-space()='Video']]"
            );


    // =========================================================
    // GENDER
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='gender']")
    private WebElement clk_gender;

    private final By femaleOption =
            By.xpath(
                    "//mat-option[.//span[normalize-space()='Female']]"
            );


    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    @FindBy(xpath = "//span[normalize-space()='BOOK APPOINTMENT']")
    private WebElement clk_bookAppoinment;


    // =========================================================
    // SELECT CATEGORY
    // =========================================================

    public void select_category() {

        System.out.println(
                "================================================="
        );

        System.out.println(
                "STARTING CATEGORY SELECTION"
        );

        System.out.println(
                "================================================="
        );


        // -----------------------------------------------------
        // Print browser window size
        // -----------------------------------------------------

        try {

            Dimension size =
                    driver.manage().window().getSize();

            System.out.println(
                    "Browser window size: " + size
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to get browser window size: "
                            + e.getMessage()
            );
        }


        // -----------------------------------------------------
        // Wait for category dropdown
        // -----------------------------------------------------

        System.out.println(
                "Waiting for category dropdown..."
        );

        wait.until(
                ExpectedConditions.visibilityOf(clk_category)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(clk_category)
        );


        // -----------------------------------------------------
        // Click category dropdown
        // -----------------------------------------------------

        System.out.println(
                "Clicking category dropdown..."
        );

        clk_category.click();


        System.out.println(
                "CATEGORY DROPDOWN CLICKED"
        );


        // -----------------------------------------------------
        // Check aria-expanded
        // -----------------------------------------------------

        try {

            String expanded =
                    clk_category.getAttribute(
                            "aria-expanded"
                    );

            System.out.println(
                    "Category aria-expanded = "
                            + expanded
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to read aria-expanded: "
                            + e.getMessage()
            );
        }


        // -----------------------------------------------------
        // Take screenshot after opening dropdown
        // -----------------------------------------------------

        takeScreenshot(
                "category-dropdown-opened"
        );


        // -----------------------------------------------------
        // Inspect CDK overlay
        // -----------------------------------------------------

        System.out.println(
                "Checking Angular CDK overlay..."
        );


        List<WebElement> overlayContainers =
                driver.findElements(
                        By.cssSelector(
                                ".cdk-overlay-container"
                        )
                );

        System.out.println(
                "CDK overlay container count = "
                        + overlayContainers.size()
        );


        List<WebElement> overlayPanes =
                driver.findElements(
                        By.cssSelector(
                                ".cdk-overlay-pane"
                        )
                );

        System.out.println(
                "CDK overlay pane count = "
                        + overlayPanes.size()
        );


        // -----------------------------------------------------
        // Check mat-option elements
        // -----------------------------------------------------

        List<WebElement> options =
                driver.findElements(
                        By.cssSelector(
                                "mat-option"
                        )
                );

        System.out.println(
                "MAT OPTION COUNT = "
                        + options.size()
        );


        if (options.isEmpty()) {

            System.out.println(
                    "WARNING: NO MAT-OPTION ELEMENTS FOUND!"
            );

        } else {

            System.out.println(
                    "AVAILABLE CATEGORY OPTIONS:"
            );

            for (WebElement option : options) {

                try {

                    String text =
                            option.getText().trim();

                    System.out.println(
                            "OPTION = [" + text + "]"
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Unable to read option text: "
                                    + e.getMessage()
                    );
                }
            }
        }


        // -----------------------------------------------------
        // Check overlay options specifically
        // -----------------------------------------------------

        List<WebElement> overlayOptions =
                driver.findElements(
                        By.cssSelector(
                                ".cdk-overlay-container mat-option"
                        )
                );

        System.out.println(
                "VISIBLE OVERLAY OPTIONS = "
                        + overlayOptions.size()
        );


        if (!overlayOptions.isEmpty()) {

            for (WebElement option : overlayOptions) {

                try {

                    String text =
                            option.getText().trim();

                    System.out.println(
                            "OVERLAY OPTION = ["
                                    + text
                                    + "]"
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Unable to read overlay option: "
                                    + e.getMessage()
                    );
                }
            }
        }


        // -----------------------------------------------------
        // Take another screenshot after DOM inspection
        // -----------------------------------------------------

        takeScreenshot(
                "category-options-inspection"
        );


        System.out.println(
                "================================================="
        );

        System.out.println(
                "WAITING FOR CAREER OPTION..."
        );

        System.out.println(
                "================================================="
        );


        // -----------------------------------------------------
        // Wait for Career
        // -----------------------------------------------------

        try {

            WebElement career =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    careerOption
                            )
                    );


            System.out.println(
                    "Career option FOUND."
            );


            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            career
                    )
            );


            career.click();


            System.out.println(
                    "Career option CLICKED successfully."
            );


        } catch (Exception e) {

            takeScreenshot(
                    "career-option-failure"
            );


            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "CAREER OPTION NOT FOUND"
            );

            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );

            System.out.println(
                    "Page title: "
                            + driver.getTitle()
            );

            System.out.println(
                    "aria-expanded: "
                            + clk_category.getAttribute(
                                    "aria-expanded"
                            )
            );

            System.out.println(
                    "================================================="
            );


            throw new RuntimeException(
                    "Career option was not found after "
                            + "opening category dropdown. "
                            + "MAT OPTION count = "
                            + driver.findElements(
                                    By.cssSelector("mat-option")
                            ).size(),
                    e
            );
        }
    }


    // =========================================================
    // SELECT LANGUAGE
    // =========================================================

    public void select_language() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_language
                )
        ).click();


        wait.until(
                ExpectedConditions.elementToBeClickable(
                        hindiOption
                )
        ).click();
    }


    // =========================================================
    // SELECT MODE
    // =========================================================

    public void select_mode() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_mode
                )
        ).click();


        wait.until(
                ExpectedConditions.elementToBeClickable(
                        videoOption
                )
        ).click();
    }


    // =========================================================
    // SELECT GENDER
    // =========================================================

    public void select_gender() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_gender
                )
        ).click();


        wait.until(
                ExpectedConditions.elementToBeClickable(
                        femaleOption
                )
        ).click();
    }


    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    public void c_bookappoinment() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_bookAppoinment
                )
        ).click();
    }


    // =========================================================
    // SCREENSHOT
    // =========================================================

    private void takeScreenshot(String name) {

        try {

            TakesScreenshot ts =
                    (TakesScreenshot) driver;


            File source =
                    ts.getScreenshotAs(
                            OutputType.FILE
                    );


            Path destination =
                    Paths.get(
                            "target",
                            "screenshots",
                            name + ".png"
                    );


            Files.createDirectories(
                    destination.getParent()
            );


            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );


            System.out.println(
                    "Screenshot saved: "
                            + destination.toAbsolutePath()
            );


        } catch (Exception e) {

            System.out.println(
                    "Unable to take screenshot: "
                            + e.getMessage()
            );
        }
    }
}
