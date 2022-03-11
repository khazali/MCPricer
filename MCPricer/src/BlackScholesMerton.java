public class BlackScholesMerton {
    public BlackScholesMerton(double price, double strike, double riskFreeRate, double volatility, double duration, String type) {
        double d1, d2, C;

        d1=(Math.log(price/strike)+(riskFreeRate+volatility*volatility/2)*duration)/(volatility*Math.sqrt(duration));
        d2=d1-volatility*Math.sqrt(duration);
        
        
        if (type.equals("Call")) {
            C=price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
            System.out.println("Call Price: " + C);
        }
        else if (type.equals("Put")) {
            C=strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
            System.out.println("Put Price: " + C);
        }
        
    }


    public double cdf(double x) {
        double sqrtpi=1.7724538509055160272981674833411;
        double sqrt2=1.4142135623730950488016887242097;
        double sum=0.0;
        int n=0;
        double z=x/sqrt2;
        double a=z;
        double erf;

        while (Math.abs(a)>1e-20) {
            sum+=a;
            n++;
            a*=-z*z/(n*(2*n+1)/(2*n-1));            
        }
        sum+=a;
        erf=sum/sqrtpi;

        return (0.5+erf);
    }
    
}
