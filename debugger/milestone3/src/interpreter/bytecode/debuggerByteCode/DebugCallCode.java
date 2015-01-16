
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.CallCode;
import interpreter.debugger.DebuggerVM;

public class DebugCallCode extends CallCode{

    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebuggerVM dvm = (DebuggerVM) vm;
        dvm.newFER();
    }
    
}
