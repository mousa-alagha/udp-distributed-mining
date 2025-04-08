import java.net.InetAddress;

public class Block_Part2_22_5 {

	int blockNumber;
	String data;
	int nonce;
	int startNonce;
	int endNonce;
	int LeadingZeros;
	String hash;
	InetAddress clientAddress;
	int clientPort;

	public Block_Part2_22_5(int blockNumber, String data, int nonce, int startNonce, int endNonce, String hash,
			int LeadingZeros, InetAddress clientAddress, int clientPort) {
		this.blockNumber = blockNumber;
		this.data = data;
		this.nonce = nonce;
		this.hash = hash;
		this.startNonce = startNonce;
		this.endNonce = endNonce;
		this.LeadingZeros = LeadingZeros;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}

	@Override
	public String toString() {
		return blockNumber + "," + data + "," + nonce + "," + startNonce + "," + endNonce + "," + hash + ","
				+ LeadingZeros + "," + clientAddress + "," + clientPort;
	}


	public int getBlockNumber() {
		return blockNumber;
	}

	public String getData() {
		return data;
	}

	public int getNonce() {
		return nonce;
	}

	public int getLeadingZeros() {
		return LeadingZeros;
	}

	public String getHash() {
		return hash;
	}

	public InetAddress getClientAddress() {
		return clientAddress;
	}

	public int getClientPort() {
		return clientPort;
	}

	public int getStartNonce() {
		return startNonce;
	}

	public int getEndNonce() {
		return endNonce;
	}


}
