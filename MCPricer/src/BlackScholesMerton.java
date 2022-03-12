public class BlackScholesMerton {
    private double optionPrice;
    private double delta;
    private double gamma;
    private double vega;

    public BlackScholesMerton(double price, double strike, double riskFreeRate, double volatility, double duration, String type) {
        double d1, d2;

        d1=(Math.log(price/strike)+(riskFreeRate+volatility*volatility/2)*duration)/(volatility*Math.sqrt(duration));
        d2=d1-volatility*Math.sqrt(duration);
        
        
        if (type.equals("Call")) {
            this.optionPrice = price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
            this.delta = cdf(d1);
            this.gamma = pdf(d1)/(price*volatility*Math.sqrt(duration));
            this.vega = price*Math.sqrt(duration)*pdf(d1);
        }
        else if (type.equals("Put")) {
            this.optionPrice = strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
            this.delta = cdf(d1)-1;
            this.gamma = pdf(d1)/(price*volatility*Math.sqrt(duration));
            this.vega = price*Math.sqrt(duration)*pdf(d1);
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
