package IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        //read from input stream
        int len = ((b.length-24)/8)+24;
        byte[] comp = new byte[len];
        in.read(comp);
        in.close();

        //copy the first 24 cells
        for (int i=0; i<24; i++)
        {
            b[i] = comp[i];
        }

        //4 cells represents byte --> int --> binary
        byte[] temp = new byte[4];
        int resInt;
        String resStr;
        for (int i=24; i<comp.length; i=i+4)
        {
            temp[0] = comp[i];
            temp[1] = comp[i+1];
            temp[2] = comp[i+2];
            temp[3] = comp[i+3];
            resInt = fromByteToInt(temp); //byte --> int
            resStr = fromDecimalToBinary(resInt); //int --> binary (string)
            for (int j=0; j<resStr.length(); j++)
            {
                String tempStr = ""+resStr.charAt(j); //each char --> string
                int digit = Integer.parseInt(tempStr); //string (with one char) --> int
                b[j+24] = (byte) digit; //int --> byte
            }
        }
        return 0;
    }

    private int fromByteToInt(byte[] bm)
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bm);
        return byteBuffer.getInt();
    }

    private String fromDecimalToBinary(int num)
    {
        String str = "";
        //convert to binary
        while (num > 0)
        {
            int digit = num % 2;
            num = num / 2;
            str = digit + str;
        }
        //add 0's to complete to 32
        if (str.length() < 32)
        {
            String zero = "";
            for (int i=0; i<(32-str.length()); i++)
            {
                zero = "0" + zero;
            }
            str = zero + str;
        }
        return str;
    }
}
