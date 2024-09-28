package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.Base;

public class HospitalNames extends Base {

	By locatn = By.xpath("//input[@placeholder='Search location']");
	By place = By.xpath("//div[text()='Bangalore']");
	By hospital = By.xpath("//input[@data-input-box-id='omni-searchbox-keyword']");
	By search = By.xpath("//div[text()='Hospital']");
	By CB24x7 = By.xpath("//div[@data-qa-id='Open_24X7_checkbox']");
	By Filters = By.xpath("//span[text()='All Filters']");
	By Parking = By.xpath("//span[text()='Has Parking']");
	By Ratings = By.xpath("//span[@class='common__star-rating__value']");
	By HNames = By.xpath("//*[@data-qa-id='hospital_name']");
	By total = By.xpath("//span[@data-qa-id='results_count']");

	public void getHospitalNames() throws InterruptedException {

		logger = report.createTest("Finding Hospital Names");

		// To find the place Bangalore and select it
		try {
			WebElement location = findElement(locatn);
			location.clear();
			location.sendKeys("bangalore");
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(place));
			findElement(place).click();
			reportPass("Bangalore is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To find Hospitals and select it
		try {
			findElement(hospital).sendKeys("Hospital");
			wait.until(ExpectedConditions.visibilityOfElementLocated(search));
			findElement(search).click();
			reportPass("Hospital is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To select Open 24X7
		try {
			findElement(CB24x7).click();
			Thread.sleep(3000);
			reportPass("Open 24X7 is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To select Has Parking
		try {
			findElement(Filters).click();
			findElement(Parking).click();
			reportPass("Has Parking is selected Successfully");
			TimeUnit.SECONDS.sleep(4);
			Screenshoot("Hospitals");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// To find the names of the hospitals
		try {
			System.out.println("*************************************************************");
			System.out.println("            Hospital Names with ratings > 3.5 :");
			System.out.println("*************************************************************");
			int totalresults = Integer.parseInt(driver.findElement(total)
					.getText());
			int pages = totalresults / 10 + 1;
			for (int p = 2; p <= pages; p++) {
				List<WebElement> ratings = driver.findElements(Ratings);
				List<WebElement> hnames = driver.findElements(HNames);
				for (int i = 0; i < ratings.size(); i++) {
					float rate = Float.parseFloat(ratings.get(i).getText());
					if (rate > 3.5) {
						System.out.println(ratings.get(i).getText() + " - "
								+ hnames.get(i).getText());
					}
				}

				// Proceed to next Page
				findElement(
						By.xpath("//a[contains(@data-qa-id,'pagination_" + p
								+ "')]")).click();
				TimeUnit.SECONDS.sleep(1);
			}
			reportPass("All Hospital are obtained Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
