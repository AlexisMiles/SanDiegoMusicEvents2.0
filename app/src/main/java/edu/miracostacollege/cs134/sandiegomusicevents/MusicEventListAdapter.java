package edu.miracostacollege.cs134.sandiegomusicevents;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import edu.miracostacollege.cs134.sandiegomusicevents.model.MusicEvent;

public class MusicEventListAdapter extends ArrayAdapter<MusicEvent> {

    private Context mContext;
    private int mResourceId;
    private List<MusicEvent> mAllEvents;

    public MusicEventListAdapter(Context context, int resource, @NonNull List<MusicEvent> objects){
        super(context, resource, objects);
        mContext = context;
        mResourceId = resource;
        mAllEvents = objects;
    }

    //In order to bridge the View (music_event_list_item) with the Model (MusicEvent)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate custom layout from List<MusicEvent>
        MusicEvent focusedEvent = mAllEvents.get(position);

        //Manually inflate the custom layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Tell the inflater to inflate music_event_list_item
        View customView = inflater.inflate(mResourceId, null);

        //Fill the parts of the custom view
        ImageView listItemImageView = customView.findViewById(R.id.listItemImageView);
        TextView listItemArtistTextView = customView.findViewById(R.id.listItemArtistTextView);
        TextView listItemDateTextView = customView.findViewById(R.id.listItemDateTextView);

        listItemArtistTextView.setText(focusedEvent.getArtist());
        listItemDateTextView.setText(focusedEvent.getDate());

        //Load the image dynamically
        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(focusedEvent.getImageName());
            Drawable image = Drawable.createFromStream(stream, focusedEvent.getArtist());
            listItemImageView.setImageDrawable(image);
        } catch (IOException e) {
            Log.e("SD Music Events", e.getMessage());
        }

        //Return customView, inflated with all information
        return customView;
    }
}
