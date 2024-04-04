package com.manager.smc;

import java.util.Date;
import java.util.List;

public class Test {
    private Mark mark;
    private final Subject subject;
    private final String date;

    public Test(Subject subject, String date, Mark mark){
        this.subject=subject;
        this.date=date;
        this.mark=mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
