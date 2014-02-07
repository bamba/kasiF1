package move.picture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class PicmoveActivity extends Activity {
    /** Called when the activity is first created. */
	
	public static int ScreenHeight;;
	public static int Screenwidth;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        ScreenHeight = dm.heightPixels;
        Screenwidth = dm.widthPixels;
        
        setContentView(new MenuView(this));
        
    }
static public class MenuView extends View {
    	
		MyButton ButtonRestart;
		MyButton ButtonExit;
		MyButton Logo;
		Context pubContex;
        public MenuView(Context context) {
        	super(context);
    		pubContex = context;
        	try {
    			AssetManager assetManager = pubContex.getAssets();
    			
    			Logo = new MyButton(assetManager, "logo.png", Float.valueOf(ScreenHeight/2 - 300),  Float.valueOf(0));
    			
    			ButtonRestart = new MyButton(assetManager, "restart.png", Float.valueOf(ScreenHeight/2),  Float.valueOf(0));
    			ButtonExit = new MyButton(assetManager, "exit.png", Float.valueOf(ScreenHeight/2 + 150),  Float.valueOf(0));
    			
    			ButtonRestart.ButtonX = (float) ((Screenwidth - ButtonRestart.bitmap.getWidth())/2); 
    			ButtonExit.ButtonX = (float) ((Screenwidth - ButtonExit.bitmap.getWidth())/2); 
    			Logo.ButtonX = (float) ((Screenwidth - Logo.bitmap.getWidth())/2); 
    			
    		}
    		catch(Exception e){
    			
    		}
    	}
        @SuppressLint("DrawAllocation")
		protected void onDraw(Canvas canvas) {
    		//Draws all the things to my Screen
        	
        	if(Logo.show)
     	       canvas.drawBitmap(Logo.bitmap, Logo.ButtonX,Logo.ButtonY, null);
        	
    		if(ButtonRestart.show)
        	       canvas.drawBitmap(ButtonRestart.bitmap, ButtonRestart.ButtonX,ButtonRestart.ButtonY, null);
    		
    		if(ButtonRestart.show)
        	       canvas.drawBitmap(ButtonExit.bitmap, ButtonExit.ButtonX,ButtonExit.ButtonY, null);
        		
    		invalidate();
    	}
    	@Override
        public boolean onTouchEvent(MotionEvent ev) {
    		//On touch on the screen
            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN: {
                	
                	
                	if(ButtonRestart.Clicked(ev.getX(), ev.getY()))
                	{
                		// restart Game
                		//setContentView(new GraphicsView(pubContex));
                		Intent intent = new Intent(pubContex, GameView.class);
        		        pubContex.startActivity(intent);
                		//startActivityForResult(intent,0);
                		
                	}
                	if(ButtonExit.Clicked(ev.getX(), ev.getY()))
                	{
                		// Exit Game
                		System.exit(0);
                		
                	}
                    break;
                }
            }
            return true;
        }
        
	}
}