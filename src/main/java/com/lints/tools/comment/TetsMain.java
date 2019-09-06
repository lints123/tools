package com.lints.tools.comment;

import com.lints.tools.entity.Student;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TetsMain {

    public static void main(String[] args) throws FileNotFoundException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Student st = new Student();
            st.setId(i);
            st.setName("张珊"+i);
            st.setSex("男");
            st.setSex1("1na");

            list.add(st);
        }

        String[] str = {"ID", "测试标题", "标题1", "标题2"};
        String[] str1 = {"子标题1", "子标题2", "子标题3", "子标题4"};
        ExeclUtil.exportExecl("测试",str,str1,list,new FileOutputStream("E:/test.xlsx"));
        System.out.println("ExeclUtil.main");
    }

}
