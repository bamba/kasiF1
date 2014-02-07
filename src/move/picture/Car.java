package move.picture;

import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Car {
	
	public Bitmap bitmap;
	public Float CarY =Float.valueOf(0);
	public Float CarX = Float.valueOf(0);
	public int type;
	public int road;
	public boolean show;
	
	Car(AssetManager assetManager,String picture,Float Y,Float X,int road, int type,boolean show)
	{
		try {
			
			InputStream inputStream = assetManager.open(picture);
			bitmap = BitmapFactory.decodeStream(inputStream);
			
			inputStream.close();
			CarY = Y;
			CarX = X;
			this.show = show;
			this.road = road;
			this.type = type;
		}
		catch(Exception e){
			//
		}
	}
	
	boolean Crashed(Car BadCar)
	{
		if(BadCar.road == this.road)
			if(BadCar.CarY + BadCar.bitmap.getHeight() >= this.CarY)
				if(BadCar.CarY < this.CarY+this.bitmap.getHeight())
					return true;
		
		return false;
	}
}
