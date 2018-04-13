package com.linsr.wanandroid.data.model;

import com.linsr.linlibrary.model.BasePojo;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description
 @author Linsr
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePojo extends BasePojo {

    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int visible;
    private int zan;
    private List<?> tags;

}
