
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class PopCode extends ByteCode{
    //number of levels to pop
    public int n;
    
    //Constructor
    public PopCode(){
        
    }
    
    //initialize variable
    @Override
    public void init(String[] args){
        n = Integer.parseInt(args[0]);
    }

    @Override
    public void execute(VirtualMachine vm) {
        for(int i=0; i < n; i++){
            vm.popRunStack();
        }
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("POP " + n + "\n");
    }
}
