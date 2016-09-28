package com.starcor.hunan.opendownload.drm;

import android.util.Base64;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.utils.Logger;

public class PlayUrlParams
{
  private static final String TAG = PlayUrlParams.class.getSimpleName();
  private String actionToken;
  private String cid;
  private DrmGetUrlCallback drmGetUrlCallback;
  private String idxUrl;
  private String playUrl;
  private String sourceContentType;
  private String sourceType;
  final String testIdxUrl = "http://m.idx.imgo.tv/idx/201412268c37da5b-237f-4682-81e8-d2f5737d2d2a_20141226183449_enc.ts.idx";
  final String testIdxUrl2 = "http://113.247.251.136:8080/20141_29864_enc.ts.idx";
  final String testPlayUrl = "http://113.247.251.136:8080/201412268c37da5b-237f-4682-81e8-d2f5737d2d2a_20141226183449_enc.ts";
  final String testPlayUrl2 = "http://113.247.251.136:8080/20141_29864_enc.ts";
  final String testToken = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ActionToken xmlns=\"urn:marlin:broadband:1-2:nemo:services:action-token\"             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">    <ConfigurationInfo broadbandServiceId=\"urn:marlin:organization:seacert:eval-marlin-service:500\"                       configVersion=\"1\">        <ResourceLocation>http://bb.test.expressplay.cn:80/mbs/ConfigurationToken.xml</ResourceLocation>    </ConfigurationInfo>    <LicenseAcquisition>      <Type>personality</Type>      <BusinessToken>AA0AAwAACFUAA0hEUF44ABDEodFfwamqwd4qyKuUvmCIAHCKTxPvjM+EGhs0m3QtZAz96J+/nYxZv6FsV7gZDd209L6lQBs9ZNRvhevGcWk3mCXvJAPaDX++eO8kexw1JfqhFBCiZgKRHkw/j1Io8LLD+fTVvqiVX0YUi7hOd1g5haKi1xitQlWeFeFWUQvxOZPHAAAAFDKOslTGsJqVFRkEuxVSMolZ10QV</BusinessToken>    </LicenseAcquisition></ActionToken>";
  final String testToken2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n\n\n\n\n\n<ActionToken xmlns=\"urn:marlin:broadband:1-2:nemo:services:action-token\"\n             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n    <ConfigurationInfo broadbandServiceId=\"urn:marlin:organization:seacert:marlin-service:200\"\n                       configVersion=\"1\">\n        <ResourceLocation>http://bb.china.expressplay.cn:80/mbs/ConfigurationToken.xml</ResourceLocation>\n    </ConfigurationInfo>\n    <LicenseAcquisition>\n\n        <Type>personality</Type>\n\n        <BusinessToken>AA0AAwAAA9IAA0hEUOBbABDdHmy5d0v/bbHxYhH3re9dAHDJQSAxSATO6OM1pQ0Pw7t9o+RnfsUv\n            exVooiuLF0OXVHuIz2uBW+9f6OUD+z76Qdj4GIB71YfF4Ns3XNE+5/8sIW3PLdZSc/iqe27jhneR\n            c/yr4uf7h35z+98zXB31ip4bc1/17safZrOCEzxJIYn+AAAAFMbwFu+h/gojFXbw+Bvrj92NvCG8\n        </BusinessToken>\n    </LicenseAcquisition>\n</ActionToken>";

  public PlayUrlParams(PlayInfoV2 paramPlayInfoV2, DrmGetUrlCallback paramDrmGetUrlCallback)
  {
    this.playUrl = paramPlayInfoV2.playUrl;
    try
    {
      String[] arrayOfString = paramPlayInfoV2.getUserValueByKey("mgtv_drm_token").split(":");
      for (int i = 0; i < arrayOfString.length; i++)
        Logger.d(TAG, "PlayUrlParams, tokenParams[" + i + "]=" + arrayOfString[i]);
      this.idxUrl = new String(Base64.decode(arrayOfString[1], 0));
      this.actionToken = new String(Base64.decode(arrayOfString[0], 0));
      this.cid = new String(Base64.decode(arrayOfString[2], 0));
      this.sourceType = "TS2";
      this.sourceContentType = "video/MP2T";
      this.drmGetUrlCallback = paramDrmGetUrlCallback;
      Logger.d(TAG, "PlayUrlParams, playUrl=" + this.playUrl);
      Logger.d(TAG, "PlayUrlParams, idxUrl=" + this.idxUrl);
      Logger.d(TAG, "PlayUrlParams, actionToken=" + this.actionToken);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e(TAG, localException.getMessage());
    }
  }

  public String getActionToken()
  {
    return this.actionToken;
  }

  public String getCid()
  {
    return this.cid;
  }

  public DrmGetUrlCallback getDrmGetUrlCallback()
  {
    return this.drmGetUrlCallback;
  }

  public String getIdxUrl()
  {
    return this.idxUrl;
  }

  public String getPlayUrl()
  {
    return this.playUrl;
  }

  public String getSourceContentType()
  {
    return this.sourceContentType;
  }

  public String getSourceType()
  {
    return this.sourceType;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.PlayUrlParams
 * JD-Core Version:    0.6.2
 */