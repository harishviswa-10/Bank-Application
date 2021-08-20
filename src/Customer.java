public class Customer {
	   private int custId;
	   private String accNo;
	   private String name;
	   private double balance;
	   private String enc_pwd;
	   public Customer(int custId,String accNo,String name,double balance,String enc_pwd){
		   this.custId=custId;
		   this.accNo=accNo;
		   this.name=name;
		   this.balance=balance;
		   this.enc_pwd=enc_pwd;
	   }
	   public String details(){
		   String s=String.valueOf(custId)+"  "+accNo+"  "+name+"  "+String.valueOf(balance)+"  "+enc_pwd+"\n";
		   return s;
	   }
	   public void setCustId(int a) {
		   custId=a;
	   }
	   public void setAccNo(String a) {
		   accNo=a;
	   }
	   public void setName(String a) {
		   name=a;
	   }
	   public void setBalance(double a) {
		   balance=a;
	   }
	   public void setEnc_pwd(String a) {
		   enc_pwd=a;
	   }
	   public int getCustId() {
		   return custId;
	   }
	   public String getAccNo() {
		   return accNo;
	   }
	   public String getName() {
		   return name;
	   }
	   public double getBalance() {
		   return balance;
	   }
	   public String getEnc_pwd() {
		   return enc_pwd;
	   }
}
