import java.util.*;



class MCPricer {
    private int numberOfSims;
    private double [] stockPrice;
    private double impliedVolatility;
    private double riskFreeRate;    
    private double initialStockPrice;
    private int duration;
    private Random rndn;
    private FinancialInstrument fi;
    private final double dt = 1.0/365.0;
    private final double sqrtdt = Math.sqrt(dt);    

    

    public MCPricer(int N, FinancialInstrument Instrument) {        
        this.numberOfSims = N;
        rndn=new Random(new Date().getTime());
        this.duration=Instrument.GetDuration();
        this.stockPrice=new double[this.duration];
        this.impliedVolatility=Instrument.GetImpliedVolatility();
        this.riskFreeRate=Instrument.GetRiskFreeRate() / 100;
        this.initialStockPrice=Instrument.GetInitialStockPrice();  
        stockPrice = new double[duration];
        stockPrice[0]=initialStockPrice;   
        fi=Instrument;   
    }

    public void ConstructStockPrice() {        
        int i;
        double W = 0;       

        for (i = 1; i <= duration; i++) {
            W += rndn.nextGaussian()*sqrtdt;
            stockPrice[i] = initialStockPrice * Math.exp((this.riskFreeRate - 0.5 * this.impliedVolatility * this.impliedVolatility) * (i*dt) + this.impliedVolatility * W);            //A year is considered as 365 days
        }
    }

    public double RunSimulation() {
        int i;
        double sum = 0;

        for (i = 0; i < numberOfSims; i++) {
            ConstructStockPrice();
            sum += fi.CaculatePrice(stockPrice);
        }

        return sum/numberOfSims;
    }
}