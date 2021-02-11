package com.aili.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/17 16:49
 **/
public class TestEasyExcel {
    public static void main(String[] args) {
//        //设置写入Excel文件夹的地址和名称
        String filename  = "D:/write.xlsx";
//        //调用EasyExcel的方法
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getList());

        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();


    }

    //学生集合
    private static List<DemoData> getList(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("学生"+i);
            list.add(data);
        }
        return list;
    }
}
