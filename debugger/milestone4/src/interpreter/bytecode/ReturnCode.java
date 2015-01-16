
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class ReturnCode extends ByteCode{
    //function name
    public String funcName;

    
    //Constructor
    public ReturnCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        funcName = args[0];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.popFrame(); 
        vm.setPC(vm.popReturnAd());
    }

    @Override
    public String toString(VirtualMachine vm) {
        String str = ("RETURN " + funcName + "   exit ");
        for(int i=0; i < funcName.length() ;i++){
            if(funcName.charAt(i) == '<' ){
                break;
            }
            str += funcName.charAt(i);
        }
        str += ":";
        str += vm.peekRunStack();
        str += "\n";
        return str;
    }
    
    public String getFuncName(){
        return funcName;
    }
}
