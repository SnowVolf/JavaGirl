package ru.SnowVolf.Splash;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.net.*;
import android.widget.*;


public class GirlDialog extends DialogFragment
{
	public String neo = "http://neoartcore.deviantart.com/art/";//теперь в uri вставляем только номер арта
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.deviant_art);
		builder.setMessage(R.string.da_author);
		builder.setIcon(R.drawable.deviant_art);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					Uri uri = Uri.parse("http://neoartcore.deviantart.com/art/Headphone-girl-537402059");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);

				}
			});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p2, int p3)
				{
					dismiss();
				}
			});
		builder.setNeutralButton(R.string.random, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p3, int p4)
				{

					String[] rnd2 = new String[]{neo + "Hestia-543590491",//hestia
						neo + "Ahri-516972803", neo + "Katarina-Fanart-543181372",//ahri and katarina
						neo + "so-hot-619945455", neo + "Gumiho-nine-tail-fox-Ahri-404642919","http://www.deviantart.com/art/Ahri-485763744",
						"http://jyzx.deviantart.com/art/Ahri-567867099", "", ""};
					Uri uri = Uri.parse(rnd2[(int) (Math.random() * rnd2.length)]);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});

		return builder.create();
	}
}
