package TigerAirAutomationProject;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CabinPlus {

	public void rtnCabinPls(WebDriver obj) throws Exception 
	
	{
		// TODO Auto-generated method stub

//		obj.findElement(By.id("cabin_plus_0_0")).click();
//		obj.findElement(By.id("cabin_plus_0_1")).click();
	
		Thread.sleep(1000);
		obj.findElement(By.xpath("//*[@id=\"passengerCabin-0\"]/div/div[2]/div[1]/div/div[3]")).click();
		Thread.sleep(1000);
		obj.findElement(By.xpath("//*[@id=\"passengerCabin-0\"]/div/div[2]/div[2]/div/div[3]")).click();

		
		
		
	}

	public void oneWayCabinPls(WebDriver obj) 
	
	{
		// TODO Auto-generated method stub

		obj.findElement(By.id("cabin_plus_0_0")).click();
	
	}
	
	
	
	
	
}
