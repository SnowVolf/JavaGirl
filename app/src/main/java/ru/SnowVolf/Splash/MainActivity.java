package ru.SnowVolf.Splash;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import android.os.Process;

public class MainActivity extends Activity
{
	//storage

	/*public static final int REQUEST_WRITE_STORAGE = 91;

	 @Override
	 public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	 {
	 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	 switch (requestCode)
	 {
	 case REQUEST_WRITE_STORAGE: {
	 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
	 Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_LONG).show();
	 else
	 Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
	 }
	 }
	 }*/
	//
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	//меню

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate main_menu.xml 
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		int id = item.getItemId();
        if (id == R.id.visit_git)
		{
            GoToGit();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	public void GoToGit()
	{
		Uri uri = Uri.parse("https://github.com/SnowVolf/JavaGirl/");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}





	//нажатие на картинку
	public void onGirlClick(View v)
	{
		//рандомно выводим тосты с этим содержанием
		String[] rnd = new String[]{"Привет",
			"Запуск браузера...", "Открыть DeviantArt?",
			"Мне было лень писать", "Редирект...","Headphones girl by NeoArtCore",
			"Хехехе", "Спасибо:\nradiation15\niSanechek\nМорфий\n\nБез них я бы даже не начал всерьёз заниматься кодингом...",
			"Ты кто такой? - Давай досвидания!"};
		Toast toast = Toast.makeText(getApplicationContext(), rnd[(int) (Math.random() * rnd.length)], Toast.LENGTH_LONG);
		//задаем позицию тоста - верх. смещение: X,Y (по центру экрана)
		toast.setGravity(Gravity.TOP, 0, 100);
		//пробуем показать картинку в тосте. если не получается -- ловим экзепшн
		//не работает на Android 6 и выше. там будет просто тост
		try
		{
			LinearLayout toastContainer = (LinearLayout) toast.getView();
			ImageView catImageView = new ImageView(getApplicationContext());
			catImageView.setImageResource(R.drawable.deviant_art);
			toastContainer.addView(catImageView, 0);
		}
		catch (Exception VolfLog)
		{}//ловим
		toast.show();
	}
	//после показывания тоста - открываем в браузере ссылку на DeviantArt
	//код обработки находится в GirlDialog.java
	/*public void onLongGirlClick(View v){
	 Toast toast = Toast.makeText(getApplicationContext(), "onGirlClick = Long Clicked", Toast.LENGTH_LONG);
	 toast.show();
	 }*/
	public void onDeviantArtClick(View view)
	{
    	GirlDialog girl = new GirlDialog();
		girl.show(getFragmentManager(), "girl");
    }

	public void onWebClick(View v)
	{
		Uri uri = Uri.parse("http://4pda.ru/forum/index.php?showuser=4324432");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	@Override
	public void onBackPressed()
	{
		finish();//закрываем активити
	}
	//кнопка выхода
	public void onExitClick(View v)
	{
		Process.killProcess(Process.myPid());
		finishAndRemoveTask(); 
		System.exit(1);

	}
}
