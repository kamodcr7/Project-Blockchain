import java.io.Serializable;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class Blockchain implements Serializable {
    ArrayList<Block> chain;
    ArrayList<Transaction> pendingTransactions;
    private int difficulty = 0;
    String ID;

    public Blockchain(String ID, int diff) throws NoSuchAlgorithmException, SocketException {
        this.ID = ID;
        chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        setDifficulty(diff);
        chain.add(generateGenesisBlock());

    }

    private Block generateGenesisBlock() throws NoSuchAlgorithmException {
        Block generate = new Block("null",pendingTransactions,"27/05/1999",difficulty);
        generate.setPrevHash(null);
        generate.calcHash();
        return generate;
    }
    void setDifficulty(int diff){
        this.difficulty = diff;
    }

    void add(Block bk) throws NoSuchAlgorithmException {
        // Addition code for a new block
        // Might need to check if prev hash == bk prev hash
        bk.setPrevHash(chain.get((int) (chain.size()-1.)).getCurrentHash());//This should be in mining code.
        bk.calcHash();
        this.chain.add(bk);
    }

    boolean isValid() throws NoSuchAlgorithmException {
        String hashTarget = new String(new char[difficulty]);

        // Checks for the validity of the block-chain.
        for (int i = chain.size()-1; i>0; i--){
            //Compare current registered hash and calculated hash
            if (chain.get(i).getCurrentHash().equals(chain.get(i).calcHash())){
                return false;
            }

            //Compare previous registered hash and previous hash
            if ((!chain.get(i).getPrevHash().equals(chain.get(i-1).calcHash()))){
                return false;
            }

            //To check is block is mined
            if (!chain.get(i).getCurrentHash().substring(0,difficulty).equals(hashTarget)){
                System.out.println("Block" + i +"is not Mined");
                return false;
            }
        }
        return true;
    }
}

