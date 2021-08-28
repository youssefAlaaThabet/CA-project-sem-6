public class SREGRegister {
    private String RegisterSpace;
    private int CarryFlag;
    private  int OverFlowFlag;
    private int NegativeFlag;
    private int SignFlag;
    private int ZeroFlag;
    public SREGRegister(){
        RegisterSpace="00000000" ;
    }
    public SREGRegister( int CarryFlag, int OverFlowFlag,int NegativeFlag,int SignFlag,int ZeroFlag)
    {
        RegisterSpace =  ZeroFlag + "" + SignFlag + "" + NegativeFlag + "" + OverFlowFlag + "" + CarryFlag + "000";
    }
    public String getRegisterSpace() {
        return RegisterSpace;
    }

    public void setRegisterSpace(String registerSpace) {
        RegisterSpace = registerSpace;
    }

    public int getCarryFlag() {
        return CarryFlag;
    }

    public void setCarryFlag(int carryFlag) {
        CarryFlag = carryFlag;
        setRegisterSpace(getRegisterSpace().substring(0,5)+""+carryFlag+"000");
    }

    public int getOverFlowFlag() {
        return OverFlowFlag;

    }

    public void setOverFlowFlag(int overFlowFlag) {
        OverFlowFlag = overFlowFlag;
        setRegisterSpace(getRegisterSpace().substring(0,4)+""+overFlowFlag+""+getCarryFlag()+"000");

    }

    public int getNegativeFlag() {
        return NegativeFlag;
    }

    public void setNegativeFlag(int negativeFlag) {
        NegativeFlag = negativeFlag;
        setRegisterSpace(getRegisterSpace().substring(0,2)+""+negativeFlag+""+getRegisterSpace().substring(3,8));

    }

    public int getSignFlag() {
        return SignFlag;
    }

    public void setSignFlag(int signFlag) {
        SignFlag = signFlag;
        setRegisterSpace(getRegisterSpace().substring(0,1)+""+signFlag+""+getRegisterSpace().substring(2,8));

    }

    public int getZeroFlag() {
        return ZeroFlag;
    }

    public void setZeroFlag(int zeroFlag) {
        ZeroFlag = zeroFlag;
        setRegisterSpace(zeroFlag+""+getRegisterSpace().substring(1,8));
    }
    public String  toString(){
        String s="\n"+"CarryFlag:"+this.getCarryFlag()+"\n"+
        "getNegativeFlag"+this.getNegativeFlag()+"\n"+
        "getZeroFlag"+this.getZeroFlag()+"\n"+
        "getOverFlowFlag"+this.getOverFlowFlag()+"\n"+
        "getSignFlag"+this.getSignFlag()+"\n";
        return s;
    }
}
