import java.io.*;
import java.nio.charset.StandardCharsets;


public class CAapp {
    int clockCycles=0;
    InstructionMemory  inst = new InstructionMemory();
    pc PC=new pc();
    Register r =new Register();
    DataMemory m = new DataMemory();
    SREGRegister S1= new SREGRegister();
    public void readtoMemory(String filepath) throws IOException
    {
        File file = new File(filepath);

        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(file));
            String string;
            while((string = br.readLine()) != null)
            {
                String[] x = string.split(" ");
                String s = "";
                switch(x[0])
                {
                    case"ADD" :
                        s = "0000";
                        break;

                    case"SUB" :
                        s = "0001";
                        break;

                    case"MUL" :
                        s = "0010";
                        break;

                    case"LDI" :
                        s = "0011";
                        break;

                    case"BEQZ" :
                        s = "0100";
                        break;

                    case"AND" :
                        s = "0101";
                        break;

                    case"OR" :
                        s = "0110";
                        break;

                    case"JR" :
                        s = "0111";
                        break;

                    case"SLC" :
                        s = "1000";
                        break;

                    case"SRC" :
                        s = "1001";
                        break;

                    case"LB" :
                        s = "1010";
                        break;

                    case"SB" :
                        s = "1011";
                        break;
                }
                String r = x[1].substring(1,x[1].length());
                int k = Integer.parseInt(r);
                String t2="";
                if(k>63){
                    throw new IOException();
                }
                String t = Integer.toBinaryString(k);
                while(t.length()<6)
                {
                    t = "0" + t;
                }
                if(x[2].substring(0,1).equals("R")) {
                    String r2 = x[2].substring(1, x[2].length());
                    int k2 = Integer.parseInt(r2);
                    if (k > 63) {
                        throw new IOException();
                    }
                    t2 = Integer.toBinaryString(k2);
                    while (t2.length() < 6) {
                        t2 = "0" + t2;
                    }
                }
                else {
                    int k2 = Integer.parseInt(x[2]);
                    t2 =convertTwosComplement(k2);
                    while (t2.length() < 6) {
                        t2 = "0" + t2;
                    }
                }
                s = s + t + t2;
                String [] y = inst.getMemory();
                y[inst.getLastspaceused()] = s;
                inst.setMemory(y);
                inst.setLastspaceused(inst.getLastspaceused() + 1);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static  String convert2(int opcode ,int rs,int rti){

        int [] j ={opcode,rs,rti};
        String s ="";
        switch(j[0])
        {
            case 0 :
                s = "ADD";
                break;

            case  1:
                s = "SUB";
                break;

            case 2 :
                s = "MUL";
                break;

            case 3 :
                s = "LDI";
                break;

            case 4 :
                s = "BEQZ";
                break;

            case 5 :
                s = "AND";
                break;

            case 6 :
                s = "OR";
                break;

            case 7 :
                s = "JR";
                break;

            case 8 :
                s = "SLC";
                break;

            case 9 :
                s = "SRC";
                break;

            case 10 :
                s = "LB";
                break;

            case 11 :
                s = "SB";
                break;
        }
        if(j[0]==3 || j[0]==4 || j[0]==8 ||j[0]==9 || j[0]==10 || j[0]==11 ){
            s=s+" "+"R"+rs+" "+rti;
            return s;
        }
        else {
            s = s + " " + "R" + rs + " " + "R" + rti;
            return s;
        }


    }
    public static  String convert(String r){

        int opcode = 0;  // bits31:26
        int rs = 0;      // bits25:21
        int rti = 0;      // bit20:16
        opcode= Integer.parseInt(r.substring(0, 4),2);
        String valueOfRs= r.substring(4, 10);
        rs= getTwosComplement(valueOfRs);



        String valueOfRt= r.substring(10, 16);
        rti=getTwosComplement(valueOfRt);

        int [] j ={opcode,rs,rti};
        String s ="";
        switch(j[0])
        {
            case 0 :
                s = "ADD";
                break;

            case  1:
                s = "SUB";
                break;

            case 2 :
                s = "MUL";
                break;

            case 3 :
                s = "LDI";
                break;

            case 4 :
                s = "BEQZ";
                break;

            case 5 :
                s = "AND";
                break;

            case 6 :
                s = "OR";
                break;

            case 7 :
                s = "JR";
                break;

            case 8 :
                s = "SLC";
                break;

            case 9 :
                s = "SRC";
                break;

            case 10 :
                s = "LB";
                break;

            case 11 :
                s = "SB";
                break;
        }
        if(j[0]==3 || j[0]==4 || j[0]==8 ||j[0]==9 || j[0]==10 || j[0]==11 ){
            s=s+" "+"R"+rs+" "+rti;
            return s;
        }
        else {
            s = s + " " + "R" + rs + " " + "R" + rti;
            return s;
        }


    }
    public void loop(){
        String F=fetch();
        PC.setPC(PC.getPC() - 1);
        int [] d =new int[3];
        d = decode(F);
        boolean flag1 = false;
        boolean flag2 = false;
        while(PC.getPC()<inst.getLastspaceused())
        {

            clockCycles++;
            System.out.println("Clockcycle:"+clockCycles);

                if (PC.getPC() == 0 || flag1 == true)
                {
                    F = fetch();
                    String l=convert(F);
                    System.out.println("Fetch:"+l);

                    flag1 = false;
                }
                else if (PC.getPC() == 1 || flag2 ==true)
                {
                    String tmp = F;
                    F = fetch();
                    String l=convert(F);
                    System.out.println("Fetch:"+l);
                    d = decode(tmp);
                    String ll=convert( tmp);
                    System.out.println("Decode:"+ll);
                    flag2 = false;
                }
                else
                {
                    String tmp = F;
                    int[] tmp2 = d;
                    F = fetch();
                    String l=convert(F);
                    System.out.println("Fetch:"+l);
                    d = decode(tmp);
                    String ll=convert(tmp);
                    System.out.println("Decode:"+ll);
                    if(tmp2[0] == 4 && r.getR()[tmp2[1]].getRegisterSpace().equals("00000000"))
                    {
                        PC.setPC(PC.getPC() - 2);
                        execute(tmp2[0], tmp2[1], tmp2[2]);
                        String lll=convert2(tmp2[0],+tmp2[1],tmp2[2]);
                        System.out.println("Execute:"+lll);
                        flag1 = true;
                        flag2 = true;
                    }
                    else   if(tmp2[0] == 7)
                    {
                        PC.setPC(PC.getPC() - 2);
                        execute(tmp2[0], tmp2[1], tmp2[2]);
                        String lll=convert2(tmp2[0],tmp2[1],tmp2[2]);
                        System.out.println("Execute:"+lll);
                        flag1 = true;
                        flag2 = true;
                    }
                    else{
                        execute(tmp2[0], tmp2[1], tmp2[2]);
                        String lll=convert2(tmp2[0],tmp2[1],tmp2[2]);
                        System.out.println("Execute:"+lll);
                    }
                }
            }
        System.out.println("Clockcycle:"+(clockCycles+1));
        int [] tmp2  = d;
        d= decode(F);
        String ll=convert(F);
        System.out.println("Decode:"+ll);
        execute(tmp2[0],tmp2[1],tmp2[2]);
        String lll=convert2(tmp2[0],tmp2[1],tmp2[2]);
        System.out.println("Execute:"+lll);

        System.out.println("Clockcycle:"+(clockCycles+2));

        execute(d[0],d[1],d[2]);
        String llll=convert2(d[0],d[1],d[2]);
        System.out.println("Execute:"+llll);

        clockCycles=clockCycles+2;
    }
    public  String fetch()
    {
        String s =inst.getMemory()[PC.getPC()];
        int i = PC.getPC()+1;
        PC.setPC(i);
        return s;
    }

    public  int[] decode(String s)
    {

        int opcode = 0;  // bits31:26
        int rs = 0;      // bits25:21
        int rti = 0;      // bit20:16
        opcode= Integer.parseInt(s.substring(0, 4),2);
        String valueOfRs= s.substring(4, 10);
        rs= getTwosComplement(valueOfRs);



        String valueOfRt= s.substring(10, 16);
        rti=getTwosComplement(valueOfRt);

        int [] j ={opcode,rs,rti};
        return j ;
    }
    public void execute(int opcode,int rs,int rti){


        switch(opcode)
        {
            case 0 :
                ADD(rs,rti);
                break;

            case 1 :
                SUB(rs,rti);
                break;

            case 2 :
                MUL(rs,rti);
                break;

            case 3 :
                LDI(rs,rti);
                break;

            case 4 :
                BEQZ(rs,rti);
                break;

            case 5 :
                AND(rs,rti);
                break;

            case 6 :
                OR(rs,rti);
                break;

            case 7 :
                JR(rs,rti);
                break;

            case 8 :
                SLC(rs,rti);
                break;

            case 9 :
                SRC(rs,rti);
                break;

            case 10 :
                LB(rs,rti);
                break;

            case 11 :
                SB(rs,rti);
                break;

        }
    }

    private void MUL( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y =getTwosComplement(r.getR()[rti].getRegisterSpace());

        int  zz= x*y;
        System.out.println("MUL"+" "+x +" "+ y +"="+zz);
        String z= convertTwosComplement4(zz);
        GPRSRegister[] t= r.getR();
        t[rs].setRegisterSpace(z);
        System.out.println("Value of R"+rs+"="+getTwosComplement(z) );

        r.setR(t);
        if(zz>128) {
            S1.setCarryFlag(1);
        }
        else{
            S1.setCarryFlag(0);
        }
        int o=x*y;
        if ((byte)o <0){
            S1.setNegativeFlag(1);
        }
        else{
            S1.setNegativeFlag(0);
        }
        if(o==0){
            S1.setZeroFlag(1);
        }
        else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }

    private void LDI( int rs, int rti) {

        GPRSRegister[] t= r.getR();
        String s =  convertTwosComplement4(rti);
        t[rs].setRegisterSpace(s);
        System.out.println("LDI"+" "+"R"+rs+ " "+ rti );
        System.out.println("Value of R"+rs+"="+rti);
        r.setR(t);
    }

    private void BEQZ( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        if(x==0){ //check
            PC.setPC(PC.getPC()+rti);
            System.out.println("BEQZ"+" "+x +" "+ rti +"="+PC.getPC());
            System.out.println("Value of PC"+"="+(PC.getPC()));
        }
    }
    private void AND( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y = getTwosComplement(r.getR()[rti].getRegisterSpace());
        int z= x&y;
        System.out.println("AND"+" "+x +" "+ y +"="+z);
        GPRSRegister[] t= r.getR();
        String h= convertTwosComplement4(z);
        t[rs].setRegisterSpace(h);
        System.out.println("Value of R"+rs+"="+getTwosComplement(h));
        r.setR(t);
        if ((byte)z <0){
            S1.setNegativeFlag(1);
        }else{
            S1.setNegativeFlag(0);
        }
        if(z==0){
            S1.setZeroFlag(1);
        }else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }

    private void OR( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y = getTwosComplement(r.getR()[rti].getRegisterSpace());
        int z= x|y;
        System.out.println("OR"+" "+x +" "+ y +"="+z);
        GPRSRegister[] t= r.getR();
        String h= convertTwosComplement4(z);
        t[rs].setRegisterSpace(h);
        System.out.println("Value of R"+rs+"="+getTwosComplement(h));

        r.setR(t);
        if ((byte)z <0){
            S1.setNegativeFlag(1);
        }
        else{
            S1.setNegativeFlag(0);
        }
        if(z==0){
            S1.setZeroFlag(1);
        }else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }
    private void JR( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y = getTwosComplement(r.getR()[rti].getRegisterSpace());
        String xx = Integer.toBinaryString(x);
        String yy = Integer.toBinaryString(y);
        String zs= xx+""+yy;
        int z=Integer.parseInt(zs,2);
        PC.setPC(z);
        System.out.println("JR"+" "+x +" "+ y +"="+z);
        System.out.println("Value of PC"+"="+z);

    }

    private void SLC( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int z= x<<rti;
        GPRSRegister[] t= r.getR();
        System.out.println("SLC"+" "+x +" "+ rti +"="+z);
        String h= convertTwosComplement4(z);
        t[rs].setRegisterSpace(h);
        System.out.println("Value of R"+rs+"="+getTwosComplement(h));


        r.setR(t);
        if ((byte)z <0){
            S1.setNegativeFlag(1);
        }else{
            S1.setNegativeFlag(0);
        }
        if(z==0){
            S1.setZeroFlag(1);
        }
        else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }
    private void SRC( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int z= x>>>rti;
        GPRSRegister[] t= r.getR();
        String h= convertTwosComplement4(z);
        System.out.println("MUL"+" "+x +" "+ rti +"="+z);
        t[rs].setRegisterSpace(h);
        System.out.println("Value of R"+rs+"="+getTwosComplement(h));
        r.setR(t);
        if ((byte)z <0){
            S1.setNegativeFlag(1);
        }else{
            S1.setNegativeFlag(0);
        }
        if(z==0){
            S1.setZeroFlag(1);
        }else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }
    private void LB( int rs, int rti) {
        int s =m.getMemory()[rti];
        GPRSRegister[] t= r.getR();
        String rr =Integer.toBinaryString(s); /// OR 1
        System.out.println("LB"+" "+"R"+rs +" "+ s );
        while(rr.length()<8){
            rr="0"+rr;
        }
        t[rs].setRegisterSpace(rr);
        System.out.println("Value of R"+rs+"="+getTwosComplement(rr));
        r.setR(t);
    }
    private void SB( int rs, int rti) {
        int  x=Integer.parseInt(r.getR()[rs].getRegisterSpace(),2);
        System.out.println("SB"+" "+"Memory of:"+rs +" "+ x );
        if(x>-128 || x<127) {

            int[] u = m.getMemory();
            u[rti] = x;
            System.out.println("Memory of :"+rti+"="+x);
            m.setMemory(u);
        }
    }
    private void SUB( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y = getTwosComplement(r.getR()[rti].getRegisterSpace());
        int  zz= x-y;
        System.out.println("SUB"+" "+x +" "+ y +"="+zz);
        String s2 =bitWiseAddition(r.getR()[rs].getRegisterSpace(),r.getR()[rti].getRegisterSpace());
        String z = convertTwosComplement4(zz);
        GPRSRegister[] t= r.getR();
        t[rs].setRegisterSpace(z);
        System.out.println("Value of R"+rs+"="+getTwosComplement(z));
        r.setR(t);
        int o = x-y;
        if(s2.length()>8 && s2.charAt(0)=='1') {
            S1.setCarryFlag(1);
        }
        else{
            S1.setCarryFlag(0);
        }

        String p= convertTwosComplement4(o);
        if((x >=0 && y>=0 && (p.charAt(0)+"").equals("1")||(x<0 && y<0 && (p.charAt(0)+"").equals("0")))){
            S1.setOverFlowFlag(1);
        }else{
            S1.setOverFlowFlag(0);
        }
        if ((byte)o <0){
            S1.setNegativeFlag(1);
        }else{
            S1.setNegativeFlag(0);
        }
        S1.setSignFlag( S1.getNegativeFlag()^ S1.getOverFlowFlag());
        if(o==0){
            S1.setZeroFlag(1);
        }else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());

    }
    private void ADD( int rs, int rti) {
        int x= getTwosComplement(r.getR()[rs].getRegisterSpace());
        int y = getTwosComplement(r.getR()[rti].getRegisterSpace());

        String s2 =bitWiseAddition(r.getR()[rs].getRegisterSpace(),r.getR()[rti].getRegisterSpace());
        int k  = x+y;
        System.out.println("ADD"+" "+x +" "+ y +"="+k);
        char  j= r.getR()[rs].getRegisterSpace().charAt(0);
        char  jj= r.getR()[rti].getRegisterSpace().charAt(0);

        String z = convertTwosComplement4(k);

        if(s2.length()>8 && s2.charAt(0)=='1') {
            S1.setCarryFlag(1);
        }
        else{
            S1.setCarryFlag(0);
        }
        GPRSRegister[] t= r.getR();
        t[rs].setRegisterSpace(z);
        r.setR(t);
        System.out.println("Value of R"+rs+"="+getTwosComplement(z));
        int o= x+y;
        String p= convertTwosComplement4(o);

        if((x >=0 && y>=0 && (p.charAt(0)+"").equals("1")||(x<0 && y<0 && (p.charAt(0)+"").equals("0")))){
            S1.setOverFlowFlag(1);
        }else{
            S1.setOverFlowFlag(0);
        }
        if ((byte)o<0){
            S1.setNegativeFlag(1);
        }else{
            S1.setNegativeFlag(0);
        }
        S1.setSignFlag( S1.getNegativeFlag()^ S1.getOverFlowFlag());
        if(o==0){
            S1.setZeroFlag(1);
        }else{
            S1.setZeroFlag(0);
        }
        System.out.println("Status Regsiter:"+S1.toString());
    }
    public static int getTwosComplement(String binaryInt) {
        if (binaryInt.charAt(0) == '1') {
            //Call our invert digits method
            String invertedInt = invertDigits(binaryInt);
            //Change this to decimal format.
            int decimalValue = Integer.parseInt(invertedInt,2);
            //Add 1 to the curernt decimal and multiply it by -1
            //because we know it's a negative number
            decimalValue = (decimalValue + 1) * -1;
            //return the final result
            return decimalValue;
        } else {
            //Else we know it's a positive number, so just convert
            //the number to decimal base.
            int r= Integer.parseInt(binaryInt,2);
            return r;
        }
    }
    public static String invertDigits(String binaryInt) {

        String result = binaryInt;
        result = result.replace("0", " "); //temp replace 0s
        result = result.replace("1", "0"); //replace 1s with 0s
        result = result.replace(" ", "1"); //put the 1s
        return result;
    }
    public static void main(String [] args){
        CAapp c = new CAapp();
        try {
            c.readtoMemory("C:/Users/20100/Desktop/t.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.loop();
        c.r.printo();
        c.m.printo();
    }
    public static  String convertTwosComplement(int x){
        if(x>31 || x<-32)
            return null;
        String s= Integer.toBinaryString(x);
        if(s.length()>6)
            return s.substring(26);
        else{
            String k="";
            for(int i =6;i>s.length();i--){
                k+=0;
            }
            return k+s;
        }
    }
    public static  String convertTwosComplement4(int x){
        String s= Integer.toBinaryString(x);
        if(s.length()>8)
            return s.substring(24);
        else{
            String k="";
            for(int i =8;i>s.length();i--){
                k+=0;
            }
            return k+s;
        }
    }
    public static String bitWiseAddition(String a ,String b ){
        String s ="";
        char c= '0';
        for (int i=0;i<a.length();i++){
            if(i==0) {
                char [] cc= fulladder(a.charAt(a.length() - 1 - i), b.charAt(b.length() - 1 - i), '0');
                s=s+cc[0];
                c=cc[1];
            }
            else{
                char [] cc= fulladder(a.charAt(a.length() - 1 - i), b.charAt(b.length() - 1 - i), c);
                s=cc[0]+s;
                c=cc[1];
            }
        }
        return c+s+"";
    }
    public static  char [] fulladder(char a,char b,char c){
        char [] ret=new char[2];
        if(a=='0' && b=='0' && c=='0'){
            ret[0]='0';
            ret[1]='0';
            return ret;
        }
        if(a=='0' && b=='1' && c=='0'){
            ret[0]='1';
            ret[1]='0';
            return ret;
        }
        if(a=='1' && b=='0' && c=='0'){
            ret[0]='1';
            ret[1]='0';
            return ret;
        }
        if(a=='0' && b=='0' && c=='1'){
            ret[0]='1';
            ret[1]='0';
            return ret;
        }
        if(a=='1' && b=='0' && c=='1'){
            ret[0]='0';
            ret[1]='1';
            return ret;
        }
        if(a=='0' && b=='1' && c=='1'){
            ret[0]='0';
            ret[1]='1';
            return ret;
        }
        if(a=='1' && b=='1' && c=='1'){
            ret[0]='1';
            ret[1]='1';
            return ret;
        }
        if (a=='1' && b=='1' && c=='0'){
            ret[0]='0';
            ret[1]='1';
            return ret;
        }
        return null;
    }
}