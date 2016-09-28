package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetSecretKeysInfo
  implements Serializable
{
  public List<ApiEncrypt> apiEncrypts = new ArrayList();
  public List<SecretKeys> secretKeys = new ArrayList();
  public String validKeyGroup = "";

  public static class ApiEncrypt
  {
    public String requestEncryptMode = "";
    public String responseEncryptMode = "";
    public String sign = "";
  }

  public static class SecretKeys
    implements Comparable<SecretKeys>
  {
    public String requestEncryptKey = "";
    public String responseEncryptKey = "";
    public String sign = "";

    public int compareTo(SecretKeys paramSecretKeys)
    {
      return this.sign.compareTo(paramSecretKeys.sign);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.GetSecretKeysInfo
 * JD-Core Version:    0.6.2
 */