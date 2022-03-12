public class Tests {
    private boolean result = false;
    private final int numberOfSims = 1000000;
    private final double tolerance = 0.01;

    public Tests() {
        this.result = RunTests();
    }


    public boolean RunTests() {
        Test4();

        return true;
    }

    private boolean Test1() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Example 15.6, pp336-337
        //Tests European Call

        double price1, price2;
        BlackScholesMerton bs = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
        price1 = bs.GetPrice();

        try {
            EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Call");            
            MCPricer mcc = new MCPricer(numberOfSims, eoc);
            price2 = mcc.RunSimulation();

            if (Math.abs(price1 - price2) < price1*tolerance) return true;
            else return false;            
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
        
        return false;
    }


    private boolean Test2() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Example 15.6, pp336-337
        //Tests European Put

        double price1, price2;
        
        BlackScholesMerton bs = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
        price1 = bs.GetPrice();

        try {
            EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Put");            
            MCPricer mcc = new MCPricer(numberOfSims, eoc);
            price2 = mcc.RunSimulation();

            if (Math.abs(price1 - price2) < price1*tolerance) return true;
            else return false;            
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return false;
    }


    private boolean Test3() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Examples 19.1 (p. 403), 19.4 (p. 412), 19.6 (p. 416)
        //Tests European options greeks 

        double delta1, delta2, gamma1, gamma2, vega1, vega2;
                
        BlackScholesMerton bs = new BlackScholesMerton(49.0, 50.0, 0.05, 0.2, 0.3846, "Call");
        delta1 = bs.GetDelta();
        gamma1 = bs.GetGamma();
        vega1 = bs.GetVega();

        try {
            EuropeanOption eoc = new EuropeanOption(49.0, 50.0, 0.05, 0.2, 0.3846, "Call");            
            MCPricer mcc = new MCPricer(numberOfSims, eoc);
            mcc.CalculateGreeks();
            delta2 = mcc.GetDelta();
            gamma2 = mcc.GetGamma();
            vega2 = mcc.GetVega();


            if (Math.abs(delta1 - delta2) < delta1*tolerance) return true;            
            if (Math.abs(gamma1 - gamma2) < gamma1*tolerance) return true;
            if (Math.abs(vega1 - vega2) < vega1*tolerance) return true;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return false;
    }

    private boolean Test4() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Example 15.6, pp336-337
        //Tests European Call

        double price1, price2;
        BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 45., 0.1, 0.2, 0.5, "CallUpIn");
        price1 = bs.GetPrice();
        System.out.println(price1);

        try {
            BarrierOption bo = new BarrierOption(40.0, 42.0, 45., 0.1, 0.2, 0.5, "CallUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();
            System.out.println(price2);

            if (Math.abs(price1 - price2) < price1*tolerance) return true;
            else return false;            
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
        
        return false;
    }
}
