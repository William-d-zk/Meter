package org.usst.electric.lab.meter.api.model;

import com.isahl.chess.king.base.util.CryptoUtil;
import com.isahl.chess.king.base.util.IoUtil;
import org.junit.jupiter.api.Test;

class DataDoTest
{
    @Test
    void testRegister()
    {
        String Hex = "0203C844466666409FE76D409FE76D409FE76D0000000000004281851F416B3333417051EB41928F5C4185C28F4281851F416B3333417051EB41928F5C4185C28F000000000000000000000000000000000000000040F851EB3FF0A3D73FD70A3D400B851F400147AE40F851EB3FF0A3D73FD70A3D400B851F400147AE0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009F2F";
        byte[] d = IoUtil.hex2bin(Hex);
        int d_len = d[2] & 0xFF;
        int length = d.length;
        System.out.printf("%d:[%d]\n", length, d_len);

        int crc16 = IoUtil.readUnsignedShort(d, d.length - 2);
        int crc16_modbus = CryptoUtil.crc16_modbus(d, 0, d.length - 2);
        System.out.println((crc16 ^ crc16_modbus) == 0);
        System.out.printf("%s,%s\n", Integer.toString(crc16, 2), Integer.toString(crc16_modbus, 2));

        for(int i = 3; ; ) {
            int read = IoUtil.readInt(d, i);
            float x = Float.intBitsToFloat(read);
            System.out.printf("%.6f\n", x);
            i += 4;
            if(i == d.length - 2) {break;}
        }

    }
}