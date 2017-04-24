                                            
package jp.venus.vtlwhtsy01801;

/** ver.1.01 疋田紗也 タッチ壁紙
 *  壁紙数：6枚
 *  タッチ・サークルフェード　：有り
 *  電池表示　：無し
 *  日付表示　：無し
 *  アプリ内課金機能　：無し
**/

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class VenusTouchLiveWall extends WallpaperService {

	private final Handler drawHandler = new Handler();
    //private int scale;
	//private int level;
	
    private GestureDetector mGestureDetector;
    
    @Override
    public Engine onCreateEngine() {
        return new LiveEngine();
    }
    
    public class LiveEngine extends Engine implements OnSharedPreferenceChangeListener{
        
        private boolean visible;        // 表示状態フラグ
        Rect src,dst;
        private Bitmap bitmap; // メイン画像
        //private Bitmap bitmapTouch; // タッチ画像
        int ofsW2 = 0; // タッチ画像用のオフセット値
        float drow_h = 0; // リサイズ画像の高さ
        float drow_s = 0; // 描画始点の高さ
        float drow_w = 0; // リサイズ画像の幅
        float base_s = 0;	//基準画像設置位置
        private boolean flag = false; // タッチ用フラグ
        private boolean FadeFlag = false; // フェード用フラグ

        private Canvas c ;
        private int intFlag = 0; // タッチ画像用のフラグ

        private int pAlpha = 255;
        private int tAlpha = 255;
        private float rParse = 0;
        private float size = 0;
        long startTime = 0;
        long endEime = 0;
        //Paint circlePaint;
        
        float touchX;
        float touchY;
        float pTouchX;
        float pTouchY;
        float pressure;
        
        private boolean DTflag = false;
        private boolean touchFlag = false;

        int ofsW = 0; // 表示用のオフセット（キャンバス＜画像の場合のみ）
        int bmpFWidth = 0; // 最終表示用の画像サイズ
        int bmpFHeight = 0; // 最終表示用の画像サイズ

        // キャンバスのサイズ
        float canvasWidth = 0;
        float canvasHeight = 0;

        // ビットマップのサイズ
        int bmpWidth = 0;
        int bmpHeight = 0;
        
        int cnt = 0;
        
        // キャンバスサイズ取得用
        float	drawWidth	= 0;
		float	drawHeight	= 0;

		// X,Y 座標それぞれの拡大率を算出
		float	widthScale	= 0;
		float	heightScale	= 0;

		float zRenge = 10;	//基準値として10を初期値とする
		
		boolean mainFlag = false;	//メイン画像である判定処理
		SharedPreferences pref;
		String[] imgSet = new String[6];
    	String imgPath = "";

        /** 描画用のRunnableオブジェクト **/
        private final Runnable drawRannable = new Runnable(){
            public void run(){
                draw();
            }
        };

        //SurfaceHolder.setFormat();
        public LiveEngine(){

        	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        	pref.registerOnSharedPreferenceChangeListener(this);
        	
        	imgPath = getApplicationContext().getDir("data", Context.MODE_PRIVATE).toString()+"/photos/";
        	imgSet[0] = imgPath+"img_main.jpg";
        	imgSet[1] = imgPath+"img001sub02.jpg";
        	imgSet[2] = imgPath+"img001sub03.jpg";
        	imgSet[3] = imgPath+"img001sub05.jpg";
        	imgSet[4] = imgPath+"img001hup01.jpg";
        	imgSet[5] = imgPath+"img001hup02.jpg";

            // 設定値の読み込み
            //onSharedPreferenceChanged(pref, "change_image");
        	//String s = pref.getString("SaveString", null);
        	drawWidth = pref.getFloat("drawWidth", drawWidth);
        	drawHeight = pref.getFloat("drawHeight", drawHeight);
        	drow_w = pref.getFloat("drow_w", drow_w);
        	drow_h = pref.getFloat("drow_h", drow_h);
        	drow_s = pref.getFloat("drow_s", drow_s);
        	base_s = pref.getFloat("drow_s", drow_s);
    		ofsW = pref.getInt("ofsW", ofsW);

    		widthScale = pref.getFloat("widthScale", widthScale);
    		heightScale = pref.getFloat("heightScale", heightScale);
    		canvasWidth = pref.getFloat("canvasWidth", canvasWidth);
    		canvasHeight = pref.getFloat("canvasHeight", canvasHeight);
        	float lenge = pref.getFloat("zRenge", zRenge);
    		if(lenge > 10){
    			zRenge = lenge;
    		}
    		//Log.i("getDrow_S", "drow_s0:"+drow_s);
}
        
        @SuppressWarnings("deprecation")
		@Override
        public void onCreate(SurfaceHolder surfaceHolder){
        	
//        	Log.i("onCreate", "--Through");
        	super.onCreate(surfaceHolder);
        	// タッチイベントを有効
        	setTouchEventsEnabled(true);
        	setDoubleTapEventsEnabled(true);
        	
	        // 複雑なタッチイベントの補足
	        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
	            // ダブルタップ時
	            @Override
	            public boolean onDoubleTap(MotionEvent pMotionEvent) {
	                // ロジック
//	            	Log.i("tag1", "onDoubleTap");
	            	DTflag = true;
	                return super.onDoubleTap(pMotionEvent);
	            }
	 
	            // タップ時
	            @Override
	            public boolean onSingleTapUp(MotionEvent pMotionEvent) {
	                // ロジック
//	            	Log.i("tag1", "onSingleTapUp");
	            	DTflag = false;
	                return super.onSingleTapUp(pMotionEvent);
	            }
	
	        });
        
        }
        
        private void setDoubleTapEventsEnabled(boolean b) {
//        	Log.i("setDoubleTapEventsEnabled", "--Through");
        	// TODO 自動生成されたメソッド・スタブ
		}

        public boolean onDoubleTap(MotionEvent e) {
//            Log.i("onDoubleTap", "--Through");
            return false;
        }

