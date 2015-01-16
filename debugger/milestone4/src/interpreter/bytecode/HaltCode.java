
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class HaltCode extends ByteCode{
    //constructor
    public HaltCode(){
    }
    
    @Override
    public void init(String[] args){
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.halt();
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("HALT \n");
    }
}
