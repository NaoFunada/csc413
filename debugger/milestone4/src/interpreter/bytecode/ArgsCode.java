
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class ArgsCode extends ByteCode{
    //number of args
    public int n;
    
    //Constructor
    public ArgsCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        n = Integer.parseInt(args[0]);
    }
    
    @Override
    /**
     * ARGS n instructs the interpreter to set a new frame n down from the top
     */
    public void execute(VirtualMachine vm){
        vm.newFrame(n);
    }
    
    @Override
    public String toString(VirtualMachine vm){
        return ("ARGS " + n + "\n");
    }
}
