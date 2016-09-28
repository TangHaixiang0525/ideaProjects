package com.starcor.report;

import com.starcor.core.utils.Logger;
import java.util.LinkedList;

public class LimitedLinkedList<E> extends LinkedList<E>
{
  public boolean add(E paramE)
  {
    if (paramE == null);
    while ((!isEmpty()) && (paramE.toString().equals(getLast().toString())))
      return false;
    return super.add(paramE);
  }

  public String getLastAt(int paramInt)
  {
    if (isEmpty())
      return "";
    if (paramInt > size())
      paramInt = size();
    if (paramInt < 0)
      paramInt = 1;
    Object localObject = get(size() - paramInt);
    if (localObject != null)
      return localObject.toString();
    return "";
  }

  public void printList()
  {
    String str = "";
    int i = size();
    for (int j = 0; j < i; j++)
      str = str + "-->" + get(j);
    Logger.i("printList = " + str);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.LimitedLinkedList
 * JD-Core Version:    0.6.2
 */