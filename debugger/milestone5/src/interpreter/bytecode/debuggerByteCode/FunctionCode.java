
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebuggerVM;

public class FunctionCode extends ByteCode{
    public String name;
    public int start;
    public int end;

    public FunctionCode(){
        
    }
    
    @Override
    public void init(String[] args) {
        name = args[0];
        start = Integer.parseInt(args[1]);
        end = Integer.parseInt(args[2]);
    }

    
    public void execute(VirtualMachine vm) {
        DebuggerVM dvm = (DebuggerVM) vm;
        dvm.pushEnvStack(name,start,end);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("FUNCTION " + name + " " + start + " " + end + "\n");
    }
    
}
