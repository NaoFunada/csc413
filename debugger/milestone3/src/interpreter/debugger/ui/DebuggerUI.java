
package interpreter.debugger.ui;

import interpreter.debugger.DebuggerVM;
import interpreter.debugger.Entry;
import interpreter.debugger.SymbolTable;
import java.io.*;
import java.util.*;

public class DebuggerUI {
    
    public void printSourceCode(Vector entries, int curLine){
        printCurFunc(entries,0,entries.size(),curLine);
    }
    
    public void printCurFunc(Vector entries,int start,int end,int curLine){
        for(int i = start; i < end; i++){
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
    
    public void printVar(DebuggerVM vm){
        if(!vm.eStackIsEmpty()){
        SymbolTable symTable = vm.peekEnvStack().getSymbolTable();
        String str = "";
        Object[] var = symTable.keys().toArray();
        for (int i = 0; i < var.length; i++) {
            //get key
            str += (var[i] + "/");
            //get value
            int offset = (Integer) symTable.get((String) var[i]);
            List list = vm.topStack();
            str += list.get(offset);
            if (i < (var.length - 1)) {
                str += ",";
            }
        }
        System.out.println(str);
        }else{
            System.out.println("None");
        }

    }
    
    public ArrayList<String> getUserCommand(){
        //Prompt the user for command
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
        }else if(!(command.equals("SB")||command.equals("CB")||
                command.equals("F")||command.equals("C")||
                command.equals("Q")||command.equals("V"))){
            System.out.println("Invalid command");
            commandLine = getUserCommand();
        }
        
        return commandLine;
    }
    
    public void helpMenu(){
        System.out.println();
        System.out.println("To set breakpoints type: SB (line number(s))");
        System.out.println("To clear breakpoints type: CB (line number(s))");
        System.out.println("To display current function type: F");
        System.out.println("To continue execution type: C");
        System.out.println("To quit execution type: Q");
        System.out.println("To display variables type: V");
    }
    
    
    
}//end class
