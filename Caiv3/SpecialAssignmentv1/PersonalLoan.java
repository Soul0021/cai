package SpecialAssignmentv1;


//Public class Personal Loan
public class PersonalLoan extends Loan {
//Personal Loan Constructor
public PersonalLoan(int L_No, String Cust_Lastn , double L_Amount, int term) {
super(L_No,Cust_Lastn , L_Amount, term);
setF_rate(0.02);
}
//Override
public void setF_rate(double P_rate) {
    this.F_rate =  P_rate + 0.02;
}
@Override
public void setF_rate() {
	// TODO Auto-generated method stub
	
}

}