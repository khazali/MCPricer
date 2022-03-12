import org.joda.time.*;
import org.joda.time.format.*;

public class Autocallable implements FinancialInstrument {
    private double strike;
    private double riskFreeRate;
    private double impliedVolatility;
    private DateTime valuationDate=new DateTime();
    private DateTime maturityDate=new DateTime();
    private double initialStockPrice;
    private int duration;
    private double price;
    private double barrier;
    private double coupon;
    private int valuationInterval;
    private int numberOfIntervals;

    public Autocallable(double initialStockPrice, double strike, double barrier, double coupon, double valuationInterval, double riskFreeRate, double impliedVolatility, String valuationDate, String maturityDate) throws DurationNotDivisible {
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetValuationDate(valuationDate);
        SetMaturityDate(maturityDate);        
        SetInitialStockPrice(initialStockPrice);
        SetBarrier(barrier);
        SetValuationInterval(valuationInterval);
        SetCoupon(coupon);
    }

    public Autocallable(double initialStockPrice, double strike, double barrier, double coupon, double valuationInterval, double riskFreeRate, double impliedVolatility, double duration) throws DurationNotDivisible {
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetDuration(((int) Math.round(duration*365.0)));
        SetInitialStockPrice(initialStockPrice);
        SetBarrier(barrier);
        SetValuationInterval(valuationInterval);
        SetCoupon(coupon);
    }
    
    public void SetCoupon(double coupon) {
        this.coupon = coupon;
    }

    public void SetBarrier(double barrier) {
        this.barrier = barrier;
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
        double profit;
        int i;       
       
        for (i = 0; i < numberOfIntervals; i++) {
            if (stockPrice[(i + 1)*this.valuationInterval - 1] >= this.strike) {          //Autocall
                profit = (1 + (i + 1)*this.coupon*this.valuationInterval/365.0)*this.initialStockPrice;
                this.price = Discount(profit, (i + 1)*this.valuationInterval);
                return this.price;
            }            
        }

        if (stockPrice[stockPrice.length - 1] >= this.barrier) {
            this.price = Discount(this.initialStockPrice, this.duration);
        }
        else {
            profit=this.initialStockPrice - (this.barrier - stockPrice[stockPrice.length - 1]);
            this.price = Discount(profit, this.duration);
        }
        
        return this.price;
    }


    public double Discount(double profit, double contractDuration) {        
        return profit*Math.exp(-this.riskFreeRate*contractDuration/365.0);
    }
    
}