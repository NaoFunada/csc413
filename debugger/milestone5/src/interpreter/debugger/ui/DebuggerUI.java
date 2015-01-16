
package interpreter.debugger.ui;

import interpreter.VirtualMachine;
import interpreter.debugger.DebuggerVM;
import interpreter.debugger.Entry;
import interpreter.debugger.FunctionEnviromentRecord;
import interpreter.debugger.SymbolTable;
import java.io.*;
import java.util.*;

public class DebuggerUI {
    
    /**
     * prints the message that you are in a intrinsic function
     * @param vm 
     */
    public void printIntrinsic(DebuggerVM vm){
        System.out.print("*********");
        System.out.print(vm.peekEnvStack().getName());
        System.out.println("*********");
    }
    
    /**
     * Prints the whole source code
     * @param entries vector containing the source code lines
     * @param curLine number of current line
     */
    public void printSourceCode(Vector entries, int curLine){
        printCurFunc(entries,1,entries.size() -1,curLine);
    }
    
    /**
     * Prints the current function given the start and end line numbers
     * @param entries vector containing the source code lines
     * @param start line number that the function starts
     * @param end line number that the function ends
     * @param curLine number of current line
     */
    public void printCurFunc(Vector entries,int start,int end,int curLine){
        for(int i = start -1; i < end; i++){
            Entry line = (Entry) entries.get(i);
            //check if breakpoint
            if(line.isBptSet()){
                System.out.print("*");
            }
            System.out.print((i + 1) + ".  ");
            System.out.print(line.toString());
            //check if current line
            if((i + 1) == curLine){
                System.out.println("   <------------");
            }else{
                System.out.println();

            }
            
        }
        System.out.println();
    }
    
    public void setBpMessage(int num){
        System.out.print("Breakpoint set: ");
        System.out.println(num);
    }
    
    public void clearBpMessage(int num){
        System.out.print("Breakpoint cleared: ");
        System.out.println(num);
    }
    
    
    /**
     * Prints a list of current set breakpoints
     * @param vm 
     */
    public void printBreakpt(Vector entries){
        Entry line;
        System.out.print("Current breakpoints: ");
        for(int i = 0; i < entries.size(); i++){
            line = (Entry) entries.get(i);
            if(line.isBptSet()){
                System.out.print(i + 1);
                System.out.print(" "); 
            }
        }
        System.out.println();
    }
    
    /**
     * Prints the current local variables
     * @param vm 
     */
    public void printVar(DebuggerVM vm){
        if(!vm.eStackIsEmpty()){
        SymbolTable symTable = vm.peekEnvStack().getSymbolTable();
        String str = "";
        Object[] var = symTable.keys().toArray();
        for (int i = 0; i < var.length; i++) {
            //get key
            str += (var[i] + ":");
            //get value
            int offset = (Integer) symTable.get((String) var[i]);
            List list = vm.topStack();
            str += list.get(offset);
            if (i < (var.length - 1)) {
                str += " ";
            }
        }
        System.out.println(str);
        }else{
            System.out.println("None");
        }

    }
    
    /**
     * Prints accordingly upon entering and exiting function 
     * @param vm
     * @param state 1 when entering, 0 when exiting
     */
    public void printTrace(DebuggerVM vm, int state){
        if(state == 1){//enter
            for (int i = 0; i < vm.getEnvStackSize() - 1; i++) {
                System.out.print(" ");
            }
            System.out.print(vm.getFName() + "(");
            if (vm.topStack().isEmpty()) {
                System.out.println(")");
            }else{
                int args = (Integer)vm.topStack().get(vm.topStack().size() -1);
                System.out.println(args + ")");
            }
        }else if (state == 0){//exit
            for (int i = 0; i < vm.getEnvStackSize(); i++) {
                System.out.print(" ");
            }
            System.out.print("exit " + vm.getFName());
            if (!vm.topStack().isEmpty()) {
                int returnVar = (Integer)vm.topStack().get(vm.topStack().size() -1);
                System.out.println(":" + returnVar);
            }
        }
        
    }
    
    /**
     * Prints the function call stack with line numbers
     * @param vm 
     */
    public void printCallStack(DebuggerVM vm){
        Stack callStack = vm.getEnvStack();
        for(int i = vm.getEnvStackSize();i > 0;i--){
            FunctionEnviromentRecord rec = (FunctionEnviromentRecord) callStack.pop();
            System.out.println(rec.getName() + ":" + rec.getCurrentline());
        }
    }
    
    /**
     * Prompts the user for a command and returns the command
     * If given ? prints the help menu and prompts the user for the command again
     * @return user command 
     */
    public ArrayList<String> getUserCommand(){
        //Prompt the user for command
        System.out.println();
        System.out.println("Type ? for help");
        System.out.print(">");
        ArrayList<String> commandLine = new ArrayList();
        InputStreamReader instr = new InputStreamReader(System.in);
        BufferedReader stuff = new BufferedReader(instr);
        try {
            String line = stuff.readLine();
            
            //Tokenize the line and add to commandLine
            StringTokenizer tok = new StringTokenizer(line);
            while (tok.hasMoreTokens()){
                commandLine.add(tok.nextToken());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        //check for command type
        String command = commandLine.get(0);
        if(command.equals("?")){
            helpMenu();
            commandLine = getUserCommand();
        }else if(!(command.equals("sb")||command.equals("cb")||command.equals("s")||
                command.equals("lb")||command.equals("f")||command.equals("c")||
                command.equals("over")||command.equals("in")||command.equals("out")||
                command.equals("q")||command.equals("t")||command.equals("v"))){
            System.out.println("Invalid command");
            commandLine = getUserCommand();
        }
        
        return commandLine;
    }
    
    /**
     * Prints the help menu
     */
    public void helpMenu(){
        System.out.println();
        System.out.println("To set breakpoints type: sb (line number(s))");
        System.out.println("To clear breakpoints type: cb (line number(s))");
        System.out.println("To list current breakpoints type: lb");
        System.out.println("To display current function type: f");
        System.out.println("To display variables type: v");        
        System.out.println("To display call stack type: s");
        System.out.println("To continue execution type: c");
        System.out.println("To Step over current line type: over");
        System.out.println("To Step into a function type: in");
        System.out.println("To Step out of current function type: out");
        System.out.println("To toggle function trace(on/off) type: t");
        System.out.println("To quit execution type: q");
    }
    
    
    
}//end class
