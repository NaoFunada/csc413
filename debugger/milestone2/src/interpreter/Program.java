
package interpreter;

import java.util.*;
import interpreter.bytecode.*;

public class Program extends Object{
    ArrayList byteCodeList;
    HashMap labels;
    
    Program(ArrayList list){
        labels = new HashMap();
        byteCodeList = list;
          
    }
    
    public ByteCode getCode(int i){
        return (ByteCode)byteCodeList.get(i);
    }
    
    public void resolveAddress(){
        //store all labels addresses in hashmap
        for(int i = 0; i < byteCodeList.size(); i++){
            if(byteCodeList.get(i) instanceof LabelCode){
                LabelCode code = (LabelCode)byteCodeList.get(i);
                labels.put(code.getLabel(), i);
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
