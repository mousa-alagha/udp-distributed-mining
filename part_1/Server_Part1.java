import java.net.*;
import java.util.Hashtable;
import java.io.*;

public class Server_Part1_22_5 {
	public static void main(String args[]) {
		
		DatagramSocket aSocket = null;
		
		try {
			
			aSocket = new DatagramSocket(20000);
			
			byte[] buffer = new byte[1000];
			
			System.out.println("Server is ready and accepting clients' requests ... ");

         	Hashtable<String, Integer> clients = new Hashtable();

			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);

				String clientid = request.getAddress().toString() + request.getPort();

				System.out.println("------------------------------");

				System.out.println("Clientid: " + clientid);

				Integer clientNumber = clients.get(clientid);

				if (clientNumber == null) { 
					clientNumber = 0;
					clients.put(clientid, clientNumber);
				}

				String block = new String(request.getData(), 0, request.getLength());
				String Data[] = block.split(",");
				int blockNumber = Integer.parseInt(Data[0]);
				String data = Data[1];
				int nonce = Integer.parseInt(Data[2]);
				String hash = Data[3];
				int LeadingZeros = Integer.parseInt(Data[4]);

				long startMiningTime = System.currentTimeMillis();
				String minedNonce = Mining(blockNumber, data, LeadingZeros);
				long endMiningTime = System.currentTimeMillis();
				long totalMiningTime = endMiningTime - startMiningTime;

				System.out.println("Total mining time: " + totalMiningTime);

				System.out.println(minedNonce);

				String newHash = StringUtil_Part1_22_5.applySha256(blockNumber + data + minedNonce);

				System.out.println(newHash);

				Block_Part1_22_5 updatedBlock = new Block_Part1_22_5(blockNumber, data, Integer.parseInt(minedNonce), newHash, LeadingZeros);
				String updatedBlockString = updatedBlock.toString();
				DatagramPacket reply = new DatagramPacket(updatedBlockString.getBytes(), updatedBlockString.length(),request.getAddress(), request.getPort());
				aSocket.send(reply);
			}
		} catch (SocketException e) {
			System.out.println("Error Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}

	public static String Mining(int blockNumber, String Data, int LeadingZeros) {
		String hashPrefix = "0".repeat(LeadingZeros);

		int nonce = 0;
		while (true) {
			String dataWithNonce = blockNumber + Data + nonce;
			String hash = StringUtil_Part1_22_5.applySha256(dataWithNonce);
			if (hash.startsWith(hashPrefix)) {
				return Integer.toString(nonce);
			}
			nonce++;
		}
	}

}
