package com.rjwl.reginet.gaotuo.entity;

import android.content.Context;

/**
 * Created by Administrator on 2018/2/1.
 */

//public class WifiCard {
//    public byte [] PhoneNum=new byte[6];
//    public int VillageNum=0;
//    public short DeviceID=0;
//    public byte [] Floor=new byte[8];
//    public byte [] DisDay=new byte[3];
//    public short Times=(short) 0xFBFB;
//    public short HomeNum=0;
//    public int PasKey=0;
//    public short Check=0;
//    public int Random=0;
//    public byte sendcheck=0;
//
//    public byte []CData=new byte[48];
//    public byte setVillage(String str)
//    {
//        this.VillageNum=Short.parseShort(str);
//        this.CData[4]=(byte) (this.VillageNum%0x100);
//        this.CData[5]=(byte) ((this.VillageNum/0x100)%0x100);
//        this.CData[6]=(byte) ((this.VillageNum/0x10000)%0x100);
//        this.CData[7]=(byte) ((this.VillageNum/0x1000000)%0x100);
//        return 0;
//    }
//    public byte setUserID(String str)
//    {
//        this.PhoneNum[0]=(byte)(0x10&(Character.getNumericValue(str.charAt(0))));
//        this.PhoneNum[1]=(byte)((Character.getNumericValue(str.charAt(1))*0x10)&(Character.getNumericValue(str.charAt(2))));
//        this.PhoneNum[2]=(byte)((Character.getNumericValue(str.charAt(3))*0x10)&(Character.getNumericValue(str.charAt(4))));
//        this.PhoneNum[3]=(byte)((Character.getNumericValue(str.charAt(5))*0x10)&(Character.getNumericValue(str.charAt(6))));
//        this.PhoneNum[4]=(byte)((Character.getNumericValue(str.charAt(7))*0x10)&(Character.getNumericValue(str.charAt(8))));
//        this.PhoneNum[5]=(byte)((Character.getNumericValue(str.charAt(9))*0x10)&(Character.getNumericValue(str.charAt(10))));
//        return 0;
//    }
//    public byte setDeviceID(String str)
//    {
//        this.DeviceID=Short.parseShort(str);
//        this.CData[8]=(byte) (this.DeviceID%0x100);
//        this.CData[9]=(byte) ((this.DeviceID/0x100)%0x100);
//        return 0;
//    }
//    public byte setFloor(String str)
//    {
//        int i;
//        //this.DeviceID[0]=Byte.parseByte(str);
//        this.Floor[Byte.parseByte(str.split(",", 8)[0])/8]=(byte)((this.Floor[Byte.parseByte(str.split(",", 8)[0])/8])|(0x01<<((Byte.parseByte(str.split(",", 8)[0])-1)%8)));
//
//        this.CData[10]=this.Floor[0];
//        this.CData[11]=this.Floor[1];
//        this.CData[12]=this.Floor[2];
//        this.CData[13]=this.Floor[3];
//        this.CData[14]=this.Floor[4];
//        this.CData[15]=this.Floor[5];
//        this.CData[16]=this.Floor[6];
//        this.CData[17]=this.Floor[7];
//        return 0;
//    }
//    public byte setDisDay(String str)
//    {
//        String strb;
//        strb=str.substring(4, 6);
//        this.DisDay[0]=Byte.parseByte(strb);
//        strb=str.substring(2, 4);
//        this.DisDay[1]=Byte.parseByte(strb);
//        strb=str.substring(0, 2);
//        this.DisDay[2]=Byte.parseByte(strb);
//
//        this.CData[18]=this.DisDay[0];
//        this.CData[19]=this.DisDay[1];
//        this.CData[20]=this.DisDay[2];
//        return 0;
//    }
//    public byte setTimes(String str)
//    {
//        this.Times=Short.parseShort(str);
//        this.CData[21]=(byte) (this.Times%0x100);
//        this.CData[22]=(byte) ((this.Times/0x100)%0x100);
//        return 0;
//    }
//    public byte setHome(String str)
//    {
//        this.HomeNum=Short.parseShort(str);
//        this.CData[23]=(byte) (this.Times%0x100);
//        this.CData[24]=(byte) ((this.Times/0x100)%0x100);
//        return 0;
//    }
//
//    public byte setPasKey(String str)
//    {
//        this.PasKey=Integer.parseInt(str);
//        this.CData[25]=(byte) (this.PasKey%0x100);
//        this.CData[26]=(byte) ((this.PasKey/0x100)%0x100);
//        this.CData[27]=(byte) ((this.PasKey/0x10000)%0x100);
//        this.CData[28]=(byte) ((this.PasKey/0x1000000)%0x100);
//        return 0;
//    }
//    public byte setCheck(String str)
//    {
//        this.Check=Short.parseShort(str);
//        this.CData[29]=(byte) (this.PasKey%0x100);
//        this.CData[30]=(byte) ((this.PasKey/0x100)%0x100);
//        return 0;
//    }
//    public byte setRandomNo(String str)
//    {
//        this.Random=Integer.parseInt(str);
//        this.CData[0]=(byte) (this.Random%0x100);
//        this.CData[1]=(byte) ((this.Random/0x100)%0x100);
//        this.CData[2]=(byte) ((this.Random/0x10000)%0x100);
//        this.CData[3]=(byte) ((this.Random/0x1000000)%0x100);
//        this.CData[31]=(byte) (this.Random%0x100);
//        this.CData[32]=(byte) ((this.Random/0x100)%0x100);
//        this.CData[33]=(byte) ((this.Random/0x10000)%0x100);
//        this.CData[34]=(byte) ((this.Random/0x1000000)%0x100);
//        return 0;
//    }


