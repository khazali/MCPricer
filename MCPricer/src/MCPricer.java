import org.joda.time.*;
import org.joda.time.format.*;




class MCPricer {
    private int numberOfSims;
    private double [] stockPrice;
    private double impliedVolatility;
    private double riskFreeRate;
    private DateTime valuationDate=new DateTime();
    private DateTime maturityDate=new DateTime();
    private double initialStockPrice;
    private int duration;

    /*
    public MCPricer() {
        numberOfSims=1000;
    }
    */

    public MCPricer(int N, String valuationDate, double initialStockPrice, double impliedVolatility, double riskFreeRate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        this.numberOfSims = N;
        this.valuationDate = formatter.parseDateTime(valuationDate);
        this.impliedVolatility = impliedVolatility;
        this.riskFreeRate = riskFreeRate;
        this.initialStockPrice = initialStockPrice;

        //maturityDate???
    }

    public void ConstructStockPrice() {
        double mu = this.riskFreeRate;      //Risk-neutral assumption
        double sigma = this.impliedVolatility;
        int i;
        double W = 0;
        double dt = 1.0/365.0;
        double sqrtdt = Math.sqrt(dt);
     

        duration = Days.daysBetween(valuationDate.toLocalDate(), maturityDate.toLocalDate()).getDays();
        stockPrice = new double[duration];

        for (i = 0; i < duration; i++) {
            W += DrawFromStdNormal()*sqrtdt;
            stockPrice[i] = initialStockPrice * Math.exp((mu - 0.5 * sigma * sigma) * (i*dt) + sigma * W);            //A year is considered as 365 days
        }


        


    }

    public double DrawFromStdNormal() {
        //change it later
        double r=0;
        return r;
    }
}