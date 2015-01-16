
package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.debuggerByteCode.FormalCode;
import java.util.List;
import java.util.Stack;

public class DebuggerVM extends VirtualMachine{
    Stack enviromentStack;
    FunctionEnviromentRecord fer;
    //boolean isSet;
    boolean isNextLine;
    String fName;
    
    public DebuggerVM(Program prog){
        super(prog);
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
        isRunning = true;
        fer = new FunctionEnviromentRecord();
        enviromentStack = new Stack();
    }
    
    @Override
    
    public void executeProgram(){
        ByteCode code = program.getCode(pc);
        code.execute(this);
        pc++;

        //if stepping into function, run functionCode
        if ((isNextLine) && (fer.getName() == null)) {
            code = program.getCode(pc);
            code.execute(this);
            pc++;

            //if there is formalCode after functionCode, run that as well
            code = program.getCode(pc);
            while (code instanceof FormalCode) {
                code.execute(this);
                pc++;
                code = program.getCode(pc);
            }
        }

        /**
     * runs program per line of source code
     
        isNextLine = false;
        while(isRunning && !(isNextLine)){
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
            
            //if stepping into function, run functionCode
            if((isNextLine) && (fer.getName() == null)){
                code = program.getCode(pc);
                code.execute(this);
                pc++;
            }
            //if there is formalCode after functionCode, run that as well
            code = program.getCode(pc);
            while(code instanceof FormalCode){
                code.execute(this);
                pc++;
                code = program.getCode(pc);
            }
            
        }
        */
        
    }
    public void setCurLine(int n){
        fer.setCurrentline(n);
        isNextLine = true;
        
    }
    
    public void pushEnvStack(String name, int start, int end){
        fName = name;
        fer.setName(name);
        fer.setStartline(start);
        fer.setEndline(end);
        if(enviromentStack.isEmpty()){
            enviromentStack.push(fer);
        }
    }
    
    public void popEnvStack(){
        enviromentStack.pop();
        fer = (FunctionEnviromentRecord) enviromentStack.peek();
    }
    
    public FunctionEnviromentRecord peekEnvStack(){
        return (FunctionEnviromentRecord) enviromentStack.peek();
    } 
    
    public boolean eStackIsEmpty(){
        return enviromentStack.isEmpty();
    }
    
    public boolean runStackIsEmpty(){
        return runStack.isEmpty();
    }
    
    public int getEnvStackSize(){
        return enviromentStack.size();
    }
    
    public Stack getEnvStack(){
        return (Stack) enviromentStack.clone();
    }
    
    public void pushSymbolTable(String xyz, int n){
        fer.pushSymbolTable(xyz,n);
    }
    
    public void popSymbolTable(int n){
        fer.popSymbolTable(n);
    }
    
    public void lit(String id){
        List list; 
        int num = (list = runStack.topStack()).size() - 1;
        fer.pushSymbolTable(id, num);
    }
    
    public void dumpFER(){
        System.out.println(fer.DumpFER());
    }
    
    public void newFER(){
        fer = new FunctionEnviromentRecord();
        enviromentStack.push(fer);
    }
    
    public String getFName(){
        return fName;
    }
    
}//end class
