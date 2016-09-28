package com.starcor.hunan.opendownload.encrypt;

import java.util.ArrayList;
import java.util.List;

public class EncryptConfig
{
  static final String AES_TRANSFORMATION = "AES/ECB/NoPadding";
  static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
  static List<String> noEncryptionArgs = new ArrayList();
  static List<String> retainArgsName = new ArrayList();

  static
  {
    noEncryptionArgs.add("nns_token");
    noEncryptionArgs.add("nns_user_id");
    noEncryptionArgs.add("nns_mac_id");
    noEncryptionArgs.add("nns_mac");
    noEncryptionArgs.add("nns_smart_card_id");
    noEncryptionArgs.add("nns_device_id");
    noEncryptionArgs.add("nns_user_password");
    noEncryptionArgs.add("nns_webtoken");
    noEncryptionArgs.add("nns_epg_server");
    noEncryptionArgs.add("nns_net_id");
    noEncryptionArgs.add("nns_login_id");
    noEncryptionArgs.add("nns_ex_data");
    retainArgsName.add("nns_func");
    retainArgsName.add("nns_output_type");
    retainArgsName.add("nns_proxy_cache");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.encrypt.EncryptConfig
 * JD-Core Version:    0.6.2
 */