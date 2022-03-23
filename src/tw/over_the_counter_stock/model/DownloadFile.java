package tw.over_the_counter_stock.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class DownloadFile {
	
	public static String FIRSTLINE;
	public static InputStream is;
	
	public static void Download() {
		try {
			try {
				URL url = new URL("https://www.tpex.org.tw/web/stock/aftertrading/peratio_analysis/pera_result.php?l=zh-tw&o=data");
				
				is = url.openStream();
				
			} catch (Exception e) {
				is = new FileInputStream("src/上櫃股票原始資料.csv");
			}
			
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String[] firstLine = br.readLine().replaceAll("\"", "").split(",");
			List<String> firstList = Arrays.asList(firstLine);
			StringJoiner sjFL = new StringJoiner(",");
			for (String s : firstList) {
				sjFL.add(s);
			}
			DownloadFile.FIRSTLINE=sjFL.toString()+"\r\n";

			String line;
			StockDaoInterface dao = new StockDao();
			Stock stock = new Stock();
			int count = 0;
			while ((line = br.readLine()) != null) {
				String[] list = line.replaceAll("\"", "").split(",");
				stock.setDate(list[0]);
				stock.setStockId(list[1]);
				stock.setStockName(list[2]);
				stock.setPriceToEarning_Ratio(list[3].equals("N/A")? 0 : Double.parseDouble(list[3]));
				stock.setDividend_Per_Share(Double.parseDouble(list[4]));
				stock.setDividend_Yield(Double.parseDouble(list[5]));
				stock.setPriceBook_Ratio(Double.parseDouble(list[6]));
				try {
					int num = dao.InsertData(stock);
					if(num == 0) {
						count++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("新增"+count + "筆!!!");
			br.close();
			isr.close();
			is.close();
			

		} catch (IOException e) {
			System.out.println("下載資料過程有誤，請與系統管理員聯繫!");
		} 
	}

}
