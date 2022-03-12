public class BlackScholesMertonBarrier {
    private double optionPrice;
    private double delta;
    private double gamma;
    private double vega;

    public BlackScholesMertonBarrier(double price, double strike, double barrier, double riskFreeRate, double volatility, double duration, String type) {        
        double d1, d2;

        d1=(Math.log(price/strike)+(riskFreeRate+volatility*volatility/2)*duration)/(volatility*Math.sqrt(duration));
        d2=d1-volatility*Math.sqrt(duration);
                
        if (type.equals("CallDownIn")) {
            double H = barrier*Math.exp(-0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H < strike) {
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = price*Math.pow(H/price, 2*g)*cdf(y) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(y - volatility*Math.sqrt(duration));                
            }
            else {
                double c = price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = c - (price*cdf(x1) - strike*Math.exp(-riskFreeRate*duration)*cdf(x1 - volatility*Math.sqrt(duration)) - price*Math.pow(H/price, 2*g)*cdf(y1) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(y1 - volatility*Math.sqrt(duration)));
            }
        }        
        else if (type.equals("CallDownOut")) {
            double H = barrier*Math.exp(-0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H < strike) {
                double c = price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = c - (price*Math.pow(H/price, 2*g)*cdf(y) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(y - volatility*Math.sqrt(duration)));                
            }
            else {                
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = price*cdf(x1) - strike*Math.exp(-riskFreeRate*duration)*cdf(x1 - volatility*Math.sqrt(duration)) - price*Math.pow(H/price, 2*g)*cdf(y1) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(y1 - volatility*Math.sqrt(duration));
            }
        }
        else if (type.equals("CallUpIn")) {
            double H = barrier*Math.exp(0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H > strike) {
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = price*cdf(x1) - strike*Math.exp(-riskFreeRate*duration)*cdf(x1 - volatility*Math.sqrt(duration)) - price*Math.pow(H/price, 2*g)*(cdf(-y) - cdf(-y1)) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*(cdf(-y + volatility*Math.sqrt(duration)) - cdf(-y1 + volatility*Math.sqrt(duration)));
            }
            else {                
                this.optionPrice = price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);                
            }
        }
        else if (type.equals("CallUpOut")) {
            double H = barrier*Math.exp(0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H > strike) {
                double c = price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = c - (price*cdf(x1) - strike*Math.exp(-riskFreeRate*duration)*cdf(x1 - volatility*Math.sqrt(duration)) - price*Math.pow(H/price, 2*g)*(cdf(-y) - cdf(-y1)) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*(cdf(-y + volatility*Math.sqrt(duration)) - cdf(-y1 + volatility*Math.sqrt(duration))));
            }
            else {                
                this.optionPrice = 0;                
            }            
        }

        else if (type.equals("PutDownIn")) {
            double H = barrier*Math.exp(-0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H > strike) {
                this.optionPrice = strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
            }
            else {                
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = -price*cdf(-x1) + strike*Math.exp(-riskFreeRate*duration)*cdf(-x1 + volatility*Math.sqrt(duration)) + price*Math.pow(H/price, 2*g)*(cdf(y) - cdf(y1)) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*(cdf(y - volatility*Math.sqrt(duration)) - cdf(y1 - volatility*Math.sqrt(duration)));
            }            
        }
        else if (type.equals("PutDownOut")) {
            double H = barrier*Math.exp(-0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H > strike) {
                this.optionPrice = 0;
            }
            else {
                double p = strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);                
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = p - (-price*cdf(-x1) + strike*Math.exp(-riskFreeRate*duration)*cdf(-x1 + volatility*Math.sqrt(duration)) + price*Math.pow(H/price, 2*g)*(cdf(y) - cdf(y1)) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*(cdf(y - volatility*Math.sqrt(duration)) - cdf(y1 - volatility*Math.sqrt(duration))));
            }           
        }
        else if (type.equals("PutUpIn")) {
            double H = barrier*Math.exp(0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H < strike) {
                double p = strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = p - (-price*cdf(-x1) + strike*Math.exp(-riskFreeRate*duration)*cdf(-x1 + volatility*Math.sqrt(duration)) + price*Math.pow(H/price, 2*g)*cdf(-y1) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(-y1 + volatility*Math.sqrt(duration)));

            }
            else {
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = -price*Math.pow(H/price, 2*g)*cdf(-y) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(-y + volatility*Math.sqrt(duration));                
            }
        }
        else if (type.equals("PutUpOut")) {
            double H = barrier*Math.exp(0.5826*volatility*Math.sqrt(1.0/365.0));
            if (H < strike) {
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double x1 = Math.log(price/H)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                double y1 = Math.log(H/price)/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = -price*cdf(-x1) + strike*Math.exp(-riskFreeRate*duration)*cdf(-x1 + volatility*Math.sqrt(duration)) + price*Math.pow(H/price, 2*g)*cdf(-y1) - strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(-y1 + volatility*Math.sqrt(duration));
            }
            else {
                double p = strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
                double g = riskFreeRate/(volatility*volatility) + 0.5;
                double y = Math.log(H*H/(price*strike))/(volatility*Math.sqrt(duration)) + g*volatility*Math.sqrt(duration);
                this.optionPrice = p - (-price*Math.pow(H/price, 2*g)*cdf(-y) + strike*Math.exp(-riskFreeRate*duration)*Math.pow(H/price, 2*g - 2)*cdf(-y + volatility*Math.sqrt(duration)));                
            }
        }        
    }

    public double GetPrice() {
        return this.optionPrice;
    }

    public double GetDelta() {
        return this.delta;
    }

    public double GetGamma() {
        return this.gamma;
    }

    public double GetVega() {
        return this.vega;
    }
    
    private double cdf(double x) {
        double sqrtpi=1.7724538509055160272981674833411;
        double sqrt2=1.4142135623730950488016887242097;
        double sum=0.0;
        int n=0;
        double z=x/sqrt2;
        double a=z;
        double erf;
        double aa=10;

        while (aa>1e-20) {
            sum+=a;
            n++;
            a*=-z*z/(n*(2*n+1.0)/(2*n-1.0));
            aa=(a<0)?-a:a;            
        }
        sum+=a;
        erf=sum/sqrtpi;

        return (0.5+erf);
    }

    private double pdf(double x) {
        return Math.exp(-x*x/2)/2.506628274631000502415765284811;
    }
    
}
