import java.net.*;
import java.io.*;
import java.util.Random;

public class Client_Part2_22_5 {

	public static void main(String args[]) {

		Random random = new Random();

		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket();
			
			long clientExecTimeStart = System.currentTimeMillis();

			int randomData = random.nextInt();
			
			String data = String.valueOf(randomData);
			int blockNumber = 1;
			int nonce = 0;
			int startNonce = 0;
			int endNonce = 0;
			String hash = null;
			int leadingZeros = 4;
			
			InetAddress loadBalancerAddress = InetAddress.getByName("localhost");
			int loadBalancerPort = 20000;
			InetAddress clientAddress = null;
			int clientPort = 0;

			Block_Part2_22_5 block = new Block_Part2_22_5(blockNumber, data, nonce, startNonce, endNonce, hash, leadingZeros, clientAddress, clientPort);
			byte[] msg = block.toString().getBytes();

			long blockExecTimeStart = System.currentTimeMillis();

			DatagramPacket request = new DatagramPacket(msg, msg.length, loadBalancerAddress, loadBalancerPort);
			aSocket.send(request);

			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);

			InetAddress serverAddress = reply.getAddress();
			int serverPort = reply.getPort();

			System.out.println("Received from server address: " + serverAddress);
			System.out.println("Received from server port: " + serverPort);

			long blockExecTimeEnd = System.currentTimeMillis();

			long totalBlockExecTime = blockExecTimeEnd - blockExecTimeStart;

			System.out.println("-----------------------------");
			System.out.println("Total execution time per Block: " + totalBlockExecTime);

			
			String newBlock = new String(reply.getData(), 0, reply.getLength());
			String newData[] = newBlock.split(",");
			int newNonce = Integer.parseInt(newData[2]);
			String receivedHash = newData[5];
			
			String newHash = StringUtil_Part2_22_5.applySha256(blockNumber + data + newNonce);

			System.out.println("Mined nonce received from server: " + newNonce);

			System.out.println("Hash received from server: " + newHash);
			
			int compareHash = receivedHash.compareTo(newHash);

			if (compareHash == 0) {
				System.out.println("The hashes are equal");
			} else {
				System.out.println("The hashes are not equal");
			}

			
			long clientExecTimeEnd = System.currentTimeMillis();
			long totalClientExecTime = clientExecTimeEnd - clientExecTimeStart;
			System.out.println("Total execution time per client: " + totalClientExecTime);

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
