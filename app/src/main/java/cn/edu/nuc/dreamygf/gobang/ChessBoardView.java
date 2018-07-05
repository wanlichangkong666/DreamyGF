package cn.edu.nuc.dreamygf.gobang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import cn.edu.nuc.dreamygf.R;
import cn.edu.nuc.dreamygf.gobang.game.BaseComputerAi;
import cn.edu.nuc.dreamygf.gobang.game.ChessBoard;
import cn.edu.nuc.dreamygf.gobang.game.HumanPlayer;
import cn.edu.nuc.dreamygf.gobang.game.IChessboard;
import cn.edu.nuc.dreamygf.gobang.game.IPlayer;
import cn.edu.nuc.dreamygf.gobang.game.Point;


public class ChessBoardView extends View {


    private int mPanelWidth;
    private static int MAX_LINE = 15;
    private float mLineHeight;

    private Paint paint = new Paint();

    //棋子图片
    private Bitmap whiteChess;
    private Bitmap blackChess;

    private float pieceLineHeight = 0.75f;

    private boolean isBlack = true; //玩家是否为黑棋
    private boolean isWin = false;//是否已获胜


    private IPlayer humanPlayer; //玩家
    private IPlayer aiPlayer; //电脑
    private IChessboard chessboard = new ChessBoard(MAX_LINE);//棋盘


    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setKeepScreenOn(true);//设置屏幕常亮
        //setBackgroundColor(0x44ff0000);
        Resources resources = getResources();
        blackChess = BitmapFactory.decodeResource(resources, R.drawable.stone_b1);
        whiteChess = BitmapFactory.decodeResource(resources, R.drawable.stone_w2);
        initPaint();
        initGame();
    }

    //音效
    private SoundPool sound;
    private int soundWin,soundDefeat,soundPoint;


    private void initGame() {
        humanPlayer = new HumanPlayer();
        humanPlayer.setChessboard(chessboard);
        aiPlayer = new BaseComputerAi();
        aiPlayer.setChessboard(chessboard);
        humanPlayer.clear();
        aiPlayer.clear();

        sound = new SoundPool(10, AudioManager.STREAM_MUSIC,0);


        soundWin = sound.load(getContext(),R.raw.win,1);
        soundDefeat=sound.load(getContext(),R.raw.defeat,1);
        soundPoint=sound.load(getContext(),R.raw.chess,1);
    }

    private void initPaint() {
        paint.setColor(Color.rgb(0xFF,0x00,0xFF));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5.0f);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int size = 0;
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            size = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            size = widthSize;
        } else {
            size = Math.min(widthSize, heightSize);
        }
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * pieceLineHeight);

        whiteChess = Bitmap.createScaledBitmap(whiteChess, pieceWidth, pieceWidth, false);
        blackChess = Bitmap.createScaledBitmap(blackChess, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChessBoard(canvas);
        drawPieces(canvas);
    }

    private void drawPieces(Canvas canvas) {
        for (Point point : humanPlayer.getMyPoints()) {
            canvas.drawBitmap(isBlack ? blackChess : whiteChess,
                    (point.x + (1 - pieceLineHeight) / 2) * mLineHeight,
                    (point.y + (1 - pieceLineHeight) / 2) * mLineHeight,
                    null);
        }
        for (Point point : aiPlayer.getMyPoints()) {
            canvas.drawBitmap(!isBlack ? blackChess : whiteChess,
                    (point.x + (1 - pieceLineHeight) / 2) * mLineHeight,
                    (point.y + (1 - pieceLineHeight) / 2) * mLineHeight,
                    null);
        }
    }

    private void drawChessBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            float startX = lineHeight / 2;
            float endX = w - lineHeight / 2;

            float y = (float) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, paint);
            canvas.drawLine(y, startX, y, endX, paint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {

            int x = (int) (event.getX() / mLineHeight);
            int y = (int) (event.getY() / mLineHeight);

            if (x >= 0 && x < MAX_LINE && y >= 0 && y < MAX_LINE) {
                onPoint(x,y);
            }
            return true;
        } else if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onPoint(int x,int y){
        if(isWin){
            return;
        }
        Point point = new Point(x,y);
        if(chessboard.getFreePoints().contains(point)){
            humanPlayer.run(aiPlayer.getMyPoints(),point);
            invalidate();
            sound.play(soundPoint,1, 1, 0, 0, 1);
            checkWin(true);
            if(!isWin){
                aiPlayer.run(humanPlayer.getMyPoints(),null);
                invalidate();//刷屏
                checkWin(false);
            }
        }
    }




    private void checkWin(boolean player){

        if (player && humanPlayer.hasWin()) {
            isWin = true;
            sound.play(soundWin,1, 1, 0, 0, 1);
            alert("你赢了！");
        }
        if(!player && aiPlayer.hasWin()){
            isWin = true;
            sound.play(soundDefeat,1, 1, 0, 0, 1);
            alert("你的女朋友赢了！");
        }
        if (chessboard.getFreePoints().isEmpty()) {
            isWin = true;
            alert("和棋！");
        }
    }



    private void alert(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("重新开局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isWin = false;
                dialogInterface.dismiss();
                clear();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public synchronized void startBlack(){
        isBlack = true;
        clear();
    }

    public synchronized void startWhite(){
        isBlack = false;
        clear();
    }

    private void clear(){
        isWin = false;
        chessboard.clear();
        aiPlayer.clear();
        humanPlayer.clear();
        //我方是白棋，电脑第一个棋子一定下在正中心
        if(!isBlack){
            Point point = new Point(MAX_LINE/2, MAX_LINE/2);
            aiPlayer.getMyPoints().add(point);
            chessboard.getFreePoints().remove(point);
        }
        invalidate();
    }

    public synchronized void back(){
        //悔棋
        if(!humanPlayer.getMyPoints().isEmpty()&&!aiPlayer.getMyPoints().isEmpty()){
            LinkedList<Point> list1 = (LinkedList<Point>) humanPlayer.getMyPoints();
            LinkedList<Point> list2 = (LinkedList<Point>) aiPlayer.getMyPoints();
            Point p1 = list1.removeLast();
            Point p2 = list2.removeLast();
            chessboard.getFreePoints().add(p1);
            chessboard.getFreePoints().add(p2);
            isWin = false;
            invalidate();
        }
    }

    private static final String instance = "INSTANCE";
    private static final String win = "ISWIN";
    private static final String black = "ISBLACK";
    private static final String human = "HUMAN";
    private static final String ai = "AI";

    //view的存储与恢复

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        Log.d("ChessView","保存棋局");
        bundle.putParcelable(instance,super.onSaveInstanceState());
        bundle.putBoolean(win,isWin);
        bundle.putBoolean(black,isBlack);
        bundle.putSerializable(human, (Serializable) humanPlayer.getMyPoints());
        bundle.putSerializable(ai, (Serializable) aiPlayer.getMyPoints());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Log.d("ChessView","加载棋局");
            Bundle bundle = (Bundle)state;
            isWin = bundle.getBoolean(win);
            isBlack = bundle.getBoolean(black);
            chessboard = new ChessBoard(MAX_LINE);
            humanPlayer = new HumanPlayer();
            humanPlayer.setChessboard(chessboard);
            aiPlayer = new BaseComputerAi();
            aiPlayer.setChessboard(chessboard);
            List<Point> humanPoints = (List<Point>) bundle.getSerializable(human);
            List<Point> aiPoints = (List<Point>) bundle.getSerializable(ai);
            humanPlayer.getMyPoints().addAll(humanPoints);
            aiPlayer.getMyPoints().addAll(aiPoints);
            for(Point point:humanPoints){
                chessboard.getFreePoints().remove(point);
            }
            for(Point point:aiPoints){
                chessboard.getFreePoints().remove(point);
            }
            super.onRestoreInstanceState(bundle.getParcelable(instance));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
