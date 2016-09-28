package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

public class SVGAndroidRenderer
{
  private static final float BEZIER_ARC_FACTOR = 0.5522848F;
  private static final String DEFAULT_FONT_FAMILY = "sans-serif";
  private static final int LUMINANCE_FACTOR_SHIFT = 15;
  private static final int LUMINANCE_TO_ALPHA_BLUE = 2362;
  private static final int LUMINANCE_TO_ALPHA_GREEN = 23442;
  private static final int LUMINANCE_TO_ALPHA_RED = 6963;
  private static final String TAG = "SVGAndroidRenderer";
  private static HashSet<String> supportedFeatures = null;
  private Stack<Bitmap> bitmapStack;
  private Canvas canvas;
  private Stack<Canvas> canvasStack;
  private SVG.Box canvasViewPort;
  private boolean directRenderingMode;
  private SVG document;
  private float dpi;
  private Stack<Matrix> matrixStack;
  private Stack<SVG.SvgContainer> parentStack;
  private RendererState state;
  private Stack<RendererState> stateStack;

  protected SVGAndroidRenderer(Canvas paramCanvas, SVG.Box paramBox, float paramFloat)
  {
    this.canvas = paramCanvas;
    this.dpi = paramFloat;
    this.canvasViewPort = paramBox;
  }

  private void addObjectToClip(SVG.GraphicsElement paramGraphicsElement, Path paramPath, Matrix paramMatrix)
  {
    updateStyleForElement(this.state, paramGraphicsElement);
    if (!display())
      break label16;
    label16: 
    while (!visible())
      return;
    if (paramGraphicsElement.transform != null)
      paramMatrix.preConcat(paramGraphicsElement.transform);
    Path localPath;
    if ((paramGraphicsElement instanceof SVG.Rect))
      localPath = makePathAndBoundingBox((SVG.Rect)paramGraphicsElement);
    while (true)
    {
      checkForClipPath(paramGraphicsElement);
      paramPath.setFillType(localPath.getFillType());
      paramPath.addPath(localPath, paramMatrix);
      return;
      if ((paramGraphicsElement instanceof SVG.Circle))
      {
        localPath = makePathAndBoundingBox((SVG.Circle)paramGraphicsElement);
      }
      else if ((paramGraphicsElement instanceof SVG.Ellipse))
      {
        localPath = makePathAndBoundingBox((SVG.Ellipse)paramGraphicsElement);
      }
      else
      {
        if (!(paramGraphicsElement instanceof SVG.PolyLine))
          break;
        localPath = makePathAndBoundingBox((SVG.PolyLine)paramGraphicsElement);
      }
    }
  }

  private void addObjectToClip(SVG.Path paramPath, Path paramPath1, Matrix paramMatrix)
  {
    updateStyleForElement(this.state, paramPath);
    if (!display());
    while (!visible())
      return;
    if (paramPath.transform != null)
      paramMatrix.preConcat(paramPath.transform);
    Path localPath = new PathConverter(paramPath.d).getPath();
    if (paramPath.boundingBox == null)
      paramPath.boundingBox = calculatePathBounds(localPath);
    checkForClipPath(paramPath);
    paramPath1.setFillType(getClipRuleFromState());
    paramPath1.addPath(localPath, paramMatrix);
  }

