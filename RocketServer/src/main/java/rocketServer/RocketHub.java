package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message){
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			//	TODO - RocketHub.messageReceived

			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			double rate = 0;
			try
			{
				rate = RateBLL.getRate(lq.getiCreditScore());
			}
			catch(RateException e)
			{
				String errorMessage = "low creditscore, no rate";
		
			}
			lq.setdPayment(RateBLL.getPayment(rate/12, lq.getiTerm()*12, lq.getdAmount()-lq.getiDownPayment(), 0, false));
			try
			{
				
				if (lq.getdPayment() > .28*lq.getIncome())
				
				{
					RateException re = new RateException(lq.getiCreditScore(), lq.getIncome());
					sendToAll(re);
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			
			try
			{
				if (lq.getdPayment() > (.36*(lq.getIncome() + lq.getExpenses())))
				{
					RateException re = new RateException(lq.getiCreditScore(), lq.getIncome());
					sendToAll(re);
				}
			}
			catch(Exception e)
			{
				throw e;
			}
			
			
			sendToAll(lq);
		}
	}
}
