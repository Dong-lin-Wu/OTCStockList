package tw.over_the_counter_stock.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class OutPutData {

	public static void writeToCSV(List<Stock> listStock) {
		try (FileOutputStream fos = new FileOutputStream("C:/stock/myCSV.csv");
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF8");
				BufferedWriter bw = new BufferedWriter(osw);) {

			bw.write(DownloadFile.FIRSTLINE);
			for (Stock s : listStock) {
				StringJoiner stringJoiner = new StringJoiner(",");
				stringJoiner.add(s.getDate()).add(s.getStockId()).add(s.getStockName())
						.add(Double.toString(s.getPriceToEarning_Ratio()))
						.add(Double.toString(s.getDividend_Per_Share())).add(Double.toString(s.getDividend_Yield()))
						.add(Double.toString(s.getPriceBook_Ratio()));

				bw.write(stringJoiner.toString());
				bw.flush();
				bw.write("\r\n");
			}

		} catch (IOException e) {
			System.out.println("資料匯出至CSV檔錯誤，請與系統管理員聯繫!");
		}
	}

	
	//Json
	private List<String> list = new ArrayList<String>();
	
	public OutPutData(List<Stock> listInput) {
		List<String> list = new ArrayList<>();
		list.add(DownloadFile.FIRSTLINE.replace("\r\n", ""));
		for (Stock s : listInput) {
			StringJoiner stringJoiner1 = new StringJoiner(",");
			stringJoiner1.add(s.getDate()).add(s.getStockId()).add(s.getStockName())
					.add(Double.toString(s.getPriceToEarning_Ratio())).add(Double.toString(s.getDividend_Per_Share()))
					.add(Double.toString(s.getDividend_Yield())).add(Double.toString(s.getPriceBook_Ratio()));
			list.add(stringJoiner1.toString());
		}
		this.list=list;
	}

	/**
	 * 獲取列數
	 */
	public int getRowNum() {
		return this.list.size();
	}

	/**
	 * 獲取行數
	 */
	public int getColNum() {
		if (!list.toString().equals("[]")) {
			if (list.get(0).toString().contains(",")) {// csv為逗號分隔文件
				return list.get(0).toString().split(",").length;
			} else if (list.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 獲取欄位資料
	 */
	public String getString(int row, int col) {
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			temp = list.get(row).toString().split(",")[col];
		} else if (colnum == 1) {
			temp = list.get(row).toString();
		} else {
			temp = null;
		}
		return temp;
	}
	
	/**
	 * 輸出Json檔案
	 */
	public static void writeToJson(List<Stock> listStock) {
		// 參考網站:https://blog.csdn.net/chengly0129/article/details/73550355
		try (FileOutputStream fos = new FileOutputStream("C:/stock/myJson.json");
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF8");
				BufferedWriter bw = new BufferedWriter(osw);) {

			OutPutData util = new OutPutData(listStock);
			int row = util.getRowNum();

			StringJoiner stringJoiner = new StringJoiner(",");
			for (int i = 1; i < row; i++) {
				List<Map<String,String>> list = new ArrayList<>();
				for (int j = 0; j < 7; j++) {
					Map<String, String> map = new HashMap<>();
					map.put(util.getString(0, j), util.getString(i, j));
					list.add(map);
				}
				stringJoiner.add((list.toString().replace("=", "\":\"").replace("{", "\"").replace("}", "\"")
						.replace("[", "{").replace("]", "}")));
			}
			//如果資料只有一筆，則前後不應有"[]"陣列符號
			if(row > 2) {
				bw.write("[" + stringJoiner.toString() + "]");	
			}else {
				bw.write(stringJoiner.toString());
			}
			bw.flush();
		} catch (Exception e) {
			System.out.println("資料匯出至Json檔錯誤，請與系統管理員聯繫!");
		}

	}

}
