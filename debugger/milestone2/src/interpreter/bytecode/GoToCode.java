
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class GoToCode extends ByteCode{
    public String label;
    public int num;
    
    //Constructor
    public GoToCode(){
        
    }
    
    //initialize label
    @Override
    public void init(String[] args){
        label = args[0];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setPC(num);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("GOTO" + num + "\n");
    }
    
    public String getLabel(){
        return label;
    }
    
    public void setNum(int i){
        num = i;
    }
}
