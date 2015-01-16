
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.LitCode;
import interpreter.debugger.DebuggerVM;

public class DebugLitCode extends LitCode{

    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebuggerVM dvm = (DebuggerVM) vm;
        if(id != null){
            dvm.lit(id);
        }
    }
    
}
