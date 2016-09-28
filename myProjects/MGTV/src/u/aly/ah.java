package u.aly;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ah extends av
  implements q
{
  public ah(String paramString, Map<String, Object> paramMap)
  {
    a(paramString);
    b(System.currentTimeMillis());
    if (paramMap.size() > 0)
      a(b(paramMap));
    if (this.d > 0);
    for (int i = this.d; ; i = 1)
    {
      a(i);
      return;
    }
  }

  private HashMap<String, bh> b(Map<String, Object> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    HashMap localHashMap = new HashMap();
    int i = 0;
    label208: 
    while ((i < 10) && (localIterator.hasNext()))
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      bh localbh = new bh();
      Object localObject = localEntry.getValue();
      if ((localObject instanceof String))
        localbh.b((String)localObject);
      while (true)
      {
        if (!localbh.k())
          break label208;
        localHashMap.put(localEntry.getKey(), localbh);
        i++;
        break;
        if ((localObject instanceof Long))
          localbh.b(((Long)localObject).longValue());
        else if ((localObject instanceof Integer))
          localbh.b(((Integer)localObject).longValue());
        else if ((localObject instanceof Float))
          localbh.b(((Float)localObject).longValue());
        else if ((localObject instanceof Double))
          localbh.b(((Double)localObject).longValue());
      }
    }
    return localHashMap;
  }

  public void a(bn parambn, String paramString)
  {
    bc localbc;
    if (parambn.s() > 0)
    {
      Iterator localIterator = parambn.u().iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localbc = (bc)localIterator.next();
      }
      while (!paramString.equals(localbc.c()));
    }
    while (true)
    {
      if (localbc == null)
      {
        localbc = new bc();
        localbc.a(paramString);
        parambn.a(localbc);
      }
      localbc.b(this);
      return;
      localbc = null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ah
 * JD-Core Version:    0.6.2
 */