/** Engine破棄時に呼び出される **/
        @Override
        public void onDestroy(){
            super.onDestroy();
            drawHandler.removeCallbacks(drawRannable);
//            Log.i("tag1", "onDestroy");
            if (bitmap != null) {
	            // Bitmapデータの解放
//	            Log.d("Bitmap:", "データの解放");
	            bitmap.recycle();
	            bitmap = null;
            }
        }
        
        /** 表示状態変更時に呼び出される **/
        @Override
        public void onVisibilityChanged(boolean visible){
            this.visible = visible;
            touchFlag = false;
  //          Log.i("tag1", "onVisibilityChanged");
            //Log.i("tag1", "cnt:"+cnt);
            if(visible){
            	intFlag = 0; // バックエンドの際は初期画面へ
                draw();
            }else{
                drawHandler.removeCallbacks(drawRannable);
            }
            cnt = 0;
        }
        /** サーフェイス生成時に呼び出される **/
        @Override
        public void onSurfaceCreated(SurfaceHolder surfaceHolder){
        	super.onSurfaceCreated(surfaceHolder);
    //    	Log.i("tag1", "onSurfaceCreated");

	//        Log.i("onSurfaceCreated", "初期処理");
	        /* 初期処理：メイン画像読み込みと基準サイズの設定、キャンバスサイズ読み込み */

            if (bitmap != null) {
	            // Bitmapデータの解放
//	            Log.d("Bitmap:", "データの解放");
	            bitmap.recycle();
	            bitmap = null;
            }

            //読み込み用のオプションオブジェクトを生成
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds = true;
            //options.inPreferredConfig = Bitmap.Config.RGB_565;
            
            final SurfaceHolder crholder = getSurfaceHolder();
            Canvas crc = null;
            crc = crholder.lockCanvas();

            bitmap = BitmapFactory.decodeFile(imgSet[0], options);
            // メイン画像セット
            mainFlag = true;
            
            // 画像　実読み込み
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)drow_w, (int)drow_h , true);

            // ガベージクリア
            System.gc();

