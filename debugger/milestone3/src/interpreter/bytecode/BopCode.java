
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class BopCode extends ByteCode{
    //operation to perform
    public String op;
    
    //Constructor
    public BopCode(){
        
    }
    
    //initialize variables
    @Override
    public void init(String[] args){
        op = args[0];
    }
    
    @Override
    /**
     * pop top 2 levels in stack and perform the binary operations
     */
    public void execute(VirtualMachine vm){
        int first = vm.popRunStack();
        int second = vm.popRunStack();
        int ans = 0;
        
        if("+".equals(op)){
            ans = second + first;
        }else if("-".equals(op)){
            ans = second - first;
        }else if("*".equals(op)){
            ans = second * first;
        }else if("/".equals(op)){
            ans = second / first;
        }else if("==".equals(op)){
            if(second == first){
                ans = 1;
            }
        }else if("!=".equals(op)){
            if(second != first){
                ans = 1;
            }
        }else if("<=".equals(op)){
            if(second <= first){
                ans = 1;
            }
        }else if("<".equals(op)){
            if(second < first){
                ans = 1;
            }
        }else if(">=".equals(op)){
            if(second >= first){
                ans = 1;
            }
        }else if(">".equals(op)){
            if(second > first){
                ans = 1;
            }
        }else if("|".equals(op)){
            if((second == 1) || (first == 1)){
                ans = 1;
            }
        }else if("&".equals(op)){
            if((second == 1) && (first == 1)){
                ans = 1;
            }
        }else{
            System.out.println("Error: Invaid operator");
        }
        
        vm.pushRunStack(ans);
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("BOP " + op + "\n");
    }
    
    
}
