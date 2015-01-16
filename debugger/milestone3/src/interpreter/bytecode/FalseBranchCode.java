
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class FalseBranchCode extends ByteCode{
    public String label;
    public int num;
    
    //Constructor
    public FalseBranchCode(){
        
    }
    
    //initialize label
    @Override
    public void init(String[] args){
        label = args[0];
    }
    

    @Override
    public void execute(VirtualMachine vm) {
        if(vm.popRunStack() == 0){
            vm.setPC(num - 1);
        }
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("FALSEBRANCH " + num + "\n");
    }
    
    public String getLabel(){
        return label;
    }
    
    public void setNum(int i){
        num = i;
    }
}
