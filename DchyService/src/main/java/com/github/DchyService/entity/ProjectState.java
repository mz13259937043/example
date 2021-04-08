package com.github.DchyService.entity;

/**
 * @author 14874
 * @title: ProjectState(项目状态)
 * @projectName DchyService
 * @description: TODO
 * @date 2020/10/30 17:28
 */
public enum ProjectState {
    WTFB("1","委托发布"),
    WQQR("3","委托确认"),
    HTSQ("4","合同上传"),
    HTQR("5","合同确认"),
    CG("6","草稿"),
    YCX("7","已撤销"),
    GSZ("8","公示中"),
    HTQ("8","合同期"),
    BLZ("8","办理中"),
    YWJ("8","已完结"),
    BJ("6","办结");

    private String code;
    private String value;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    ProjectState(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
