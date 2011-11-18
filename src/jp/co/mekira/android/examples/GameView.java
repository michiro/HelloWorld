package jp.co.mekira.android.examples.helloworld1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class GameView extends SurfaceView implements
                                          SurfaceHolder.Callback,
                                          Runnable {
    private SurfaceHolder holder;
    private boolean       surfaceCreated;
    private Thread        thread;
    private int           status;

    private Paint         bgPaint;
    private Paint         textPaint;
    private int           textSize;

    public static final int StatusNOP  = 0;
    public static final int StatusDraw = 0;

    public GameView(Activity activity) {
        super(activity);
        init();

        thread = new Thread(this);
        thread.start();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        surfaceCreated = false;
        thread         = null;
        status         = StatusNOP;

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setARGB(0xff,0xff,0xff,0xff); //背景は白

        textSize = 24;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setARGB(0xff,0,0,0); //文字の色は黒
        textPaint.setTextSize(textSize);
    }

    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        surfaceCreated = true;
        repaint();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceCreated  = false;
    }

    public void run() {
        while (thread != null) {
            if (status == StatusDraw) {
                repaint();
            } else {
                synchronized (this) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public void finish() {
        thread = null;
        wakeup();
    }

    private void wakeup() {
        synchronized(this) {
            notifyAll();
        }
    }

    public void repaint() {
        Canvas canvas = null;

        if (!surfaceCreated) {
            return;
        }

        try {
            canvas = holder.lockCanvas();
            synchronized (holder) {
                paint(canvas);
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    protected void paint(Canvas c) {
        //背景を塗りつぶす
        c.drawRect(0,0,getWidth(),getHeight(),bgPaint);

        String str = "ハローワールド";
        Rect   bounds = new Rect();
        int    xx,yy;

        //文字列の描画範囲を取得する
        textPaint.getTextBounds(str,0,str.length(),bounds);
        xx = (getWidth() - bounds.width()) / 2;
        yy = (getHeight() - textSize) / 2;
        c.drawText(str,xx,yy,textPaint);
    }
}
