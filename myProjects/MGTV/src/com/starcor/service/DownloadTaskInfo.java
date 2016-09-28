package com.starcor.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadTaskInfo
  implements Parcelable
{
  public static Parcelable.Creator<DownloadTaskInfo> CREATOR = new Parcelable.Creator()
  {
    public DownloadTaskInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      DownloadTaskInfo localDownloadTaskInfo = new DownloadTaskInfo();
      localDownloadTaskInfo.readFromParcel(paramAnonymousParcel);
      return localDownloadTaskInfo;
    }

    public DownloadTaskInfo[] newArray(int paramAnonymousInt)
    {
      return new DownloadTaskInfo[paramAnonymousInt];
    }
  };
  public static final String TASK_STATE_CANCELLED = "cancelled";
  public static final String TASK_STATE_DOWNLOADING = "downloading";
  public static final String TASK_STATE_FAILED = "failed";
  public static final String TASK_STATE_INSTALLING = "installing";
  public static final String TASK_STATE_PAUSED = "paused";
  public static final String TASK_STATE_PENDING = "pending";
  public static final String TASK_STATE_SUCCESS = "success";
  long beginTime = 0L;
  long downloadSize = 0L;
  long endTime = 0L;
  String errCode = "";
  String errMsg = "";
  JSONObject extInfo = new JSONObject();
  int id = -1;
  String localFile = "";
  String state = "pending";
  HashSet<String> tags = new HashSet();
  long totalSize = 0L;
  String url = "";

  private void readFromParcel(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.url = paramParcel.readString();
    this.localFile = paramParcel.readString();
    this.state = paramParcel.readString();
    this.errCode = paramParcel.readString();
    this.errMsg = paramParcel.readString();
    this.downloadSize = paramParcel.readLong();
    this.totalSize = paramParcel.readLong();
    this.beginTime = paramParcel.readLong();
    this.endTime = paramParcel.readLong();
    this.tags = ((HashSet)paramParcel.readSerializable());
    try
    {
      this.extInfo = new JSONObject(paramParcel.readString());
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      this.extInfo = new JSONObject();
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public long getDownloadSize()
  {
    return this.downloadSize;
  }

  public JSONObject getExtInfo()
  {
    return this.extInfo;
  }

  public String getLocalFile()
  {
    return this.localFile;
  }

  public String getState()
  {
    return this.state;
  }

  public int getTaskId()
  {
    return this.id;
  }

  public long getTotalSize()
  {
    return this.totalSize;
  }

  public String getUrl()
  {
    return this.url;
  }

  public boolean isSuccess()
  {
    return "success".equals(this.state);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.url);
    paramParcel.writeString(this.localFile);
    paramParcel.writeString(this.state);
    paramParcel.writeString(this.errCode);
    paramParcel.writeString(this.errMsg);
    paramParcel.writeLong(this.downloadSize);
    paramParcel.writeLong(this.totalSize);
    paramParcel.writeLong(this.beginTime);
    paramParcel.writeLong(this.endTime);
    paramParcel.writeSerializable(this.tags);
    paramParcel.writeString(this.extInfo.toString());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.DownloadTaskInfo
 * JD-Core Version:    0.6.2
 */