package com.starcor.xul.Prop;

import android.text.TextUtils;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.Script.IScript;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulDataNode._Builder;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulSelect;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulAction extends XulProp
{
  private int _event;
  private IScript _script;
  private String _type;

  public XulAction()
  {
  }

  public XulAction(XulAction paramXulAction)
  {
    super(paramXulAction);
    this._event = paramXulAction._event;
    this._type = paramXulAction._type;
    this._script = paramXulAction._script;
  }

  public String getName()
  {
    if (this._nameId < 0)
      return XulPropNameCache.id2Name(this._event);
    return super.getName();
  }

  public int getNameId()
  {
    if (this._nameId < 0)
      return this._event;
    return this._nameId;
  }

  public IScript getScript()
  {
    return this._script;
  }

  public String getType()
  {
    return this._type;
  }

  public XulAction makeClone()
  {
    if (isBinding())
      this = new XulAction(this);
    return this;
  }

  public void setValue(Object paramObject)
  {
    if ((!TextUtils.isEmpty(this._type)) && (this._type.startsWith("script/")))
    {
      String str1 = this._type.substring(7);
      if (TextUtils.isEmpty(str1));
      IScriptContext localIScriptContext;
      do
      {
        return;
        localIScriptContext = XulManager.getScriptContext(str1);
      }
      while (localIScriptContext == null);
      String str2 = String.valueOf(paramObject).trim();
      IScript localIScript = localIScriptContext.getFunction(null, str2);
      if (localIScript == null)
      {
        this._script = localIScriptContext.compileFunction(str2, "", 0);
        return;
      }
      this._script = localIScript;
      return;
    }
    super.setValue(paramObject);
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulAction _action;
    XulFactory.ItemBuilder.FinalCallback<XulAction> _callback;
    StringBuilder _content;
    ArrayList<XulDataNode> _dataContent;
    XulFactory.ItemBuilder.FinalCallback<XulDataNode> _xulDataNodeCallback = new XulFactory.ItemBuilder.FinalCallback()
    {
      public void onFinal(XulDataNode paramAnonymousXulDataNode)
      {
        if (XulAction._Builder.this._dataContent == null)
          XulAction._Builder.this._dataContent = new ArrayList();
        XulAction._Builder.this._dataContent.add(paramAnonymousXulDataNode);
      }
    };

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulSelect paramXulSelect)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulSelect);
      return local_Builder;
    }

    public static _Builder create(XulView paramXulView)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulView);
      return local_Builder;
    }

    private void init(final XulSelect paramXulSelect)
    {
      this._action = new XulAction();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulAction paramAnonymousXulAction)
        {
          paramXulSelect.addProp(paramAnonymousXulAction);
        }
      };
      this._content = null;
      this._dataContent = null;
    }

    private void init(final XulView paramXulView)
    {
      this._action = new XulAction();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulAction paramAnonymousXulAction)
        {
          paramXulView.addInplaceProp(paramAnonymousXulAction);
        }
      };
      this._content = null;
      this._dataContent = null;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._action = null;
      _recycled_builder._content = null;
      _recycled_builder._dataContent = null;
    }

    public Object finalItem()
    {
      XulAction localXulAction = this._action;
      XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
      if (this._dataContent != null)
        localXulAction.setValue(this._dataContent);
      while (true)
      {
        recycle(this);
        localFinalCallback.onFinal(localXulAction);
        return localXulAction;
        if (this._content != null)
          localXulAction.setValue(this._content.toString());
      }
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._action._nameId = XulPropNameCache.name2Id(paramAttributes.getValue("name"));
      this._action._desc = paramAttributes.getValue("desc");
      XulAction.access$002(this._action, XulPropNameCache.name2Id(paramAttributes.getValue("event")));
      this._action._binding = XulUtils.getCachedString(paramAttributes.getValue("binding"));
      XulAction.access$102(this._action, XulUtils.getCachedString(paramAttributes.getValue("type")));
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      XulDataNode._Builder local_Builder = XulDataNode._Builder.create(paramString2, this._xulDataNodeCallback);
      local_Builder.initialize(paramString2, paramAttributes);
      return local_Builder;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      if (this._content == null)
        this._content = new StringBuilder();
      this._content.append(paramIPullParser.getText());
      return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulAction
 * JD-Core Version:    0.6.2
 */