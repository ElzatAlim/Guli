package com.aili.eduservice.service;

import com.aili.eduservice.entity.EduSubject;
import com.aili.eduservice.entity.subject.SubjectList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    //获取所有分类
    List<SubjectList> getAllSubject();
}
