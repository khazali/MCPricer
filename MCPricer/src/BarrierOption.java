import org.joda.time.*;
import org.joda.time.format.*;

public class BarrierOption implements FinancialInstrument {
    private double strike;
    private double riskFreeRate;
    private double impliedVolatility;
    private String type;
    private DateTime valuationDate = new DateTime();
    private DateTime maturityDate = new DateTime();
    private double initialStockPrice;
    private int duration;
    private double price;
    private double barrier;

    public BarrierOption() throws IncorrectContractType {
        System.out.print("Please enter the current underlying price: ");
        SetInitialStockPrice(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the strike price: ");
        SetStrike(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the barrier price: ");
        SetBarrier(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the risk free rate per year (fraction): ");
        SetRiskFreeRate(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the implied volatility per year (fraction): ");
        SetImpliedVolatility(Double.parseDouble(System.console().readLine()));
        System.out.print("Please enter the contract duration (years): ");
        SetDuration((int) (365.0*Double.parseDouble(System.console().readLine())));
        System.out.print("Please enter the contract+barrier type (CallUpIn | CallDownIn | CallUpOut | CallDownOut | PutUpIn | PutDownIn | PutUpOut | PutDownOut): ");        
        SetType(System.console().readLine());
    }

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
        if (type.equals("CallUpIn") || type.equals("CallDownIn") || type.equals("PutUpIn") || type.equals("PutDownIn") || type.equals("CallUpOut") || type.equals("CallDownOut") || type.equals("PutUpOut") || type.equals("PutDownOut")) {
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
        int i;
        boolean pass;

        if (this.type.equals("CallUpIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= this.barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - this.strike;
            }
        }
        else if (this.type.equals("CallDownIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= this.barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - this.strike;
            }
        }
        else if (this.type.equals("CallUpOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= this.barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - this.strike;
            }
        }
        else if (this.type.equals("CallDownOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= this.barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = stockPrice[stockPrice.length - 1] - this.strike;
            }
        }

        else if (this.type.equals("PutUpIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= this.barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = this.strike - stockPrice[stockPrice.length - 1];
            }
        }
        else if (this.type.equals("PutDownIn")) {
            pass = false;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= this.barrier) {
                    pass = true;
                    break;
                }
            }
                
            if (pass) {
                profit = this.strike - stockPrice[stockPrice.length - 1];
            }
        }
        else if (this.type.equals("PutUpOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] >= this.barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = this.strike - stockPrice[stockPrice.length - 1];
            }
        }
        else if (this.type.equals("PutDownOut")) {
            pass = true;
            for (i = 0; i < stockPrice.length; i++) {
                if (stockPrice[i] <= this.barrier) {
                    pass = false;
                    break;
                }
            }
                
            if (pass) {
                profit = this.strike - stockPrice[stockPrice.length - 1];
            }
        }

        if (profit < 0) this.price = 0;
        else this.price = Discount(profit, this.duration);

        return this.price;
    }


    public double Discount(double profit, double contractDuration) {        
        return profit*Math.exp(-this.riskFreeRate*contractDuration/365.0);
    }
    
}
