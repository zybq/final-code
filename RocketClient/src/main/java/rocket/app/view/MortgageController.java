package rocket.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import eNums.eAction;
import exceptions.RateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	@FXML TextField txtCreditScore;
	@FXML TextField txtMortgageAmt;
	@FXML TextField txtIncome;
	@FXML TextField txtExpenses;
	@FXML TextField txtHouseCost;
	@FXML TextField txtTerms;
	@FXML TextField txtDownPayment;
	
	@FXML Label lblMortgagePayment;
	@FXML ComboBox<String[]> cmbTerm;
	@FXML Label downPayment;
	@FXML Label error;
	@FXML Label error2;
	
	private TextField txtNew;
	private Label lblNew;
	private ComboBox<Label> cmbNew;
	
	private MainApp mainApp;
	private double mp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		boolean goOn = true;
		
		double rate = 0;
		int credScore = Integer.parseInt(txtCreditScore.getText());
		try
		{			
			rate = (RateBLL.getRate(credScore))/100;
			System.out.println("google rate: " + rate);
		}
		catch(RateException e)
		{
			goOn = false;
			
			lblMortgagePayment.setText("");
			error.setText("Your credit-score is too low!");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Credit Score failure");
			alert.setHeaderText("Credit-score is too low for a rate");
			alert.showAndWait().ifPresent(rs -> {
			    if (rs == ButtonType.OK) {
			        System.out.println("Pressed OK.");
			    }
			});
		}
		double houseCost = Double.parseDouble(txtHouseCost.getText());
		double downPayment = Double.parseDouble(txtDownPayment.getText());
		int years = Integer.parseInt(txtTerms.getText());
		int nop = years * 12;
		
		double payment = (RateBLL.getPayment(rate, nop, (houseCost-downPayment), 0, false));
		double pmt = Math.round (payment * 100.0) / 100.0;
		System.out.println("paymt: " + pmt);
		
		double piti1 = (Double.parseDouble(txtIncome.getText())/12)*.28;
		System.out.println("PITI1: " + piti1);
		
		double piti2 = ((Double.parseDouble(txtIncome.getText())/12)*.36) - Double.parseDouble(txtExpenses.getText());
		System.out.println("PITI2: " + piti2);
		
		if (pmt > piti1)
		{
			//Doesn't pass the test, throw an error in their face
			if(goOn)
			PITIError();
		}
		else if(pmt > piti2)
		{
			//Doesn't pass the test, throw an error in their face
			if(goOn)
			PITIError();
		}
		else
		{
			//They passed the tests, display their payment if they also passed the credit score test
			if(goOn == true)
			{
				//They passed the credit score test
				lblShowPayment(pmt);
			}
			else
			{
				//They didn't pass the credit score test, don't display anything extra
			}
			
		}		
	}
		
	@FXML
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		
		
		//I didn't know how to activate this method so i handled it in other FXML attributes/methods
		
		
		/*double dPayment = lRequest.getdPayment();
		double rounded = Math.round (dPayment * 100.0) / 100.0;
		String r = Double.toString(123.45);
		downPayment.setText(r);*/
	}
	
	public void PITIError()
	{
		lblMortgagePayment.setText("");
		error.setText("House Cost is too high. Sorry!");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Failed PITI Test");
		alert.setHeaderText("HOUSE COST IS TOO HIGH!");
		alert.setContentText("Your income is too low to get a mortgage on this house.");
		alert.showAndWait().ifPresent(rs -> {
		    if (rs == ButtonType.OK) {
		        System.out.println("Pressed OK.");
		    }
		});
	}
	
	public void lblShowPayment(double pmt)
	{	
		String pmts = Double.toString(pmt);
		error.setText("");
		lblMortgagePayment.setText("$ " + pmts);
	}
}
