import org.joda.time.*;
import org.joda.time.format.*;

public class AsianOption implements FinancialInstrument {
    private double strike;
    private double riskFreeRate;
    private double impliedVolatility;
    private String type;
    private DateTime valuationDate=new DateTime();
    private DateTime maturityDate=new DateTime();
    private double initialStockPrice;
    private int duration;
    private double price;
    private int valuationInterval;
    private int numberOfIntervals;

    public AsianOption(double initialStockPrice, double strike, double valuationInterval, double riskFreeRate, double impliedVolatility, String valuationDate, String maturityDate, String type) throws IncorrectContractType, DurationNotDivisible{
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetValuationDate(valuationDate);
        SetMaturityDate(maturityDate);        
        SetInitialStockPrice(initialStockPrice);
        SetValuationInterval(valuationInterval);
    }

    public AsianOption(double initialStockPrice, double strike, double valuationInterval, double riskFreeRate, double impliedVolatility, double duration, String type) throws IncorrectContractType, DurationNotDivisible {
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetDuration(((int) Math.round(duration*365.0)));
        SetInitialStockPrice(initialStockPrice);
        SetValuationInterval(valuationInterval);
    }

    public void SetValuationInterval(double valuationInterval) throws DurationNotDivisible {
        this.valuationInterval = (int) Math.round(365.0*valuationInterval);
        if (this.duration%this.valuationInterval != 0) {
            throw new DurationNotDivisible("Valuation interval must be a factor of the duration");
        }
        numberOfIntervals=this.duration/this.valuationInterval;
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

    public DateTime GetValuationDate() {       
        return this.valuationDate;
    }

    public void SetMaturityDate(String maturityDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.maturityDate = formatter.parseDateTime(maturityDate);
        this.duration = Days.daysBetween(this.valuationDate.toLocalDate(), this.maturityDate.toLocalDate()).getDays();
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

    public void SetType(String type) throws IncorrectContractType {
        if (type.equals("CallGeometric") || type.equals("CallArithmetic") || type.equals("PutGeometric") || type.equals("PutArithmetic")) {
            this.type = type;
        } else {
            throw new IncorrectContractType("Incorrect contract type");
        }
        //this.type = type;
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
        double s;
        int i;


        if (type.equals("CallGeometric")) {
            s = 1;
            for (i = 0; i < numberOfIntervals; i++) s*=stockPrice[(i + 1)*valuationInterval - 1];
            s=Math.pow(s, 1.0/numberOfIntervals);
            profit = s - strike;
        }
        else if (type.equals("CallArithmetic")) {
            s = 0;
            for (i = 0; i < numberOfIntervals; i++) s+=stockPrice[(i + 1)*valuationInterval - 1];
            s/=numberOfIntervals;
            profit = s - strike;
        }

        else if (type.equals("PutGeometric")) {
            s = 1;
            for (i = 0; i < numberOfIntervals; i++) s*=stockPrice[(i + 1)*valuationInterval - 1];
            s=Math.pow(s, 1.0/numberOfIntervals);
            profit = strike - s;
        }
        else if (type.equals("PutArithmetic")) {
            s = 0;
            for (i = 0; i < numberOfIntervals; i++) s+=stockPrice[(i + 1)*valuationInterval - 1];
            s/=numberOfIntervals;
            profit = strike - s;
        }

        if (profit < 0) profit = 0;
        Discount(profit);

        return this.price;
    }


    public double Discount(double profit) {
        this.price=profit*Math.exp(-this.riskFreeRate*this.duration/365.0);
        return this.price;
    }
}