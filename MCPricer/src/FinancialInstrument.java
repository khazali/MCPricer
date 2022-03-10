import org.joda.time.*;

public interface FinancialInstrument {
    public double GetPrice();
    public void SetRiskFreeRate(double riskFreeRate);
    public double GetRiskFreeRate();
    public void SetImpliedVolatility(double impliedVolatility);
    public double GetImpliedVolatility();
    public void SetValuationDate(String valuationDate);
    public DateTime GetValuationDate();
    public void SetMaturityDate(String maturityDate);
    public DateTime GetMaturityDate();
    public void SetInitialStockPrice(double initialStockPrice);
    public double GetInitialStockPrice();
    public void SetType(String type) throws IncorrectContractType;
    public String GetType();
    public int GetDuration();



    public double CaculatePrice(double [] stockPrice);
    public void SetStrike(double strike);
    public double Discount(double profit);   
    
}