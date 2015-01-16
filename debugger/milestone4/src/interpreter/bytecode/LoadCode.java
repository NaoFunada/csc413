
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class LoadCode extends ByteCode{
    //offset number from start of fame
    public int n;
    //variable name of data stored
    public String id;
    
    //Constructor
    public LoadCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        n = Integer.parseInt(args[0]);
        id = args[1];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.loadStack(n);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("LOAD " + n + " " + id +"   <load " + id + ">\n");
    }
}
