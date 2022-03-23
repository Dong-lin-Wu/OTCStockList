package tw.over_the_counter_stock.model;

import java.sql.SQLException;
import java.util.List;

public interface StockDaoInterface {

	void createTable() throws SQLException;

	List<Stock> findAllData() throws SQLException;

	int InsertData(Stock s) throws SQLException;

	List<Stock> SelectData(String s) throws SQLException;

	void UpdateData(Stock s) throws SQLException;

	int DeleteData(String s) throws SQLException;

	List<Stock> SelectDataBySQL(String s) throws SQLException;

}