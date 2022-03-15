public class Tests {
    private boolean result = false;
    private final int numberOfSims = 2000000;
    private final double tolerance = 0.1;

    public Tests() {
        this.result = RunTests();
    }

    public boolean GetResult() {
        return this.result;
    }


    public boolean RunTests() {
        boolean ret = true;

        if (Test1()) System.out.println("European Call works fine!");
        else {
            System.out.println("European Call failed!");
            ret = false;
        }
        if (Test2()) System.out.println("European Put works fine!");
        else {
            System.out.println("European Put failed!");
            ret = false;
        }
        if (Test3()) System.out.println("Vanilla options greeks are OK!");
        else {
            System.out.println("Vanilla options greeks failed!");
            ret = false;
        }
        if (Test4()) System.out.println("Barrier options and their greeks work fine!");
        else {
            System.out.println("Barrier options failed!");
            ret = false;
        }
        if (Test5()) System.out.println("Asian Call works fine!");
        else {
            System.out.println("Asian Call failed!");
            ret = false;
        }
        if (Test6()) System.out.println("Autocallable seems fine!");
        else {
            System.out.println("Autocallable failed!");
            ret = false;
        }

        System.out.println("");
        if (ret) {
            System.out.println("All tests passed!");
            System.out.println(":-)");
        }
        else {
            System.out.println("Tests failed!");
            System.out.println(":-(");
        }
        
        return ret;
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

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
        
        return true;
    }


    private boolean Test2() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Example 15.6, pp. 336-337
        //Tests European Put

        double price1, price2;
        
        BlackScholesMerton bs = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
        price1 = bs.GetPrice();

        try {
            EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Put");            
            MCPricer mcc = new MCPricer(numberOfSims, eoc);
            price2 = mcc.RunSimulation();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
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


            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return true;
    }

    private boolean Test4() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //pp. 598-600
        //Extensive testing of barrier options greeks

        
        double price1, price2, delta1, delta2, gamma1, gamma2, vega1, vega2;
        
        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpIn");
            price1 = bs.GetPrice();

            BarrierOption bo = new BarrierOption(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "CallUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "CallUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(42.0, 46.0, 38.0, 0.1, 0.2, 0.5, "CallDownIn");
            price1 = bs.GetPrice();
            
            
            BarrierOption bo = new BarrierOption(42.0, 46.0, 38.0, 0.1, 0.2, 0.5, "CallDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "CallDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "CallDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(38.0, 42.0, 46.0, 0.1, 0.2, 0.5, "CallUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(42.0, 46.0, 38.0, 0.1, 0.2, 0.5, "CallDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(42.0, 46.0, 38.0, 0.1, 0.2, 0.5, "CallDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "CallDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "CallDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 42.0, 38.0, 0.1, 0.2, 0.5, "PutDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 42.0, 38.0, 0.1, 0.2, 0.5, "PutDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "PutDownIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "PutDownIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 42.0, 38.0, 0.1, 0.2, 0.5, "PutDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 42.0, 38.0, 0.1, 0.2, 0.5, "PutDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "PutDownOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(46.0, 38.0, 42.0, 0.1, 0.2, 0.5, "PutDownOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(42.0, 38.0, 46.0, 0.1, 0.2, 0.5, "PutUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(42.0, 38.0, 46.0, 0.1, 0.2, 0.5, "PutUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "PutUpIn");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "PutUpIn");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(42.0, 38.0, 46.0, 0.1, 0.2, 0.5, "PutUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(42.0, 38.0, 46.0, 0.1, 0.2, 0.5, "PutUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            BlackScholesMertonBarrier bs = new BlackScholesMertonBarrier(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "PutUpOut");
            price1 = bs.GetPrice();
            
            BarrierOption bo = new BarrierOption(38.0, 46.0, 42.0, 0.1, 0.2, 0.5, "PutUpOut");            
            MCPricer mcc = new MCPricer(numberOfSims, bo);
            mcc.CalculateGreeks();

            delta1 = bs.GetDelta();
            delta2 = mcc.GetDelta();
            gamma1 = bs.GetGamma();
            gamma2 = mcc.GetGamma();
            vega1 = bs.GetVega();
            vega2 = mcc.GetVega();            
            price2 = mcc.GetPrice();

            if ((price1 != price2) && (Math.abs(price1 - price2) > tolerance)) return false;
            if ((delta1 != delta2) && (Math.abs(delta1 - delta2) > tolerance)) return false;
            if ((gamma1 != gamma2) && (Math.abs(gamma1 - gamma2) > tolerance)) return false;
            if ((vega1 != vega2) && (Math.abs(vega1 - vega2) > 50*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }
       

        
        return true;
    }


    private boolean Test5() {
        //Reference: Hull, J. C. (2003). Options futures and other derivatives (10th Edition). Pearson Education India.
        //Example 26.3, p. 605
        //Tests Asian Call for different valuation periods


        double price;

        try {
            AsianOption ao = new AsianOption(50.0, 50.0, 1.0/365.0, 0.1, 0.4, 1.0, "CallArithmetic");            
            MCPricer mcc = new MCPricer(numberOfSims, ao);
            price = mcc.RunSimulation();

            if ((price != 5.62) && (Math.abs(price - 5.62) > 5*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }


        try {
            AsianOption ao = new AsianOption(50.0, 50.0, 1.0/52.0, 0.1, 0.4, 364.0/365.0, "CallArithmetic");            
            MCPricer mcc = new MCPricer(numberOfSims, ao);
            price = mcc.RunSimulation();

            if ((price != 6.0) && (Math.abs(price - 6.0) > 5*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        try {
            AsianOption ao = new AsianOption(50.0, 50.0, 1.0/12.0, 0.1, 0.4, 360.0/365.0, "CallArithmetic");            
            MCPricer mcc = new MCPricer(numberOfSims, ao);
            price = mcc.RunSimulation();

            if ((price != 5.70) && (Math.abs(price - 5.70) > 5*tolerance)) return false;
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        return true;
    }


    private boolean Test6() {
        //Tests Autocallable
        //Not a rigorous test. Just a logical comparison!

        double price1 = 0;
        double price2 = 0;

        try {
            Autocallable ac = new Autocallable(100.0, 100.0, 75.0, 0.08, 1.0, 0.1, 0.2, 4);            
            MCPricer mcc = new MCPricer(numberOfSims, ac);
            price1 = mcc.RunSimulation();
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }


        try {
            Autocallable ac = new Autocallable(100.0, 100.0, 80.0, 0.08, 1.0, 0.1, 0.2, 4);            
            MCPricer mcc = new MCPricer(numberOfSims, ac);
            price2 = mcc.RunSimulation();
        }
        catch (Exception err) {
            System.out.println("Exception: " + err.getMessage());
            System.exit(-1);
        }

        if (price1 < price2) return false;        

        return true;
    }
}



