package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.domain.TerminalRealtimeParam;
import com.starcor.core.domain.TerminalRealtimeParamList;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetTermialRealtimeParamsSAXParserJson<Result>
  implements IXmlParser<Result>
{
  TerminalRealtimeParamList resultList = new TerminalRealtimeParamList();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
      return null;
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = new JSONObject(new String(paramArrayOfByte)).getJSONArray("data");
        if (localJSONArray != null)
        {
          i = 0;
          int j = localJSONArray.length();
          if (i < j)
          {
            TerminalRealtimeParam localTerminalRealtimeParam = new TerminalRealtimeParam();
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            localTerminalRealtimeParam.key = localJSONObject.optString("key");
            if (TextUtils.isEmpty(localTerminalRealtimeParam.key))
              break label141;
            localTerminalRealtimeParam.value = localJSONObject.optString("value");
            localTerminalRealtimeParam.group = localJSONObject.optString("group");
            this.resultList.addParam(localTerminalRealtimeParam);
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return this.resultList;
      label141: i++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetTermialRealtimeParamsSAXParserJson
 * JD-Core Version:    0.6.2
 */