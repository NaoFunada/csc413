
package interpreter;
import java.util.*;

public class CodeTable extends Object {
    //Hashtable for names of ByteCode classes
    static HashMap codeT;
    
    public static String get(String code){
        return (String) codeT.get(code);
    }
    
    //initializes the hashtable
    public static void init(){
        codeT = new HashMap();
        
        codeT.put("HALT", "HaltCode");
        codeT.put("POP", "PopCode");
        codeT.put("FALSEBRANCH", "FalseBranchCode");
        codeT.put("GOTO", "GoToCode");
        codeT.put("STORE", "StoreCode");
        codeT.put("LOAD", "LoadCode");
        codeT.put("LIT", "LitCode");
        codeT.put("ARGS", "ArgsCode");
        codeT.put("CALL", "CallCode");
        codeT.put("RETURN", "ReturnCode");
        codeT.put("BOP", "BopCode");
        codeT.put("READ", "ReadCode");
        codeT.put("WRITE", "WriteCode");
        codeT.put("LABEL", "LabelCode");
        codeT.put("DUMP", "DumpCode");
        
        
    }
    
    //initializes the hashtable for the debugger
    public static void debugInit(){
        codeT.put("CALL", "debuggerByteCode.DebugCallCode");
        codeT.put("LIT", "debuggerByteCode.DebugLitCode");
        codeT.put("POP", "debuggerByteCode.DebugPopCode");
        codeT.put("RETURN", "debuggerByteCode.DebugReturnCode");
        codeT.put("FORMAL", "debuggerByteCode.FormalCode");
        codeT.put("FUNCTION", "debuggerByteCode.FunctionCode");
        codeT.put("LINE", "debuggerByteCode.LineCode");
    }
    
}
