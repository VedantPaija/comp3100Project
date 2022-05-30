import java.io.*;
import java.net.*;

public class stage2 {
	public static void main(String[] args) throws Exception{
		Socket s=new Socket("localhost",50000); //socket used for communication localhost-ip address: 127.0.0.1 port: 50000
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader dis=new BufferedReader(new
		InputStreamReader(s.getInputStream()));
		
		String strServer;
		int smallest = 0;
		int count = 0;
		
		String client = "HELO\n"; //intial handshake
		
		dout.write(client.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		//System.out.println("Message = " + strServer);	
		
		String username = System.getProperty("user.name");
		client = "AUTH "+username+"\n"; //user authentication
		
		dout.write(client.getBytes());
		dout.flush();

		strServer = dis.readLine();
		//System.out.println("Message = " + strServer);
		
		while(!(strServer.contains("NONE"))) { //terminates when no more jobs remaining 
			client = "REDY\n"; 
			
			dout.write(client.getBytes());
			dout.flush();
			
			strServer = dis.readLine(); //JOB
			//System.out.println("Job = " + strServer);
			
			if (strServer.contains("JOBN")) { //if server response includes a job
				String[] core = strServer.split(" ");
				int coree = Integer.parseInt(core[4]); 
				//System.out.println("Core: " + core[4]);
				
				client = "GETS Avail " + core[4] + " " + core[5] + " " + core[6] + "\n"; 
				
				dout.write(client.getBytes());
				dout.flush();
				
				strServer = dis.readLine(); //DATA
				//System.out.println("Data = " + strServer);
				
				client = "OK\n";
				
				dout.write(client.getBytes());
				dout.flush();
				
				String[] data = strServer.split(" ");
				
				if (Integer.parseInt(data[1]) != 0) { //if no servers available 
					int availServers = Integer.parseInt(data[1]); 
				
					String[] nRec = new String[availServers];
				
					for (int i = 0; i < availServers; i++) {
						strServer = dis.readLine();
						nRec[i] = strServer;
						//System.out.println(nRec[i]);
					}
				
					client = "OK\n";
				
					dout.write(client.getBytes());
					dout.flush();
				
					strServer = dis.readLine();
		
					String[] first = nRec[0].split(" "); //1st
		
					client = "SCHD " + count + " " + first[0] + " " + first[1] + "\n"; //schedule job to first available server given jobID, server type and serverID 
				
					dout.write(client.getBytes());
					dout.flush();
		
					strServer = dis.readLine();
					//System.out.println("Message = " + strServer); 
					count++; //increment jobID
				} else {
					client = "GETS Capable " + core[4] + " " + core[5] + " " + core[6] + "\n";
					
					dout.write(client.getBytes());
					dout.flush();
					
					strServer = dis.readLine(); //DATA
					//System.out.println("Data = " + strServer);
					
					client = "OK\n";
					
					dout.write(client.getBytes());
					dout.flush();
					
					strServer = dis.readLine();
					
					String[] capSer = strServer.split(" ");
					
					int capServ = Integer.parseInt(capSer[1]);
					
					String[] nRec = new String[capServ];
					
					for (int i = 0; i < capServ; i++) {
						strServer = dis.readLine();
						nRec[i] = strServer;
						//System.out.println(nRec[i]);
					}
					
					client = "OK\n";
				
					dout.write(client.getBytes());
					dout.flush();
				
					strServer = dis.readLine();
		
					String[] first = nRec[0].split(" "); //1st
		
					client = "SCHD " + count + " " + first[0] + " " + first[1] + "\n"; //schedule job to first capable server given jobID, server type and serverID
				
					dout.write(client.getBytes());
					dout.flush();
		
					strServer = dis.readLine();
					//System.out.println("Message = " + strServer); 
					count++;
					
				}
			}
		}
		String quit = "QUIT\n";
		
		dout.write(quit.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		//System.out.println("Message = " + strServer);
		
		dout.close();
		s.close();
	}
}
