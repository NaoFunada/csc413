
package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.bytecode.debuggerByteCode.FunctionCode;
import java.util.List;
import java.util.Stack;

public class DebuggerVM extends VirtualMachine{
    Stack enviromentStack;
    FunctionEnviromentRecord fer;
    boolean isNextLine;
    
    public DebuggerVM(Program prog){
        super(prog);
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
        isRunning = true;
        dumpFlag = "OFF";
        fer = new FunctionEnviromentRecord();
        enviromentStack = new Stack();
    }
    
    @Override
    public void executeProgram(){
        isNextLine = false;
        while(isRunning && !(isNextLine)){
            /*
            if(dumpFlag.equals("ON")){
                dump = 1;
            }
            */
            ByteCode code = program.getCode(pc);
            code.execute(this);
            /*
            if(dumpFlag.equals("OFF")){
                dump = 0;
            }
            if(dump == 1){
                System.out.print(code.toString(this));
                runStack.dump();
            }
            */
            pc++;
            
            //if stepping into function, run functionCode
            if((isNextLine) && (fer.getName() == null)){
                code = program.getCode(pc);
                code.execute(this);
                pc++;
            }
        }
        
    }
    public void setCurLine(int n){
        //currentLine = n;
        fer.setCurrentline(n);
        isNextLine = true;
        
    }
    
    public void pushEnvStack(String name, int start, int end){
        fer.setName(name);
        fer.setStartline(start);
        fer.setEndline(end);
        if(enviromentStack.isEmpty()){
            enviromentStack.push(fer);
        }else if(!enviromentStack.peek().equals(fer)){
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
}//end class
