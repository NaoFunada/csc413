
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class WriteCode extends ByteCode{
    //constructor
    public WriteCode(){
    }
    
    @Override
    public void init(String[] args){
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.println(vm.peekRunStack());
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("WRITE\n");
    }
}
