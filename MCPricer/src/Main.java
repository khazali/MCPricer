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
        

        switch (selection) {
            case 1:
                System.out.print("Please enter the number of paths: ");
                N = Integer.parseInt(System.console().readLine());
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
                System.out.print("Please enter the number of paths: ");
                N = Integer.parseInt(System.console().readLine());            
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
                System.out.print("Please enter the number of paths: ");
                N = Integer.parseInt(System.console().readLine());
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
                System.out.print("Please enter the number of paths: ");
                N = Integer.parseInt(System.console().readLine());
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
                new Tests();
                break;

            default:
                System.out.println("Invalid selection");
                break;
        }        
    }   
    
}