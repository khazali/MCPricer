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

    public EuropeanOption() throws IncorrectContractType {
        System.out.print("Please enter the current underlying price: ");
        SetInitialStockPrice(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the strike price: ");
        SetStrike(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the risk free rate per year (fraction): ");
        SetRiskFreeRate(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the implied volatility per year (fraction): ");
        SetImpliedVolatility(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the time to maturity (years): ");
        SetDuration((int) (365.0*Double.parseDouble(System.console().readLine())));
        System.out.print("Please enter the contract type (Call | Put): ");        
        SetType(System.console().readLine());
    }

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
        this.duration = Days.daysBetween(this.valuationDate.toLocalDate(), this.maturityDate.toLocalDate()).getDays();
    }    

    public void SetMaturityDate(String maturityDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.maturityDate = formatter.parseDateTime(maturityDate);
        this.duration = Days.daysBetween(this.valuationDate.toLocalDate(), this.maturityDate.toLocalDate()).getDays();
    }    

    public void SetInitialStockPrice(double initialStockPrice) {
        this.initialStockPrice = initialStockPrice;
    }

    public double GetInitialStockPrice() {
        return this.initialStockPrice;
    }

    public void SetType(String type) throws IncorrectContractType {
        if (type.equals("Call") || type.equals("Put")) {
            this.type = type;
        } else {
            throw new IncorrectContractType("Incorrect contract type!");
        }
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

        if (this.type.equals("Call")) {
            profit = stockPrice[stockPrice.length-1]-this.strike;
        }
        else if (this.type.equals("Put")) {
            profit = this.strike-stockPrice[stockPrice.length-1];
        }
        
        if (profit < 0) this.price = 0;
        else this.price = Discount(profit, this.duration);

        return this.price;
    }


    public double Discount(double profit, double contractDuration) {        
        return profit*Math.exp(-this.riskFreeRate*contractDuration/365.0);
    }
}