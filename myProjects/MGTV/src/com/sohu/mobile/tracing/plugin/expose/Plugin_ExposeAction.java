package com.sohu.mobile.tracing.plugin.expose;

public enum Plugin_ExposeAction
{
  static
  {
    EXPOSE_CLICK = new Plugin_ExposeAction("EXPOSE_CLICK", 1);
    UNKNOWN = new Plugin_ExposeAction("UNKNOWN", 2);
    Plugin_ExposeAction[] arrayOfPlugin_ExposeAction = new Plugin_ExposeAction[3];
    arrayOfPlugin_ExposeAction[0] = EXPOSE_SHOW;
    arrayOfPlugin_ExposeAction[1] = EXPOSE_CLICK;
    arrayOfPlugin_ExposeAction[2] = UNKNOWN;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction
 * JD-Core Version:    0.6.2
 */