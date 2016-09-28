package com.starcor.hunan.domain;

import com.starcor.core.utils.Logger;
import java.util.HashMap;

public class UploadCombineMediaDataManageFactory
{
  private static final String TAG = UploadCombineMediaDataManageFactory.class.getSimpleName();
  private static UploadCombineMediaDataManageFactory uploadCombineMediaDataManageFactory = null;
  private HashMap<LocalMediaDataManage.MediaDateType, MediaDataTypeInterface> mediaDataList = null;

  public static UploadCombineMediaDataManageFactory getInstance()
  {
    if (uploadCombineMediaDataManageFactory == null)
    {
      Logger.i(TAG, "uploadCombineMediaDataManageFactory first create");
      uploadCombineMediaDataManageFactory = new UploadCombineMediaDataManageFactory();
    }
    return uploadCombineMediaDataManageFactory;
  }

  // ERROR //
  private MediaDataTypeInterface getSpecialMediaDataTypeInstance(LocalMediaDataManage.MediaDateType paramMediaDateType)
  {
    // Byte code:
    //   0: getstatic 51	com/starcor/hunan/domain/UploadCombineMediaDataManageFactory$1:$SwitchMap$com$starcor$hunan$domain$LocalMediaDataManage$MediaDateType	[I
    //   3: aload_1
    //   4: invokevirtual 57	com/starcor/hunan/domain/LocalMediaDataManage$MediaDateType:ordinal	()I
    //   7: iaload
    //   8: tableswitch	default:+28 -> 36, 1:+30->38, 2:+62->70, 3:+82->90
    //   37: areturn
    //   38: ldc 59
    //   40: invokevirtual 63	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   43: checkcast 65	com/starcor/hunan/domain/MediaDataTypeInterface
    //   46: astore 10
    //   48: aload 10
    //   50: areturn
    //   51: astore 9
    //   53: aload 9
    //   55: invokevirtual 68	java/lang/InstantiationException:printStackTrace	()V
    //   58: aconst_null
    //   59: areturn
    //   60: astore 8
    //   62: aload 8
    //   64: invokevirtual 69	java/lang/IllegalAccessException:printStackTrace	()V
    //   67: goto -9 -> 58
    //   70: ldc 71
    //   72: invokevirtual 63	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   75: checkcast 65	com/starcor/hunan/domain/MediaDataTypeInterface
    //   78: astore 7
    //   80: aload 7
    //   82: areturn
    //   83: astore 6
    //   85: aload 6
    //   87: invokevirtual 68	java/lang/InstantiationException:printStackTrace	()V
    //   90: ldc 73
    //   92: invokevirtual 63	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   95: checkcast 65	com/starcor/hunan/domain/MediaDataTypeInterface
    //   98: astore 4
    //   100: aload 4
    //   102: areturn
    //   103: astore 5
    //   105: aload 5
    //   107: invokevirtual 69	java/lang/IllegalAccessException:printStackTrace	()V
    //   110: goto -20 -> 90
    //   113: astore_3
    //   114: aload_3
    //   115: invokevirtual 68	java/lang/InstantiationException:printStackTrace	()V
    //   118: goto -82 -> 36
    //   121: astore_2
    //   122: aload_2
    //   123: invokevirtual 69	java/lang/IllegalAccessException:printStackTrace	()V
    //   126: goto -90 -> 36
    //
    // Exception table:
    //   from	to	target	type
    //   38	48	51	java/lang/InstantiationException
    //   38	48	60	java/lang/IllegalAccessException
    //   70	80	83	java/lang/InstantiationException
    //   70	80	103	java/lang/IllegalAccessException
    //   90	100	113	java/lang/InstantiationException
    //   90	100	121	java/lang/IllegalAccessException
  }

  public HashMap<LocalMediaDataManage.MediaDateType, MediaDataTypeInterface> getMediaDataTypeListInstance()
    throws InstantiationException, IllegalAccessException, ClassNotFoundException
  {
    if (this.mediaDataList == null)
      this.mediaDataList = new HashMap();
    for (LocalMediaDataManage.MediaDateType localMediaDateType : LocalMediaDataManage.MediaDateType.values())
      this.mediaDataList.put(localMediaDateType, getSpecialMediaDataTypeInstance(localMediaDateType));
    return this.mediaDataList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.UploadCombineMediaDataManageFactory
 * JD-Core Version:    0.6.2
 */