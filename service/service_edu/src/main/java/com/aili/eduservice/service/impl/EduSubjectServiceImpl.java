package com.aili.eduservice.service.impl;

import com.aili.eduservice.entity.EduSubject;
import com.aili.eduservice.entity.excel.SubjectData;
import com.aili.eduservice.entity.subject.SubjectList;
import com.aili.eduservice.listener.SubjectExcelListener;
import com.aili.eduservice.mapper.EduSubjectMapper;
import com.aili.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //从文件中国读取 添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try{
            //文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取所有的分类
    @Override
    public List<SubjectList> getAllSubject() {

        List<SubjectList> subjectLists = new ArrayList<>();
        List<EduSubject> list = this.list(null);

//        如果属性名相同 可以用 这个方法 把值赋值个另一个对选哪个   BeanUtils.copyProperties();
        for (EduSubject subject : list) {
            SubjectList subjectList = new SubjectList();
            if ("0".equals(subject.getParentId())){
                subjectList.setId(subject.getId());
                subjectList.setLabel(subject.getTitle());
                List<SubjectList> lists = new ArrayList<>();
                //获取他的子类
                for (EduSubject children : list) {
                    if(children.getParentId().equals(subject.getId())){
                        SubjectList child = new SubjectList();
                        child.setId(children.getId());
                        child.setLabel(children.getTitle());
                        //有三级分类在这加就可以
                        lists.add(child);
                    }
                }
                subjectList.setChildren(lists);
                subjectLists.add(subjectList);
            }
        }
        return subjectLists;
    }
}
