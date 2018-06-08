package com.meeting.client.domain.sign;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SignLocationDomain implements Serializable {


        public String location;

        public long time;

        public SignLocationDomain(String location, long time) {
            this.location = location;
            this.time = time;
        }


}
