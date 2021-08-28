public class DataMemory {

    private int [] memory ;

    public DataMemory()
    {
        this.memory = new int [2048];
    }

    public int[] getMemory() {
        return memory;
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }
    public void printo(){
        for(int i=0;i<memory.length;i++){
                System.out.println("Memory of :" + i + "=" +(memory[i]));

        }

    }
}
