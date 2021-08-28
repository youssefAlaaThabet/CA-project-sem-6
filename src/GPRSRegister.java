public class GPRSRegister {
    public GPRSRegister(){
    }
    private String RegisterSpace;
    public GPRSRegister(String RegisterSpace)
    {
        this.RegisterSpace = RegisterSpace;
    }
    public String getRegisterSpace() {
        return RegisterSpace;
    }
    public void setRegisterSpace(String registerSpace)
    {
        RegisterSpace = registerSpace;
    }
}
