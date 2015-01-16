
package interpreter.bytecode;

import interpreter.VirtualMachine;

public class LitCode extends ByteCode{
    //literal value
    public int n;
    public String id;
    
    //constructor
    public LitCode(){  
    }
    
    @Override
    public void init(String[] args){
        n = Integer.parseInt(args[0]);
        if(args[1] != null){
            id = args[1];
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(n);
    }

    @Override
    public String toString(VirtualMachine vm) {
        String str = ("LIT " + n);
        if(id != null){
            str += (" " + id + "   int " + id + "\n");
        }else{
            str += ("\n");
        }
        return str;
    }
}
