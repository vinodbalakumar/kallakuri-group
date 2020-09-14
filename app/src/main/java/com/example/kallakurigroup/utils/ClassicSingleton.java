package com.example.kallakurigroup.utils;

public class ClassicSingleton {

 private static ClassicSingleton instance = null;

 public static String VersionCode = "0.1 - 1";

 public static boolean enable_logs = true;

 public String missedcall_number, Idtwofactorauth, MissedCallFeature = "0";

 private ClassicSingleton() {
  // Exists only to defeat instantiation.
 }

 public static ClassicSingleton getInstance() {
  if(instance == null) {
   instance = new ClassicSingleton();
  }
  return instance;
 }

/* http://ec2-3-84-237-24.compute-1.amazonaws.com:8080/v1/error

 Request :


 {
  "description": "screenname_issue_discription",
         "errorMessage": "error.getMessage()",
         "id": 0,
         "phoneNo": "9542233301"
 }*/

}