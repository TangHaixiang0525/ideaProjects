package com.starcor.core.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TerminalRealtimeParamList
{
  private static final String GROUP_TERMINAL = "terminal";
  private static final String KEY_INTERACTIVE_HISTORY_URL = "terminal_interact_list_url";
  public List<TerminalRealtimeParam> terminalRealtimeParams = new ArrayList();

  public void addParam(TerminalRealtimeParam paramTerminalRealtimeParam)
  {
    if (this.terminalRealtimeParams == null)
      this.terminalRealtimeParams = new ArrayList();
    this.terminalRealtimeParams.add(paramTerminalRealtimeParam);
  }

  public TerminalRealtimeParam getInteractiveMenuParam()
  {
    if (this.terminalRealtimeParams != null)
    {
      Iterator localIterator = this.terminalRealtimeParams.iterator();
      while (localIterator.hasNext())
      {
        TerminalRealtimeParam localTerminalRealtimeParam = (TerminalRealtimeParam)localIterator.next();
        if ((localTerminalRealtimeParam != null) && ("terminal_interact_list_url".equals(localTerminalRealtimeParam.key)) && ("terminal".equals(localTerminalRealtimeParam.group)))
          return localTerminalRealtimeParam;
      }
    }
    return null;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("TerminalRealtimeParamList [ ");
    if (this.terminalRealtimeParams != null)
    {
      Iterator localIterator = this.terminalRealtimeParams.iterator();
      while (localIterator.hasNext())
      {
        TerminalRealtimeParam localTerminalRealtimeParam = (TerminalRealtimeParam)localIterator.next();
        if (localTerminalRealtimeParam != null)
          localStringBuffer.append(localTerminalRealtimeParam.toString() + ", ");
      }
    }
    localStringBuffer.append(" ]");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.TerminalRealtimeParamList
 * JD-Core Version:    0.6.2
 */