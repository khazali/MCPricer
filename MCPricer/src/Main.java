class Main {
    public static void main(String[] args) {
        BlackScholesMerton bsc = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
        BlackScholesMerton bsp = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Put");

        try {
            EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
            MCPricer mcc = new MCPricer(1000000, eoc);
            System.out.println(mcc.RunSimulation());

            EuropeanOption eop = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
            MCPricer mcp = new MCPricer(1000000, eop);
            System.out.println(mcp.RunSimulation());

        } catch (Exception e) {
            System.out.println("Exception"+e.getMessage());
            System.exit(-1);
        }

        
    }   
    
}