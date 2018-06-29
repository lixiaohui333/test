package com.meeting.client.domain.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SignUserItemDomain implements Serializable{

    public String Signer_Drop_Id;


    public String User_Name;
    public String Phone;
    public String Head;

    public String Company;
    public String Position;

    public int Jurisdiction;
    public String SignInPoint;

    public int Signer_Number;

    public List<HistorySign> SignerLogsData;



    class HistorySign implements Serializable{

        public long Signer_Time;
        public String Signer_Drop_Name;

    }

}
