import org.joda.time.*;
import org.joda.time.format.*;

public class BarrierOption implements FinancialInstrument {
    private double strike;
    private double riskFreeRate;
    private double impliedVolatility;
    private String type;
    private DateTime valuationDate=new DateTime();
    private DateTime maturityDate=new DateTime();
    private double initialStockPrice;
    private int duration;
    private double price;
    private double barrier;

    public BarrierOption(double initialStockPrice, double strike, double barrier, double riskFreeRate, double impliedVolatility, String valuationDate, String maturityDate, String type) throws IncorrectContractType{
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetValuationDate(valuationDate);
        SetMaturityDate(maturityDate);        
        SetInitialStockPrice(initialStockPrice);
        SetBarrier(barrier);
    }

    public BarrierOption(double initialStockPrice, double strike, double barrier, double riskFreeRate, double impliedVolatility, double duration, String type) throws IncorrectContractType{
        SetStrike(strike);
        SetRiskFreeRate(riskFreeRate);
        SetImpliedVolatility(impliedVolatility);
        SetType(type);
        SetDuration(((int) Math.round(duration*365.0)));
        SetInitialStockPrice(initialStockPrice);
        SetBarrier(barrier);
    }

    public double GetBarrier() {
        return this.barrier;
    }

    public void SetBarrier(double barrier) {
        this.barrier = barrier;
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

    public void SetType(String type) throws IncorrectContractType {
        if (type.equals("CallUpIn") || type.equals("CallDownIn") || type.equals("PutUpIn") || type.equals("PutDownIn") || type.equals("CallUpOut") || type.equals("CallDownOut") || type.equals("PutUpOut") || type.equals("PutDownOut")) {
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
        int i;
        boolean pass;

        if (type.equals("CallUpIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - strike;
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("CallDownIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - strike;
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("CallUpOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - strike;
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("CallDownOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - strike;
                if (profit < 0) profit = 0;
            }
        }

        else if (type.equals("PutUpIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = strike - stockPrice[stockPrice.length - 1];
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("PutDownIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = strike - stockPrice[stockPrice.length - 1];
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("PutUpOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = strike - stockPrice[stockPrice.length - 1];
                if (profit < 0) profit = 0;
            }
        }
        else if (type.equals("PutDownOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = strike - stockPrice[stockPrice.length - 1];
                if (profit < 0) profit = 0;
            }
        }


        Discount(profit);

        return this.price;
    }


    public double Discount(double profit) {
        this.price=profit*Math.exp(-this.riskFreeRate*this.duration/365.0);
        return this.price;
    }
    
}
