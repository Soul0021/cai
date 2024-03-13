	package SpecialAssignmentv1;
	
	//BusinessLoan.java
	public class BusinessLoan extends Loan {
			//Business Loan Constructor
			public BusinessLoan(int L_No, String Cust_Lastn , double L_Amount, int term) {
			super(L_No,Cust_Lastn , L_Amount, term);
			setF_rate(0.01);

			}
			//Override
			public void setF_rate(double P_rate) {
			    this.F_rate =  P_rate + 0.01;
			}
			@Override
			public void setF_rate() {
				// TODO Auto-generated method stub
			}
			}