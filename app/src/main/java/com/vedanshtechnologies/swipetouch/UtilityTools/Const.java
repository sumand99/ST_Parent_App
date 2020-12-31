package com.vedanshtechnologies.swipetouch.UtilityTools;

/**
 * Created by HPLAPTOP on 20/12/2019.
 */

public class Const {

    public interface URL {


        String HOST_URL = "http://stage.swiftcampus.com/universal_app_api2.php?Parameter=";

        String LeaveReasons= HOST_URL+"LeaveReason&StudentId=12345";
        String submitleave= HOST_URL+"SubmitLeaves&StudentId=12345";
        String ListOfLeaves= HOST_URL+"ListOfLeaves&StudentId=12345";
        String UserLogin= HOST_URL+"LoginAPI&StudentId=12345";
        String OnlineMeetingList= HOST_URL+"MeetingList&StudentId=12345";
        String NoticeList= HOST_URL+"NoticeList&StudentId=12345";
        String NoticeDescription= HOST_URL+"NoticeDescription&StudentId=12345&Notice_Id=1";


    }
}

