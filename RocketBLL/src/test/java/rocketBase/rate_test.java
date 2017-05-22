package rocketBase;

import static org.junit.Assert.*;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.junit.Test;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class rate_test 
{
	
	RateDomainModel r1 =  new RateDomainModel(); 
	
	@Test
	public void PaymentTest() throws RateException
	{
		double r = .04;
		int n = 30*12;
		double p = 300000;
		double f = 0;
		boolean t = false;
		double answer = RateBLL.getPayment(r, n, p, f, t);
		System.out.println("google1 " + RateBLL.getRate(720) );
		System.out.println("google " + answer + "\n" + RateBLL.getRate(600));
		assertEquals(answer, 1432.25, .01);		
	}

	@Test(expected = RateException.class)
	public void TestRateException() throws RateException
	{
		int creditScore = 400;
		RateBLL.getRate(creditScore);
		double rate = 0;
		boolean check = true;
		try
		{
			rate = RateBLL.getRate(500);
		}
		catch(RateException e)
		{
			check = false;
			System.out.println("Increase your creditscore");
			System.out.println("You can't loan because you dont have enough creditscore.");
		}
		if(check)
		{
			assertTrue(rate > 7 );
		}
		else if(!check)
		{
			assertTrue(rate < 7);
		}
		
	}
	
}
