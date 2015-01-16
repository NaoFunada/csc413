
package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.List;

public class CallCode extends ByteCode {
    //function name
    public String funcName;
    public int num;
    
    //Constructor
    public CallCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        funcName = args[0];
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushReturnAd(vm.getPC()); 
        vm.setPC(num - 1); 
    }

    @Override
    public String toString(VirtualMachine vm) {
        String str = ("CALL " + num + " " + funcName + "   ");
        for(int i=0; i < funcName.length() ;i++){
            if(funcName.charAt(i) == '<' ){
                break;
            }
            str += funcName.charAt(i);
        }
        str += "(";
        List top = vm.topStack();
        for(int i=0; i < top.size() - 1;i++){
            str += top.get(i);
            str += ",";
        }
        str += top.get(top.size() - 1);
        str += ")\n";
        return str;
    }
    
    public String getFuncName(){
        return funcName;
    }
    
    public void setNum(int i){
        num = i;
    }

}
