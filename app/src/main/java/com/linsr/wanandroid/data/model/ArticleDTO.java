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
public class ArticleDTO extends BasePojo{

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticlePojo> datas;

}
