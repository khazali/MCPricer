public interface FinancialInstrument {
    public double GetPrice();
    public void SetRiskFreeRate(double riskFreeRate);
    public double GetRiskFreeRate();
    public void SetImpliedVolatility(double impliedVolatility);
    public double GetImpliedVolatility();
    public void SetValuationDate(String valuationDate);
    public void SetMaturityDate(String maturityDate);
    public void SetInitialStockPrice(double initialStockPrice);
    public double GetInitialStockPrice();
    public int GetDuration();
    public void SetDuration(int duration);
    public double CaculatePrice(double [] stockPrice);
    public void SetStrike(double strike);
    public double Discount(double profit, double contractDuration);    
}