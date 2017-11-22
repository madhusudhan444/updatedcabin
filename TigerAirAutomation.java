package TigerAirAutomationProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.OBJ_ADAPTER;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TigerAirAutomation

{
	static WebDriver obj;

	public TigerAirAutomation(WebDriver obj) {

	}

	public static void main(String[] args) throws Exception {

		
		for (int a = 0; a < 3; a++) {

			String filePath = "E:\\Sellenium\\FrameWork\\TestData\\BookingDetails.xlsx";
			FileInputStream file = new FileInputStream(new File(filePath));

			XSSFWorkbook wrkBook = new XSSFWorkbook(file);
			XSSFSheet wrkSht = wrkBook.getSheetAt(a);

			HashMap<String, String> map = new HashMap<String, String>();

			for (int i = 1; i < 24; i++) {

				map.put(wrkSht.getRow(i).getCell(0).getStringCellValue(),
						wrkSht.getRow(i).getCell(1).getStringCellValue());

			}


			System.setProperty("webdriver.gecko.driver", "C:\\Users\\madhusudhan\\Desktop\\geckodriver.exe");
			WebDriver obj = new FirefoxDriver();

//			System.setProperty("webdriver.Chrome.driver", "C:\\Users\\madhusudhan\\Downloads\\chromedriver_win32(1)\\chromedriver.exe");
//			WebDriver obj = new ChromeDriver();

				
			
			
			obj.get("https://uatbooking.tigerair.com.au/TigerAirIBE3415/Booking/search");

			// Flight Search
			FlightSearch fSearch = new FlightSearch();
			fSearch.fltSearchRtn(obj, map);
//			fSearch.fltSearchOneway(obj, map);

			// Flight selection
			String fare = "light";
			String rtnFareVal = map.get("fareType");

			ReturnFareFltSelect rtnLtFr = new ReturnFareFltSelect();
			Thread.sleep(3000);

			if (rtnFareVal.equals(fare)) {
				rtnLtFr.RtnLtFr(obj);
			} else {
				rtnLtFr.RtnExpFr(obj);
			}

			Thread.sleep(3000);

			// Enter Pax Details
			PaxDetails paxDet1 = new PaxDetails();
			paxDet1.PasngrDet1(obj, map);

			// // insert test case validation-- If required at any point of application
			// String PaxName = obj.findElement(By.id("passengerSelet")).getText();
			// System.out.println("Pax Name is :"+PaxName);

			// Pax Contact details
			PaxContactDetails PaxCntDet = new PaxContactDetails();
			PaxCntDet.PaxCntDetails(obj, map);

			// Cabin+ Selection
			CabinPlus cabinSelect = new CabinPlus();
			cabinSelect.rtnCabinPls(obj);

			// Baggage Selection
			BaggageSelection SlctBag = new BaggageSelection();
			SlctBag.rtnSelectBaggage(obj);

			// SPorts Equipment
			SportsEquipments SprtsEquip = new SportsEquipments();
			SprtsEquip.OnePieceSportsEquip(obj);

			// Navigate to Seat Selection page
			obj.findElement(By.id("ContinueBtn")).click();

			// Seat Selection
			SeatSelection SelectSeat = new SeatSelection();
			SelectSeat.noSeatSelection(obj);
			Thread.sleep(9000);

			 // Select Meal
			 MealsSelection SelectMeal = new MealsSelection();
			 SelectMeal.rtnMealsSelect(obj);
			 Thread.sleep(2000);
			
			
			
			 // Select Queue JUmp
			 QueueJumpSelection SelectQjmp = new QueueJumpSelection();
			 SelectQjmp.rtnQueueJumpSelect(obj);
			 Thread.sleep(2000);
			
			
			
			 //Select Carbon Offset
			 CarbonOffsetSelection SelectCarbonOffset = new CarbonOffsetSelection();
			 SelectCarbonOffset.rtnCarbonOffsetSelection(obj);
			 Thread.sleep(2000);
			
			
			
			 //Select CarHire
			 CarHireSelection SelectCarHire = new CarHireSelection();
			 SelectCarHire.mSelectCarHire(obj);
			 Thread.sleep(2000);

			// //Select Hotels
			// HotelSelection SelectHotel = new HotelSelection();
			// SelectHotel.mHotelSelection(obj);
			// Thread.sleep(2000);

			 //Select Airport Parking
			 AirportParkingSelection SelectAirPortPaking = new AirportParkingSelection();
			 SelectAirPortPaking.mairportParkingSelect(obj);
			 Thread.sleep(2000);

			// Select Insurance
			InsuranceSelection SelectInsure = new InsuranceSelection();
			SelectInsure.yesInsurance(obj);
			Thread.sleep(2000);

			// Navigate to Checkout page
			obj.findElement(By.id("ContinueBtn")).click();
			Thread.sleep(2000);

			// Card FOP
			CardPayment visaCC = new CardPayment();
			visaCC.cardPay(obj, map);

			// #disclaimer-check
			obj.findElement(By.xpath("//*[@class='check']")).click();
			obj.findElement(By.xpath("//*[@class='check']")).click();
			obj.findElement(By.id("SubmitPaymentBtn")).click();

			Thread.sleep(20000);
			Thread.sleep(10000);

			obj.navigate().refresh();
			String PNR = obj.findElement(By.xpath("//*[@id=\"PNRNumber\"]/h2")).getText();

			
			System.out.println("______________________________________________________________");
			System.out.println("Booking Referenxe is : "+PNR);
			System.out.println("______________________________________________________________");
			
			
			
			
//			// adding xl file
//
//			String excelFileName = "E:\\Sellenium\\Test.xls";// name of excel file
//			String sheetName = "Sheet1";// name of sheet
//
//			HSSFWorkbook wb = new HSSFWorkbook();
//			HSSFSheet sheet = wb.createSheet(sheetName);
//
//			// iterating r number of rows
//			for (int r = a; r < a+1; r++) {
//				HSSFRow row = sheet.createRow(r);
//				HSSFCell cell = row.createCell(0);
//
//				cell.setCellValue(PNR);
//			}
//
//			FileOutputStream fileOut = new FileOutputStream(excelFileName);
//
//			// write this workbook to an Outputstream.
//			wb.write(fileOut);
//			fileOut.flush();
//			fileOut.close();

		}
	}

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();

// obj.switchTo().frame("frame").close();
// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();

// obj.findElement(By.id("rl__roktAppsBanner213659633")).click();
// obj = (FirefoxDriver) obj.switchTo().frame(null);
// obj.findElement(By.xpath("//div[3]/div/div/div[1]/div/div/a")).click();

// String parent = obj.getWindowHandle();
// obj.switchTo().window(parent);
// //obj.close();
// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();
//

// obj.switchTo().alert().dismiss();
//

// String parent = obj.getWindowHandle();
//
// Set<String> children = obj.getWindowHandles();
//
// String windowHandler = null;
//
// Iterator<String> itr = children.iterator();
//
// while(itr.hasNext ())
// {
//
// windowHandler = itr.next();
//
// if(!windowHandler.equals(parent))
// //if(!windowHandle.equals(parent))
// {
//
// obj.switchTo().window(windowHandler);
// Thread.sleep(1000);
//
// obj.close();
//
// }
//
// }

// Thread.sleep(2000);

// ROKTdailogClose roktmsg = new ROKTdailogClose();
// roktmsg.roktDailogClosing(obj);
//

// obj.findElement(By.id("ux_iagree_button")).click();
// obj.findElement(By.id("ux_smartsignup_layout_buttons_next")).click();

// Alert alrt = obj.switchTo().alert();
//
// alrt.dismiss();

// String frameID = "//*[@id=\"rl__widget";
// String regExp = "\\d{9}";
//
// Pattern p = Pattern (frameID + regExp);

// WebElement xPathUserList;
// xPathUserList.findElement(By.xpath(p));

// int size = obj.findElements(By.tagName("iframe")).size();
// System.out.println(size);
//
// Thread.sleep(5000);

// WebElement
// frame=obj.findElement(By.xpath("//iframe[contains(@class='wdHolder.*')]"));

// WebElement
// frame=obj.findElement(By.xpath("//iframe[contains(@id='rl__widget.*')]"));
// Thread.sleep(2000);
// obj.switchTo().frame(frame);

// Thread.sleep(2000);

// WebElement modal =
// obj.findElement(By.xpath("//div[contains(@class,'wdHolder')]"));
// WebElement radio =
// modal.findElement(By.xpath("//iframe[contains(@id='rl__widget.*')]"));
//
//
// obj.switchTo().frame(radio);

// obj.switchTo().parentFrame();

// obj.switchTo().frame(p);
// rl__widget212879716
// rl__widget511487027

// System.out.println("1");
//
// Thread.sleep(3000);
// obj.findElement(By.xpath("(//*[@class='ui_widget_close_button button close
// small'])[1]")).click();

//
// System.out.println("2");
//
// Thread.sleep(2000);
//
// obj.navigate().refresh();
//
//
// String PNR =
// obj.findElement(By.xpath("//*[@id=\"PNRNumber\"]/h2")).getText();
//
// System.out.println(PNR);
//
//
//
// map.put("PNRnumber",PNR);

// Label PNRdetail =new Label(22,2,PNR);
// wrkSht.addCell(PNRdetail);

//
//// adding xl file
//
// String excelFileName = "E:\\Sellenium\\Test.xls";//name of excel file
//
// String sheetName = "Sheet1";//name of sheet
//
// HSSFWorkbook wb = new HSSFWorkbook();
// HSSFSheet sheet = wb.createSheet(sheetName) ;
//
//// iterating r number of rows
// for (int r=1;r < 5; r++ )
// {
// HSSFRow row = sheet.createRow(r);
// HSSFCell cell = row.createCell(0);
//
// cell.setCellValue("Cell "+r+" "+r);
//
// //iterating c number of columns
//// for (int c=0;c < r; c++ )
//// {
//// HSSFCell cell = row.createCell(c);
////
//// cell.setCellValue("Cell "+r+" "+c);
//// }
// }
//
// FileOutputStream fileOut = new FileOutputStream(excelFileName);
//
//// write this workbook to an Outputstream.
// wb.write(fileOut);
// fileOut.flush();
// fileOut.close();
//

// }

// }
