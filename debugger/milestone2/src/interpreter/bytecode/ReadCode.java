
package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Scanner;

public class ReadCode extends ByteCode{
    //constructor
    public ReadCode(){
    }
    
    @Override
    public void init(String[] args){
    }

    @Override
    public void execute(VirtualMachine vm) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a integer:");
        vm.pushRunStack(sc.nextInt());
    }

    @Override
    public String toString(VirtualMachine vm) {
        return ("READ\n");
    }
}
