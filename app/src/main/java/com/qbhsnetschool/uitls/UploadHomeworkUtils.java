package com.qbhsnetschool.uitls;

public class UploadHomeworkUtils {

    private volatile UploadHomeworkUtils uploadHomeworkUtils;

    private UploadHomeworkUtils(){}

    public UploadHomeworkUtils getInstance() {
        if (uploadHomeworkUtils == null){
            synchronized (UploadHomeworkUtils.class){
                if (uploadHomeworkUtils == null){
                    uploadHomeworkUtils = new UploadHomeworkUtils();
                }
            }
        }
        return uploadHomeworkUtils;
    }


}
