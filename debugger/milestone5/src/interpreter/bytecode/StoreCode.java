
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class StoreCode extends ByteCode{
    //offset number from start of fame
    public int n;
    //variable name of data stored
    public String id;
    
    //Constructor
    public StoreCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        n = Integer.parseInt(args[0]);
        id = args[1];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.storeStack(n);
    }

    @Override
    public String toString(VirtualMachine vm) {
        int top = vm.peekRunStack();
        return ("STORE " + n + " " + id + "   " + id + "=" + top + "\n");
    }
}
