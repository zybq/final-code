package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rocketDomain.RateDomainModel;

public class Rate_Test 
{

	
	@Test
	public void RateTest()
	{
		
		int size = RateDAL.getAllRates().size();
		
		assertTrue(size > 0);
		
	}
	
	
	public void RateExceptionTest() throws Exception
	
	{
		
		int size = RateDAL.getAllRates().size();
		
		if(size == 0)
		
		{
			
			throw new Exception();
		
		}
	}

}
