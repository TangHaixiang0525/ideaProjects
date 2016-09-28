package com.caverock.androidsvg;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.xml.sax.SAXException;

public class CSSParser
{
  private static final String CLASS = "class";
  private static final String ID = "id";
  private static final String TAG = "AndroidSVG CSSParser";
  private boolean inMediaRule = false;
  private MediaType rendererMediaType = null;

  public CSSParser(MediaType paramMediaType)
  {
    this.rendererMediaType = paramMediaType;
  }

  private static int getChildPosition(List<SVG.SvgContainer> paramList, int paramInt, SVG.SvgElementBase paramSvgElementBase)
  {
    if (paramInt < 0)
    {
      i = -1;
      return i;
    }
    if (paramList.get(paramInt) != paramSvgElementBase.parent)
      return -1;
    int i = 0;
    Iterator localIterator = paramSvgElementBase.parent.getChildren().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        break label72;
      if ((SVG.SvgObject)localIterator.next() == paramSvgElementBase)
        break;
      i++;
    }
    label72: return -1;
  }

  public static boolean mediaMatches(String paramString, MediaType paramMediaType)
    throws SAXException
  {
    CSSTextScanner localCSSTextScanner = new CSSTextScanner(paramString);
    localCSSTextScanner.skipWhitespace();
    List localList = parseMediaList(localCSSTextScanner);
    if (!localCSSTextScanner.empty())
      throw new SAXException("Invalid @media type list");
    return mediaMatches(localList, paramMediaType);
  }

  private static boolean mediaMatches(List<MediaType> paramList, MediaType paramMediaType)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      MediaType localMediaType = (MediaType)localIterator.next();
      if ((localMediaType == MediaType.all) || (localMediaType == paramMediaType))
        return true;
    }
    return false;
  }

  private void parseAtRule(Ruleset paramRuleset, CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    String str = paramCSSTextScanner.nextIdentifier();
    paramCSSTextScanner.skipWhitespace();
    if (str == null)
      throw new SAXException("Invalid '@' rule in <style> element");
    if ((!this.inMediaRule) && (str.equals("media")))
    {
      List localList = parseMediaList(paramCSSTextScanner);
      if (!paramCSSTextScanner.consume('{'))
        throw new SAXException("Invalid @media rule: missing rule set");
      paramCSSTextScanner.skipWhitespace();
      if (mediaMatches(localList, this.rendererMediaType))
      {
        this.inMediaRule = true;
        paramRuleset.addAll(parseRuleset(paramCSSTextScanner));
        this.inMediaRule = false;
      }
    }
    while (!paramCSSTextScanner.consume('}'))
    {
      throw new SAXException("Invalid @media rule: expected '}' at end of rule set");
      parseRuleset(paramCSSTextScanner);
      continue;
      warn("Ignoring @%s rule", new Object[] { str });
      skipAtRule(paramCSSTextScanner);
    }
    paramCSSTextScanner.skipWhitespace();
  }

  protected static List<String> parseClassAttribute(String paramString)
    throws SAXException
  {
    CSSTextScanner localCSSTextScanner = new CSSTextScanner(paramString);
    ArrayList localArrayList = null;
    while (!localCSSTextScanner.empty())
    {
      String str = localCSSTextScanner.nextIdentifier();
      if (str == null)
        throw new SAXException("Invalid value for \"class\" attribute: " + paramString);
      if (localArrayList == null)
        localArrayList = new ArrayList();
      localArrayList.add(str);
      localCSSTextScanner.skipWhitespace();
    }
    return localArrayList;
  }

  private SVG.Style parseDeclarations(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    SVG.Style localStyle = new SVG.Style();
    String str1 = paramCSSTextScanner.nextIdentifier();
    paramCSSTextScanner.skipWhitespace();
    if (!paramCSSTextScanner.consume(':'));
    while (true)
    {
      throw new SAXException("Malformed rule set in <style> element");
      paramCSSTextScanner.skipWhitespace();
      String str2 = paramCSSTextScanner.nextPropertyValue();
      if (str2 != null)
      {
        paramCSSTextScanner.skipWhitespace();
        if (paramCSSTextScanner.consume('!'))
        {
          paramCSSTextScanner.skipWhitespace();
          if (!paramCSSTextScanner.consume("important"))
            throw new SAXException("Malformed rule set in <style> element: found unexpected '!'");
          paramCSSTextScanner.skipWhitespace();
        }
        paramCSSTextScanner.consume(';');
        SVGParser.processStyleProperty(localStyle, str1, str2);
        paramCSSTextScanner.skipWhitespace();
        if (paramCSSTextScanner.consume('}'))
          return localStyle;
        if (!paramCSSTextScanner.empty())
          break;
      }
    }
  }

  private static List<MediaType> parseMediaList(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    ArrayList localArrayList = new ArrayList();
    while (true)
    {
      String str;
      if (!paramCSSTextScanner.empty())
        str = paramCSSTextScanner.nextToken(',');
      try
      {
        localArrayList.add(MediaType.valueOf(str));
        if (!paramCSSTextScanner.skipCommaWhitespace())
          return localArrayList;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
      }
    }
    throw new SAXException("Invalid @media type list");
  }

  private boolean parseRule(Ruleset paramRuleset, CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    List localList = parseSelectorGroup(paramCSSTextScanner);
    if ((localList != null) && (!localList.isEmpty()))
    {
      if (!paramCSSTextScanner.consume('{'))
        throw new SAXException("Malformed rule block in <style> element: missing '{'");
      paramCSSTextScanner.skipWhitespace();
      SVG.Style localStyle = parseDeclarations(paramCSSTextScanner);
      paramCSSTextScanner.skipWhitespace();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
        paramRuleset.add(new Rule((Selector)localIterator.next(), localStyle));
      return true;
    }
    return false;
  }

  private Ruleset parseRuleset(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    Ruleset localRuleset = new Ruleset();
    do
      while (true)
      {
        if (paramCSSTextScanner.empty())
          break label60;
        if ((!paramCSSTextScanner.consume("<!--")) && (!paramCSSTextScanner.consume("-->")))
        {
          if (!paramCSSTextScanner.consume('@'))
            break;
          parseAtRule(localRuleset, paramCSSTextScanner);
        }
      }
    while (parseRule(localRuleset, paramCSSTextScanner));
    label60: return localRuleset;
  }

  private List<Selector> parseSelectorGroup(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    Object localObject;
    if (paramCSSTextScanner.empty())
      localObject = null;
    Selector localSelector;
    do
    {
      return localObject;
      localObject = new ArrayList(1);
      localSelector = new Selector();
      while ((!paramCSSTextScanner.empty()) && (paramCSSTextScanner.nextSimpleSelector(localSelector)))
        if (paramCSSTextScanner.skipCommaWhitespace())
        {
          ((ArrayList)localObject).add(localSelector);
          localSelector = new Selector();
        }
    }
    while (localSelector.isEmpty());
    ((ArrayList)localObject).add(localSelector);
    return localObject;
  }

  private static boolean ruleMatch(Selector paramSelector, int paramInt1, List<SVG.SvgContainer> paramList, int paramInt2, SVG.SvgElementBase paramSvgElementBase)
  {
    SimpleSelector localSimpleSelector = paramSelector.get(paramInt1);
    if (!selectorMatch(localSimpleSelector, paramList, paramInt2, paramSvgElementBase))
      break label38;
    label38: int i;
    do
    {
      do
      {
        return false;
        if (localSimpleSelector.combinator != Combinator.DESCENDANT)
          break;
        if (paramInt1 == 0)
        {
          return true;
          paramInt2--;
        }
      }
      while (paramInt2 < 0);
      if (!ruleMatchOnAncestors(paramSelector, paramInt1 - 1, paramList, paramInt2))
        break;
      return true;
      if (localSimpleSelector.combinator == Combinator.CHILD)
        return ruleMatchOnAncestors(paramSelector, paramInt1 - 1, paramList, paramInt2);
      i = getChildPosition(paramList, paramInt2, paramSvgElementBase);
    }
    while (i <= 0);
    SVG.SvgElementBase localSvgElementBase = (SVG.SvgElementBase)paramSvgElementBase.parent.getChildren().get(i - 1);
    return ruleMatch(paramSelector, paramInt1 - 1, paramList, paramInt2, localSvgElementBase);
  }

  protected static boolean ruleMatch(Selector paramSelector, SVG.SvgElementBase paramSvgElementBase)
  {
    ArrayList localArrayList = new ArrayList();
    for (SVG.SvgContainer localSvgContainer = paramSvgElementBase.parent; localSvgContainer != null; localSvgContainer = ((SVG.SvgObject)localSvgContainer).parent)
      localArrayList.add(0, localSvgContainer);
    int i = -1 + localArrayList.size();
    if (paramSelector.size() == 1)
      return selectorMatch(paramSelector.get(0), localArrayList, i, paramSvgElementBase);
    return ruleMatch(paramSelector, -1 + paramSelector.size(), localArrayList, i, paramSvgElementBase);
  }

  private static boolean ruleMatchOnAncestors(Selector paramSelector, int paramInt1, List<SVG.SvgContainer> paramList, int paramInt2)
  {
    SimpleSelector localSimpleSelector = paramSelector.get(paramInt1);
    SVG.SvgElementBase localSvgElementBase1 = (SVG.SvgElementBase)paramList.get(paramInt2);
    if (!selectorMatch(localSimpleSelector, paramList, paramInt2, localSvgElementBase1))
      break label50;
    label50: int i;
    do
    {
      do
      {
        return false;
        if (localSimpleSelector.combinator != Combinator.DESCENDANT)
          break;
        if (paramInt1 == 0)
          return true;
      }
      while (paramInt2 <= 0);
      int j = paramInt1 - 1;
      paramInt2--;
      if (!ruleMatchOnAncestors(paramSelector, j, paramList, paramInt2))
        break;
      return true;
      if (localSimpleSelector.combinator == Combinator.CHILD)
        return ruleMatchOnAncestors(paramSelector, paramInt1 - 1, paramList, paramInt2 - 1);
      i = getChildPosition(paramList, paramInt2, localSvgElementBase1);
    }
    while (i <= 0);
    SVG.SvgElementBase localSvgElementBase2 = (SVG.SvgElementBase)localSvgElementBase1.parent.getChildren().get(i - 1);
    return ruleMatch(paramSelector, paramInt1 - 1, paramList, paramInt2, localSvgElementBase2);
  }

  private static boolean selectorMatch(SimpleSelector paramSimpleSelector, List<SVG.SvgContainer> paramList, int paramInt, SVG.SvgElementBase paramSvgElementBase)
  {
    if (paramSimpleSelector.tag != null)
      if (paramSimpleSelector.tag.equalsIgnoreCase("G"))
      {
        if ((paramSvgElementBase instanceof SVG.Group));
      }
      else
        while (!paramSimpleSelector.tag.equals(paramSvgElementBase.getClass().getSimpleName().toLowerCase(Locale.US)))
          return false;
    if (paramSimpleSelector.attribs != null)
    {
      Iterator localIterator2 = paramSimpleSelector.attribs.iterator();
      while (true)
        if (localIterator2.hasNext())
        {
          Attrib localAttrib = (Attrib)localIterator2.next();
          if (localAttrib.name == "id")
          {
            if (!localAttrib.value.equals(paramSvgElementBase.id))
              return false;
          }
          else
          {
            if ((localAttrib.name != "class") || (paramSvgElementBase.classNames == null))
              break;
            if (!paramSvgElementBase.classNames.contains(localAttrib.value))
              return false;
          }
        }
    }
    if (paramSimpleSelector.pseudos != null)
    {
      Iterator localIterator1 = paramSimpleSelector.pseudos.iterator();
      while (true)
        if (localIterator1.hasNext())
        {
          if (!((String)localIterator1.next()).equals("first-child"))
            break;
          if (getChildPosition(paramList, paramInt, paramSvgElementBase) != 0)
            return false;
        }
    }
    return true;
  }

  private void skipAtRule(CSSTextScanner paramCSSTextScanner)
  {
    int i = 0;
    do
    {
      int j;
      do
        while (true)
        {
          if (!paramCSSTextScanner.empty())
          {
            j = paramCSSTextScanner.nextChar().intValue();
            if ((j != 59) || (i != 0));
          }
          else
          {
            return;
          }
          if (j != 123)
            break;
          i++;
        }
      while ((j != 125) || (i <= 0));
      i--;
    }
    while (i != 0);
  }

  private static void warn(String paramString, Object[] paramArrayOfObject)
  {
    Log.w("AndroidSVG CSSParser", String.format(paramString, paramArrayOfObject));
  }

  public Ruleset parse(String paramString)
    throws SAXException
  {
    CSSTextScanner localCSSTextScanner = new CSSTextScanner(paramString);
    localCSSTextScanner.skipWhitespace();
    return parseRuleset(localCSSTextScanner);
  }

  public static class Attrib
  {
    public String name = null;
    public CSSParser.AttribOp operation;
    public String value = null;

    public Attrib(String paramString1, CSSParser.AttribOp paramAttribOp, String paramString2)
    {
      this.name = paramString1;
      this.operation = paramAttribOp;
      this.value = paramString2;
    }
  }

  private static enum AttribOp
  {
    static
    {
      EQUALS = new AttribOp("EQUALS", 1);
      INCLUDES = new AttribOp("INCLUDES", 2);
      DASHMATCH = new AttribOp("DASHMATCH", 3);
      AttribOp[] arrayOfAttribOp = new AttribOp[4];
      arrayOfAttribOp[0] = EXISTS;
      arrayOfAttribOp[1] = EQUALS;
      arrayOfAttribOp[2] = INCLUDES;
      arrayOfAttribOp[3] = DASHMATCH;
    }
  }

  private static class CSSTextScanner extends SVGParser.TextScanner
  {
    public CSSTextScanner(String paramString)
    {
      super();
    }

    private String nextAttribValue()
    {
      String str;
      if (empty())
        str = null;
      do
      {
        return str;
        str = nextQuotedString();
      }
      while (str != null);
      return nextIdentifier();
    }

    private int scanForIdentifier()
    {
      if (empty())
        return this.position;
      int i = this.position;
      int j = this.position;
      int k = this.input.charAt(this.position);
      if (k == 45)
        k = advanceChar();
      if (((k >= 65) && (k <= 90)) || ((k >= 97) && (k <= 122)) || (k == 95))
      {
        for (int m = advanceChar(); ((m >= 65) && (m <= 90)) || ((m >= 97) && (m <= 122)) || ((m >= 48) && (m <= 57)) || (m == 45) || (m == 95); m = advanceChar());
        j = this.position;
      }
      this.position = i;
      return j;
    }

    public String nextIdentifier()
    {
      int i = scanForIdentifier();
      if (i == this.position)
        return null;
      String str = this.input.substring(this.position, i);
      this.position = i;
      return str;
    }

    public String nextPropertyValue()
    {
      if (empty())
        return null;
      int i = this.position;
      int j = this.position;
      for (int k = this.input.charAt(this.position); (k != -1) && (k != 59) && (k != 125) && (k != 33) && (!isEOL(k)); k = advanceChar())
        if (!isWhitespace(k))
          j = 1 + this.position;
      if (this.position > i)
        return this.input.substring(i, j);
      this.position = i;
      return null;
    }

    public boolean nextSimpleSelector(CSSParser.Selector paramSelector)
      throws SAXException
    {
      if (empty())
        return false;
      int i = this.position;
      boolean bool1 = paramSelector.isEmpty();
      CSSParser.Combinator localCombinator = null;
      CSSParser.SimpleSelector localSimpleSelector;
      if (!bool1)
      {
        if (consume('>'))
        {
          localCombinator = CSSParser.Combinator.CHILD;
          skipWhitespace();
        }
      }
      else
      {
        if (!consume('*'))
          break label147;
        localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
      }
      while (true)
        if (!empty())
        {
          if (consume('.'))
          {
            if (localSimpleSelector == null)
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
            String str4 = nextIdentifier();
            if (str4 == null)
            {
              throw new SAXException("Invalid \".class\" selector in <style> element");
              boolean bool3 = consume('+');
              localCombinator = null;
              if (!bool3)
                break;
              localCombinator = CSSParser.Combinator.FOLLOWS;
              skipWhitespace();
              break;
              label147: String str5 = nextIdentifier();
              localSimpleSelector = null;
              if (str5 == null)
                continue;
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, str5);
              paramSelector.addedElement();
              continue;
            }
            localSimpleSelector.addAttrib("class", CSSParser.AttribOp.EQUALS, str4);
            paramSelector.addedAttributeOrPseudo();
            continue;
          }
          if (consume('#'))
          {
            if (localSimpleSelector == null)
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
            String str3 = nextIdentifier();
            if (str3 == null)
              throw new SAXException("Invalid \"#id\" selector in <style> element");
            localSimpleSelector.addAttrib("id", CSSParser.AttribOp.EQUALS, str3);
            paramSelector.addedIdAttribute();
          }
          if (localSimpleSelector != null)
            break label281;
        }
      while (true)
      {
        if (localSimpleSelector == null)
          break label549;
        paramSelector.add(localSimpleSelector);
        return true;
        label281: if (consume('['))
        {
          skipWhitespace();
          String str1 = nextIdentifier();
          if (str1 == null)
            throw new SAXException("Invalid attribute selector in <style> element");
          skipWhitespace();
          CSSParser.AttribOp localAttribOp;
          if (consume('='))
            localAttribOp = CSSParser.AttribOp.EQUALS;
          String str2;
          while (true)
          {
            str2 = null;
            if (localAttribOp == null)
              break label411;
            skipWhitespace();
            str2 = nextAttribValue();
            if (str2 != null)
              break;
            throw new SAXException("Invalid attribute selector in <style> element");
            if (consume("~="))
            {
              localAttribOp = CSSParser.AttribOp.INCLUDES;
            }
            else
            {
              boolean bool2 = consume("|=");
              localAttribOp = null;
              if (bool2)
                localAttribOp = CSSParser.AttribOp.DASHMATCH;
            }
          }
          skipWhitespace();
          label411: if (!consume(']'))
            throw new SAXException("Invalid attribute selector in <style> element");
          if (localAttribOp == null)
            localAttribOp = CSSParser.AttribOp.EXISTS;
          localSimpleSelector.addAttrib(str1, localAttribOp, str2);
          paramSelector.addedAttributeOrPseudo();
          break;
        }
        if (consume(':'))
        {
          int j = this.position;
          if (nextIdentifier() != null)
            if (consume('('))
            {
              skipWhitespace();
              if (nextIdentifier() != null)
              {
                skipWhitespace();
                if (!consume(')'))
                  this.position = (j - 1);
              }
            }
            else
            {
              localSimpleSelector.addPseudo(this.input.substring(j, this.position));
              paramSelector.addedAttributeOrPseudo();
            }
        }
      }
      label549: this.position = i;
      return false;
    }
  }

  private static enum Combinator
  {
    static
    {
      CHILD = new Combinator("CHILD", 1);
      FOLLOWS = new Combinator("FOLLOWS", 2);
      Combinator[] arrayOfCombinator = new Combinator[3];
      arrayOfCombinator[0] = DESCENDANT;
      arrayOfCombinator[1] = CHILD;
      arrayOfCombinator[2] = FOLLOWS;
    }
  }

  public static enum MediaType
  {
    static
    {
      MediaType[] arrayOfMediaType = new MediaType[10];
      arrayOfMediaType[0] = all;
      arrayOfMediaType[1] = aural;
      arrayOfMediaType[2] = braille;
      arrayOfMediaType[3] = embossed;
      arrayOfMediaType[4] = handheld;
      arrayOfMediaType[5] = print;
      arrayOfMediaType[6] = projection;
      arrayOfMediaType[7] = screen;
      arrayOfMediaType[8] = tty;
      arrayOfMediaType[9] = tv;
    }
  }

  public static class Rule
  {
    public CSSParser.Selector selector = null;
    public SVG.Style style = null;

    public Rule(CSSParser.Selector paramSelector, SVG.Style paramStyle)
    {
      this.selector = paramSelector;
      this.style = paramStyle;
    }

    public String toString()
    {
      return this.selector + " {}";
    }
  }

  public static class Ruleset
  {
    private List<CSSParser.Rule> rules = null;

    public void add(CSSParser.Rule paramRule)
    {
      if (this.rules == null)
        this.rules = new ArrayList();
      for (int i = 0; i < this.rules.size(); i++)
        if (((CSSParser.Rule)this.rules.get(i)).selector.specificity > paramRule.selector.specificity)
        {
          this.rules.add(i, paramRule);
          return;
        }
      this.rules.add(paramRule);
    }

    public void addAll(Ruleset paramRuleset)
    {
      if (paramRuleset.rules == null);
      while (true)
      {
        return;
        if (this.rules == null)
          this.rules = new ArrayList(paramRuleset.rules.size());
        Iterator localIterator = paramRuleset.rules.iterator();
        while (localIterator.hasNext())
        {
          CSSParser.Rule localRule = (CSSParser.Rule)localIterator.next();
          this.rules.add(localRule);
        }
      }
    }

    public List<CSSParser.Rule> getRules()
    {
      return this.rules;
    }

    public boolean isEmpty()
    {
      return (this.rules == null) || (this.rules.isEmpty());
    }

    public String toString()
    {
      if (this.rules == null)
        return "";
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.rules.iterator();
      while (localIterator.hasNext())
        localStringBuilder.append(((CSSParser.Rule)localIterator.next()).toString()).append('\n');
      return localStringBuilder.toString();
    }
  }

  public static class Selector
  {
    public List<CSSParser.SimpleSelector> selector = null;
    public int specificity = 0;

    public void add(CSSParser.SimpleSelector paramSimpleSelector)
    {
      if (this.selector == null)
        this.selector = new ArrayList();
      this.selector.add(paramSimpleSelector);
    }

    public void addedAttributeOrPseudo()
    {
      this.specificity = (100 + this.specificity);
    }

    public void addedElement()
    {
      this.specificity = (1 + this.specificity);
    }

    public void addedIdAttribute()
    {
      this.specificity = (10000 + this.specificity);
    }

    public CSSParser.SimpleSelector get(int paramInt)
    {
      return (CSSParser.SimpleSelector)this.selector.get(paramInt);
    }

    public boolean isEmpty()
    {
      if (this.selector == null)
        return true;
      return this.selector.isEmpty();
    }

    public int size()
    {
      if (this.selector == null)
        return 0;
      return this.selector.size();
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.selector.iterator();
      while (localIterator.hasNext())
        localStringBuilder.append((CSSParser.SimpleSelector)localIterator.next()).append(' ');
      return '(' + this.specificity + ')';
    }
  }

  private static class SimpleSelector
  {
    public List<CSSParser.Attrib> attribs = null;
    public CSSParser.Combinator combinator = null;
    public List<String> pseudos = null;
    public String tag = null;

    public SimpleSelector(CSSParser.Combinator paramCombinator, String paramString)
    {
      if (paramCombinator != null);
      while (true)
      {
        this.combinator = paramCombinator;
        this.tag = paramString;
        return;
        paramCombinator = CSSParser.Combinator.DESCENDANT;
      }
    }

    public void addAttrib(String paramString1, CSSParser.AttribOp paramAttribOp, String paramString2)
    {
      if (this.attribs == null)
        this.attribs = new ArrayList();
      this.attribs.add(new CSSParser.Attrib(paramString1, paramAttribOp, paramString2));
    }

    public void addPseudo(String paramString)
    {
      if (this.pseudos == null)
        this.pseudos = new ArrayList();
      this.pseudos.add(paramString);
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str1;
      label35: label59: CSSParser.Attrib localAttrib;
      if (this.combinator == CSSParser.Combinator.CHILD)
      {
        localStringBuilder.append("> ");
        if (this.tag != null)
          break label166;
        str1 = "*";
        localStringBuilder.append(str1);
        if (this.attribs == null)
          break label228;
        Iterator localIterator2 = this.attribs.iterator();
        if (!localIterator2.hasNext())
          break label228;
        localAttrib = (CSSParser.Attrib)localIterator2.next();
        localStringBuilder.append('[').append(localAttrib.name);
        switch (CSSParser.1.$SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp[localAttrib.operation.ordinal()])
        {
        default:
        case 1:
        case 2:
        case 3:
        }
      }
      while (true)
      {
        localStringBuilder.append(']');
        break label59;
        if (this.combinator != CSSParser.Combinator.FOLLOWS)
          break;
        localStringBuilder.append("+ ");
        break;
        label166: str1 = this.tag;
        break label35;
        localStringBuilder.append('=').append(localAttrib.value);
        continue;
        localStringBuilder.append("~=").append(localAttrib.value);
        continue;
        localStringBuilder.append("|=").append(localAttrib.value);
      }
      label228: if (this.pseudos != null)
      {
        Iterator localIterator1 = this.pseudos.iterator();
        while (localIterator1.hasNext())
        {
          String str2 = (String)localIterator1.next();
          localStringBuilder.append(':').append(str2);
        }
      }
      return localStringBuilder.toString();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.CSSParser
 * JD-Core Version:    0.6.2
 */