  private void addObjectToClip(SVG.SvgObject paramSvgObject, boolean paramBoolean, Path paramPath, Matrix paramMatrix)
  {
    if (!display())
      return;
    clipStatePush();
    if ((paramSvgObject instanceof SVG.Use))
      if (paramBoolean)
        addObjectToClip((SVG.Use)paramSvgObject, paramPath, paramMatrix);
    while (true)
    {
      clipStatePop();
      return;
      error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
      continue;
      if ((paramSvgObject instanceof SVG.Path))
      {
        addObjectToClip((SVG.Path)paramSvgObject, paramPath, paramMatrix);
      }
      else if ((paramSvgObject instanceof SVG.Text))
      {
        addObjectToClip((SVG.Text)paramSvgObject, paramPath, paramMatrix);
      }
      else if ((paramSvgObject instanceof SVG.GraphicsElement))
      {
        addObjectToClip((SVG.GraphicsElement)paramSvgObject, paramPath, paramMatrix);
      }
      else
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramSvgObject.getClass().getSimpleName();
        error("Invalid %s element found in clipPath definition", arrayOfObject);
      }
    }
  }

  private void addObjectToClip(SVG.Text paramText, Path paramPath, Matrix paramMatrix)
  {
    updateStyleForElement(this.state, paramText);
    if (!display())
      return;
    if (paramText.transform != null)
      paramMatrix.preConcat(paramText.transform);
    float f1;
    float f2;
    label77: float f3;
    label99: float f4;
    label121: float f5;
    if ((paramText.x == null) || (paramText.x.size() == 0))
    {
      f1 = 0.0F;
      if ((paramText.y != null) && (paramText.y.size() != 0))
        break label317;
      f2 = 0.0F;
      if ((paramText.dx != null) && (paramText.dx.size() != 0))
        break label339;
      f3 = 0.0F;
      if ((paramText.dy != null) && (paramText.dy.size() != 0))
        break label361;
      f4 = 0.0F;
      if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start)
      {
        f5 = calculateTextWidth(paramText);
        if (this.state.style.textAnchor != SVG.Style.TextAnchor.Middle)
          break label383;
      }
    }
    label317: label339: label361: label383: for (f1 -= f5 / 2.0F; ; f1 -= f5)
    {
      if (paramText.boundingBox == null)
      {
        TextBoundsCalculator localTextBoundsCalculator = new TextBoundsCalculator(f1, f2);
        enumerateTextSpans(paramText, localTextBoundsCalculator);
        paramText.boundingBox = new SVG.Box(localTextBoundsCalculator.bbox.left, localTextBoundsCalculator.bbox.top, localTextBoundsCalculator.bbox.width(), localTextBoundsCalculator.bbox.height());
      }
      checkForClipPath(paramText);
      Path localPath = new Path();
      enumerateTextSpans(paramText, new PlainTextToPath(f1 + f3, f2 + f4, localPath));
      paramPath.setFillType(getClipRuleFromState());
      paramPath.addPath(localPath, paramMatrix);
      return;
      f1 = ((SVG.Length)paramText.x.get(0)).floatValueX(this);
      break;
      f2 = ((SVG.Length)paramText.y.get(0)).floatValueY(this);
      break label77;
      f3 = ((SVG.Length)paramText.dx.get(0)).floatValueX(this);
      break label99;
      f4 = ((SVG.Length)paramText.dy.get(0)).floatValueY(this);
      break label121;
    }
  }

  private void addObjectToClip(SVG.Use paramUse, Path paramPath, Matrix paramMatrix)
  {
    updateStyleForElement(this.state, paramUse);
    if (!display());
    while (!visible())
      return;
    if (paramUse.transform != null)
      paramMatrix.preConcat(paramUse.transform);
    SVG.SvgObject localSvgObject = paramUse.document.resolveIRI(paramUse.href);
    if (localSvgObject == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUse.href;
      error("Use reference '%s' not found", arrayOfObject);
      return;
    }
    checkForClipPath(paramUse);
    addObjectToClip(localSvgObject, false, paramPath, paramMatrix);
  }

  private static void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, boolean paramBoolean1, boolean paramBoolean2, float paramFloat6, float paramFloat7, SVG.PathInterface paramPathInterface)
  {
    if ((paramFloat1 == paramFloat6) && (paramFloat2 == paramFloat7))
      return;
    if ((paramFloat3 == 0.0F) || (paramFloat4 == 0.0F))
    {
      paramPathInterface.lineTo(paramFloat6, paramFloat7);
      return;
    }
    float f1 = Math.abs(paramFloat3);
    float f2 = Math.abs(paramFloat4);
    float f3 = (float)Math.toRadians(paramFloat5 % 360.0D);
    double d1 = Math.cos(f3);
    double d2 = Math.sin(f3);
    double d3 = (paramFloat1 - paramFloat6) / 2.0D;
    double d4 = (paramFloat2 - paramFloat7) / 2.0D;
    double d5 = d1 * d3 + d2 * d4;
    double d6 = d3 * -d2 + d1 * d4;
    double d7 = f1 * f1;
    double d8 = f2 * f2;
    double d9 = d5 * d5;
    double d10 = d6 * d6;
    double d11 = d9 / d7 + d10 / d8;
    if (d11 > 1.0D)
    {
      f1 *= (float)Math.sqrt(d11);
      f2 *= (float)Math.sqrt(d11);
      d7 = f1 * f1;
      d8 = f2 * f2;
    }
    double d12;
    label229: double d19;
    double d20;
    double d26;
    label439: double d27;
    double d30;
    label517: double d31;
    if (paramBoolean1 == paramBoolean2)
    {
      d12 = -1.0D;
      double d13 = (d7 * d8 - d7 * d10 - d8 * d9) / (d7 * d10 + d8 * d9);
      if (d13 < 0.0D)
        d13 = 0.0D;
      double d14 = d12 * Math.sqrt(d13);
      double d15 = d14 * (d6 * f1 / f2);
      double d16 = d14 * -(d5 * f2 / f1);
      double d17 = (paramFloat1 + paramFloat6) / 2.0D;
      double d18 = (paramFloat2 + paramFloat7) / 2.0D;
      d19 = d17 + (d1 * d15 - d2 * d16);
      d20 = d18 + (d2 * d15 + d1 * d16);
      double d21 = (d5 - d15) / f1;
      double d22 = (d6 - d16) / f2;
      double d23 = (-d5 - d15) / f1;
      double d24 = (-d6 - d16) / f2;
      double d25 = Math.sqrt(d21 * d21 + d22 * d22);
      if (d22 >= 0.0D)
        break label715;
      d26 = -1.0D;
      d27 = Math.toDegrees(d26 * Math.acos(d21 / d25));
      double d28 = Math.sqrt((d21 * d21 + d22 * d22) * (d23 * d23 + d24 * d24));
      double d29 = d21 * d23 + d22 * d24;
      if (d21 * d24 - d22 * d23 >= 0.0D)
        break label721;
      d30 = -1.0D;
      d31 = Math.toDegrees(d30 * Math.acos(d29 / d28));
      if ((paramBoolean2) || (d31 <= 0.0D))
        break label727;
    }
    for (d31 -= 360.0D; ; d31 += 360.0D)
      label715: label721: label727: 
      do
      {
        double d32 = d31 % 360.0D;
        float[] arrayOfFloat = arcToBeziers(d27 % 360.0D, d32);
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(f1, f2);
        localMatrix.postRotate(paramFloat5);
        localMatrix.postTranslate((float)d19, (float)d20);
        localMatrix.mapPoints(arrayOfFloat);
        arrayOfFloat[(-2 + arrayOfFloat.length)] = paramFloat6;
        arrayOfFloat[(-1 + arrayOfFloat.length)] = paramFloat7;
        for (int i = 0; ; i += 6)
        {
          int j = arrayOfFloat.length;
          if (i >= j)
            break;
          paramPathInterface.cubicTo(arrayOfFloat[i], arrayOfFloat[(i + 1)], arrayOfFloat[(i + 2)], arrayOfFloat[(i + 3)], arrayOfFloat[(i + 4)], arrayOfFloat[(i + 5)]);
        }
        d12 = 1.0D;
        break label229;
        d26 = 1.0D;
        break label439;
        d30 = 1.0D;
        break label517;
      }
      while ((!paramBoolean2) || (d31 >= 0.0D));
  }

  private static float[] arcToBeziers(double paramDouble1, double paramDouble2)
  {
    int i = (int)Math.ceil(Math.abs(paramDouble2) / 90.0D);
    double d1 = Math.toRadians(paramDouble1);
    float f = (float)(Math.toRadians(paramDouble2) / i);
    double d2 = 1.333333333333333D * Math.sin(f / 2.0D) / (1.0D + Math.cos(f / 2.0D));
    float[] arrayOfFloat = new float[i * 6];
    int j = 0;
    int k = 0;
    while (j < i)
    {
      double d3 = d1 + f * j;
      double d4 = Math.cos(d3);
      double d5 = Math.sin(d3);
      int m = k + 1;
      arrayOfFloat[k] = ((float)(d4 - d2 * d5));
      int n = m + 1;
      arrayOfFloat[m] = ((float)(d5 + d2 * d4));
      double d6 = d3 + f;
      double d7 = Math.cos(d6);
      double d8 = Math.sin(d6);
      int i1 = n + 1;
      arrayOfFloat[n] = ((float)(d7 + d2 * d8));
      int i2 = i1 + 1;
      arrayOfFloat[i1] = ((float)(d8 - d2 * d7));
      int i3 = i2 + 1;
      arrayOfFloat[i2] = ((float)d7);
      k = i3 + 1;
      arrayOfFloat[i3] = ((float)d8);
      j++;
    }
    return arrayOfFloat;
  }

  private List<MarkerVector> calculateMarkerPositions(SVG.Line paramLine)
  {
    float f1;
    float f2;
    label32: float f3;
    if (paramLine.x1 != null)
    {
      f1 = paramLine.x1.floatValueX(this);
      if (paramLine.y1 == null)
        break label138;
      f2 = paramLine.y1.floatValueY(this);
      if (paramLine.x2 == null)
        break label143;
      f3 = paramLine.x2.floatValueX(this);
      label49: if (paramLine.y2 == null)
        break label149;
    }
    label138: label143: label149: for (float f4 = paramLine.y2.floatValueY(this); ; f4 = 0.0F)
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(new MarkerVector(f1, f2, f3 - f1, f4 - f2));
      localArrayList.add(new MarkerVector(f3, f4, f3 - f1, f4 - f2));
      return localArrayList;
      f1 = 0.0F;
      break;
      f2 = 0.0F;
      break label32;
      f3 = 0.0F;
      break label49;
    }
  }

  private List<MarkerVector> calculateMarkerPositions(SVG.PolyLine paramPolyLine)
  {
    int i = paramPolyLine.points.length;
    Object localObject;
    if (i < 2)
      localObject = null;
    MarkerVector localMarkerVector1;
    float f1;
    float f2;
    do
    {
      return localObject;
      localObject = new ArrayList();
      localMarkerVector1 = new MarkerVector(paramPolyLine.points[0], paramPolyLine.points[1], 0.0F, 0.0F);
      f1 = 0.0F;
      f2 = 0.0F;
      for (int j = 2; j < i; j += 2)
      {
        f1 = paramPolyLine.points[j];
        f2 = paramPolyLine.points[(j + 1)];
        localMarkerVector1.add(f1, f2);
        ((List)localObject).add(localMarkerVector1);
        localMarkerVector1 = new MarkerVector(f1, f2, f1 - localMarkerVector1.x, f2 - localMarkerVector1.y);
      }
      if (!(paramPolyLine instanceof SVG.Polygon))
        break;
    }
    while ((f1 == paramPolyLine.points[0]) || (f2 == paramPolyLine.points[1]));
    float f3 = paramPolyLine.points[0];
    float f4 = paramPolyLine.points[1];
    localMarkerVector1.add(f3, f4);
    ((List)localObject).add(localMarkerVector1);
    MarkerVector localMarkerVector2 = new MarkerVector(f3, f4, f3 - localMarkerVector1.x, f4 - localMarkerVector1.y);
    localMarkerVector2.add((MarkerVector)((List)localObject).get(0));
    ((List)localObject).add(localMarkerVector2);
    ((List)localObject).set(0, localMarkerVector2);
    return localObject;
    ((List)localObject).add(localMarkerVector1);
    return localObject;
  }

  private SVG.Box calculatePathBounds(Path paramPath)
  {
    RectF localRectF = new RectF();
    paramPath.computeBounds(localRectF, true);
    return new SVG.Box(localRectF.left, localRectF.top, localRectF.width(), localRectF.height());
  }

  private float calculateTextWidth(SVG.TextContainer paramTextContainer)
  {
    TextWidthCalculator localTextWidthCalculator = new TextWidthCalculator(null);
    enumerateTextSpans(paramTextContainer, localTextWidthCalculator);
    return localTextWidthCalculator.x;
  }

  private Matrix calculateViewBoxTransform(SVG.Box paramBox1, SVG.Box paramBox2, PreserveAspectRatio paramPreserveAspectRatio)
  {
    Matrix localMatrix = new Matrix();
    if ((paramPreserveAspectRatio == null) || (paramPreserveAspectRatio.getAlignment() == null))
      return localMatrix;
    float f1 = paramBox1.width / paramBox2.width;
    float f2 = paramBox1.height / paramBox2.height;
    float f3 = -paramBox2.minX;
    float f4 = -paramBox2.minY;
    if (paramPreserveAspectRatio.equals(PreserveAspectRatio.STRETCH))
    {
      localMatrix.preTranslate(paramBox1.minX, paramBox1.minY);
      localMatrix.preScale(f1, f2);
      localMatrix.preTranslate(f3, f4);
      return localMatrix;
    }
    float f5;
    float f6;
    float f7;
    if (paramPreserveAspectRatio.getScale() == PreserveAspectRatio.Scale.Slice)
    {
      f5 = Math.max(f1, f2);
      f6 = paramBox1.width / f5;
      f7 = paramBox1.height / f5;
      switch (1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[paramPreserveAspectRatio.getAlignment().ordinal()])
      {
      default:
        label192: switch (1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[paramPreserveAspectRatio.getAlignment().ordinal()])
        {
        case 4:
        default:
        case 2:
        case 5:
        case 7:
        case 3:
        case 6:
        case 8:
        }
        break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    }
    while (true)
    {
      localMatrix.preTranslate(paramBox1.minX, paramBox1.minY);
      localMatrix.preScale(f5, f5);
      localMatrix.preTranslate(f3, f4);
      return localMatrix;
      f5 = Math.min(f1, f2);
      break;
      f3 -= (paramBox2.width - f6) / 2.0F;
      break label192;
      f3 -= paramBox2.width - f6;
      break label192;
      f4 -= (paramBox2.height - f7) / 2.0F;
      continue;
      f4 -= paramBox2.height - f7;
    }
  }

  private void checkForClipPath(SVG.SvgElement paramSvgElement)
  {
    checkForClipPath(paramSvgElement, paramSvgElement.boundingBox);
  }

  private void checkForClipPath(SVG.SvgElement paramSvgElement, SVG.Box paramBox)
  {
    if (this.state.style.clipPath == null)
      return;
    SVG.SvgObject localSvgObject = paramSvgElement.document.resolveIRI(this.state.style.clipPath);
    if (localSvgObject == null)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.state.style.clipPath;
      error("ClipPath reference '%s' not found", arrayOfObject2);
      return;
    }
    SVG.ClipPath localClipPath = (SVG.ClipPath)localSvgObject;
    if (localClipPath.children.isEmpty())
    {
      this.canvas.clipRect(0, 0, 0, 0);
      return;
    }
    if ((localClipPath.clipPathUnitsAreUser == null) || (localClipPath.clipPathUnitsAreUser.booleanValue()));
    for (int i = 1; ((paramSvgElement instanceof SVG.Group)) && (i == 0); i = 0)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramSvgElement.getClass().getSimpleName();
      warn("<clipPath clipPathUnits=\"objectBoundingBox\"> is not supported when referenced from container elements (like %s)", arrayOfObject1);
      return;
    }
    clipStatePush();
    if (i == 0)
    {
      Matrix localMatrix = new Matrix();
      localMatrix.preTranslate(paramBox.minX, paramBox.minY);
      localMatrix.preScale(paramBox.width, paramBox.height);
      this.canvas.concat(localMatrix);
    }
    if (localClipPath.transform != null)
      this.canvas.concat(localClipPath.transform);
    this.state = findInheritFromAncestorState(localClipPath);
    checkForClipPath(localClipPath);
    Path localPath = new Path();
    Iterator localIterator = localClipPath.children.iterator();
    while (localIterator.hasNext())
      addObjectToClip((SVG.SvgObject)localIterator.next(), true, localPath, new Matrix());
    this.canvas.clipPath(localPath);
    clipStatePop();
  }

  private void checkForGradiantsAndPatterns(SVG.SvgElement paramSvgElement)
  {
    if ((this.state.style.fill instanceof SVG.PaintReference))
      decodePaintReference(true, paramSvgElement.boundingBox, (SVG.PaintReference)this.state.style.fill);
    if ((this.state.style.stroke instanceof SVG.PaintReference))
      decodePaintReference(false, paramSvgElement.boundingBox, (SVG.PaintReference)this.state.style.stroke);
  }

  private Bitmap checkForImageDataURL(String paramString)
  {
    if (!paramString.startsWith("data:"));
    int i;
    do
    {
      do
        return null;
      while (paramString.length() < 14);
      i = paramString.indexOf(',');
    }
    while ((i == -1) || (i < 12) || (!";base64".equals(paramString.substring(i - 7, i))));
    byte[] arrayOfByte = Base64.decode(paramString.substring(i + 1), 0);
    return BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
  }

  private Typeface checkGenericFont(String paramString, Integer paramInteger, SVG.Style.FontStyle paramFontStyle)
  {
    int i;
    int j;
    label28: Typeface localTypeface;
    if (paramFontStyle == SVG.Style.FontStyle.Italic)
    {
      i = 1;
      if (paramInteger.intValue() <= 500)
        break label63;
      if (i == 0)
        break label57;
      j = 3;
      if (!paramString.equals("serif"))
        break label80;
      localTypeface = Typeface.create(Typeface.SERIF, j);
    }
    label57: label63: label80: boolean bool;
    do
    {
      return localTypeface;
      i = 0;
      break;
      j = 1;
      break label28;
      if (i != 0)
      {
        j = 2;
        break label28;
      }
      j = 0;
      break label28;
      if (paramString.equals("sans-serif"))
        return Typeface.create(Typeface.SANS_SERIF, j);
      if (paramString.equals("monospace"))
        return Typeface.create(Typeface.MONOSPACE, j);
      if (paramString.equals("cursive"))
        return Typeface.create(Typeface.SANS_SERIF, j);
      bool = paramString.equals("fantasy");
      localTypeface = null;
    }
    while (!bool);
    return Typeface.create(Typeface.SANS_SERIF, j);
  }

  private void checkXMLSpaceAttribute(SVG.SvgObject paramSvgObject)
  {
    if (!(paramSvgObject instanceof SVG.SvgElementBase));
    SVG.SvgElementBase localSvgElementBase;
    do
    {
      return;
      localSvgElementBase = (SVG.SvgElementBase)paramSvgObject;
    }
    while (localSvgElementBase.spacePreserve == null);
    this.state.spacePreserve = localSvgElementBase.spacePreserve.booleanValue();
  }

  private int clamp255(float paramFloat)
  {
    int i = (int)(256.0F * paramFloat);
    if (i < 0)
      i = 0;
    while (i <= 255)
      return i;
    return 255;
  }

  private void clipStatePop()
  {
    this.canvas.restore();
    this.state = ((RendererState)this.stateStack.pop());
  }

  private void clipStatePush()
  {
    this.canvas.save(1);
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
  }

  private static void debug(String paramString, Object[] paramArrayOfObject)
  {
  }

  private void decodePaintReference(boolean paramBoolean, SVG.Box paramBox, SVG.PaintReference paramPaintReference)
  {
    SVG.SvgObject localSvgObject = this.document.resolveIRI(paramPaintReference.href);
    String str;
    if (localSvgObject == null)
    {
      Object[] arrayOfObject = new Object[2];
      if (paramBoolean)
      {
        str = "Fill";
        arrayOfObject[0] = str;
        arrayOfObject[1] = paramPaintReference.href;
        error("%s reference '%s' not found", arrayOfObject);
        if (paramPaintReference.fallback == null)
          break label84;
        setPaintColour(this.state, paramBoolean, paramPaintReference.fallback);
      }
    }
    label84: 
    do
    {
      return;
      str = "Stroke";
      break;
      if (paramBoolean)
      {
        this.state.hasFill = false;
        return;
      }
      this.state.hasStroke = false;
      return;
      if ((localSvgObject instanceof SVG.SvgLinearGradient))
        makeLinearGradiant(paramBoolean, paramBox, (SVG.SvgLinearGradient)localSvgObject);
      if ((localSvgObject instanceof SVG.SvgRadialGradient))
        makeRadialGradiant(paramBoolean, paramBox, (SVG.SvgRadialGradient)localSvgObject);
    }
    while (!(localSvgObject instanceof SVG.SolidColor));
    setSolidColor(paramBoolean, (SVG.SolidColor)localSvgObject);
  }

  private boolean display()
  {
    if (this.state.style.display != null)
      return this.state.style.display.booleanValue();
    return true;
  }

  private void doFilledPath(SVG.SvgElement paramSvgElement, Path paramPath)
  {
    if ((this.state.style.fill instanceof SVG.PaintReference))
    {
      SVG.SvgObject localSvgObject = this.document.resolveIRI(((SVG.PaintReference)this.state.style.fill).href);
      if ((localSvgObject instanceof SVG.Pattern))
      {
        fillWithPattern(paramSvgElement, paramPath, (SVG.Pattern)localSvgObject);
        return;
      }
    }
    this.canvas.drawPath(paramPath, this.state.fillPaint);
  }

  private void doStroke(Path paramPath)
  {
    if (this.state.style.vectorEffect == SVG.Style.VectorEffect.NonScalingStroke)
    {
      Matrix localMatrix1 = this.canvas.getMatrix();
      Path localPath = new Path();
      paramPath.transform(localMatrix1, localPath);
      this.canvas.setMatrix(new Matrix());
      Shader localShader = this.state.strokePaint.getShader();
      Matrix localMatrix2 = new Matrix();
      if (localShader != null)
      {
        localShader.getLocalMatrix(localMatrix2);
        Matrix localMatrix3 = new Matrix(localMatrix2);
        localMatrix3.postConcat(localMatrix1);
        localShader.setLocalMatrix(localMatrix3);
      }
      this.canvas.drawPath(localPath, this.state.strokePaint);
      this.canvas.setMatrix(localMatrix1);
      if (localShader != null)
        localShader.setLocalMatrix(localMatrix2);
      return;
    }
    this.canvas.drawPath(paramPath, this.state.strokePaint);
  }

  private void duplicateCanvas()
  {
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(this.canvas.getWidth(), this.canvas.getHeight(), Bitmap.Config.ARGB_8888);
      this.bitmapStack.push(localBitmap);
      Canvas localCanvas = new Canvas(localBitmap);
      localCanvas.setMatrix(this.canvas.getMatrix());
      this.canvas = localCanvas;
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      error("Not enough memory to create temporary bitmaps for mask processing", new Object[0]);
      throw localOutOfMemoryError;
    }
  }

  private void enumerateTextSpans(SVG.TextContainer paramTextContainer, TextProcessor paramTextProcessor)
  {
    if (!display())
      return;
    Iterator localIterator = paramTextContainer.children.iterator();
    boolean bool1 = true;
    label21: SVG.SvgObject localSvgObject;
    boolean bool2;
    if (localIterator.hasNext())
    {
      localSvgObject = (SVG.SvgObject)localIterator.next();
      if (!(localSvgObject instanceof SVG.TextSequence))
        break label97;
      String str = ((SVG.TextSequence)localSvgObject).text;
      if (localIterator.hasNext())
        break label91;
      bool2 = true;
      label71: paramTextProcessor.processText(textXMLSpaceTransform(str, bool1, bool2));
    }
    while (true)
    {
      bool1 = false;
      break label21;
      break;
      label91: bool2 = false;
      break label71;
      label97: processTextChild(localSvgObject, paramTextProcessor);
    }
  }

  private static void error(String paramString, Object[] paramArrayOfObject)
  {
    Log.e("SVGAndroidRenderer", String.format(paramString, paramArrayOfObject));
  }

  private void extractRawText(SVG.TextContainer paramTextContainer, StringBuilder paramStringBuilder)
  {
    Iterator localIterator = paramTextContainer.children.iterator();
    boolean bool1 = true;
    if (localIterator.hasNext())
    {
      SVG.SvgObject localSvgObject = (SVG.SvgObject)localIterator.next();
      if ((localSvgObject instanceof SVG.TextContainer))
        extractRawText((SVG.TextContainer)localSvgObject, paramStringBuilder);
      while (!(localSvgObject instanceof SVG.TextSequence))
      {
        bool1 = false;
        break;
      }
      String str = ((SVG.TextSequence)localSvgObject).text;
      if (!localIterator.hasNext());
      for (boolean bool2 = true; ; bool2 = false)
      {
        paramStringBuilder.append(textXMLSpaceTransform(str, bool1, bool2));
        break;
      }
    }
  }

  private void fillInChainedGradientFields(SVG.GradientElement paramGradientElement, String paramString)
  {
    SVG.SvgObject localSvgObject = paramGradientElement.document.resolveIRI(paramString);
    if (localSvgObject == null)
      warn("Gradient reference '%s' not found", new Object[] { paramString });
    while (true)
    {
      return;
      if (!(localSvgObject instanceof SVG.GradientElement))
      {
        error("Gradient href attributes must point to other gradient elements", new Object[0]);
        return;
      }
      if (localSvgObject == paramGradientElement)
      {
        error("Circular reference in gradient href attribute '%s'", new Object[] { paramString });
        return;
      }
      SVG.GradientElement localGradientElement = (SVG.GradientElement)localSvgObject;
      if (paramGradientElement.gradientUnitsAreUser == null)
        paramGradientElement.gradientUnitsAreUser = localGradientElement.gradientUnitsAreUser;
      if (paramGradientElement.gradientTransform == null)
        paramGradientElement.gradientTransform = localGradientElement.gradientTransform;
      if (paramGradientElement.spreadMethod == null)
        paramGradientElement.spreadMethod = localGradientElement.spreadMethod;
      if (paramGradientElement.children.isEmpty())
        paramGradientElement.children = localGradientElement.children;
      try
      {
        if ((paramGradientElement instanceof SVG.SvgLinearGradient))
          fillInChainedGradientFields((SVG.SvgLinearGradient)paramGradientElement, (SVG.SvgLinearGradient)localSvgObject);
        label160: 
        while (localGradientElement.href != null)
        {
          fillInChainedGradientFields(paramGradientElement, localGradientElement.href);
          return;
          fillInChainedGradientFields((SVG.SvgRadialGradient)paramGradientElement, (SVG.SvgRadialGradient)localSvgObject);
        }
      }
      catch (ClassCastException localClassCastException)
      {
        break label160;
      }
    }
  }

  private void fillInChainedGradientFields(SVG.SvgLinearGradient paramSvgLinearGradient1, SVG.SvgLinearGradient paramSvgLinearGradient2)
  {
    if (paramSvgLinearGradient1.x1 == null)
      paramSvgLinearGradient1.x1 = paramSvgLinearGradient2.x1;
    if (paramSvgLinearGradient1.y1 == null)
      paramSvgLinearGradient1.y1 = paramSvgLinearGradient2.y1;
    if (paramSvgLinearGradient1.x2 == null)
      paramSvgLinearGradient1.x2 = paramSvgLinearGradient2.x2;
    if (paramSvgLinearGradient1.y2 == null)
      paramSvgLinearGradient1.y2 = paramSvgLinearGradient2.y2;
  }

  private void fillInChainedGradientFields(SVG.SvgRadialGradient paramSvgRadialGradient1, SVG.SvgRadialGradient paramSvgRadialGradient2)
  {
    if (paramSvgRadialGradient1.cx == null)
      paramSvgRadialGradient1.cx = paramSvgRadialGradient2.cx;
    if (paramSvgRadialGradient1.cy == null)
      paramSvgRadialGradient1.cy = paramSvgRadialGradient2.cy;
    if (paramSvgRadialGradient1.r == null)
      paramSvgRadialGradient1.r = paramSvgRadialGradient2.r;
    if (paramSvgRadialGradient1.fx == null)
      paramSvgRadialGradient1.fx = paramSvgRadialGradient2.fx;
    if (paramSvgRadialGradient1.fy == null)
      paramSvgRadialGradient1.fy = paramSvgRadialGradient2.fy;
  }

  private void fillInChainedPatternFields(SVG.Pattern paramPattern, String paramString)
  {
    SVG.SvgObject localSvgObject = paramPattern.document.resolveIRI(paramString);
    if (localSvgObject == null)
      warn("Pattern reference '%s' not found", new Object[] { paramString });
    SVG.Pattern localPattern;
    do
    {
      return;
      if (!(localSvgObject instanceof SVG.Pattern))
      {
        error("Pattern href attributes must point to other pattern elements", new Object[0]);
        return;
      }
      if (localSvgObject == paramPattern)
      {
        error("Circular reference in pattern href attribute '%s'", new Object[] { paramString });
        return;
      }
      localPattern = (SVG.Pattern)localSvgObject;
      if (paramPattern.patternUnitsAreUser == null)
        paramPattern.patternUnitsAreUser = localPattern.patternUnitsAreUser;
      if (paramPattern.patternContentUnitsAreUser == null)
        paramPattern.patternContentUnitsAreUser = localPattern.patternContentUnitsAreUser;
      if (paramPattern.patternTransform == null)
        paramPattern.patternTransform = localPattern.patternTransform;
      if (paramPattern.x == null)
        paramPattern.x = localPattern.x;
      if (paramPattern.y == null)
        paramPattern.y = localPattern.y;
      if (paramPattern.width == null)
        paramPattern.width = localPattern.width;
      if (paramPattern.height == null)
        paramPattern.height = localPattern.height;
      if (paramPattern.children.isEmpty())
        paramPattern.children = localPattern.children;
      if (paramPattern.viewBox == null)
        paramPattern.viewBox = localPattern.viewBox;
      if (paramPattern.preserveAspectRatio == null)
        paramPattern.preserveAspectRatio = localPattern.preserveAspectRatio;
    }
    while (localPattern.href == null);
    fillInChainedPatternFields(paramPattern, localPattern.href);
  }

  private void fillWithPattern(SVG.SvgElement paramSvgElement, Path paramPath, SVG.Pattern paramPattern)
  {
    int i;
    float f5;
    label58: float f6;
    label75: float f7;
    if ((paramPattern.patternUnitsAreUser != null) && (paramPattern.patternUnitsAreUser.booleanValue()))
    {
      i = 1;
      if (paramPattern.href != null)
        fillInChainedPatternFields(paramPattern, paramPattern.href);
      if (i == 0)
        break label154;
      if (paramPattern.x == null)
        break label130;
      f5 = paramPattern.x.floatValueX(this);
      if (paramPattern.y == null)
        break label136;
      f6 = paramPattern.y.floatValueY(this);
      if (paramPattern.width == null)
        break label142;
      f7 = paramPattern.width.floatValueX(this);
      label92: if (paramPattern.height == null)
        break label148;
    }
    label130: label136: label142: label148: for (float f8 = paramPattern.height.floatValueY(this); ; f8 = 0.0F)
    {
      if ((f7 != 0.0F) && (f8 != 0.0F))
        break label317;
      return;
      i = 0;
      break;
      f5 = 0.0F;
      break label58;
      f6 = 0.0F;
      break label75;
      f7 = 0.0F;
      break label92;
    }
    label154: float f1;
    label172: float f2;
    label190: float f3;
    if (paramPattern.x != null)
    {
      f1 = paramPattern.x.floatValue(this, 1.0F);
      if (paramPattern.y == null)
        break label299;
      f2 = paramPattern.y.floatValue(this, 1.0F);
      if (paramPattern.width == null)
        break label305;
      f3 = paramPattern.width.floatValue(this, 1.0F);
      label208: if (paramPattern.height == null)
        break label311;
    }
    label299: label305: label311: for (float f4 = paramPattern.height.floatValue(this, 1.0F); ; f4 = 0.0F)
    {
      f5 = paramSvgElement.boundingBox.minX + f1 * paramSvgElement.boundingBox.width;
      f6 = paramSvgElement.boundingBox.minY + f2 * paramSvgElement.boundingBox.height;
      f7 = f3 * paramSvgElement.boundingBox.width;
      f8 = f4 * paramSvgElement.boundingBox.height;
      break;
      f1 = 0.0F;
      break label172;
      f2 = 0.0F;
      break label190;
      f3 = 0.0F;
      break label208;
    }
    label317: if (paramPattern.preserveAspectRatio != null);
    RectF localRectF;
    for (PreserveAspectRatio localPreserveAspectRatio = paramPattern.preserveAspectRatio; ; localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX)
    {
      statePush();
      this.canvas.clipPath(paramPath);
      RendererState localRendererState = new RendererState();
      updateStyle(localRendererState, SVG.Style.getDefaultStyle());
      localRendererState.style.overflow = Boolean.valueOf(false);
      this.state = findInheritFromAncestorState(paramPattern, localRendererState);
      localBox1 = paramSvgElement.boundingBox;
      if (paramPattern.patternTransform == null)
        break label743;
      this.canvas.concat(paramPattern.patternTransform);
      Matrix localMatrix = new Matrix();
      if (!paramPattern.patternTransform.invert(localMatrix))
        break label743;
      float[] arrayOfFloat = new float[8];
      arrayOfFloat[0] = paramSvgElement.boundingBox.minX;
      arrayOfFloat[1] = paramSvgElement.boundingBox.minY;
      arrayOfFloat[2] = paramSvgElement.boundingBox.maxX();
      arrayOfFloat[3] = paramSvgElement.boundingBox.minY;
      arrayOfFloat[4] = paramSvgElement.boundingBox.maxX();
      arrayOfFloat[5] = paramSvgElement.boundingBox.maxY();
      arrayOfFloat[6] = paramSvgElement.boundingBox.minX;
      arrayOfFloat[7] = paramSvgElement.boundingBox.maxY();
      localMatrix.mapPoints(arrayOfFloat);
      localRectF = new RectF(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[0], arrayOfFloat[1]);
      for (int k = 2; k <= 6; k += 2)
      {
        if (arrayOfFloat[k] < localRectF.left)
          localRectF.left = arrayOfFloat[k];
        if (arrayOfFloat[k] > localRectF.right)
          localRectF.right = arrayOfFloat[k];
        if (arrayOfFloat[(k + 1)] < localRectF.top)
          localRectF.top = arrayOfFloat[(k + 1)];
        if (arrayOfFloat[(k + 1)] > localRectF.bottom)
          localRectF.bottom = arrayOfFloat[(k + 1)];
      }
    }
    float f15 = localRectF.left;
    float f16 = localRectF.top;
    float f17 = localRectF.right - localRectF.left;
    float f18 = localRectF.bottom - localRectF.top;
    SVG.Box localBox1 = new SVG.Box(f15, f16, f17, f18);
    label743: float f9 = f5 + f7 * (float)Math.floor((localBox1.minX - f5) / f7);
    float f10 = f6 + f8 * (float)Math.floor((localBox1.minY - f6) / f8);
    float f11 = localBox1.maxX();
    float f12 = localBox1.maxY();
    SVG.Box localBox2 = new SVG.Box(0.0F, 0.0F, f7, f8);
    for (float f13 = f10; f13 < f12; f13 += f8)
      for (float f14 = f9; f14 < f11; f14 += f7)
      {
        localBox2.minX = f14;
        localBox2.minY = f13;
        statePush();
        if (!this.state.style.overflow.booleanValue())
          setClipRect(localBox2.minX, localBox2.minY, localBox2.width, localBox2.height);
        boolean bool;
        if (paramPattern.viewBox != null)
        {
          this.canvas.concat(calculateViewBoxTransform(localBox2, paramPattern.viewBox, localPreserveAspectRatio));
          bool = pushLayer();
          Iterator localIterator = paramPattern.children.iterator();
          while (localIterator.hasNext())
            render((SVG.SvgObject)localIterator.next());
        }
        if ((paramPattern.patternContentUnitsAreUser == null) || (paramPattern.patternContentUnitsAreUser.booleanValue()));
        for (int j = 1; ; j = 0)
        {
          this.canvas.translate(f14, f13);
          if (j != 0)
            break;
          this.canvas.scale(paramSvgElement.boundingBox.width, paramSvgElement.boundingBox.height);
          break;
        }
        if (bool)
          popLayer(paramPattern);
        statePop();
      }
    statePop();
  }

  private RendererState findInheritFromAncestorState(SVG.SvgObject paramSvgObject)
  {
    RendererState localRendererState = new RendererState();
    updateStyle(localRendererState, SVG.Style.getDefaultStyle());
    return findInheritFromAncestorState(paramSvgObject, localRendererState);
  }

  private RendererState findInheritFromAncestorState(SVG.SvgObject paramSvgObject, RendererState paramRendererState)
  {
    ArrayList localArrayList = new ArrayList();
    while (true)
    {
      if ((paramSvgObject instanceof SVG.SvgElementBase))
        localArrayList.add(0, (SVG.SvgElementBase)paramSvgObject);
      if (paramSvgObject.parent == null)
      {
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
          updateStyleForElement(paramRendererState, (SVG.SvgElementBase)localIterator.next());
      }
      paramSvgObject = (SVG.SvgObject)paramSvgObject.parent;
    }
    paramRendererState.viewBox = this.document.getRootElement().viewBox;
    if (paramRendererState.viewBox == null)
      paramRendererState.viewBox = this.canvasViewPort;
    paramRendererState.viewPort = this.canvasViewPort;
    paramRendererState.directRendering = this.state.directRendering;
    return paramRendererState;
  }

  private SVG.Style.TextAnchor getAnchorPosition()
  {
    if ((this.state.style.direction == SVG.Style.TextDirection.LTR) || (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle))
      return this.state.style.textAnchor;
    if (this.state.style.textAnchor == SVG.Style.TextAnchor.Start)
      return SVG.Style.TextAnchor.End;
    return SVG.Style.TextAnchor.Start;
  }

  private Path.FillType getClipRuleFromState()
  {
    if (this.state.style.clipRule == null)
      return Path.FillType.WINDING;
    switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$FillRule[this.state.style.clipRule.ordinal()])
    {
    default:
      return Path.FillType.WINDING;
    case 1:
    }
    return Path.FillType.EVEN_ODD;
  }

  private Path.FillType getFillTypeFromState()
  {
    if (this.state.style.fillRule == null)
      return Path.FillType.WINDING;
    switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$FillRule[this.state.style.fillRule.ordinal()])
    {
    default:
      return Path.FillType.WINDING;
    case 1:
    }
    return Path.FillType.EVEN_ODD;
  }

  private static void info(String paramString, Object[] paramArrayOfObject)
  {
    Log.i("SVGAndroidRenderer", String.format(paramString, paramArrayOfObject));
  }

  private static void initialiseSupportedFeaturesMap()
  {
    try
    {
      supportedFeatures = new HashSet();
      supportedFeatures.add("Structure");
      supportedFeatures.add("BasicStructure");
      supportedFeatures.add("ConditionalProcessing");
      supportedFeatures.add("Image");
      supportedFeatures.add("Style");
      supportedFeatures.add("ViewportAttribute");
      supportedFeatures.add("Shape");
      supportedFeatures.add("BasicText");
      supportedFeatures.add("PaintAttribute");
      supportedFeatures.add("BasicPaintAttribute");
      supportedFeatures.add("OpacityAttribute");
      supportedFeatures.add("BasicGraphicsAttribute");
      supportedFeatures.add("Marker");
      supportedFeatures.add("Gradient");
      supportedFeatures.add("Pattern");
      supportedFeatures.add("Clip");
      supportedFeatures.add("BasicClip");
      supportedFeatures.add("Mask");
      supportedFeatures.add("View");
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private boolean isSpecified(SVG.Style paramStyle, long paramLong)
  {
    return (paramLong & paramStyle.specifiedFlags) != 0L;
  }

  private void makeLinearGradiant(boolean paramBoolean, SVG.Box paramBox, SVG.SvgLinearGradient paramSvgLinearGradient)
  {
    if (paramSvgLinearGradient.href != null)
      fillInChainedGradientFields(paramSvgLinearGradient, paramSvgLinearGradient.href);
    int i;
    Paint localPaint;
    label49: SVG.Box localBox;
    float f1;
    label77: float f2;
    label94: float f3;
    if ((paramSvgLinearGradient.gradientUnitsAreUser != null) && (paramSvgLinearGradient.gradientUnitsAreUser.booleanValue()))
    {
      i = 1;
      if (!paramBoolean)
        break label239;
      localPaint = this.state.fillPaint;
      if (i == 0)
        break label279;
      localBox = getCurrentViewPortInUserUnits();
      if (paramSvgLinearGradient.x1 == null)
        break label251;
      f1 = paramSvgLinearGradient.x1.floatValueX(this);
      if (paramSvgLinearGradient.y1 == null)
        break label257;
      f2 = paramSvgLinearGradient.y1.floatValueY(this);
      if (paramSvgLinearGradient.x2 == null)
        break label263;
      f3 = paramSvgLinearGradient.x2.floatValueX(this);
      label111: if (paramSvgLinearGradient.y2 == null)
        break label273;
    }
    Matrix localMatrix;
    int j;
    label257: label263: label273: for (float f4 = paramSvgLinearGradient.y2.floatValueY(this); ; f4 = 0.0F)
    {
      statePush();
      this.state = findInheritFromAncestorState(paramSvgLinearGradient);
      localMatrix = new Matrix();
      if (i == 0)
      {
        localMatrix.preTranslate(paramBox.minX, paramBox.minY);
        localMatrix.preScale(paramBox.width, paramBox.height);
      }
      if (paramSvgLinearGradient.gradientTransform != null)
        localMatrix.preConcat(paramSvgLinearGradient.gradientTransform);
      j = paramSvgLinearGradient.children.size();
      if (j != 0)
        break label387;
      statePop();
      if (!paramBoolean)
        break label378;
      this.state.hasFill = false;
      return;
      i = 0;
      break;
      label239: localPaint = this.state.strokePaint;
      break label49;
      label251: f1 = 0.0F;
      break label77;
      f2 = 0.0F;
      break label94;
      f3 = localBox.width;
      break label111;
    }
    label279: if (paramSvgLinearGradient.x1 != null)
    {
      f1 = paramSvgLinearGradient.x1.floatValue(this, 1.0F);
      label297: if (paramSvgLinearGradient.y1 == null)
        break label360;
      f2 = paramSvgLinearGradient.y1.floatValue(this, 1.0F);
      label315: if (paramSvgLinearGradient.x2 == null)
        break label366;
      f3 = paramSvgLinearGradient.x2.floatValue(this, 1.0F);
      label333: if (paramSvgLinearGradient.y2 == null)
        break label372;
    }
    label360: label366: label372: for (f4 = paramSvgLinearGradient.y2.floatValue(this, 1.0F); ; f4 = 0.0F)
    {
      break;
      f1 = 0.0F;
      break label297;
      f2 = 0.0F;
      break label315;
      f3 = 1.0F;
      break label333;
    }
    label378: this.state.hasStroke = false;
    return;
    label387: int[] arrayOfInt = new int[j];
    float[] arrayOfFloat = new float[j];
    int k = 0;
    float f5 = -1.0F;
    Iterator localIterator = paramSvgLinearGradient.children.iterator();
    if (localIterator.hasNext())
    {
      SVG.Stop localStop = (SVG.Stop)localIterator.next();
      if ((k == 0) || (localStop.offset.floatValue() >= f5))
      {
        arrayOfFloat[k] = localStop.offset.floatValue();
        f5 = localStop.offset.floatValue();
      }
      while (true)
      {
        statePush();
        updateStyleForElement(this.state, localStop);
        SVG.Colour localColour = (SVG.Colour)this.state.style.stopColor;
        if (localColour == null)
          localColour = SVG.Colour.BLACK;
        arrayOfInt[k] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24 | localColour.colour);
        k++;
        statePop();
        break;
        arrayOfFloat[k] = f5;
      }
    }
    if (((f1 == f3) && (f2 == f4)) || (j == 1))
    {
      statePop();
      int m = arrayOfInt[(j - 1)];
      localPaint.setColor(m);
      return;
    }
    Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
    if (paramSvgLinearGradient.spreadMethod != null)
    {
      if (paramSvgLinearGradient.spreadMethod != SVG.GradientSpread.reflect)
        break label688;
      localTileMode = Shader.TileMode.MIRROR;
    }
    while (true)
    {
      statePop();
      LinearGradient localLinearGradient = new LinearGradient(f1, f2, f3, f4, arrayOfInt, arrayOfFloat, localTileMode);
      localLinearGradient.setLocalMatrix(localMatrix);
      localPaint.setShader(localLinearGradient);
      return;
      label688: if (paramSvgLinearGradient.spreadMethod == SVG.GradientSpread.repeat)
        localTileMode = Shader.TileMode.REPEAT;
    }
  }

  private Path makePathAndBoundingBox(SVG.Circle paramCircle)
  {
    float f1;
    if (paramCircle.cx != null)
    {
      f1 = paramCircle.cx.floatValueX(this);
      if (paramCircle.cy == null)
        break label213;
    }
    label213: for (float f2 = paramCircle.cy.floatValueY(this); ; f2 = 0.0F)
    {
      float f3 = paramCircle.r.floatValue(this);
      float f4 = f1 - f3;
      float f5 = f2 - f3;
      float f6 = f1 + f3;
      float f7 = f2 + f3;
      if (paramCircle.boundingBox == null)
        paramCircle.boundingBox = new SVG.Box(f4, f5, 2.0F * f3, 2.0F * f3);
      float f8 = f3 * 0.5522848F;
      Path localPath = new Path();
      localPath.moveTo(f1, f5);
      localPath.cubicTo(f1 + f8, f5, f6, f2 - f8, f6, f2);
      localPath.cubicTo(f6, f2 + f8, f1 + f8, f7, f1, f7);
      localPath.cubicTo(f1 - f8, f7, f4, f2 + f8, f4, f2);
      localPath.cubicTo(f4, f2 - f8, f1 - f8, f5, f1, f5);
      localPath.close();
      return localPath;
      f1 = 0.0F;
      break;
    }
  }

  private Path makePathAndBoundingBox(SVG.Ellipse paramEllipse)
  {
    float f1;
    if (paramEllipse.cx != null)
    {
      f1 = paramEllipse.cx.floatValueX(this);
      if (paramEllipse.cy == null)
        break label230;
    }
    label230: for (float f2 = paramEllipse.cy.floatValueY(this); ; f2 = 0.0F)
    {
      float f3 = paramEllipse.rx.floatValueX(this);
      float f4 = paramEllipse.ry.floatValueY(this);
      float f5 = f1 - f3;
      float f6 = f2 - f4;
      float f7 = f1 + f3;
      float f8 = f2 + f4;
      if (paramEllipse.boundingBox == null)
        paramEllipse.boundingBox = new SVG.Box(f5, f6, 2.0F * f3, 2.0F * f4);
      float f9 = f3 * 0.5522848F;
      float f10 = f4 * 0.5522848F;
      Path localPath = new Path();
      localPath.moveTo(f1, f6);
      localPath.cubicTo(f1 + f9, f6, f7, f2 - f10, f7, f2);
      localPath.cubicTo(f7, f2 + f10, f1 + f9, f8, f1, f8);
      localPath.cubicTo(f1 - f9, f8, f5, f2 + f10, f5, f2);
      localPath.cubicTo(f5, f2 - f10, f1 - f9, f6, f1, f6);
      localPath.close();
      return localPath;
      f1 = 0.0F;
      break;
    }
  }

  private Path makePathAndBoundingBox(SVG.Line paramLine)
  {
    float f1;
    float f2;
    label18: float f3;
    if (paramLine.x1 == null)
    {
      f1 = 0.0F;
      if (paramLine.y1 != null)
        break label121;
      f2 = 0.0F;
      if (paramLine.x2 != null)
        break label133;
      f3 = 0.0F;
      label28: if (paramLine.y2 != null)
        break label146;
    }
    label133: label146: for (float f4 = 0.0F; ; f4 = paramLine.y2.floatValueY(this))
    {
      if (paramLine.boundingBox == null)
        paramLine.boundingBox = new SVG.Box(Math.min(f1, f2), Math.min(f2, f4), Math.abs(f3 - f1), Math.abs(f4 - f2));
      Path localPath = new Path();
      localPath.moveTo(f1, f2);
      localPath.lineTo(f3, f4);
      return localPath;
      f1 = paramLine.x1.floatValueX(this);
      break;
      label121: f2 = paramLine.y1.floatValueY(this);
      break label18;
      f3 = paramLine.x2.floatValueX(this);
      break label28;
    }
  }

  private Path makePathAndBoundingBox(SVG.PolyLine paramPolyLine)
  {
    Path localPath = new Path();
    localPath.moveTo(paramPolyLine.points[0], paramPolyLine.points[1]);
    for (int i = 2; i < paramPolyLine.points.length; i += 2)
      localPath.lineTo(paramPolyLine.points[i], paramPolyLine.points[(i + 1)]);
    if ((paramPolyLine instanceof SVG.Polygon))
      localPath.close();
    if (paramPolyLine.boundingBox == null)
      paramPolyLine.boundingBox = calculatePathBounds(localPath);
    localPath.setFillType(getClipRuleFromState());
    return localPath;
  }

  private Path makePathAndBoundingBox(SVG.Rect paramRect)
  {
    float f1;
    float f2;
    float f4;
    float f6;
    float f7;
    label75: float f8;
    label92: float f11;
    float f12;
    Path localPath;
    if ((paramRect.rx == null) && (paramRect.ry == null))
    {
      f1 = 0.0F;
      f2 = 0.0F;
      float f3 = paramRect.width.floatValueX(this) / 2.0F;
      f4 = Math.min(f1, f3);
      float f5 = paramRect.height.floatValueY(this) / 2.0F;
      f6 = Math.min(f2, f5);
      if (paramRect.x == null)
        break label291;
      f7 = paramRect.x.floatValueX(this);
      if (paramRect.y == null)
        break label297;
      f8 = paramRect.y.floatValueY(this);
      float f9 = paramRect.width.floatValueX(this);
      float f10 = paramRect.height.floatValueY(this);
      if (paramRect.boundingBox == null)
        paramRect.boundingBox = new SVG.Box(f7, f8, f9, f10);
      f11 = f7 + f9;
      f12 = f8 + f10;
      localPath = new Path();
      if ((f4 != 0.0F) && (f6 != 0.0F))
        break label303;
      localPath.moveTo(f7, f8);
      localPath.lineTo(f11, f8);
      localPath.lineTo(f11, f12);
      localPath.lineTo(f7, f12);
      localPath.lineTo(f7, f8);
    }
    while (true)
    {
      localPath.close();
      return localPath;
      if (paramRect.rx == null)
      {
        f2 = paramRect.ry.floatValueY(this);
        f1 = f2;
        break;
      }
      if (paramRect.ry == null)
      {
        f2 = paramRect.rx.floatValueX(this);
        f1 = f2;
        break;
      }
      f1 = paramRect.rx.floatValueX(this);
      f2 = paramRect.ry.floatValueY(this);
      break;
      label291: f7 = 0.0F;
      break label75;
      label297: f8 = 0.0F;
      break label92;
      label303: float f13 = f4 * 0.5522848F;
      float f14 = f6 * 0.5522848F;
      localPath.moveTo(f7, f8 + f6);
      localPath.cubicTo(f7, f8 + f6 - f14, f7 + f4 - f13, f8, f7 + f4, f8);
      localPath.lineTo(f11 - f4, f8);
      localPath.cubicTo(f13 + (f11 - f4), f8, f11, f8 + f6 - f14, f11, f8 + f6);
      localPath.lineTo(f11, f12 - f6);
      localPath.cubicTo(f11, f14 + (f12 - f6), f13 + (f11 - f4), f12, f11 - f4, f12);
      localPath.lineTo(f7 + f4, f12);
      float f15 = f7 + f4 - f13;
      float f16 = f14 + (f12 - f6);
      float f17 = f12 - f6;
      localPath.cubicTo(f15, f12, f7, f16, f7, f17);
      localPath.lineTo(f7, f8 + f6);
    }
  }

  private void makeRadialGradiant(boolean paramBoolean, SVG.Box paramBox, SVG.SvgRadialGradient paramSvgRadialGradient)
  {
    if (paramSvgRadialGradient.href != null)
      fillInChainedGradientFields(paramSvgRadialGradient, paramSvgRadialGradient.href);
    int i;
    Paint localPaint;
    label49: SVG.Length localLength;
    float f1;
    label86: float f2;
    if ((paramSvgRadialGradient.gradientUnitsAreUser != null) && (paramSvgRadialGradient.gradientUnitsAreUser.booleanValue()))
    {
      i = 1;
      if (!paramBoolean)
        break label231;
      localPaint = this.state.fillPaint;
      if (i == 0)
        break label276;
      localLength = new SVG.Length(50.0F, SVG.Unit.percent);
      if (paramSvgRadialGradient.cx == null)
        break label243;
      f1 = paramSvgRadialGradient.cx.floatValueX(this);
      if (paramSvgRadialGradient.cy == null)
        break label254;
      f2 = paramSvgRadialGradient.cy.floatValueY(this);
      label103: if (paramSvgRadialGradient.r == null)
        break label265;
    }
    Matrix localMatrix;
    int j;
    label265: for (float f3 = paramSvgRadialGradient.r.floatValue(this); ; f3 = localLength.floatValue(this))
    {
      statePush();
      this.state = findInheritFromAncestorState(paramSvgRadialGradient);
      localMatrix = new Matrix();
      if (i == 0)
      {
        localMatrix.preTranslate(paramBox.minX, paramBox.minY);
        localMatrix.preScale(paramBox.width, paramBox.height);
      }
      if (paramSvgRadialGradient.gradientTransform != null)
        localMatrix.preConcat(paramSvgRadialGradient.gradientTransform);
      j = paramSvgRadialGradient.children.size();
      if (j != 0)
        break label366;
      statePop();
      if (!paramBoolean)
        break label357;
      this.state.hasFill = false;
      return;
      i = 0;
      break;
      label231: localPaint = this.state.strokePaint;
      break label49;
      label243: f1 = localLength.floatValueX(this);
      break label86;
      label254: f2 = localLength.floatValueY(this);
      break label103;
    }
    label276: if (paramSvgRadialGradient.cx != null)
    {
      f1 = paramSvgRadialGradient.cx.floatValue(this, 1.0F);
      label294: if (paramSvgRadialGradient.cy == null)
        break label341;
      f2 = paramSvgRadialGradient.cy.floatValue(this, 1.0F);
      label312: if (paramSvgRadialGradient.r == null)
        break label349;
    }
    label341: label349: for (f3 = paramSvgRadialGradient.r.floatValue(this, 1.0F); ; f3 = 0.5F)
    {
      break;
      f1 = 0.5F;
      break label294;
      f2 = 0.5F;
      break label312;
    }
    label357: this.state.hasStroke = false;
    return;
    label366: int[] arrayOfInt = new int[j];
    float[] arrayOfFloat = new float[j];
    int k = 0;
    float f4 = -1.0F;
    Iterator localIterator = paramSvgRadialGradient.children.iterator();
    if (localIterator.hasNext())
    {
      SVG.Stop localStop = (SVG.Stop)localIterator.next();
      if ((k == 0) || (localStop.offset.floatValue() >= f4))
      {
        arrayOfFloat[k] = localStop.offset.floatValue();
        f4 = localStop.offset.floatValue();
      }
      while (true)
      {
        statePush();
        updateStyleForElement(this.state, localStop);
        SVG.Colour localColour = (SVG.Colour)this.state.style.stopColor;
        if (localColour == null)
          localColour = SVG.Colour.BLACK;
        arrayOfInt[k] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24 | localColour.colour);
        k++;
        statePop();
        break;
        arrayOfFloat[k] = f4;
      }
    }
    if ((f3 == 0.0F) || (j == 1))
    {
      statePop();
      int m = arrayOfInt[(j - 1)];
      localPaint.setColor(m);
      return;
    }
    Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
    if (paramSvgRadialGradient.spreadMethod != null)
    {
      if (paramSvgRadialGradient.spreadMethod != SVG.GradientSpread.reflect)
        break label656;
      localTileMode = Shader.TileMode.MIRROR;
    }
    while (true)
    {
      statePop();
      RadialGradient localRadialGradient = new RadialGradient(f1, f2, f3, arrayOfInt, arrayOfFloat, localTileMode);
      localRadialGradient.setLocalMatrix(localMatrix);
      localPaint.setShader(localRadialGradient);
      return;
      label656: if (paramSvgRadialGradient.spreadMethod == SVG.GradientSpread.repeat)
        localTileMode = Shader.TileMode.REPEAT;
    }
  }

  private void parentPop()
  {
    this.parentStack.pop();
    this.matrixStack.pop();
  }

  private void parentPush(SVG.SvgContainer paramSvgContainer)
  {
    this.parentStack.push(paramSvgContainer);
    this.matrixStack.push(this.canvas.getMatrix());
  }

  private void popLayer(SVG.SvgElement paramSvgElement)
  {
    if ((this.state.style.mask != null) && (this.state.directRendering))
    {
      SVG.SvgObject localSvgObject = this.document.resolveIRI(this.state.style.mask);
      duplicateCanvas();
      renderMask((SVG.Mask)localSvgObject, paramSvgElement);
      Bitmap localBitmap = processMaskBitmaps();
      this.canvas = ((Canvas)this.canvasStack.pop());
      this.canvas.save();
      this.canvas.setMatrix(new Matrix());
      this.canvas.drawBitmap(localBitmap, 0.0F, 0.0F, this.state.fillPaint);
      localBitmap.recycle();
      this.canvas.restore();
    }
    statePop();
  }

  private Bitmap processMaskBitmaps()
  {
    Bitmap localBitmap1 = (Bitmap)this.bitmapStack.pop();
    Bitmap localBitmap2 = (Bitmap)this.bitmapStack.pop();
    int i = localBitmap1.getWidth();
    int j = localBitmap1.getHeight();
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    for (int k = 0; k < j; k++)
    {
      localBitmap1.getPixels(arrayOfInt1, 0, i, 0, k, i, 1);
      localBitmap2.getPixels(arrayOfInt2, 0, i, 0, k, i, 1);
      int m = 0;
      if (m < i)
      {
        int n = arrayOfInt1[m];
        int i1 = n & 0xFF;
        int i2 = 0xFF & n >> 8;
        int i3 = 0xFF & n >> 16;
        int i4 = 0xFF & n >> 24;
        if (i4 == 0)
          arrayOfInt2[m] = 0;
        while (true)
        {
          m++;
          break;
          int i5 = i4 * (i3 * 6963 + i2 * 23442 + i1 * 2362) / 8355840;
          int i6 = arrayOfInt2[m];
          int i7 = i5 * (0xFF & i6 >> 24) / 255;
          arrayOfInt2[m] = (0xFFFFFF & i6 | i7 << 24);
        }
      }
      localBitmap2.setPixels(arrayOfInt2, 0, i, 0, k, i, 1);
    }
    localBitmap1.recycle();
    return localBitmap2;
  }

  private void processTextChild(SVG.SvgObject paramSvgObject, TextProcessor paramTextProcessor)
  {
    if (!paramTextProcessor.doTextContainer((SVG.TextContainer)paramSvgObject));
    label163: label187: label329: label352: 
    do
    {
      return;
      if ((paramSvgObject instanceof SVG.TextPath))
      {
        statePush();
        renderTextPath((SVG.TextPath)paramSvgObject);
        statePop();
        return;
      }
      if ((paramSvgObject instanceof SVG.TSpan))
      {
        debug("TSpan render", new Object[0]);
        statePush();
        SVG.TSpan localTSpan = (SVG.TSpan)paramSvgObject;
        updateStyleForElement(this.state, localTSpan);
        float f1;
        float f3;
        float f4;
        if (display())
        {
          boolean bool1 = paramTextProcessor instanceof PlainTextDrawer;
          f1 = 0.0F;
          f2 = 0.0F;
          f3 = 0.0F;
          f4 = 0.0F;
          if (bool1)
          {
            if ((localTSpan.x != null) && (localTSpan.x.size() != 0))
              break label283;
            f3 = ((PlainTextDrawer)paramTextProcessor).x;
            if ((localTSpan.y != null) && (localTSpan.y.size() != 0))
              break label306;
            f4 = ((PlainTextDrawer)paramTextProcessor).y;
            if ((localTSpan.dx != null) && (localTSpan.dx.size() != 0))
              break label329;
            f1 = 0.0F;
            if ((localTSpan.dy != null) && (localTSpan.dy.size() != 0))
              break label352;
          }
        }
        for (float f2 = 0.0F; ; f2 = ((SVG.Length)localTSpan.dy.get(0)).floatValueY(this))
        {
          checkForGradiantsAndPatterns((SVG.SvgElement)localTSpan.getTextRoot());
          if ((paramTextProcessor instanceof PlainTextDrawer))
          {
            ((PlainTextDrawer)paramTextProcessor).x = (f3 + f1);
            ((PlainTextDrawer)paramTextProcessor).y = (f4 + f2);
          }
          boolean bool2 = pushLayer();
          enumerateTextSpans(localTSpan, paramTextProcessor);
          if (bool2)
            popLayer(localTSpan);
          statePop();
          return;
          f3 = ((SVG.Length)localTSpan.x.get(0)).floatValueX(this);
          break;
          f4 = ((SVG.Length)localTSpan.y.get(0)).floatValueY(this);
          break label163;
          f1 = ((SVG.Length)localTSpan.dx.get(0)).floatValueX(this);
          break label187;
        }
      }
    }
    while (!(paramSvgObject instanceof SVG.TRef));
    label283: label306: statePush();
    SVG.TRef localTRef = (SVG.TRef)paramSvgObject;
    updateStyleForElement(this.state, localTRef);
    if (display())
    {
      checkForGradiantsAndPatterns((SVG.SvgElement)localTRef.getTextRoot());
      SVG.SvgObject localSvgObject = paramSvgObject.document.resolveIRI(localTRef.href);
      if ((localSvgObject == null) || (!(localSvgObject instanceof SVG.TextContainer)))
        break label486;
      StringBuilder localStringBuilder = new StringBuilder();
      extractRawText((SVG.TextContainer)localSvgObject, localStringBuilder);
      if (localStringBuilder.length() > 0)
        paramTextProcessor.processText(localStringBuilder.toString());
    }
    while (true)
    {
      statePop();
      return;
      label486: Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localTRef.href;
      error("Tref reference '%s' not found", arrayOfObject);
    }
  }

  private boolean pushLayer()
  {
    if (!requiresCompositing())
      return false;
    this.canvas.saveLayerAlpha(null, clamp255(this.state.style.opacity.floatValue()), 4);
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
    if ((this.state.style.mask != null) && (this.state.directRendering))
    {
      SVG.SvgObject localSvgObject = this.document.resolveIRI(this.state.style.mask);
      if ((localSvgObject == null) || (!(localSvgObject instanceof SVG.Mask)))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.state.style.mask;
        error("Mask reference '%s' not found", arrayOfObject);
        this.state.style.mask = null;
        return true;
      }
      this.canvasStack.push(this.canvas);
      duplicateCanvas();
    }
    return true;
  }

  private void render(SVG.Circle paramCircle)
  {
    debug("Circle render", new Object[0]);
    if ((paramCircle.r == null) || (paramCircle.r.isZero()));
    boolean bool;
    do
    {
      do
      {
        return;
        updateStyleForElement(this.state, paramCircle);
      }
      while ((!display()) || (!visible()));
      if (paramCircle.transform != null)
        this.canvas.concat(paramCircle.transform);
      Path localPath = makePathAndBoundingBox(paramCircle);
      updateParentBoundingBox(paramCircle);
      checkForGradiantsAndPatterns(paramCircle);
      checkForClipPath(paramCircle);
      bool = pushLayer();
      if (this.state.hasFill)
        doFilledPath(paramCircle, localPath);
      if (this.state.hasStroke)
        doStroke(localPath);
    }
    while (!bool);
    popLayer(paramCircle);
  }

  private void render(SVG.Ellipse paramEllipse)
  {
    debug("Ellipse render", new Object[0]);
    if ((paramEllipse.rx == null) || (paramEllipse.ry == null) || (paramEllipse.rx.isZero()) || (paramEllipse.ry.isZero()));
    boolean bool;
    do
    {
      do
      {
        return;
        updateStyleForElement(this.state, paramEllipse);
      }
      while ((!display()) || (!visible()));
      if (paramEllipse.transform != null)
        this.canvas.concat(paramEllipse.transform);
      Path localPath = makePathAndBoundingBox(paramEllipse);
      updateParentBoundingBox(paramEllipse);
      checkForGradiantsAndPatterns(paramEllipse);
      checkForClipPath(paramEllipse);
      bool = pushLayer();
      if (this.state.hasFill)
        doFilledPath(paramEllipse, localPath);
      if (this.state.hasStroke)
        doStroke(localPath);
    }
    while (!bool);
    popLayer(paramEllipse);
  }

  private void render(SVG.Group paramGroup)
  {
    debug("Group render", new Object[0]);
    updateStyleForElement(this.state, paramGroup);
    if (!display())
      return;
    if (paramGroup.transform != null)
      this.canvas.concat(paramGroup.transform);
    checkForClipPath(paramGroup);
    boolean bool = pushLayer();
    renderChildren(paramGroup, true);
    if (bool)
      popLayer(paramGroup);
    updateParentBoundingBox(paramGroup);
  }

  private void render(SVG.Image paramImage)
  {
    debug("Image render", new Object[0]);
    if ((paramImage.width == null) || (paramImage.width.isZero()) || (paramImage.height == null) || (paramImage.height.isZero()));
    label44: PreserveAspectRatio localPreserveAspectRatio;
    Bitmap localBitmap;
    label135: 
    do
    {
      break label44;
      do
        return;
      while (paramImage.href == null);
      if (paramImage.preserveAspectRatio != null);
      for (localPreserveAspectRatio = paramImage.preserveAspectRatio; ; localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX)
      {
        localBitmap = checkForImageDataURL(paramImage.href);
        if (localBitmap == null)
        {
          SVGExternalFileResolver localSVGExternalFileResolver = this.document.getFileResolver();
          if (localSVGExternalFileResolver == null)
            break;
          localBitmap = localSVGExternalFileResolver.resolveImage(paramImage.href);
        }
        if (localBitmap != null)
          break label135;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramImage.href;
        error("Could not locate image '%s'", arrayOfObject);
        return;
      }
      updateStyleForElement(this.state, paramImage);
    }
    while ((!display()) || (!visible()));
    if (paramImage.transform != null)
      this.canvas.concat(paramImage.transform);
    float f1;
    if (paramImage.x != null)
    {
      f1 = paramImage.x.floatValueX(this);
      label193: if (paramImage.y == null)
        break label412;
    }
    label412: for (float f2 = paramImage.y.floatValueY(this); ; f2 = 0.0F)
    {
      float f3 = paramImage.width.floatValueX(this);
      float f4 = paramImage.height.floatValueX(this);
      this.state.viewPort = new SVG.Box(f1, f2, f3, f4);
      if (!this.state.style.overflow.booleanValue())
        setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
      paramImage.boundingBox = new SVG.Box(0.0F, 0.0F, localBitmap.getWidth(), localBitmap.getHeight());
      this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, paramImage.boundingBox, localPreserveAspectRatio));
      updateParentBoundingBox(paramImage);
      checkForClipPath(paramImage);
      boolean bool = pushLayer();
      viewportFill();
      this.canvas.drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
      if (!bool)
        break;
      popLayer(paramImage);
      return;
      f1 = 0.0F;
      break label193;
    }
  }

  private void render(SVG.Line paramLine)
  {
    debug("Line render", new Object[0]);
    updateStyleForElement(this.state, paramLine);
    if (!display());
    boolean bool;
    do
    {
      do
        return;
      while ((!visible()) || (!this.state.hasStroke));
      if (paramLine.transform != null)
        this.canvas.concat(paramLine.transform);
      Path localPath = makePathAndBoundingBox(paramLine);
      updateParentBoundingBox(paramLine);
      checkForGradiantsAndPatterns(paramLine);
      checkForClipPath(paramLine);
      bool = pushLayer();
      doStroke(localPath);
      renderMarkers(paramLine);
    }
    while (!bool);
    popLayer(paramLine);
  }

  private void render(SVG.Path paramPath)
  {
    debug("Path render", new Object[0]);
    if (paramPath.d == null);
    boolean bool;
    do
    {
      do
      {
        return;
        updateStyleForElement(this.state, paramPath);
      }
      while ((!display()) || (!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill)));
      if (paramPath.transform != null)
        this.canvas.concat(paramPath.transform);
      Path localPath = new PathConverter(paramPath.d).getPath();
      if (paramPath.boundingBox == null)
        paramPath.boundingBox = calculatePathBounds(localPath);
      updateParentBoundingBox(paramPath);
      checkForGradiantsAndPatterns(paramPath);
      checkForClipPath(paramPath);
      bool = pushLayer();
      if (this.state.hasFill)
      {
        localPath.setFillType(getFillTypeFromState());
        doFilledPath(paramPath, localPath);
      }
      if (this.state.hasStroke)
        doStroke(localPath);
      renderMarkers(paramPath);
    }
    while (!bool);
    popLayer(paramPath);
  }

  private void render(SVG.PolyLine paramPolyLine)
  {
    debug("PolyLine render", new Object[0]);
    updateStyleForElement(this.state, paramPolyLine);
    if (!display());
    boolean bool;
    do
    {
      do
      {
        do
          return;
        while ((!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill)));
        if (paramPolyLine.transform != null)
          this.canvas.concat(paramPolyLine.transform);
      }
      while (paramPolyLine.points.length < 2);
      Path localPath = makePathAndBoundingBox(paramPolyLine);
      updateParentBoundingBox(paramPolyLine);
      checkForGradiantsAndPatterns(paramPolyLine);
      checkForClipPath(paramPolyLine);
      bool = pushLayer();
      if (this.state.hasFill)
        doFilledPath(paramPolyLine, localPath);
      if (this.state.hasStroke)
        doStroke(localPath);
      renderMarkers(paramPolyLine);
    }
    while (!bool);
    popLayer(paramPolyLine);
  }

  private void render(SVG.Polygon paramPolygon)
  {
    debug("Polygon render", new Object[0]);
    updateStyleForElement(this.state, paramPolygon);
    if (!display());
    boolean bool;
    do
    {
      do
      {
        do
          return;
        while ((!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill)));
        if (paramPolygon.transform != null)
          this.canvas.concat(paramPolygon.transform);
      }
      while (paramPolygon.points.length < 2);
      Path localPath = makePathAndBoundingBox(paramPolygon);
      updateParentBoundingBox(paramPolygon);
      checkForGradiantsAndPatterns(paramPolygon);
      checkForClipPath(paramPolygon);
      bool = pushLayer();
      if (this.state.hasFill)
        doFilledPath(paramPolygon, localPath);
      if (this.state.hasStroke)
        doStroke(localPath);
      renderMarkers(paramPolygon);
    }
    while (!bool);
    popLayer(paramPolygon);
  }

  private void render(SVG.Rect paramRect)
  {
    debug("Rect render", new Object[0]);
    if ((paramRect.width == null) || (paramRect.height == null) || (paramRect.width.isZero()) || (paramRect.height.isZero()));
    boolean bool;
    do
    {
      do
      {
        return;
        updateStyleForElement(this.state, paramRect);
      }
      while ((!display()) || (!visible()));
      if (paramRect.transform != null)
        this.canvas.concat(paramRect.transform);
      Path localPath = makePathAndBoundingBox(paramRect);
      updateParentBoundingBox(paramRect);
      checkForGradiantsAndPatterns(paramRect);
      checkForClipPath(paramRect);
      bool = pushLayer();
      if (this.state.hasFill)
        doFilledPath(paramRect, localPath);
      if (this.state.hasStroke)
        doStroke(localPath);
    }
    while (!bool);
    popLayer(paramRect);
  }

  private void render(SVG.Svg paramSvg)
  {
    render(paramSvg, paramSvg.width, paramSvg.height);
  }

  private void render(SVG.Svg paramSvg, SVG.Length paramLength1, SVG.Length paramLength2)
  {
    render(paramSvg, paramLength1, paramLength2, paramSvg.viewBox, paramSvg.preserveAspectRatio);
  }

  private void render(SVG.Svg paramSvg, SVG.Length paramLength1, SVG.Length paramLength2, SVG.Box paramBox, PreserveAspectRatio paramPreserveAspectRatio)
  {
    debug("Svg render", new Object[0]);
    if (((paramLength1 != null) && (paramLength1.isZero())) || ((paramLength2 != null) && (paramLength2.isZero())));
    do
    {
      return;
      if (paramPreserveAspectRatio == null)
      {
        if (paramSvg.preserveAspectRatio == null)
          break;
        paramPreserveAspectRatio = paramSvg.preserveAspectRatio;
      }
      updateStyleForElement(this.state, paramSvg);
    }
    while (!display());
    SVG.SvgContainer localSvgContainer = paramSvg.parent;
    float f1 = 0.0F;
    float f2 = 0.0F;
    label101: label118: SVG.Box localBox;
    float f3;
    label135: float f4;
    if (localSvgContainer != null)
    {
      if (paramSvg.x != null)
      {
        f1 = paramSvg.x.floatValueX(this);
        if (paramSvg.y == null)
          break label324;
        f2 = paramSvg.y.floatValueY(this);
      }
    }
    else
    {
      localBox = getCurrentViewPortInUserUnits();
      if (paramLength1 == null)
        break label330;
      f3 = paramLength1.floatValueX(this);
      if (paramLength2 == null)
        break label340;
      f4 = paramLength2.floatValueY(this);
      label146: this.state.viewPort = new SVG.Box(f1, f2, f3, f4);
      if (!this.state.style.overflow.booleanValue())
        setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
      checkForClipPath(paramSvg, this.state.viewPort);
      if (paramBox == null)
        break label350;
      this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, paramBox, paramPreserveAspectRatio));
      this.state.viewBox = paramSvg.viewBox;
    }
    while (true)
    {
      boolean bool = pushLayer();
      viewportFill();
      renderChildren(paramSvg, true);
      if (bool)
        popLayer(paramSvg);
      updateParentBoundingBox(paramSvg);
      return;
      paramPreserveAspectRatio = PreserveAspectRatio.LETTERBOX;
      break;
      f1 = 0.0F;
      break label101;
      label324: f2 = 0.0F;
      break label118;
      label330: f3 = localBox.width;
      break label135;
      label340: f4 = localBox.height;
      break label146;
      label350: this.canvas.translate(f1, f2);
    }
  }

  private void render(SVG.SvgObject paramSvgObject)
  {
    if ((paramSvgObject instanceof SVG.NotDirectlyRendered))
      return;
    statePush();
    checkXMLSpaceAttribute(paramSvgObject);
    if ((paramSvgObject instanceof SVG.Svg))
      render((SVG.Svg)paramSvgObject);
    while (true)
    {
      statePop();
      return;
      if ((paramSvgObject instanceof SVG.Use))
        render((SVG.Use)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Switch))
        render((SVG.Switch)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Group))
        render((SVG.Group)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Image))
        render((SVG.Image)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Path))
        render((SVG.Path)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Rect))
        render((SVG.Rect)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Circle))
        render((SVG.Circle)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Ellipse))
        render((SVG.Ellipse)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Line))
        render((SVG.Line)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Polygon))
        render((SVG.Polygon)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.PolyLine))
        render((SVG.PolyLine)paramSvgObject);
      else if ((paramSvgObject instanceof SVG.Text))
        render((SVG.Text)paramSvgObject);
    }
  }

  private void render(SVG.Switch paramSwitch)
  {
    debug("Switch render", new Object[0]);
    updateStyleForElement(this.state, paramSwitch);
    if (!display())
      return;
    if (paramSwitch.transform != null)
      this.canvas.concat(paramSwitch.transform);
    checkForClipPath(paramSwitch);
    boolean bool = pushLayer();
    renderSwitchChild(paramSwitch);
    if (bool)
      popLayer(paramSwitch);
    updateParentBoundingBox(paramSwitch);
  }

  private void render(SVG.Symbol paramSymbol, SVG.Length paramLength1, SVG.Length paramLength2)
  {
    debug("Symbol render", new Object[0]);
    if (((paramLength1 != null) && (paramLength1.isZero())) || ((paramLength2 != null) && (paramLength2.isZero())))
      return;
    PreserveAspectRatio localPreserveAspectRatio;
    float f1;
    if (paramSymbol.preserveAspectRatio != null)
    {
      localPreserveAspectRatio = paramSymbol.preserveAspectRatio;
      updateStyleForElement(this.state, paramSymbol);
      if (paramLength1 == null)
        break label235;
      f1 = paramLength1.floatValueX(this);
      label66: if (paramLength2 == null)
        break label250;
    }
    label235: label250: for (float f2 = paramLength2.floatValueX(this); ; f2 = this.state.viewPort.height)
    {
      this.state.viewPort = new SVG.Box(0.0F, 0.0F, f1, f2);
      if (!this.state.style.overflow.booleanValue())
        setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
      if (paramSymbol.viewBox != null)
      {
        this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, paramSymbol.viewBox, localPreserveAspectRatio));
        this.state.viewBox = paramSymbol.viewBox;
      }
      boolean bool = pushLayer();
      renderChildren(paramSymbol, true);
      if (bool)
        popLayer(paramSymbol);
      updateParentBoundingBox(paramSymbol);
      return;
      localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX;
      break;
      f1 = this.state.viewPort.width;
      break label66;
    }
  }

  private void render(SVG.Text paramText)
  {
    debug("Text render", new Object[0]);
    updateStyleForElement(this.state, paramText);
    if (!display())
      return;
    if (paramText.transform != null)
      this.canvas.concat(paramText.transform);
    float f1;
    label66: float f2;
    label87: float f3;
    label109: float f4;
    label131: float f5;
    if ((paramText.x == null) || (paramText.x.size() == 0))
    {
      f1 = 0.0F;
      if ((paramText.y != null) && (paramText.y.size() != 0))
        break label310;
      f2 = 0.0F;
      if ((paramText.dx != null) && (paramText.dx.size() != 0))
        break label331;
      f3 = 0.0F;
      if ((paramText.dy != null) && (paramText.dy.size() != 0))
        break label353;
      f4 = 0.0F;
      SVG.Style.TextAnchor localTextAnchor = getAnchorPosition();
      if (localTextAnchor != SVG.Style.TextAnchor.Start)
      {
        f5 = calculateTextWidth(paramText);
        if (localTextAnchor != SVG.Style.TextAnchor.Middle)
          break label375;
      }
    }
    label310: label331: label353: label375: for (f1 -= f5 / 2.0F; ; f1 -= f5)
    {
      if (paramText.boundingBox == null)
      {
        TextBoundsCalculator localTextBoundsCalculator = new TextBoundsCalculator(f1, f2);
        enumerateTextSpans(paramText, localTextBoundsCalculator);
        paramText.boundingBox = new SVG.Box(localTextBoundsCalculator.bbox.left, localTextBoundsCalculator.bbox.top, localTextBoundsCalculator.bbox.width(), localTextBoundsCalculator.bbox.height());
      }
      updateParentBoundingBox(paramText);
      checkForGradiantsAndPatterns(paramText);
      checkForClipPath(paramText);
      boolean bool = pushLayer();
      enumerateTextSpans(paramText, new PlainTextDrawer(f1 + f3, f2 + f4));
      if (!bool)
        break;
      popLayer(paramText);
      return;
      f1 = ((SVG.Length)paramText.x.get(0)).floatValueX(this);
      break label66;
      f2 = ((SVG.Length)paramText.y.get(0)).floatValueY(this);
      break label87;
      f3 = ((SVG.Length)paramText.dx.get(0)).floatValueX(this);
      break label109;
      f4 = ((SVG.Length)paramText.dy.get(0)).floatValueY(this);
      break label131;
    }
  }

  private void render(SVG.Use paramUse)
  {
    debug("Use render", new Object[0]);
    if (((paramUse.width != null) && (paramUse.width.isZero())) || ((paramUse.height != null) && (paramUse.height.isZero())));
    do
    {
      return;
      updateStyleForElement(this.state, paramUse);
    }
    while (!display());
    SVG.SvgObject localSvgObject = paramUse.document.resolveIRI(paramUse.href);
    if (localSvgObject == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUse.href;
      error("Use reference '%s' not found", arrayOfObject);
      return;
    }
    if (paramUse.transform != null)
      this.canvas.concat(paramUse.transform);
    Matrix localMatrix = new Matrix();
    float f1;
    float f2;
    label160: boolean bool;
    SVG.Svg localSvg;
    SVG.Length localLength3;
    label223: SVG.Length localLength4;
    if (paramUse.x != null)
    {
      f1 = paramUse.x.floatValueX(this);
      if (paramUse.y == null)
        break label276;
      f2 = paramUse.y.floatValueY(this);
      localMatrix.preTranslate(f1, f2);
      this.canvas.concat(localMatrix);
      checkForClipPath(paramUse);
      bool = pushLayer();
      parentPush(paramUse);
      if (!(localSvgObject instanceof SVG.Svg))
        break label302;
      statePush();
      localSvg = (SVG.Svg)localSvgObject;
      if (paramUse.width == null)
        break label282;
      localLength3 = paramUse.width;
      if (paramUse.height == null)
        break label292;
      localLength4 = paramUse.height;
      label236: render(localSvg, localLength3, localLength4);
      statePop();
    }
    while (true)
    {
      parentPop();
      if (bool)
        popLayer(paramUse);
      updateParentBoundingBox(paramUse);
      return;
      f1 = 0.0F;
      break;
      label276: f2 = 0.0F;
      break label160;
      label282: localLength3 = localSvg.width;
      break label223;
      label292: localLength4 = localSvg.height;
      break label236;
      label302: if ((localSvgObject instanceof SVG.Symbol))
      {
        SVG.Length localLength1;
        if (paramUse.width != null)
        {
          localLength1 = paramUse.width;
          label322: if (paramUse.height == null)
            break label376;
        }
        label376: for (SVG.Length localLength2 = paramUse.height; ; localLength2 = new SVG.Length(100.0F, SVG.Unit.percent))
        {
          statePush();
          render((SVG.Symbol)localSvgObject, localLength1, localLength2);
          statePop();
          break;
          localLength1 = new SVG.Length(100.0F, SVG.Unit.percent);
          break label322;
        }
      }
      render(localSvgObject);
    }
  }

  private void renderChildren(SVG.SvgContainer paramSvgContainer, boolean paramBoolean)
  {
    if (paramBoolean)
      parentPush(paramSvgContainer);
    Iterator localIterator = paramSvgContainer.getChildren().iterator();
    while (localIterator.hasNext())
      render((SVG.SvgObject)localIterator.next());
    if (paramBoolean)
      parentPop();
  }

  private void renderMarker(SVG.Marker paramMarker, MarkerVector paramMarkerVector)
  {
    statePush();
    Float localFloat = paramMarker.orient;
    float f1 = 0.0F;
    if (localFloat != null)
    {
      if (!Float.isNaN(paramMarker.orient.floatValue()))
        break label531;
      if (paramMarkerVector.dx == 0.0F)
      {
        boolean bool2 = paramMarkerVector.dy < 0.0F;
        f1 = 0.0F;
        if (!bool2);
      }
      else
      {
        f1 = (float)Math.toDegrees(Math.atan2(paramMarkerVector.dy, paramMarkerVector.dx));
      }
    }
    float f2;
    label83: Matrix localMatrix;
    float f3;
    label150: float f4;
    label167: float f5;
    label184: float f6;
    label201: float f7;
    float f8;
    PreserveAspectRatio localPreserveAspectRatio;
    label245: float f13;
    label276: float f9;
    float f10;
    float f11;
    label392: float f12;
    if (paramMarker.markerUnitsAreUser)
    {
      f2 = 1.0F;
      this.state = findInheritFromAncestorState(paramMarker);
      localMatrix = new Matrix();
      localMatrix.preTranslate(paramMarkerVector.x, paramMarkerVector.y);
      localMatrix.preRotate(f1);
      localMatrix.preScale(f2, f2);
      if (paramMarker.refX == null)
        break label565;
      f3 = paramMarker.refX.floatValueX(this);
      if (paramMarker.refY == null)
        break label571;
      f4 = paramMarker.refY.floatValueY(this);
      if (paramMarker.markerWidth == null)
        break label577;
      f5 = paramMarker.markerWidth.floatValueX(this);
      if (paramMarker.markerHeight == null)
        break label585;
      f6 = paramMarker.markerHeight.floatValueY(this);
      if (paramMarker.viewBox == null)
        break label665;
      f7 = f5 / paramMarker.viewBox.width;
      f8 = f6 / paramMarker.viewBox.height;
      if (paramMarker.preserveAspectRatio == null)
        break label593;
      localPreserveAspectRatio = paramMarker.preserveAspectRatio;
      if (!localPreserveAspectRatio.equals(PreserveAspectRatio.STRETCH))
      {
        if (localPreserveAspectRatio.getScale() != PreserveAspectRatio.Scale.Slice)
          break label601;
        f13 = Math.max(f7, f8);
        f8 = f13;
        f7 = f13;
      }
      localMatrix.preTranslate(f7 * -f3, f8 * -f4);
      this.canvas.concat(localMatrix);
      f9 = f7 * paramMarker.viewBox.width;
      f10 = f8 * paramMarker.viewBox.height;
      int i = 1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[localPreserveAspectRatio.getAlignment().ordinal()];
      f11 = 0.0F;
      switch (i)
      {
      default:
        int j = 1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[localPreserveAspectRatio.getAlignment().ordinal()];
        f12 = 0.0F;
        switch (j)
        {
        case 4:
        default:
          label452: if (!this.state.style.overflow.booleanValue())
            setClipRect(f11, f12, f5, f6);
          localMatrix.reset();
          localMatrix.preScale(f7, f8);
          this.canvas.concat(localMatrix);
        case 2:
        case 5:
        case 7:
        case 3:
        case 6:
        case 8:
        }
        break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    }
    while (true)
    {
      boolean bool1 = pushLayer();
      renderChildren(paramMarker, false);
      if (bool1)
        popLayer(paramMarker);
      statePop();
      return;
      label531: f1 = paramMarker.orient.floatValue();
      break;
      f2 = this.state.style.strokeWidth.floatValue(this.dpi);
      break label83;
      label565: f3 = 0.0F;
      break label150;
      label571: f4 = 0.0F;
      break label167;
      label577: f5 = 3.0F;
      break label184;
      label585: f6 = 3.0F;
      break label201;
      label593: localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX;
      break label245;
      label601: f13 = Math.min(f7, f8);
      break label276;
      f11 = 0.0F - (f5 - f9) / 2.0F;
      break label392;
      f11 = 0.0F - (f5 - f9);
      break label392;
      f12 = 0.0F - (f6 - f10) / 2.0F;
      break label452;
      f12 = 0.0F - (f6 - f10);
      break label452;
      label665: localMatrix.preTranslate(-f3, -f4);
      this.canvas.concat(localMatrix);
      if (!this.state.style.overflow.booleanValue())
        setClipRect(0.0F, 0.0F, f5, f6);
    }
  }

  private void renderMarkers(SVG.GraphicsElement paramGraphicsElement)
  {
    if ((this.state.style.markerStart == null) && (this.state.style.markerMid == null) && (this.state.style.markerEnd == null));
    label87: SVG.Marker localMarker3;
    label138: label189: List localList;
    int i;
    label370: 
    do
      while (true)
      {
        return;
        String str1 = this.state.style.markerStart;
        SVG.Marker localMarker1 = null;
        SVG.Marker localMarker2;
        if (str1 != null)
        {
          SVG.SvgObject localSvgObject3 = paramGraphicsElement.document.resolveIRI(this.state.style.markerStart);
          if (localSvgObject3 != null)
            localMarker1 = (SVG.Marker)localSvgObject3;
        }
        else
        {
          String str2 = this.state.style.markerMid;
          localMarker2 = null;
          if (str2 != null)
          {
            SVG.SvgObject localSvgObject2 = paramGraphicsElement.document.resolveIRI(this.state.style.markerMid);
            if (localSvgObject2 == null)
              break label370;
            localMarker2 = (SVG.Marker)localSvgObject2;
          }
          String str3 = this.state.style.markerEnd;
          localMarker3 = null;
          if (str3 != null)
          {
            SVG.SvgObject localSvgObject1 = paramGraphicsElement.document.resolveIRI(this.state.style.markerEnd);
            if (localSvgObject1 == null)
              break label404;
            localMarker3 = (SVG.Marker)localSvgObject1;
          }
          if (!(paramGraphicsElement instanceof SVG.Path))
            break label438;
          localList = new MarkerPositionCalculator(((SVG.Path)paramGraphicsElement).d).getMarkers();
        }
        while (true)
        {
          if (localList == null)
            break label469;
          i = localList.size();
          if (i == 0)
            break;
          SVG.Style localStyle1 = this.state.style;
          SVG.Style localStyle2 = this.state.style;
          this.state.style.markerEnd = null;
          localStyle2.markerMid = null;
          localStyle1.markerStart = null;
          if (localMarker1 != null)
            renderMarker(localMarker1, (MarkerVector)localList.get(0));
          if (localMarker2 == null)
            break label471;
          for (int j = 1; j < i - 1; j++)
            renderMarker(localMarker2, (MarkerVector)localList.get(j));
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = this.state.style.markerStart;
          error("Marker reference '%s' not found", arrayOfObject3);
          localMarker1 = null;
          break label87;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.state.style.markerMid;
          error("Marker reference '%s' not found", arrayOfObject2);
          localMarker2 = null;
          break label138;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.state.style.markerEnd;
          error("Marker reference '%s' not found", arrayOfObject1);
          localMarker3 = null;
          break label189;
          if ((paramGraphicsElement instanceof SVG.Line))
            localList = calculateMarkerPositions((SVG.Line)paramGraphicsElement);
          else
            localList = calculateMarkerPositions((SVG.PolyLine)paramGraphicsElement);
        }
      }
    while (localMarker3 == null);
    label404: label438: label469: label471: renderMarker(localMarker3, (MarkerVector)localList.get(i - 1));
  }

  private void renderMask(SVG.Mask paramMask, SVG.SvgElement paramSvgElement)
  {
    debug("Mask render", new Object[0]);
    int i;
    float f5;
    label50: float f6;
    if ((paramMask.maskUnitsAreUser != null) && (paramMask.maskUnitsAreUser.booleanValue()))
    {
      i = 1;
      if (i == 0)
        break label195;
      if (paramMask.width == null)
        break label119;
      f5 = paramMask.width.floatValueX(this);
      if (paramMask.height == null)
        break label131;
      f6 = paramMask.height.floatValueY(this);
      label67: if (paramMask.x == null)
        break label143;
      paramMask.x.floatValueX(this);
      label83: if (paramMask.y == null)
        break label169;
      paramMask.y.floatValueY(this);
    }
    while (true)
    {
      if ((f5 != 0.0F) && (f6 != 0.0F))
        break label364;
      return;
      i = 0;
      break;
      label119: f5 = paramSvgElement.boundingBox.width;
      break label50;
      label131: f6 = paramSvgElement.boundingBox.height;
      break label67;
      label143: ((float)(paramSvgElement.boundingBox.minX - 0.1D * paramSvgElement.boundingBox.width));
      break label83;
      label169: ((float)(paramSvgElement.boundingBox.minY - 0.1D * paramSvgElement.boundingBox.height));
    }
    label195: float f1;
    label213: float f2;
    label231: float f3;
    if (paramMask.x != null)
    {
      f1 = paramMask.x.floatValue(this, 1.0F);
      if (paramMask.y == null)
        break label340;
      f2 = paramMask.y.floatValue(this, 1.0F);
      if (paramMask.width == null)
        break label348;
      f3 = paramMask.width.floatValue(this, 1.0F);
      label249: if (paramMask.height == null)
        break label356;
    }
    label340: label348: label356: for (float f4 = paramMask.height.floatValue(this, 1.0F); ; f4 = 1.2F)
    {
      (paramSvgElement.boundingBox.minX + f1 * paramSvgElement.boundingBox.width);
      (paramSvgElement.boundingBox.minY + f2 * paramSvgElement.boundingBox.height);
      f5 = f3 * paramSvgElement.boundingBox.width;
      f6 = f4 * paramSvgElement.boundingBox.height;
      break;
      f1 = -0.1F;
      break label213;
      f2 = -0.1F;
      break label231;
      f3 = 1.2F;
      break label249;
    }
    label364: statePush();
    this.state = findInheritFromAncestorState(paramMask);
    this.state.style.opacity = Float.valueOf(1.0F);
    if ((paramMask.maskContentUnitsAreUser == null) || (paramMask.maskContentUnitsAreUser.booleanValue()));
    for (int j = 1; ; j = 0)
    {
      if (j == 0)
      {
        this.canvas.translate(paramSvgElement.boundingBox.minX, paramSvgElement.boundingBox.minY);
        this.canvas.scale(paramSvgElement.boundingBox.width, paramSvgElement.boundingBox.height);
      }
      renderChildren(paramMask, false);
      statePop();
      return;
    }
  }

  private void renderSwitchChild(SVG.Switch paramSwitch)
  {
    String str = Locale.getDefault().getLanguage();
    SVGExternalFileResolver localSVGExternalFileResolver = this.document.getFileResolver();
    Iterator localIterator1 = paramSwitch.getChildren().iterator();
    while (localIterator1.hasNext())
    {
      SVG.SvgObject localSvgObject = (SVG.SvgObject)localIterator1.next();
      if ((localSvgObject instanceof SVG.SvgConditional))
      {
        SVG.SvgConditional localSvgConditional = (SVG.SvgConditional)localSvgObject;
        if (localSvgConditional.getRequiredExtensions() == null)
        {
          Set localSet1 = localSvgConditional.getSystemLanguage();
          if ((localSet1 == null) || ((!localSet1.isEmpty()) && (localSet1.contains(str))))
          {
            Set localSet2 = localSvgConditional.getRequiredFeatures();
            if (localSet2 != null)
            {
              if (supportedFeatures == null)
                initialiseSupportedFeaturesMap();
              if ((localSet2.isEmpty()) || (!supportedFeatures.containsAll(localSet2)))
                break;
            }
            else
            {
              Set localSet3 = localSvgConditional.getRequiredFormats();
              if (localSet3 != null)
              {
                if ((!localSet3.isEmpty()) && (localSVGExternalFileResolver != null))
                {
                  Iterator localIterator3 = localSet3.iterator();
                  while (true)
                    if (localIterator3.hasNext())
                      if (!localSVGExternalFileResolver.isFormatSupported((String)localIterator3.next()))
                        break;
                }
              }
              else
              {
                Set localSet4 = localSvgConditional.getRequiredFonts();
                if (localSet4 != null)
                {
                  if ((!localSet4.isEmpty()) && (localSVGExternalFileResolver != null))
                  {
                    Iterator localIterator2 = localSet4.iterator();
                    while (true)
                      if (localIterator2.hasNext())
                        if (localSVGExternalFileResolver.resolveFont((String)localIterator2.next(), this.state.style.fontWeight.intValue(), String.valueOf(this.state.style.fontStyle)) == null)
                          break;
                  }
                }
                else
                  render(localSvgObject);
              }
            }
          }
        }
      }
    }
  }

  private void renderTextPath(SVG.TextPath paramTextPath)
  {
    debug("TextPath render", new Object[0]);
    updateStyleForElement(this.state, paramTextPath);
    if (!display())
      break label26;
    label26: 
    while (!visible())
      return;
    SVG.SvgObject localSvgObject = paramTextPath.document.resolveIRI(paramTextPath.href);
    if (localSvgObject == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramTextPath.href;
      error("TextPath reference '%s' not found", arrayOfObject);
      return;
    }
    SVG.Path localPath = (SVG.Path)localSvgObject;
    Path localPath1 = new PathConverter(localPath.d).getPath();
    if (localPath.transform != null)
      localPath1.transform(localPath.transform);
    PathMeasure localPathMeasure = new PathMeasure(localPath1, false);
    float f1;
    label145: float f2;
    if (paramTextPath.startOffset != null)
    {
      f1 = paramTextPath.startOffset.floatValue(this, localPathMeasure.getLength());
      SVG.Style.TextAnchor localTextAnchor = getAnchorPosition();
      if (localTextAnchor != SVG.Style.TextAnchor.Start)
      {
        f2 = calculateTextWidth(paramTextPath);
        if (localTextAnchor != SVG.Style.TextAnchor.Middle)
          break label235;
      }
    }
    label235: for (f1 -= f2 / 2.0F; ; f1 -= f2)
    {
      checkForGradiantsAndPatterns((SVG.SvgElement)paramTextPath.getTextRoot());
      boolean bool = pushLayer();
      enumerateTextSpans(paramTextPath, new PathTextDrawer(localPath1, f1, 0.0F));
      if (!bool)
        break;
      popLayer(paramTextPath);
      return;
      f1 = 0.0F;
      break label145;
    }
  }

  private boolean requiresCompositing()
  {
    if ((this.state.style.mask != null) && (!this.state.directRendering))
      warn("Masks are not supported when using getPicture()", new Object[0]);
    boolean bool1;
    if (this.state.style.opacity.floatValue() >= 1.0F)
    {
      String str = this.state.style.mask;
      bool1 = false;
      if (str != null)
      {
        boolean bool2 = this.state.directRendering;
        bool1 = false;
        if (!bool2);
      }
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  private void resetState()
  {
    this.state = new RendererState();
    this.stateStack = new Stack();
    updateStyle(this.state, SVG.Style.getDefaultStyle());
    this.state.viewPort = this.canvasViewPort;
    this.state.spacePreserve = false;
    this.state.directRendering = this.directRenderingMode;
    this.stateStack.push((RendererState)this.state.clone());
    this.canvasStack = new Stack();
    this.bitmapStack = new Stack();
    this.matrixStack = new Stack();
    this.parentStack = new Stack();
  }

  private void setClipRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    float f3 = paramFloat1 + paramFloat3;
    float f4 = paramFloat2 + paramFloat4;
    if (this.state.style.clip != null)
    {
      f1 += this.state.style.clip.left.floatValueX(this);
      f2 += this.state.style.clip.top.floatValueY(this);
      f3 -= this.state.style.clip.right.floatValueX(this);
      f4 -= this.state.style.clip.bottom.floatValueY(this);
    }
    this.canvas.clipRect(f1, f2, f3, f4);
  }

  private void setPaintColour(RendererState paramRendererState, boolean paramBoolean, SVG.SvgPaint paramSvgPaint)
  {
    Float localFloat;
    float f;
    if (paramBoolean)
    {
      localFloat = paramRendererState.style.fillOpacity;
      f = localFloat.floatValue();
      if (!(paramSvgPaint instanceof SVG.Colour))
        break label76;
    }
    int j;
    for (int i = ((SVG.Colour)paramSvgPaint).colour; ; i = paramRendererState.style.color.colour)
    {
      j = i | clamp255(f) << 24;
      if (!paramBoolean)
        break label98;
      paramRendererState.fillPaint.setColor(j);
      label76: 
      do
      {
        return;
        localFloat = paramRendererState.style.strokeOpacity;
        break;
      }
      while (!(paramSvgPaint instanceof SVG.CurrentColor));
    }
    label98: paramRendererState.strokePaint.setColor(j);
  }

  private void setSolidColor(boolean paramBoolean, SVG.SolidColor paramSolidColor)
  {
    boolean bool = true;
    if (paramBoolean)
    {
      RendererState localRendererState2;
      if (isSpecified(paramSolidColor.baseStyle, 2147483648L))
      {
        this.state.style.fill = paramSolidColor.baseStyle.solidColor;
        localRendererState2 = this.state;
        if (paramSolidColor.baseStyle.solidColor == null)
          break label124;
      }
      while (true)
      {
        localRendererState2.hasFill = bool;
        if (isSpecified(paramSolidColor.baseStyle, 4294967296L))
          this.state.style.fillOpacity = paramSolidColor.baseStyle.solidOpacity;
        if (isSpecified(paramSolidColor.baseStyle, 6442450944L))
          setPaintColour(this.state, paramBoolean, this.state.style.fill);
        return;
        label124: bool = false;
      }
    }
    RendererState localRendererState1;
    if (isSpecified(paramSolidColor.baseStyle, 2147483648L))
    {
      this.state.style.stroke = paramSolidColor.baseStyle.solidColor;
      localRendererState1 = this.state;
      if (paramSolidColor.baseStyle.solidColor == null)
        break label247;
    }
    while (true)
    {
      localRendererState1.hasStroke = bool;
      if (isSpecified(paramSolidColor.baseStyle, 4294967296L))
        this.state.style.strokeOpacity = paramSolidColor.baseStyle.solidOpacity;
      if (!isSpecified(paramSolidColor.baseStyle, 6442450944L))
        break;
      setPaintColour(this.state, paramBoolean, this.state.style.stroke);
      return;
      label247: bool = false;
    }
  }

  private void statePop()
  {
    this.canvas.restore();
    this.state = ((RendererState)this.stateStack.pop());
  }

  private void statePush()
  {
    this.canvas.save();
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
  }

  private String textXMLSpaceTransform(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.state.spacePreserve)
      return paramString.replaceAll("[\\n\\t]", " ");
    String str = paramString.replaceAll("\\n", "").replaceAll("\\t", " ");
    if (paramBoolean1)
      str = str.replaceAll("^\\s+", "");
    if (paramBoolean2)
      str = str.replaceAll("\\s+$", "");
    return str.replaceAll("\\s{2,}", " ");
  }

  private void updateParentBoundingBox(SVG.SvgElement paramSvgElement)
  {
    if (paramSvgElement.parent == null);
    Matrix localMatrix;
    do
    {
      do
        return;
      while (paramSvgElement.boundingBox == null);
      localMatrix = new Matrix();
    }
    while (!((Matrix)this.matrixStack.peek()).invert(localMatrix));
    float[] arrayOfFloat = new float[8];
    arrayOfFloat[0] = paramSvgElement.boundingBox.minX;
    arrayOfFloat[1] = paramSvgElement.boundingBox.minY;
    arrayOfFloat[2] = paramSvgElement.boundingBox.maxX();
    arrayOfFloat[3] = paramSvgElement.boundingBox.minY;
    arrayOfFloat[4] = paramSvgElement.boundingBox.maxX();
    arrayOfFloat[5] = paramSvgElement.boundingBox.maxY();
    arrayOfFloat[6] = paramSvgElement.boundingBox.minX;
    arrayOfFloat[7] = paramSvgElement.boundingBox.maxY();
    localMatrix.preConcat(this.canvas.getMatrix());
    localMatrix.mapPoints(arrayOfFloat);
    RectF localRectF = new RectF(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[0], arrayOfFloat[1]);
    for (int i = 2; i <= 6; i += 2)
    {
      if (arrayOfFloat[i] < localRectF.left)
        localRectF.left = arrayOfFloat[i];
      if (arrayOfFloat[i] > localRectF.right)
        localRectF.right = arrayOfFloat[i];
      if (arrayOfFloat[(i + 1)] < localRectF.top)
        localRectF.top = arrayOfFloat[(i + 1)];
      if (arrayOfFloat[(i + 1)] > localRectF.bottom)
        localRectF.bottom = arrayOfFloat[(i + 1)];
    }
    SVG.SvgElement localSvgElement = (SVG.SvgElement)this.parentStack.peek();
    if (localSvgElement.boundingBox == null)
    {
      localSvgElement.boundingBox = SVG.Box.fromLimits(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom);
      return;
    }
    localSvgElement.boundingBox.union(SVG.Box.fromLimits(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom));
  }

  private void updateStyle(RendererState paramRendererState, SVG.Style paramStyle)
  {
    if (isSpecified(paramStyle, 4096L))
      paramRendererState.style.color = paramStyle.color;
    if (isSpecified(paramStyle, 2048L))
      paramRendererState.style.opacity = paramStyle.opacity;
    boolean bool6;
    boolean bool5;
    label180: label352: boolean bool1;
    label412: label668: label930: boolean bool2;
    label522: label955: boolean bool3;
    label989: Paint localPaint4;
    if (isSpecified(paramStyle, 1L))
    {
      paramRendererState.style.fill = paramStyle.fill;
      if (paramStyle.fill != null)
      {
        bool6 = true;
        paramRendererState.hasFill = bool6;
      }
    }
    else
    {
      if (isSpecified(paramStyle, 4L))
        paramRendererState.style.fillOpacity = paramStyle.fillOpacity;
      if (isSpecified(paramStyle, 6149L))
        setPaintColour(paramRendererState, true, paramRendererState.style.fill);
      if (isSpecified(paramStyle, 2L))
        paramRendererState.style.fillRule = paramStyle.fillRule;
      if (isSpecified(paramStyle, 8L))
      {
        paramRendererState.style.stroke = paramStyle.stroke;
        if (paramStyle.stroke == null)
          break label1381;
        bool5 = true;
        paramRendererState.hasStroke = bool5;
      }
      if (isSpecified(paramStyle, 16L))
        paramRendererState.style.strokeOpacity = paramStyle.strokeOpacity;
      if (isSpecified(paramStyle, 6168L))
        setPaintColour(paramRendererState, false, paramRendererState.style.stroke);
      if (isSpecified(paramStyle, 34359738368L))
        paramRendererState.style.vectorEffect = paramStyle.vectorEffect;
      if (isSpecified(paramStyle, 32L))
      {
        paramRendererState.style.strokeWidth = paramStyle.strokeWidth;
        paramRendererState.strokePaint.setStrokeWidth(paramRendererState.style.strokeWidth.floatValue(this));
      }
      if (isSpecified(paramStyle, 64L))
        paramRendererState.style.strokeLineCap = paramStyle.strokeLineCap;
      switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineCaps[paramStyle.strokeLineCap.ordinal()])
      {
      default:
        if (isSpecified(paramStyle, 128L))
          paramRendererState.style.strokeLineJoin = paramStyle.strokeLineJoin;
        switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[paramStyle.strokeLineJoin.ordinal()])
        {
        default:
          if (isSpecified(paramStyle, 256L))
          {
            paramRendererState.style.strokeMiterLimit = paramStyle.strokeMiterLimit;
            paramRendererState.strokePaint.setStrokeMiter(paramStyle.strokeMiterLimit.floatValue());
          }
          if (isSpecified(paramStyle, 512L))
            paramRendererState.style.strokeDashArray = paramStyle.strokeDashArray;
          if (isSpecified(paramStyle, 1024L))
            paramRendererState.style.strokeDashOffset = paramStyle.strokeDashOffset;
          if (isSpecified(paramStyle, 1536L))
          {
            if (paramRendererState.style.strokeDashArray == null)
              paramRendererState.strokePaint.setPathEffect(null);
          }
          else
          {
            if (isSpecified(paramStyle, 16384L))
            {
              float f1 = getCurrentFontSize();
              paramRendererState.style.fontSize = paramStyle.fontSize;
              paramRendererState.fillPaint.setTextSize(paramStyle.fontSize.floatValue(this, f1));
              paramRendererState.strokePaint.setTextSize(paramStyle.fontSize.floatValue(this, f1));
            }
            if (isSpecified(paramStyle, 8192L))
              paramRendererState.style.fontFamily = paramStyle.fontFamily;
            if (isSpecified(paramStyle, 32768L))
            {
              if ((paramStyle.fontWeight.intValue() != -1) || (paramRendererState.style.fontWeight.intValue() <= 100))
                break label1623;
              SVG.Style localStyle2 = paramRendererState.style;
              localStyle2.fontWeight = Integer.valueOf(-100 + localStyle2.fontWeight.intValue());
            }
            if (isSpecified(paramStyle, 65536L))
              paramRendererState.style.fontStyle = paramStyle.fontStyle;
            if (isSpecified(paramStyle, 106496L))
            {
              List localList = paramRendererState.style.fontFamily;
              Typeface localTypeface = null;
              if (localList != null)
              {
                SVG localSVG = this.document;
                localTypeface = null;
                if (localSVG != null)
                {
                  SVGExternalFileResolver localSVGExternalFileResolver = this.document.getFileResolver();
                  Iterator localIterator = paramRendererState.style.fontFamily.iterator();
                  do
                  {
                    if (!localIterator.hasNext())
                      break;
                    String str = (String)localIterator.next();
                    localTypeface = checkGenericFont(str, paramRendererState.style.fontWeight, paramRendererState.style.fontStyle);
                    if ((localTypeface == null) && (localSVGExternalFileResolver != null))
                      localTypeface = localSVGExternalFileResolver.resolveFont(str, paramRendererState.style.fontWeight.intValue(), String.valueOf(paramRendererState.style.fontStyle));
                  }
                  while (localTypeface == null);
                }
              }
              if (localTypeface == null)
                localTypeface = checkGenericFont("sans-serif", paramRendererState.style.fontWeight, paramRendererState.style.fontStyle);
              paramRendererState.fillPaint.setTypeface(localTypeface);
              paramRendererState.strokePaint.setTypeface(localTypeface);
            }
            if (isSpecified(paramStyle, 131072L))
            {
              paramRendererState.style.textDecoration = paramStyle.textDecoration;
              Paint localPaint1 = paramRendererState.fillPaint;
              if (paramStyle.textDecoration != SVG.Style.TextDecoration.LineThrough)
                break label1692;
              bool1 = true;
              localPaint1.setStrikeThruText(bool1);
              Paint localPaint2 = paramRendererState.fillPaint;
              if (paramStyle.textDecoration != SVG.Style.TextDecoration.Underline)
                break label1698;
              bool2 = true;
              localPaint2.setUnderlineText(bool2);
              if (Build.VERSION.SDK_INT >= 17)
              {
                Paint localPaint3 = paramRendererState.strokePaint;
                if (paramStyle.textDecoration != SVG.Style.TextDecoration.LineThrough)
                  break label1704;
                bool3 = true;
                localPaint3.setStrikeThruText(bool3);
                localPaint4 = paramRendererState.strokePaint;
                if (paramStyle.textDecoration != SVG.Style.TextDecoration.Underline)
                  break label1710;
              }
            }
          }
          break;
        case 1:
        case 2:
        case 3:
        }
        break;
      case 1:
      case 2:
      case 3:
      }
    }
    label1692: label1698: label1704: label1710: for (boolean bool4 = true; ; bool4 = false)
    {
      localPaint4.setUnderlineText(bool4);
      if (isSpecified(paramStyle, 68719476736L))
        paramRendererState.style.direction = paramStyle.direction;
      if (isSpecified(paramStyle, 262144L))
        paramRendererState.style.textAnchor = paramStyle.textAnchor;
      if (isSpecified(paramStyle, 524288L))
        paramRendererState.style.overflow = paramStyle.overflow;
      if (isSpecified(paramStyle, 2097152L))
        paramRendererState.style.markerStart = paramStyle.markerStart;
      if (isSpecified(paramStyle, 4194304L))
        paramRendererState.style.markerMid = paramStyle.markerMid;
      if (isSpecified(paramStyle, 8388608L))
        paramRendererState.style.markerEnd = paramStyle.markerEnd;
      if (isSpecified(paramStyle, 16777216L))
        paramRendererState.style.display = paramStyle.display;
      if (isSpecified(paramStyle, 33554432L))
        paramRendererState.style.visibility = paramStyle.visibility;
      if (isSpecified(paramStyle, 1048576L))
        paramRendererState.style.clip = paramStyle.clip;
      if (isSpecified(paramStyle, 268435456L))
        paramRendererState.style.clipPath = paramStyle.clipPath;
      if (isSpecified(paramStyle, 536870912L))
        paramRendererState.style.clipRule = paramStyle.clipRule;
      if (isSpecified(paramStyle, 1073741824L))
        paramRendererState.style.mask = paramStyle.mask;
      if (isSpecified(paramStyle, 67108864L))
        paramRendererState.style.stopColor = paramStyle.stopColor;
      if (isSpecified(paramStyle, 134217728L))
        paramRendererState.style.stopOpacity = paramStyle.stopOpacity;
      if (isSpecified(paramStyle, 8589934592L))
        paramRendererState.style.viewportFill = paramStyle.viewportFill;
      if (isSpecified(paramStyle, 17179869184L))
        paramRendererState.style.viewportFillOpacity = paramStyle.viewportFillOpacity;
      return;
      bool6 = false;
      break;
      label1381: bool5 = false;
      break label180;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.BUTT);
      break label352;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.ROUND);
      break label352;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
      break label352;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.MITER);
      break label412;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.ROUND);
      break label412;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
      break label412;
      float f2 = 0.0F;
      int i = paramRendererState.style.strokeDashArray.length;
      if (i % 2 == 0);
      float[] arrayOfFloat;
      for (int j = i; ; j = i * 2)
      {
        arrayOfFloat = new float[j];
        for (int k = 0; k < j; k++)
        {
          arrayOfFloat[k] = paramRendererState.style.strokeDashArray[(k % i)].floatValue(this);
          f2 += arrayOfFloat[k];
        }
      }
      if (f2 == 0.0F)
      {
        paramRendererState.strokePaint.setPathEffect(null);
        break label522;
      }
      float f3 = paramRendererState.style.strokeDashOffset.floatValue(this);
      if (f3 < 0.0F)
        f3 = f2 + f3 % f2;
      paramRendererState.strokePaint.setPathEffect(new DashPathEffect(arrayOfFloat, f3));
      break label522;
      label1623: if ((paramStyle.fontWeight.intValue() == 1) && (paramRendererState.style.fontWeight.intValue() < 900))
      {
        SVG.Style localStyle1 = paramRendererState.style;
        localStyle1.fontWeight = Integer.valueOf(100 + localStyle1.fontWeight.intValue());
        break label668;
      }
      paramRendererState.style.fontWeight = paramStyle.fontWeight;
      break label668;
      bool1 = false;
      break label930;
      bool2 = false;
      break label955;
      bool3 = false;
      break label989;
    }
  }

  private void updateStyleForElement(RendererState paramRendererState, SVG.SvgElementBase paramSvgElementBase)
  {
    if (paramSvgElementBase.parent == null);
    for (boolean bool = true; ; bool = false)
    {
      paramRendererState.style.resetNonInheritingProperties(bool);
      if (paramSvgElementBase.baseStyle != null)
        updateStyle(paramRendererState, paramSvgElementBase.baseStyle);
      if (!this.document.hasCSSRules())
        break;
      Iterator localIterator = this.document.getCSSRules().iterator();
      while (localIterator.hasNext())
      {
        CSSParser.Rule localRule = (CSSParser.Rule)localIterator.next();
        if (CSSParser.ruleMatch(localRule.selector, paramSvgElementBase))
          updateStyle(paramRendererState, localRule.style);
      }
    }
    if (paramSvgElementBase.style != null)
      updateStyle(paramRendererState, paramSvgElementBase.style);
  }

  private void viewportFill()
  {
    if ((this.state.style.viewportFill instanceof SVG.Colour));
    for (int i = ((SVG.Colour)this.state.style.viewportFill).colour; ; i = this.state.style.color.colour)
    {
      if (this.state.style.viewportFillOpacity != null)
        i |= clamp255(this.state.style.viewportFillOpacity.floatValue()) << 24;
      this.canvas.drawColor(i);
      do
        return;
      while (!(this.state.style.viewportFill instanceof SVG.CurrentColor));
    }
  }

  private boolean visible()
  {
    if (this.state.style.visibility != null)
      return this.state.style.visibility.booleanValue();
    return true;
  }

  private static void warn(String paramString, Object[] paramArrayOfObject)
  {
    Log.w("SVGAndroidRenderer", String.format(paramString, paramArrayOfObject));
  }

  protected float getCurrentFontSize()
  {
    return this.state.fillPaint.getTextSize();
  }

  protected float getCurrentFontXHeight()
  {
    return this.state.fillPaint.getTextSize() / 2.0F;
  }

  protected SVG.Box getCurrentViewPortInUserUnits()
  {
    if (this.state.viewBox != null)
      return this.state.viewBox;
    return this.state.viewPort;
  }

  protected float getDPI()
  {
    return this.dpi;
  }

  protected void renderDocument(SVG paramSVG, SVG.Box paramBox, PreserveAspectRatio paramPreserveAspectRatio, boolean paramBoolean)
  {
    this.document = paramSVG;
    this.directRenderingMode = paramBoolean;
    SVG.Svg localSvg = paramSVG.getRootElement();
    if (localSvg == null)
    {
      warn("Nothing to render. Document is empty.", new Object[0]);
      return;
    }
    resetState();
    checkXMLSpaceAttribute(localSvg);
    SVG.Length localLength1 = localSvg.width;
    SVG.Length localLength2 = localSvg.height;
    SVG.Box localBox;
    if (paramBox != null)
    {
      localBox = paramBox;
      if (paramPreserveAspectRatio == null)
        break label96;
    }
    label96: for (PreserveAspectRatio localPreserveAspectRatio = paramPreserveAspectRatio; ; localPreserveAspectRatio = localSvg.preserveAspectRatio)
    {
      render(localSvg, localLength1, localLength2, localBox, localPreserveAspectRatio);
      return;
      localBox = localSvg.viewBox;
      break;
    }
  }

  private class MarkerPositionCalculator
    implements SVG.PathInterface
  {
    private boolean closepathReAdjustPending;
    private SVGAndroidRenderer.MarkerVector lastPos = null;
    private List<SVGAndroidRenderer.MarkerVector> markers = new ArrayList();
    private boolean normalCubic = true;
    private boolean startArc = false;
    private float startX;
    private float startY;
    private int subpathStartIndex = -1;

    public MarkerPositionCalculator(SVG.PathDefinition arg2)
    {
      Object localObject;
      if (localObject == null);
      do
      {
        return;
        localObject.enumeratePath(this);
        if (this.closepathReAdjustPending)
        {
          this.lastPos.add((SVGAndroidRenderer.MarkerVector)this.markers.get(this.subpathStartIndex));
          this.markers.set(this.subpathStartIndex, this.lastPos);
          this.closepathReAdjustPending = false;
        }
      }
      while (this.lastPos == null);
      this.markers.add(this.lastPos);
    }

    public void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      this.startArc = true;
      this.normalCubic = false;
      SVGAndroidRenderer.arcTo(this.lastPos.x, this.lastPos.y, paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, paramFloat4, paramFloat5, this);
      this.normalCubic = true;
      this.closepathReAdjustPending = false;
    }

    public void close()
    {
      this.markers.add(this.lastPos);
      lineTo(this.startX, this.startY);
      this.closepathReAdjustPending = true;
    }

    public void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      if ((this.normalCubic) || (this.startArc))
      {
        this.lastPos.add(paramFloat1, paramFloat2);
        this.markers.add(this.lastPos);
        this.startArc = false;
      }
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat5, paramFloat6, paramFloat5 - paramFloat3, paramFloat6 - paramFloat4);
      this.closepathReAdjustPending = false;
    }

    public List<SVGAndroidRenderer.MarkerVector> getMarkers()
    {
      return this.markers;
    }

    public void lineTo(float paramFloat1, float paramFloat2)
    {
      this.lastPos.add(paramFloat1, paramFloat2);
      this.markers.add(this.lastPos);
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat1, paramFloat2, paramFloat1 - this.lastPos.x, paramFloat2 - this.lastPos.y);
      this.closepathReAdjustPending = false;
    }

    public void moveTo(float paramFloat1, float paramFloat2)
    {
      if (this.closepathReAdjustPending)
      {
        this.lastPos.add((SVGAndroidRenderer.MarkerVector)this.markers.get(this.subpathStartIndex));
        this.markers.set(this.subpathStartIndex, this.lastPos);
        this.closepathReAdjustPending = false;
      }
      if (this.lastPos != null)
        this.markers.add(this.lastPos);
      this.startX = paramFloat1;
      this.startY = paramFloat2;
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat1, paramFloat2, 0.0F, 0.0F);
      this.subpathStartIndex = this.markers.size();
    }

    public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.lastPos.add(paramFloat1, paramFloat2);
      this.markers.add(this.lastPos);
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat3, paramFloat4, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
      this.closepathReAdjustPending = false;
    }
  }

  private class MarkerVector
  {
    public float dx = 0.0F;
    public float dy = 0.0F;
    public float x;
    public float y;

    public MarkerVector(float paramFloat1, float paramFloat2, float paramFloat3, float arg5)
    {
      this.x = paramFloat1;
      this.y = paramFloat2;
      Object localObject;
      double d = Math.sqrt(paramFloat3 * paramFloat3 + localObject * localObject);
      if (d != 0.0D)
      {
        this.dx = ((float)(paramFloat3 / d));
        this.dy = ((float)(localObject / d));
      }
    }

    public void add(float paramFloat1, float paramFloat2)
    {
      float f1 = paramFloat1 - this.x;
      float f2 = paramFloat2 - this.y;
      double d = Math.sqrt(f1 * f1 + f2 * f2);
      if (d != 0.0D)
      {
        this.dx += (float)(f1 / d);
        this.dy += (float)(f2 / d);
      }
    }

    public void add(MarkerVector paramMarkerVector)
    {
      this.dx += paramMarkerVector.dx;
      this.dy += paramMarkerVector.dy;
    }

    public String toString()
    {
      return "(" + this.x + "," + this.y + " " + this.dx + "," + this.dy + ")";
    }
  }

  private class PathConverter
    implements SVG.PathInterface
  {
    float lastX;
    float lastY;
    Path path = new Path();

    public PathConverter(SVG.PathDefinition arg2)
    {
      Object localObject;
      if (localObject == null)
        return;
      localObject.enumeratePath(this);
    }

    public void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      SVGAndroidRenderer.arcTo(this.lastX, this.lastY, paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, paramFloat4, paramFloat5, this);
      this.lastX = paramFloat4;
      this.lastY = paramFloat5;
    }

    public void close()
    {
      this.path.close();
    }

    public void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      this.path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
      this.lastX = paramFloat5;
      this.lastY = paramFloat6;
    }

    public Path getPath()
    {
      return this.path;
    }

    public void lineTo(float paramFloat1, float paramFloat2)
    {
      this.path.lineTo(paramFloat1, paramFloat2);
      this.lastX = paramFloat1;
      this.lastY = paramFloat2;
    }

    public void moveTo(float paramFloat1, float paramFloat2)
    {
      this.path.moveTo(paramFloat1, paramFloat2);
      this.lastX = paramFloat1;
      this.lastY = paramFloat2;
    }

    public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.path.quadTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      this.lastX = paramFloat3;
      this.lastY = paramFloat4;
    }
  }

  private class PathTextDrawer extends SVGAndroidRenderer.PlainTextDrawer
  {
    private Path path;

    public PathTextDrawer(Path paramFloat1, float paramFloat2, float arg4)
    {
      super(paramFloat2, localObject);
      this.path = paramFloat1;
    }

    public void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        if (SVGAndroidRenderer.this.state.hasFill)
          SVGAndroidRenderer.this.canvas.drawTextOnPath(paramString, this.path, this.x, this.y, SVGAndroidRenderer.this.state.fillPaint);
        if (SVGAndroidRenderer.this.state.hasStroke)
          SVGAndroidRenderer.this.canvas.drawTextOnPath(paramString, this.path, this.x, this.y, SVGAndroidRenderer.this.state.strokePaint);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }

  private class PlainTextDrawer extends SVGAndroidRenderer.TextProcessor
  {
    public float x;
    public float y;

    public PlainTextDrawer(float paramFloat1, float arg3)
    {
      super(null);
      this.x = paramFloat1;
      Object localObject;
      this.y = localObject;
    }

    public void processText(String paramString)
    {
      SVGAndroidRenderer.debug("TextSequence render", new Object[0]);
      if (SVGAndroidRenderer.this.visible())
      {
        if (SVGAndroidRenderer.this.state.hasFill)
          SVGAndroidRenderer.this.canvas.drawText(paramString, this.x, this.y, SVGAndroidRenderer.this.state.fillPaint);
        if (SVGAndroidRenderer.this.state.hasStroke)
          SVGAndroidRenderer.this.canvas.drawText(paramString, this.x, this.y, SVGAndroidRenderer.this.state.strokePaint);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }

  private class PlainTextToPath extends SVGAndroidRenderer.TextProcessor
  {
    public Path textAsPath;
    public float x;
    public float y;

    public PlainTextToPath(float paramFloat1, float paramPath, Path arg4)
    {
      super(null);
      this.x = paramFloat1;
      this.y = paramPath;
      Object localObject;
      this.textAsPath = localObject;
    }

    public boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      if ((paramTextContainer instanceof SVG.TextPath))
      {
        SVGAndroidRenderer.warn("Using <textPath> elements in a clip path is not supported.", new Object[0]);
        return false;
      }
      return true;
    }

    public void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        Path localPath = new Path();
        SVGAndroidRenderer.this.state.fillPaint.getTextPath(paramString, 0, paramString.length(), this.x, this.y, localPath);
        this.textAsPath.addPath(localPath);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }

  private class RendererState
    implements Cloneable
  {
    public boolean directRendering;
    public Paint fillPaint = new Paint();
    public boolean hasFill;
    public boolean hasStroke;
    public boolean spacePreserve;
    public Paint strokePaint;
    public SVG.Style style;
    public SVG.Box viewBox;
    public SVG.Box viewPort;

    public RendererState()
    {
      this.fillPaint.setFlags(385);
      this.fillPaint.setStyle(Paint.Style.FILL);
      this.fillPaint.setTypeface(Typeface.DEFAULT);
      this.strokePaint = new Paint();
      this.strokePaint.setFlags(385);
      this.strokePaint.setStyle(Paint.Style.STROKE);
      this.strokePaint.setTypeface(Typeface.DEFAULT);
      this.style = SVG.Style.getDefaultStyle();
    }

    protected Object clone()
    {
      try
      {
        RendererState localRendererState = (RendererState)super.clone();
        localRendererState.style = ((SVG.Style)this.style.clone());
        localRendererState.fillPaint = new Paint(this.fillPaint);
        localRendererState.strokePaint = new Paint(this.strokePaint);
        return localRendererState;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        throw new InternalError(localCloneNotSupportedException.toString());
      }
    }
  }

  private class TextBoundsCalculator extends SVGAndroidRenderer.TextProcessor
  {
    RectF bbox = new RectF();
    float x;
    float y;

    public TextBoundsCalculator(float paramFloat1, float arg3)
    {
      super(null);
      this.x = paramFloat1;
      Object localObject;
      this.y = localObject;
    }

    public boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      if ((paramTextContainer instanceof SVG.TextPath))
      {
        SVG.TextPath localTextPath = (SVG.TextPath)paramTextContainer;
        SVG.SvgObject localSvgObject = paramTextContainer.document.resolveIRI(localTextPath.href);
        if (localSvgObject == null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localTextPath.href;
          SVGAndroidRenderer.error("TextPath path reference '%s' not found", arrayOfObject);
          return false;
        }
        SVG.Path localPath = (SVG.Path)localSvgObject;
        Path localPath1 = new SVGAndroidRenderer.PathConverter(SVGAndroidRenderer.this, localPath.d).getPath();
        if (localPath.transform != null)
          localPath1.transform(localPath.transform);
        RectF localRectF = new RectF();
        localPath1.computeBounds(localRectF, true);
        this.bbox.union(localRectF);
        return false;
      }
      return true;
    }

    public void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        Rect localRect = new Rect();
        SVGAndroidRenderer.this.state.fillPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
        RectF localRectF = new RectF(localRect);
        localRectF.offset(this.x, this.y);
        this.bbox.union(localRectF);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }

  private abstract class TextProcessor
  {
    private TextProcessor()
    {
    }

    public boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      return true;
    }

    public abstract void processText(String paramString);
  }

  private class TextWidthCalculator extends SVGAndroidRenderer.TextProcessor
  {
    public float x = 0.0F;

    private TextWidthCalculator()
    {
      super(null);
    }

    public void processText(String paramString)
    {
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGAndroidRenderer
 * JD-Core Version:    0.6.2
 */