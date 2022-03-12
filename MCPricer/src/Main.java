class Main {
    public static void main(String[] args) {
        double ci, price;
        int selection, N;

        System.out.println("Welcome to Monte-Carlo pricing program!");
        System.out.println("Please choose the option type:");

        System.out.println("1- European");
        System.out.println("2- Knock In/Out Barrier");
        System.out.println("3- Asian");
        System.out.println("4- Autocallable");
        System.out.println("5- Run tests");
        System.out.println("");
        System.out.print("Please enter you selection: ");
        selection = Integer.parseInt(System.console().readLine());
        System.out.print("Please enter the number of paths: ");
        N = Integer.parseInt(System.console().readLine());
        

        switch (selection) {
            case 1:
                try {
                    EuropeanOption option = new EuropeanOption();
                    MCPricer mcp = new MCPricer(N, option);
                    mcp.CalculateGreeks();
                    ci = mcp.GetConfidenceInterval();
                    price = mcp.GetPrice();
                    System.out.println("");
                    System.out.println("The price of the option is: " + price);
                    System.out.println("95% confidence interval is: [" + (price - ci) + ", " + (price + ci) + "]");
                    System.out.println("The delta of the option is: " + mcp.GetDelta());
                    System.out.println("The gamma of the option is: " + mcp.GetGamma());
                    System.out.println("The vega of the option is: " + mcp.GetVega());
                }
                catch (Exception err) {
                    System.out.println("Exception: " + err.getMessage());
                    System.exit(-1);
                }
                break;

            case 2:
                try {
                    BarrierOption option = new BarrierOption();
                    MCPricer mcp = new MCPricer(N, option);
                    mcp.CalculateGreeks();
                    ci = mcp.GetConfidenceInterval();
                    price = mcp.GetPrice();
                    System.out.println("");
                    System.out.println("The price of the option is: " + price);
                    System.out.println("95% confidence interval is: [" + (price - ci) + ", " + (price + ci) + "]");
                    System.out.println("The delta of the option is: " + mcp.GetDelta());
                    System.out.println("The gamma of the option is: " + mcp.GetGamma());
                    System.out.println("The vega of the option is: " + mcp.GetVega());
                }
                catch (Exception err) {
                    System.out.println("Exception: " + err.getMessage());
                    System.exit(-1);
                }
                break;

            case 3:
                try {
                    AsianOption option = new AsianOption();
                    MCPricer mcp = new MCPricer(N, option);
                    mcp.CalculateGreeks();
                    ci = mcp.GetConfidenceInterval();
                    price = mcp.GetPrice();
                    System.out.println("");
                    System.out.println("The price of the option is: " + price);
                    System.out.println("95% confidence interval is: [" + (price - ci) + ", " + (price + ci) + "]");
                    System.out.println("The delta of the option is: " + mcp.GetDelta());
                    System.out.println("The gamma of the option is: " + mcp.GetGamma());
                    System.out.println("The vega of the option is: " + mcp.GetVega());
                }
                catch (Exception err) {
                    System.out.println("Exception: " + err.getMessage());
                    System.exit(-1);
                }
                break;

            case 4:
                try {
                    Autocallable option = new Autocallable();
                    MCPricer mcp = new MCPricer(N, option);
                    mcp.CalculateGreeks();
                    ci = mcp.GetConfidenceInterval();
                    price = mcp.GetPrice();
                    System.out.println("");
                    System.out.println("The price of the option is: " + price);
                    System.out.println("95% confidence interval is: [" + (price - ci) + ", " + (price + ci) + "]");
                    System.out.println("The delta of the option is: " + mcp.GetDelta());
                    System.out.println("The gamma of the option is: " + mcp.GetGamma());
                    System.out.println("The vega of the option is: " + mcp.GetVega());
                }
                catch (Exception err) {
                    System.out.println("Exception: " + err.getMessage());
                    System.exit(-1);
                }
                break;
                
            case 5:
                ///Todo tests
                break;

            default:
                System.out.println("Invalid selection");
                break;
        }
 

        











        //BlackScholesMerton bsc = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
        //System.out.println("");
        //BlackScholesMerton bsp = new BlackScholesMerton(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
        //BlackScholesMerton bsc = new BlackScholesMerton(49.0, 50.0, 0.05, 0.2, 0.3846, "Call");

        //try {
            //EuropeanOption eoc = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Call");
            /*
            EuropeanOption eoc = new EuropeanOption(49.0, 50.0, 0.05, 0.2, 0.3846, "Call");
            MCPricer mcc = new MCPricer(1000000, eoc);
            mcc.CalculateGreeks();
            System.out.println(mcc.GetDelta());
            System.out.println(mcc.GetGamma());
            System.out.println(mcc.GetVega()); */


            //BarrierOption bc = new BarrierOption(49.0, 50.0, 53.0, 0.05, 0.2, 0.3846, "CallUpIn");
            //BarrierOption bc = new BarrierOption(49.0, 50.0, 53.0, 0.05, 0.2, "1/1/2022", "30/6/2022", "CallUpIn");
            //AsianOption ao = new AsianOption(49.0, 50.0, 0.08219178082191780821917808219178, 0.05, 0.2, 0.24657534246575342465753424657534, "CallGeometric");
            //Autocallable ac= new Autocallable(100.0, 100.0, 75.0, 0.06, 1.0, 0.1, 0.2, 6.0);
            //MCPricer mcc = new MCPricer(1000000, ac);
            //System.out.println(mcc.RunSimulation());
            //System.out.println(mcc.GetConfidenceInterval());
            //mcc.CalculateGreeks();
            //System.out.println(mcc.GetPrice());
            //System.out.println(mcc.GetDelta());
            //System.out.println(mcc.GetGamma());
            //System.out.println(mcc.GetVega());
            

            //System.out.println(mcc.RunSimulation());

            //EuropeanOption eop = new EuropeanOption(42.0, 40.0, 0.1, 0.2, 0.5, "Put");
            //MCPricer mcp = new MCPricer(1000000, eop);
            //System.out.println(mcp.RunSimulation());

        //} catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            //1System.exit(-1);
        //}

        
    }   
    
}