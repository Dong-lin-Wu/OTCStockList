package tw.over_the_counter_stock.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tw.over_the_counter_stock.util.ConnectionFactotry;

public class StockDao implements StockDaoInterface {

	private Connection conn;

	public StockDao() {
		this.conn = ConnectionFactotry.getConnection();
	}
	
	@Override
	public void createTable() throws SQLException {

		String sql = "DROP TABLE IF EXISTS OTCStockList;create table OTCStockList( " + "id int identity(1,1) primary key not null, "
				+ "date date," + "stockId varchar(10) unique," + "stockName nvarchar(20),"
				+ "priceToEarning_Ratio decimal(10,2)," + "dividend_Per_Share decimal(10,2)," + "dividend_Yield decimal(10,2),"
				+ "priceBook_Ratio decimal(10,2))";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.execute();
		preState.close();
	}

	@Override
	public List<Stock> findAllData() throws SQLException {
		
		String sql = "select * from OTCStockList";
		PreparedStatement preState = conn.prepareStatement(sql);
		ResultSet rs = preState.executeQuery();
		
		List<Stock> list = new ArrayList<>();
		
		while (rs.next()) {
			Stock stock = new Stock();
			stock.setDate(rs.getString(2));
			stock.setStockId(rs.getString(3));
			stock.setStockName(rs.getString(4));
			stock.setPriceToEarning_Ratio(rs.getDouble(5));
			stock.setDividend_Per_Share(rs.getDouble(6));
			stock.setDividend_Yield(rs.getDouble(7));
			stock.setPriceBook_Ratio(rs.getDouble(8));
			list.add(stock);
		}
		rs.close();
		preState.close();
		return list;

	}
	
	@Override
	public int InsertData(Stock s) throws SQLException {
		String sql = "insert into OTCStockList (date, stockId, stockName, priceToEarning_Ratio, dividend_Per_Share, dividend_Yield, priceBook_Ratio) Values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preState = conn.prepareStatement(sql);
		StockDaoInterface dao = new StockDao();
		List<Stock> list = dao.SelectData(s.getStockId());
		if(list.size() == 0) {
			String date = s.getDate();
			int parseInt = Integer.parseInt(date);
			String string = Integer.toString(parseInt + 19110000);
			try {
				Date simpleDate = new SimpleDateFormat("yyyyMMdd").parse(string);
				java.sql.Date sqlDate = new java.sql.Date(simpleDate.getTime());
				preState.setDate(1, sqlDate);
			} catch (ParseException e) {
				System.out.println("SQLDate Wrong!");
				e.printStackTrace();
			}
			
			preState.setString(2, s.getStockId());
			preState.setString(3, s.getStockName());
			preState.setDouble(4, s.getPriceToEarning_Ratio());
			preState.setDouble(5, s.getDividend_Per_Share());
			preState.setDouble(6, s.getDividend_Yield());
			preState.setDouble(7, s.getPriceBook_Ratio());
			preState.execute();
			preState.close();
		}
		return list.size();
		
	
	}
	@Override
	public List<Stock> SelectData(String s) throws SQLException {
		String sql= "select * from OTCStockList where stockID = ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, s);
		ResultSet rs = preState.executeQuery();
		List<Stock> list = new ArrayList<>();
		
		while (rs.next()) {
			Stock stock = new Stock();
			stock.setDate(rs.getString(2));
			stock.setStockId(rs.getString(3));
			stock.setStockName(rs.getString(4));
			stock.setPriceToEarning_Ratio(rs.getDouble(5));
			stock.setDividend_Per_Share(rs.getDouble(6));
			stock.setDividend_Yield(rs.getDouble(7));
			stock.setPriceBook_Ratio(rs.getDouble(8));
			list.add(stock);
		}
		rs.close();
		preState.close();
		return list;
		
	}
	@Override
	public void UpdateData(Stock s) throws SQLException {
		String sql = "update OTCStockList set date = ? , priceToEarning_Ratio = ? , dividend_Per_Share = ? ,dividend_Yield = ? , priceBook_Ratio = ? where stockid = ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setDouble(2, s.getPriceToEarning_Ratio());
		preState.setDouble(3, s.getDividend_Per_Share());
		preState.setDouble(4, s.getDividend_Yield());
		preState.setDouble(5, s.getPriceBook_Ratio());
		preState.setString(6, s.getStockId());
		try {
			Date simpleDate = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
			java.sql.Date sqlDate = new java.sql.Date(simpleDate.getTime());
			preState.setDate(1, sqlDate);
			
		} catch (ParseException e) {
			System.out.println("獲取當下日期錯誤，請與系統管理員聯繫!");
		}
		
		preState.execute();
		preState.close();
		
	}
	@Override
	public int DeleteData(String s) throws SQLException {
		String sql = "delete OTCStockList where stockId = ? ";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, s);
		int count = preState.executeUpdate();
		preState.close();
		return count;
		
	}
	
	@Override
	public List<Stock> SelectDataBySQL(String s) throws SQLException {

		PreparedStatement preState = conn.prepareStatement(s);
		ResultSet rs = preState.executeQuery();
		List<Stock> list = new ArrayList<>();
		
		while (rs.next()) {
			Stock stock = new Stock();
			stock.setDate(rs.getString(2));
			stock.setStockId(rs.getString(3));
			stock.setStockName(rs.getString(4));
			stock.setPriceToEarning_Ratio(rs.getDouble(5));
			stock.setDividend_Per_Share(rs.getDouble(6));
			stock.setDividend_Yield(rs.getDouble(7));
			stock.setPriceBook_Ratio(rs.getDouble(8));
			list.add(stock);
		}
		rs.close();
		preState.close();
		return list;
	}
	
}
