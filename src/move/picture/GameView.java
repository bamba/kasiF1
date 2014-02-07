package move.picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameView extends Activity{
	
	public static long GameSpeed = 25;
	public static int ScreenHeight;;
	public static int Screenwidth;
    
	public static boolean crashed = false;
	public static float Score = (float) 0.0;
	public static int Levels = 1;
	public static boolean pause = false; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//  Auto-generated method stub8
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        

        ScreenHeight = dm.heightPixels;
        Screenwidth = dm.widthPixels;
		
        setContentView(new GraphicsView(this));
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        // do something on back.
	    	//Toast.makeText(getApplicationContext(), "Test works", Toast.LENGTH_SHORT).show();
	    	//
	    	if(crashed)
	    	{
	    		System.exit(0);
	    		return true;
	    	}
	    	if(!pause)
	    		pause = true;

	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	 static public class GraphicsView extends View {
	    	

		 
	    	Car MyCar;
	    	
	    	Random randomGenerator = new Random();
	    	
	    	MyButton ButtonRestart;
	    	
	    	MyButton LeftButton;
	    	MyButton RightButton;
	    	MyButton NextLevelButton;
	    	
	    	MyButton ExitButton;
	    	MyButton ResumeButton;
	    	
	    	MyButton CrashImage; 
	    	
	    	Float Road_Left = Float.valueOf(Screenwidth/2-90);
	    	Float Road_Right = Float.valueOf(Screenwidth/2+10);
	    	
	    	int Button_top = 600;
	    	int MyCarTop = 500;
	    	
	    	Context pubContex;
	    	boolean nextClick = false;
	    	Timer myTimer = new Timer();
	    	
	    	AssetManager assetManager;
	    	public List<Car> BadCars = new ArrayList<Car>();
	    	int BadCarCount = 10;
	    	
	    	public GraphicsView(Context context) {
	    		super(context);
	    		pubContex = context;
	    		

	    		Score = (float) 0.0;
	    		Levels = 1;
	    		try{
	    			assetManager = context.getAssets();

	    			
	    			ReloadBadCars();
	    			
	    			ButtonRestart = new MyButton(assetManager, "restart.png", Float.valueOf(Button_top),  Float.valueOf(0));
	    			ButtonRestart.ButtonX = (float) ((Screenwidth - ButtonRestart.bitmap.getWidth())/2); 
	    			ButtonRestart.show = false;
	    			
	    			LeftButton = new MyButton(assetManager, "left.png", Float.valueOf(Button_top),  Float.valueOf(0));
	    			LeftButton.ButtonY = (float) (ScreenHeight - LeftButton.bitmap.getHeight());
	    			
	    			RightButton = new MyButton(assetManager, "right.png", Float.valueOf(Button_top),  Float.valueOf(370));
	    			RightButton.ButtonY = (float) (ScreenHeight - RightButton.bitmap.getHeight());
	    			RightButton.ButtonX = (float) (Screenwidth - RightButton.bitmap.getWidth());
	    			
	    			ExitButton = new MyButton(assetManager, "quit.png", Float.valueOf(Button_top + 120),  Float.valueOf(370));
	    	    	ResumeButton = new MyButton(assetManager, "resume.png", Float.valueOf(Button_top),  Float.valueOf(370));
	    	    	
	    	    	ExitButton.ButtonX = (float) ((Screenwidth - ExitButton.bitmap.getWidth())/2); 
	    	    	ResumeButton.ButtonX = (float) ((Screenwidth - ResumeButton.bitmap.getWidth())/2); 
	    			
	    			MyCar = new Car(assetManager, "car.png", Float.valueOf(500), Road_Right, 1, 1,true);
	    			MyCar.CarY = (float) (ScreenHeight - MyCar.bitmap.getHeight() - RightButton.bitmap.getHeight()*2);
	    			
	    			CrashImage = new MyButton(assetManager, "bang.png", Float.valueOf(100),  Float.valueOf(10));
	    			CrashImage.show = false;
	    			CrashImage.ButtonX = (float) ((Screenwidth - CrashImage.bitmap.getWidth())/2); 
	    			
	    			
	    			NextLevelButton = new MyButton(assetManager, "next_level.png", Float.valueOf(100),  Float.valueOf(10));
	    			NextLevelButton.show = false;
	    			NextLevelButton.ButtonX = (float) ((Screenwidth - NextLevelButton.bitmap.getWidth())/2); 
	    			NextLevelButton.ButtonY = (float) (ScreenHeight - NextLevelButton.bitmap.getHeight() - 40); 
	    		}
	    		catch(Exception e){
	    			
	    		}
	    		
	    		play(GameSpeed);

	    	}
	    	private void ReloadBadCars() 
	    	{
	    		BadCars.clear();
	    		for(int i=0; i< BadCarCount; i++)
    			{
    				Car MyBadCar;
 
    				MyBadCar = new Car(assetManager, "bad_car.png", Float.valueOf(0), Road_Right, 1, 1,false);
    				
       				//Random randomGenerator = new Random();
    				int path = randomGenerator.nextInt(2);
    				if(path == 1)
    				{
    					MyBadCar.road = path;
    					MyBadCar.CarX = Road_Right;
    				}
    				else
    				{
    					MyBadCar.road = path;
    					MyBadCar.CarX = Road_Left;
    				}
    				BadCars.add(MyBadCar);
    			}
    			//Show the First BAD CAR
    			BadCars.get(0).show = true;
				
			}
			void play(long speed)
	    	{
				if(speed <= 0)
					speed = 1;
	    		myTimer.schedule(new TimerTask() {
	    			
	    			@Override
	    			public void run() {
	    				TimerMethod();
	    			}
	    		}, 0, speed);
	    	}
	    	private void TimerMethod()
	    	{
	    		//This method is called directly by the timer
	    		//and runs in the same thread as the timer.

	    		//We call the method that will work with the UI
	    		//through the runOnUiThread method.
	    		((Activity) pubContex).runOnUiThread(Timer_Tick);
	    		
	    	}

	    	private Runnable Timer_Tick = new Runnable() {
	    		public void run() {

	    		//This method runs in the same thread as the UI.    	       
	    		//Score=(float) (Score + 0.005);
	    		//Do something to the UI thread here

	    		if(pause)
	    		{
	    			invalidate();
	    			return;
	    		}
	    		//level 2
	    		if((int)Score== 20)
	    		{
	    			myTimer.cancel();
	    			myTimer = new Timer();
	    			GameSpeed-=5;
	    			NextLevelButton.show = true;
	    			Levels++;
	    			nextClick = true;
	    			System.gc();
	    		}
	    		//level 3
	    		if((int)Score== 40)
	    		{
	    			myTimer.cancel();
	    			myTimer = new Timer();
	    			GameSpeed-=5;
	    			NextLevelButton.show = true;
	    			Levels++;
	    			nextClick = true;
	    			System.gc();
	    		}
	    		//level 4
	    		if((int)Score== 90)
	    		{
	    			myTimer.cancel();
	    			myTimer = new Timer();
	    			GameSpeed-=5;
	    			NextLevelButton.show = true;
	    			Levels++;
	    			nextClick = true;
	    			System.gc();
	    		}
	    		//level 5
	    		if((int)Score== 120)
	    		{
	    			myTimer.cancel();
	    			myTimer = new Timer();
	    			GameSpeed-=1;
	    			NextLevelButton.show = true;
	    			Levels++;
	    			nextClick = true;
	    			System.gc();
	    		}
	    		//level 6
	    		if((int)Score== 140)
	    		{
	    			myTimer.cancel();
	    			myTimer = new Timer();
	    			GameSpeed-=2;
	    			NextLevelButton.show = true;
	    			Levels++;
	    			nextClick = true;
	    			System.gc();
	    		}
	    		int i = 1;
	    		for (Car MyBadCar : BadCars) {
	    			
	    			if(MyBadCar.show)
	    			{	
	    				MyBadCar.CarY+=(float) 5;
	    			
	    				if(MyCar.Crashed(MyBadCar))
	    				{
	    					// do crash
	    					CrashImage.show = true;
	    					myTimer.cancel();
	    					myTimer = new Timer();
	    					crashed = true;
	    					GameSpeed = 25;

	    					System.gc();
	    				}
	    			}
	    			if(MyBadCar.CarY > ((MyBadCar.bitmap.getHeight()*2)+50))
	    			{
	    				if(i< BadCars.size())
	    				{
	    					BadCars.get(i).show = true;
	    				}
	    				else
	    				{
	    					BadCars.get(0).show = true;
	    				}
	    			}
	    			if(MyBadCar.CarY > 2000)
	    			{
	    				//set the car back to the top of the screen
	    				//Score=(float) (Score + 1);
	    				MyBadCar.CarY = Float.valueOf(-50);
	    				
	    				int path = randomGenerator.nextInt(2);
	    				if(path == 1)
	    				{
	    					MyBadCar.road = path;
	    					MyBadCar.CarX = Road_Right;
	    				}
	    				else
	    				{
	    					MyBadCar.road = path;
	    					MyBadCar.CarX = Road_Left;
	    				}
	    				
	    			}
	    			i++;
	    		}
	    		Score+=0.02;
	    		invalidate();
	    		}
	    	};
	    	
	    	@SuppressLint({ "DrawAllocation", "DrawAllocation" })
			@Override
	    	protected void onDraw(Canvas canvas) {
	    		//Draws all the things to my Screen
	    		
	    			
	    		if(pause)
	    		{
	    			//TODO
	    			canvas.drawBitmap(ResumeButton.bitmap, ResumeButton.ButtonX,ResumeButton.ButtonY, null);
	    			canvas.drawBitmap(ExitButton.bitmap, ExitButton.ButtonX,ExitButton.ButtonY, null);
	    			return;
	    			
	    		}
	    		
	    		Paint paint = new Paint();
	    	    paint.setColor(Color.WHITE);
	    	    int tempscore = (int)Score;
	    	    if(NextLevelButton.show)
	    	    {
	    	    	canvas.drawBitmap(NextLevelButton.bitmap, NextLevelButton.ButtonX,NextLevelButton.ButtonY, null);
	    			paint.setTextSize(40);
	    			paint.setColor(Color.BLUE);	    			
	    			paint.setAntiAlias(true);
	    			
	    			Typeface font = Typeface.createFromAsset(getContext().getAssets(),"gamefont.ttf");
	    			paint.setTypeface(font);
		    	    canvas.drawText("Score: " +String.valueOf(tempscore), 150, CrashImage.ButtonY+CrashImage.bitmap.getHeight()+20,paint);
		    	    canvas.drawText("Level: " +String.valueOf(Levels -1), 150, CrashImage.ButtonY+CrashImage.bitmap.getHeight()-60,paint);
	    			return;
	    	    }
	    		if(CrashImage.show)
	    		{
	    			canvas.drawBitmap(CrashImage.bitmap, CrashImage.ButtonX,CrashImage.ButtonY, null);
	    			paint.setTextSize(40);
	    			paint.setColor(Color.BLUE);	    			
	    			paint.setAntiAlias(true);
	    			
	    			Typeface font = Typeface.createFromAsset(getContext().getAssets(),"gamefont.ttf");
	    			paint.setTypeface(font);
		    	    canvas.drawText("Score: " +String.valueOf(tempscore), 150, CrashImage.ButtonY+CrashImage.bitmap.getHeight()+20,paint);
		    	    canvas.drawText("Level: " +String.valueOf(Levels), 150, CrashImage.ButtonY+CrashImage.bitmap.getHeight()-60,paint);
		    	    
		    	    canvas.drawBitmap(ButtonRestart.bitmap, ButtonRestart.ButtonX,ButtonRestart.ButtonY, null);
		    	    ButtonRestart.show = true;
		    	    
		    	    canvas.drawBitmap(ExitButton.bitmap, ExitButton.ButtonX,ExitButton.ButtonY, null);
	    			
		    	    ExitButton.show = true;
	    			return;
	    		}	
	    		
	    		if(MyCar.show)
	    			canvas.drawBitmap(MyCar.bitmap, MyCar.CarX,MyCar.CarY, null);
	    	       
	    		if(LeftButton.show)
	    	       canvas.drawBitmap(LeftButton.bitmap, LeftButton.ButtonX,LeftButton.ButtonY, null);
	    		
	    	    if(RightButton.show)
	    	       canvas.drawBitmap(RightButton.bitmap, RightButton.ButtonX,RightButton.ButtonY, null);
	    	    
	    	    for (Car MyBadCar : BadCars) {
	    	    	if(MyBadCar.show)
	        	    	canvas.drawBitmap(MyBadCar.bitmap, MyBadCar.CarX,MyBadCar.CarY, null);
				}
	    	    
	    	    
	    	    canvas.drawLine(Screenwidth/2 - 100, 0, Screenwidth/2 - 100, ScreenHeight, paint);
	    	    canvas.drawLine(Screenwidth/2 +100, 0, Screenwidth/2+100, ScreenHeight, paint);
	    	    
	    	    
	    	    paint.setTextSize(15);
	    	    canvas.drawText("Score: "+String.valueOf(tempscore), Screenwidth-100, 50,paint);
	    	    canvas.drawText("Level: " +String.valueOf(Levels), 0, 50,paint);
    			
	    	    paint.setStyle(Style.STROKE);
	    	    paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
	    	    canvas.drawLine(Screenwidth/2, 0, Screenwidth/2, ScreenHeight, paint);
	    	    
	    	}
	    	@Override
	        public boolean onTouchEvent(MotionEvent ev) {
	    		//On touch on the screen
	            switch (ev.getAction()) {

	                case MotionEvent.ACTION_DOWN: {
	                	
	                	if(ExitButton.Clicked(ev.getX(), ev.getY()) && ExitButton.show == true)
	                	{
	                		System.exit(0);
	                	}
	                	if(ResumeButton.Clicked(ev.getX(), ev.getY()) && ResumeButton.show == true)
	                	{
	                		pause = false;
	                		
	                	}
	                	if(LeftButton.Clicked(ev.getX(), ev.getY()) && LeftButton.show == true)
	                	{
	                		//Do Move car left
	                		if(MyCar.road == 1)
	                			MyCar.CarX = Road_Left;
	                		
	                		MyCar.road = 0;
	                		
	                	}
	                	if(RightButton.Clicked(ev.getX(), ev.getY()) && RightButton.show == true)
	                	{
	                		//Do Move car right
	                		if(MyCar.road == 0)
	                			MyCar.CarX = Road_Right;
	                		
	                		MyCar.road = 1;
	                	}
	                	if(NextLevelButton.Clicked(ev.getX(), ev.getY()) && NextLevelButton.show == true)
	                	{
	                		
	                		if(!nextClick)
	                			return true;
	                		NextLevelButton.show = false;
	                		play(GameSpeed);
	                		ReloadBadCars();
	                		Score++;
	                		nextClick = false;
	                	}
	                	if(ButtonRestart.Clicked(ev.getX(), ev.getY()) && ButtonRestart.show == true)
	                	{
	                		//Restart
	                		//
	                		ButtonRestart.show = false;
	                		ExitButton.show = false;
	                		Intent intent = new Intent(pubContex, GameView.class);
	        		        pubContex.startActivity(intent);
	        		        System.exit(0);
	                	}
	                    break;
	                }
	            }
	            return true;
	        }
	    }

}
