
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
    
}
