public class Block_Part1_22_5 {

	int blockNumber;
	int nonce;
	String data;
	int LeadingZeros;
	String hash;

	public Block_Part1_22_5(int blockNumber, String data, int nonce, String hash, int LeadingZeros) {
		this.blockNumber = blockNumber;
		this.data = data;
		this.nonce = nonce;
		this.hash = hash;
		this.LeadingZeros = LeadingZeros;
	}

	@Override
	public String toString() {
		return blockNumber + "," + data + "," + nonce + "," + hash + "," + LeadingZeros;
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


}