//            Log.d("onSurfaceCreated", "Finalize Cleared");
            crholder.unlockCanvasAndPost(crc);
            
        }
        /** サーフェイス変更時に呼び出される **/
        @Override
        public void onSurfaceChanged(SurfaceHolder holder,int format, int width , int height){
            super.onSurfaceChanged(holder, format, width, height);
//            Log.i("tag1", "onSurfaceChanged");
            touchFlag = false;
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder){
            super.onSurfaceDestroyed(holder);
            visible = false;
            drawHandler.removeCallbacks(drawRannable);
//            Log.i("tag1", "onSurfaceDestroyed");
            if (bitmap != null) {
	            // Bitmapデータの解放
//	            Log.d("Bitmap:", "データの解放");
	            bitmap.recycle();
	            bitmap = null;
            }
            	intFlag = 0;
        }
        
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels){
            draw();
//            Log.i("tag1", "onOffsetsChanged");
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
//        Log.d("tag1", "onDoubleTapEvent");
            return false;
        }
        
        @Override
        // タッチ処理
        public void onTouchEvent(MotionEvent event) {
            cnt += 1;
            
            //ダブルクリック用タッチイベントリスナー
            mGestureDetector.onTouchEvent(event);
            
            //タッチされた箇所X座標を取得
            touchX = event.getX();
            	//Log.d("Touch", "X="+touchX);
            //タッチされた箇所Y座標を取得
            touchY = event.getY();
            	//Log.d("Touch", "Y="+touchY);

            //zRenge座標拡大率をX,Yそれぞれを割り当てる　→　Xの拡大率を軸に行う
            heightScale = widthScale;
            //Log.d("Scales", "widthScale:"+widthScale+"/heightScale:"+heightScale);
            
            // 基準値はldpi　座標軸を主とする
            float jHipsX = 103 * widthScale;					//ワキ　X軸基準値
            float jHipsY = (130+54+drow_s) * heightScale;		//ワキ　Y軸基準値
            float dRipsX = 182 * widthScale;					//ほくろ　X軸基準値
            float dRipsY = (103+54+drow_s) * heightScale;		//ほくろ　Y軸基準値
            float dBodyX = 130 * widthScale;					//唇　X軸基準値
            float dBodyY = (88+54+drow_s) * heightScale;		//唇　Y軸基準値
	            float dPncX = 134 * widthScale;					//胸パニック　X軸基準値169
	            float dPncY = (143+54+drow_s) * heightScale;	//胸パニック　Y軸基準値270
	            float jPncX = 203 * widthScale;					//胸パニック2　X軸基準値
	            float jPncY = (265+54+drow_s) * heightScale;	//胸パニック2　Y軸基準値
            
            // 長押し時間計測　ライブ壁紙では設定画面に遷移してしまう
            //long nagaosi = event.getDownTime();
            	//Log.d("zRenge", "="+zRenge);
            	//Log.d("scalsY", "="+(73+54+drow_s));
                        	
            //jHipsX = jHipsX * widthScale;
            //jHipsY = jHipsY * heightScale;
            //jHipsY = jHipsY + drow_s * heightScale;            		

            // 口元
            /**
            if(touchX>400&&touchX<600&&touchY>600&&touchY<800){
	            intFlag = 1;
	            flag = true;
	            //imgSource = 0x7f020003;
	            Log.d("Touch jRIPS", "X="+touchX+"/Y="+touchY+"/Flag="+intFlag);
				//
				//指定領域拡大の為　中間値を入れ込む
	            touchX = 400; touchY = 700;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img001sub01);
            }
            **/
            //Log.d("Touch jHIPS", "X="+jHipsX+"/Y="+jHipsY); 

            try{
            	//タップのみの操作制御
                switch (event.getAction()) {
    	            case MotionEvent.ACTION_DOWN:

    	            	//タップダウンのみを判定したタッチ判定（サークル用）
    	                touchFlag = true;
    	                startTime = System.currentTimeMillis();
    	                    	               
    	            	//　ワキ
    	                if(touchX>(jHipsX-zRenge)&&touchX<(jHipsX+zRenge)&&touchY>(jHipsY-zRenge)&&touchY<(jHipsY+zRenge)){
    	    	            intFlag = 2;
    	    	            flag = true;
    	    	            //imgSource = 0x7f020004;
//    	    	            Log.d("Touch jHIPS", "X="+jHipsX+"/Y="+jHipsY+"/Flag="+intFlag);             
//    	    	            Log.d("Renge:",(jHipsX-zRenge)+"<"+touchX+"<"+(jHipsX+zRenge));
//    	    	            Log.d("Renge:",(jHipsY-zRenge)+"<"+touchY+"<"+(jHipsY+zRenge));
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	    	            //touchX = 120; touchY = 1450;//img001sub02
    	    	            
    	    	            //メイン画像からタッチ変更検知用のフラグ
    	    	            if(mainFlag == true){
    	    	                //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001sub02);
    	    	                bitmap = BitmapFactory.decodeFile(imgSet[1]);
    	    	                //メイン画像以外にセット
    	    	                mainFlag = false;
    	    	            }
    	    	            else{
    	    	            	if(flag == true){
        	    	            	FadeFlag = true;
        	    	            }
    	    	            	//mainFlag = true;
    	    	            }
    	                }
    	                //　ほくろ
    	                else if(touchX>(dRipsX-zRenge)&&touchX<(dRipsX+zRenge)&&touchY>(dRipsY-zRenge)&&touchY<(dRipsY+zRenge)){
    	    	            intFlag = 3;//155 113
    	    	            flag = true;
    	    	            //imgSource = 0x7f020007;
    	    	            //Log.d("Touch dRIPS", "X="+dRipsX+"/Y="+dRipsY+"/Flag="+intFlag+"/zRenge= "+zRenge);             
    	    	            //Log.d("Renge:",(dRipsX-zRenge)+"<"+touchX+"<"+(dRipsX+zRenge));
    	    	            //Log.d("Renge:",(dRipsY-zRenge)+"<"+touchY+"<"+(dRipsY+zRenge));
    	    	            //Log.d("RengeX:","dRipsX- ="+(dRipsX-zRenge));
    	    	            //Log.d("RengeX:","dRipsX+ ="+(dRipsX+zRenge));
    	    	            
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	    	            touchX = 750; touchY = 800;
    	    	            if(mainFlag == true){
    	    	                //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001sub05);
    	    	                bitmap = BitmapFactory.decodeFile(imgSet[2]);
    	    	                //メイン画像以外にセット
    	    	                mainFlag = false;
    	    	            }
    	    	            else{
    	    	            	if(flag == true){
        	    	            	FadeFlag = true;
        	    	            }
    	    	            	//mainFlag = true;
    	    	            }
    	                }
    	                //　唇
    	                else if(touchX>(dBodyX-zRenge)&&touchX<(dBodyX+zRenge)&&touchY>(dBodyY-zRenge)&&touchY<(dBodyY+zRenge)){
    	    	            intFlag = 4;
    	    	            flag = true;
    	    	            //imgSource = 0x7f020005;
    	    	            //Log.d("Touch dBODY", "X="+dBodyX+"/Y="+dBodyY+"/Flag="+intFlag);             
    	    	            //Log.d("Renge:",(dBodyX-zRenge)+"<"+touchX+"<"+(dBodyX+zRenge));
    	    	            //Log.d("Renge:",(dBodyY-zRenge)+"<"+touchY+"<"+(dBodyY+zRenge));
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	    	            touchX = 700; touchY = 1300;
    	    	            if(mainFlag == true){
    	    	                //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001sub03);
    	    	                bitmap = BitmapFactory.decodeFile(imgSet[3]);
    	    	                //メイン画像以外にセット
    	    	                mainFlag = false;
    	    	            }
    	    	            else{
    	    	            	if(flag == true){
        	    	            	FadeFlag = true;
        	    	            }
    	    	            	//mainFlag = true;
    	    	            }
    	                }
    	                //　肩
    	                /**
    	                else if(touchX>900&&touchX<1000&&touchY>800&&touchY<900){
    	    	            intFlag = 5;
    	    	            flag = true;
    	    	            //imgSource = 0x7f020006;
    	    	            Log.d("Touch dSHOULDER", "X="+touchX+"/Y="+touchY+"/Flag="+intFlag);             
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	    	            touchX = 500; touchY = 1100;
    	                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001sub04);
    	                } 
    	                **/
    	                //　パニック胸
    	                else if(touchX>(dPncX-zRenge)&&touchX<(dPncX+zRenge)&&touchY>(dPncY-zRenge)&&touchY<(dPncY+zRenge)){
    	    	            //imgSource = 0x7f020006;
//    	    	            Log.d("Touch dPc", "X="+dPncX+"/Y="+dPncY+"/Flag="+intFlag);             
//    	    	            Log.d("Renge:",(dPncX-zRenge)+"<"+touchX+"<"+(dPncX+zRenge));
//    	    	            Log.d("Renge:",(dPncY-zRenge)+"<"+touchY+"<"+(dPncY+zRenge));
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	                    if(DTflag == true){
    	    		            intFlag = 6;
    	    		            flag = true;
//    	    	            	Log.i("ダブルタップフラグ", "ON:SUCCESS！");
//    	    	                Log.d("パニック　：　下腹部", "SUCCESS/Resource Set");
    	    		            if(mainFlag == true){
    	    		                //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001hup01);
    	    		                bitmap = BitmapFactory.decodeFile(imgSet[4]);
    	    		                //メイン画像以外にセット
    	    		                mainFlag = false;
    	    		            }
        	    	            else{
        	    	            	if(flag == true){
            	    	            	FadeFlag = true;
            	    	            }
        	    	            	//mainFlag = true;
        	    	            }

    	    		            pTouchX = 142 * widthScale; pTouchY = 180 * heightScale;
    	    		            
    	    		            //pTouchX = 550; pTouchY = 1700;
    	    	                //FadeFlag = true;
    	    	                //DTflag = false;
    	                    }else{
    	                    	//FadeFlag = false;
//    	                    	Log.d("パニック　：　下腹部", "FAILED/Tap Is Single");
    	                    }
    	                }
    	                //　パニック胸2
    	                else if(touchX>(jPncX-zRenge)&&touchX<(jPncX+zRenge)&&touchY>(jPncY-zRenge)&&touchY<(jPncY+zRenge)){
    	    	            //imgSource = 0x7f020004;
//    	    	            Log.d("Touch jpc", "X="+jPncX+"/Y="+jPncY+"/Flag="+intFlag);             
//    	    	            Log.d("Renge:",(jPncX-zRenge)+"<"+touchX+"<"+(jPncX+zRenge));
//    	    	            Log.d("Renge:",(jPncY-zRenge)+"<"+touchY+"<"+(jPncY+zRenge));
    	    	            // 指定領域拡大の為　中間値を入れ込む
    	                    if(DTflag == true){
    	    		            intFlag = 7;
    	    		            flag = true;
//    	                    	Log.i("ダブルタップフラグ", "ON:SUCCESS！");
//    	                       Log.w("パニック　：　尻", "SUCCESS/Resource Set");
    	    		            if(mainFlag == true){
    	    	                    //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img001hup02);
    	    	                    bitmap = BitmapFactory.decodeFile(imgSet[5]);
    	    		                //メイン画像以外にセット
    	    		                mainFlag = false;
    	    		            }
        	    	            else{
        	    	            	if(flag == true){
            	    	            	FadeFlag = true;
            	    	            }
        	    	            	//mainFlag = true;
        	    	            }

    	        	            pTouchX = 123 * widthScale; pTouchY = 186 * heightScale;
    	        	            //pTouchX = 250; pTouchY = 1450; 唇：131/142
    	                        //FadeFlag = true;
    	                        //DTflag = false;
    	                    }else{
    	                    	//FadeFlag = false;
//    	                    	Log.d("パニック　：　尻", "FAILED/Tap Is Single");
    	                    }
    	                }
    	                else{
    	    	            //intFlag = 0;
    	    	            //flag = true;
    	    	            //imgSource = 0x7f020008;
//    	    	            Log.d("Touch", "Zone-Free / Flag="+intFlag); 
    	    	            // 初期化
    	    	            
    	    	            //touchX = 0; touchY = 0;
    	    	            if(flag == true){
    	    	            	FadeFlag = true;
    	    	            }
    	    	            //Log.d("TouchZone Else", "bitmap On MAIN:"+mainFlag);
    	    	            
//    	    	            Log.d("Touch", "TouchCnt:"+cnt+" / FadeFlag:"+FadeFlag); 
    	    	            //flag = false;
    	                    //bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img_main);
    	                }

    	            	break;
    	            	
    	            case MotionEvent.ACTION_UP:
    	            	
    	                //size = (float) (System.currentTimeMillis() - startTime) / 10f;
    	                
	                	break;
    	            
                }
            }
            catch(Exception e){
            	//Log.d("tag", "Touch Error");
            	return;
            }
            
            // 画像　実読み込み
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)drow_w, (int)drow_h , true);

            if(flag == true){
//                	Log.d("drow_s:",""+drow_s);
            	rParse = base_s;//drow_s;
            	drow_s = 0;
            	//画像毎に位置調整　今作はヘソとパニック尻をベースポイントに設定する
            	if(intFlag == 7 || intFlag == 4){
            		//drow_s = base_s;
            	}
            	//Log.d("rParse", "rParse:"+rParse);
            }
            
