package tw.over_the_counter_stock.model;

public class Stock {

	private int id;
	private String date;
	private String stockId;
	private String stockName;
	private double priceToEarning_Ratio;
	private double dividend_Per_Share;
	private double dividend_Yield;
	private double priceBook_Ratio;

	
	public Stock() {
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStockId() {
		return stockId;
	}


	public void setStockId(String stockId) {
		this.stockId = stockId;
	}


	public String getStockName() {
		return stockName;
	}


	public void setStockName(String stockName) {
		this.stockName = stockName;
	}


	public Double getPriceToEarning_Ratio() {
		return priceToEarning_Ratio;
	}


	public void setPriceToEarning_Ratio(Double priceToEarning_Ratio) {
		this.priceToEarning_Ratio = priceToEarning_Ratio;
	}


	public Double getDividend_Per_Share() {
		return dividend_Per_Share;
	}


	public void setDividend_Per_Share(Double eps) {
		this.dividend_Per_Share = eps;
	}


	public Double getDividend_Yield() {
		return dividend_Yield;
	}


	public void setDividend_Yield(Double dividend_Yield) {
		this.dividend_Yield = dividend_Yield;
	}


	public Double getPriceBook_Ratio() {
		return priceBook_Ratio;
	}


	public void setPriceBook_Ratio(Double priceBook_Ratio) {
		this.priceBook_Ratio = priceBook_Ratio;
	}
	
	
	
	
	

}
