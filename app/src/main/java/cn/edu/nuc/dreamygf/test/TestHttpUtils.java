package cn.edu.nuc.dreamygf.test;

import junit.framework.TestCase;

import cn.edu.nuc.dreamygf.utils.HttpUtils;


/**
 * Created by 51164 on 2018/6/4.
 */

public class TestHttpUtils extends TestCase {
    private static final String TAG = "TestHttpUtils";
    public void testSendInfo()
    {
        String res=HttpUtils.doPost("给我讲个笑话");
        System.out.println(res);
        res=HttpUtils.doPost("给我讲个鬼故事");
        System.out.println(res);

    }
}
