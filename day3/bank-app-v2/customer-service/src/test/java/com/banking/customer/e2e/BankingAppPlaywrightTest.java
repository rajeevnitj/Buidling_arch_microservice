package com.banking.customer.e2e;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BankingAppPlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void testCustomerLogin() {
        page.navigate("http://localhost:8090/login");
        
        page.fill("input[name='email']", "test@example.com");
        page.fill("input[name='password']", "password");
        page.click("button[type='submit']");
        
        page.waitForURL("**/dashboard");
        assertTrue(page.url().contains("dashboard"));
        
        String welcomeText = page.textContent(".welcome-message");
        assertTrue(welcomeText.contains("Welcome"));
    }

    @Test
    void testViewAccounts() {
        // Login first
        page.navigate("http://localhost:8090/login");
        page.fill("input[name='email']", "test@example.com");
        page.fill("input[name='password']", "password");
        page.click("button[type='submit']");
        
        page.waitForURL("**/dashboard");
        
        // Check if accounts are displayed
        page.waitForSelector(".account-list");
        int accountCount = page.locator(".account-item").count();
        assertTrue(accountCount > 0, "Should display at least one account");
    }

    @Test
    void testViewTransactions() {
        // Login and navigate to accounts
        page.navigate("http://localhost:8090/login");
        page.fill("input[name='email']", "test@example.com");
        page.fill("input[name='password']", "password");
        page.click("button[type='submit']");
        
        page.waitForURL("**/dashboard");
        
        // Click on first account
        page.click(".account-item:first-child");
        
        // Wait for transactions to load
        page.waitForSelector(".transaction-list");
        
        String transactionHeader = page.textContent("h2");
        assertTrue(transactionHeader.contains("Transactions"));
    }
}