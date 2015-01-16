
package interpreter;

import java.util.*;
import interpreter.bytecode.*;
import interpreter.bytecode.debuggerByteCode.LineCode;

public class Program extends Object{
    ArrayList byteCodeList;
    HashMap labels;
    ArrayList<Integer> lineNums;

    Program(ArrayList list){
        labels = new HashMap();
        byteCodeList = list;
        lineNums = new ArrayList<Integer>();
    }
    
    public ByteCode getCode(int i){
        return (ByteCode)byteCodeList.get(i);
    }
    
    public ArrayList<Integer> getLineNums(){
        return lineNums;
    }
    
    public void resolveAddress(){
        //store all labels addresses in hashmap and store lineCode numbers
        for(int i = 0; i < byteCodeList.size(); i++){
            if(byteCodeList.get(i) instanceof LabelCode){
                LabelCode code = (LabelCode)byteCodeList.get(i);
                labels.put(code.getLabel(), i);
            }else if(byteCodeList.get(i) instanceof LineCode){
                LineCode code = (LineCode)byteCodeList.get(i);
                lineNums.add(code.n);
            }
        }
        
        //walk through byteCodeList and assgin proper address
        for(int i = 0; i < byteCodeList.size(); i++){
            if(byteCodeList.get(i) instanceof GoToCode){
                GoToCode code = (GoToCode)byteCodeList.get(i);
                code.setNum((Integer)labels.get(code.getLabel()));
            }else if(byteCodeList.get(i) instanceof FalseBranchCode){
                FalseBranchCode code = (FalseBranchCode)byteCodeList.get(i);
                code.setNum((Integer)labels.get(code.getLabel()));
            }else if(byteCodeList.get(i) instanceof CallCode){
                CallCode code = (CallCode)byteCodeList.get(i);
                code.setNum((Integer)labels.get(code.getFuncName()));
            }
        }
    }
    
    
}
