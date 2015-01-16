
package interpreter;

import java.util.*;
import interpreter.bytecode.*;

public class VirtualMachine {
    RunTimeStack runStack;
    int pc;
    Stack returnAddrs;
    boolean isRunning;
    Program program;
    String dumpFlag;
    int dump;
    
    public VirtualMachine(Program prog){
        program = prog;
    }
    
    public void executeProgram(){
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
        isRunning = true;
        dumpFlag = "OFF";
        while(isRunning){
            if(dumpFlag.equals("ON")){
                dump = 1;
            }
            ByteCode code = program.getCode(pc);
            code.execute(this);
            if(dumpFlag.equals("OFF")){
                dump = 0;
            }
            if(dump == 1){
                System.out.print(code.toString(this));
                runStack.dump();
            }
            pc++;
            
        }
    }
    
    

    public void setPC(int i){
        pc = i;
    }
    
    public int getPC(){
        return pc;
    }
    
    public void newFrame(int i){
        runStack.newFrameAt(i);
    }
    
    public void popFrame(){
        runStack.popFrame();
    }
    
    public int popRunStack(){
        return runStack.pop();
    }
    
    public int peekRunStack(){
        return runStack.peek();
    }
    
    public void pushRunStack(int i){
        runStack.push(i);
    }
    
    public void loadStack(int i){
        runStack.load(i);
    }
    
    public void storeStack(int i){
        runStack.store(i);
    }
    
    public List topStack(){
        List top = runStack.topStack();
        return top;
    }
            
    public void pushReturnAd(int i){
        returnAddrs.push(i);
    }
    
    public int popReturnAd(){
        return (Integer)returnAddrs.pop();
    }
    
    public void dumpSwitch(String flag){
        dumpFlag = flag;
    }

    public void halt(){
        isRunning = false;
    }
    
}
