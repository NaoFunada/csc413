
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;

public class LineCode extends ByteCode{
    //line number
    public int n; 
    
    public LineCode(){
    }
    
    @Override
    public void init(String[] args) {
        n = Integer.parseInt(args[0]);
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVM dvm = (DebuggerVM) vm;
        dvm.setCurLine(n);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("LINE " + n + "\n");
    }
    
}
