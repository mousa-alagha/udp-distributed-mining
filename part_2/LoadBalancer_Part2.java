import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class LoadBalancer_Part2_22_5 {

	public static void main(String args[]) {
		
		DatagramSocket aSocket = null;
		try {
			
			aSocket = new DatagramSocket(20000);
			byte[] buffer = new byte[1000];
			System.out.println("Load Balancer is ready and accepting clients' requests ... ");

			while (true) {
				
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				
				int numOfServers = Integer.parseInt(args[0]);
				int maxRange = Integer.MAX_VALUE / numOfServers;
				
				String block = new String(request.getData(), 0, request.getLength());
				String blockData[] = block.split(",");
				int blockNumber = Integer.parseInt(blockData[0]);
				String data = blockData[1];
				int nonce = Integer.parseInt(blockData[2]);
				
				int startNonce = 1;
				int endNonce = maxRange;
				
				String hash = blockData[5];
				int leadingZeros = Integer.parseInt(blockData[6]);
				
				InetAddress serverAddress = InetAddress.getByName("localhost");
				int serverPort = 20001;
				
				for (int i = 1; i <= numOfServers; i++) {

					InetAddress clientAddress = request.getAddress();
					int clientPort = request.getPort();
					
					System.out.println("-----------------------------------");
					System.out.println("Sending task to server " + serverAddress + ":" + serverPort);
					
					Block_Part2_22_5 updatedBlock = new Block_Part2_22_5(blockNumber, data, nonce, startNonce, endNonce,
							hash, leadingZeros, clientAddress, clientPort);
					String updatedBlockString = updatedBlock.toString();
					System.out.println("Task data: " + updatedBlockString);
					
					DatagramPacket Task = new DatagramPacket(updatedBlockString.getBytes(), updatedBlockString.length(),
							serverAddress, serverPort);
					aSocket.send(Task);
					
					startNonce = startNonce + maxRange;
					endNonce = endNonce + maxRange;
					serverPort++;

					System.out.println("Task sent successfully to server " + serverAddress + ":" + serverPort);
				}

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

}
