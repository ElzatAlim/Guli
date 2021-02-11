package com.aili.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 艾力
 * @date 2021/1/17 16:47
 **/


@Data
public class DemoData {

    //设置Excel表头的注解
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;

}
