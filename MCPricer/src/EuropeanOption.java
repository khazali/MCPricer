import org.joda.time.*;
import org.joda.time.format.*;

public class EuropeanOption implements FinancialInstrument {
    private double strike;
    private double riskFreeRate;
    private double impliedVolatility;
    private String type;
    private DateTime valuationDate=new DateTime();
    private DateTime maturityDate=new DateTime();
    private double initialStockPrice;
    private int duration;
    private double price;

    public EuropeanOption(double initialStockPrice, double strike, double riskFreeRate, double impliedVolatility, String valuationDate, String maturityDate, String type) throws IncorrectContractType{
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetValuationDate(valuationDate);
        SetMaturityDate(maturityDate);        
        SetInitialStockPrice(initialStockPrice);
    }

    public EuropeanOption(double initialStockPrice, double strike, double riskFreeRate, double impliedVolatility, double duration, String type) throws IncorrectContractType{
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetDuration(((int) Math.round(duration*365.0)));
        SetInitialStockPrice(initialStockPrice);
    }

    public double GetPrice() {
        return this.price;
    }

    public void SetRiskFreeRate(double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    public double GetRiskFreeRate() {
        return this.riskFreeRate;
    }

    public void SetImpliedVolatility(double impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public double GetImpliedVolatility() {
        return this.impliedVolatility;
    }

    public void SetValuationDate(String valuationDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.valuationDate = formatter.parseDateTime(valuationDate);
        this.duration = Days.daysBetween(this.valuationDate.toLocalDate(), this.maturityDate.toLocalDate()).getDays()+1;
    }

    public DateTime GetValuationDate() {       
        return this.valuationDate;
    }

    public void SetMaturityDate(String maturityDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.maturityDate = formatter.parseDateTime(maturityDate);
        this.duration = Days.daysBetween(this.valuationDate.toLocalDate(), this.maturityDate.toLocalDate()).getDays()+1;
    }

    public DateTime GetMaturityDate() {
        return this.maturityDate;
    }

    public void SetInitialStockPrice(double initialStockPrice) {
        this.initialStockPrice = initialStockPrice;
    }

    public double GetInitialStockPrice() {
        return this.initialStockPrice;
    }

    public void SetType(String type) throws IncorrectContractType{
        if (type.equals("Call") || type.equals("Put")) {
            this.type = type;
        } else {
            throw new IncorrectContractType();
        }
        this.type = type;
    }

    public String GetType() {
        return this.type;
    }

    public int GetDuration() {
        return this.duration;
    }

    public void SetDuration(int duration) {
        this.duration = duration;
    }

    public void SetStrike(double strike) {
        this.strike = strike;
    }

    
    public double CaculatePrice(double [] stockPrice) {
        double profit = 0;

        if (type.equals("Call")) {
            profit=stockPrice[stockPrice.length-1]-strike;
            if (profit<0) profit=0;            
        }
        else if (type.equals("Put")) {
            profit=strike-stockPrice[stockPrice.length-1];
            if (profit<0) profit=0;
        }

        Discount(profit);

        return this.price;

        //return profit;
    }


    public double Discount(double profit) {
        this.price=profit*Math.exp(-this.riskFreeRate*this.duration/365.0);
        return this.price;
    }
}