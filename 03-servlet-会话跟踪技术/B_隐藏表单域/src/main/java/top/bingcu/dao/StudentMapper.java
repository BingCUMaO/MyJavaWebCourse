package top.bingcu.dao;

import top.bingcu.pojo.bean.Student;

import java.util.*;

public class StudentMapper {
    private Map<String, Student> map = new LinkedHashMap<>();

    public StudentMapper(){
        initDatas();
    }

    private void initDatas(){
        String uuid01 = UUID.randomUUID().toString();
        Student student01 = new Student();
        student01.setId(uuid01);
        student01.setName("张三");
        student01.setClazz("19软件工程1班");

        String uuid02 = UUID.randomUUID().toString();
        Student student02 = new Student();
        student02.setId(uuid02);
        student02.setName("李四");
        student02.setClazz("20软件工程2班");

        map.put(student01.getId(), student01);
        map.put(student02.getId(), student02);
    }

    public Student obtain(String id) {
        return map.get(id);
    }

    public Collection<Student> obtainAllStudents(){
        return map.values();
    }

    public Set<String> obtainAllStudentId(){
        return map.keySet();
    }

}
