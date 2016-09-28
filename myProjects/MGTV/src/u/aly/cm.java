package u.aly;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class cm
{
  public static cl a(Class<? extends cl> paramClass, int paramInt)
  {
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      Method localMethod = paramClass.getMethod("findByValue", arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      cl localcl = (cl)localMethod.invoke(null, arrayOfObject);
      return localcl;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.cm
 * JD-Core Version:    0.6.2
 */