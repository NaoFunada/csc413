
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;

public class FormalCode extends ByteCode{
    public String xyz;
    public int offset;
    
    public FormalCode(){
    }

    @Override
    public void init(String[] args) {
        xyz = args[0];
        offset = Integer.parseInt(args[1]);
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVM dvm = (DebuggerVM) vm;
        dvm.pushSymbolTable(xyz, offset);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("FORMAL " + xyz + " " + offset + "\n");
    }
    
}
