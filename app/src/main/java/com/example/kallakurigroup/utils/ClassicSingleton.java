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
 // public static ArrayList<SchemesModel> list=new ArrayList<SchemesModel>();

  /* public static int OperatorsVerCode = 0;
   public static int TabMenuVerCode = 0;
   public static int UrlsVerCode = 0;
   public static int ALMVerCode = 0;
   public static JSONObject Operators = null;
   public static JSONObject URLS = null;
   public static JSONObject tabMenu = null;
   public static JSONObject ALM = null;

   public static JSONObject ob, operators;
   public static JSONObject jsonRootObject;
   public static JSONArray MobileArray, DthArray, PowerArray, GasArray, InsArray, array_menu_order = null;
   public static int position = 0, plans_Flag = 0, plans_value_amount = 1 ;
   public static ArrayList<String> descriptions, titles_plans, valid_of_plan;
   public static ArrayList<String> Code, AccountNum, BankName;

   public static HashMap<String, String> prevSelectedVariants = new HashMap<>();

   public static Float promo_amt;
   public static int PingBackTime = 15;


   public static String mDeviceAddress,paynear_macid="";

   public static String partnerAPIKey="AFAB49A656DD";
   // public static String partnerAPIKey="2016665A802D";
   //test  2016665A802D
   //live  AFAB49A656DD

   public static ArrayList<String> IFSC_array, city_array, state_bene_array, branch_bene_array, mobile_bene_array,
           bene_status_array, account_array, verify_array, bene_id_array, otp_status_array, mmid_addedby, mmid_number, bank_ref_num;

   public static ArrayList<String> his_tr_DATE, his_tr_SenName,
           his_tr_RecName, his_tr_TransType,
           his_tr_main_stats, his_tr_senLogo,
           his_tr_receiLogo, his_tr_amount, ifsc_his,
           send_mobile_his, sender_acc_his, branch_his, his_tr_RecPhone, State_arr, City_array;

   public static ListView listView_Beneficiary;

   public static String from_money_trans_login = "false";
   public static ArrayList<String> issue_status_cpl_list,
           date_compl_list, message_cpl_list, compla_present, compl_type,
           Type_comple_date, Type_compl_operator, Type_compl_mobile_num, Type_comple_trans_num, Type_compl_status;

   public static String imgId[] = {};
   public static String Ban_imgId[] = {};
   public static int Ban_Time = 10, setIFSC_valu = 0, show_Fail = 0;
   public static int Time = 10;
   public static int S_Time = 30;

   public static final String PREFERENCE = "KALLAKURI";


   public static boolean almflag = true;
   public static boolean pincodeflag = true, flag_OTP_page = false, flag_OTP_page1 = false;
   public static Boolean plans_page = false;

   public static HashMap<String, Integer> tab_select_icon_map = new HashMap<>();
   public static HashMap<String, Integer> tab_unselect_icon_map = new HashMap<>();


   public static int poss = 1000;
   public static int poss_mob = 1000;

   public static String selectedtab = "";
   public static String selectedoprname = "";

   public static String devid;

   public static String query_cat = "";
   public static String query_key = "prepaid";

   public static List<String> op_status_list = new ArrayList<String>();
   public static List<String> ppop_status_list = new ArrayList<String>();
   public static List<String> dthop_status_list = new ArrayList<String>();
   public static List<String> gasop_status_list = new ArrayList<String>();

   public static List<String> powerop_status_list = new ArrayList<String>();
   public static List<String> insop_status_list = new ArrayList<String>();

   public static String seltag="";

   public static int deviceCommMode=1;

   public static String printservice="";

 *//* public static String agent_tagged, user_id = "", agent_id = "", user_rold_id, username*//*;
  public static String appType = "RESTOCK", user_balance_new, user_balance_mf, cash_menu_bal, insta_menu_bal,user_balance,
        cashbackBal;

 public static String idProdHist = "";
 public static  int userQTY = 0;

 public static String email_agent_ver;
   // public static String from="newsignup";
 public JSONArray pod_data;
 public boolean from_editcod;
 public JSONObject deliveryAddress;
 public JSONObject selected_address_json = new JSONObject();
 public Bitmap bitmapNoti = null;
 public boolean launch = false;
 //public static boolean alreadysined = false;


 public static int businessSelectionPosition = -1;


 public List<Bitmap> bitmapsData = new ArrayList<>();
 public List<String> imagePathsData = new ArrayList<>();

 public static boolean fromDelete = false;
 public static int bitmapCurrentPosition =-1;
 public static Bitmap currentCapturedimageBitmap = null;

 public Bitmap CurrentbitmapsData = null;
 public String CurrentimagePathsData = null;


 public static String data = "";


 public static String fromDT = "";
 public static String toDT = "";

 public static String atm_text = "";
 public static String sale_text = "", instacash_flag="";
 public static String shop_cart_count="0";
 public static String shop_cart_amt, NM_number;

 public static String sale_pop ,atm_pop ,kyc_showPop , kyc_register , kycmessage , kycmessagewallet, kycFlag_btn_disable;

 public static JSONObject bank_resp_notf;

 public static boolean inappnotify_flag=false;

 public static int pictype=0;


 public static int cartQuantity = 0;


 public static boolean isSupport = false;

 public static JSONObject current_track_order = new JSONObject();//check
 public static JSONObject current_order_item = new JSONObject();//check


 public static String document_type_json = "";
 public static String signatorydocument_type = "";
 public static String idproof_type = "";

 public static String registeredMobileNumber = "";*/

// public static boolean allowClick = false;

}