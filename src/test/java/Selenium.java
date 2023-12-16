import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Selenium {

    // inicjalizacja drivera
    WebDriver driver;

    //inicjalizacja wait
    WebDriverWait wait;

    //start drivera
    @BeforeEach
    public void driverSetup(){
        //określenie o jaki driver chodzi i gdzie jest
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        // określenie implicite wait
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //określenie explicite wait
        wait = new WebDriverWait(driver,5);
    }

    // co ma się stać po teście
    @AfterEach
    public void driverClose(){
        //zamknięcie drivera i przeglądarki
        driver.quit();
    }

    @Test
    public void firstTest(){
        // przejście na strone
        driver.get("https://sdacademy.pl/");
        // pobranie tytułu i wydrukowanie
        System.out.println(driver.getTitle());
        // pobranie urla i wydrukowanie
        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public void secondTest(){
        driver.get("https://sdacademy.pl/");
        // przejście pod link
        driver.navigate().to("https://www.amazon.pl/");
        driver.navigate().back();
        // odświeżenie strony
        driver.navigate().refresh();
        driver.navigate().forward();
    }

    @Test
    public void thirdTest(){
        driver.get("https://skleptest.pl/");
        // selektor na 1 element
        WebElement searchInput = driver.findElement(By.id("search-field-top-bar"));
        WebElement email = driver.findElement(By.className("top-email"));
        WebElement nameInput = driver.findElement(By.name("es_txt_name"));

        // selektor na wiele elementów
        List<WebElement> elementList = driver.findElements(By.tagName("div"));
        System.out.println(elementList.size());
    }

    @Test
    public void firstTask(){
        driver.get("https://skleptest.pl/");
        WebElement searchInput = driver.findElement(By.cssSelector("#search-field-top-bar"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@type=\"submit\"]"));

        // clear czyści pole a send keys uzupełnia wartość w polu
        searchInput.clear();
        searchInput.sendKeys("super blog");
        searchButton.click();
        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public void secondTask(){
        driver.get("https://skleptest.pl/");
        WebElement nameInput = driver.findElement(By.id("es_txt_name"));
        WebElement emailInput = driver.findElement(By.cssSelector("#es_txt_email"));
        WebElement subscribeButton = driver.findElement(By.name("es_txt_button"));

        nameInput.clear();
        nameInput.sendKeys("name");

        emailInput.clear();
        emailInput.sendKeys("email");

        //click klika w guzior bądź inny element
        subscribeButton.click();
    }

    @Test
    public void thirdTask(){
        driver.get("https://skleptest.pl/");
        WebElement accountButton = driver.findElement(By.linkText("Account"));
        accountButton.click();
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.clear();
        usernameInput.sendKeys("test");
        WebElement loginButton = driver.findElement(By.xpath("//input[@name=\"login\"]"));
        loginButton.click();

        WebElement errorInfo = driver.findElement(By.className("woocommerce-error"));
        String errorMessage = errorInfo.getText();

        String occurredErrorMessage = "Error: The password field is empty.";
        // asercja sprawdza czy oczekiwany string jest równy z tym, który pobrany jest ze strony
        Assertions.assertEquals(occurredErrorMessage,errorMessage);
    }

    @Test
    public void ForthTest(){
        driver.get("https://skleptest.pl/");
        WebElement accountButton = driver.findElement(By.cssSelector(".top-account"));
        accountButton.click();
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.clear();
        passwordInput.sendKeys("test");
        WebElement loginButton = driver.findElement(By.xpath("//input[@name=\"login\"]"));
        loginButton.click();
        WebElement info = driver.findElement(By.cssSelector(".woocommerce-error"));
        Assertions.assertEquals("Error: Username is required.", info.getText());
    }

    @Test
    public void FifthTest(){
        driver.get("https://skleptest.pl/product/little-black-top/");
        WebElement quantity = driver.findElement(By.name("quantity"));
        quantity.clear();
        quantity.sendKeys("1");
        WebElement addToCart = driver.findElement(By.name("add-to-cart"));
        addToCart.click();
        WebElement myCartButton = driver.findElement(By.cssSelector(".top-cart"));
        myCartButton.click();
        WebElement quantityInCard = driver.findElement(By.cssSelector(".styled-number input"));
        quantityInCard.clear();
        quantityInCard.sendKeys("2");
        WebElement updateCartButton = driver.findElement(By.name("update_cart"));
        updateCartButton.click();

        WebElement proceedToCheckoutButton = driver.findElement(By.className("wc-proceed-to-checkout"));
        // czekamy na element aż będzie klikalny
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        Assertions.assertTrue(proceedToCheckoutButton.isEnabled());
    }
}
