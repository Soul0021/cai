package SpecialAssignmentv1;


public abstract class Loan implements LoanConstants {

    private int L_No;
    private String Cust_Lastn;
    private double L_Amount;
    double F_rate;
    private int term;
//Instantiation 
    public Loan(int L_No, String Cust_Lastn, double L_Amount, int term) {
        this.L_No = L_No;
        this.Cust_Lastn = Cust_Lastn;
        setLoanAmount(L_Amount);
        setTerm(term);
    }
    // condition statement for  amount 
    public void setLoanAmount( double L_Amount) {
        if (L_Amount > Max_loan) {
        	System.out.println("Loan amount exceeds the maximum allowed amount.");
        	
        } else {
        	this.L_Amount = L_Amount;
            
            
        }
    }
//condition statement for the term 
    public void setTerm(int term) {
        if (term == S_term || term == M_term || term == L_term) {
        	this.term = term;
        } else {
        	System.out.println("Invalid input. Short term will be the default choice.\n");
            this.term = S_term;
            
        }
    }

  public abstract void setF_rate(double P_rate);

    public double calculateAmountOwed() {
        return L_Amount + (L_Amount * F_rate);
    }

// will show the output that fill up by the user 
    @Override
    public String toString() {
    	
        return "Loan Number: " + L_No +
                "\nCustomer Last Name: " + Cust_Lastn +
                "\nLoan Amount: Php " + L_Amount +
                "\nInterest Rate: " + (F_rate * 100)  + "%" +
                "\nTerm: " + term + " year(s)" +
                "\nTotal Amount Owed: Php " + calculateAmountOwed();
    }
	public double getF_rate() {
		return F_rate;
	}
	public void setF_rate() {
		// TODO Auto-generated method stub
		
	}




}
