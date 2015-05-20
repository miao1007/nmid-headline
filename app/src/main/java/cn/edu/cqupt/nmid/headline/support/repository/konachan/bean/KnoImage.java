package cn.edu.cqupt.nmid.headline.support.repository.konachan.bean;

/*
 * Copyright (c) 2014.
 * Author : leon
 * Feel free to ues it!
 */

import java.io.Serializable;

public class KnoImage implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    //Download Percent
    private int downLoadProgress = 0;
    private long mDownloadID = 0;

    public long getmDownloadID() {
        return mDownloadID;
    }

    public void setmDownloadID(long mDownloadID) {
        this.mDownloadID = mDownloadID;
    }

    public int getDownLoadProgress() {
        return downLoadProgress;
    }

    public void setDownLoadProgress(int downLoadProgress) {
        this.downLoadProgress = downLoadProgress;
    }

    //file > jpeg > sample > preview
    String tags;
    String rating;

    //file
    String file_url;
    int width;
    int height;
    int file_size;

    public String getStringResolution(int width,int height){
        String resolution = Integer.toString(width) + "x" + Integer.toString(height);
        return (resolution);
    }

    public String getStringSize(int size){
        float s = size;
        s/= 1000000;
        return Float.toString(s) + "MBit";
    }

    //jpeg
    String jpeg_url;
    int jpeg_width;
    int jpeg_height;
    int jpeg_file_size;


    //sample
    String sample_url;
    int sample_width;
    int sample_height;
    int sample_file_size;

    //preview
    String preview_url;
    int actual_preview_width;
    int actual_preview_height;

    public String getTags() {
        return tags;
    }

    public String getFile_url() {
        return file_url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFile_size() {
        return file_size;
    }

    public String getJpeg_url() {
        return jpeg_url;
    }

    public int getJpeg_width() {
        return jpeg_width;
    }

    public int getJpeg_height() {
        return jpeg_height;
    }

    public int getJpeg_file_size() {
        return jpeg_file_size;
    }

    public String getSample_url() {
        return sample_url;
    }

    public int getSample_width() {
        return sample_width;
    }

    public String getRating() {
        return rating;
    }

    public int getSample_height() {
        return sample_height;
    }

    public int getSample_file_size() {
        return sample_file_size;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public int getActual_preview_width() {
        return actual_preview_width;
    }

    public int getActual_preview_height() {
        return actual_preview_height;
    }

}
