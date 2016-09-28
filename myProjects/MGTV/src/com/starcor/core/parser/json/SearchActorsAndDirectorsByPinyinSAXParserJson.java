package com.starcor.core.parser.json;

import com.starcor.core.domain.VideoRoleInfo;
import com.starcor.core.domain.VideoRoleList;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActorsAndDirectorsByPinyinSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "SearchActorsAndDirectorsByPinyinSAXParserJson";
  private VideoRoleList roleList = new VideoRoleList();

  private void loadActorList(JSONObject paramJSONObject)
  {
    try
    {
      this.roleList.actorList = new ArrayList();
      JSONArray localJSONArray = paramJSONObject.getJSONArray("actor_list");
      int i = 0;
      while (true)
        if (i < localJSONArray.length())
        {
          VideoRoleInfo localVideoRoleInfo = new VideoRoleInfo();
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.has("name"))
            localVideoRoleInfo.name = localJSONObject.getString("name");
          try
          {
            localVideoRoleInfo.hasLabel = Integer.valueOf(localJSONObject.getString("label")).intValue();
            if (localJSONObject.has("id"))
              localVideoRoleInfo.labelID = localJSONObject.getString("id");
            this.roleList.actorList.add(localVideoRoleInfo);
            i++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            while (true)
              localVideoRoleInfo.hasLabel = 0;
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void loadDirectorList(JSONObject paramJSONObject)
  {
    try
    {
      this.roleList.directorList = new ArrayList();
      JSONArray localJSONArray = paramJSONObject.getJSONArray("director_list");
      int i = 0;
      while (true)
        if (i < localJSONArray.length())
        {
          VideoRoleInfo localVideoRoleInfo = new VideoRoleInfo();
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.has("name"))
            localVideoRoleInfo.name = localJSONObject.getString("name");
          try
          {
            localVideoRoleInfo.hasLabel = Integer.valueOf(localJSONObject.getString("label")).intValue();
            if (localJSONObject.has("id"))
              localVideoRoleInfo.labelID = localJSONObject.getString("id");
            this.roleList.directorList.add(localVideoRoleInfo);
            i++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            while (true)
              localVideoRoleInfo.hasLabel = 0;
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte));
      if (localJSONObject.has("actor_list"))
        loadActorList(localJSONObject);
      if (localJSONObject.has("director_list"))
        loadDirectorList(localJSONObject);
      return this.roleList;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.SearchActorsAndDirectorsByPinyinSAXParserJson
 * JD-Core Version:    0.6.2
 */