public class BlackScholesMerton {
    public BlackScholesMerton(double price, double strike, double riskFreeRate, double volatility, double duration, String type) {
        double d1, d2, C, delta, gamma, vega;

        d1=(Math.log(price/strike)+(riskFreeRate+volatility*volatility/2)*duration)/(volatility*Math.sqrt(duration));
        d2=d1-volatility*Math.sqrt(duration);
        
        
        if (type.equals("Call")) {
            C=price*cdf(d1)-strike*Math.exp(-riskFreeRate*duration)*cdf(d2);
            delta=cdf(d1);
            gamma=pdf(d1)/(price*volatility*Math.sqrt(duration));
            vega=price*Math.sqrt(duration)*pdf(d1);
            
            System.out.println("Call Price: " + C);
            System.out.println("Call Delta: " + delta);
            System.out.println("Call Gamma: " + gamma);
            System.out.println("Call Vega: " + vega);
        }
        else if (type.equals("Put")) {
            C=strike*Math.exp(-riskFreeRate*duration)*cdf(-d2)-price*cdf(-d1);
            delta=cdf(d1)-1;
            gamma=pdf(d1)/(price*volatility*Math.sqrt(duration));
            vega=price*Math.sqrt(duration)*pdf(d1);

            System.out.println("Put Price: " + C);
            System.out.println("Put Delta: " + delta);
            System.out.println("Put Gamma: " + gamma);
            System.out.println("Put Vega: " + vega);
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

    public double pdf(double x) {
        return Math.exp(-x*x/2)/2.506628274631000502415765284811;
    }
    
}
