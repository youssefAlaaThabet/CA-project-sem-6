public class InstructionMemory {

    private String [] Memory;
    private int lastspaceused;

    public InstructionMemory()
    {
        this.Memory = new String[1024];
        this.lastspaceused = 0;
    }

    public String [] getMemory() {
        return Memory;
    }

    public void setMemory(String [] memory) {
        Memory = memory;
    }

    public int getLastspaceused() {
        return lastspaceused;
    }

    public void setLastspaceused(int lastspaceused) {
        this.lastspaceused = lastspaceused;
    }
}
