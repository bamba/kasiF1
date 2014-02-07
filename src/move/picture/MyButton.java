package move.picture;

import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyButton {
	
	public Bitmap bitmap;
	public Float ButtonY = Float.valueOf(0);
	public Float ButtonX = Float.valueOf(0);
	public boolean show;
	
	MyButton(AssetManager assetManager,String picture,Float Y,Float X)
	{
		try {
			
			InputStream inputStream = assetManager.open(picture);
			bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
			ButtonY = Y;
			ButtonX = X;
			show = true;
			
		}
		catch(Exception e){
			//
		}
	}
	public boolean Clicked(Float X,Float Y)
	{
		if(show)
			if(X >= ButtonX )
				if(X <= ButtonX+bitmap.getWidth())
					if(Y >= ButtonY)
						if(Y <= ButtonY + bitmap.getHeight())
							return true;
		return false;
	}

}
