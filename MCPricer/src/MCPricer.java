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
    private double FPrice=-1.0e307;
    private double delta;
    private double gamma;
    private double vega;
    private final double Peps = 0.02;
    private final double Veps = 0.02;  
    private boolean isSimulated=false;   

    

    public MCPricer(int N, FinancialInstrument Instrument) {        
        this.numberOfSims = N;
        rndn=new Random(new Date().getTime());
        this.duration=Instrument.GetDuration();
        this.stockPrice=new double[this.duration+1];
        this.impliedVolatility=Instrument.GetImpliedVolatility();
        this.riskFreeRate=Instrument.GetRiskFreeRate();
        this.initialStockPrice=Instrument.GetInitialStockPrice();          
        this.fi=Instrument;        
    }

    public void ConstructStockPrice() {        
        int i;
        double W = 0;       

        for (i = 1; i <= duration; i++) {
            W += rndn.nextGaussian()*sqrtdt;
            this.stockPrice[i] = initialStockPrice * Math.exp((this.riskFreeRate - 0.5 * this.impliedVolatility * this.impliedVolatility) * (i*dt) + this.impliedVolatility * W);            //A year is considered as 365 days
        }
    }

    public double RunSimulation() {
        int i;
        double sum = 0;

        isSimulated=true;
        this.stockPrice[0]=initialStockPrice;

        for (i = 0; i < numberOfSims; i++) {
            ConstructStockPrice();
            sum += this.fi.CaculatePrice(stockPrice);
        }

        this.FPrice = sum/numberOfSims;
        return this.FPrice;
    }

    public double GetVega() {
        return this.vega;
    }

    public double GetGamma() {
        return this.gamma;
    }

    public double GetDelta() {
        return this.delta;
    }

    public double GetPrice() {
        return this.FPrice;
    }

    public void CalculateGreeks() {
        double FPlusD, FMinusD, F, vol, S0, CPeps, CVeps;

        CPeps=Peps*initialStockPrice;           //Scale epsilons
        CVeps=Veps*impliedVolatility;           //Scale epsilons

        if (isSimulated) F=this.FPrice;
        else F=RunSimulation();
        vol=this.impliedVolatility;
        S0=this.initialStockPrice;

        initialStockPrice=S0+CPeps;
        FPlusD=RunSimulation();
        initialStockPrice=S0-CPeps;
        FMinusD=RunSimulation();
        this.delta=(FPlusD-FMinusD)/(2*CPeps);                   //Central difference scheme
        this.gamma=(FPlusD+FMinusD-2*F)/(CPeps*CPeps);            //Central difference scheme

        this.initialStockPrice=S0;
        this.impliedVolatility=vol+CVeps;
        FPlusD=RunSimulation();
        this.impliedVolatility=vol-CVeps;
        FMinusD=RunSimulation();
        this.vega=(FPlusD-FMinusD)/(2*CVeps);                    //Central difference scheme



        //return to normal
        this.impliedVolatility=vol;        
        this.FPrice=F;        
    }
    
}