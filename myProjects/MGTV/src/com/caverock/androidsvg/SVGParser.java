package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class SVGParser extends DefaultHandler2
{
  private static final String CURRENTCOLOR = "currentColor";
  private static final String FEATURE_STRING_PREFIX = "http://www.w3.org/TR/SVG11/feature#";
  private static final String NONE = "none";
  private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
  private static final String TAG = "SVGParser";
  private static final String VALID_DISPLAY_VALUES = "|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|";
  private static final String VALID_VISIBILITY_VALUES = "|visible|hidden|collapse|";
  private static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";
  private SVG.SvgContainer currentElement = null;
  private int ignoreDepth;
  private boolean ignoring = false;
  private boolean inMetadataElement = false;
  private boolean inStyleElement = false;
  private StringBuilder metadataElementContents = null;
  private SVGElem metadataTag = null;
  private StringBuilder styleElementContents = null;
  private Set<String> supportedFormats = null;
  private SVG svgDocument = null;

  private void circle(Attributes paramAttributes)
    throws SAXException
  {
    debug("<circle>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Circle localCircle = new SVG.Circle();
    localCircle.document = this.svgDocument;
    localCircle.parent = this.currentElement;
    parseAttributesCore(localCircle, paramAttributes);
    parseAttributesStyle(localCircle, paramAttributes);
    parseAttributesTransform(localCircle, paramAttributes);
    parseAttributesConditional(localCircle, paramAttributes);
    parseAttributesCircle(localCircle, paramAttributes);
    this.currentElement.addChild(localCircle);
  }

  private static int clamp255(float paramFloat)
  {
    if (paramFloat < 0.0F)
      return 0;
    if (paramFloat > 255.0F)
      return 255;
    return Math.round(paramFloat);
  }

  private void clipPath(Attributes paramAttributes)
    throws SAXException
  {
    debug("<clipPath>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.ClipPath localClipPath = new SVG.ClipPath();
    localClipPath.document = this.svgDocument;
    localClipPath.parent = this.currentElement;
    parseAttributesCore(localClipPath, paramAttributes);
    parseAttributesStyle(localClipPath, paramAttributes);
    parseAttributesTransform(localClipPath, paramAttributes);
    parseAttributesConditional(localClipPath, paramAttributes);
    parseAttributesClipPath(localClipPath, paramAttributes);
    this.currentElement.addChild(localClipPath);
    this.currentElement = localClipPath;
  }

  private void debug(String paramString, Object[] paramArrayOfObject)
  {
  }

  private void defs(Attributes paramAttributes)
    throws SAXException
  {
    debug("<defs>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Defs localDefs = new SVG.Defs();
    localDefs.document = this.svgDocument;
    localDefs.parent = this.currentElement;
    parseAttributesCore(localDefs, paramAttributes);
    parseAttributesStyle(localDefs, paramAttributes);
    parseAttributesTransform(localDefs, paramAttributes);
    this.currentElement.addChild(localDefs);
    this.currentElement = localDefs;
  }

  private void dumpNode(SVG.SvgObject paramSvgObject, String paramString)
  {
    Log.d("SVGParser", paramString + paramSvgObject);
    if ((paramSvgObject instanceof SVG.SvgConditionalContainer))
    {
      String str = paramString + "  ";
      Iterator localIterator = ((SVG.SvgConditionalContainer)paramSvgObject).children.iterator();
      while (localIterator.hasNext())
        dumpNode((SVG.SvgObject)localIterator.next(), str);
    }
  }

  private void ellipse(Attributes paramAttributes)
    throws SAXException
  {
    debug("<ellipse>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Ellipse localEllipse = new SVG.Ellipse();
    localEllipse.document = this.svgDocument;
    localEllipse.parent = this.currentElement;
    parseAttributesCore(localEllipse, paramAttributes);
    parseAttributesStyle(localEllipse, paramAttributes);
    parseAttributesTransform(localEllipse, paramAttributes);
    parseAttributesConditional(localEllipse, paramAttributes);
    parseAttributesEllipse(localEllipse, paramAttributes);
    this.currentElement.addChild(localEllipse);
  }

  private static SVG.Style.FontStyle fontStyleKeyword(String paramString)
  {
    if ("italic".equals(paramString))
      return SVG.Style.FontStyle.Italic;
    if ("normal".equals(paramString))
      return SVG.Style.FontStyle.Normal;
    if ("oblique".equals(paramString))
      return SVG.Style.FontStyle.Oblique;
    return null;
  }

  private void g(Attributes paramAttributes)
    throws SAXException
  {
    debug("<g>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Group localGroup = new SVG.Group();
    localGroup.document = this.svgDocument;
    localGroup.parent = this.currentElement;
    parseAttributesCore(localGroup, paramAttributes);
    parseAttributesStyle(localGroup, paramAttributes);
    parseAttributesTransform(localGroup, paramAttributes);
    parseAttributesConditional(localGroup, paramAttributes);
    this.currentElement.addChild(localGroup);
    this.currentElement = localGroup;
  }

  private void image(Attributes paramAttributes)
    throws SAXException
  {
    debug("<image>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Image localImage = new SVG.Image();
    localImage.document = this.svgDocument;
    localImage.parent = this.currentElement;
    parseAttributesCore(localImage, paramAttributes);
    parseAttributesStyle(localImage, paramAttributes);
    parseAttributesTransform(localImage, paramAttributes);
    parseAttributesConditional(localImage, paramAttributes);
    parseAttributesImage(localImage, paramAttributes);
    this.currentElement.addChild(localImage);
    this.currentElement = localImage;
  }

  private void line(Attributes paramAttributes)
    throws SAXException
  {
    debug("<line>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Line localLine = new SVG.Line();
    localLine.document = this.svgDocument;
    localLine.parent = this.currentElement;
    parseAttributesCore(localLine, paramAttributes);
    parseAttributesStyle(localLine, paramAttributes);
    parseAttributesTransform(localLine, paramAttributes);
    parseAttributesConditional(localLine, paramAttributes);
    parseAttributesLine(localLine, paramAttributes);
    this.currentElement.addChild(localLine);
  }

  private void linearGradient(Attributes paramAttributes)
    throws SAXException
  {
    debug("<linearGradiant>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.SvgLinearGradient localSvgLinearGradient = new SVG.SvgLinearGradient();
    localSvgLinearGradient.document = this.svgDocument;
    localSvgLinearGradient.parent = this.currentElement;
    parseAttributesCore(localSvgLinearGradient, paramAttributes);
    parseAttributesStyle(localSvgLinearGradient, paramAttributes);
    parseAttributesGradient(localSvgLinearGradient, paramAttributes);
    parseAttributesLinearGradient(localSvgLinearGradient, paramAttributes);
    this.currentElement.addChild(localSvgLinearGradient);
    this.currentElement = localSvgLinearGradient;
  }

  private void marker(Attributes paramAttributes)
    throws SAXException
  {
    debug("<marker>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Marker localMarker = new SVG.Marker();
    localMarker.document = this.svgDocument;
    localMarker.parent = this.currentElement;
    parseAttributesCore(localMarker, paramAttributes);
    parseAttributesStyle(localMarker, paramAttributes);
    parseAttributesConditional(localMarker, paramAttributes);
    parseAttributesViewBox(localMarker, paramAttributes);
    parseAttributesMarker(localMarker, paramAttributes);
    this.currentElement.addChild(localMarker);
    this.currentElement = localMarker;
  }

  private void mask(Attributes paramAttributes)
    throws SAXException
  {
    debug("<mask>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Mask localMask = new SVG.Mask();
    localMask.document = this.svgDocument;
    localMask.parent = this.currentElement;
    parseAttributesCore(localMask, paramAttributes);
    parseAttributesStyle(localMask, paramAttributes);
    parseAttributesConditional(localMask, paramAttributes);
    parseAttributesMask(localMask, paramAttributes);
    this.currentElement.addChild(localMask);
    this.currentElement = localMask;
  }

  private void parseAttributesCircle(SVG.Circle paramCircle, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 12:
      case 13:
      case 14:
      }
      do
      {
        while (true)
        {
          i++;
          break;
          paramCircle.cx = parseLength(str);
          continue;
          paramCircle.cy = parseLength(str);
        }
        paramCircle.r = parseLength(str);
      }
      while (!paramCircle.r.isNegative());
      throw new SAXException("Invalid <circle> element. r cannot be negative");
    }
  }

  private void parseAttributesClipPath(SVG.ClipPath paramClipPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 38:
      }
      while (true)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramClipPath.clipPathUnitsAreUser = Boolean.valueOf(false);
        }
        else
        {
          if (!"userSpaceOnUse".equals(str))
            break label110;
          paramClipPath.clipPathUnitsAreUser = Boolean.valueOf(true);
        }
      }
      label110: throw new SAXException("Invalid value for attribute clipPathUnits");
    }
  }

  private void parseAttributesConditional(SVG.SvgConditional paramSvgConditional, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 21:
      case 22:
      case 23:
      case 24:
        while (true)
        {
          i++;
          break;
          paramSvgConditional.setRequiredFeatures(parseRequiredFeatures(str));
          continue;
          paramSvgConditional.setRequiredExtensions(str);
          continue;
          paramSvgConditional.setSystemLanguage(parseSystemLanguage(str));
          continue;
          paramSvgConditional.setRequiredFormats(parseRequiredFormats(str));
        }
      case 25:
      }
      List localList = parseFontFamily(str);
      if (localList != null);
      for (HashSet localHashSet = new HashSet(localList); ; localHashSet = new HashSet(0))
      {
        paramSvgConditional.setRequiredFonts(localHashSet);
        break;
      }
    }
  }

  private void parseAttributesCore(SVG.SvgElementBase paramSvgElementBase, Attributes paramAttributes)
    throws SAXException
  {
    for (int i = 0; ; i++)
    {
      String str1;
      if (i < paramAttributes.getLength())
      {
        str1 = paramAttributes.getQName(i);
        if ((str1.equals("id")) || (str1.equals("xml:id")))
          paramSvgElementBase.id = paramAttributes.getValue(i).trim();
      }
      else
      {
        return;
      }
      if (str1.equals("xml:space"))
      {
        String str2 = paramAttributes.getValue(i).trim();
        if ("default".equals(str2))
        {
          paramSvgElementBase.spacePreserve = Boolean.FALSE;
          return;
        }
        if ("preserve".equals(str2))
        {
          paramSvgElementBase.spacePreserve = Boolean.TRUE;
          return;
        }
        throw new SAXException("Invalid value for \"xml:space\" attribute: " + str2);
      }
    }
  }

  private void parseAttributesEllipse(SVG.Ellipse paramEllipse, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 12:
      case 13:
      case 10:
      case 11:
      }
      do
      {
        do
        {
          while (true)
          {
            i++;
            break;
            paramEllipse.cx = parseLength(str);
            continue;
            paramEllipse.cy = parseLength(str);
          }
          paramEllipse.rx = parseLength(str);
        }
        while (!paramEllipse.rx.isNegative());
        throw new SAXException("Invalid <ellipse> element. rx cannot be negative");
        paramEllipse.ry = parseLength(str);
      }
      while (!paramEllipse.ry.isNegative());
      throw new SAXException("Invalid <ellipse> element. ry cannot be negative");
    }
  }

  private void parseAttributesGradient(SVG.GradientElement paramGradientElement, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramGradientElement.gradientUnitsAreUser = Boolean.valueOf(false);
        }
        else if ("userSpaceOnUse".equals(str))
        {
          paramGradientElement.gradientUnitsAreUser = Boolean.valueOf(true);
        }
        else
        {
          throw new SAXException("Invalid value for attribute gradientUnits");
          paramGradientElement.gradientTransform = parseTransformList(str);
          continue;
          try
          {
            paramGradientElement.spreadMethod = SVG.GradientSpread.valueOf(str);
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw new SAXException("Invalid spreadMethod attribute. \"" + str + "\" is not a valid value.");
          }
          if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
            paramGradientElement.href = str;
        }
      }
    }
  }

  private void parseAttributesImage(SVG.Image paramImage, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      case 5:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 6:
      case 7:
      }
      while (true)
      {
        i++;
        break;
        paramImage.x = parseLength(str);
        continue;
        paramImage.y = parseLength(str);
        continue;
        paramImage.width = parseLength(str);
        if (paramImage.width.isNegative())
        {
          throw new SAXException("Invalid <use> element. width cannot be negative");
          paramImage.height = parseLength(str);
          if (paramImage.height.isNegative())
          {
            throw new SAXException("Invalid <use> element. height cannot be negative");
            if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
            {
              paramImage.href = str;
              continue;
              parsePreserveAspectRatio(paramImage, str);
            }
          }
        }
      }
    }
  }

  private void parseAttributesLine(SVG.Line paramLine, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 15:
      case 16:
      case 17:
      case 18:
      }
      while (true)
      {
        i++;
        break;
        paramLine.x1 = parseLength(str);
        continue;
        paramLine.y1 = parseLength(str);
        continue;
        paramLine.x2 = parseLength(str);
        continue;
        paramLine.y2 = parseLength(str);
      }
    }
  }

  private void parseAttributesLinearGradient(SVG.SvgLinearGradient paramSvgLinearGradient, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 15:
      case 16:
      case 17:
      case 18:
      }
      while (true)
      {
        i++;
        break;
        paramSvgLinearGradient.x1 = parseLength(str);
        continue;
        paramSvgLinearGradient.y1 = parseLength(str);
        continue;
        paramSvgLinearGradient.x2 = parseLength(str);
        continue;
        paramSvgLinearGradient.y2 = parseLength(str);
      }
    }
  }

  private void parseAttributesMarker(SVG.Marker paramMarker, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      }
      while (true)
      {
        i++;
        break;
        paramMarker.refX = parseLength(str);
        continue;
        paramMarker.refY = parseLength(str);
        continue;
        paramMarker.markerWidth = parseLength(str);
        if (paramMarker.markerWidth.isNegative())
        {
          throw new SAXException("Invalid <marker> element. markerWidth cannot be negative");
          paramMarker.markerHeight = parseLength(str);
          if (paramMarker.markerHeight.isNegative())
          {
            throw new SAXException("Invalid <marker> element. markerHeight cannot be negative");
            if ("strokeWidth".equals(str))
            {
              paramMarker.markerUnitsAreUser = false;
            }
            else if ("userSpaceOnUse".equals(str))
            {
              paramMarker.markerUnitsAreUser = true;
            }
            else
            {
              throw new SAXException("Invalid value for attribute markerUnits");
              if ("auto".equals(str))
                paramMarker.orient = Float.valueOf((0.0F / 0.0F));
              else
                paramMarker.orient = Float.valueOf(parseFloat(str));
            }
          }
        }
      }
    }
  }

  private void parseAttributesMask(SVG.Mask paramMask, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      }
      do
      {
        do
        {
          while (true)
          {
            i++;
            break;
            if ("objectBoundingBox".equals(str))
            {
              paramMask.maskUnitsAreUser = Boolean.valueOf(false);
            }
            else if ("userSpaceOnUse".equals(str))
            {
              paramMask.maskUnitsAreUser = Boolean.valueOf(true);
            }
            else
            {
              throw new SAXException("Invalid value for attribute maskUnits");
              if ("objectBoundingBox".equals(str))
              {
                paramMask.maskContentUnitsAreUser = Boolean.valueOf(false);
              }
              else if ("userSpaceOnUse".equals(str))
              {
                paramMask.maskContentUnitsAreUser = Boolean.valueOf(true);
              }
              else
              {
                throw new SAXException("Invalid value for attribute maskContentUnits");
                paramMask.x = parseLength(str);
                continue;
                paramMask.y = parseLength(str);
              }
            }
          }
          paramMask.width = parseLength(str);
        }
        while (!paramMask.width.isNegative());
        throw new SAXException("Invalid <mask> element. width cannot be negative");
        paramMask.height = parseLength(str);
      }
      while (!paramMask.height.isNegative());
      throw new SAXException("Invalid <mask> element. height cannot be negative");
    }
  }

  private void parseAttributesPath(SVG.Path paramPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 8:
      case 9:
      }
      do
      {
        while (true)
        {
          i++;
          break;
          paramPath.d = parsePath(str);
        }
        paramPath.pathLength = Float.valueOf(parseFloat(str));
      }
      while (paramPath.pathLength.floatValue() >= 0.0F);
      throw new SAXException("Invalid <path> element. pathLength cannot be negative");
    }
  }

  private void parseAttributesPattern(SVG.Pattern paramPattern, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramPattern.patternUnitsAreUser = Boolean.valueOf(false);
        }
        else if ("userSpaceOnUse".equals(str))
        {
          paramPattern.patternUnitsAreUser = Boolean.valueOf(true);
        }
        else
        {
          throw new SAXException("Invalid value for attribute patternUnits");
          if ("objectBoundingBox".equals(str))
          {
            paramPattern.patternContentUnitsAreUser = Boolean.valueOf(false);
          }
          else if ("userSpaceOnUse".equals(str))
          {
            paramPattern.patternContentUnitsAreUser = Boolean.valueOf(true);
          }
          else
          {
            throw new SAXException("Invalid value for attribute patternContentUnits");
            paramPattern.patternTransform = parseTransformList(str);
            continue;
            paramPattern.x = parseLength(str);
            continue;
            paramPattern.y = parseLength(str);
            continue;
            paramPattern.width = parseLength(str);
            if (paramPattern.width.isNegative())
            {
              throw new SAXException("Invalid <pattern> element. width cannot be negative");
              paramPattern.height = parseLength(str);
              if (paramPattern.height.isNegative())
              {
                throw new SAXException("Invalid <pattern> element. height cannot be negative");
                if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
                  paramPattern.href = str;
              }
            }
          }
        }
      }
    }
  }

  private void parseAttributesPolyLine(SVG.PolyLine paramPolyLine, Attributes paramAttributes, String paramString)
    throws SAXException
  {
    for (int i = 0; i < paramAttributes.getLength(); i++)
      if (SVGAttr.fromString(paramAttributes.getLocalName(i)) == SVGAttr.points)
      {
        TextScanner localTextScanner = new TextScanner(paramAttributes.getValue(i));
        ArrayList localArrayList = new ArrayList();
        localTextScanner.skipWhitespace();
        while (!localTextScanner.empty())
        {
          float f2 = localTextScanner.nextFloat();
          if (Float.isNaN(f2))
            throw new SAXException("Invalid <" + paramString + "> points attribute. Non-coordinate content found in list.");
          localTextScanner.skipCommaWhitespace();
          float f3 = localTextScanner.nextFloat();
          if (Float.isNaN(f3))
            throw new SAXException("Invalid <" + paramString + "> points attribute. There should be an even number of coordinates.");
          localTextScanner.skipCommaWhitespace();
          localArrayList.add(Float.valueOf(f2));
          localArrayList.add(Float.valueOf(f3));
        }
        paramPolyLine.points = new float[localArrayList.size()];
        int j = 0;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          float f1 = ((Float)localIterator.next()).floatValue();
          float[] arrayOfFloat = paramPolyLine.points;
          int k = j + 1;
          arrayOfFloat[j] = f1;
          j = k;
        }
      }
  }

  private void parseAttributesRadialGradient(SVG.SvgRadialGradient paramSvgRadialGradient, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      case ???:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        paramSvgRadialGradient.cx = parseLength(str);
        continue;
        paramSvgRadialGradient.cy = parseLength(str);
        continue;
        paramSvgRadialGradient.r = parseLength(str);
        if (paramSvgRadialGradient.r.isNegative())
        {
          throw new SAXException("Invalid <radialGradient> element. r cannot be negative");
          paramSvgRadialGradient.fx = parseLength(str);
          continue;
          paramSvgRadialGradient.fy = parseLength(str);
        }
      }
    }
  }

  private void parseAttributesRect(SVG.Rect paramRect, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 10:
      case 11:
      }
      do
      {
        do
        {
          do
          {
            do
            {
              while (true)
              {
                i++;
                break;
                paramRect.x = parseLength(str);
                continue;
                paramRect.y = parseLength(str);
              }
              paramRect.width = parseLength(str);
            }
            while (!paramRect.width.isNegative());
            throw new SAXException("Invalid <rect> element. width cannot be negative");
            paramRect.height = parseLength(str);
          }
          while (!paramRect.height.isNegative());
          throw new SAXException("Invalid <rect> element. height cannot be negative");
          paramRect.rx = parseLength(str);
        }
        while (!paramRect.rx.isNegative());
        throw new SAXException("Invalid <rect> element. rx cannot be negative");
        paramRect.ry = parseLength(str);
      }
      while (!paramRect.ry.isNegative());
      throw new SAXException("Invalid <rect> element. ry cannot be negative");
    }
  }

  private void parseAttributesSVG(SVG.Svg paramSvg, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
      while (true)
      {
        i++;
        break;
        paramSvg.x = parseLength(str);
        continue;
        paramSvg.y = parseLength(str);
        continue;
        paramSvg.width = parseLength(str);
        if (paramSvg.width.isNegative())
        {
          throw new SAXException("Invalid <svg> element. width cannot be negative");
          paramSvg.height = parseLength(str);
          if (paramSvg.height.isNegative())
          {
            throw new SAXException("Invalid <svg> element. height cannot be negative");
            paramSvg.version = str;
          }
        }
      }
    }
  }

  private void parseAttributesStop(SVG.Stop paramStop, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 37:
      }
      while (true)
      {
        i++;
        break;
        paramStop.offset = parseGradiantOffset(str);
      }
    }
  }

  private void parseAttributesStyle(SVG.SvgElementBase paramSvgElementBase, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      if (str.length() == 0);
      while (true)
      {
        i++;
        break;
        switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
        {
        default:
          if (paramSvgElementBase.baseStyle == null)
            paramSvgElementBase.baseStyle = new SVG.Style();
          processStyleProperty(paramSvgElementBase.baseStyle, paramAttributes.getLocalName(i), paramAttributes.getValue(i).trim());
          break;
        case 45:
          parseStyle(paramSvgElementBase, str);
          break;
        case 46:
          paramSvgElementBase.classNames = CSSParser.parseClassAttribute(str);
        }
      }
    }
  }

  private void parseAttributesTRef(SVG.TRef paramTRef, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 6:
      }
      while (true)
      {
        i++;
        break;
        if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
          paramTRef.href = str;
      }
    }
  }

  private void parseAttributesTextPath(SVG.TextPath paramTextPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
        {
          paramTextPath.href = str;
          continue;
          paramTextPath.startOffset = parseLength(str);
        }
      }
    }
  }

  private void parseAttributesTextPosition(SVG.TextPositionedContainer paramTextPositionedContainer, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        paramTextPositionedContainer.x = parseLengthList(str);
        continue;
        paramTextPositionedContainer.y = parseLengthList(str);
        continue;
        paramTextPositionedContainer.dx = parseLengthList(str);
        continue;
        paramTextPositionedContainer.dy = parseLengthList(str);
      }
    }
  }

  private void parseAttributesTransform(SVG.HasTransform paramHasTransform, Attributes paramAttributes)
    throws SAXException
  {
    for (int i = 0; i < paramAttributes.getLength(); i++)
      if (SVGAttr.fromString(paramAttributes.getLocalName(i)) == SVGAttr.transform)
        paramHasTransform.setTransform(parseTransformList(paramAttributes.getValue(i)));
  }

  private void parseAttributesUse(SVG.Use paramUse, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      case 5:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 6:
      }
      while (true)
      {
        i++;
        break;
        paramUse.x = parseLength(str);
        continue;
        paramUse.y = parseLength(str);
        continue;
        paramUse.width = parseLength(str);
        if (paramUse.width.isNegative())
        {
          throw new SAXException("Invalid <use> element. width cannot be negative");
          paramUse.height = parseLength(str);
          if (paramUse.height.isNegative())
          {
            throw new SAXException("Invalid <use> element. height cannot be negative");
            if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
              paramUse.href = str;
          }
        }
      }
    }
  }

  private void parseAttributesViewBox(SVG.SvgViewBoxContainer paramSvgViewBoxContainer, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      default:
      case ???:
      case ???:
      }
      while (true)
      {
        i++;
        break;
        paramSvgViewBoxContainer.viewBox = parseViewBox(str);
        continue;
        parsePreserveAspectRatio(paramSvgViewBoxContainer, str);
      }
    }
  }

  private void parseCSSStyleSheet(String paramString)
    throws SAXException
  {
    CSSParser localCSSParser = new CSSParser(CSSParser.MediaType.screen);
    this.svgDocument.addCSSRules(localCSSParser.parse(paramString));
  }

  private static SVG.CSSClipRect parseClip(String paramString)
    throws SAXException
  {
    if ("auto".equals(paramString))
      return null;
    if (!paramString.toLowerCase(Locale.US).startsWith("rect("))
      throw new SAXException("Invalid clip attribute shape. Only rect() is supported.");
    TextScanner localTextScanner = new TextScanner(paramString.substring(5));
    localTextScanner.skipWhitespace();
    SVG.Length localLength1 = parseLengthOrAuto(localTextScanner);
    localTextScanner.skipCommaWhitespace();
    SVG.Length localLength2 = parseLengthOrAuto(localTextScanner);
    localTextScanner.skipCommaWhitespace();
    SVG.Length localLength3 = parseLengthOrAuto(localTextScanner);
    localTextScanner.skipCommaWhitespace();
    SVG.Length localLength4 = parseLengthOrAuto(localTextScanner);
    localTextScanner.skipWhitespace();
    if (!localTextScanner.consume(')'))
      throw new SAXException("Bad rect() clip definition: " + paramString);
    return new SVG.CSSClipRect(localLength1, localLength2, localLength3, localLength4);
  }

  private static SVG.Colour parseColour(String paramString)
    throws SAXException
  {
    if (paramString.charAt(0) == '#')
    {
      IntegerParser localIntegerParser = IntegerParser.parseHex(paramString, 1, paramString.length());
      if (localIntegerParser == null)
        throw new SAXException("Bad hex colour value: " + paramString);
      int i = localIntegerParser.getEndPos();
      if (i == 7)
        return new SVG.Colour(localIntegerParser.value());
      if (i == 4)
      {
        int j = localIntegerParser.value();
        int k = j & 0xF00;
        int m = j & 0xF0;
        int n = j & 0xF;
        return new SVG.Colour(n | (k << 16 | k << 12 | m << 8 | m << 4 | n << 4));
      }
      throw new SAXException("Bad hex colour value: " + paramString);
    }
    if (paramString.toLowerCase(Locale.US).startsWith("rgb("))
    {
      TextScanner localTextScanner = new TextScanner(paramString.substring(4));
      localTextScanner.skipWhitespace();
      float f1 = localTextScanner.nextFloat();
      if ((!Float.isNaN(f1)) && (localTextScanner.consume('%')))
        f1 = 256.0F * f1 / 100.0F;
      float f2 = localTextScanner.checkedNextFloat(f1);
      if ((!Float.isNaN(f2)) && (localTextScanner.consume('%')))
        f2 = 256.0F * f2 / 100.0F;
      float f3 = localTextScanner.checkedNextFloat(f2);
      if ((!Float.isNaN(f3)) && (localTextScanner.consume('%')))
        f3 = 256.0F * f3 / 100.0F;
      localTextScanner.skipWhitespace();
      if ((Float.isNaN(f3)) || (!localTextScanner.consume(')')))
        throw new SAXException("Bad rgb() colour value: " + paramString);
      return new SVG.Colour(clamp255(f1) << 16 | clamp255(f2) << 8 | clamp255(f3));
    }
    return parseColourKeyword(paramString);
  }

  private static SVG.Colour parseColourKeyword(String paramString)
    throws SAXException
  {
    Integer localInteger = ColourKeywords.get(paramString.toLowerCase(Locale.US));
    if (localInteger == null)
      throw new SAXException("Invalid colour keyword: " + paramString);
    return new SVG.Colour(localInteger.intValue());
  }

  private static SVG.SvgPaint parseColourSpecifer(String paramString)
    throws SAXException
  {
    if (paramString.equals("none"))
      return null;
    if (paramString.equals("currentColor"))
      return SVG.CurrentColor.getInstance();
    return parseColour(paramString);
  }

  private static SVG.Style.FillRule parseFillRule(String paramString)
    throws SAXException
  {
    if ("nonzero".equals(paramString))
      return SVG.Style.FillRule.NonZero;
    if ("evenodd".equals(paramString))
      return SVG.Style.FillRule.EvenOdd;
    throw new SAXException("Invalid fill-rule property: " + paramString);
  }

  private static float parseFloat(String paramString)
    throws SAXException
  {
    int i = paramString.length();
    if (i == 0)
      throw new SAXException("Invalid float value (empty string)");
    return parseFloat(paramString, 0, i);
  }

  private static float parseFloat(String paramString, int paramInt1, int paramInt2)
    throws SAXException
  {
    float f = new NumberParser().parseNumber(paramString, paramInt1, paramInt2);
    if (!Float.isNaN(f))
      return f;
    throw new SAXException("Invalid float value: " + paramString);
  }

  private static void parseFont(SVG.Style paramStyle, String paramString)
    throws SAXException
  {
    Integer localInteger = null;
    SVG.Style.FontStyle localFontStyle1 = null;
    Object localObject = null;
    if ("|caption|icon|menu|message-box|small-caption|status-bar|".indexOf('|' + paramString + '|') != -1)
      return;
    TextScanner localTextScanner = new TextScanner(paramString);
    SVG.Length localLength;
    String str2;
    while (true)
    {
      String str1 = localTextScanner.nextToken('/');
      localTextScanner.skipWhitespace();
      if (str1 == null)
        throw new SAXException("Invalid font style attribute: missing font size and family");
      if ((localInteger != null) && (localFontStyle1 != null));
      do
      {
        localLength = parseFontSize(str1);
        if (!localTextScanner.consume('/'))
          break label207;
        localTextScanner.skipWhitespace();
        str2 = localTextScanner.nextToken();
        if (str2 != null)
          break label196;
        throw new SAXException("Invalid font style attribute: missing line-height");
        if (str1.equals("normal"))
          break;
        if (localInteger == null)
        {
          localInteger = FontWeightKeywords.get(str1);
          if (localInteger != null)
            break;
        }
        if (localFontStyle1 == null)
        {
          localFontStyle1 = fontStyleKeyword(str1);
          if (localFontStyle1 != null)
            break;
        }
      }
      while ((localObject != null) || (!str1.equals("small-caps")));
      localObject = str1;
    }
    label196: parseLength(str2);
    localTextScanner.skipWhitespace();
    label207: paramStyle.fontFamily = parseFontFamily(localTextScanner.restOfText());
    paramStyle.fontSize = localLength;
    int i;
    if (localInteger == null)
    {
      i = 400;
      paramStyle.fontWeight = Integer.valueOf(i);
      if (localFontStyle1 != null)
        break label280;
    }
    label280: for (SVG.Style.FontStyle localFontStyle2 = SVG.Style.FontStyle.Normal; ; localFontStyle2 = localFontStyle1)
    {
      paramStyle.fontStyle = localFontStyle2;
      paramStyle.specifiedFlags = (0x1E000 | paramStyle.specifiedFlags);
      return;
      i = localInteger.intValue();
      break;
    }
  }

  private static List<String> parseFontFamily(String paramString)
    throws SAXException
  {
    Object localObject = null;
    TextScanner localTextScanner = new TextScanner(paramString);
    do
    {
      String str = localTextScanner.nextQuotedString();
      if (str == null)
        str = localTextScanner.nextToken(',');
      if (str == null)
        return localObject;
      if (localObject == null)
        localObject = new ArrayList();
      ((List)localObject).add(str);
      localTextScanner.skipCommaWhitespace();
    }
    while (!localTextScanner.empty());
    return localObject;
  }

  private static SVG.Length parseFontSize(String paramString)
    throws SAXException
  {
    SVG.Length localLength = FontSizeKeywords.get(paramString);
    if (localLength == null)
      localLength = parseLength(paramString);
    return localLength;
  }

  private static SVG.Style.FontStyle parseFontStyle(String paramString)
    throws SAXException
  {
    SVG.Style.FontStyle localFontStyle = fontStyleKeyword(paramString);
    if (localFontStyle != null)
      return localFontStyle;
    throw new SAXException("Invalid font-style property: " + paramString);
  }

  private static Integer parseFontWeight(String paramString)
    throws SAXException
  {
    Integer localInteger = FontWeightKeywords.get(paramString);
    if (localInteger == null)
      throw new SAXException("Invalid font-weight property: " + paramString);
    return localInteger;
  }

  private static String parseFunctionalIRI(String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString1.equals("none"))
      return null;
    if ((!paramString1.startsWith("url(")) || (!paramString1.endsWith(")")))
      throw new SAXException("Bad " + paramString2 + " attribute. Expected \"none\" or \"url()\" format");
    return paramString1.substring(4, -1 + paramString1.length()).trim();
  }

  private Float parseGradiantOffset(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0)
      throw new SAXException("Invalid offset value in <stop> (empty string)");
    int i = paramString.length();
    int j = paramString.charAt(-1 + paramString.length());
    int k = 0;
    if (j == 37)
    {
      i--;
      k = 1;
    }
    while (true)
    {
      float f;
      try
      {
        f = parseFloat(paramString, 0, i);
        if (k != 0)
        {
          f /= 100.0F;
          break label132;
          Float localFloat = Float.valueOf(f);
          return localFloat;
          if (f <= 100.0F)
            continue;
          f = 100.0F;
          continue;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new SAXException("Invalid offset value in <stop>: " + paramString, localNumberFormatException);
      }
      label132: if (f < 0.0F)
        f = 0.0F;
    }
  }

  protected static SVG.Length parseLength(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0)
      throw new SAXException("Invalid length value (empty string)");
    int i = paramString.length();
    Object localObject = SVG.Unit.px;
    char c = paramString.charAt(i - 1);
    if (c == '%')
    {
      i--;
      localObject = SVG.Unit.percent;
    }
    try
    {
      while (true)
      {
        SVG.Length localLength = new SVG.Length(parseFloat(paramString, 0, i), (SVG.Unit)localObject);
        return localLength;
        if ((i <= 2) || (!Character.isLetter(c)) || (!Character.isLetter(paramString.charAt(i - 2))))
          continue;
        i -= 2;
        String str = paramString.substring(i);
        try
        {
          SVG.Unit localUnit = SVG.Unit.valueOf(str.toLowerCase(Locale.US));
          localObject = localUnit;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new SAXException("Invalid length unit specifier: " + paramString);
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new SAXException("Invalid length value: " + paramString, localNumberFormatException);
    }
  }

  private static List<SVG.Length> parseLengthList(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0)
      throw new SAXException("Invalid length list (empty string)");
    ArrayList localArrayList = new ArrayList(1);
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    while (!localTextScanner.empty())
    {
      float f = localTextScanner.nextFloat();
      if (Float.isNaN(f))
        throw new SAXException("Invalid length list value: " + localTextScanner.ahead());
      SVG.Unit localUnit = localTextScanner.nextUnit();
      if (localUnit == null)
        localUnit = SVG.Unit.px;
      localArrayList.add(new SVG.Length(f, localUnit));
      localTextScanner.skipCommaWhitespace();
    }
    return localArrayList;
  }

  private static SVG.Length parseLengthOrAuto(TextScanner paramTextScanner)
  {
    if (paramTextScanner.consume("auto"))
      return new SVG.Length(0.0F);
    return paramTextScanner.nextLength();
  }

  private static float parseOpacity(String paramString)
    throws SAXException
  {
    float f = parseFloat(paramString);
    if (f < 0.0F)
      f = 0.0F;
    while (f <= 1.0F)
      return f;
    return 1.0F;
  }

  private static Boolean parseOverflow(String paramString)
    throws SAXException
  {
    if (("visible".equals(paramString)) || ("auto".equals(paramString)))
      return Boolean.TRUE;
    if (("hidden".equals(paramString)) || ("scroll".equals(paramString)))
      return Boolean.FALSE;
    throw new SAXException("Invalid toverflow property: " + paramString);
  }

  private static SVG.SvgPaint parsePaintSpecifier(String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString1.startsWith("url("))
    {
      int i = paramString1.indexOf(")");
      if (i == -1)
        throw new SAXException("Bad " + paramString2 + " attribute. Unterminated url() reference");
      String str1 = paramString1.substring(4, i).trim();
      String str2 = paramString1.substring(i + 1).trim();
      int j = str2.length();
      SVG.SvgPaint localSvgPaint = null;
      if (j > 0)
        localSvgPaint = parseColourSpecifer(str2);
      return new SVG.PaintReference(str1, localSvgPaint);
    }
    return parseColourSpecifer(paramString1);
  }

  private static SVG.PathDefinition parsePath(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    SVG.PathDefinition localPathDefinition = new SVG.PathDefinition();
    if (localTextScanner.empty());
    int i;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    int k;
    do
    {
      return localPathDefinition;
      i = localTextScanner.nextChar().intValue();
      int j = i;
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = 0.0F;
      f6 = 0.0F;
      if (j == 77)
        break;
      k = i;
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = 0.0F;
      f6 = 0.0F;
    }
    while (k != 109);
    label90: localTextScanner.skipWhitespace();
    float f33;
    float f34;
    float f35;
    Boolean localBoolean1;
    Boolean localBoolean2;
    float f37;
    float f36;
    switch (i)
    {
    default:
      return localPathDefinition;
    case 65:
    case 97:
      f33 = localTextScanner.nextFloat();
      f34 = localTextScanner.checkedNextFloat(f33);
      f35 = localTextScanner.checkedNextFloat(f34);
      localBoolean1 = localTextScanner.checkedNextFlag(Float.valueOf(f35));
      localBoolean2 = localTextScanner.checkedNextFlag(localBoolean1);
      if (localBoolean2 == null)
      {
        f37 = (0.0F / 0.0F);
        f36 = f37;
        if ((!Float.isNaN(f37)) && (f33 >= 0.0F) && (f34 >= 0.0F))
          break label1441;
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      break;
    case 77:
    case 109:
      label321: float f31 = localTextScanner.nextFloat();
      float f32 = localTextScanner.checkedNextFloat(f31);
      if (Float.isNaN(f32))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if ((i == 109) && (!localPathDefinition.isEmpty()))
      {
        f31 += f1;
        f32 += f2;
      }
      localPathDefinition.moveTo(f31, f32);
      f3 = f31;
      f5 = f31;
      f1 = f31;
      f4 = f32;
      f6 = f32;
      f2 = f32;
      if (i == 109)
        i = 108;
      break;
    case 76:
    case 108:
    case 67:
    case 99:
    case 83:
    case 115:
    case 90:
    case 122:
    case 72:
    case 104:
    case 86:
    case 118:
    case 81:
    case 113:
    case 84:
    case 116:
    }
    while (true)
    {
      localTextScanner.skipCommaWhitespace();
      if (localTextScanner.empty())
        break;
      if (!localTextScanner.hasLetter())
        break label90;
      i = localTextScanner.nextChar().intValue();
      break label90;
      i = 76;
      continue;
      float f29 = localTextScanner.nextFloat();
      float f30 = localTextScanner.checkedNextFloat(f29);
      if (Float.isNaN(f30))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 108)
      {
        f29 += f1;
        f30 += f2;
      }
      localPathDefinition.lineTo(f29, f30);
      f3 = f29;
      f1 = f29;
      f4 = f30;
      f2 = f30;
      continue;
      float f23 = localTextScanner.nextFloat();
      float f24 = localTextScanner.checkedNextFloat(f23);
      float f25 = localTextScanner.checkedNextFloat(f24);
      float f26 = localTextScanner.checkedNextFloat(f25);
      float f27 = localTextScanner.checkedNextFloat(f26);
      float f28 = localTextScanner.checkedNextFloat(f27);
      if (Float.isNaN(f28))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 99)
      {
        f27 += f1;
        f28 += f2;
        f23 += f1;
        f24 += f2;
        f25 += f1;
        f26 += f2;
      }
      localPathDefinition.cubicTo(f23, f24, f25, f26, f27, f28);
      f3 = f25;
      f4 = f26;
      f1 = f27;
      f2 = f28;
      continue;
      float f17 = 2.0F * f1 - f3;
      float f18 = 2.0F * f2 - f4;
      float f19 = localTextScanner.nextFloat();
      float f20 = localTextScanner.checkedNextFloat(f19);
      float f21 = localTextScanner.checkedNextFloat(f20);
      float f22 = localTextScanner.checkedNextFloat(f21);
      if (Float.isNaN(f22))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 115)
      {
        f21 += f1;
        f22 += f2;
        f19 += f1;
        f20 += f2;
      }
      localPathDefinition.cubicTo(f17, f18, f19, f20, f21, f22);
      f3 = f19;
      f4 = f20;
      f1 = f21;
      f2 = f22;
      continue;
      localPathDefinition.close();
      f3 = f5;
      f1 = f5;
      f4 = f6;
      f2 = f6;
      continue;
      float f16 = localTextScanner.nextFloat();
      if (Float.isNaN(f16))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 104)
        f16 += f1;
      localPathDefinition.lineTo(f16, f2);
      f3 = f16;
      f1 = f16;
      continue;
      float f15 = localTextScanner.nextFloat();
      if (Float.isNaN(f15))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 118)
        f15 += f2;
      localPathDefinition.lineTo(f1, f15);
      f4 = f15;
      f2 = f15;
      continue;
      float f11 = localTextScanner.nextFloat();
      float f12 = localTextScanner.checkedNextFloat(f11);
      float f13 = localTextScanner.checkedNextFloat(f12);
      float f14 = localTextScanner.checkedNextFloat(f13);
      if (Float.isNaN(f14))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 113)
      {
        f13 += f1;
        f14 += f2;
        f11 += f1;
        f12 += f2;
      }
      localPathDefinition.quadTo(f11, f12, f13, f14);
      f3 = f11;
      f4 = f12;
      f1 = f13;
      f2 = f14;
      continue;
      float f7 = 2.0F * f1 - f3;
      float f8 = 2.0F * f2 - f4;
      float f9 = localTextScanner.nextFloat();
      float f10 = localTextScanner.checkedNextFloat(f9);
      if (Float.isNaN(f10))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)i + " path segment");
        return localPathDefinition;
      }
      if (i == 116)
      {
        f9 += f1;
        f10 += f2;
      }
      localPathDefinition.quadTo(f7, f8, f9, f10);
      f3 = f7;
      f4 = f8;
      f1 = f9;
      f2 = f10;
      continue;
      f36 = localTextScanner.possibleNextFloat();
      f37 = localTextScanner.checkedNextFloat(f36);
      break label321;
      label1441: if (i == 97)
      {
        f36 += f1;
        f37 += f2;
      }
      localPathDefinition.arcTo(f33, f34, f35, localBoolean1.booleanValue(), localBoolean2.booleanValue(), f36, f37);
      f3 = f36;
      f1 = f36;
      f4 = f37;
      f2 = f37;
    }
  }

  private static void parsePreserveAspectRatio(SVG.SvgPreserveAspectRatioContainer paramSvgPreserveAspectRatioContainer, String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    String str1 = localTextScanner.nextToken();
    if ("defer".equals(str1))
    {
      localTextScanner.skipWhitespace();
      str1 = localTextScanner.nextToken();
    }
    PreserveAspectRatio.Alignment localAlignment = AspectRatioKeywords.get(str1);
    localTextScanner.skipWhitespace();
    boolean bool = localTextScanner.empty();
    PreserveAspectRatio.Scale localScale = null;
    String str2;
    if (!bool)
    {
      str2 = localTextScanner.nextToken();
      if (!str2.equals("meet"))
        break label99;
    }
    for (localScale = PreserveAspectRatio.Scale.Meet; ; localScale = PreserveAspectRatio.Scale.Slice)
    {
      paramSvgPreserveAspectRatioContainer.preserveAspectRatio = new PreserveAspectRatio(localAlignment, localScale);
      return;
      label99: if (!str2.equals("slice"))
        break;
    }
    throw new SAXException("Invalid preserveAspectRatio definition: " + paramString);
  }

  private static Set<String> parseRequiredFeatures(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    HashSet localHashSet = new HashSet();
    if (!localTextScanner.empty())
    {
      String str = localTextScanner.nextToken();
      if (str.startsWith("http://www.w3.org/TR/SVG11/feature#"))
        localHashSet.add(str.substring("http://www.w3.org/TR/SVG11/feature#".length()));
      while (true)
      {
        localTextScanner.skipWhitespace();
        break;
        localHashSet.add("UNSUPPORTED");
      }
    }
    return localHashSet;
  }

  private static Set<String> parseRequiredFormats(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    HashSet localHashSet = new HashSet();
    while (!localTextScanner.empty())
    {
      localHashSet.add(localTextScanner.nextToken());
      localTextScanner.skipWhitespace();
    }
    return localHashSet;
  }

  private static SVG.Length[] parseStrokeDashArray(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    if (localTextScanner.empty());
    float f;
    ArrayList localArrayList;
    do
    {
      SVG.Length localLength1;
      do
      {
        return null;
        localLength1 = localTextScanner.nextLength();
      }
      while (localLength1 == null);
      if (localLength1.isNegative())
        throw new SAXException("Invalid stroke-dasharray. Dash segemnts cannot be negative: " + paramString);
      f = localLength1.floatValue();
      localArrayList = new ArrayList();
      localArrayList.add(localLength1);
      while (!localTextScanner.empty())
      {
        localTextScanner.skipCommaWhitespace();
        SVG.Length localLength2 = localTextScanner.nextLength();
        if (localLength2 == null)
          throw new SAXException("Invalid stroke-dasharray. Non-Length content found: " + paramString);
        if (localLength2.isNegative())
          throw new SAXException("Invalid stroke-dasharray. Dash segemnts cannot be negative: " + paramString);
        localArrayList.add(localLength2);
        f += localLength2.floatValue();
      }
    }
    while (f == 0.0F);
    return (SVG.Length[])localArrayList.toArray(new SVG.Length[localArrayList.size()]);
  }

  private static SVG.Style.LineCaps parseStrokeLineCap(String paramString)
    throws SAXException
  {
    if ("butt".equals(paramString))
      return SVG.Style.LineCaps.Butt;
    if ("round".equals(paramString))
      return SVG.Style.LineCaps.Round;
    if ("square".equals(paramString))
      return SVG.Style.LineCaps.Square;
    throw new SAXException("Invalid stroke-linecap property: " + paramString);
  }

  private static SVG.Style.LineJoin parseStrokeLineJoin(String paramString)
    throws SAXException
  {
    if ("miter".equals(paramString))
      return SVG.Style.LineJoin.Miter;
    if ("round".equals(paramString))
      return SVG.Style.LineJoin.Round;
    if ("bevel".equals(paramString))
      return SVG.Style.LineJoin.Bevel;
    throw new SAXException("Invalid stroke-linejoin property: " + paramString);
  }

  private static void parseStyle(SVG.SvgElementBase paramSvgElementBase, String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString.replaceAll("/\\*.*?\\*/", ""));
    while (true)
    {
      String str1 = localTextScanner.nextToken(':');
      localTextScanner.skipWhitespace();
      if (!localTextScanner.consume(':'));
      String str2;
      do
      {
        return;
        localTextScanner.skipWhitespace();
        str2 = localTextScanner.nextToken(';');
      }
      while (str2 == null);
      localTextScanner.skipWhitespace();
      if ((localTextScanner.empty()) || (localTextScanner.consume(';')))
      {
        if (paramSvgElementBase.style == null)
          paramSvgElementBase.style = new SVG.Style();
        processStyleProperty(paramSvgElementBase.style, str1, str2);
        localTextScanner.skipWhitespace();
      }
    }
  }

  private static Set<String> parseSystemLanguage(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    HashSet localHashSet = new HashSet();
    while (!localTextScanner.empty())
    {
      String str = localTextScanner.nextToken();
      int i = str.indexOf('-');
      if (i != -1)
        str = str.substring(0, i);
      localHashSet.add(new Locale(str, "", "").getLanguage());
      localTextScanner.skipWhitespace();
    }
    return localHashSet;
  }

  private static SVG.Style.TextAnchor parseTextAnchor(String paramString)
    throws SAXException
  {
    if ("start".equals(paramString))
      return SVG.Style.TextAnchor.Start;
    if ("middle".equals(paramString))
      return SVG.Style.TextAnchor.Middle;
    if ("end".equals(paramString))
      return SVG.Style.TextAnchor.End;
    throw new SAXException("Invalid text-anchor property: " + paramString);
  }

  private static SVG.Style.TextDecoration parseTextDecoration(String paramString)
    throws SAXException
  {
    if ("none".equals(paramString))
      return SVG.Style.TextDecoration.None;
    if ("underline".equals(paramString))
      return SVG.Style.TextDecoration.Underline;
    if ("overline".equals(paramString))
      return SVG.Style.TextDecoration.Overline;
    if ("line-through".equals(paramString))
      return SVG.Style.TextDecoration.LineThrough;
    if ("blink".equals(paramString))
      return SVG.Style.TextDecoration.Blink;
    throw new SAXException("Invalid text-decoration property: " + paramString);
  }

  private static SVG.Style.TextDirection parseTextDirection(String paramString)
    throws SAXException
  {
    if ("ltr".equals(paramString))
      return SVG.Style.TextDirection.LTR;
    if ("rtl".equals(paramString))
      return SVG.Style.TextDirection.RTL;
    throw new SAXException("Invalid direction property: " + paramString);
  }

  private Matrix parseTransformList(String paramString)
    throws SAXException
  {
    Matrix localMatrix1 = new Matrix();
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    while (true)
    {
      String str;
      if (!localTextScanner.empty())
      {
        str = localTextScanner.nextFunction();
        if (str == null)
          throw new SAXException("Bad transform function encountered in transform list: " + paramString);
        if (!str.equals("matrix"))
          break label271;
        localTextScanner.skipWhitespace();
        float f10 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f11 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f12 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f13 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f14 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f15 = localTextScanner.nextFloat();
        localTextScanner.skipWhitespace();
        if ((Float.isNaN(f15)) || (!localTextScanner.consume(')')))
          throw new SAXException("Invalid transform list: " + paramString);
        Matrix localMatrix2 = new Matrix();
        localMatrix2.setValues(new float[] { f10, f12, f14, f11, f13, f15, 0.0F, 0.0F, 1.0F });
        localMatrix1.preConcat(localMatrix2);
      }
      while (localTextScanner.empty())
      {
        return localMatrix1;
        label271: if (str.equals("translate"))
        {
          localTextScanner.skipWhitespace();
          float f8 = localTextScanner.nextFloat();
          float f9 = localTextScanner.possibleNextFloat();
          localTextScanner.skipWhitespace();
          if ((Float.isNaN(f8)) || (!localTextScanner.consume(')')))
            throw new SAXException("Invalid transform list: " + paramString);
          if (Float.isNaN(f9))
            localMatrix1.preTranslate(f8, 0.0F);
          else
            localMatrix1.preTranslate(f8, f9);
        }
        else if (str.equals("scale"))
        {
          localTextScanner.skipWhitespace();
          float f6 = localTextScanner.nextFloat();
          float f7 = localTextScanner.possibleNextFloat();
          localTextScanner.skipWhitespace();
          if ((Float.isNaN(f6)) || (!localTextScanner.consume(')')))
            throw new SAXException("Invalid transform list: " + paramString);
          if (Float.isNaN(f7))
            localMatrix1.preScale(f6, f6);
          else
            localMatrix1.preScale(f6, f7);
        }
        else if (str.equals("rotate"))
        {
          localTextScanner.skipWhitespace();
          float f3 = localTextScanner.nextFloat();
          float f4 = localTextScanner.possibleNextFloat();
          float f5 = localTextScanner.possibleNextFloat();
          localTextScanner.skipWhitespace();
          if ((Float.isNaN(f3)) || (!localTextScanner.consume(')')))
            throw new SAXException("Invalid transform list: " + paramString);
          if (Float.isNaN(f4))
            localMatrix1.preRotate(f3);
          else if (!Float.isNaN(f5))
            localMatrix1.preRotate(f3, f4, f5);
          else
            throw new SAXException("Invalid transform list: " + paramString);
        }
        else if (str.equals("skewX"))
        {
          localTextScanner.skipWhitespace();
          float f2 = localTextScanner.nextFloat();
          localTextScanner.skipWhitespace();
          if ((Float.isNaN(f2)) || (!localTextScanner.consume(')')))
            throw new SAXException("Invalid transform list: " + paramString);
          localMatrix1.preSkew((float)Math.tan(Math.toRadians(f2)), 0.0F);
        }
        else if (str.equals("skewY"))
        {
          localTextScanner.skipWhitespace();
          float f1 = localTextScanner.nextFloat();
          localTextScanner.skipWhitespace();
          if ((Float.isNaN(f1)) || (!localTextScanner.consume(')')))
            throw new SAXException("Invalid transform list: " + paramString);
          localMatrix1.preSkew(0.0F, (float)Math.tan(Math.toRadians(f1)));
        }
        else if (str != null)
        {
          throw new SAXException("Invalid transform list fn: " + str + ")");
        }
      }
      localTextScanner.skipCommaWhitespace();
    }
  }

  private static SVG.Style.VectorEffect parseVectorEffect(String paramString)
    throws SAXException
  {
    if ("none".equals(paramString))
      return SVG.Style.VectorEffect.None;
    if ("non-scaling-stroke".equals(paramString))
      return SVG.Style.VectorEffect.NonScalingStroke;
    throw new SAXException("Invalid vector-effect property: " + paramString);
  }

  private static SVG.Box parseViewBox(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    float f1 = localTextScanner.nextFloat();
    localTextScanner.skipCommaWhitespace();
    float f2 = localTextScanner.nextFloat();
    localTextScanner.skipCommaWhitespace();
    float f3 = localTextScanner.nextFloat();
    localTextScanner.skipCommaWhitespace();
    float f4 = localTextScanner.nextFloat();
    if ((Float.isNaN(f1)) || (Float.isNaN(f2)) || (Float.isNaN(f3)) || (Float.isNaN(f4)))
      throw new SAXException("Invalid viewBox definition - should have four numbers");
    if (f3 < 0.0F)
      throw new SAXException("Invalid viewBox. width cannot be negative");
    if (f4 < 0.0F)
      throw new SAXException("Invalid viewBox. height cannot be negative");
    return new SVG.Box(f1, f2, f3, f4);
  }

  private void path(Attributes paramAttributes)
    throws SAXException
  {
    debug("<path>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Path localPath = new SVG.Path();
    localPath.document = this.svgDocument;
    localPath.parent = this.currentElement;
    parseAttributesCore(localPath, paramAttributes);
    parseAttributesStyle(localPath, paramAttributes);
    parseAttributesTransform(localPath, paramAttributes);
    parseAttributesConditional(localPath, paramAttributes);
    parseAttributesPath(localPath, paramAttributes);
    this.currentElement.addChild(localPath);
  }

  private void pattern(Attributes paramAttributes)
    throws SAXException
  {
    debug("<pattern>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Pattern localPattern = new SVG.Pattern();
    localPattern.document = this.svgDocument;
    localPattern.parent = this.currentElement;
    parseAttributesCore(localPattern, paramAttributes);
    parseAttributesStyle(localPattern, paramAttributes);
    parseAttributesConditional(localPattern, paramAttributes);
    parseAttributesViewBox(localPattern, paramAttributes);
    parseAttributesPattern(localPattern, paramAttributes);
    this.currentElement.addChild(localPattern);
    this.currentElement = localPattern;
  }

  private void polygon(Attributes paramAttributes)
    throws SAXException
  {
    debug("<polygon>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Polygon localPolygon = new SVG.Polygon();
    localPolygon.document = this.svgDocument;
    localPolygon.parent = this.currentElement;
    parseAttributesCore(localPolygon, paramAttributes);
    parseAttributesStyle(localPolygon, paramAttributes);
    parseAttributesTransform(localPolygon, paramAttributes);
    parseAttributesConditional(localPolygon, paramAttributes);
    parseAttributesPolyLine(localPolygon, paramAttributes, "polygon");
    this.currentElement.addChild(localPolygon);
  }

  private void polyline(Attributes paramAttributes)
    throws SAXException
  {
    debug("<polyline>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.PolyLine localPolyLine = new SVG.PolyLine();
    localPolyLine.document = this.svgDocument;
    localPolyLine.parent = this.currentElement;
    parseAttributesCore(localPolyLine, paramAttributes);
    parseAttributesStyle(localPolyLine, paramAttributes);
    parseAttributesTransform(localPolyLine, paramAttributes);
    parseAttributesConditional(localPolyLine, paramAttributes);
    parseAttributesPolyLine(localPolyLine, paramAttributes, "polyline");
    this.currentElement.addChild(localPolyLine);
  }

  protected static void processStyleProperty(SVG.Style paramStyle, String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString2.length() == 0);
    while (paramString2.equals("inherit"))
      return;
    switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramString1).ordinal()])
    {
    default:
      return;
    case 47:
      paramStyle.fill = parsePaintSpecifier(paramString2, "fill");
      paramStyle.specifiedFlags = (1L | paramStyle.specifiedFlags);
      return;
    case 48:
      paramStyle.fillRule = parseFillRule(paramString2);
      paramStyle.specifiedFlags = (0x2 | paramStyle.specifiedFlags);
      return;
    case 49:
      paramStyle.fillOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x4 | paramStyle.specifiedFlags);
      return;
    case 50:
      paramStyle.stroke = parsePaintSpecifier(paramString2, "stroke");
      paramStyle.specifiedFlags = (0x8 | paramStyle.specifiedFlags);
      return;
    case 51:
      paramStyle.strokeOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x10 | paramStyle.specifiedFlags);
      return;
    case 52:
      paramStyle.strokeWidth = parseLength(paramString2);
      paramStyle.specifiedFlags = (0x20 | paramStyle.specifiedFlags);
      return;
    case 53:
      paramStyle.strokeLineCap = parseStrokeLineCap(paramString2);
      paramStyle.specifiedFlags = (0x40 | paramStyle.specifiedFlags);
      return;
    case 54:
      paramStyle.strokeLineJoin = parseStrokeLineJoin(paramString2);
      paramStyle.specifiedFlags = (0x80 | paramStyle.specifiedFlags);
      return;
    case 55:
      paramStyle.strokeMiterLimit = Float.valueOf(parseFloat(paramString2));
      paramStyle.specifiedFlags = (0x100 | paramStyle.specifiedFlags);
      return;
    case 56:
      if ("none".equals(paramString2));
      for (paramStyle.strokeDashArray = null; ; paramStyle.strokeDashArray = parseStrokeDashArray(paramString2))
      {
        paramStyle.specifiedFlags = (0x200 | paramStyle.specifiedFlags);
        return;
      }
    case 57:
      paramStyle.strokeDashOffset = parseLength(paramString2);
      paramStyle.specifiedFlags = (0x400 | paramStyle.specifiedFlags);
      return;
    case 58:
      paramStyle.opacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x800 | paramStyle.specifiedFlags);
      return;
    case 59:
      paramStyle.color = parseColour(paramString2);
      paramStyle.specifiedFlags = (0x1000 | paramStyle.specifiedFlags);
      return;
    case 60:
      parseFont(paramStyle, paramString2);
      return;
    case 61:
      paramStyle.fontFamily = parseFontFamily(paramString2);
      paramStyle.specifiedFlags = (0x2000 | paramStyle.specifiedFlags);
      return;
    case 62:
      paramStyle.fontSize = parseFontSize(paramString2);
      paramStyle.specifiedFlags = (0x4000 | paramStyle.specifiedFlags);
      return;
    case 63:
      paramStyle.fontWeight = parseFontWeight(paramString2);
      paramStyle.specifiedFlags = (0x8000 | paramStyle.specifiedFlags);
      return;
    case 64:
      paramStyle.fontStyle = parseFontStyle(paramString2);
      paramStyle.specifiedFlags = (0x10000 | paramStyle.specifiedFlags);
      return;
    case 65:
      paramStyle.textDecoration = parseTextDecoration(paramString2);
      paramStyle.specifiedFlags = (0x20000 | paramStyle.specifiedFlags);
      return;
    case 66:
      paramStyle.direction = parseTextDirection(paramString2);
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
    case 67:
      paramStyle.textAnchor = parseTextAnchor(paramString2);
      paramStyle.specifiedFlags = (0x40000 | paramStyle.specifiedFlags);
      return;
    case 68:
      paramStyle.overflow = parseOverflow(paramString2);
      paramStyle.specifiedFlags = (0x80000 | paramStyle.specifiedFlags);
      return;
    case 69:
      paramStyle.markerStart = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.markerMid = paramStyle.markerStart;
      paramStyle.markerEnd = paramStyle.markerStart;
      paramStyle.specifiedFlags = (0xE00000 | paramStyle.specifiedFlags);
      return;
    case 70:
      paramStyle.markerStart = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x200000 | paramStyle.specifiedFlags);
      return;
    case 71:
      paramStyle.markerMid = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x400000 | paramStyle.specifiedFlags);
      return;
    case 72:
      paramStyle.markerEnd = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x800000 | paramStyle.specifiedFlags);
      return;
    case 73:
      if ((paramString2.indexOf('|') >= 0) || ("|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|".indexOf('|' + paramString2 + '|') == -1))
        throw new SAXException("Invalid value for \"display\" attribute: " + paramString2);
      if (!paramString2.equals("none"));
      for (boolean bool = true; ; bool = false)
      {
        paramStyle.display = Boolean.valueOf(bool);
        paramStyle.specifiedFlags = (0x1000000 | paramStyle.specifiedFlags);
        return;
      }
    case 74:
      if ((paramString2.indexOf('|') >= 0) || ("|visible|hidden|collapse|".indexOf('|' + paramString2 + '|') == -1))
        throw new SAXException("Invalid value for \"visibility\" attribute: " + paramString2);
      paramStyle.visibility = Boolean.valueOf(paramString2.equals("visible"));
      paramStyle.specifiedFlags = (0x2000000 | paramStyle.specifiedFlags);
      return;
    case 75:
      if (paramString2.equals("currentColor"));
      for (paramStyle.stopColor = SVG.CurrentColor.getInstance(); ; paramStyle.stopColor = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x4000000 | paramStyle.specifiedFlags);
        return;
      }
    case 76:
      paramStyle.stopOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x8000000 | paramStyle.specifiedFlags);
      return;
    case 77:
      paramStyle.clip = parseClip(paramString2);
      paramStyle.specifiedFlags = (0x100000 | paramStyle.specifiedFlags);
      return;
    case 78:
      paramStyle.clipPath = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x10000000 | paramStyle.specifiedFlags);
      return;
    case 79:
      paramStyle.clipRule = parseFillRule(paramString2);
      paramStyle.specifiedFlags = (0x20000000 | paramStyle.specifiedFlags);
      return;
    case 80:
      paramStyle.mask = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x40000000 | paramStyle.specifiedFlags);
      return;
    case 81:
      if (paramString2.equals("currentColor"));
      for (paramStyle.solidColor = SVG.CurrentColor.getInstance(); ; paramStyle.solidColor = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x80000000 | paramStyle.specifiedFlags);
        return;
      }
    case 82:
      paramStyle.solidOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
    case 83:
      if (paramString2.equals("currentColor"));
      for (paramStyle.viewportFill = SVG.CurrentColor.getInstance(); ; paramStyle.viewportFill = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
        return;
      }
    case 84:
      paramStyle.viewportFillOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
    case 85:
    }
    paramStyle.vectorEffect = parseVectorEffect(paramString2);
    paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
  }

  private void radialGradient(Attributes paramAttributes)
    throws SAXException
  {
    debug("<radialGradient>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.SvgRadialGradient localSvgRadialGradient = new SVG.SvgRadialGradient();
    localSvgRadialGradient.document = this.svgDocument;
    localSvgRadialGradient.parent = this.currentElement;
    parseAttributesCore(localSvgRadialGradient, paramAttributes);
    parseAttributesStyle(localSvgRadialGradient, paramAttributes);
    parseAttributesGradient(localSvgRadialGradient, paramAttributes);
    parseAttributesRadialGradient(localSvgRadialGradient, paramAttributes);
    this.currentElement.addChild(localSvgRadialGradient);
    this.currentElement = localSvgRadialGradient;
  }

  private void rect(Attributes paramAttributes)
    throws SAXException
  {
    debug("<rect>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Rect localRect = new SVG.Rect();
    localRect.document = this.svgDocument;
    localRect.parent = this.currentElement;
    parseAttributesCore(localRect, paramAttributes);
    parseAttributesStyle(localRect, paramAttributes);
    parseAttributesTransform(localRect, paramAttributes);
    parseAttributesConditional(localRect, paramAttributes);
    parseAttributesRect(localRect, paramAttributes);
    this.currentElement.addChild(localRect);
  }

  private void solidColor(Attributes paramAttributes)
    throws SAXException
  {
    debug("<solidColor>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.SolidColor localSolidColor = new SVG.SolidColor();
    localSolidColor.document = this.svgDocument;
    localSolidColor.parent = this.currentElement;
    parseAttributesCore(localSolidColor, paramAttributes);
    parseAttributesStyle(localSolidColor, paramAttributes);
    this.currentElement.addChild(localSolidColor);
    this.currentElement = localSolidColor;
  }

  private void stop(Attributes paramAttributes)
    throws SAXException
  {
    debug("<stop>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    if (!(this.currentElement instanceof SVG.GradientElement))
      throw new SAXException("Invalid document. <stop> elements are only valid inside <linearGradiant> or <radialGradient> elements.");
    SVG.Stop localStop = new SVG.Stop();
    localStop.document = this.svgDocument;
    localStop.parent = this.currentElement;
    parseAttributesCore(localStop, paramAttributes);
    parseAttributesStyle(localStop, paramAttributes);
    parseAttributesStop(localStop, paramAttributes);
    this.currentElement.addChild(localStop);
    this.currentElement = localStop;
  }

  private void style(Attributes paramAttributes)
    throws SAXException
  {
    debug("<style>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    boolean bool = true;
    Object localObject = "all";
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default:
      case 87:
      case 88:
      }
      while (true)
      {
        i++;
        break;
        bool = str.equals("text/css");
        continue;
        localObject = str;
      }
    }
    if ((bool) && (CSSParser.mediaMatches((String)localObject, CSSParser.MediaType.screen)))
    {
      this.inStyleElement = true;
      return;
    }
    this.ignoring = true;
    this.ignoreDepth = 1;
  }

  private void svg(Attributes paramAttributes)
    throws SAXException
  {
    debug("<svg>", new Object[0]);
    SVG.Svg localSvg = new SVG.Svg();
    localSvg.document = this.svgDocument;
    localSvg.parent = this.currentElement;
    parseAttributesCore(localSvg, paramAttributes);
    parseAttributesStyle(localSvg, paramAttributes);
    parseAttributesConditional(localSvg, paramAttributes);
    parseAttributesViewBox(localSvg, paramAttributes);
    parseAttributesSVG(localSvg, paramAttributes);
    if (this.currentElement == null)
      this.svgDocument.setRootElement(localSvg);
    while (true)
    {
      this.currentElement = localSvg;
      return;
      this.currentElement.addChild(localSvg);
    }
  }

  private void symbol(Attributes paramAttributes)
    throws SAXException
  {
    debug("<symbol>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Symbol localSymbol = new SVG.Symbol();
    localSymbol.document = this.svgDocument;
    localSymbol.parent = this.currentElement;
    parseAttributesCore(localSymbol, paramAttributes);
    parseAttributesStyle(localSymbol, paramAttributes);
    parseAttributesConditional(localSymbol, paramAttributes);
    parseAttributesViewBox(localSymbol, paramAttributes);
    this.currentElement.addChild(localSymbol);
    this.currentElement = localSymbol;
  }

  private void text(Attributes paramAttributes)
    throws SAXException
  {
    debug("<text>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Text localText = new SVG.Text();
    localText.document = this.svgDocument;
    localText.parent = this.currentElement;
    parseAttributesCore(localText, paramAttributes);
    parseAttributesStyle(localText, paramAttributes);
    parseAttributesTransform(localText, paramAttributes);
    parseAttributesConditional(localText, paramAttributes);
    parseAttributesTextPosition(localText, paramAttributes);
    this.currentElement.addChild(localText);
    this.currentElement = localText;
  }

  private void textPath(Attributes paramAttributes)
    throws SAXException
  {
    debug("<textPath>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.TextPath localTextPath = new SVG.TextPath();
    localTextPath.document = this.svgDocument;
    localTextPath.parent = this.currentElement;
    parseAttributesCore(localTextPath, paramAttributes);
    parseAttributesStyle(localTextPath, paramAttributes);
    parseAttributesConditional(localTextPath, paramAttributes);
    parseAttributesTextPath(localTextPath, paramAttributes);
    this.currentElement.addChild(localTextPath);
    this.currentElement = localTextPath;
    if ((localTextPath.parent instanceof SVG.TextRoot))
    {
      localTextPath.setTextRoot((SVG.TextRoot)localTextPath.parent);
      return;
    }
    localTextPath.setTextRoot(((SVG.TextChild)localTextPath.parent).getTextRoot());
  }

  private void tref(Attributes paramAttributes)
    throws SAXException
  {
    debug("<tref>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    if (!(this.currentElement instanceof SVG.TextContainer))
      throw new SAXException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
    SVG.TRef localTRef = new SVG.TRef();
    localTRef.document = this.svgDocument;
    localTRef.parent = this.currentElement;
    parseAttributesCore(localTRef, paramAttributes);
    parseAttributesStyle(localTRef, paramAttributes);
    parseAttributesConditional(localTRef, paramAttributes);
    parseAttributesTRef(localTRef, paramAttributes);
    this.currentElement.addChild(localTRef);
    if ((localTRef.parent instanceof SVG.TextRoot))
    {
      localTRef.setTextRoot((SVG.TextRoot)localTRef.parent);
      return;
    }
    localTRef.setTextRoot(((SVG.TextChild)localTRef.parent).getTextRoot());
  }

  private void tspan(Attributes paramAttributes)
    throws SAXException
  {
    debug("<tspan>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    if (!(this.currentElement instanceof SVG.TextContainer))
      throw new SAXException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
    SVG.TSpan localTSpan = new SVG.TSpan();
    localTSpan.document = this.svgDocument;
    localTSpan.parent = this.currentElement;
    parseAttributesCore(localTSpan, paramAttributes);
    parseAttributesStyle(localTSpan, paramAttributes);
    parseAttributesConditional(localTSpan, paramAttributes);
    parseAttributesTextPosition(localTSpan, paramAttributes);
    this.currentElement.addChild(localTSpan);
    this.currentElement = localTSpan;
    if ((localTSpan.parent instanceof SVG.TextRoot))
    {
      localTSpan.setTextRoot((SVG.TextRoot)localTSpan.parent);
      return;
    }
    localTSpan.setTextRoot(((SVG.TextChild)localTSpan.parent).getTextRoot());
  }

  private void use(Attributes paramAttributes)
    throws SAXException
  {
    debug("<use>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Use localUse = new SVG.Use();
    localUse.document = this.svgDocument;
    localUse.parent = this.currentElement;
    parseAttributesCore(localUse, paramAttributes);
    parseAttributesStyle(localUse, paramAttributes);
    parseAttributesTransform(localUse, paramAttributes);
    parseAttributesConditional(localUse, paramAttributes);
    parseAttributesUse(localUse, paramAttributes);
    this.currentElement.addChild(localUse);
    this.currentElement = localUse;
  }

  private void view(Attributes paramAttributes)
    throws SAXException
  {
    debug("<view>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.View localView = new SVG.View();
    localView.document = this.svgDocument;
    localView.parent = this.currentElement;
    parseAttributesCore(localView, paramAttributes);
    parseAttributesConditional(localView, paramAttributes);
    parseAttributesViewBox(localView, paramAttributes);
    this.currentElement.addChild(localView);
    this.currentElement = localView;
  }

  private void zwitch(Attributes paramAttributes)
    throws SAXException
  {
    debug("<switch>", new Object[0]);
    if (this.currentElement == null)
      throw new SAXException("Invalid document. Root element must be <svg>");
    SVG.Switch localSwitch = new SVG.Switch();
    localSwitch.document = this.svgDocument;
    localSwitch.parent = this.currentElement;
    parseAttributesCore(localSwitch, paramAttributes);
    parseAttributesStyle(localSwitch, paramAttributes);
    parseAttributesTransform(localSwitch, paramAttributes);
    parseAttributesConditional(localSwitch, paramAttributes);
    this.currentElement.addChild(localSwitch);
    this.currentElement = localSwitch;
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.ignoring);
    do
    {
      return;
      if (this.inMetadataElement)
      {
        if (this.metadataElementContents == null)
          this.metadataElementContents = new StringBuilder(paramInt2);
        this.metadataElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
        return;
      }
      if (this.inStyleElement)
      {
        if (this.styleElementContents == null)
          this.styleElementContents = new StringBuilder(paramInt2);
        this.styleElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
        return;
      }
    }
    while (!(this.currentElement instanceof SVG.TextContainer));
    SVG.SvgConditionalContainer localSvgConditionalContainer = (SVG.SvgConditionalContainer)this.currentElement;
    int i = localSvgConditionalContainer.children.size();
    if (i == 0);
    for (SVG.SvgObject localSvgObject = null; (localSvgObject instanceof SVG.TextSequence); localSvgObject = (SVG.SvgObject)localSvgConditionalContainer.children.get(i - 1))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      SVG.TextSequence localTextSequence = (SVG.TextSequence)localSvgObject;
      localTextSequence.text += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    ((SVG.SvgConditionalContainer)this.currentElement).addChild(new SVG.TextSequence(new String(paramArrayOfChar, paramInt1, paramInt2)));
  }

  public void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.ignoring);
    while (!this.inStyleElement)
      return;
    if (this.styleElementContents == null)
      this.styleElementContents = new StringBuilder(paramInt2);
    this.styleElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endDocument()
    throws SAXException
  {
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (this.ignoring)
    {
      int i = -1 + this.ignoreDepth;
      this.ignoreDepth = i;
      if (i == 0)
        this.ignoring = false;
    }
    do
    {
      do
        return;
      while ((!"http://www.w3.org/2000/svg".equals(paramString1)) && (!"".equals(paramString1)));
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.fromString(paramString2).ordinal()])
      {
      case 3:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 15:
      default:
        return;
      case 1:
      case 2:
      case 4:
      case 5:
      case 13:
      case 14:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 31:
        this.currentElement = ((SVG.SvgObject)this.currentElement).parent;
        return;
      case 22:
      case 23:
        this.inMetadataElement = false;
        if (this.metadataTag == SVGElem.title)
          this.svgDocument.setTitle(this.metadataElementContents.toString());
        while (true)
        {
          this.metadataElementContents.setLength(0);
          return;
          if (this.metadataTag == SVGElem.desc)
            this.svgDocument.setDesc(this.metadataElementContents.toString());
        }
      case 30:
      }
    }
    while (this.styleElementContents == null);
    this.inStyleElement = false;
    parseCSSStyleSheet(this.styleElementContents.toString());
    this.styleElementContents.setLength(0);
  }

  // ERROR //
  protected SVG parse(java.io.InputStream paramInputStream)
    throws SVGParseException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 1894	java/io/InputStream:markSupported	()Z
    //   4: ifne +230 -> 234
    //   7: new 1896	java/io/BufferedInputStream
    //   10: dup
    //   11: aload_1
    //   12: invokespecial 1899	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   15: astore_2
    //   16: aload_2
    //   17: iconst_3
    //   18: invokevirtual 1902	java/io/InputStream:mark	(I)V
    //   21: aload_2
    //   22: invokevirtual 1905	java/io/InputStream:read	()I
    //   25: aload_2
    //   26: invokevirtual 1905	java/io/InputStream:read	()I
    //   29: bipush 8
    //   31: ishl
    //   32: iadd
    //   33: istore 15
    //   35: aload_2
    //   36: invokevirtual 1908	java/io/InputStream:reset	()V
    //   39: iload 15
    //   41: ldc_w 1909
    //   44: if_icmpne +184 -> 228
    //   47: new 1911	java/util/zip/GZIPInputStream
    //   50: dup
    //   51: aload_2
    //   52: invokespecial 1912	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   55: astore 4
    //   57: invokestatic 1918	javax/xml/parsers/SAXParserFactory:newInstance	()Ljavax/xml/parsers/SAXParserFactory;
    //   60: astore 5
    //   62: aload 5
    //   64: invokevirtual 1922	javax/xml/parsers/SAXParserFactory:newSAXParser	()Ljavax/xml/parsers/SAXParser;
    //   67: invokevirtual 1928	javax/xml/parsers/SAXParser:getXMLReader	()Lorg/xml/sax/XMLReader;
    //   70: astore 12
    //   72: aload 12
    //   74: aload_0
    //   75: invokeinterface 1934 2 0
    //   80: aload 12
    //   82: ldc_w 1936
    //   85: aload_0
    //   86: invokeinterface 1940 3 0
    //   91: aload 12
    //   93: new 1942	org/xml/sax/InputSource
    //   96: dup
    //   97: aload 4
    //   99: invokespecial 1943	org/xml/sax/InputSource:<init>	(Ljava/io/InputStream;)V
    //   102: invokeinterface 1946 2 0
    //   107: aload 4
    //   109: invokevirtual 1947	java/io/InputStream:close	()V
    //   112: aload_0
    //   113: getfield 53	com/caverock/androidsvg/SVGParser:svgDocument	Lcom/caverock/androidsvg/SVG;
    //   116: areturn
    //   117: astore_3
    //   118: aload_2
    //   119: astore 4
    //   121: goto -64 -> 57
    //   124: astore 13
    //   126: ldc 20
    //   128: ldc_w 1949
    //   131: invokestatic 1154	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   134: pop
    //   135: goto -23 -> 112
    //   138: astore 11
    //   140: new 1885	com/caverock/androidsvg/SVGParseException
    //   143: dup
    //   144: ldc_w 1951
    //   147: aload 11
    //   149: invokespecial 1954	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   152: athrow
    //   153: astore 7
    //   155: aload 4
    //   157: invokevirtual 1947	java/io/InputStream:close	()V
    //   160: aload 7
    //   162: athrow
    //   163: astore 10
    //   165: new 1885	com/caverock/androidsvg/SVGParseException
    //   168: dup
    //   169: ldc_w 1956
    //   172: aload 10
    //   174: invokespecial 1954	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   177: athrow
    //   178: astore 6
    //   180: new 1885	com/caverock/androidsvg/SVGParseException
    //   183: dup
    //   184: new 152	java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   191: ldc_w 1958
    //   194: invokevirtual 157	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: aload 6
    //   199: invokevirtual 1961	org/xml/sax/SAXException:getMessage	()Ljava/lang/String;
    //   202: invokevirtual 157	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: invokevirtual 164	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   208: aload 6
    //   210: invokespecial 1954	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   213: athrow
    //   214: astore 8
    //   216: ldc 20
    //   218: ldc_w 1949
    //   221: invokestatic 1154	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   224: pop
    //   225: goto -65 -> 160
    //   228: aload_2
    //   229: astore 4
    //   231: goto -174 -> 57
    //   234: aload_1
    //   235: astore_2
    //   236: goto -220 -> 16
    //
    // Exception table:
    //   from	to	target	type
    //   16	39	117	java/io/IOException
    //   47	57	117	java/io/IOException
    //   107	112	124	java/io/IOException
    //   62	107	138	java/io/IOException
    //   62	107	153	finally
    //   140	153	153	finally
    //   165	178	153	finally
    //   180	214	153	finally
    //   62	107	163	javax/xml/parsers/ParserConfigurationException
    //   62	107	178	org/xml/sax/SAXException
    //   155	160	214	java/io/IOException
  }

  protected void setSupportedFormats(String[] paramArrayOfString)
  {
    this.supportedFormats = new HashSet(paramArrayOfString.length);
    Collections.addAll(this.supportedFormats, paramArrayOfString);
  }

  public void startDocument()
    throws SAXException
  {
    this.svgDocument = new SVG();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (this.ignoring)
      this.ignoreDepth = (1 + this.ignoreDepth);
    while ((!"http://www.w3.org/2000/svg".equals(paramString1)) && (!"".equals(paramString1)))
      return;
    SVGElem localSVGElem = SVGElem.fromString(paramString2);
    switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[localSVGElem.ordinal()])
    {
    default:
      this.ignoring = true;
      this.ignoreDepth = 1;
      return;
    case 1:
      svg(paramAttributes);
      return;
    case 2:
    case 3:
      g(paramAttributes);
      return;
    case 4:
      defs(paramAttributes);
      return;
    case 5:
      use(paramAttributes);
      return;
    case 6:
      path(paramAttributes);
      return;
    case 7:
      rect(paramAttributes);
      return;
    case 8:
      circle(paramAttributes);
      return;
    case 9:
      ellipse(paramAttributes);
      return;
    case 10:
      line(paramAttributes);
      return;
    case 11:
      polyline(paramAttributes);
      return;
    case 12:
      polygon(paramAttributes);
      return;
    case 13:
      text(paramAttributes);
      return;
    case 14:
      tspan(paramAttributes);
      return;
    case 15:
      tref(paramAttributes);
      return;
    case 16:
      zwitch(paramAttributes);
      return;
    case 17:
      symbol(paramAttributes);
      return;
    case 18:
      marker(paramAttributes);
      return;
    case 19:
      linearGradient(paramAttributes);
      return;
    case 20:
      radialGradient(paramAttributes);
      return;
    case 21:
      stop(paramAttributes);
      return;
    case 22:
    case 23:
      this.inMetadataElement = true;
      this.metadataTag = localSVGElem;
      return;
    case 24:
      clipPath(paramAttributes);
      return;
    case 25:
      textPath(paramAttributes);
      return;
    case 26:
      pattern(paramAttributes);
      return;
    case 27:
      image(paramAttributes);
      return;
    case 28:
      view(paramAttributes);
      return;
    case 29:
      mask(paramAttributes);
      return;
    case 30:
      style(paramAttributes);
      return;
    case 31:
    }
    solidColor(paramAttributes);
  }

  private static class AspectRatioKeywords
  {
    private static final Map<String, PreserveAspectRatio.Alignment> aspectRatioKeywords = new HashMap(10);

    static
    {
      aspectRatioKeywords.put("none", PreserveAspectRatio.Alignment.None);
      aspectRatioKeywords.put("xMinYMin", PreserveAspectRatio.Alignment.XMinYMin);
      aspectRatioKeywords.put("xMidYMin", PreserveAspectRatio.Alignment.XMidYMin);
      aspectRatioKeywords.put("xMaxYMin", PreserveAspectRatio.Alignment.XMaxYMin);
      aspectRatioKeywords.put("xMinYMid", PreserveAspectRatio.Alignment.XMinYMid);
      aspectRatioKeywords.put("xMidYMid", PreserveAspectRatio.Alignment.XMidYMid);
      aspectRatioKeywords.put("xMaxYMid", PreserveAspectRatio.Alignment.XMaxYMid);
      aspectRatioKeywords.put("xMinYMax", PreserveAspectRatio.Alignment.XMinYMax);
      aspectRatioKeywords.put("xMidYMax", PreserveAspectRatio.Alignment.XMidYMax);
      aspectRatioKeywords.put("xMaxYMax", PreserveAspectRatio.Alignment.XMaxYMax);
    }

    public static PreserveAspectRatio.Alignment get(String paramString)
    {
      return (PreserveAspectRatio.Alignment)aspectRatioKeywords.get(paramString);
    }
  }

  private static class ColourKeywords
  {
    private static final Map<String, Integer> colourKeywords = new HashMap(47);

    static
    {
      colourKeywords.put("aliceblue", Integer.valueOf(15792383));
      colourKeywords.put("antiquewhite", Integer.valueOf(16444375));
      colourKeywords.put("aqua", Integer.valueOf(65535));
      colourKeywords.put("aquamarine", Integer.valueOf(8388564));
      colourKeywords.put("azure", Integer.valueOf(15794175));
      colourKeywords.put("beige", Integer.valueOf(16119260));
      colourKeywords.put("bisque", Integer.valueOf(16770244));
      colourKeywords.put("black", Integer.valueOf(0));
      colourKeywords.put("blanchedalmond", Integer.valueOf(16772045));
      colourKeywords.put("blue", Integer.valueOf(255));
      colourKeywords.put("blueviolet", Integer.valueOf(9055202));
      colourKeywords.put("brown", Integer.valueOf(10824234));
      colourKeywords.put("burlywood", Integer.valueOf(14596231));
      colourKeywords.put("cadetblue", Integer.valueOf(6266528));
      colourKeywords.put("chartreuse", Integer.valueOf(8388352));
      colourKeywords.put("chocolate", Integer.valueOf(13789470));
      colourKeywords.put("coral", Integer.valueOf(16744272));
      colourKeywords.put("cornflowerblue", Integer.valueOf(6591981));
      colourKeywords.put("cornsilk", Integer.valueOf(16775388));
      colourKeywords.put("crimson", Integer.valueOf(14423100));
      colourKeywords.put("cyan", Integer.valueOf(65535));
      colourKeywords.put("darkblue", Integer.valueOf(139));
      colourKeywords.put("darkcyan", Integer.valueOf(35723));
      colourKeywords.put("darkgoldenrod", Integer.valueOf(12092939));
      colourKeywords.put("darkgray", Integer.valueOf(11119017));
      colourKeywords.put("darkgreen", Integer.valueOf(25600));
      colourKeywords.put("darkgrey", Integer.valueOf(11119017));
      colourKeywords.put("darkkhaki", Integer.valueOf(12433259));
      colourKeywords.put("darkmagenta", Integer.valueOf(9109643));
      colourKeywords.put("darkolivegreen", Integer.valueOf(5597999));
      colourKeywords.put("darkorange", Integer.valueOf(16747520));
      colourKeywords.put("darkorchid", Integer.valueOf(10040012));
      colourKeywords.put("darkred", Integer.valueOf(9109504));
      colourKeywords.put("darksalmon", Integer.valueOf(15308410));
      colourKeywords.put("darkseagreen", Integer.valueOf(9419919));
      colourKeywords.put("darkslateblue", Integer.valueOf(4734347));
      colourKeywords.put("darkslategray", Integer.valueOf(3100495));
      colourKeywords.put("darkslategrey", Integer.valueOf(3100495));
      colourKeywords.put("darkturquoise", Integer.valueOf(52945));
      colourKeywords.put("darkviolet", Integer.valueOf(9699539));
      colourKeywords.put("deeppink", Integer.valueOf(16716947));
      colourKeywords.put("deepskyblue", Integer.valueOf(49151));
      colourKeywords.put("dimgray", Integer.valueOf(6908265));
      colourKeywords.put("dimgrey", Integer.valueOf(6908265));
      colourKeywords.put("dodgerblue", Integer.valueOf(2003199));
      colourKeywords.put("firebrick", Integer.valueOf(11674146));
      colourKeywords.put("floralwhite", Integer.valueOf(16775920));
      colourKeywords.put("forestgreen", Integer.valueOf(2263842));
      colourKeywords.put("fuchsia", Integer.valueOf(16711935));
      colourKeywords.put("gainsboro", Integer.valueOf(14474460));
      colourKeywords.put("ghostwhite", Integer.valueOf(16316671));
      colourKeywords.put("gold", Integer.valueOf(16766720));
      colourKeywords.put("goldenrod", Integer.valueOf(14329120));
      colourKeywords.put("gray", Integer.valueOf(8421504));
      colourKeywords.put("green", Integer.valueOf(32768));
      colourKeywords.put("greenyellow", Integer.valueOf(11403055));
      colourKeywords.put("grey", Integer.valueOf(8421504));
      colourKeywords.put("honeydew", Integer.valueOf(15794160));
      colourKeywords.put("hotpink", Integer.valueOf(16738740));
      colourKeywords.put("indianred", Integer.valueOf(13458524));
      colourKeywords.put("indigo", Integer.valueOf(4915330));
      colourKeywords.put("ivory", Integer.valueOf(16777200));
      colourKeywords.put("khaki", Integer.valueOf(15787660));
      colourKeywords.put("lavender", Integer.valueOf(15132410));
      colourKeywords.put("lavenderblush", Integer.valueOf(16773365));
      colourKeywords.put("lawngreen", Integer.valueOf(8190976));
      colourKeywords.put("lemonchiffon", Integer.valueOf(16775885));
      colourKeywords.put("lightblue", Integer.valueOf(11393254));
      colourKeywords.put("lightcoral", Integer.valueOf(15761536));
      colourKeywords.put("lightcyan", Integer.valueOf(14745599));
      colourKeywords.put("lightgoldenrodyellow", Integer.valueOf(16448210));
      colourKeywords.put("lightgray", Integer.valueOf(13882323));
      colourKeywords.put("lightgreen", Integer.valueOf(9498256));
      colourKeywords.put("lightgrey", Integer.valueOf(13882323));
      colourKeywords.put("lightpink", Integer.valueOf(16758465));
      colourKeywords.put("lightsalmon", Integer.valueOf(16752762));
      colourKeywords.put("lightseagreen", Integer.valueOf(2142890));
      colourKeywords.put("lightskyblue", Integer.valueOf(8900346));
      colourKeywords.put("lightslategray", Integer.valueOf(7833753));
      colourKeywords.put("lightslategrey", Integer.valueOf(7833753));
      colourKeywords.put("lightsteelblue", Integer.valueOf(11584734));
      colourKeywords.put("lightyellow", Integer.valueOf(16777184));
      colourKeywords.put("lime", Integer.valueOf(65280));
      colourKeywords.put("limegreen", Integer.valueOf(3329330));
      colourKeywords.put("linen", Integer.valueOf(16445670));
      colourKeywords.put("magenta", Integer.valueOf(16711935));
      colourKeywords.put("maroon", Integer.valueOf(8388608));
      colourKeywords.put("mediumaquamarine", Integer.valueOf(6737322));
      colourKeywords.put("mediumblue", Integer.valueOf(205));
      colourKeywords.put("mediumorchid", Integer.valueOf(12211667));
      colourKeywords.put("mediumpurple", Integer.valueOf(9662683));
      colourKeywords.put("mediumseagreen", Integer.valueOf(3978097));
      colourKeywords.put("mediumslateblue", Integer.valueOf(8087790));
      colourKeywords.put("mediumspringgreen", Integer.valueOf(64154));
      colourKeywords.put("mediumturquoise", Integer.valueOf(4772300));
      colourKeywords.put("mediumvioletred", Integer.valueOf(13047173));
      colourKeywords.put("midnightblue", Integer.valueOf(1644912));
      colourKeywords.put("mintcream", Integer.valueOf(16121850));
      colourKeywords.put("mistyrose", Integer.valueOf(16770273));
      colourKeywords.put("moccasin", Integer.valueOf(16770229));
      colourKeywords.put("navajowhite", Integer.valueOf(16768685));
      colourKeywords.put("navy", Integer.valueOf(128));
      colourKeywords.put("oldlace", Integer.valueOf(16643558));
      colourKeywords.put("olive", Integer.valueOf(8421376));
      colourKeywords.put("olivedrab", Integer.valueOf(7048739));
      colourKeywords.put("orange", Integer.valueOf(16753920));
      colourKeywords.put("orangered", Integer.valueOf(16729344));
      colourKeywords.put("orchid", Integer.valueOf(14315734));
      colourKeywords.put("palegoldenrod", Integer.valueOf(15657130));
      colourKeywords.put("palegreen", Integer.valueOf(10025880));
      colourKeywords.put("paleturquoise", Integer.valueOf(11529966));
      colourKeywords.put("palevioletred", Integer.valueOf(14381203));
      colourKeywords.put("papayawhip", Integer.valueOf(16773077));
      colourKeywords.put("peachpuff", Integer.valueOf(16767673));
      colourKeywords.put("peru", Integer.valueOf(13468991));
      colourKeywords.put("pink", Integer.valueOf(16761035));
      colourKeywords.put("plum", Integer.valueOf(14524637));
      colourKeywords.put("powderblue", Integer.valueOf(11591910));
      colourKeywords.put("purple", Integer.valueOf(8388736));
      colourKeywords.put("red", Integer.valueOf(16711680));
      colourKeywords.put("rosybrown", Integer.valueOf(12357519));
      colourKeywords.put("royalblue", Integer.valueOf(4286945));
      colourKeywords.put("saddlebrown", Integer.valueOf(9127187));
      colourKeywords.put("salmon", Integer.valueOf(16416882));
      colourKeywords.put("sandybrown", Integer.valueOf(16032864));
      colourKeywords.put("seagreen", Integer.valueOf(3050327));
      colourKeywords.put("seashell", Integer.valueOf(16774638));
      colourKeywords.put("sienna", Integer.valueOf(10506797));
      colourKeywords.put("silver", Integer.valueOf(12632256));
      colourKeywords.put("skyblue", Integer.valueOf(8900331));
      colourKeywords.put("slateblue", Integer.valueOf(6970061));
      colourKeywords.put("slategray", Integer.valueOf(7372944));
      colourKeywords.put("slategrey", Integer.valueOf(7372944));
      colourKeywords.put("snow", Integer.valueOf(16775930));
      colourKeywords.put("springgreen", Integer.valueOf(65407));
      colourKeywords.put("steelblue", Integer.valueOf(4620980));
      colourKeywords.put("tan", Integer.valueOf(13808780));
      colourKeywords.put("teal", Integer.valueOf(32896));
      colourKeywords.put("thistle", Integer.valueOf(14204888));
      colourKeywords.put("tomato", Integer.valueOf(16737095));
      colourKeywords.put("turquoise", Integer.valueOf(4251856));
      colourKeywords.put("violet", Integer.valueOf(15631086));
      colourKeywords.put("wheat", Integer.valueOf(16113331));
      colourKeywords.put("white", Integer.valueOf(16777215));
      colourKeywords.put("whitesmoke", Integer.valueOf(16119285));
      colourKeywords.put("yellow", Integer.valueOf(16776960));
      colourKeywords.put("yellowgreen", Integer.valueOf(10145074));
    }

    public static Integer get(String paramString)
    {
      return (Integer)colourKeywords.get(paramString);
    }
  }

  private static class FontSizeKeywords
  {
    private static final Map<String, SVG.Length> fontSizeKeywords = new HashMap(9);

    static
    {
      fontSizeKeywords.put("xx-small", new SVG.Length(0.694F, SVG.Unit.pt));
      fontSizeKeywords.put("x-small", new SVG.Length(0.833F, SVG.Unit.pt));
      fontSizeKeywords.put("small", new SVG.Length(10.0F, SVG.Unit.pt));
      fontSizeKeywords.put("medium", new SVG.Length(12.0F, SVG.Unit.pt));
      fontSizeKeywords.put("large", new SVG.Length(14.4F, SVG.Unit.pt));
      fontSizeKeywords.put("x-large", new SVG.Length(17.299999F, SVG.Unit.pt));
      fontSizeKeywords.put("xx-large", new SVG.Length(20.700001F, SVG.Unit.pt));
      fontSizeKeywords.put("smaller", new SVG.Length(83.330002F, SVG.Unit.percent));
      fontSizeKeywords.put("larger", new SVG.Length(120.0F, SVG.Unit.percent));
    }

    public static SVG.Length get(String paramString)
    {
      return (SVG.Length)fontSizeKeywords.get(paramString);
    }
  }

  private static class FontWeightKeywords
  {
    private static final Map<String, Integer> fontWeightKeywords = new HashMap(13);

    static
    {
      fontWeightKeywords.put("normal", Integer.valueOf(400));
      fontWeightKeywords.put("bold", Integer.valueOf(700));
      fontWeightKeywords.put("bolder", Integer.valueOf(1));
      fontWeightKeywords.put("lighter", Integer.valueOf(-1));
      fontWeightKeywords.put("100", Integer.valueOf(100));
      fontWeightKeywords.put("200", Integer.valueOf(200));
      fontWeightKeywords.put("300", Integer.valueOf(300));
      fontWeightKeywords.put("400", Integer.valueOf(400));
      fontWeightKeywords.put("500", Integer.valueOf(500));
      fontWeightKeywords.put("600", Integer.valueOf(600));
      fontWeightKeywords.put("700", Integer.valueOf(700));
      fontWeightKeywords.put("800", Integer.valueOf(800));
      fontWeightKeywords.put("900", Integer.valueOf(900));
    }

    public static Integer get(String paramString)
    {
      return (Integer)fontWeightKeywords.get(paramString);
    }
  }

  private static enum SVGAttr
  {
    private static final Map<String, SVGAttr> cache = new HashMap();

    static
    {
      clipPathUnits = new SVGAttr("clipPathUnits", 3);
      clip_rule = new SVGAttr("clip_rule", 4);
      color = new SVGAttr("color", 5);
      cx = new SVGAttr("cx", 6);
      cy = new SVGAttr("cy", 7);
      direction = new SVGAttr("direction", 8);
      dx = new SVGAttr("dx", 9);
      dy = new SVGAttr("dy", 10);
      fx = new SVGAttr("fx", 11);
      fy = new SVGAttr("fy", 12);
      d = new SVGAttr("d", 13);
      display = new SVGAttr("display", 14);
      fill = new SVGAttr("fill", 15);
      fill_rule = new SVGAttr("fill_rule", 16);
      fill_opacity = new SVGAttr("fill_opacity", 17);
      font = new SVGAttr("font", 18);
      font_family = new SVGAttr("font_family", 19);
      font_size = new SVGAttr("font_size", 20);
      font_weight = new SVGAttr("font_weight", 21);
      font_style = new SVGAttr("font_style", 22);
      gradientTransform = new SVGAttr("gradientTransform", 23);
      gradientUnits = new SVGAttr("gradientUnits", 24);
      height = new SVGAttr("height", 25);
      href = new SVGAttr("href", 26);
      id = new SVGAttr("id", 27);
      marker = new SVGAttr("marker", 28);
      marker_start = new SVGAttr("marker_start", 29);
      marker_mid = new SVGAttr("marker_mid", 30);
      marker_end = new SVGAttr("marker_end", 31);
      markerHeight = new SVGAttr("markerHeight", 32);
      markerUnits = new SVGAttr("markerUnits", 33);
      markerWidth = new SVGAttr("markerWidth", 34);
      mask = new SVGAttr("mask", 35);
      maskContentUnits = new SVGAttr("maskContentUnits", 36);
      maskUnits = new SVGAttr("maskUnits", 37);
      media = new SVGAttr("media", 38);
      offset = new SVGAttr("offset", 39);
      opacity = new SVGAttr("opacity", 40);
      orient = new SVGAttr("orient", 41);
      overflow = new SVGAttr("overflow", 42);
      pathLength = new SVGAttr("pathLength", 43);
      patternContentUnits = new SVGAttr("patternContentUnits", 44);
      patternTransform = new SVGAttr("patternTransform", 45);
      patternUnits = new SVGAttr("patternUnits", 46);
      points = new SVGAttr("points", 47);
      preserveAspectRatio = new SVGAttr("preserveAspectRatio", 48);
      r = new SVGAttr("r", 49);
      refX = new SVGAttr("refX", 50);
      refY = new SVGAttr("refY", 51);
      requiredFeatures = new SVGAttr("requiredFeatures", 52);
      requiredExtensions = new SVGAttr("requiredExtensions", 53);
      requiredFormats = new SVGAttr("requiredFormats", 54);
      requiredFonts = new SVGAttr("requiredFonts", 55);
      rx = new SVGAttr("rx", 56);
      ry = new SVGAttr("ry", 57);
      solid_color = new SVGAttr("solid_color", 58);
      solid_opacity = new SVGAttr("solid_opacity", 59);
      spreadMethod = new SVGAttr("spreadMethod", 60);
      startOffset = new SVGAttr("startOffset", 61);
      stop_color = new SVGAttr("stop_color", 62);
      stop_opacity = new SVGAttr("stop_opacity", 63);
      stroke = new SVGAttr("stroke", 64);
      stroke_dasharray = new SVGAttr("stroke_dasharray", 65);
      stroke_dashoffset = new SVGAttr("stroke_dashoffset", 66);
      stroke_linecap = new SVGAttr("stroke_linecap", 67);
      stroke_linejoin = new SVGAttr("stroke_linejoin", 68);
      stroke_miterlimit = new SVGAttr("stroke_miterlimit", 69);
      stroke_opacity = new SVGAttr("stroke_opacity", 70);
      stroke_width = new SVGAttr("stroke_width", 71);
      style = new SVGAttr("style", 72);
      systemLanguage = new SVGAttr("systemLanguage", 73);
      text_anchor = new SVGAttr("text_anchor", 74);
      text_decoration = new SVGAttr("text_decoration", 75);
      transform = new SVGAttr("transform", 76);
      type = new SVGAttr("type", 77);
      vector_effect = new SVGAttr("vector_effect", 78);
      version = new SVGAttr("version", 79);
      viewBox = new SVGAttr("viewBox", 80);
      width = new SVGAttr("width", 81);
      x = new SVGAttr("x", 82);
      y = new SVGAttr("y", 83);
      x1 = new SVGAttr("x1", 84);
      y1 = new SVGAttr("y1", 85);
      x2 = new SVGAttr("x2", 86);
      y2 = new SVGAttr("y2", 87);
      viewport_fill = new SVGAttr("viewport_fill", 88);
      viewport_fill_opacity = new SVGAttr("viewport_fill_opacity", 89);
      visibility = new SVGAttr("visibility", 90);
      UNSUPPORTED = new SVGAttr("UNSUPPORTED", 91);
      SVGAttr[] arrayOfSVGAttr = new SVGAttr[92];
      arrayOfSVGAttr[0] = CLASS;
      arrayOfSVGAttr[1] = clip;
      arrayOfSVGAttr[2] = clip_path;
      arrayOfSVGAttr[3] = clipPathUnits;
      arrayOfSVGAttr[4] = clip_rule;
      arrayOfSVGAttr[5] = color;
      arrayOfSVGAttr[6] = cx;
      arrayOfSVGAttr[7] = cy;
      arrayOfSVGAttr[8] = direction;
      arrayOfSVGAttr[9] = dx;
      arrayOfSVGAttr[10] = dy;
      arrayOfSVGAttr[11] = fx;
      arrayOfSVGAttr[12] = fy;
      arrayOfSVGAttr[13] = d;
      arrayOfSVGAttr[14] = display;
      arrayOfSVGAttr[15] = fill;
      arrayOfSVGAttr[16] = fill_rule;
      arrayOfSVGAttr[17] = fill_opacity;
      arrayOfSVGAttr[18] = font;
      arrayOfSVGAttr[19] = font_family;
      arrayOfSVGAttr[20] = font_size;
      arrayOfSVGAttr[21] = font_weight;
      arrayOfSVGAttr[22] = font_style;
      arrayOfSVGAttr[23] = gradientTransform;
      arrayOfSVGAttr[24] = gradientUnits;
      arrayOfSVGAttr[25] = height;
      arrayOfSVGAttr[26] = href;
      arrayOfSVGAttr[27] = id;
      arrayOfSVGAttr[28] = marker;
      arrayOfSVGAttr[29] = marker_start;
      arrayOfSVGAttr[30] = marker_mid;
      arrayOfSVGAttr[31] = marker_end;
      arrayOfSVGAttr[32] = markerHeight;
      arrayOfSVGAttr[33] = markerUnits;
      arrayOfSVGAttr[34] = markerWidth;
      arrayOfSVGAttr[35] = mask;
      arrayOfSVGAttr[36] = maskContentUnits;
      arrayOfSVGAttr[37] = maskUnits;
      arrayOfSVGAttr[38] = media;
      arrayOfSVGAttr[39] = offset;
      arrayOfSVGAttr[40] = opacity;
      arrayOfSVGAttr[41] = orient;
      arrayOfSVGAttr[42] = overflow;
      arrayOfSVGAttr[43] = pathLength;
      arrayOfSVGAttr[44] = patternContentUnits;
      arrayOfSVGAttr[45] = patternTransform;
      arrayOfSVGAttr[46] = patternUnits;
      arrayOfSVGAttr[47] = points;
      arrayOfSVGAttr[48] = preserveAspectRatio;
      arrayOfSVGAttr[49] = r;
      arrayOfSVGAttr[50] = refX;
      arrayOfSVGAttr[51] = refY;
      arrayOfSVGAttr[52] = requiredFeatures;
      arrayOfSVGAttr[53] = requiredExtensions;
      arrayOfSVGAttr[54] = requiredFormats;
      arrayOfSVGAttr[55] = requiredFonts;
      arrayOfSVGAttr[56] = rx;
      arrayOfSVGAttr[57] = ry;
      arrayOfSVGAttr[58] = solid_color;
      arrayOfSVGAttr[59] = solid_opacity;
      arrayOfSVGAttr[60] = spreadMethod;
      arrayOfSVGAttr[61] = startOffset;
      arrayOfSVGAttr[62] = stop_color;
      arrayOfSVGAttr[63] = stop_opacity;
      arrayOfSVGAttr[64] = stroke;
      arrayOfSVGAttr[65] = stroke_dasharray;
      arrayOfSVGAttr[66] = stroke_dashoffset;
      arrayOfSVGAttr[67] = stroke_linecap;
      arrayOfSVGAttr[68] = stroke_linejoin;
      arrayOfSVGAttr[69] = stroke_miterlimit;
      arrayOfSVGAttr[70] = stroke_opacity;
      arrayOfSVGAttr[71] = stroke_width;
      arrayOfSVGAttr[72] = style;
      arrayOfSVGAttr[73] = systemLanguage;
      arrayOfSVGAttr[74] = text_anchor;
      arrayOfSVGAttr[75] = text_decoration;
      arrayOfSVGAttr[76] = transform;
      arrayOfSVGAttr[77] = type;
      arrayOfSVGAttr[78] = vector_effect;
      arrayOfSVGAttr[79] = version;
      arrayOfSVGAttr[80] = viewBox;
      arrayOfSVGAttr[81] = width;
      arrayOfSVGAttr[82] = x;
      arrayOfSVGAttr[83] = y;
      arrayOfSVGAttr[84] = x1;
      arrayOfSVGAttr[85] = y1;
      arrayOfSVGAttr[86] = x2;
      arrayOfSVGAttr[87] = y2;
      arrayOfSVGAttr[88] = viewport_fill;
      arrayOfSVGAttr[89] = viewport_fill_opacity;
      arrayOfSVGAttr[90] = visibility;
      arrayOfSVGAttr[91] = UNSUPPORTED;
    }

    public static SVGAttr fromString(String paramString)
    {
      SVGAttr localSVGAttr1 = (SVGAttr)cache.get(paramString);
      if (localSVGAttr1 != null)
        return localSVGAttr1;
      if (paramString.equals("class"))
      {
        cache.put(paramString, CLASS);
        return CLASS;
      }
      if (paramString.indexOf('_') != -1)
      {
        cache.put(paramString, UNSUPPORTED);
        return UNSUPPORTED;
      }
      try
      {
        SVGAttr localSVGAttr2 = valueOf(paramString.replace('-', '_'));
        if (localSVGAttr2 != CLASS)
        {
          cache.put(paramString, localSVGAttr2);
          return localSVGAttr2;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        cache.put(paramString, UNSUPPORTED);
      }
      return UNSUPPORTED;
    }
  }

  private static enum SVGElem
  {
    private static final Map<String, SVGElem> cache = new HashMap();

    static
    {
      a = new SVGElem("a", 1);
      circle = new SVGElem("circle", 2);
      clipPath = new SVGElem("clipPath", 3);
      defs = new SVGElem("defs", 4);
      desc = new SVGElem("desc", 5);
      ellipse = new SVGElem("ellipse", 6);
      g = new SVGElem("g", 7);
      image = new SVGElem("image", 8);
      line = new SVGElem("line", 9);
      linearGradient = new SVGElem("linearGradient", 10);
      marker = new SVGElem("marker", 11);
      mask = new SVGElem("mask", 12);
      path = new SVGElem("path", 13);
      pattern = new SVGElem("pattern", 14);
      polygon = new SVGElem("polygon", 15);
      polyline = new SVGElem("polyline", 16);
      radialGradient = new SVGElem("radialGradient", 17);
      rect = new SVGElem("rect", 18);
      solidColor = new SVGElem("solidColor", 19);
      stop = new SVGElem("stop", 20);
      style = new SVGElem("style", 21);
      SWITCH = new SVGElem("SWITCH", 22);
      symbol = new SVGElem("symbol", 23);
      text = new SVGElem("text", 24);
      textPath = new SVGElem("textPath", 25);
      title = new SVGElem("title", 26);
      tref = new SVGElem("tref", 27);
      tspan = new SVGElem("tspan", 28);
      use = new SVGElem("use", 29);
      view = new SVGElem("view", 30);
      UNSUPPORTED = new SVGElem("UNSUPPORTED", 31);
      SVGElem[] arrayOfSVGElem = new SVGElem[32];
      arrayOfSVGElem[0] = svg;
      arrayOfSVGElem[1] = a;
      arrayOfSVGElem[2] = circle;
      arrayOfSVGElem[3] = clipPath;
      arrayOfSVGElem[4] = defs;
      arrayOfSVGElem[5] = desc;
      arrayOfSVGElem[6] = ellipse;
      arrayOfSVGElem[7] = g;
      arrayOfSVGElem[8] = image;
      arrayOfSVGElem[9] = line;
      arrayOfSVGElem[10] = linearGradient;
      arrayOfSVGElem[11] = marker;
      arrayOfSVGElem[12] = mask;
      arrayOfSVGElem[13] = path;
      arrayOfSVGElem[14] = pattern;
      arrayOfSVGElem[15] = polygon;
      arrayOfSVGElem[16] = polyline;
      arrayOfSVGElem[17] = radialGradient;
      arrayOfSVGElem[18] = rect;
      arrayOfSVGElem[19] = solidColor;
      arrayOfSVGElem[20] = stop;
      arrayOfSVGElem[21] = style;
      arrayOfSVGElem[22] = SWITCH;
      arrayOfSVGElem[23] = symbol;
      arrayOfSVGElem[24] = text;
      arrayOfSVGElem[25] = textPath;
      arrayOfSVGElem[26] = title;
      arrayOfSVGElem[27] = tref;
      arrayOfSVGElem[28] = tspan;
      arrayOfSVGElem[29] = use;
      arrayOfSVGElem[30] = view;
      arrayOfSVGElem[31] = UNSUPPORTED;
    }

    public static SVGElem fromString(String paramString)
    {
      SVGElem localSVGElem1 = (SVGElem)cache.get(paramString);
      if (localSVGElem1 != null)
        return localSVGElem1;
      if (paramString.equals("switch"))
      {
        cache.put(paramString, SWITCH);
        return SWITCH;
      }
      try
      {
        SVGElem localSVGElem2 = valueOf(paramString);
        if (localSVGElem2 != SWITCH)
        {
          cache.put(paramString, localSVGElem2);
          return localSVGElem2;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        cache.put(paramString, UNSUPPORTED);
      }
      return UNSUPPORTED;
    }
  }

  protected static class TextScanner
  {
    protected String input;
    protected int inputLength = 0;
    private NumberParser numberParser = new NumberParser();
    protected int position = 0;

    public TextScanner(String paramString)
    {
      this.input = paramString.trim();
      this.inputLength = this.input.length();
    }

    protected int advanceChar()
    {
      if (this.position == this.inputLength);
      do
      {
        return -1;
        this.position = (1 + this.position);
      }
      while (this.position >= this.inputLength);
      return this.input.charAt(this.position);
    }

    public String ahead()
    {
      int i = this.position;
      while ((!empty()) && (!isWhitespace(this.input.charAt(this.position))))
        this.position = (1 + this.position);
      String str = this.input.substring(i, this.position);
      this.position = i;
      return str;
    }

    public Boolean checkedNextFlag(Object paramObject)
    {
      if (paramObject == null)
        return null;
      skipCommaWhitespace();
      return nextFlag();
    }

    public float checkedNextFloat(float paramFloat)
    {
      if (Float.isNaN(paramFloat))
        return (0.0F / 0.0F);
      skipCommaWhitespace();
      return nextFloat();
    }

    public boolean consume(char paramChar)
    {
      if ((this.position < this.inputLength) && (this.input.charAt(this.position) == paramChar));
      for (boolean bool = true; ; bool = false)
      {
        if (bool)
          this.position = (1 + this.position);
        return bool;
      }
    }

    public boolean consume(String paramString)
    {
      int i = paramString.length();
      if ((this.position <= this.inputLength - i) && (this.input.substring(this.position, i + this.position).equals(paramString)));
      for (boolean bool = true; ; bool = false)
      {
        if (bool)
          this.position = (i + this.position);
        return bool;
      }
    }

    public boolean empty()
    {
      return this.position == this.inputLength;
    }

    public boolean hasLetter()
    {
      if (this.position == this.inputLength);
      int i;
      do
      {
        return false;
        i = this.input.charAt(this.position);
      }
      while (((i < 97) || (i > 122)) && ((i < 65) || (i > 90)));
      return true;
    }

    protected boolean isEOL(int paramInt)
    {
      return (paramInt == 10) || (paramInt == 13);
    }

    protected boolean isWhitespace(int paramInt)
    {
      return (paramInt == 32) || (paramInt == 10) || (paramInt == 13) || (paramInt == 9);
    }

    public Integer nextChar()
    {
      if (this.position == this.inputLength)
        return null;
      String str = this.input;
      int i = this.position;
      this.position = (i + 1);
      return Integer.valueOf(str.charAt(i));
    }

    public Boolean nextFlag()
    {
      if (this.position == this.inputLength);
      int i;
      do
      {
        return null;
        i = this.input.charAt(this.position);
      }
      while ((i != 48) && (i != 49));
      this.position = (1 + this.position);
      if (i == 49);
      for (boolean bool = true; ; bool = false)
        return Boolean.valueOf(bool);
    }

    public float nextFloat()
    {
      float f = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
      if (!Float.isNaN(f))
        this.position = this.numberParser.getEndPos();
      return f;
    }

    public String nextFunction()
    {
      if (empty())
        return null;
      int i = this.position;
      for (int j = this.input.charAt(this.position); ((j >= 97) && (j <= 122)) || ((j >= 65) && (j <= 90)); j = advanceChar());
      int k = this.position;
      while (isWhitespace(j))
        j = advanceChar();
      if (j == 40)
      {
        this.position = (1 + this.position);
        return this.input.substring(i, k);
      }
      this.position = i;
      return null;
    }

    public Integer nextInteger()
    {
      IntegerParser localIntegerParser = IntegerParser.parseInt(this.input, this.position, this.inputLength);
      if (localIntegerParser == null)
        return null;
      this.position = localIntegerParser.getEndPos();
      return Integer.valueOf(localIntegerParser.value());
    }

    public SVG.Length nextLength()
    {
      float f = nextFloat();
      if (Float.isNaN(f))
        return null;
      SVG.Unit localUnit = nextUnit();
      if (localUnit == null)
        return new SVG.Length(f, SVG.Unit.px);
      return new SVG.Length(f, localUnit);
    }

    public String nextQuotedString()
    {
      if (empty());
      int i;
      int j;
      do
      {
        return null;
        i = this.position;
        j = this.input.charAt(this.position);
      }
      while ((j != 39) && (j != 34));
      for (int k = advanceChar(); (k != -1) && (k != j); k = advanceChar());
      if (k == -1)
      {
        this.position = i;
        return null;
      }
      this.position = (1 + this.position);
      return this.input.substring(i + 1, -1 + this.position);
    }

    public String nextToken()
    {
      return nextToken(' ');
    }

    public String nextToken(char paramChar)
    {
      if (empty());
      char c1;
      do
      {
        return null;
        c1 = this.input.charAt(this.position);
      }
      while ((isWhitespace(c1)) || (c1 == paramChar));
      int i = this.position;
      for (char c2 = advanceChar(); (c2 != 'èøø') && (c2 != paramChar) && (!isWhitespace(c2)); c2 = advanceChar());
      return this.input.substring(i, this.position);
    }

    public SVG.Unit nextUnit()
    {
      if (empty())
        return null;
      if (this.input.charAt(this.position) == '%')
      {
        this.position = (1 + this.position);
        return SVG.Unit.percent;
      }
      if (this.position > -2 + this.inputLength)
        return null;
      try
      {
        SVG.Unit localUnit = SVG.Unit.valueOf(this.input.substring(this.position, 2 + this.position).toLowerCase(Locale.US));
        this.position = (2 + this.position);
        return localUnit;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
      }
      return null;
    }

    public float possibleNextFloat()
    {
      skipCommaWhitespace();
      float f = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
      if (!Float.isNaN(f))
        this.position = this.numberParser.getEndPos();
      return f;
    }

    public String restOfText()
    {
      if (empty())
        return null;
      int i = this.position;
      this.position = this.inputLength;
      return this.input.substring(i);
    }

    public boolean skipCommaWhitespace()
    {
      skipWhitespace();
      if (this.position == this.inputLength);
      while (this.input.charAt(this.position) != ',')
        return false;
      this.position = (1 + this.position);
      skipWhitespace();
      return true;
    }

    public void skipWhitespace()
    {
      while (true)
      {
        if ((this.position >= this.inputLength) || (!isWhitespace(this.input.charAt(this.position))))
          return;
        this.position = (1 + this.position);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\ÂèçÁºñËØëÂ∑•ÂÖ∑ÂåÖ\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGParser
 * JD-Core Version:    0.6.2
 */