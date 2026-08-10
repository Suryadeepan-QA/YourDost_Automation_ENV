
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

        /*
         * Keep browser size consistent between local and GitHub Actions.
         */
        try {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } catch (Exception e) {
            System.out.println(
                    "Unable to set browser window size: " + e.getMessage()
            );
        }

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

    /*
     * Angular Material renders mat-option inside the CDK overlay.
     */
    private final By careerOption = By.xpath(
            "//div[contains(@class,'cdk-overlay-container')]"
            + "//mat-option[.//span[normalize-space()='Career'] "
            + "or normalize-space()='Career']"
    );

    private final By categoryOptions = By.cssSelector(
            ".cdk-overlay-container mat-option"
    );

    private final By categoryOverlay = By.cssSelector(
            ".cdk-overlay-container .cdk-overlay-pane"
    );

    /*
     * Angular Material backdrop.
     * This can remain on the page for a short time after clicking
     * an option and can intercept the next click.
     */
    private final By overlayBackdrop = By.cssSelector(
            ".cdk-overlay-backdrop"
    );

    // =========================================================
    // LANGUAGE
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='language']")
    private WebElement clk_language;

    private final By hindiOption = By.xpath(
            "//mat-option[.//span[normalize-space()='English'] "
            + "or normalize-space()='Hindi']"
    );

    // =========================================================
    // MODE
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='mode']")
    private WebElement clk_mode;

    private final By videoOption = By.xpath(
            "//mat-option[.//span[normalize-space()='Video'] "
            + "or normalize-space()='Video']"
    );

    // =========================================================
    // GENDER
    // =========================================================

    @FindBy(xpath = "//mat-select[@name='gender']")
    private WebElement clk_gender;

    private final By femaleOption = By.xpath(
            "//mat-option[.//span[normalize-space()='Female'] "
            + "or normalize-space()='Female']"
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

        System.out.println("=================================================");
        System.out.println("STARTING CATEGORY SELECTION");
        System.out.println("=================================================");

        // -----------------------------------------------------
        // Browser size
        // -----------------------------------------------------

        try {

            Dimension size = driver.manage().window().getSize();

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
        // Scroll category dropdown into view
        // -----------------------------------------------------

        try {

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            clk_category
                    );

        } catch (Exception e) {

            System.out.println(
                    "Unable to scroll category dropdown: "
                            + e.getMessage()
            );
        }

        // -----------------------------------------------------
        // Click category dropdown
        // -----------------------------------------------------

        System.out.println(
                "Clicking category dropdown..."
        );

        try {

            clk_category.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal click failed. Trying JavaScript click..."
            );

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            clk_category
                    );
        }

        System.out.println(
                "CATEGORY DROPDOWN CLICKED"
        );

        // -----------------------------------------------------
        // Wait until dropdown is open
        // -----------------------------------------------------

        try {

            wait.until(
                    ExpectedConditions.attributeToBe(
                            clk_category,
                            "aria-expanded",
                            "true"
                    )
            );

            System.out.println(
                    "Category dropdown is OPEN."
            );

        } catch (Exception e) {

            System.out.println(
                    "aria-expanded did not become true. "
                            + "Continuing with overlay detection."
            );
        }

        // -----------------------------------------------------
        // Print aria-expanded
        // -----------------------------------------------------

        try {

            String expanded =
                    clk_category.getAttribute("aria-expanded");

            System.out.println(
                    "Category aria-expanded = " + expanded
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to read aria-expanded: "
                            + e.getMessage()
            );
        }

        // -----------------------------------------------------
        // Screenshot after opening dropdown
        // -----------------------------------------------------

        takeScreenshot(
                "category-dropdown-opened"
        );

        // -----------------------------------------------------
        // Wait for Angular CDK overlay
        // -----------------------------------------------------

        System.out.println(
                "Waiting for Angular CDK overlay..."
        );

        try {

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(".cdk-overlay-container")
                    )
            );

            System.out.println(
                    "CDK overlay container FOUND."
            );

        } catch (Exception e) {

            System.out.println(
                    "CDK overlay container was not found."
            );
        }

        // -----------------------------------------------------
        // Wait for CDK overlay pane
        // -----------------------------------------------------

        try {

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            categoryOverlay
                    )
            );

            System.out.println(
                    "CDK overlay pane FOUND."
            );

        } catch (Exception e) {

            System.out.println(
                    "CDK overlay pane was not found."
            );
        }

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
        // Wait for category options
        // -----------------------------------------------------

        System.out.println(
                "Waiting for category options..."
        );

        try {

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            categoryOptions
                    )
            );

            System.out.println(
                    "Category options are present."
            );

        } catch (Exception e) {

            System.out.println(
                    "Category options were NOT found after "
                            + "initial dropdown click."
            );

            // -------------------------------------------------
            // Retry
            // -------------------------------------------------

            try {

                System.out.println(
                        "Trying to reopen category dropdown..."
                );

                String expanded =
                        clk_category.getAttribute(
                                "aria-expanded"
                        );

                if (!"true".equalsIgnoreCase(expanded)) {

                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    clk_category
                            )
                    ).click();

                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    categoryOptions
                            )
                    );
                }

            } catch (Exception retryException) {

                System.out.println(
                        "Retry also failed: "
                                + retryException.getMessage()
                );
            }
        }

        // -----------------------------------------------------
        // Check mat-option elements
        // -----------------------------------------------------

        List<WebElement> options =
                driver.findElements(
                        By.cssSelector(
                                ".cdk-overlay-container mat-option"
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
        // Screenshot after DOM inspection
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

            /*
             * IMPORTANT:
             *
             * Angular Material can keep the backdrop alive for a
             * short time after clicking an option.
             *
             * If the next dropdown is clicked immediately,
             * the backdrop can intercept the click.
             */
            waitForOverlayToDisappear();

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

            try {

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

            } catch (Exception diagnosticException) {

                System.out.println(
                        "Unable to collect diagnostic information: "
                                + diagnosticException.getMessage()
                );
            }

            System.out.println(
                    "CDK overlay options at failure = "
                            + driver.findElements(
                                    categoryOptions
                            ).size()
            );

            System.out.println(
                    "================================================="
            );

            throw new RuntimeException(
                    "Career option was not found after "
                            + "opening category dropdown. "
                            + "CDK overlay MAT OPTION count = "
                            + driver.findElements(
                                    categoryOptions
                            ).size(),
                    e
            );
        }
    }

    // =========================================================
    // WAIT FOR ANGULAR MATERIAL OVERLAY TO DISAPPEAR
    // =========================================================

    private void waitForOverlayToDisappear() {

        System.out.println(
                "Waiting for category overlay to disappear..."
        );

        try {

            /*
             * Wait for the actual backdrop to disappear.
             */
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            overlayBackdrop
                    )
            );

            System.out.println(
                    "Angular Material backdrop disappeared."
            );

        } catch (Exception e) {

            System.out.println(
                    "Backdrop did not disappear within the wait. "
                            + "Checking dropdown state..."
            );
        }

        /*
         * Also verify that the category select is closed.
         */
        try {

            wait.until(
                    ExpectedConditions.attributeToBe(
                            clk_category,
                            "aria-expanded",
                            "false"
                    )
            );

            System.out.println(
                    "Category dropdown is CLOSED."
            );

        } catch (Exception e) {

            System.out.println(
                    "Category aria-expanded did not become false."
            );
        }
    }

    // =========================================================
    // SELECT LANGUAGE
    // =========================================================

    public void select_language() {

        System.out.println(
                "================================================="
        );

        System.out.println(
                "STARTING LANGUAGE SELECTION"
        );

        System.out.println(
                "================================================="
        );

        /*
         * Make sure any previous Angular overlay is gone.
         */
        waitForAnyOverlayToDisappear();

        wait.until(
                ExpectedConditions.visibilityOf(
                        clk_language
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_language
                )
        );

        try {

            clk_language.click();

        } catch (Exception e) {

            System.out.println(
                    "Language normal click failed. "
                            + "Trying JavaScript click..."
            );

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            clk_language
                    );
        }

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        hindiOption
                )
        ).click();

        /*
         * Wait until Language overlay closes.
         */
        waitForAnyOverlayToDisappear();

        System.out.println(
                "Hindi language selected successfully."
        );
    }

    // =========================================================
    // SELECT MODE
    // =========================================================

    public void select_mode() {

        System.out.println(
                "================================================="
        );

        System.out.println(
                "STARTING MODE SELECTION"
        );

        System.out.println(
                "================================================="
        );

        /*
         * This is especially important in GitHub Actions.
         */
        waitForAnyOverlayToDisappear();

        wait.until(
                ExpectedConditions.visibilityOf(
                        clk_mode
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_mode
                )
        );

        try {

            clk_mode.click();

        } catch (Exception e) {

            System.out.println(
                    "Mode normal click failed. "
                            + "Trying JavaScript click..."
            );

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            clk_mode
                    );
        }

        System.out.println(
                "Mode dropdown opened."
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        videoOption
                )
        ).click();

        /*
         * Wait until Mode overlay closes.
         */
        waitForAnyOverlayToDisappear();

        System.out.println(
                "Video mode selected successfully."
        );
    }

    // =========================================================
    // SELECT GENDER
    // =========================================================

    public void select_gender() {

        System.out.println(
                "================================================="
        );

        System.out.println(
                "STARTING GENDER SELECTION"
        );

        System.out.println(
                "================================================="
        );

        /*
         * Make sure no previous Angular overlay is blocking
         * the Gender dropdown.
         */
        waitForAnyOverlayToDisappear();

        wait.until(
                ExpectedConditions.visibilityOf(
                        clk_gender
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clk_gender
                )
        );

        try {

            clk_gender.click();

        } catch (Exception e) {

            System.out.println(
                    "Gender normal click failed. "
                            + "Trying JavaScript click..."
            );

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            clk_gender
                    );
        }

        System.out.println(
                "Gender dropdown opened."
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        femaleOption
                )
        ).click();

        /*
         * Wait until Gender overlay closes.
         */
        waitForAnyOverlayToDisappear();

        System.out.println(
                "Female gender selected successfully."
        );
    }

    // =========================================================
    // WAIT FOR ANY ANGULAR OVERLAY
    // =========================================================

    private void waitForAnyOverlayToDisappear() {

        try {

            /*
             * Wait for backdrop to disappear if it exists.
             */
            List<WebElement> backdrops =
                    driver.findElements(
                            overlayBackdrop
                    );

            if (!backdrops.isEmpty()) {

                wait.until(
                        ExpectedConditions.invisibilityOfElementLocated(
                                overlayBackdrop
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Overlay backdrop check: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    public void c_bookappoinment() {

        System.out.println(
                "================================================="
        );

        System.out.println(
                "CLICKING BOOK APPOINTMENT"
        );

        System.out.println(
                "================================================="
        );

        /*
         * Make sure any Angular Material overlay/backdrop
         * from the previous dropdown is completely gone.
         */
        waitForAnyOverlayToDisappear();

        /*
         * Find the actual clickable button instead of clicking
         * directly on the span.
         */
        By bookAppointmentButton = By.xpath(
                "//span[normalize-space()='BOOK APPOINTMENT']"
                + "/ancestor::button[1]"
        );

        try {

            /*
             * Wait until the button exists.
             */
            WebElement button =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    bookAppointmentButton
                            )
                    );

            System.out.println(
                    "BOOK APPOINTMENT BUTTON FOUND."
            );

            /*
             * Scroll the button to the center of the viewport.
             */
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({"
                                    + "block:'center',"
                                    + "inline:'center'"
                                    + "});",
                            button
                    );

            System.out.println(
                    "BOOK APPOINTMENT BUTTON SCROLLED INTO VIEW."
            );

            /*
             * Wait until Selenium considers it clickable.
             */
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            button
                    )
            );

            System.out.println(
                    "BOOK APPOINTMENT BUTTON IS CLICKABLE."
            );

            /*
             * Normal Selenium click first.
             */
            try {

                button.click();

                System.out.println(
                        "BOOK APPOINTMENT CLICKED SUCCESSFULLY."
                );

            } catch (org.openqa.selenium.ElementClickInterceptedException e) {

                System.out.println(
                        "Normal click intercepted."
                );

                System.out.println(
                        "Trying JavaScript click..."
                );

                /*
                 * If another transparent/animated element is still
                 * covering the button in GitHub Actions, JS click
                 * bypasses the physical hit-test.
                 */
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                button
                        );

                System.out.println(
                        "BOOK APPOINTMENT CLICKED USING JAVASCRIPT."
                );
            }

        } catch (Exception e) {

            takeScreenshot(
                    "book-appointment-click-failure"
            );

            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "BOOK APPOINTMENT CLICK FAILED"
            );

            System.out.println(
                    "================================================="
            );

            try {

                System.out.println(
                        "Current URL: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Page title: "
                                + driver.getTitle()
                );

                System.out.println(
                        "Book Appointment buttons found: "
                                + driver.findElements(
                                        bookAppointmentButton
                                ).size()
                );

            } catch (Exception diagnosticException) {

                System.out.println(
                        "Unable to collect diagnostic information: "
                                + diagnosticException.getMessage()
                );
            }

            throw new RuntimeException(
                    "BOOK APPOINTMENT click failed.",
                    e
            );
        }
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

