package com.starcor.core.sax;

import com.starcor.core.domain.GetSecretKeysInfo;
import com.starcor.core.domain.GetSecretKeysInfo.ApiEncrypt;
import com.starcor.core.domain.GetSecretKeysInfo.SecretKeys;
import java.util.Collections;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetSecretKeysHandler extends DefaultHandler
{
  private GetSecretKeysInfo.ApiEncrypt apiEncrypt;
  private String curTagName;
  private GetSecretKeysInfo getSecretKeysInfo;
  private String parentTagName;
  private StringBuilder sb = new StringBuilder();
  private GetSecretKeysInfo.SecretKeys secretKeys;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    if ("secret_keys".equals(this.parentTagName))
      if ("sign".equals(this.curTagName))
        this.secretKeys.sign = str;
    do
    {
      do
      {
        do
        {
          return;
          if ("request_encrypt_key".equals(this.curTagName))
          {
            this.secretKeys.requestEncryptKey = str;
            return;
          }
          if ("response_encrypt_key".equals(this.curTagName))
          {
            this.secretKeys.responseEncryptKey = str;
            return;
          }
        }
        while (!"valid_group".equals(this.curTagName));
        this.getSecretKeysInfo.validKeyGroup = str;
        return;
      }
      while (!"api_encrypt".equals(this.parentTagName));
      if ("sign".equals(this.curTagName))
      {
        this.apiEncrypt.sign = str;
        return;
      }
      if ("request_encrypt_mode".equals(this.curTagName))
      {
        this.apiEncrypt.requestEncryptMode = str;
        return;
      }
    }
    while (!"response_encrypt_mode".equals(this.curTagName));
    this.apiEncrypt.responseEncryptMode = str;
  }

  public void endDocument()
    throws SAXException
  {
    super.endDocument();
    Collections.sort(this.getSecretKeysInfo.secretKeys);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("secret_keys"))
      this.parentTagName = null;
    while (true)
    {
      this.curTagName = null;
      return;
      if (paramString2.equalsIgnoreCase("api_encrypt"))
        this.parentTagName = null;
    }
  }

  public GetSecretKeysInfo getGetSecretKeysInfo()
  {
    return this.getSecretKeysInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.getSecretKeysInfo = new GetSecretKeysInfo();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    if (paramString2.equalsIgnoreCase("secret_keys"))
      this.parentTagName = paramString2;
    while (true)
    {
      this.curTagName = paramString2;
      return;
      if (paramString2.equalsIgnoreCase("api_encrypt"))
        this.parentTagName = paramString2;
      else if (paramString2.equalsIgnoreCase("i"))
        if ("secret_keys".equals(this.parentTagName))
        {
          this.secretKeys = new GetSecretKeysInfo.SecretKeys();
          this.getSecretKeysInfo.secretKeys.add(this.secretKeys);
        }
        else if ("api_encrypt".equals(this.parentTagName))
        {
          this.apiEncrypt = new GetSecretKeysInfo.ApiEncrypt();
          this.getSecretKeysInfo.apiEncrypts.add(this.apiEncrypt);
        }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetSecretKeysHandler
 * JD-Core Version:    0.6.2
 */