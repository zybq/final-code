package rocketBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.formula.functions.*;
import org.hibernate.Session;

import exceptions.RateException;
import rocketDomain.RateDomainModel;
import util.HibernateUtil;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException 
	{
		double dInterestRate = 0;
		
		//TODO - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		
		
		
				
		//TODO - RocketBLL RateBLL.getRate
		//			obviously this should be changed to return the determined rate
		
		
		//Changed this to List from ArrayList**
		List<RateDomainModel> rates = RateDAL.getAllRates();
		//Collections.sort(list, c);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String hql = "FROM RateDomainModel r ORDER BY r.iMinCreditScore ASC";
		
		List lstRates = session.createQuery(hql).list();
		

		//TODO: Filter the ArrayList...  look for the correct rate for the given credit score.
		//	Easiest way is to apply a filter using a Lambda function.
		//
		//	Example... how to use Lambda functions:
		//			https://github.com/CISC181/Lambda
		
				for(RateDomainModel rdm : rates)
				{
					if(rdm.getiMinCreditScore() <= GivenCreditScore)
					
					{
						dInterestRate = rdm.getdInterestRate();
					}
					else
					{
						break;
					}
				}
				if(dInterestRate == 0)
				{
					throw new RateException();
				}
		
				

		session.close();
		return dInterestRate;
		
		
	}
		
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		

		return FinanceLib.pmt(r, n, p, f, t);
	}
}