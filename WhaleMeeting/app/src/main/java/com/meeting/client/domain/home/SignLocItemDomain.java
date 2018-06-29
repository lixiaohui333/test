package com.meeting.client.domain.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SignLocItemDomain implements Serializable{

    public String Signer_Drop_Id;
    public String Signer_Drop_Name;


    @Override
    public String toString() {
        return "SignLocItemDomain{" +
                "Signer_Drop_Id='" + Signer_Drop_Id + '\'' +
                ", Signer_Drop_Name='" + Signer_Drop_Name + '\'' +
                '}';
    }
}
