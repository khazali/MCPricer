# MCPricer
MCPricer is a Monte-Carlo options pricing tool, written in java.



## The code
The code can calculate the following option prices and their greeks using Monte-Carlo method:
1.	European options (in EuropeanOption.java)
2.	Barrier options (in BarrierOption.java)
3.	Asian options (in AsianOption.java)
4.	Autocallable (in Autocallable.java)

The Monte-Carlo method is implemented in MCPricer.java. It is initialized by the number of paths and an object of the above classes. In addition, analytical solutions of the Black-Scholes-Merton equation can be calculated for the pricing of the following options (which are used to test the validity of Monte-Carlo method for similar cases):
1.	European options (in BlackScholesMerton.java)
2.	Barrier options (in BlackScholesMertonBarrier.java)

The option classes implement the FinancialInstrument (in FinancialInstrument.java) interface and have multiple constructors for flexibility. The default constructor uses the console to get the required data from the user. The interested readers are encouraged to check the code for themselves and contact me if there are any questions.



## Usage
Just run MCPricer.jar located in MCPricer directory as:

```bash
java -jar --enable-preview MCPricer.jar
```

After the run, you are presented with the available financial instruments to be priced by the Monte-Carlo engine. By entering your desired choice, the model parameters will be asked, and then, results will be shown.



## Assumptions
* Risk-neutral.
* The underlying follows the geometric Brownian motion, with constant drift and volatility.
* There are no transaction costs or taxes. All securities are perfectly divisible.
* Security trading is continuous.
* The risk-free interest rate, r, is constant and the same for all maturities.
* There are no riskless arbitrage opportunities.
* The short-selling of securities with full use of proceeds is permitted.
* Continuous compounding was used.
* A year is considered to be 365 days.
* Underlying stock pays no dividend.
* Only the daily closing price of the underlying is considered.
* The duration of observing autocallable is assumed to be fixed. In each observation, the payoff is assumed to be the next multiple of the base payoff.



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests as appropriate.
The code still lacks the following features, which are being added gradually:

1. A Grphical User Interface (GUI).
2. Taking the underlying's dividends into account.
3. Increasing the performance and adding parallel/GPU processing abilities.
4. More precise calculation methods for the greeks in the Monte-Carlo pricing engine.
5. A more rigourous testing of Asian opions and autocallables.
6. Pricing of more financial instruments.
7. Multiple underlyings.
8. Implementing the Kahan summation algorithm to reduce round-off errors in Monte-Carlo engine.
9. Definitely more comments!


## Tests
The code can test the validity of the Monte-Carlo method for the European and barrier options against the analytical solutions of the Black-Scholes-Merton model. The testing of Asian options and Autocallable are approximate for now.
Since the tests try to validate the answer in all possible conditions, the testing procedure can take a long time to complete.



## Quotes
“Every problem is a gift. Without them we wouldn’t grow” – Tony Robbins.

I never refused a good problem, which APIServer was surely one of them. When I have a challenge, I feel alive. How about you?
 
 
 
## License
[GPL-3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)
