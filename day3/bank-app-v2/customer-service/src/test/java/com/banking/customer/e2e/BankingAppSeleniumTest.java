package com.banking.customer.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankingAppSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testCustomerLoginFlow() {
        driver.get("http://localhost:8090/login");
        
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        emailField.sendKeys("test@example.com");
        passwordField.sendKeys("password");
        loginButton.click();
        
        wait.until(ExpectedConditions.urlContains("dashboard"));
        assertTrue(driver.getCurrentUrl().contains("dashboard"));
        
        WebElement welcomeMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("welcome-message")));
        assertTrue(welcomeMessage.getText().contains("Welcome"));
    }

    @Test
    void testAccountsDisplay() {
        // Login first
        driver.get("http://localhost:8090/login");
        driver.findElement(By.name("email")).sendKeys("test@example.com");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("dashboard"));
        
        // Verify accounts are displayed
        WebElement accountsList = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("account-list")));
        List<WebElement> accounts = accountsList.findElements(By.className("account-item"));
        
        assertFalse(accounts.isEmpty(), "Should display at least one account");
        
        // Verify account details are shown
        WebElement firstAccount = accounts.get(0);
        assertTrue(firstAccount.getText().contains("Account"));
        assertTrue(firstAccount.getText().contains("Balance"));
    }

    @Test
    void testTransactionView() {
        // Login and navigate to dashboard
        driver.get("http://localhost:8090/login");
        driver.findElement(By.name("email")).sendKeys("test@example.com");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("dashboard"));
        
        // Click on first account to view transactions
        WebElement firstAccount = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".account-item:first-child")));
        firstAccount.click();
        
        // Wait for transactions to load
        WebElement transactionsList = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("transaction-list")));
        
        // Verify transaction header
        WebElement header = driver.findElement(By.tagName("h2"));
        assertTrue(header.getText().contains("Transactions"));
        
        // Verify transactions are displayed (if any)
        List<WebElement> transactions = transactionsList.findElements(By.className("transaction-item"));
        // Note: This might be empty for new accounts, so we just check the structure exists
        assertNotNull(transactionsList);
    }

    @Test
    void testLogout() {
        // Login first
        driver.get("http://localhost:8090/login");
        driver.findElement(By.name("email")).sendKeys("test@example.com");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("dashboard"));
        
        // Click logout button
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("logout-btn")));
        logoutButton.click();
        
        // Verify redirected to login page
        wait.until(ExpectedConditions.urlContains("login"));
        assertTrue(driver.getCurrentUrl().contains("login"));
    }
}