public class WifiCard {
    public byte[] UID;// = {(byte) 0x01, (byte) 0x38, (byte) 0x33, (byte) 0x57, (byte) 0x70, (byte) 0x23};
    //public byte[] UID2;
    public byte[] CData = new byte[48];
    public byte[] sendBuf = new byte[80];
    Context mContext;

    public WifiCard(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    public static byte DatKey[] = {
            //0   1    2    3    4    5    6    7    8    9    A    B    C    D    E    F
            (byte) 0x10, (byte) 0x60, (byte) 0xF8, (byte) 0xD3, (byte) 0x86, (byte) 0xAF, (byte) 0x45, (byte) 0xE0, (byte) 0xD5, (byte) 0xA9, (byte) 0x05, (byte) 0xFF, (byte) 0xF6, (byte) 0x47, (byte) 0x4C, (byte) 0x2A,//0
            (byte) 0xBB, (byte) 0x84, (byte) 0x61, (byte) 0x5E, (byte) 0x6E, (byte) 0x43, (byte) 0x01, (byte) 0x9A, (byte) 0xE8, (byte) 0x9F, (byte) 0x29, (byte) 0xA3, (byte) 0x5F, (byte) 0x64, (byte) 0x09, (byte) 0x8B,//1
            (byte) 0x16, (byte) 0x3B, (byte) 0x92, (byte) 0x82, (byte) 0xAA, (byte) 0x75, (byte) 0x34, (byte) 0xA8, (byte) 0xCE, (byte) 0xDC, (byte) 0x55, (byte) 0xBC, (byte) 0x90, (byte) 0x37, (byte) 0x62, (byte) 0x14,//2
            (byte) 0x4B, (byte) 0xAD, (byte) 0x67, (byte) 0x31, (byte) 0x8A, (byte) 0xCC, (byte) 0x17, (byte) 0x7D, (byte) 0xE4, (byte) 0xB7, (byte) 0x32, (byte) 0x1B, (byte) 0x40, (byte) 0x8E, (byte) 0xD9, (byte) 0x59,//3
            (byte) 0x41, (byte) 0xEB, (byte) 0xB2, (byte) 0x42, (byte) 0x95, (byte) 0x3F, (byte) 0x52, (byte) 0x6B, (byte) 0xC5, (byte) 0x98, (byte) 0x80, (byte) 0xE2, (byte) 0x1C, (byte) 0xC3, (byte) 0xA6, (byte) 0xE5,//4
            (byte) 0xCB, (byte) 0xB3, (byte) 0xD2, (byte) 0x7A, (byte) 0x5D, (byte) 0xF1, (byte) 0x13, (byte) 0x18, (byte) 0x3D, (byte) 0x76, (byte) 0x65, (byte) 0x5A, (byte) 0xEA, (byte) 0x02, (byte) 0x1D, (byte) 0xB4,//5
            (byte) 0x87, (byte) 0xCF, (byte) 0x1A, (byte) 0x0D, (byte) 0xA7, (byte) 0x0F, (byte) 0x50, (byte) 0xA0, (byte) 0xDE, (byte) 0xFD, (byte) 0xED, (byte) 0x91, (byte) 0x54, (byte) 0xD6, (byte) 0x3A, (byte) 0x11,//6
            (byte) 0xCD, (byte) 0x6F, (byte) 0xC8, (byte) 0xDA, (byte) 0x23, (byte) 0xC2, (byte) 0x1E, (byte) 0x77, (byte) 0x9E, (byte) 0xA4, (byte) 0x6C, (byte) 0x04, (byte) 0xD7, (byte) 0x7E, (byte) 0x2E, (byte) 0x2B,//7
            (byte) 0x63, (byte) 0xCA, (byte) 0xA5, (byte) 0x22, (byte) 0x06, (byte) 0x33, (byte) 0xBE, (byte) 0x4D, (byte) 0xB1, (byte) 0x9D, (byte) 0x88, (byte) 0xAC, (byte) 0xD1, (byte) 0xFC, (byte) 0x7C, (byte) 0xBD,//8
            (byte) 0x73, (byte) 0x53, (byte) 0x99, (byte) 0x94, (byte) 0x56, (byte) 0x44, (byte) 0x00, (byte) 0xB5, (byte) 0xF7, (byte) 0xC1, (byte) 0x70, (byte) 0xB0, (byte) 0xF5, (byte) 0xDD, (byte) 0xBA, (byte) 0x2D,//9
            (byte) 0x97, (byte) 0xD8, (byte) 0x25, (byte) 0x4A, (byte) 0x0C, (byte) 0xFA, (byte) 0xC4, (byte) 0x5C, (byte) 0x1F, (byte) 0xEE, (byte) 0x71, (byte) 0x21, (byte) 0xE6, (byte) 0xE1, (byte) 0x57, (byte) 0x78,//A
            (byte) 0x46, (byte) 0x79, (byte) 0x08, (byte) 0xF9, (byte) 0xD4, (byte) 0xE3, (byte) 0x51, (byte) 0xD0, (byte) 0xE7, (byte) 0x03, (byte) 0xDF, (byte) 0x36, (byte) 0x19, (byte) 0xC9, (byte) 0x26, (byte) 0x3C,//B
            (byte) 0x39, (byte) 0xC7, (byte) 0xB6, (byte) 0x0A, (byte) 0x15, (byte) 0xB8, (byte) 0x58, (byte) 0xF4, (byte) 0xE9, (byte) 0x81, (byte) 0xC6, (byte) 0x68, (byte) 0x38, (byte) 0x2C, (byte) 0xF0, (byte) 0x30,//C
            (byte) 0xA1, (byte) 0xC0, (byte) 0xFB, (byte) 0x20, (byte) 0x48, (byte) 0xF2, (byte) 0x3E, (byte) 0x27, (byte) 0xEC, (byte) 0x4E, (byte) 0xEF, (byte) 0x07, (byte) 0x72, (byte) 0x0B, (byte) 0x0E, (byte) 0x12,//D
            (byte) 0xDB, (byte) 0x35, (byte) 0x96, (byte) 0xA2, (byte) 0x8C, (byte) 0x89, (byte) 0x28, (byte) 0x6A, (byte) 0x9B, (byte) 0x5B, (byte) 0x9C, (byte) 0x8F, (byte) 0x74, (byte) 0xAB, (byte) 0x83, (byte) 0x93,//E
            (byte) 0x69, (byte) 0x85, (byte) 0x49, (byte) 0x2F, (byte) 0x7F, (byte) 0x4F, (byte) 0xBF, (byte) 0x8D, (byte) 0xAE, (byte) 0x6D, (byte) 0xF3, (byte) 0x66, (byte) 0x7B, (byte) 0xB9, (byte) 0xFE, (byte) 0x24,//F
    };

    public String byteToHstr(byte b) {
        String Astr = "0123456789ABCDEF";
        String str = "";

        str = str + Astr.charAt((b >> 4) & 0xf);
        str = str + Astr.charAt((b & 0x0f));

        return str;
    }

    public void setUID(byte[] UID) {
        this.UID = UID;
    }

    public int HstrTobyte(String str, byte[] ret) {
        int i;
        byte a = 0;
        String Astr = "0123456789ABCDEFabcdef";
        if (str.length() != 2)
            return -1;
        for (i = 0; i < 22; i++) {
            if (str.charAt(0) == Astr.charAt(i)) {
                if (i <= 16)
                    a = (byte) (i);
                else
                    a = (byte) (i - 6);
                break;
            }
        }
        a = (byte) ((a << 4) & 0xff);
        for (i = 0; i < 22; i++) {
            if (str.charAt(1) == Astr.charAt(i)) {
                if (i <= 16)
                    a = (byte) (a + (i));
                else
                    a = (byte) (a + (i - 6));
                break;
            }
        }
        ret[0] = a;
        return 0;
    }

    //字符串转换byte数组
    public int setCtrlBytes(String str) {
        int i;
        String Bstr;
        byte[] a = {(byte) 0};
        if (str.length() % 2 > 0) return -1;
        for (i = 0; i < (str.length() / 2); i++) {
            Bstr = str.substring(i * 2, i * 2 + 2);
            if (HstrTobyte(Bstr, a) != 0)
                return -1;
            else
                CData[i] = a[0];
        }
        return 0;
    }

    //传入设备编号
    public void GetCtrl(String sPID) {
        //根据设备编号从数据库查询到对应权限的HEX字符型数据,此处数据为示例
        //String str= "222222220F0000000100010000000000000001010101010101123000002A0A";
        String str = "2222222212345678432149000000000000001205010101010112300000E70B";
        //将权限数据赋值到Cdata
        setCtrlBytes(sPID);
        SetCtrlCheck(CData,UID);//权限校验
    }

    //随机数校验计算,pCheck即是输入也是输出，输入获得的随机数，输出计算后的校验值
    public int SetRandomCheck(byte pCtrl[], byte pCheck[]) {
        int i, sum = 0;
        byte c[] = {0, 0, 0, 0};
        for (i = 0; i < 31; i++) {
            sum = sum + (DatKey[(pCtrl[i] & 0xff)] & 0xff);
        }
        c[0] = (byte) ((sum + (DatKey[(pCheck[0] & 0xff)] & 0xff)) & 0xff);
        c[1] = (byte) ((sum + (DatKey[(pCheck[1] & 0xff)] & 0xff) + (c[0] & 0xff)) & 0xff);
        c[2] = (byte) ((sum + (DatKey[(pCheck[2] & 0xff)] & 0xff) + (c[1] & 0xff)) & 0xff);
        c[3] = (byte) ((sum + (DatKey[(pCheck[3] & 0xff)] & 0xff) + (c[2] & 0xff)) & 0xff);

        pCheck[0] = c[0];
        pCheck[1] = c[1];
        pCheck[2] = c[2];
        pCheck[3] = c[3];

        return 0;
    }

    //Ȩ�ް��ɷ�������������·�,�˺����ɷ��������
    public void SetCtrl() {
        //滚动码
        CData[0] = (byte) 0x22;
        CData[1] = (byte) 0x22;
        CData[2] = (byte) 0x22;
        CData[3] = (byte) 0x22;
        //设备号
        CData[4] = (byte) 0x12;
        CData[5] = (byte) 0x34;
        CData[6] = (byte) 0x56;
        CData[7] = (byte) 0x78;
        CData[8] = (byte) 0x43;
        CData[9] = (byte) 0x21;
        //楼层
        CData[10] = (byte) 0x13;
        CData[11] = (byte) 0x00;
        CData[12] = (byte) 0x00;
        CData[13] = (byte) 0x00;
        CData[14] = (byte) 0x00;
        CData[15] = (byte) 0x00;
        CData[16] = (byte) 0x00;
        CData[17] = (byte) 0x00;
        //有效期
        CData[18] = (byte) 0x12;
        CData[19] = (byte) 0x05;
        CData[20] = (byte) 0x01;
        //ʹ�ô���
        CData[21] = (byte) 0x01;
        CData[22] = (byte) 0x01;
        //ס�����ƺ�
        CData[23] = (byte) 0x01;
        CData[24] = (byte) 0x01;
        //ס�����ƿ�������
        CData[25] = (byte) 0x12;
        CData[26] = (byte) 0x30;
        CData[27] = (byte) 0x00;
        CData[28] = (byte) 0x00;
        //��У��
        SetCtrlCheck(CData, UID);//�˺�������Ȩ�ް���29����30�ֽ�
        //User.CData[29]=(byte)0x00;
        //User.CData[30]=(byte)0x00;
    }

    //权限包校验，
    int SetCtrlCheck(byte pCtrl[], byte pID[]) {
        int i;
        int sum = 0;
        for (i = 0; i < 29; i++) {
            sum = sum + (DatKey[pCtrl[i] & 0xff] & 0xff);
        }
        for (i = 0; i < 6; i++)
            sum = sum + (DatKey[pID[i] & 0xff] & 0xff);

        pCtrl[29] = (byte) (sum & 0xff);
        pCtrl[30] = (byte) ((sum >> 8) & 0xff);
        return 0;
    }

    //手机发送随机码到设备
    public void setdate(int cmd, String str, byte[] random) {
        byte check = 0;
        sendBuf[0] = (byte) 0xAA;
        sendBuf[2] = UID[0];
        check += UID[0];
        sendBuf[3] = UID[1];
        check += UID[1];
        sendBuf[4] = UID[2];
        check += UID[2];
        sendBuf[5] = UID[3];
        check += UID[3];
        sendBuf[6] = UID[4];
        check += UID[4];
        sendBuf[7] = UID[5];
        check += UID[5];
        //步骤B
        if (cmd == 1) {
            sendBuf[1] = (byte) 0x01;
            check += sendBuf[1];
            sendBuf[8] = (byte) 0x00;
            check += sendBuf[8];
            sendBuf[9] = (byte) ((0 - check) & 0xff);
            sendBuf[10] = (byte) 0xCC;
        } else if (cmd == 4) {//步骤e
            sendBuf[1] = (byte) 0x04;
            check += sendBuf[1];
            sendBuf[8] = (byte) 0x24;
            check += sendBuf[8];

            for (int i = 0; i < 31; i++) {
                sendBuf[i + 9] = CData[i];
                check += sendBuf[i + 9];
            }
            if (str != null && str.length() != 0)
                sendBuf[40] = (byte) (Integer.valueOf(str).intValue() & 0xff);
            else
                sendBuf[40] = 0;
            check += sendBuf[40];
            SetRandomCheck(CData, random);
            sendBuf[41] = random[0];
            check += random[0];
            sendBuf[42] = random[1];
            check += random[1];
            sendBuf[43] = random[2];
            check += random[2];
            sendBuf[44] = random[3];
            check += random[3];
            sendBuf[45] = (byte) ((0 - check) & 0xff);
            sendBuf[46] = (byte) 0xCC;
        } else if (cmd == 7) {
            byte[] bPID = {0, 0, 0, 0, 0, 0};
            sendBuf[1] = (byte) 0x07;
            check += sendBuf[1];
            sendBuf[8] = (byte) 0x06;
            check += sendBuf[8];

            byte[] a = {(byte) 0};
            if (str.length() % 2 > 0) return;
            for (int i = 0; i < (str.length() / 2); i++) {
                String Bstr = str.substring(i * 2, i * 2 + 2);
                if (HstrTobyte(Bstr, a) != 0) {
                    return;
                } else
                    bPID[i] = a[0];
            }
            HstrTobyte(str, bPID);

            sendBuf[9] = bPID[0];
            check += bPID[0];
            sendBuf[10] = bPID[1];
            check += bPID[1];
            sendBuf[11] = bPID[2];
            check += bPID[2];
            sendBuf[12] = bPID[3];
            check += bPID[3];
            sendBuf[13] = bPID[4];
            check += bPID[4];
            sendBuf[14] = bPID[5];
            check += bPID[5];
            sendBuf[15] = (byte) ((0 - check) & 0xff);
            sendBuf[16] = (byte) 0xCC;
        }/*else if(cmd==8){
                sendBuf[1]=(byte)0x08;check+=sendBuf[1];
				sendBuf[8]=(byte)0x07;check+=sendBuf[8];
				sendBuf[9]=bTime[0];check+=bTime[0];
				sendBuf[10]=bTime[1];check+=bTime[1];
				sendBuf[11]=bTime[2];check+=bTime[2];
				sendBuf[12]=bTime[3];check+=bTime[3];
				sendBuf[13]=bTime[4];check+=bTime[4];
				sendBuf[14]=bTime[5];check+=bTime[5];
				sendBuf[15]=bTime[6];check+=bTime[6];
				sendBuf[16]=(byte)((0-check)&0xff);
			    sendBuf[17]=(byte)0xCC;
			}*/
    }

}