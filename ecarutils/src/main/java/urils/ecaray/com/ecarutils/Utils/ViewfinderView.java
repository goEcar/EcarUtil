package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public final class ViewfinderView extends View {
    private final Paint paint;
    private final Paint paintLine;
    public Rect frame;
    int w, h;
    private boolean boo;
    public int length = 0;
    public int t, b, l, r;

    public ViewfinderView(Context context, int w, int h, boolean boo) {
        super(context);
        this.w = w;
        this.h = h;
        this.boo = boo;
        paint = new Paint();
        paintLine = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (boo) {

            // if (height < 1080 || height > 1620) {
            // length = height / 2;
            // } else {
            // length = 250;
            // }
            length = width / 2 - DataFormatUtil.setDip2px(getContext(), 40);// width
            // /
            // 2刚好等于屏幕宽度
        } else {
            // if (width < 1080 || width > 1620) {
            // length = width / 2;
            // } else {
            // length = 250;
            // }
            length = width / 2 - DataFormatUtil.setDip2px(getContext(), 40);
        }
        l = w / 2 - length;
        r = w / 2 + length;
        t = h / 2 - length;
        b = h / 2 + length;
        frame = new Rect(l, t, r, b);
        // ����Ӱ���֣����Ĳ��֣�����Ļ�Ϸ���ɨ�����Ϸ�������Ļ��ߵ�ɨ�������
        // ��ɨ����ұߵ���Ļ�ұߣ���ɨ���ײ�����Ļ�ײ�
        paint.setColor(Color.argb(128, 0, 0, 0));
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paintLine.setColor(Color.rgb(0, 255, 0));
        paintLine.setStrokeWidth(4);
        paintLine.setAntiAlias(true);
        canvas.drawLine(l, t, l + 50, t, paintLine);
        canvas.drawLine(l, t, l, t + 50, paintLine);
        canvas.drawLine(r, t, r - 50, t, paintLine);
        canvas.drawLine(r, t, r, t + 50, paintLine);
        canvas.drawLine(l, b, l + 50, b, paintLine);
        canvas.drawLine(l, b, l, b - 50, paintLine);
        canvas.drawLine(r, b, r - 50, b, paintLine);
        canvas.drawLine(r, b, r, b - 50, paintLine);
        // }

        if (frame == null) {
            return;
        }

    }
}
