package com.meeting.client.domain.sign;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UserInfoDomain implements Serializable {

    public boolean signStatus;

    public String id;
    public String name;
    public String phoneNum;
    public String company;
    public String post;
    public String head;

    public String tempSignName;

    public List<SignLocationDomain> locations;


}
