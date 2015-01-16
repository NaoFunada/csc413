
package interpreter.bytecode.debuggerByteCode;

import interpreter.VirtualMachine;
import interpreter.bytecode.PopCode;
import interpreter.debugger.DebuggerVM;

public class DebugPopCode extends PopCode{

    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        DebuggerVM dvm = (DebuggerVM) vm;
        dvm.popSymbolTable(n);
    }
}
