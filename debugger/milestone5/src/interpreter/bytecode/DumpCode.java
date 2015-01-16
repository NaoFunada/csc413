
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class DumpCode extends ByteCode{
    public String state;
    
    public DumpCode(){
    }
    
    @Override
    public void init(String[] args) {
        state = args[0];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.dumpSwitch(state);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("DUMP" + state + "\n");
    }
    
}
