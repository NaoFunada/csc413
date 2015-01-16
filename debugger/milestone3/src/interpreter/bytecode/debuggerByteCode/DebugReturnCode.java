
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.ReturnCode;
import interpreter.debugger.DebuggerVM;

public class DebugReturnCode extends ReturnCode{

    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebuggerVM dvm =(DebuggerVM) vm;
        dvm.popEnvStack();
    }
    
}
