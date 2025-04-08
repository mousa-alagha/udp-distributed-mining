import java.net.*;
import java.util.Hashtable;
import java.io.*;

public class Server_Part2_22_5{    
   public static void main(String args[]) { 
	   
	DatagramSocket aSocket = null;
	
    try{	   
    	aSocket = new DatagramSocket(20000 + Integer.parseInt(args[0]));  
	    byte[] buffer = new byte[1000]; 			
	   	System.out.println("Server " + args[0] + " is ready and accepting clients' requests ... ");
	   	
	   	Hashtable<String, Integer> clients = new Hashtable();
	    
		while(true){ 	
			
			DatagramPacket request = new DatagramPacket(buffer,buffer.length);
			aSocket.receive(request);	
			String clientid = request.getAddress().toString() + request.getPort();
			System.out.println("------------------------------");
			System.out.println("Clientid: " + clientid);
			Integer clientNumber = clients.get(clientid);
			
			if (clientNumber == null) { 
				clientNumber = 0;
				clients.put(clientid, clientNumber);	
			}
			
			String block = new String(request.getData(), 0 , request.getLength());
			String blockData[] = block.split(",");
			int blockNumber = Integer.parseInt(blockData[0]);
			String data = blockData[1];
			int nonce = Integer.parseInt(blockData[2]);
			int startNonce = Integer.parseInt(blockData[3]);
        	int endNonce = Integer.parseInt(blockData[4]);
			String hash = blockData[5];
			int LeadingZeros = Integer.parseInt(blockData[6]);
			
			InetAddress clientAddress = InetAddress.getByName(blockData[7].substring(1));
			int clientPort = Integer.parseInt(blockData[8]);;
			
			long startMiningTime = System.currentTimeMillis();
			String minedNonce = Mining(blockNumber, data, startNonce, endNonce, LeadingZeros);
			long endMiningTime = System.currentTimeMillis();
			
			String newHash = StringUtil_Part2_22_5.applySha256(blockNumber + data + minedNonce);
			
			long totalMiningTime = endMiningTime - startMiningTime;
			
			System.out.println("blockNumber: " + blockNumber + ", data: " + data + ", minedNonce: " + minedNonce + ", startNonce: " + startNonce + ", endNonce: " + endNonce+ "\n , hash: " + newHash + ", LeadingZeros: " + LeadingZeros);

			System.out.println("Total mining time: " + totalMiningTime);
						
			System.out.println("The mined nonce: " + minedNonce);

			System.out.println("The generated hash code: " + newHash);
			
			Block_Part2_22_5 updatedBlock = new  Block_Part2_22_5(blockNumber, data, Integer.parseInt(minedNonce), startNonce, endNonce, newHash, LeadingZeros, clientAddress, clientPort);
			String updatedBlockString = updatedBlock.toString();
			DatagramPacket reply = new DatagramPacket(updatedBlockString.getBytes(),updatedBlockString.length(),clientAddress,clientPort);
			aSocket.send(reply);
		}		
 	}catch (SocketException e){System.out.println("Error Socket: " + e.getMessage());
 	}catch (IOException e) {System.out.println("Error IO: " + e.getMessage());
	}finally {
		if(aSocket != null) aSocket.close();
	}
   }
   
   public static String Mining(int blockNumber, String Data, int startNonce, int endNonce, int LeadingZeros) {
       String hashPrefix = "0".repeat(LeadingZeros);
       
       int nonce = startNonce;
       while (nonce < endNonce) {
           String dataWithNonce = blockNumber + Data + nonce;
           String hash = StringUtil_Part2_22_5.applySha256(dataWithNonce);
           if (hash.startsWith(hashPrefix)) {
               return Integer.toString(nonce);
           }
           nonce++;
       }
       return "0";
   }
  
}
