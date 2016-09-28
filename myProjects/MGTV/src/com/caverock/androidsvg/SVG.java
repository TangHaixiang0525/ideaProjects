package com.caverock.androidsvg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public class SVG
{
  private static final int DEFAULT_PICTURE_HEIGHT = 512;
  private static final int DEFAULT_PICTURE_WIDTH = 512;
  protected static final long SPECIFIED_ALL = -1L;
  protected static final long SPECIFIED_CLIP = 1048576L;
  protected static final long SPECIFIED_CLIP_PATH = 268435456L;
  protected static final long SPECIFIED_CLIP_RULE = 536870912L;
  protected static final long SPECIFIED_COLOR = 4096L;
  protected static final long SPECIFIED_DIRECTION = 68719476736L;
  protected static final long SPECIFIED_DISPLAY = 16777216L;
  protected static final long SPECIFIED_FILL = 1L;
  protected static final long SPECIFIED_FILL_OPACITY = 4L;
  protected static final long SPECIFIED_FILL_RULE = 2L;
  protected static final long SPECIFIED_FONT_FAMILY = 8192L;
  protected static final long SPECIFIED_FONT_SIZE = 16384L;
  protected static final long SPECIFIED_FONT_STYLE = 65536L;
  protected static final long SPECIFIED_FONT_WEIGHT = 32768L;
  protected static final long SPECIFIED_MARKER_END = 8388608L;
  protected static final long SPECIFIED_MARKER_MID = 4194304L;
  protected static final long SPECIFIED_MARKER_START = 2097152L;
  protected static final long SPECIFIED_MASK = 1073741824L;
  protected static final long SPECIFIED_NON_INHERITING = 68133849088L;
  protected static final long SPECIFIED_OPACITY = 2048L;
  protected static final long SPECIFIED_OVERFLOW = 524288L;
  protected static final long SPECIFIED_SOLID_COLOR = 2147483648L;
  protected static final long SPECIFIED_SOLID_OPACITY = 4294967296L;
  protected static final long SPECIFIED_STOP_COLOR = 67108864L;
  protected static final long SPECIFIED_STOP_OPACITY = 134217728L;
  protected static final long SPECIFIED_STROKE = 8L;
  protected static final long SPECIFIED_STROKE_DASHARRAY = 512L;
  protected static final long SPECIFIED_STROKE_DASHOFFSET = 1024L;
  protected static final long SPECIFIED_STROKE_LINECAP = 64L;
  protected static final long SPECIFIED_STROKE_LINEJOIN = 128L;
  protected static final long SPECIFIED_STROKE_MITERLIMIT = 256L;
  protected static final long SPECIFIED_STROKE_OPACITY = 16L;
  protected static final long SPECIFIED_STROKE_WIDTH = 32L;
  protected static final long SPECIFIED_TEXT_ANCHOR = 262144L;
  protected static final long SPECIFIED_TEXT_DECORATION = 131072L;
  protected static final long SPECIFIED_VECTOR_EFFECT = 34359738368L;
  protected static final long SPECIFIED_VIEWPORT_FILL = 8589934592L;
  protected static final long SPECIFIED_VIEWPORT_FILL_OPACITY = 17179869184L;
  protected static final long SPECIFIED_VISIBILITY = 33554432L;
  private static final double SQRT2 = 1.414213562373095D;
  protected static final String SUPPORTED_SVG_VERSION = "1.2";
  private static final String TAG = "AndroidSVG";
  private static final String VERSION = "1.2.2-beta-2";
  private CSSParser.Ruleset cssRules = new CSSParser.Ruleset();
  private String desc = "";
  private SVGExternalFileResolver fileResolver = null;
  Map<String, SvgElementBase> idToElementMap = new HashMap();
  private float renderDPI = 96.0F;
  private Svg rootElement = null;
  private String title = "";

  private Box getDocumentDimensions(float paramFloat)
  {
    Length localLength1 = this.rootElement.width;
    Length localLength2 = this.rootElement.height;
    if ((localLength1 == null) || (localLength1.isZero()) || (localLength1.unit == Unit.percent) || (localLength1.unit == Unit.em) || (localLength1.unit == Unit.ex))
      return new Box(-1.0F, -1.0F, -1.0F, -1.0F);
    float f1 = localLength1.floatValue(paramFloat);
    float f2;
    if (localLength2 != null)
    {
      if ((localLength2.isZero()) || (localLength2.unit == Unit.percent) || (localLength2.unit == Unit.em) || (localLength2.unit == Unit.ex))
        return new Box(-1.0F, -1.0F, -1.0F, -1.0F);
      f2 = localLength2.floatValue(paramFloat);
    }
    while (true)
    {
      return new Box(0.0F, 0.0F, f1, f2);
      if (this.rootElement.viewBox != null)
        f2 = f1 * this.rootElement.viewBox.height / this.rootElement.viewBox.width;
      else
        f2 = f1;
    }
  }

  private SvgElementBase getElementById(SvgContainer paramSvgContainer, String paramString)
  {
    SvgElementBase localSvgElementBase1 = (SvgElementBase)paramSvgContainer;
    if (paramString.equals(localSvgElementBase1.id))
      return localSvgElementBase1;
    Iterator localIterator = paramSvgContainer.getChildren().iterator();
    while (localIterator.hasNext())
    {
      SvgObject localSvgObject = (SvgObject)localIterator.next();
      if ((localSvgObject instanceof SvgElementBase))
      {
        SvgElementBase localSvgElementBase2 = (SvgElementBase)localSvgObject;
        if (paramString.equals(localSvgElementBase2.id))
          return localSvgElementBase2;
        if ((localSvgObject instanceof SvgContainer))
        {
          SvgElementBase localSvgElementBase3 = getElementById((SvgContainer)localSvgObject, paramString);
          if (localSvgElementBase3 != null)
            return localSvgElementBase3;
        }
      }
    }
    return null;
  }

  private List<SvgObject> getElementsByTagName(SvgContainer paramSvgContainer, Class paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramSvgContainer.getClass() == paramClass)
      localArrayList.add((SvgObject)paramSvgContainer);
    Iterator localIterator = paramSvgContainer.getChildren().iterator();
    while (localIterator.hasNext())
    {
      SvgObject localSvgObject = (SvgObject)localIterator.next();
      if (localSvgObject.getClass() == paramClass)
        localArrayList.add(localSvgObject);
      if ((localSvgObject instanceof SvgContainer))
        getElementsByTagName((SvgContainer)localSvgObject, paramClass);
    }
    return localArrayList;
  }

  // ERROR //
  public static SVG getFromAsset(android.content.res.AssetManager paramAssetManager, String paramString)
    throws SVGParseException, java.io.IOException
  {
    // Byte code:
    //   0: new 290	com/caverock/androidsvg/SVGParser
    //   3: dup
    //   4: invokespecial 291	com/caverock/androidsvg/SVGParser:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: aload_1
    //   10: invokevirtual 297	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   13: astore_3
    //   14: aload_2
    //   15: aload_3
    //   16: invokevirtual 301	com/caverock/androidsvg/SVGParser:parse	(Ljava/io/InputStream;)Lcom/caverock/androidsvg/SVG;
    //   19: astore 6
    //   21: aload_3
    //   22: invokevirtual 306	java/io/InputStream:close	()V
    //   25: aload 6
    //   27: areturn
    //   28: astore 4
    //   30: aload_3
    //   31: invokevirtual 306	java/io/InputStream:close	()V
    //   34: aload 4
    //   36: athrow
    //   37: astore 7
    //   39: aload 6
    //   41: areturn
    //   42: astore 5
    //   44: goto -10 -> 34
    //
    // Exception table:
    //   from	to	target	type
    //   14	21	28	finally
    //   21	25	37	java/io/IOException
    //   30	34	42	java/io/IOException
  }

  public static SVG getFromInputStream(InputStream paramInputStream)
    throws SVGParseException
  {
    return new SVGParser().parse(paramInputStream);
  }

  public static SVG getFromResource(Context paramContext, int paramInt)
    throws SVGParseException
  {
    return getFromResource(paramContext.getResources(), paramInt);
  }

  // ERROR //
  public static SVG getFromResource(android.content.res.Resources paramResources, int paramInt)
    throws SVGParseException
  {
    // Byte code:
    //   0: new 290	com/caverock/androidsvg/SVGParser
    //   3: dup
    //   4: invokespecial 291	com/caverock/androidsvg/SVGParser:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: iload_1
    //   10: invokevirtual 324	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   13: astore_3
    //   14: aload_2
    //   15: aload_3
    //   16: invokevirtual 301	com/caverock/androidsvg/SVGParser:parse	(Ljava/io/InputStream;)Lcom/caverock/androidsvg/SVG;
    //   19: astore 6
    //   21: aload_3
    //   22: invokevirtual 306	java/io/InputStream:close	()V
    //   25: aload 6
    //   27: areturn
    //   28: astore 4
    //   30: aload_3
    //   31: invokevirtual 306	java/io/InputStream:close	()V
    //   34: aload 4
    //   36: athrow
    //   37: astore 7
    //   39: aload 6
    //   41: areturn
    //   42: astore 5
    //   44: goto -10 -> 34
    //
    // Exception table:
    //   from	to	target	type
    //   14	21	28	finally
    //   21	25	37	java/io/IOException
    //   30	34	42	java/io/IOException
  }

  public static SVG getFromString(String paramString)
    throws SVGParseException
  {
    return new SVGParser().parse(new ByteArrayInputStream(paramString.getBytes()));
  }

  public static String getVersion()
  {
    return "1.2.2-beta-2";
  }

  protected void addCSSRules(CSSParser.Ruleset paramRuleset)
  {
    this.cssRules.addAll(paramRuleset);
  }

  protected List<CSSParser.Rule> getCSSRules()
  {
    return this.cssRules.getRules();
  }

  public float getDocumentAspectRatio()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    Length localLength1 = this.rootElement.width;
    Length localLength2 = this.rootElement.height;
    if ((localLength1 != null) && (localLength2 != null) && (localLength1.unit != Unit.percent) && (localLength2.unit != Unit.percent))
      if ((!localLength1.isZero()) && (!localLength2.isZero()));
    while ((this.rootElement.viewBox == null) || (this.rootElement.viewBox.width == 0.0F) || (this.rootElement.viewBox.height == 0.0F))
    {
      return -1.0F;
      return localLength1.floatValue(this.renderDPI) / localLength2.floatValue(this.renderDPI);
    }
    return this.rootElement.viewBox.width / this.rootElement.viewBox.height;
  }

  public String getDocumentDescription()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    return this.desc;
  }

  public float getDocumentHeight()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    return getDocumentDimensions(this.renderDPI).height;
  }

  public PreserveAspectRatio getDocumentPreserveAspectRatio()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    if (this.rootElement.preserveAspectRatio == null)
      return null;
    return this.rootElement.preserveAspectRatio;
  }

  public String getDocumentSVGVersion()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    return this.rootElement.version;
  }

  public String getDocumentTitle()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    return this.title;
  }

  public RectF getDocumentViewBox()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    if (this.rootElement.viewBox == null)
      return null;
    return this.rootElement.viewBox.toRectF();
  }

  public float getDocumentWidth()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    return getDocumentDimensions(this.renderDPI).width;
  }

  protected SvgObject getElementById(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return null;
    if (paramString.equals(this.rootElement.id))
      return this.rootElement;
    if (this.idToElementMap.containsKey(paramString))
      return (SvgObject)this.idToElementMap.get(paramString);
    SvgElementBase localSvgElementBase = getElementById(this.rootElement, paramString);
    this.idToElementMap.put(paramString, localSvgElementBase);
    return localSvgElementBase;
  }

  protected List<SvgObject> getElementsByTagName(Class paramClass)
  {
    return getElementsByTagName(this.rootElement, paramClass);
  }

  protected SVGExternalFileResolver getFileResolver()
  {
    return this.fileResolver;
  }

  public float getRenderDPI()
  {
    return this.renderDPI;
  }

  protected Svg getRootElement()
  {
    return this.rootElement;
  }

  public Set<String> getViewList()
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    List localList = getElementsByTagName(View.class);
    HashSet localHashSet = new HashSet(localList.size());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      if (localView.id != null)
        localHashSet.add(localView.id);
      else
        Log.w("AndroidSVG", "getViewList(): found a <view> without an id attribute");
    }
    return localHashSet;
  }

  protected boolean hasCSSRules()
  {
    return !this.cssRules.isEmpty();
  }

  public void registerExternalFileResolver(SVGExternalFileResolver paramSVGExternalFileResolver)
  {
    this.fileResolver = paramSVGExternalFileResolver;
  }

  public void renderToCanvas(Canvas paramCanvas)
  {
    renderToCanvas(paramCanvas, null);
  }

  public void renderToCanvas(Canvas paramCanvas, RectF paramRectF)
  {
    if (paramRectF != null);
    for (Box localBox = Box.fromLimits(paramRectF.left, paramRectF.top, paramRectF.right, paramRectF.bottom); ; localBox = new Box(0.0F, 0.0F, paramCanvas.getWidth(), paramCanvas.getHeight()))
    {
      new SVGAndroidRenderer(paramCanvas, localBox, this.renderDPI).renderDocument(this, null, null, true);
      return;
    }
  }

  public Picture renderToPicture()
  {
    Length localLength1 = this.rootElement.width;
    if (localLength1 != null)
    {
      float f1 = localLength1.floatValue(this.renderDPI);
      Box localBox = this.rootElement.viewBox;
      float f2;
      if (localBox != null)
        f2 = f1 * localBox.height / localBox.width;
      while (true)
      {
        return renderToPicture((int)Math.ceil(f1), (int)Math.ceil(f2));
        Length localLength2 = this.rootElement.height;
        if (localLength2 != null)
          f2 = localLength2.floatValue(this.renderDPI);
        else
          f2 = f1;
      }
    }
    return renderToPicture(512, 512);
  }

  public Picture renderToPicture(int paramInt1, int paramInt2)
  {
    Picture localPicture = new Picture();
    new SVGAndroidRenderer(localPicture.beginRecording(paramInt1, paramInt2), new Box(0.0F, 0.0F, paramInt1, paramInt2), this.renderDPI).renderDocument(this, null, null, false);
    localPicture.endRecording();
    return localPicture;
  }

  public void renderViewToCanvas(String paramString, Canvas paramCanvas)
  {
    renderViewToCanvas(paramString, paramCanvas, null);
  }

  public void renderViewToCanvas(String paramString, Canvas paramCanvas, RectF paramRectF)
  {
    SvgObject localSvgObject = getElementById(paramString);
    if (localSvgObject == null);
    while (!(localSvgObject instanceof View))
      return;
    View localView = (View)localSvgObject;
    if (localView.viewBox == null)
    {
      Log.w("AndroidSVG", "View element is missing a viewBox attribute.");
      return;
    }
    if (paramRectF != null);
    for (Box localBox = Box.fromLimits(paramRectF.left, paramRectF.top, paramRectF.right, paramRectF.bottom); ; localBox = new Box(0.0F, 0.0F, paramCanvas.getWidth(), paramCanvas.getHeight()))
    {
      new SVGAndroidRenderer(paramCanvas, localBox, this.renderDPI).renderDocument(this, localView.viewBox, localView.preserveAspectRatio, true);
      return;
    }
  }

  public Picture renderViewToPicture(String paramString, int paramInt1, int paramInt2)
  {
    SvgObject localSvgObject = getElementById(paramString);
    if (localSvgObject == null);
    while (!(localSvgObject instanceof View))
      return null;
    View localView = (View)localSvgObject;
    if (localView.viewBox == null)
    {
      Log.w("AndroidSVG", "View element is missing a viewBox attribute.");
      return null;
    }
    Picture localPicture = new Picture();
    new SVGAndroidRenderer(localPicture.beginRecording(paramInt1, paramInt2), new Box(0.0F, 0.0F, paramInt1, paramInt2), this.renderDPI).renderDocument(this, localView.viewBox, localView.preserveAspectRatio, false);
    localPicture.endRecording();
    return localPicture;
  }

  protected SvgObject resolveIRI(String paramString)
  {
    if (paramString == null);
    while ((paramString.length() <= 1) || (!paramString.startsWith("#")))
      return null;
    return getElementById(paramString.substring(1));
  }

  protected void setDesc(String paramString)
  {
    this.desc = paramString;
  }

  public void setDocumentHeight(float paramFloat)
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    this.rootElement.height = new Length(paramFloat);
  }

  public void setDocumentHeight(String paramString)
    throws SVGParseException
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    try
    {
      this.rootElement.height = SVGParser.parseLength(paramString);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new SVGParseException(localSAXException.getMessage());
    }
  }

  public void setDocumentPreserveAspectRatio(PreserveAspectRatio paramPreserveAspectRatio)
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    this.rootElement.preserveAspectRatio = paramPreserveAspectRatio;
  }

  public void setDocumentViewBox(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    this.rootElement.viewBox = new Box(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public void setDocumentWidth(float paramFloat)
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    this.rootElement.width = new Length(paramFloat);
  }

  public void setDocumentWidth(String paramString)
    throws SVGParseException
  {
    if (this.rootElement == null)
      throw new IllegalArgumentException("SVG document is empty");
    try
    {
      this.rootElement.width = SVGParser.parseLength(paramString);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new SVGParseException(localSAXException.getMessage());
    }
  }

  public void setRenderDPI(float paramFloat)
  {
    this.renderDPI = paramFloat;
  }

  protected void setRootElement(Svg paramSvg)
  {
    this.rootElement = paramSvg;
  }

  protected void setTitle(String paramString)
  {
    this.title = paramString;
  }

  protected static class Box
    implements Cloneable
  {
    public float height;
    public float minX;
    public float minY;
    public float width;

    public Box(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.minX = paramFloat1;
      this.minY = paramFloat2;
      this.width = paramFloat3;
      this.height = paramFloat4;
    }

    public static Box fromLimits(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return new Box(paramFloat1, paramFloat2, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
    }

    public float maxX()
    {
      return this.minX + this.width;
    }

    public float maxY()
    {
      return this.minY + this.height;
    }

    public RectF toRectF()
    {
      return new RectF(this.minX, this.minY, maxX(), maxY());
    }

    public String toString()
    {
      return "[" + this.minX + " " + this.minY + " " + this.width + " " + this.height + "]";
    }

    public void union(Box paramBox)
    {
      if (paramBox.minX < this.minX)
        this.minX = paramBox.minX;
      if (paramBox.minY < this.minY)
        this.minY = paramBox.minY;
      if (paramBox.maxX() > maxX())
        this.width = (paramBox.maxX() - this.minX);
      if (paramBox.maxY() > maxY())
        this.height = (paramBox.maxY() - this.minY);
    }
  }

  protected static class CSSClipRect
  {
    public SVG.Length bottom;
    public SVG.Length left;
    public SVG.Length right;
    public SVG.Length top;

    public CSSClipRect(SVG.Length paramLength1, SVG.Length paramLength2, SVG.Length paramLength3, SVG.Length paramLength4)
    {
      this.top = paramLength1;
      this.right = paramLength2;
      this.bottom = paramLength3;
      this.left = paramLength4;
    }
  }

  protected static class Circle extends SVG.GraphicsElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length r;
  }

  protected static class ClipPath extends SVG.Group
    implements SVG.NotDirectlyRendered
  {
    public Boolean clipPathUnitsAreUser;
  }

  protected static class Colour extends SVG.SvgPaint
  {
    public static final Colour BLACK = new Colour(0);
    public int colour;

    public Colour(int paramInt)
    {
      this.colour = paramInt;
    }

    public String toString()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.colour);
      return String.format("#%06x", arrayOfObject);
    }
  }

  protected static class CurrentColor extends SVG.SvgPaint
  {
    private static CurrentColor instance = new CurrentColor();

    public static CurrentColor getInstance()
    {
      return instance;
    }
  }

  protected static class Defs extends SVG.Group
    implements SVG.NotDirectlyRendered
  {
  }

  protected static class Ellipse extends SVG.GraphicsElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length rx;
    public SVG.Length ry;
  }

  protected static class GradientElement extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public List<SVG.SvgObject> children = new ArrayList();
    public Matrix gradientTransform;
    public Boolean gradientUnitsAreUser;
    public String href;
    public SVG.GradientSpread spreadMethod;

    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      if ((paramSvgObject instanceof SVG.Stop))
      {
        this.children.add(paramSvgObject);
        return;
      }
      throw new SAXException("Gradient elements cannot contain " + paramSvgObject + " elements.");
    }

    public List<SVG.SvgObject> getChildren()
    {
      return this.children;
    }
  }

  protected static enum GradientSpread
  {
    static
    {
      GradientSpread[] arrayOfGradientSpread = new GradientSpread[3];
      arrayOfGradientSpread[0] = pad;
      arrayOfGradientSpread[1] = reflect;
      arrayOfGradientSpread[2] = repeat;
    }
  }

  protected static abstract class GraphicsElement extends SVG.SvgConditionalElement
    implements SVG.HasTransform
  {
    public Matrix transform;

    public void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }

  protected static class Group extends SVG.SvgConditionalContainer
    implements SVG.HasTransform
  {
    public Matrix transform;

    public void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }

  protected static abstract interface HasTransform
  {
    public abstract void setTransform(Matrix paramMatrix);
  }

  protected static class Image extends SVG.SvgPreserveAspectRatioContainer
    implements SVG.HasTransform
  {
    public SVG.Length height;
    public String href;
    public Matrix transform;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;

    public void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }

  protected static class Length
    implements Cloneable
  {
    SVG.Unit unit = SVG.Unit.px;
    float value = 0.0F;

    public Length(float paramFloat)
    {
      this.value = paramFloat;
      this.unit = SVG.Unit.px;
    }

    public Length(float paramFloat, SVG.Unit paramUnit)
    {
      this.value = paramFloat;
      this.unit = paramUnit;
    }

    public float floatValue()
    {
      return this.value;
    }

    public float floatValue(float paramFloat)
    {
      switch (SVG.1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()])
      {
      case 2:
      case 3:
      default:
        return this.value;
      case 1:
        return this.value;
      case 4:
        return paramFloat * this.value;
      case 5:
        return paramFloat * this.value / 2.54F;
      case 6:
        return paramFloat * this.value / 25.4F;
      case 7:
        return paramFloat * this.value / 72.0F;
      case 8:
      }
      return paramFloat * this.value / 6.0F;
    }

    public float floatValue(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      if (this.unit == SVG.Unit.percent)
      {
        SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
        if (localBox == null)
          return this.value;
        float f1 = localBox.width;
        float f2 = localBox.height;
        if (f1 == f2)
          return f1 * this.value / 100.0F;
        return (float)(Math.sqrt(f1 * f1 + f2 * f2) / 1.414213562373095D) * this.value / 100.0F;
      }
      return floatValueX(paramSVGAndroidRenderer);
    }

    public float floatValue(SVGAndroidRenderer paramSVGAndroidRenderer, float paramFloat)
    {
      if (this.unit == SVG.Unit.percent)
        return paramFloat * this.value / 100.0F;
      return floatValueX(paramSVGAndroidRenderer);
    }

    public float floatValueX(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      switch (SVG.1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()])
      {
      default:
        return this.value;
      case 1:
        return this.value;
      case 2:
        return this.value * paramSVGAndroidRenderer.getCurrentFontSize();
      case 3:
        return this.value * paramSVGAndroidRenderer.getCurrentFontXHeight();
      case 4:
        return this.value * paramSVGAndroidRenderer.getDPI();
      case 5:
        return this.value * paramSVGAndroidRenderer.getDPI() / 2.54F;
      case 6:
        return this.value * paramSVGAndroidRenderer.getDPI() / 25.4F;
      case 7:
        return this.value * paramSVGAndroidRenderer.getDPI() / 72.0F;
      case 8:
        return this.value * paramSVGAndroidRenderer.getDPI() / 6.0F;
      case 9:
      }
      SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
      if (localBox == null)
        return this.value;
      return this.value * localBox.width / 100.0F;
    }

    public float floatValueY(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      if (this.unit == SVG.Unit.percent)
      {
        SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
        if (localBox == null)
          return this.value;
        return this.value * localBox.height / 100.0F;
      }
      return floatValueX(paramSVGAndroidRenderer);
    }

    public boolean isNegative()
    {
      return this.value < 0.0F;
    }

    public boolean isZero()
    {
      return this.value == 0.0F;
    }

    public String toString()
    {
      return String.valueOf(this.value) + this.unit;
    }
  }

  protected static class Line extends SVG.GraphicsElement
  {
    public SVG.Length x1;
    public SVG.Length x2;
    public SVG.Length y1;
    public SVG.Length y2;
  }

  protected static class Marker extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length markerHeight;
    public boolean markerUnitsAreUser;
    public SVG.Length markerWidth;
    public Float orient;
    public SVG.Length refX;
    public SVG.Length refY;
  }

  protected static class Mask extends SVG.SvgConditionalContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length height;
    public Boolean maskContentUnitsAreUser;
    public Boolean maskUnitsAreUser;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }

  protected static abstract interface NotDirectlyRendered
  {
  }

  protected static class PaintReference extends SVG.SvgPaint
  {
    public SVG.SvgPaint fallback;
    public String href;

    public PaintReference(String paramString, SVG.SvgPaint paramSvgPaint)
    {
      this.href = paramString;
      this.fallback = paramSvgPaint;
    }

    public String toString()
    {
      return this.href + " " + this.fallback;
    }
  }

  protected static class Path extends SVG.GraphicsElement
  {
    public SVG.PathDefinition d;
    public Float pathLength;
  }

  protected static class PathDefinition
    implements SVG.PathInterface
  {
    private static final byte ARCTO = 4;
    private static final byte CLOSE = 8;
    private static final byte CUBICTO = 2;
    private static final byte LINETO = 1;
    private static final byte MOVETO = 0;
    private static final byte QUADTO = 3;
    private byte[] commands = null;
    private int commandsLength = 0;
    private float[] coords = null;
    private int coordsLength = 0;

    private void addCommand(byte paramByte)
    {
      if (this.commandsLength == this.commands.length)
      {
        byte[] arrayOfByte2 = new byte[2 * this.commands.length];
        System.arraycopy(this.commands, 0, arrayOfByte2, 0, this.commands.length);
        this.commands = arrayOfByte2;
      }
      byte[] arrayOfByte1 = this.commands;
      int i = this.commandsLength;
      this.commandsLength = (i + 1);
      arrayOfByte1[i] = paramByte;
    }

    private void coordsEnsure(int paramInt)
    {
      if (this.coords.length < paramInt + this.coordsLength)
      {
        float[] arrayOfFloat = new float[2 * this.coords.length];
        System.arraycopy(this.coords, 0, arrayOfFloat, 0, this.coords.length);
        this.coords = arrayOfFloat;
      }
    }

    public void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      if (paramBoolean1);
      for (int i = 2; ; i = 0)
      {
        int j = i | 0x4;
        int k = 0;
        if (paramBoolean2)
          k = 1;
        addCommand((byte)(j | k));
        coordsEnsure(5);
        float[] arrayOfFloat1 = this.coords;
        int m = this.coordsLength;
        this.coordsLength = (m + 1);
        arrayOfFloat1[m] = paramFloat1;
        float[] arrayOfFloat2 = this.coords;
        int n = this.coordsLength;
        this.coordsLength = (n + 1);
        arrayOfFloat2[n] = paramFloat2;
        float[] arrayOfFloat3 = this.coords;
        int i1 = this.coordsLength;
        this.coordsLength = (i1 + 1);
        arrayOfFloat3[i1] = paramFloat3;
        float[] arrayOfFloat4 = this.coords;
        int i2 = this.coordsLength;
        this.coordsLength = (i2 + 1);
        arrayOfFloat4[i2] = paramFloat4;
        float[] arrayOfFloat5 = this.coords;
        int i3 = this.coordsLength;
        this.coordsLength = (i3 + 1);
        arrayOfFloat5[i3] = paramFloat5;
        return;
      }
    }

    public void close()
    {
      addCommand((byte)8);
    }

    public void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      addCommand((byte)2);
      coordsEnsure(6);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
      float[] arrayOfFloat3 = this.coords;
      int k = this.coordsLength;
      this.coordsLength = (k + 1);
      arrayOfFloat3[k] = paramFloat3;
      float[] arrayOfFloat4 = this.coords;
      int m = this.coordsLength;
      this.coordsLength = (m + 1);
      arrayOfFloat4[m] = paramFloat4;
      float[] arrayOfFloat5 = this.coords;
      int n = this.coordsLength;
      this.coordsLength = (n + 1);
      arrayOfFloat5[n] = paramFloat5;
      float[] arrayOfFloat6 = this.coords;
      int i1 = this.coordsLength;
      this.coordsLength = (i1 + 1);
      arrayOfFloat6[i1] = paramFloat6;
    }

    public void enumeratePath(SVG.PathInterface paramPathInterface)
    {
      int i = 0;
      int j = 0;
      if (j < this.commandsLength)
      {
        int k = this.commands[j];
        boolean bool1;
        switch (k)
        {
        case 4:
        case 5:
        case 6:
        case 7:
        default:
          if ((k & 0x2) != 0)
          {
            bool1 = true;
            label82: if ((k & 0x1) == 0)
              break label527;
          }
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        case 8:
        }
        label527: for (boolean bool2 = true; ; bool2 = false)
        {
          float[] arrayOfFloat15 = this.coords;
          int i9 = i + 1;
          float f11 = arrayOfFloat15[i];
          float[] arrayOfFloat16 = this.coords;
          int i10 = i9 + 1;
          float f12 = arrayOfFloat16[i9];
          float[] arrayOfFloat17 = this.coords;
          int i11 = i10 + 1;
          float f13 = arrayOfFloat17[i10];
          float[] arrayOfFloat18 = this.coords;
          int i12 = i11 + 1;
          float f14 = arrayOfFloat18[i11];
          float[] arrayOfFloat19 = this.coords;
          int i13 = i12 + 1;
          paramPathInterface.arcTo(f11, f12, f13, bool1, bool2, f14, arrayOfFloat19[i12]);
          i = i13;
          while (true)
          {
            j++;
            break;
            float[] arrayOfFloat13 = this.coords;
            int i8 = i + 1;
            float f10 = arrayOfFloat13[i];
            float[] arrayOfFloat14 = this.coords;
            i = i8 + 1;
            paramPathInterface.moveTo(f10, arrayOfFloat14[i8]);
            continue;
            float[] arrayOfFloat11 = this.coords;
            int i7 = i + 1;
            float f9 = arrayOfFloat11[i];
            float[] arrayOfFloat12 = this.coords;
            i = i7 + 1;
            paramPathInterface.lineTo(f9, arrayOfFloat12[i7]);
            continue;
            float[] arrayOfFloat5 = this.coords;
            int i2 = i + 1;
            float f4 = arrayOfFloat5[i];
            float[] arrayOfFloat6 = this.coords;
            int i3 = i2 + 1;
            float f5 = arrayOfFloat6[i2];
            float[] arrayOfFloat7 = this.coords;
            int i4 = i3 + 1;
            float f6 = arrayOfFloat7[i3];
            float[] arrayOfFloat8 = this.coords;
            int i5 = i4 + 1;
            float f7 = arrayOfFloat8[i4];
            float[] arrayOfFloat9 = this.coords;
            int i6 = i5 + 1;
            float f8 = arrayOfFloat9[i5];
            float[] arrayOfFloat10 = this.coords;
            i = i6 + 1;
            paramPathInterface.cubicTo(f4, f5, f6, f7, f8, arrayOfFloat10[i6]);
            continue;
            float[] arrayOfFloat1 = this.coords;
            int m = i + 1;
            float f1 = arrayOfFloat1[i];
            float[] arrayOfFloat2 = this.coords;
            int n = m + 1;
            float f2 = arrayOfFloat2[m];
            float[] arrayOfFloat3 = this.coords;
            int i1 = n + 1;
            float f3 = arrayOfFloat3[n];
            float[] arrayOfFloat4 = this.coords;
            i = i1 + 1;
            paramPathInterface.quadTo(f1, f2, f3, arrayOfFloat4[i1]);
            continue;
            paramPathInterface.close();
          }
          bool1 = false;
          break label82;
        }
      }
    }

    public boolean isEmpty()
    {
      return this.commandsLength == 0;
    }

    public void lineTo(float paramFloat1, float paramFloat2)
    {
      addCommand((byte)1);
      coordsEnsure(2);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
    }

    public void moveTo(float paramFloat1, float paramFloat2)
    {
      addCommand((byte)0);
      coordsEnsure(2);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
    }

    public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      addCommand((byte)3);
      coordsEnsure(4);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
      float[] arrayOfFloat3 = this.coords;
      int k = this.coordsLength;
      this.coordsLength = (k + 1);
      arrayOfFloat3[k] = paramFloat3;
      float[] arrayOfFloat4 = this.coords;
      int m = this.coordsLength;
      this.coordsLength = (m + 1);
      arrayOfFloat4[m] = paramFloat4;
    }
  }

  protected static abstract interface PathInterface
  {
    public abstract void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5);

    public abstract void close();

    public abstract void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

    public abstract void lineTo(float paramFloat1, float paramFloat2);

    public abstract void moveTo(float paramFloat1, float paramFloat2);

    public abstract void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  }

  protected static class Pattern extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length height;
    public String href;
    public Boolean patternContentUnitsAreUser;
    public Matrix patternTransform;
    public Boolean patternUnitsAreUser;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }

  protected static class PolyLine extends SVG.GraphicsElement
  {
    public float[] points;
  }

  protected static class Polygon extends SVG.PolyLine
  {
  }

  protected static class Rect extends SVG.GraphicsElement
  {
    public SVG.Length height;
    public SVG.Length rx;
    public SVG.Length ry;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }

  protected static class SolidColor extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public SVG.Length solidColor;
    public SVG.Length solidOpacity;

    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
    }

    public List<SVG.SvgObject> getChildren()
    {
      return Collections.emptyList();
    }
  }

  protected static class Stop extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public Float offset;

    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
    }

    public List<SVG.SvgObject> getChildren()
    {
      return Collections.emptyList();
    }
  }

  protected static class Style
    implements Cloneable
  {
    public static final int FONT_WEIGHT_BOLD = 700;
    public static final int FONT_WEIGHT_BOLDER = 1;
    public static final int FONT_WEIGHT_LIGHTER = -1;
    public static final int FONT_WEIGHT_NORMAL = 400;
    public SVG.CSSClipRect clip;
    public String clipPath;
    public FillRule clipRule;
    public SVG.Colour color;
    public TextDirection direction;
    public Boolean display;
    public SVG.SvgPaint fill;
    public Float fillOpacity;
    public FillRule fillRule;
    public List<String> fontFamily;
    public SVG.Length fontSize;
    public FontStyle fontStyle;
    public Integer fontWeight;
    public String markerEnd;
    public String markerMid;
    public String markerStart;
    public String mask;
    public Float opacity;
    public Boolean overflow;
    public SVG.SvgPaint solidColor;
    public Float solidOpacity;
    public long specifiedFlags = 0L;
    public SVG.SvgPaint stopColor;
    public Float stopOpacity;
    public SVG.SvgPaint stroke;
    public SVG.Length[] strokeDashArray;
    public SVG.Length strokeDashOffset;
    public LineCaps strokeLineCap;
    public LineJoin strokeLineJoin;
    public Float strokeMiterLimit;
    public Float strokeOpacity;
    public SVG.Length strokeWidth;
    public TextAnchor textAnchor;
    public TextDecoration textDecoration;
    public VectorEffect vectorEffect;
    public SVG.SvgPaint viewportFill;
    public Float viewportFillOpacity;
    public Boolean visibility;

    public static Style getDefaultStyle()
    {
      Style localStyle = new Style();
      localStyle.specifiedFlags = -1L;
      localStyle.fill = SVG.Colour.BLACK;
      localStyle.fillRule = FillRule.NonZero;
      localStyle.fillOpacity = Float.valueOf(1.0F);
      localStyle.stroke = null;
      localStyle.strokeOpacity = Float.valueOf(1.0F);
      localStyle.strokeWidth = new SVG.Length(1.0F);
      localStyle.strokeLineCap = LineCaps.Butt;
      localStyle.strokeLineJoin = LineJoin.Miter;
      localStyle.strokeMiterLimit = Float.valueOf(4.0F);
      localStyle.strokeDashArray = null;
      localStyle.strokeDashOffset = new SVG.Length(0.0F);
      localStyle.opacity = Float.valueOf(1.0F);
      localStyle.color = SVG.Colour.BLACK;
      localStyle.fontFamily = null;
      localStyle.fontSize = new SVG.Length(12.0F, SVG.Unit.pt);
      localStyle.fontWeight = Integer.valueOf(400);
      localStyle.fontStyle = FontStyle.Normal;
      localStyle.textDecoration = TextDecoration.None;
      localStyle.direction = TextDirection.LTR;
      localStyle.textAnchor = TextAnchor.Start;
      localStyle.overflow = Boolean.valueOf(true);
      localStyle.clip = null;
      localStyle.markerStart = null;
      localStyle.markerMid = null;
      localStyle.markerEnd = null;
      localStyle.display = Boolean.TRUE;
      localStyle.visibility = Boolean.TRUE;
      localStyle.stopColor = SVG.Colour.BLACK;
      localStyle.stopOpacity = Float.valueOf(1.0F);
      localStyle.clipPath = null;
      localStyle.clipRule = FillRule.NonZero;
      localStyle.mask = null;
      localStyle.solidColor = null;
      localStyle.solidOpacity = Float.valueOf(1.0F);
      localStyle.viewportFill = null;
      localStyle.viewportFillOpacity = Float.valueOf(1.0F);
      localStyle.vectorEffect = VectorEffect.None;
      return localStyle;
    }

    protected Object clone()
    {
      try
      {
        Style localStyle = (Style)super.clone();
        if (this.strokeDashArray != null)
          localStyle.strokeDashArray = ((SVG.Length[])this.strokeDashArray.clone());
        return localStyle;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        throw new InternalError(localCloneNotSupportedException.toString());
      }
    }

    public void resetNonInheritingProperties()
    {
      resetNonInheritingProperties(false);
    }

    public void resetNonInheritingProperties(boolean paramBoolean)
    {
      this.display = Boolean.TRUE;
      if (paramBoolean);
      for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
      {
        this.overflow = localBoolean;
        this.clip = null;
        this.clipPath = null;
        this.opacity = Float.valueOf(1.0F);
        this.stopColor = SVG.Colour.BLACK;
        this.stopOpacity = Float.valueOf(1.0F);
        this.mask = null;
        this.solidColor = null;
        this.solidOpacity = Float.valueOf(1.0F);
        this.viewportFill = null;
        this.viewportFillOpacity = Float.valueOf(1.0F);
        this.vectorEffect = VectorEffect.None;
        return;
      }
    }

    public static enum FillRule
    {
      static
      {
        EvenOdd = new FillRule("EvenOdd", 1);
        FillRule[] arrayOfFillRule = new FillRule[2];
        arrayOfFillRule[0] = NonZero;
        arrayOfFillRule[1] = EvenOdd;
      }
    }

    public static enum FontStyle
    {
      static
      {
        Italic = new FontStyle("Italic", 1);
        Oblique = new FontStyle("Oblique", 2);
        FontStyle[] arrayOfFontStyle = new FontStyle[3];
        arrayOfFontStyle[0] = Normal;
        arrayOfFontStyle[1] = Italic;
        arrayOfFontStyle[2] = Oblique;
      }
    }

    public static enum LineCaps
    {
      static
      {
        LineCaps[] arrayOfLineCaps = new LineCaps[3];
        arrayOfLineCaps[0] = Butt;
        arrayOfLineCaps[1] = Round;
        arrayOfLineCaps[2] = Square;
      }
    }

    public static enum LineJoin
    {
      static
      {
        Bevel = new LineJoin("Bevel", 2);
        LineJoin[] arrayOfLineJoin = new LineJoin[3];
        arrayOfLineJoin[0] = Miter;
        arrayOfLineJoin[1] = Round;
        arrayOfLineJoin[2] = Bevel;
      }
    }

    public static enum TextAnchor
    {
      static
      {
        Middle = new TextAnchor("Middle", 1);
        End = new TextAnchor("End", 2);
        TextAnchor[] arrayOfTextAnchor = new TextAnchor[3];
        arrayOfTextAnchor[0] = Start;
        arrayOfTextAnchor[1] = Middle;
        arrayOfTextAnchor[2] = End;
      }
    }

    public static enum TextDecoration
    {
      static
      {
        Overline = new TextDecoration("Overline", 2);
        LineThrough = new TextDecoration("LineThrough", 3);
        Blink = new TextDecoration("Blink", 4);
        TextDecoration[] arrayOfTextDecoration = new TextDecoration[5];
        arrayOfTextDecoration[0] = None;
        arrayOfTextDecoration[1] = Underline;
        arrayOfTextDecoration[2] = Overline;
        arrayOfTextDecoration[3] = LineThrough;
        arrayOfTextDecoration[4] = Blink;
      }
    }

    public static enum TextDirection
    {
      static
      {
        TextDirection[] arrayOfTextDirection = new TextDirection[2];
        arrayOfTextDirection[0] = LTR;
        arrayOfTextDirection[1] = RTL;
      }
    }

    public static enum VectorEffect
    {
      static
      {
        NonScalingStroke = new VectorEffect("NonScalingStroke", 1);
        VectorEffect[] arrayOfVectorEffect = new VectorEffect[2];
        arrayOfVectorEffect[0] = None;
        arrayOfVectorEffect[1] = NonScalingStroke;
      }
    }
  }

  protected static class Svg extends SVG.SvgViewBoxContainer
  {
    public SVG.Length height;
    public String version;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }

  protected static abstract interface SvgConditional
  {
    public abstract String getRequiredExtensions();

    public abstract Set<String> getRequiredFeatures();

    public abstract Set<String> getRequiredFonts();

    public abstract Set<String> getRequiredFormats();

    public abstract Set<String> getSystemLanguage();

    public abstract void setRequiredExtensions(String paramString);

    public abstract void setRequiredFeatures(Set<String> paramSet);

    public abstract void setRequiredFonts(Set<String> paramSet);

    public abstract void setRequiredFormats(Set<String> paramSet);

    public abstract void setSystemLanguage(Set<String> paramSet);
  }

  protected static class SvgConditionalContainer extends SVG.SvgElement
    implements SVG.SvgContainer, SVG.SvgConditional
  {
    public List<SVG.SvgObject> children = new ArrayList();
    public String requiredExtensions = null;
    public Set<String> requiredFeatures = null;
    public Set<String> requiredFonts = null;
    public Set<String> requiredFormats = null;
    public Set<String> systemLanguage = null;

    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      this.children.add(paramSvgObject);
    }

    public List<SVG.SvgObject> getChildren()
    {
      return this.children;
    }

    public String getRequiredExtensions()
    {
      return this.requiredExtensions;
    }

    public Set<String> getRequiredFeatures()
    {
      return this.requiredFeatures;
    }

    public Set<String> getRequiredFonts()
    {
      return this.requiredFonts;
    }

    public Set<String> getRequiredFormats()
    {
      return this.requiredFormats;
    }

    public Set<String> getSystemLanguage()
    {
      return null;
    }

    public void setRequiredExtensions(String paramString)
    {
      this.requiredExtensions = paramString;
    }

    public void setRequiredFeatures(Set<String> paramSet)
    {
      this.requiredFeatures = paramSet;
    }

    public void setRequiredFonts(Set<String> paramSet)
    {
      this.requiredFonts = paramSet;
    }

    public void setRequiredFormats(Set<String> paramSet)
    {
      this.requiredFormats = paramSet;
    }

    public void setSystemLanguage(Set<String> paramSet)
    {
      this.systemLanguage = paramSet;
    }
  }

  protected static class SvgConditionalElement extends SVG.SvgElement
    implements SVG.SvgConditional
  {
    public String requiredExtensions = null;
    public Set<String> requiredFeatures = null;
    public Set<String> requiredFonts = null;
    public Set<String> requiredFormats = null;
    public Set<String> systemLanguage = null;

    public String getRequiredExtensions()
    {
      return this.requiredExtensions;
    }

    public Set<String> getRequiredFeatures()
    {
      return this.requiredFeatures;
    }

    public Set<String> getRequiredFonts()
    {
      return this.requiredFonts;
    }

    public Set<String> getRequiredFormats()
    {
      return this.requiredFormats;
    }

    public Set<String> getSystemLanguage()
    {
      return this.systemLanguage;
    }

    public void setRequiredExtensions(String paramString)
    {
      this.requiredExtensions = paramString;
    }

    public void setRequiredFeatures(Set<String> paramSet)
    {
      this.requiredFeatures = paramSet;
    }

    public void setRequiredFonts(Set<String> paramSet)
    {
      this.requiredFonts = paramSet;
    }

    public void setRequiredFormats(Set<String> paramSet)
    {
      this.requiredFormats = paramSet;
    }

    public void setSystemLanguage(Set<String> paramSet)
    {
      this.systemLanguage = paramSet;
    }
  }

  protected static abstract interface SvgContainer
  {
    public abstract void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException;

    public abstract List<SVG.SvgObject> getChildren();
  }

  protected static class SvgElement extends SVG.SvgElementBase
  {
    public SVG.Box boundingBox = null;
  }

  protected static class SvgElementBase extends SVG.SvgObject
  {
    public SVG.Style baseStyle = null;
    public List<String> classNames = null;
    public String id = null;
    public Boolean spacePreserve = null;
    public SVG.Style style = null;
  }

  protected static class SvgLinearGradient extends SVG.GradientElement
  {
    public SVG.Length x1;
    public SVG.Length x2;
    public SVG.Length y1;
    public SVG.Length y2;
  }

  protected static class SvgObject
  {
    public SVG document;
    public SVG.SvgContainer parent;

    public String toString()
    {
      return getClass().getSimpleName();
    }
  }

  protected static abstract class SvgPaint
    implements Cloneable
  {
  }

  protected static class SvgPreserveAspectRatioContainer extends SVG.SvgConditionalContainer
  {
    public PreserveAspectRatio preserveAspectRatio = null;
  }

  protected static class SvgRadialGradient extends SVG.GradientElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length fx;
    public SVG.Length fy;
    public SVG.Length r;
  }

  protected static class SvgViewBoxContainer extends SVG.SvgPreserveAspectRatioContainer
  {
    public SVG.Box viewBox;
  }

  protected static class Switch extends SVG.Group
  {
  }

  protected static class Symbol extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
  }

  protected static class TRef extends SVG.TextContainer
    implements SVG.TextChild
  {
    public String href;
    private SVG.TextRoot textRoot;

    public SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }

    public void setTextRoot(SVG.TextRoot paramTextRoot)
    {
      this.textRoot = paramTextRoot;
    }
  }

  protected static class TSpan extends SVG.TextPositionedContainer
    implements SVG.TextChild
  {
    private SVG.TextRoot textRoot;

    public SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }

    public void setTextRoot(SVG.TextRoot paramTextRoot)
    {
      this.textRoot = paramTextRoot;
    }
  }

  protected static class Text extends SVG.TextPositionedContainer
    implements SVG.TextRoot, SVG.HasTransform
  {
    public Matrix transform;

    public void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }

  protected static abstract interface TextChild
  {
    public abstract SVG.TextRoot getTextRoot();

    public abstract void setTextRoot(SVG.TextRoot paramTextRoot);
  }

  protected static class TextContainer extends SVG.SvgConditionalContainer
  {
    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      if ((paramSvgObject instanceof SVG.TextChild))
      {
        this.children.add(paramSvgObject);
        return;
      }
      throw new SAXException("Text content elements cannot contain " + paramSvgObject + " elements.");
    }
  }

  protected static class TextPath extends SVG.TextContainer
    implements SVG.TextChild
  {
    public String href;
    public SVG.Length startOffset;
    private SVG.TextRoot textRoot;

    public SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }

    public void setTextRoot(SVG.TextRoot paramTextRoot)
    {
      this.textRoot = paramTextRoot;
    }
  }

  protected static class TextPositionedContainer extends SVG.TextContainer
  {
    public List<SVG.Length> dx;
    public List<SVG.Length> dy;
    public List<SVG.Length> x;
    public List<SVG.Length> y;
  }

  protected static abstract interface TextRoot
  {
  }

  protected static class TextSequence extends SVG.SvgObject
    implements SVG.TextChild
  {
    public String text;
    private SVG.TextRoot textRoot;

    public TextSequence(String paramString)
    {
      this.text = paramString;
    }

    public SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }

    public void setTextRoot(SVG.TextRoot paramTextRoot)
    {
      this.textRoot = paramTextRoot;
    }

    public String toString()
    {
      return getClass().getSimpleName() + " '" + this.text + "'";
    }
  }

  protected static enum Unit
  {
    static
    {
      em = new Unit("em", 1);
      ex = new Unit("ex", 2);
      in = new Unit("in", 3);
      cm = new Unit("cm", 4);
      mm = new Unit("mm", 5);
      pt = new Unit("pt", 6);
      pc = new Unit("pc", 7);
      percent = new Unit("percent", 8);
      Unit[] arrayOfUnit = new Unit[9];
      arrayOfUnit[0] = px;
      arrayOfUnit[1] = em;
      arrayOfUnit[2] = ex;
      arrayOfUnit[3] = in;
      arrayOfUnit[4] = cm;
      arrayOfUnit[5] = mm;
      arrayOfUnit[6] = pt;
      arrayOfUnit[7] = pc;
      arrayOfUnit[8] = percent;
    }
  }

  protected static class Use extends SVG.Group
  {
    public SVG.Length height;
    public String href;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }

  protected static class View extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVG
 * JD-Core Version:    0.6.2
 */