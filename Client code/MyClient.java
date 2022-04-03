import java.io.*;
import java.net.*;

//

public class MyClient {
	public static void main(String[] args) throws Exception{
	
		Socket s=new Socket("localhost",50000);
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader dis=new BufferedReader(new
		InputStreamReader(s.getInputStream()));
		
		String strServer;
		String client = "HELO\n";
		String gets;
		int count = 1;
		int[] lrrArray;
		int max = 0;
		int idx = 0;
		String lrrName = "";
		int totalServers = 0;
		
		dout.write(client.getBytes());
		dout.flush();

		strServer = dis.readLine();
		System.out.println("message= "+strServer);

		String username = System.getProperty("user.name");
		client = "AUTH "+username+"\n";
		dout.write(client.getBytes());
		dout.flush();

		strServer = dis.readLine();
		System.out.println("message= "+strServer);
		
		client = "REDY\n";
		dout.write(client.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		System.out.println("message= "+strServer);
// String	str3=(String)dis.readLine();
// System.out.println("message= "+str3);

// dout.write(("OK\n").getBytes());
// dout.flush();

// dout.write(("GETS All\n").getBytes());		
// dout.flush();

// String	str4=(String)dis.readLine();
// System.out.println("message= "+str4);

		
		//String strServer;
		//String client;
		client = "GETS All\n";
     		dout.write(client.getBytes());		
     		dout.flush();

     		strServer = dis.readLine();
     		System.out.println("message= "+strServer);
     		
     		gets = strServer;
     		System.out.println("Gets = " + gets);
     		int id1 = gets.indexOf("DATA") + 5;
     		int id2 = gets.indexOf(' ',gets.indexOf(' ') + 1);
     		System.out.println("1 : " + id1);
     		System.out.println("2 : " + id2);
     		String extract = gets.substring(id1, id2);
     		System.out.println("Extract = " + extract);
     		int getsInt = Integer.parseInt(extract);
     		System.out.println("Int = " + getsInt);
     		
     		lrrArray = new int[getsInt];
     			
     		client = "OK\n";
     		dout.write(client.getBytes());
     		dout.flush();

			
     			//System.out.println("message= "+strServer);
			
     		String[] nRec = new String[getsInt];
     		int i = 0;
     		//for (int i = 0; i < nRec.length; i++) {
     		while (i < getsInt) {
     			strServer = dis.readLine();
     			nRec[i] = strServer;
     			System.out.println("nRec: " + nRec[i]);
     			i++;
     		}
     		//}
     		
     		int[] coreNum = new int[getsInt];
     		String[] cores;
     		
     		for (int k = 0; k < lrrArray.length; k++) {
     			cores = nRec[k].split(" ");//nRec[k].substring(nRec[k].indexOf("inactive")+12, nRec[k].indexOf("inactive")+14);
     			//nRec[k].split(" ");
     			//if (cores.contains(" ")) {
     			//	cores = cores.substring(0,cores.length()-1);
     			//	lrrArray[k] = Integer.parseInt(cores);
     			//} else {
     			//	lrrArray[k] = Integer.parseInt(cores);
     			
     			//}
     			coreNum[k] = Integer.parseInt(cores[4]);
     			
     		}
     		
     		for (int j = 0; j < coreNum.length; j++) {
     			if (coreNum[j] > coreNum[idx]) {
     				max = j;
     				//idx = j;
     			}
     		}
     		
     		//lrrName = nRec[max].substring(0,lrrName.indexOf(' '));
     		String[] temp = nRec[max].split(" ");
     		lrrName = temp[0];
     		
     		for (int r = 0; r < nRec.length; r++) {
     			if (nRec[r].contains(lrrName)) {
     				totalServers++;
     			}
     		}
     		
     		int currServer = 1;
     		
     		System.out.println(lrrName);
     		System.out.println(totalServers);
     		
     		client = "OK\n";
     		dout.write(client.getBytes());
     		dout.flush();	
     		
     		strServer = dis.readLine();
     		System.out.println(strServer);
     		
     		//client = "SCHD 0 super-silk 0\n";
     		client = "SCHD 0 " + lrrName + " 0\n";
     		dout.write(client.getBytes());
     		dout.flush();
     		strServer = dis.readLine();
     		System.out.println(strServer);

		while(!(strServer.contains("NONE"))){
  // dout.write(("OK\n").getBytes());
  // dout.flush();

  			client = "REDY\n";
  			dout.write(client.getBytes());
  			dout.flush();
  //strServer = dis.readLine();
   			strServer = dis.readLine();
   			System.out.println("message= "+strServer);
			
			//client = "OK\n";
   			//dout.write(client.getBytes());
   			//dout.flush();
     
  			//for(count = 0; count< Integer.parseInt(nRec[8]); count++){
      // String str7=(String)dis.readLine();
      // System.out.println("message= "+str7);
				//client = "OK\n";
     				//dout.write(client.getBytes());
     				//dout.flush();
  				if(strServer.contains("JOBN")){    
     					client = "SCHD " + count + " " + lrrName + " " + currServer + "\n";
     					dout.write(client.getBytes());
     					dout.flush();
     					strServer = dis.readLine();
     					System.out.println(strServer);
     					count++;
     					currServer++;
     					if (currServer == totalServers) {
     						currServer = 0;
     					}
    //  strServer = dis.readLine();
    //  System.out.println("message= "+strServer);
    //  dout.flush();
  				}
  			//}
//dout.close();
//s.close();
		}
		client = "QUIT\n";
		dout.write(client.getBytes());
		dout.flush();
		dout.close();
		s.close();
	}
}
