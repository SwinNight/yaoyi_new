package com.xiaojd.utils;
import com.sun.jna.Library;
import com.sun.jna.Native;
public interface HisInfoPost
    extends Library {

    HisInfoPost INSTANCE = (HisInfoPost) Native
            .loadLibrary("vanxsd", HisInfoPost.class);
    // 说明书测试
    /*void runGetPrescription(String url, String drugInstructionId);
    
    // 合理用药审核测试
    String runxxxxx(String url, String param);*/
    int VanAnaData(String preType, String isSave, String sData);
}
