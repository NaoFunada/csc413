
package interpreter.debugger;

import interpreter.Interpreter;
import interpreter.Program;
import interpreter.debugger.ui.DebuggerUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Debugger extends Interpreter{
   DebuggerUI ui;
   String filename;
   Vector entries;
   boolean isRunning;
   Program program;
   DebuggerVM vm;
   Entry line;
   
    public Debugger(String args){
        super(args + ".x.cod", true);
        filename = args;
        ui = new DebuggerUI();
        
    }
    
    public void run() {
        program = super.bcl.loadCodes();
        vm = new DebuggerVM(program);
        
        //set entries
        try {
            setEntries(filename + ".x");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        //print sourcecode
        ui.printSourceCode(entries, vm.fer.getCurrentline());

        isRunning = true;
        line = (Entry) entries.get(0);
        while (isRunning) {
            //prompt user and get command
            ArrayList<String> commandLine = ui.getUserCommand();
            String command = commandLine.get(0);

            //resolve command
            if (command.equals("SB")) {
                //set breakpoint(s)
                for (int i = 1; i < commandLine.size(); i++) {
                    int num = Integer.parseInt(commandLine.get(i));
                    //check if LineCode exists
                    if (program.getLineNums().contains(num)) {
                        Entry a = (Entry) entries.get(num -1);
                        a.setBpt(true);
                    }
                }
            } else if (command.equals("CB")) {
                //clear breakpoint(s)
                for (int i = 1; i < commandLine.size(); i++) {
                    int num = Integer.parseInt(commandLine.get(i));
                    Entry a = (Entry) entries.get(num -1);
                    a.setBpt(false);
                }
            } else if (command.equals("F")) {
                //print current function
                if (vm.fer.getName() == null) {
                    ui.printSourceCode(entries,vm.fer.getCurrentline());
                }else if(vm.fer.getCurrentline() < 0){
                    System.out.print("Inside internal function: ");
                    System.out.println(vm.fer.getName());
                } else {
                    ui.printCurFunc(entries, vm.fer.getStartline() -1, 
                            vm.fer.getEndline(), vm.fer.getCurrentline());
                }
            } else if (command.equals("C")) {
                //continue execution until you hit a breakpoint
                Boolean run = true;
                //run per line and check if you have a breakpoint
                while (run) {
                    vm.executeProgram();
                    //check for breakpoint
                    if (vm.fer.getCurrentline() > 0) {
                        line = (Entry) entries.get(vm.fer.getCurrentline() - 1);
                        run = (!(line.isBptSet()));

                    }

                    //check if you finished running the program
                    if (vm.done()) {
                        run = false;
                        isRunning = false;
                    }
                }
                
            } else if (command.equals("Out")) {
                boolean isSet = false; //if true break out
                int envSize = vm.enviromentStack.size(); //when size decreases you stepped out
                
                //continue running until you step out or find a breakpoint
                while (!isSet && isRunning) {
                    vm.executeProgram();
                    int newEnvSize = vm.enviromentStack.size();
                    
                    
                    //check for breakpoint
                    if (vm.fer.getCurrentline() > 0) {
                        line = (Entry) entries.get(vm.fer.getCurrentline() - 1);
                        isSet = line.isBptSet();
                    }
                    
                    //check for stepping out
                    if(newEnvSize < envSize) {
                        isSet = true;
                    }
                    
                    //check if you finished running the program
                    if (vm.done()) {
                        isSet = true;
                        isRunning = false;
                    }
                }
                
            } else if (command.equals("Q")) {
                isRunning = false;
            } else if (command.equals("V")) {
                ui.printVar(vm);
            }
        }

    }
    

    public void setEntries(String sourcefile)throws IOException{
        entries = new Vector();
        BufferedReader source;
        source = new BufferedReader(new FileReader(sourcefile));
        String line = null;
        while ((line = source.readLine()) != null) {
            entries.add(new Entry(line));
        }
    }
    
}//end debugger

