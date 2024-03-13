package SpecialAssignmentv1;

import java.util.Scanner;
public class CreateLoans {
   
    

	public static void main(String[] args, String Comp_name) {
    	
    	
        Scanner sc = new Scanner(System.in);

        Loan[] loans = new Loan[2];
        
		
        for (int i = 0; i < loans.length; i++) {
            int loanType;
            System.out.println("Welcome to " + Comp_name);
            System.out.println("Select Loan Type:");
            System.out.println("1. Business Loan");
            System.out.println("2. Personal Loan");

            System.out.print("Enter your choice (1 or 2): ");
            loanType = sc.nextInt();

            double P_rate;
            System.out.print("Enter the prime interest rate: ");
            P_rate = sc.nextDouble();

            switch (loanType) {
                case 1:
                    loans[i] = createBusinessLoan(sc, i + 1, P_rate); // choice for business loan
                    break;
                case 2:
                    loans[i] = createPersonalLoan(sc, i + 1, P_rate);// choice for personal loans
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    i--; // Decrement the counter to repeat the current iteration
            }
        }

        // Display all the loans
        for (Loan loan : loans) {
            System.out.println("\n" + loan.toString());
        }
    }

    private static BusinessLoan createBusinessLoan(Scanner sc, int L_No, double P_rate) {
        System.out.print("Enter customer last name: ");// to get customer last name
        String Cust_Lastn = sc.next();

        System.out.print("Enter loan amount: Php "); // to get the amount value
        double L_Amount = sc.nextDouble();

        System.out.print("Enter loan term (1, 3, or 5 years): "); // to get the years of term
        int term = sc.nextInt();

        BusinessLoan businessLoan = new BusinessLoan(L_No, Cust_Lastn, L_Amount, term);
        businessLoan.setF_rate(P_rate); // Set the F_rate using the provided P_rate

        return businessLoan;
    }

    private static PersonalLoan createPersonalLoan(Scanner sc, int L_No, double P_rate) {
        System.out.print("Enter customer last name: ");// to get customer last name
        String Cust_Lastn = sc.next();

        System.out.print("Enter loan amount: Php ");// to get the amount value
        double L_Amount = sc.nextDouble();

        System.out.print("Enter loan term (1, 3, or 5 years): ");// to get the years of term
        int term = sc.nextInt();

        PersonalLoan personalLoan = new PersonalLoan(L_No, Cust_Lastn, L_Amount, term);
        personalLoan.setF_rate(P_rate); // Set the F_rate using the provided P_rate

        return personalLoan;
    }
}

