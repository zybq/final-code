package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception 
{
	//	TODO - RocketBLL RateException - RateDomainModel should be an attribute of RateException
	//	* Add RateRomainModel as an attribute
	//	* Create a constructor, passing in RateDomainModel
	//	* Create a getter (no setter, set value only in Constructor)
	
	private RateDomainModel rate;
	
	private double income;
	
	private double creditScore;
	
	
	public RateException()
	{
		super("You cannot get a rate.");
	}
	
	public RateException(RateDomainModel r)
	{
		this.rate = r;
	}
	
	public RateException(double inc, double credit)
	{
		this();
		this.income = inc;
		this.creditScore = credit;
	}
	
	
	public RateDomainModel getRate()
	{
		return this.rate;
	}
	
}
