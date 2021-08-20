import java.io.*;
import java.util.*;
public class CustomerManagement {
	static Scanner sc=new Scanner(System.in);
	static ArrayList<Customer> cust=new ArrayList<Customer>();
	
	public void addCustomer(int a,String b,String c,double d,String e) {
		Customer cc=new Customer(a,b,c,d,e);
		cust.add(cc);
	}
	
	public void deposit(int id){
		String log;
		System.out.println("Enter amount to deposit:");
		double d=sc.nextDouble();
		double balance=0;
		for(Customer c:cust){
			if(id==c.getCustId()){
				balance=c.getBalance()+d;
				c.setBalance(balance);
				break;
			}
		}
		System.out.println("Amount deposited successfully.");
		log="  Cash Deposit  "+d+"  "+balance;
		logfile(id,log);
	}
	
    public void withdraw(int id){
    	String log;
    	double balance=0,d;
    	while(true){
    		int check=0;
    	   System.out.println("Enter amount to withdraw:");
		   d=sc.nextDouble();
		   for(Customer c:cust){
			if(id==c.getCustId() && c.getBalance()>=d){
				balance=c.getBalance()-d;
				c.setBalance(balance);
				check=1;
				break;
			}
		   }
		   if(check==1) break;
		   else System.out.println("Insufficient Balance.");
    	}
    	System.out.println("Amount withdrawed successfully.");
		log="  ATM Withdrawl  "+d+"  "+balance;
		logfile(id,log);
    }
    
    public void transfer(int id){
        System.out.println("RS 10 will be charged if transfer amount is above 5000");
    	String log1="",log2="";
    	double balance1=0,balance2,d;
    	String trans_acc="";
    	int trans_id=0;
    	for(Customer c:cust){
			if(c.getCustId()==id){
				trans_acc=c.getAccNo();
				balance1=c.getBalance();
			}
		}
    	while(true){
    	   System.out.println("Enter amount to transfer:");
		   d=sc.nextDouble();
		   if(d>5000 && (d+10)<=balance1) break;
		   else if(d<=5000 && d<=balance1) break;
		   else System.out.println("Insuffcient Balance.");
    	}
		String acc;
		while(true){
		   System.out.println("Enter account no to transfer:");
		   acc=sc.next();
		   int check=0;
		   for(Customer c:cust){
			 if(acc.equals(c.getAccNo())){
				check=1;break;
			 }
		   }
		   if(check==1) break;
		   else System.out.println("Enter correct account number.");
		}
		for(Customer c:cust){
			if(id==c.getCustId()){
				String op="";
				balance1=c.getBalance()-d;
				if(d>5000){
					balance1-=10;
					op=" with service charge";
				}
				c.setBalance(balance1);
				log1="  Transfer To "+acc+op+"  "+d+"  "+balance1;	
			}
			if(acc.equals(c.getAccNo())){
				trans_id=c.getCustId();
				balance2=c.getBalance()+d;
				c.setBalance(balance2);
				log2=" Transfer From "+trans_acc+"  "+d+"  "+balance2;
			}
		}
		System.out.println("Amount transferred successfully.");
		logfile(id,log1);
		logfile(trans_id,log2);	
    }
    
    public void changepassword(int id){
      while(true){
    	String pwd;
    	while(true){
    	  System.out.println("Enter new password:");
    	  pwd=sc.next();
    	  if(check_pwd(pwd)) break;
    	  else System.out.println("Password should contain atleast 2 upper-case,2 lowercase and 2 number");
    	}
    	System.out.println("Confirm new password:");
    	String rpwd=sc.next();
    	if(pwd.equals(rpwd)){
    		String password=encrypt(pwd);
    		for(Customer c:cust){
    			if(id==c.getCustId()){
    				c.setEnc_pwd(password);
    			}
    		}
    		break;
    	}
    	else System.out.println("Password doesn't match.");
      }
      System.out.println("Password changed successfully.");
    }
    
    public boolean check_pwd(String s){
	    int u=0,l=0,n=0;
	    for(int i=0;i<s.length();i++){
	    	char c=s.charAt(i);
	    	if(Character.isLowerCase(c)) l++;
	    	else if(Character.isUpperCase(c)) u++;
	    	else n++;
	    }
	    if(u>1 && l>1 && n>1)  return true;
	    return false;
	}
    
    public void update(){
      try{
    	FileWriter f=new FileWriter("bank_db.txt");
	    for(Customer c:cust){
		  f.write(c.details());
	    }
	    f.close();
      }
      catch(Exception e){
    	System.out.println(e);
      }
    }
    
	public String encrypt(String pwd) {
		String s="";
		for(int i=0;i<pwd.length();i++) {
			char c=pwd.charAt(i);
			if(c=='z') c='a';
			else if(c=='Z') c='A';
			else if(c=='9') c='0';
			else c+=1;
			s+=c;
		}
		return s;
	}
	
	public int acc_exists(String name,String pwd){
		pwd=encrypt(pwd);
		for(Customer c:cust){
			if(c.getName().equals(name) && c.getEnc_pwd().equals(pwd)) return c.getCustId();
		}
		return 0;
	}
	
	public int randCustId() {
		int id=0;
		for(Customer c:cust) id=c.getCustId();
		return id+1;
	}
	
	public String randAccNo() {
		int id=0;
		for(Customer c:cust) id=c.getCustId();
		id+=1;
		return (id+"0"+id);
	}
	
	public void logfile(int id,String log){
		String file="Customer"+id+".txt";
	  try{
	    FileReader a=new FileReader(file);
	    BufferedReader abr=new BufferedReader(a);
	    FileWriter b=new FileWriter(file,true);
	    int trans_id=0;
	    String s,str="";
	    while((s=abr.readLine())!=null){
	    	str=s;
	    }
	    	String arr[]=str.split("  ");
	    	trans_id=Integer.parseInt(arr[0]);
	    trans_id+=1;
	    b.write("\n"+trans_id+log);
	    if(trans_id==5){
	    	System.out.println("You have performed 5 transactions.You should change the password for doing more transactions.");
	    	changepassword(id);
	    }
	    if(trans_id%10==0){
	    	trans_id+=1;
	    	double bal=0;
	    	for(Customer c:cust){
	    		if(c.getCustId()==id){
	    			bal=c.getBalance();
	    			c.setBalance(bal-100);
	    		}
	    	}
	    	b.write("\n"+trans_id+"  Maintenance Fee  100  "+(bal-100));
	    }
	    abr.close();
	    b.close();
	  }
	  catch(Exception e){
	  }
	}
}