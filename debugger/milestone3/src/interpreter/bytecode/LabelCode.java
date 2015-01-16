
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class LabelCode extends ByteCode{
    //target label name for brances
    public String label;
    
    //Constructor
    public LabelCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        label = args[0];
    }
    
    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("LABEL " + label + "\n");
    }

    public String getLabel(){
        return label;
    }
}
