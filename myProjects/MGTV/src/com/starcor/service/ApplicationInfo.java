package com.starcor.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.starcor.hunan.App;
import com.starcor.xul.XulUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationInfo
  implements Parcelable
{
  public static final String APP_STATE_INSTALLED = "installed";
  public static final String APP_STATE_UNINSTALLING = "uninstalling";
  public static final String APP_STATE_UNKNOWN = "unknown";
  public static Parcelable.Creator<ApplicationInfo> CREATOR;
  public JSONObject ext_info = new JSONObject();
  public String file;
  public String icon;
  public boolean is_installed = false;
  public String name;
  public String pkgName;
  public String sign;
  public String state = "unknown";
  public String task_id;
  public String url;
  public String version;
  public int versionCode;

  static
  {
    if (!ApplicationInfo.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      CREATOR = new Parcelable.Creator()
      {
        public ApplicationInfo createFromParcel(Parcel paramAnonymousParcel)
        {
          ApplicationInfo localApplicationInfo = new ApplicationInfo();
          localApplicationInfo.readFromParcel(paramAnonymousParcel);
          return localApplicationInfo;
        }

        public ApplicationInfo[] newArray(int paramAnonymousInt)
        {
          return new ApplicationInfo[paramAnonymousInt];
        }
      };
      return;
    }
  }

  public static ApplicationInfo fromPkgName(String paramString)
  {
    ApplicationInfo localApplicationInfo = new ApplicationInfo();
    localApplicationInfo.pkgName = paramString;
    PackageManager localPackageManager = App.getAppContext().getPackageManager();
    try
    {
      if ((!$assertionsDisabled) && (localPackageManager == null))
        throw new AssertionError();
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      localApplicationInfo.is_installed = false;
      return localApplicationInfo;
    }
    PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 64);
    localApplicationInfo.name = String.valueOf(localPackageInfo.applicationInfo.loadLabel(localPackageManager));
    localApplicationInfo.is_installed = true;
    localApplicationInfo.state = "installed";
    localApplicationInfo.version = localPackageInfo.versionName;
    localApplicationInfo.versionCode = localPackageInfo.versionCode;
    localApplicationInfo.file = localPackageInfo.applicationInfo.sourceDir;
    localApplicationInfo.sign = XulUtils.calMD5(localPackageInfo.signatures[0].toByteArray());
    File localFile = new File(App.getAppContext().getDir("app_mall", 1), localApplicationInfo.pkgName + "-icon.png");
    Bitmap localBitmap;
    if ((!localFile.exists()) || (localFile.lastModified() < localPackageInfo.lastUpdateTime))
    {
      Drawable localDrawable = localPackageInfo.applicationInfo.loadIcon(localPackageManager);
      int i = App.Operation(32.0F);
      localDrawable.setBounds(0, 0, i, i);
      Canvas localCanvas = new Canvas();
      localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
      localCanvas.setBitmap(localBitmap);
      localDrawable.draw(localCanvas);
    }
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      localBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
      localFileOutputStream.flush();
      localFileOutputStream.close();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("data:image/png;base64,");
      FileInputStream localFileInputStream = new FileInputStream(localFile);
      byte[] arrayOfByte = new byte[(int)localFile.length()];
      localFileInputStream.read(arrayOfByte);
      localStringBuilder.append(Base64.encodeToString(arrayOfByte, 2));
      localApplicationInfo.icon = localStringBuilder.toString();
      return localApplicationInfo;
    }
    catch (Exception localException2)
    {
      while (true)
        localException2.printStackTrace();
    }
  }

  private void readFromParcel(Parcel paramParcel)
  {
    int i = 1;
    this.name = paramParcel.readString();
    this.pkgName = paramParcel.readString();
    this.state = paramParcel.readString();
    if (paramParcel.readInt() == i);
    while (true)
    {
      this.is_installed = i;
      this.icon = paramParcel.readString();
      this.url = paramParcel.readString();
      this.file = paramParcel.readString();
      this.version = paramParcel.readString();
      this.versionCode = paramParcel.readInt();
      this.sign = paramParcel.readString();
      this.task_id = paramParcel.readString();
      try
      {
        this.ext_info = new JSONObject(paramParcel.readString());
        return;
        i = 0;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        this.ext_info = new JSONObject();
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public JSONObject toJson()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("name", this.name);
    }
    catch (JSONException localJSONException11)
    {
      try
      {
        localJSONObject.put("package", this.pkgName);
      }
      catch (JSONException localJSONException11)
      {
        try
        {
          localJSONObject.put("state", this.state);
        }
        catch (JSONException localJSONException11)
        {
          try
          {
            localJSONObject.put("is_installed", this.is_installed);
          }
          catch (JSONException localJSONException11)
          {
            try
            {
              localJSONObject.put("icon", this.icon);
            }
            catch (JSONException localJSONException11)
            {
              try
              {
                localJSONObject.put("url", this.url);
              }
              catch (JSONException localJSONException11)
              {
                try
                {
                  localJSONObject.put("file", this.file);
                }
                catch (JSONException localJSONException11)
                {
                  try
                  {
                    localJSONObject.put("version", this.version);
                  }
                  catch (JSONException localJSONException11)
                  {
                    try
                    {
                      localJSONObject.put("versionCode", this.versionCode);
                    }
                    catch (JSONException localJSONException11)
                    {
                      try
                      {
                        localJSONObject.put("sign", this.sign);
                      }
                      catch (JSONException localJSONException11)
                      {
                        try
                        {
                          localJSONObject.put("task_id", this.task_id);
                        }
                        catch (JSONException localJSONException11)
                        {
                          try
                          {
                            while (true)
                            {
                              localJSONObject.put("ext_info", this.ext_info);
                              return localJSONObject;
                              localJSONException1 = localJSONException1;
                              localJSONException1.printStackTrace();
                              continue;
                              localJSONException2 = localJSONException2;
                              localJSONException2.printStackTrace();
                              continue;
                              localJSONException3 = localJSONException3;
                              localJSONException3.printStackTrace();
                              continue;
                              localJSONException4 = localJSONException4;
                              localJSONException4.printStackTrace();
                              continue;
                              localJSONException5 = localJSONException5;
                              localJSONException5.printStackTrace();
                              continue;
                              localJSONException6 = localJSONException6;
                              localJSONException6.printStackTrace();
                              continue;
                              localJSONException7 = localJSONException7;
                              localJSONException7.printStackTrace();
                              continue;
                              localJSONException8 = localJSONException8;
                              localJSONException8.printStackTrace();
                              continue;
                              localJSONException9 = localJSONException9;
                              localJSONException9.printStackTrace();
                              continue;
                              localJSONException10 = localJSONException10;
                              localJSONException10.printStackTrace();
                              continue;
                              localJSONException11 = localJSONException11;
                              localJSONException11.printStackTrace();
                            }
                          }
                          catch (JSONException localJSONException12)
                          {
                            localJSONException12.printStackTrace();
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return localJSONObject;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.pkgName);
    paramParcel.writeString(this.state);
    if (this.is_installed);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      paramParcel.writeString(this.icon);
      paramParcel.writeString(this.url);
      paramParcel.writeString(this.file);
      paramParcel.writeString(this.version);
      paramParcel.writeInt(this.versionCode);
      paramParcel.writeString(this.sign);
      paramParcel.writeString(this.task_id);
      paramParcel.writeString(this.ext_info.toString());
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.ApplicationInfo
 * JD-Core Version:    0.6.2
 */