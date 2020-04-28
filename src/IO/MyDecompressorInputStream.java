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
        int len = ((int) Math.ceil((b.length-24) / 32.0))*4+24;
        byte[] comp = new byte[len];
        in.read(comp);
        in.close();

        //copy the first 24 cells to b
        for (int i=0; i<24; i++)
        {
            b[i] = comp[i];
        }

        //4 cells represents byte --> int --> binary
        byte[] temp = new byte[4];
        int resInt;
        String resStr;
        int dif = b.length-24;
        int index = 0;
        for (int i=24; i<comp.length; i=i+4)
        {
            //byte --> int
            temp[0] = comp[i];
            temp[1] = comp[i+1];
            temp[2] = comp[i+2];
            temp[3] = comp[i+3];
            ByteBuffer byteBuffer = ByteBuffer.wrap(temp);
            resInt = byteBuffer.getInt();
            //int --> binary (string)
            resStr = Integer.toBinaryString(resInt);
            //checks the length of the string
            if (dif >=32 ) {
                dif = dif - 32;
                if (resStr.length()<32)
                    resStr = addZero (resStr, 32-resStr.length());
            }
            else
            {
                if (resStr.length()<dif)
                    resStr = addZero (resStr, dif-resStr.length());
            }
            //more converts
            for (int j=0; j<resStr.length(); j++)
            {
                String tempStr = ""+resStr.charAt(j); //each char --> string
                int digit = Integer.parseInt(tempStr); //string (with one char) --> int
                b[index+24] = (byte) digit; //int --> byte
                index++;
            }
        }
        return 0;
    }

    private String addZero (String str, int num)
    {
        String zero="";
        for (int i=0; i<num; i++)
            zero +="0";
        str = zero + str;
        return str;
    }
}
