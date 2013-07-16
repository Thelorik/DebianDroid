package com.debian.debiandroid.apiLayer;

import org.ksoap2.serialization.PropertyInfo;

public class BTSSoapCaller extends SoapCaller{

    public BTSSoapCaller() {
    	NAMESPACE = "Debbugs/SOAP";
    	URL = "http://bugs.debian.org/cgi-bin/soap.cgi";
    }
    
    /** Key values for 'key' parameter in getBugs method*/
    public enum BUGKEY {PACKAGE, SUBMITTER,MAINT, SRC, SEVERITY, STATUS, OWNER};

    public String getBugs(String key, String value) {
    	PropertyInfo[] properties = new PropertyInfo[2];
        properties[0] = new PropertyInfo();
        properties[0].setName("key");
        properties[0].setValue(key);
        properties[0].setType(String.class);
        properties[1] = new PropertyInfo();
        properties[1].setName("value");
        properties[1].setValue(value);
        properties[1].setType(String.class);
        
        try {
        	return doRequest("get_bugs", "get_bugs", properties).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }
    
    public String getStatus(int[] bugNumbers) {
    	PropertyInfo[] properties = new PropertyInfo[bugNumbers.length];
    	for(int i=0; i<bugNumbers.length; i++) {
	        properties[i] = new PropertyInfo();
	        properties[i].setName("bugnumber");
	        properties[i].setValue(bugNumbers[i]);
	        properties[i].setType(int.class);
    	}
        try {
        	return doRequest("get_status", "get_status", properties).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }
    
    public String getBugLog(int bugNumber) {
    	PropertyInfo[] properties = new PropertyInfo[1];
        properties[0] = new PropertyInfo();
        properties[0].setName("bugnumber");
        properties[0].setValue(bugNumber);
        properties[0].setType(int.class);
        try {
        	return doRequest("get_bug_log", "get_bug_log", properties).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }
    
    public String getUserTag(String email, String[] tags) {
    	PropertyInfo[] properties = new PropertyInfo[1+tags.length];
        properties[0] = new PropertyInfo();
        properties[0].setName("email");
        properties[0].setValue(email);
        properties[0].setType(String.class);
        
        for(int i=1; i<tags.length; i++) {
	        properties[i] = new PropertyInfo();
	        properties[i].setName("tag");
	        properties[i].setValue(tags[i]);
	        properties[i].setType(String.class);
    	}
        try {
        	return doRequest("get_usertag", "get_usertag", properties).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }
    
    public String getNewestBugs(int numOfBugs) {
    	PropertyInfo[] properties = new PropertyInfo[1];
        properties[0] = new PropertyInfo();
        properties[0].setName("amount");
        properties[0].setValue(numOfBugs);
        properties[0].setType(int.class);
        try {
        	return doRequest("newest_bugs", "newest_bugs", properties).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }

}