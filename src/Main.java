import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		CustomerManagement cm=new CustomerManagement();
		System.out.println("Welcome!");
		try {
		  FileReader fr=new FileReader("bank_db.txt");
		  BufferedReader br=new BufferedReader(fr);
		  FileWriter fw=new FileWriter("bank_db.txt",true);
		  String str;
		  while((str=br.readLine())!=null) {
			if(str.equals("")) break;
			String arr[]=str.split("  ");
			cm.addCustomer(Integer.parseInt(arr[0]),arr[1],arr[2],Double.parseDouble(arr[3]),arr[4]);
		  }
		while(true){
		  System.out.println("Enter 1 for new customer,2 for customer already,0 to exit:");
		  int n=sc.nextInt();
		  sc.nextLine();
		  if(n==1) {
			  System.out.println("Enter name:");
			  String name=sc.nextLine();
			  String pwd;
			  while(true){
			    System.out.println("Enter password:");
			    pwd=sc.next();
			    if(cm.check_pwd(pwd)) break;
			    else System.out.println("Password should contain atleast 2 upper-case,2 lowercase and 2 number");
		      }
			  System.out.println("Re-enter password:");
			  String rpwd=sc.next();
			  if(!pwd.equals(rpwd)) System.out.println("Password does not match");
			  else {
				  String password=cm.encrypt(pwd);
				  int id=cm.randCustId();
				  String acc=cm.randAccNo();
				  cm.addCustomer(id,acc,name,10000,password);
				  String s=id+"  "+acc+"  "+name+"  10000  "+password;
				  fw.write(s);
				  System.out.println("Account created successfully");
				  String td="Customer"+id+".txt";
				  File f=new File(td);
				  f.createNewFile();
				  FileWriter x=new FileWriter(f,true);
				  x.write("--Account Statement--\n--Name-"+name+"--\nAccount No-"+acc+"--\nCustomer Id-"+id+"--");
				  x.write("\nTransId  TransType  Amount  Balance");
				  x.write("\n----------------------------------------");
				  x.write("\n1  Opening Account  10000  10000");
				  x.close();
			  }
		  }
		  else if(n==2){
			  System.out.println("Enter name:");
			  String name=sc.nextLine();
			  System.out.println("Enter password:");
			  String pwd=sc.next();
			  int id=cm.acc_exists(name,pwd);
			  if(id!=0){
				  System.out.println("Account Found.");
				  while(true){
				    System.out.println("\nEnter 1 to Deposit,2 to Withdraw,3 to Money Transfer,4 to Change Password,5 to Tansaction History,0 to EXIT:");
				    int m=sc.nextInt();
				    if(m==1){
					  cm.deposit(id);
				    }
				    else if(m==2){
					  cm.withdraw(id);
				    }
				    else if(m==3){
				      cm.transfer(id);
				    }
				    else if(m==4){
					  cm.changepassword(id);
				    }
				    else if(m==5){
				      String log="Customer"+id+".txt";
				      FileReader lg=new FileReader(log);
				      BufferedReader blg=new BufferedReader(lg);
				      String c;
				      while((c=blg.readLine())!=null){
				    	  System.out.println(c);
				      }
				      blg.close();
				    }
				    else if(m==0)  break;
				    cm.update();
				  }
			  }
			  else{
				  System.out.println("Account not exists.Check for correct name and password.");
			  }
		  }
		  else if(n==0) break;
		  
		  cm.update();
		}
          fr.close();
          br.close();
          fw.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		sc.close();
	}
}
