package tw.over_the_counter_stock.action;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import tw.over_the_counter_stock.model.DownloadFile;
import tw.over_the_counter_stock.model.OutPutData;
import tw.over_the_counter_stock.model.Stock;
import tw.over_the_counter_stock.model.StockDao;
import tw.over_the_counter_stock.model.StockDaoInterface;
import tw.over_the_counter_stock.util.ConnectionFactotry;

public class OTCStockAction {

	public static void main(String[] args) {
		StockDaoInterface dao = new StockDao();
		Stock stock = new Stock();
		try (Scanner scanner = new Scanner(System.in);) {
			dao.createTable();
			DownloadFile.Download();
			while (true) {
				System.out.println("---------------------------------------------------");
				System.out.println("請輸入號碼:(1.查詢 2.新增 3.修改 4.刪除 5.匯出所有資料 Q:離開)");
				String firstIn = scanner.nextLine();
				if (firstIn.equalsIgnoreCase("Q")) {
					break;
				}
				switch (firstIn) {
				case "1":
					System.out.println("1.股票代碼查詢 2.SQL指令查詢");
					String selectIn1 = scanner.nextLine();
					switch (selectIn1) {
					case "1":
						System.out.println("請輸入想查詢之股票代碼:");
						String selectIn2 = scanner.nextLine();
						List<Stock> list = dao.SelectData(selectIn2);
						if (list.size() == 0) {
							System.out.println("查無此資料!");
						} else {
							System.out.print(DownloadFile.FIRSTLINE);
							for (Stock s : list) {
								StringJoiner stringJoiner1 = new StringJoiner(",");
								stringJoiner1.add(s.getDate()).add(s.getStockId()).add(s.getStockName())
										.add(Double.toString(s.getPriceToEarning_Ratio()))
										.add(Double.toString(s.getDividend_Per_Share()))
										.add(Double.toString(s.getDividend_Yield()))
										.add(Double.toString(s.getPriceBook_Ratio()));
								System.out.println(stringJoiner1.toString());
							}
						}
						break;

					case "2":
						System.out.println("請輸入SQL指令:");
						String selectIn3 = scanner.nextLine();
						try {
							if (selectIn3.substring(0, 6).equalsIgnoreCase("select")) {
								if (selectIn3.contains(";")) {
									System.out.println("不可輸入;");
									break;
								} else {
									try {
										List<Stock> list1 = dao.SelectDataBySQL(selectIn3);
										if (list1.size() == 0) {
											System.out.println("查無此資料!");
										} else {
											System.out.print(DownloadFile.FIRSTLINE);
											for (Stock s : list1) {
												StringJoiner stringJoiner1 = new StringJoiner(",");
												stringJoiner1.add(s.getDate()).add(s.getStockId()).add(s.getStockName())
														.add(Double.toString(s.getPriceToEarning_Ratio()))
														.add(Double.toString(s.getDividend_Per_Share()))
														.add(Double.toString(s.getDividend_Yield()))
														.add(Double.toString(s.getPriceBook_Ratio()));
												System.out.println(stringJoiner1.toString());
											}
										}
										System.out.println("是否將資料輸出至檔案?(Y,N)");
										String selectIn4 = scanner.nextLine();
										if (selectIn4.equalsIgnoreCase("Y")) {
											System.out.println("請輸入匯出檔案格式:1.CSV 2.Json 3.兩種都要");
											String selectIn5 = scanner.nextLine();
											switch (selectIn5) {
											case "1":
												OutPutData.writeToCSV(list1);
												System.out.println("已將所有資料匯出至C:/stock/myCSV.csv");
												break;
											case "2":
												OutPutData.writeToJson(list1);
												System.out.println("已將所有資料匯出至C:/stock/myJson.json");
												break;
											case "3":
												OutPutData.writeToCSV(list1);
												OutPutData.writeToJson(list1);
												System.out.println("已將所有資料匯出至C:/stock資料夾中之myJson.json與myCSV.csv");
												break;
											default:
												System.out.println("輸入錯誤，請重新輸入!");
												break;
											}
											break;
										}
									} catch (SQLException e) {
										System.out.println("SQL指令輸入錯誤!");
									}
								}
							} else {
								System.out.println("查詢指令輸入錯誤!");
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("輸入長度過短!請以Select開頭。");
						}
						break;
					default:
						System.out.println("輸入錯誤，請重新輸入!");
						break;
					}
					break;

				case "2":
					System.out.println("請輸入想新增之股票代碼:");
					String insertIn1 = scanner.nextLine();
					List<Stock> list1 = dao.SelectData(insertIn1);
					if (list1.size() == 1) {
						System.out.println("已存在資料無法新增!");
						break;
					}
					try {
						stock.setStockId(insertIn1);
						stock.setDate(Integer.toString(Integer.parseInt(LocalDate.now().toString().replaceAll("-", "")) - 19110000));
						System.out.println("股票名稱:");
						String insertIn2 = scanner.nextLine();
						stock.setStockName(insertIn2);
						System.out.println("本益比:");
						String insertIn3 = scanner.nextLine();
						stock.setPriceToEarning_Ratio(Double.parseDouble(insertIn3));
						System.out.println("每股股利:");
						String insertIn4 = scanner.nextLine();
						stock.setDividend_Per_Share(Double.parseDouble(insertIn4));
						System.out.println("殖利率:");
						String insertIn5 = scanner.nextLine();
						stock.setDividend_Yield(Double.parseDouble(insertIn5));
						System.out.println("股價淨值比:");
						String insertIn6 = scanner.nextLine();
						stock.setPriceBook_Ratio(Double.parseDouble(insertIn6));
						dao.InsertData(stock);
						break;
					} catch (NumberFormatException e) {
						System.out.println("輸入格式錯誤!");
						break;
					} catch (SQLException e) {
						System.out.println("輸入格式錯誤!");
						break;
					}

				case "3":
					System.out.println("請輸入想更新之股票代碼:");
					String updateIn1 = scanner.nextLine();
					List<Stock> list2 = dao.SelectData(updateIn1);
					if (list2.size() == 0) {
						System.out.println("無資料可更新!");
						break;
					}
					try {
						stock.setStockId(updateIn1);
						System.out.println("本益比:");
						String updateIn2 = scanner.nextLine();
						stock.setPriceToEarning_Ratio(Double.parseDouble(updateIn2));
						System.out.println("每股股利:");
						String updateIn3 = scanner.nextLine();
						stock.setDividend_Per_Share(Double.parseDouble(updateIn3));
						System.out.println("殖利率:");
						String updateIn4 = scanner.nextLine();
						stock.setDividend_Yield(Double.parseDouble(updateIn4));
						System.out.println("股價淨值比:");
						String updateIn5 = scanner.nextLine();
						stock.setPriceBook_Ratio(Double.parseDouble(updateIn5));
						dao.UpdateData(stock);
						System.out.println("資料已更新!");
						break;
					} catch (NumberFormatException e) {
						System.out.println("輸入格式錯誤!");
						break;
					} catch (SQLException e) {
						System.out.println("輸入格式錯誤!");
						break;
					}

				case "4":
					System.out.println("請輸入想刪除之股票代碼:");
					String deleteIn = scanner.nextLine();
					int count = dao.DeleteData(deleteIn);
					if (count == 0) {
						System.out.println("無資料可刪除!");
					} else {
						System.out.println("資料已刪除!");
					}
					break;

				case "5":
					System.out.println("請輸入匯出檔案格式:1.CSV 2.Json 3.兩種都要");
					String outPut1 = scanner.nextLine();
					switch (outPut1) {
					case "1":
						OutPutData.writeToCSV(dao.findAllData());
						System.out.println("已將所有資料匯出至C:/stock/myCSV.csv");
						break;
					case "2":
						OutPutData.writeToJson(dao.findAllData());
						System.out.println("已將所有資料匯出至C:/stock/myJson.json");
						break;
					case "3":
						OutPutData.writeToCSV(dao.findAllData());
						OutPutData.writeToJson(dao.findAllData());
						System.out.println("已將所有資料匯出至C:/stock/資料夾中之myJson.json與myCSV.csv");
						break;
					default:
						System.out.println("輸入錯誤，請重新輸入!");
						break;
					}
					break;
				default:
					System.out.println("輸入錯誤，請重新輸入!");
					break;
				}

			}
		} catch (SQLException e) {
			System.out.println("Action Wrong!");
		}
		ConnectionFactotry.closeConnection();
		System.out.println("已結束，下次再見!");
	}
}
