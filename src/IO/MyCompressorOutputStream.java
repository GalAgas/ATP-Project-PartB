package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream output)
    {
        out = output;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        //copies the first 24 bytes
        byte[] compressed = Arrays.copyOfRange(bytes, 0, 24);

        int size = bytes.length-24;
        int i = 24;
        while(size>0)
        {
            if (size >= 32)
            {
                compressed = convertBatch(bytes, i,32,compressed);
                size-=32;
                i+=32;
            }
            else
            {
                int batchSize = size%32;
                compressed = convertBatch(bytes, i, size, compressed);
                size-=batchSize;
            }
        }
        out.write(compressed);
    }

    private byte[] convertBatch(byte[] bytes, int startBatch, int batchSize, byte[] compressed)
    {
        //converts the batch of bytes to a binary string
        byte[] sliceBatch = Arrays.copyOfRange(bytes, startBatch, startBatch+batchSize);
        String bBinary  = Arrays.toString(sliceBatch);
        String sBinary = bBinary.replaceAll("[^0-9.]", "");

        if(batchSize<32)
        {
            int additionSize = 32-batchSize;
            sBinary += String.join("", Collections.nCopies(additionSize,"0"));
        }

        //converts the 32 binary string to a decimal int
        int decimal = (int)Long.parseLong(sBinary,2);

        //converts the decimal int to 4 bytes
        ByteBuffer bytes4 = ByteBuffer.allocate(4);
        bytes4.putInt(decimal);

        //concatenates the 4 bytes to the compressed byte array
        byte[] res = new byte[compressed.length + 4];
        System.arraycopy(compressed, 0, res, 0, compressed.length);
        System.arraycopy(bytes4.array(), 0, res, compressed.length, 4);
        return res;
    }
}
