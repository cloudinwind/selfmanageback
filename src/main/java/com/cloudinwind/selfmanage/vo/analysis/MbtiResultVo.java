package com.cloudinwind.selfmanage.vo.analysis;

import lombok.Data;

@Data
public class MbtiResultVo {

    private Long userId;

    private String email;

    private String name;

    private String avatarUrl;

    private int E;

    private int I;

    private int S;

    private int N;

    private int T;

    private int F;

    private int J;

    private int P;

    private String result;

    private String pe;

    private String pi;

    private String ps;

    private String pn;

    private String pt;

    private String pf;

    private String pj;

    private String pp;
}
