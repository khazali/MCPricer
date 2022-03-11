class Main {
    public static void main(String[] args) {
        //BlackScholesMerton bsc = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
        //System.out.println("");
        //BlackScholesMerton bsp = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
        BlackScholesMerton bsc = new BlackScholesMerton(49.0, 50.0, 0.05, 0.2, 0.3846, "Call");

        try {
            //EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
            EuropeanOption eoc = new EuropeanOption(49.0, 50.0, 0.05, 0.2, 0.3846, "Calls");
            MCPricer mcc = new MCPricer(1000000, eoc);
            mcc.CalculateGreeks();
            System.out.println(mcc.GetDelta());
            System.out.println(mcc.GetGamma());
            System.out.println(mcc.GetVega());

            //System.out.println(mcc.RunSimulation());

            //EuropeanOption eop = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
            //MCPricer mcp = new MCPricer(1000000, eop);
            //System.out.println(mcp.RunSimulation());

        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
            System.exit(-1);
        }

        
    }   
    
}