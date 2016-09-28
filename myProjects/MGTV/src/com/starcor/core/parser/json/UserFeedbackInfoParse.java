package com.starcor.core.parser.json;

import com.starcor.core.domain.GetUserFeedbackInfo;
import com.starcor.core.domain.GetUserFeedbackInfo.ResultDataInfo;

public class UserFeedbackInfoParse
{
  GetUserFeedbackInfo.ResultDataInfo resultDataInfo;
  GetUserFeedbackInfo userFeedbackInfo = new GetUserFeedbackInfo();

  // ERROR //
  public GetUserFeedbackInfo parseFeebbackReturnInfo(java.lang.String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 27	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_1
    //   10: invokestatic 33	com/starcor/core/utils/JsonUtils:getJsonObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   13: astore_3
    //   14: aload_3
    //   15: ldc 35
    //   17: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   20: ifeq +52 -> 72
    //   23: aload_0
    //   24: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   27: aload_3
    //   28: ldc 35
    //   30: invokevirtual 45	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   33: invokestatic 51	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   36: invokevirtual 55	java/lang/Integer:intValue	()I
    //   39: putfield 59	com/starcor/core/domain/GetUserFeedbackInfo:state	I
    //   42: ldc 61
    //   44: new 63	java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   51: ldc 66
    //   53: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: aload_0
    //   57: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   60: getfield 59	com/starcor/core/domain/GetUserFeedbackInfo:state	I
    //   63: invokevirtual 73	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   66: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: invokestatic 83	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   72: aload_3
    //   73: ldc 85
    //   75: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   78: ifeq +46 -> 124
    //   81: aload_0
    //   82: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   85: aload_3
    //   86: ldc 85
    //   88: invokevirtual 45	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   91: putfield 89	com/starcor/core/domain/GetUserFeedbackInfo:msg	Ljava/lang/String;
    //   94: ldc 61
    //   96: new 63	java/lang/StringBuilder
    //   99: dup
    //   100: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   103: ldc 91
    //   105: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: aload_0
    //   109: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   112: getfield 89	com/starcor/core/domain/GetUserFeedbackInfo:msg	Ljava/lang/String;
    //   115: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   121: invokestatic 83	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   124: aload_3
    //   125: ldc 93
    //   127: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   130: ifeq +192 -> 322
    //   133: aload_0
    //   134: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   137: astore 7
    //   139: aload 7
    //   141: invokevirtual 97	java/lang/Object:getClass	()Ljava/lang/Class;
    //   144: pop
    //   145: aload_0
    //   146: new 99	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo
    //   149: dup
    //   150: aload 7
    //   152: invokespecial 102	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo:<init>	(Lcom/starcor/core/domain/GetUserFeedbackInfo;)V
    //   155: putfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   158: new 37	org/json/JSONObject
    //   161: dup
    //   162: aload_3
    //   163: ldc 93
    //   165: invokevirtual 45	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   168: invokespecial 107	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   171: astore 9
    //   173: aload 9
    //   175: ldc 109
    //   177: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   180: ifeq +47 -> 227
    //   183: aload_0
    //   184: getfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   187: aload 9
    //   189: ldc 109
    //   191: invokevirtual 45	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   194: putfield 111	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo:id	Ljava/lang/String;
    //   197: ldc 61
    //   199: new 63	java/lang/StringBuilder
    //   202: dup
    //   203: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   206: ldc 113
    //   208: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: aload_0
    //   212: getfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   215: getfield 111	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo:id	Ljava/lang/String;
    //   218: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   224: invokestatic 83	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   227: aload 9
    //   229: ldc 115
    //   231: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   234: ifeq +47 -> 281
    //   237: aload_0
    //   238: getfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   241: aload 9
    //   243: ldc 115
    //   245: invokevirtual 45	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   248: putfield 117	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo:deviceId	Ljava/lang/String;
    //   251: ldc 61
    //   253: new 63	java/lang/StringBuilder
    //   256: dup
    //   257: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   260: ldc 119
    //   262: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: aload_0
    //   266: getfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   269: getfield 117	com/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo:deviceId	Ljava/lang/String;
    //   272: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   278: invokestatic 83	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   281: aload_0
    //   282: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   285: aload_0
    //   286: getfield 104	com/starcor/core/parser/json/UserFeedbackInfoParse:resultDataInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   289: putfield 122	com/starcor/core/domain/GetUserFeedbackInfo:result_data	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   292: ldc 61
    //   294: new 63	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   301: ldc 124
    //   303: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: aload_0
    //   307: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   310: getfield 122	com/starcor/core/domain/GetUserFeedbackInfo:result_data	Lcom/starcor/core/domain/GetUserFeedbackInfo$ResultDataInfo;
    //   313: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   316: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   319: invokestatic 83	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   322: aload_0
    //   323: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   326: areturn
    //   327: astore 4
    //   329: aload 4
    //   331: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   334: aload_0
    //   335: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   338: iconst_0
    //   339: putfield 59	com/starcor/core/domain/GetUserFeedbackInfo:state	I
    //   342: goto -270 -> 72
    //   345: astore_2
    //   346: aload_2
    //   347: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   350: aload_0
    //   351: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   354: iconst_0
    //   355: putfield 59	com/starcor/core/domain/GetUserFeedbackInfo:state	I
    //   358: goto -36 -> 322
    //   361: astore 5
    //   363: aload 5
    //   365: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   368: aload_0
    //   369: getfield 17	com/starcor/core/parser/json/UserFeedbackInfoParse:userFeedbackInfo	Lcom/starcor/core/domain/GetUserFeedbackInfo;
    //   372: ldc 132
    //   374: putfield 89	com/starcor/core/domain/GetUserFeedbackInfo:msg	Ljava/lang/String;
    //   377: goto -253 -> 124
    //   380: astore 6
    //   382: aload 6
    //   384: invokevirtual 130	java/lang/Exception:printStackTrace	()V
    //   387: goto -65 -> 322
    //
    // Exception table:
    //   from	to	target	type
    //   14	72	327	java/lang/Exception
    //   9	14	345	java/lang/Exception
    //   329	342	345	java/lang/Exception
    //   363	377	345	java/lang/Exception
    //   382	387	345	java/lang/Exception
    //   72	124	361	java/lang/Exception
    //   124	227	380	java/lang/Exception
    //   227	281	380	java/lang/Exception
    //   281	322	380	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.UserFeedbackInfoParse
 * JD-Core Version:    0.6.2
 */