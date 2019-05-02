package com.example.minitwitter.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUploadPhoto {

    @SerializedName("fieldname")
    @Expose
    private String fieldname;

    @SerializedName("orifinalname")
    @Expose
    private String orifinalname;

    @SerializedName("encoding")
    @Expose
    private String encoding;

    @SerializedName("mimetype")
    @Expose
    private String mimetype;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("size")
    @Expose
    private String size;

    public ResponseUploadPhoto() {
    }

    public ResponseUploadPhoto(String fieldname, String orifinalname, String encoding, String mimetype, String destination, String filename, String path, String size) {
        this.fieldname = fieldname;
        this.orifinalname = orifinalname;
        this.encoding = encoding;
        this.mimetype = mimetype;
        this.destination = destination;
        this.filename = filename;
        this.path = path;
        this.size = size;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getOrifinalname() {
        return orifinalname;
    }

    public void setOrifinalname(String orifinalname) {
        this.orifinalname = orifinalname;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
