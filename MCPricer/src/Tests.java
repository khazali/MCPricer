public class Tests {
    private boolean result = false;
    private final int numberOfSims = 1000000;
    private final double tolerance = 0.10;

    public Tests() {
        this.result = RunTests();
    }


    public boolean RunTests() {
        System.out.println(Test4());

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

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
        
        return true;
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

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return true;
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


            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > delta1*tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > gamma1*tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > vega1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return true;
    }

    private boolean Test4() {        
        double price1, price2;
        
        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpIn");
            price1 = bs.GetPrice();

            BarrierOption bo = new BarrierOption(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 45.0, 42.0, 0.1, 0.2, 0.5, "CallUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 45.0, 42.0, 0.1, 0.2, 0.5, "CallUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 39.0, 0.1, 0.2, 0.5, "CallDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 42.0, 39.0, 0.1, 0.2, 0.5, "CallDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(42.0, 39.0, 41.0, 0.1, 0.2, 0.5, "CallDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(42.0, 39.0, 41.0, 0.1, 0.2, 0.5, "CallDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 42.0, 45.0, 0.1, 0.2, 0.5, "CallUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 42.0, 39.0, 0.1, 0.2, 0.5, "CallDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 42.0, 39.0, 0.1, 0.2, 0.5, "CallDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 38.0, 39.0, 0.1, 0.2, 0.5, "CallDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 38.0, 39.0, 0.1, 0.2, 0.5, "CallDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 37.0, 38.0, 0.1, 0.2, 0.5, "PutDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 37.0, 38.0, 0.1, 0.2, 0.5, "PutDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 37.0, 38.0, 0.1, 0.2, 0.5, "PutDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 37.0, 38.0, 0.1, 0.2, 0.5, "PutDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 38.0, 41.0, 0.1, 0.2, 0.5, "PutUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 38.0, 41.0, 0.1, 0.2, 0.5, "PutUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(36.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(36.0, 38.0, 37.0, 0.1, 0.2, 0.5, "PutUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(40.0, 39.0, 42.0, 0.1, 0.2, 0.5, "PutUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(40.0, 39.0, 42.0, 0.1, 0.2, 0.5, "PutUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(34.0, 38.0, 36.0, 0.1, 0.2, 0.5, "PutUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(34.0, 38.0, 36.0, 0.1, 0.2, 0.5, "PutUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > price1*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
       

        
        return true;
    }
}
