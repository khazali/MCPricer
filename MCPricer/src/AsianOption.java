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

    public AsianOption() throws IncorrectContractType, DurationNotDivisible {
        System.out.print("Please enter the current underlying price: ");
        SetInitialStockPrice(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the strike price: ");
        SetStrike(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the valuation interval (years): ");
        SetValuationInterval((int) (365.0*Double.parseDouble(System.console().readLine())));
        System.out.print("Please enter the risk free rate per year (fraction): ");
        SetRiskFreeRate(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the implied volatility per year (fraction): ");
        SetImpliedVolatility(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the time to maturity (years): ");
        SetDuration((int) (365.0*Double.parseDouble(System.console().readLine())));
        System.out.print("Please enter the contract+averaging type (CallGeometric | CallArithmetic | PutGeometric | PutArithmetic): ");        
        SetType(System.console().readLine());
    }

    public AsianOption(double initialStockPrice, double strike, double valuationInterval, double riskFreeRate, double impliedVolatility, String valuationDate, String maturityDate, String type) throws IncorrectContractType, DurationNotDivisible {
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetValuationDate(valuationDate);
        SetMaturityDate(maturityDate);        
        SetInitialStockPrice(initialStockPrice);
        SetValuationInterval(((int) Math.round(valuationInterval*365.0)));
    }

    public AsianOption(double initialStockPrice, double strike, double valuationInterval, double riskFreeRate, double impliedVolatility, double duration, String type) throws IncorrectContractType, DurationNotDivisible {
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetDuration(((int) Math.round(duration*365.0)));
        SetInitialStockPrice(initialStockPrice);
        SetValuationInterval(((int) Math.round(valuationInterval*365.0)));
    }

    public void SetValuationInterval(int valuationInterval) throws DurationNotDivisible {
        this.valuationInterval = valuationInterval;
        if (this.duration%this.valuationInterval != 0) {
            throw new DurationNotDivisible("Valuation interval must be a factor of the duration");
        }
        this.numberOfIntervals = this.duration/this.valuationInterval;
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
        if (type.equals("CallGeometric") || type.equals("CallArithmetic") || type.equals("PutGeometric") || type.equals("PutArithmetic")) {
            this.type = type;
        } else {
            throw new IncorrectContractType("Incorrect contract type");
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
        double s;
        int i;


        if (this.type.equals("CallGeometric")) {
            s = 1;
            for (i = 0; i < this.numberOfIntervals; i++) s*=stockPrice[(i + 1)*this.valuationInterval - 1];
            s=Math.pow(s, 1.0/this.numberOfIntervals);
            profit = s - this.strike;
        }
        else if (this.type.equals("CallArithmetic")) {
            s = 0;
            for (i = 0; i < this.numberOfIntervals; i++) s+=stockPrice[(i + 1)*this.valuationInterval - 1];
            s/=this.numberOfIntervals;
            profit = s - this.strike;
        }

        else if (this.type.equals("PutGeometric")) {
            s = 1;
            for (i = 0; i < this.numberOfIntervals; i++) s*=stockPrice[(i + 1)*this.valuationInterval - 1];
            s=Math.pow(s, 1.0/this.numberOfIntervals);
            profit = this.strike - s;
        }
        else if (this.type.equals("PutArithmetic")) {
            s = 0;
            for (i = 0; i < this.numberOfIntervals; i++) s+=stockPrice[(i + 1)*this.valuationInterval - 1];
            s/=this.numberOfIntervals;
            profit = this.strike - s;
        }

        if (profit < 0) this.price = 0;
        else this.price = Discount(profit, this.duration);

        return this.price;
    }


    public double Discount(double profit, double contractDuration) {        
        return profit*Math.exp(-this.riskFreeRate*contractDuration/365.0);
    }
}