package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiCallback
  implements Drawable.Callback
{
  private final CopyOnWriteArrayList<ViewWeakReference> mViewList = new CopyOnWriteArrayList();

  public void addView(@NonNull View paramView)
  {
    for (int i = 0; i < this.mViewList.size(); i++)
    {
      ViewWeakReference localViewWeakReference = (ViewWeakReference)this.mViewList.get(i);
      if ((View)localViewWeakReference.get() == null)
        this.mViewList.remove(localViewWeakReference);
    }
    this.mViewList.addIfAbsent(new ViewWeakReference(paramView));
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    int i = 0;
    if (i < this.mViewList.size())
    {
      ViewWeakReference localViewWeakReference = (ViewWeakReference)this.mViewList.get(i);
      View localView = (View)localViewWeakReference.get();
      if (localView != null)
        localView.invalidateDrawable(paramDrawable);
      while (true)
      {
        i++;
        break;
        this.mViewList.remove(localViewWeakReference);
      }
    }
  }

  public void removeView(View paramView)
  {
    for (int i = 0; i < this.mViewList.size(); i++)
    {
      ViewWeakReference localViewWeakReference = (ViewWeakReference)this.mViewList.get(i);
      View localView = (View)localViewWeakReference.get();
      if ((localView == null) || (localView == paramView))
        this.mViewList.remove(localViewWeakReference);
    }
  }

  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    int i = 0;
    if (i < this.mViewList.size())
    {
      ViewWeakReference localViewWeakReference = (ViewWeakReference)this.mViewList.get(i);
      View localView = (View)localViewWeakReference.get();
      if (localView != null)
        localView.scheduleDrawable(paramDrawable, paramRunnable, paramLong);
      while (true)
      {
        i++;
        break;
        this.mViewList.remove(localViewWeakReference);
      }
    }
  }

  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    int i = 0;
    if (i < this.mViewList.size())
    {
      ViewWeakReference localViewWeakReference = (ViewWeakReference)this.mViewList.get(i);
      View localView = (View)localViewWeakReference.get();
      if (localView != null)
        localView.unscheduleDrawable(paramDrawable);
      while (true)
      {
        i++;
        break;
        this.mViewList.remove(localViewWeakReference);
      }
    }
  }

  private static final class ViewWeakReference extends WeakReference<View>
  {
    ViewWeakReference(View paramView)
    {
      super();
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      do
      {
        return true;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
          return false;
      }
      while (get() == ((ViewWeakReference)paramObject).get());
      return false;
    }

    public int hashCode()
    {
      View localView = (View)get();
      if (localView != null)
        return localView.hashCode();
      return 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.MultiCallback
 * JD-Core Version:    0.6.2
 */