//            Log.d("Touch Event", "CLEARED");
        }

        private void draw(){
            final SurfaceHolder holder = getSurfaceHolder();
//            Log.d("◆START", "MAIN FRAME");
            //Log.d("TIME", "4:" + System.currentTimeMillis());
            
//            Log.i("drawキャンバスサイズ:","WIDTH="+canvasWidth);
//            Log.i("drawキャンバスサイズ:","HEIGHT="+canvasHeight);
//            Log.i("draw描画サイズ:","WIDTH="+bmpFWidth);
//            Log.i("draw描画サイズ:","HEIGHT="+bmpFHeight);

            try{
            c = null;
                c = holder.lockCanvas();
                if(c != null){
                c.drawColor(Color.BLACK); //背景色で塗りつぶし
                
                //ガベージクリア
                //System.gc();
                
                    Paint p = new Paint();
                    p.setAntiAlias(true);

                    Matrix mtrx = new Matrix();
                    mtrx.postScale(1.0f, 1.0f);
                    float[] v = new float[9];
                    mtrx.getValues(v);
                    v[Matrix.MTRANS_X] = 0.0f;
                    v[Matrix.MTRANS_Y] = 0.0f;
                    mtrx.setValues(v);
                                       
                    Paint paint;
                    Paint tPaint;
                    
                    // パニック用の画像処理　主に重点箇所の拡大とフェードアウト処理
//                	Log.w("PANIC", "boolean:"+DTflag);
                    
                	//タッチ画像表示時、再度のタッチによるフェードアウトを行う
                    if(FadeFlag == true & flag == true){
                    	
                    	//画像フェードアウト処理にはタッチサークルを表示しない
                    	touchFlag = false;
                    	
//                    	Log.w("Fade", "ON START--");
	                    // touchX　：　タッチ取得座標X
	                    // touchY　：　タッチ取得座標Y
                    	
                        // 元画像から切り出す位置（タッチ用画像に合った座標を指定）の設定
                    	if(intFlag == 7){
                    		//touchX = 0;
                    		//touchY = 0;
                    	}
                        src = new Rect((int)pTouchX, (int)pTouchY, bmpFWidth, bmpFHeight);
                    
                        // リサイズ画像の領域を指定
                        // パニック１　尻の画像はやや拡大
                        boolean rParseFlag = false;
                        if(intFlag == 7 || intFlag == 6){
	                        src = new Rect((int)pTouchX, (int)pTouchY, bmpFWidth, bmpFHeight);
	                        dst = new Rect(0, 0, bmpFWidth*1, bmpFHeight*1);
//	                        Log.w("PanicSourceSet", "pTouchX:"+pTouchX+" / pTouchY:"+pTouchY);
                        }
                        // パニック２　股画像は拡大無し
                        else{
	                        //設定のみ　初回バージョンには使わない
	                        dst = new Rect(0, 0, bmpFWidth, bmpFHeight);
                        }
//                        Log.w("RECT", "dst:"+dst);
                        
                        // フェードアウト処理　繰り返し呼ばれるdrawで、呼ばれるごとに透過率をデクリメントする
						if(pAlpha > 0 ){
							paint = new Paint();
							paint.setAlpha(pAlpha);
							paint.setAntiAlias(true);
							
		                    Matrix mtrix = new Matrix();
		                    
		                    //rParse += 0.05;
//		                    //mtrix.postScale(1.0f+rParse, 1.0f+rParse);
		                    mtrix.postScale(2.5f, 2.5f,pTouchX, pTouchY);//-pAlpha);
		                    //mtrix.postTranslate(pTouchX, pTouchY);
		                    //mtrix.postScale(sx, sy, px, py);
		                    
	                        //if(intFlag == 7){
	                        //	Log.w("intFlag:7", "拡大処理"+rParse);
	                        	//bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpFWidth, bmpFHeight, mtrix, true);
	                        	//　やや拡大処理
	                        //	dst = new Rect(0, 0, bmpFWidth*1, bmpFHeight*1);
	                        //}

							if(intFlag == 7 || intFlag == 6){
//								Log.w("intFlag:7", "拡大処理alpha内:MATRIX");
								//c.drawBitmap(bitmap,0,0,paint);
								//c.drawBitmap(bitmap,src,dst,paint);
								c.drawBitmap(bitmap,mtrix,paint);
								
							}else{
								// Rectを使わない通常処理
//								Log.w("intFlag:etc", "Rectを使わない通常処理:MATRIX");
								c.drawBitmap(bitmap,mtrx,paint);
			            		//c.drawCircle(touchX, touchY, 100, tPaint);
							}
							// 透過率の減算値
							pAlpha -= 15;
//							Log.d("alpha1", "alpha= "+pAlpha);
						}
						else{
//							Log.d("alpha", "0 Nothing");
							pAlpha = 255;
							flag = false;
							DTflag = false;
							intFlag = 0;
							FadeFlag = false;
							//サブBitmapに保管されたメイン画像をメインBitmapへ入れ込む
							//bitmap = bitmapTouch;
				            bitmap = BitmapFactory.decodeFile(imgSet[0]);
				            bitmap = Bitmap.createScaledBitmap(bitmap, (int)drow_w, (int)drow_h , true);

							//メイン画像をセット
							mainFlag = true;
							cnt = 0;
							
							drow_s = rParse;
							//Log.d("drow_s", "drow_s2:"+drow_s);
							//FadeFlag = false;
							//bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img_main);
							//c.drawBitmap(bitmap,ofsW,drow_s,p);
						}
						//flag = false;
                    }
                    // タッチ画像用の座標がタッチされていない場合
                    else{
//	                    Log.i("main draw", "メイン画像描画");
	                    //Log.d("表示画像", "フラグ："+intFlag+"/drow_s:"+drow_s+"/ofsW:"+ofsW);
	                    c.drawBitmap(bitmap,ofsW,drow_s,p);
	                    //c.drawCircle(touchX, touchY, 100, tPaint);
                    }
                    
                    //タッチサークル用のフェード処理
                    if(touchFlag == true){
                    	
                        if(tAlpha > 0 ){
							tPaint = new Paint();
							tPaint.setAlpha(tAlpha);
							tPaint.setAntiAlias(true);
		                    tPaint.setColor(Color.parseColor("#FF6699"));

                        	tPaint.setAlpha(tAlpha);
                        	tAlpha -= 20;
                        	size = (float) (System.currentTimeMillis() - startTime) / 10f;
                        	c.drawCircle(touchX, touchY, size, tPaint);
                        	//Log.i("draw circle:", "Alpha:"+tAlpha+"/size:"+size);
                        }
                        else{
                        	tAlpha = 255;
                        	touchFlag = false;
                        	//c.drawCircle(touchX, touchY, 100, tPaint);
                        }
                    }
                    //Log.i("draw circle:", "size:"+size);

//                    Log.d("draw main", "CLEARED");
                }
            }finally{
                if(c != null){
                    holder.unlockCanvasAndPost(c);
//                    Log.d("holder main:", "unlocked");
                    //if (bitmap != null) {
	                   // Bitmapデータの解放
                       //Log.d("Bitmap:", "データの解放");
	                   //bitmap.recycle();
	                   //bitmap = null;
                    //}
                }
            }
            
            // 次の描画をセット
            drawHandler.removeCallbacks(drawRannable);
  //          Log.d("drawHandler", "removeCallbacks");
            if(visible){
                //Log.d("Next Images:", "Delay 10");
                drawHandler.postDelayed(drawRannable, 10);
            }
        }

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// TODO 自動生成されたメソッド・スタブ
			
		}        
    }


}