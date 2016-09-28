package com.starcor.core.parser.json;

import com.starcor.core.domain.BarrageMeta;
import com.starcor.core.domain.BarrageResponse;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetBarrageDataParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = GetBarrageDataParserJson.class.getSimpleName();
  private BarrageResponse barrageResponse;

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
    throws JSONException
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0));
    while (true)
    {
      int k;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(new String(paramArrayOfByte));
        this.barrageResponse = new BarrageResponse();
        this.barrageResponse.setErrorCode(localJSONObject1.getInt("err_code"));
        this.barrageResponse.setErrorMsg(localJSONObject1.optString("err_msg"));
        if (localJSONObject1.has("data"))
        {
          JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
          this.barrageResponse.setInterval(localJSONObject2.optInt("interval"));
          this.barrageResponse.setCurTimeKey(localJSONObject2.optString("currentTimeKey"));
          int i = localJSONObject2.optInt("total");
          if (i > 0)
          {
            this.barrageResponse.setTotalCount(i);
            JSONArray localJSONArray = localJSONObject2.getJSONArray("datas");
            int j = localJSONArray.length();
            if (j > 0)
            {
              this.barrageResponse.setTotalCount(j);
              if (j != i)
                Logger.w(TAG, "totalCount: " + i + ", realCount: " + j);
              long l = System.currentTimeMillis();
              k = 0;
              if (k < j)
              {
                JSONObject localJSONObject3 = localJSONArray.getJSONObject(k);
                if (localJSONObject3 == null)
                  break label443;
                BarrageMeta localBarrageMeta = new BarrageMeta();
                localBarrageMeta.nickName = localJSONObject3.optString("nickName");
                localBarrageMeta.avatar = localJSONObject3.optString("avatar");
                localBarrageMeta.uuid = localJSONObject3.optString("uuid");
                localBarrageMeta.createTime = localJSONObject3.optString("createTime");
                localBarrageMeta.rcvTime = l;
                localBarrageMeta.pos = localJSONObject3.optInt("pos");
                localBarrageMeta.content = localJSONObject3.optString("barrageContent");
                localBarrageMeta.fontColor = localJSONObject3.optInt("fontColor");
                localBarrageMeta.fontSize = localJSONObject3.optInt("fontSize");
                this.barrageResponse.addBarrageMeta(localBarrageMeta);
                break label443;
              }
            }
            else
            {
              this.barrageResponse.setTotalCount(0);
              Logger.e(TAG, "totalCount: " + i + ", realCount: " + j);
            }
          }
        }
        BarrageResponse localBarrageResponse = this.barrageResponse;
        return localBarrageResponse;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        Logger.e(TAG, "parse result error: " + localJSONException);
        throw localJSONException;
      }
      return null;
      label443: k++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetBarrageDataParserJson
 * JD-Core Version:    0.6.2
 */