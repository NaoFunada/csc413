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

public class Debugger extends Interpreter {

    DebuggerUI ui;
    String filename;
    Vector entries;
    boolean isRunning;
    Program program;
    DebuggerVM vm;
    Entry line;
    boolean isTraceOn;
    ArrayList<String> commandLine;

    public Debugger(String args) {
        super(args + ".x.cod", true);
        filename = args;
        ui = new DebuggerUI();
        isTraceOn = false;
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
            commandLine = ui.getUserCommand();
            String command = commandLine.get(0);

            //resolve command
            try {
                execute(command);
            } catch (Exception e) {
                System.out.println("try again");
                e.printStackTrace();
            }



        }

    }

    private void execute(String command) {
        if (command.equals("sb")) {
            //set breakpoint(s)
            for (int i = 1; i < commandLine.size(); i++) {
                int num = Integer.parseInt(commandLine.get(i));
                //check if LineCode exists
                if (program.getLineNums().contains(num)) {
                    Entry a = (Entry) entries.get(num - 1);
                    a.setBpt(true);
                    ui.setBpMessage(num);
                }
            }
        } else if (command.equals("cb")) {
            //clear breakpoint(s)
            for (int i = 1; i < commandLine.size(); i++) {
                int num = Integer.parseInt(commandLine.get(i));
                Entry a = (Entry) entries.get(num - 1);
                a.setBpt(false);
                ui.clearBpMessage(num);
            }
        } else if (command.equals("lb")) {
            //list breakpoint(s)
            ui.printBreakpt(entries);
        } else if (command.equals("f")) {
            //print function
            printFunc();
        } else if (command.equals("c")) {
            //continue execution until you hit a breakpoint
            boolean isSet = false; //if true break out
            int envSize = vm.enviromentStack.size(); //when size decreases you stepped out

            while (!isSet) {

                //run a line of code
                vm.isNextLine = false;
                while (!vm.isNextLine) {
                    vm.executeProgram();

                    //check if you finished running the program
                    if (vm.done()) {
                        isSet = true;
                        isRunning = false;
                        break;
                    }

                    //print trace when exiting funtion
                    if (isTraceOn) {
                        int newEnvSize = vm.enviromentStack.size();
                        if (newEnvSize < envSize) {
                            ui.printTrace(vm, 0);
                        } else if (newEnvSize > envSize) {
                            //run labelCode
                            vm.executeProgram();
                            //run lineCode, etc.
                            vm.executeProgram();
                            ui.printTrace(vm, 1);
                        }

                        envSize = newEnvSize;
                    }
                }//end while

                if (!isSet) {
                    //check for breakpoint
                    if (vm.fer.getCurrentline() > 0) {
                        line = (Entry) entries.get(vm.fer.getCurrentline() - 1);
                        isSet = line.isBptSet();
                    }
                }

            }//end outer while


            //print source code after running
            printFunc();

        } else if (command.equals("over")) {
            //Stepping over a line
            boolean isSet = false; //if true break out
            int envSize = vm.enviromentStack.size(); //when size decreases you stepped out

            //continue running until you reach next line
            while (!isSet) {

                //run a line of code
                vm.isNextLine = false;
                while (!vm.isNextLine) {
                    vm.executeProgram();

                    //check if you finished running the program
                    if (vm.done()) {
                        isSet = false;
                        isRunning = false;
                        break;
                    }
                }

                int newEnvSize = vm.enviromentStack.size();

                //check for stepping out if you enter a function
                if (newEnvSize <= envSize) {
                    isSet = true;
                }
            }

            //print source code after running
            printFunc();

        } else if (command.equals("in")) {
            //Stepping into a function
            //run a line of code
            vm.isNextLine = false;
            while (!vm.isNextLine) {
                vm.executeProgram();

                //check if you finished running the program
                if (vm.done()) {
                    isRunning = false;
                    break;
                }
            }

            //print source code after running
            printFunc();

        } else if (command.equals("out")) {
            //Stepping out of a function
            boolean isSet = false; //if true break out
            int envSize = vm.enviromentStack.size(); //when size decreases you stepped out

            //continue running until you step out or find a breakpoint
            while (!isSet) {

                //run a line of code
                vm.isNextLine = false;
                while (!vm.isNextLine) {
                    vm.executeProgram();

                    int newEnvSize = vm.enviromentStack.size();

                    //check for stepping out
                    if (newEnvSize < envSize) {
                        isSet = true;
                        break;
                    }

                    //check if you finished running the program
                    if (vm.done()) {
                        isSet = false;
                        isRunning = false;
                        break;
                    }
                }

                if (!isSet) {
                    //check for breakpoint
                    if (vm.fer.getCurrentline() > 0) {
                        line = (Entry) entries.get(vm.fer.getCurrentline() - 1);
                        isSet = line.isBptSet();
                    }
                }

            }


            //print source code after running
            printFunc();

        } else if (command.equals("t")) {
            //set trace flag on/off
            if (isTraceOn) {
                isTraceOn = false;
                System.out.println("Trace is OFF");
            } else {
                isTraceOn = true;
                System.out.println("Trace is ON");
            }

        } else if (command.equals("q")) {
            //halt execution
            isRunning = false;
        } else if (command.equals("s")) {
            //print call stack
            ui.printCallStack(vm);
        } else if (command.equals("v")) {
            //Print local variables
            ui.printVar(vm);
        }
    }

    public void printFunc() {
        //print current function
        if (!vm.done()) {
            System.out.println();
            if (vm.fer.getName() == null) {
                ui.printSourceCode(entries, vm.fer.getCurrentline());
            } else if (vm.fer.getCurrentline() < 0) {
                ui.printIntrinsic(vm);
            } else {
                ui.printCurFunc(entries, vm.fer.getStartline(),
                        vm.fer.getEndline(), vm.fer.getCurrentline());
            }
        }

    }

    public void setEntries(String sourcefile) throws IOException {
        entries = new Vector();
        BufferedReader source;
        source = new BufferedReader(new FileReader(sourcefile));
        String line = null;
        while ((line = source.readLine()) != null) {
            entries.add(new Entry(line));
        }
    }
}//end debugger

