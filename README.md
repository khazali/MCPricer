# MCPricer
MCPricer is a Monte-Carlo options pricing tool, written in java.



## The code
The code can calculate the following option prices and their greeks using Monte-Carlo method:
1.	European options (in EuropeanOption.java)
2.	Barrier options (in BarrierOption.java)
3.	Asian options (in AsianOption.java)
4.	Autocallable (in Autocallable.java)

The Monte-Carlo method is implemented in MCPricer.java. It is initialized by the number of paths and an object of the above classes. In addition, analytical solutions of the Black-Scholes-Merton equation can be calculated for the pricing of the following options (which are used to test the validity of Monte-Carlo methods for similar cases):
1.	European options
2.	Barrier options

The option classes implement the FinancialInstrument (in FinancialInstrument.java) interface and have multiple constructors for flexibility. The default constructor uses the console to get the required data from the user. The interested readers are encouraged to check the code for themselves and contact me if there are any questions.



## Usage
Just run MCPricer.jar located in as:

```bash
java -jar MCPricer.jar
```


## Assumptions
* A year is considered to be 365 days.
* Underlying stock pays no dividend.
* Only the closing price of the underlying is considered.



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests as appropriate.
The code still lacks the following features, which are being added gradually:

1. A Grphical User Interface (GUI).
2. Taking the underlying's dividends into account.
3. Parallel processing abilities.
4. More precise calculation methods for the greeks in the Monte-Carlo pricing engine.
5. A more rigourous testing of Asian opions and autocallables.
6. Pricing of more financial instruments.
7. Multiple underlyings.
8. Definitely more comments!



## Quotes
“Every problem is a gift. Without them we wouldn’t grow” – Tony Robbins.

I never refuse a good problem, which MCPricer was surely one of them. When I have a challenge, I feel alive. How abou you?
 
 
 
## License
[GPL-3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)
