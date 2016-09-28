package com.starcor.xul.Render.Effect;

import android.graphics.Camera;
import android.graphics.Matrix;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.XulViewRender;

public class FlipAnimation
  implements IXulAnimation
{
  float _angle = 0.0F;
  Camera _camera = new Camera();
  Matrix _matrix = new Matrix();
  protected XulViewRender _render;

  public FlipAnimation(XulViewRender paramXulViewRender)
  {
    this._render = paramXulViewRender;
    this._render.addAnimation(this);
  }

  public float getAngle()
  {
    return this._angle;
  }

  public void postDraw(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this._camera.restore();
    paramXulDC.restore();
  }

  public void preDraw(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramXulDC.save();
    this._camera.save();
    this._camera.rotateY(this._angle);
    this._camera.getMatrix(this._matrix);
    this._matrix.preTranslate(-paramFloat1 - paramFloat3 / 2.0F, -paramFloat2 - paramFloat4 / 2.0F);
    this._matrix.postTranslate(paramFloat1 + paramFloat3 / 2.0F, paramFloat2 + paramFloat4 / 2.0F);
    paramXulDC.setMatrix(this._matrix);
  }

  public void setAngle(float paramFloat)
  {
    this._angle = paramFloat;
  }

  public boolean updateAnimation(long paramLong)
  {
    if (this._render == null)
      return false;
    this._angle = (3.0F + this._angle);
    this._angle = ((float)(this._angle % 360.0D));
    this._render.markDirtyView();
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Effect.FlipAnimation
 * JD-Core Version:    0.6.2
 */