public class Register {
    private GPRSRegister [] r;

    public GPRSRegister[] getR() {
        return r;
    }

    public void setR(GPRSRegister[] r) {
        this.r = r;
    }

    public Register(){
        this.r=new GPRSRegister[64];
        for(int i =0;i<64;i++){
            GPRSRegister s=new GPRSRegister();
            r[i]=s;
        }
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
    public void printo(){
        for(int i=0;i<r.length;i++){
            if(r[i].getRegisterSpace()== null){
                System.out.println("R"+i+"=null");
            }
            else {
                System.out.println("R" + i + "=" + getTwosComplement(r[i].getRegisterSpace()));
            }
        }

    }
}
