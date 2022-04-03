import java.net.*;
import java.io.*;

class Client {
	public static void main(String args[])throws Exception{  
	    
	    Socket s=new Socket("localhost",50000);  
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
	    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	     
	    String username = System.getProperty("user.name");
	    
	    String client = "HELO\n";
	    dout.write(client.getBytes());
	    dout.flush();
	    
	    client = "AUTH" + username + "\n";
	    dout.write(client.getBytes());
	    dout.flush();
	    
	    int count = 0;
	    String strServer;
	    strServer = in.readLine();
	    
	    while (!(strServer.contains("NONE"))) {
	    	
	    	client = "REDY\n";
	    	dout.write(client.getBytes());
	    	dout.flush();
	    	strServer = in.readLine();
	    	
	    	dout.write(("GETS All\n").getBytes());
	    	dout.flush();
	    	
	    	dout.write(("OK\n").getBytes());
	    	dout.flush();
	   	
	    	if (strServer.contains("JOBN") && count < 10) {
	    		client = "SCHD " + count + " super-silk 0\n";
	    		dout.write(client.getBytes());
	    		dout.flush();
	    		
	    		//strServer = in.readLine();
	    		System.out.println(strServer);
	    		count++;
	    	}
	    }
	    
	    //System.out.println(strServer);
	    client = "QUIT\n";
	    dout.write(client.getBytes());
	    dout.flush();
	    
	    dout.close();  
	    s.close();  
	}}
