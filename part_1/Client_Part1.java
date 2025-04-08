import java.net.*;
import java.io.*;
import java.util.Random;

public class Client_Part1_22_5 {

	public static void main(String args[]) {

		Random random = new Random();

		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket();

			int blockNumber = 1;
			int nonce = 0;
			String hash = null;
			int leadingZeros = 2;

			long clientExecTimeStart = System.currentTimeMillis();

			for (int i = 0; i < 10; i++) {

				int randomData = random.nextInt();

				String data = String.valueOf(randomData);

				Block_Part1_22_5 block = new Block_Part1_22_5(blockNumber, data, nonce, hash, leadingZeros);
				byte[] msg = block.toString().getBytes();
				InetAddress serverAddress = InetAddress.getByName("localhost");
				int serverPort = 20000;

				long blockExecTimeStart = System.currentTimeMillis();

				DatagramPacket request = new DatagramPacket(msg, msg.length, serverAddress, serverPort);
				aSocket.send(request);

				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);

				long blockExecTimeEnd = System.currentTimeMillis();

				long totalBlockExecTime = blockExecTimeEnd - blockExecTimeStart;

				System.out.println("-----------------------------");
				System.out.println("Total execution time per Block: " + totalBlockExecTime);

				String newBlock = new String(reply.getData(), 0, reply.getLength());
				String newData[] = newBlock.split(",");
				int newNonce = Integer.parseInt(newData[2]);
				String receivedHash = newData[3];

				String newHash = StringUtil_Part1_22_5.applySha256(blockNumber + data + newNonce);

				System.out.println("Mined nonce received from server: " + newNonce);

				System.out.println("Hash received from server: " + newHash);

				int compareHash = receivedHash.compareTo(newHash);

				if (compareHash == 0) {
					System.out.println("The hashes are equal");
				} else {
					System.out.println("The hashes are not equal");
				}

				blockNumber = blockNumber + 1;

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
