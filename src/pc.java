public class pc {
    //static pc instance=null;

    private int PC;

    public pc()
    {
        this.PC = 0;
    }

    public int getPC() {
      /*  if(instance==null){
            instance=new pc();
        }*/
        return PC;
    }

    public void setPC(int PC) {
        if(this.getPC()<65535) {
            this.PC = PC;
        }
        else{
            this.PC=0;
        }
    }
}
