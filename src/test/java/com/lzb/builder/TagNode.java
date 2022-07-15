package com.lzb.builder;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-05-23 23:47
 *
 * @author lizebin
 */
public class TagNode {

    private String tagName;
    private List<TagNode> children;

    public TagNode(String tagName) {
        this.tagName = tagName;
    }

    public void add(TagNode childNode) {
        children.add(childNode);
    }

}
