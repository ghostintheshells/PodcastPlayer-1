package website.julianrosser.podcastplayer;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A fragment containing a the playerview
 */
public class PlayerFragment extends android.support.v4.app.Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "PlayerFragment";


    static SeekBar seekBar;


    static TextView textSongTitle;
    static TextView textSongArtist;

    /**
     * Required empty public constructor
     */
    public PlayerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlayerFragment newInstance(int sectionNumber) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ImageButton playPause = (ImageButton) view.findViewById(R.id.buttonPlay);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if already playing, pause
                if (MainActivity.musicSrv.isPng()) {

                    MainActivity.musicSrv.pausePlayer();
                    playPause.setImageDrawable(getResources().getDrawable(R.drawable.play));

                } else {

                    // if initialized
                    if (MainActivity.musicSrv != null && MainActivity.musicBound) {

                        playPause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                        MainActivity.musicSrv.go();
                    } else {
                        Log.i(TAG, "SERVICE NULL / PLAYER NOT BOUND");
                    }
                }
            }
        });

        // New Audio Track Button
        final Button newFile = (Button) view.findViewById(R.id.buttonTrack);
        newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.musicSrv.playRandom();
                playPause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            }
        });


        if (MainActivity.musicSrv != null && MainActivity.musicSrv.isPng()) {
            textSongTitle = (TextView) view.findViewById(R.id.songTitle);
            textSongArtist = (TextView) view.findViewById(R.id.songArtist);
            textSongTitle.setText(MusicService.songTitle);
            textSongArtist.setText(MusicService.songArtist);
        }

        // todo set if playing

        ImageButton rewind = (ImageButton) view.findViewById(R.id.buttonRewind);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.musicSrv.playPrev();
            }
        });

        ImageButton forward = (ImageButton) view.findViewById(R.id.buttonForward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.musicSrv.playNext();
            }
        });

        // play current song
        //MainActivity.musicSrv.playSong();
       //Log.i(TAG, "PLAY SONG");


        return view;
    }

        /*  Create listener
        playPause.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {

            }
        });

        //noinspection deprecation
        //playPause.setImageDrawable(getResources().getDrawable(R.drawable.pause));

        ImageButton rewind = (ImageButton) view.findViewById(R.id.buttonRewind);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AudioPlayerService.rewind();
            }
        });

        ImageButton forward = (ImageButton) view.findViewById(R.id.buttonForward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayerService.forward();
            }
        });

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    AudioPlayerService.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button buttonTrack = (Button) view.findViewById(R.id.buttonTrack);
        buttonTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int i = r.nextInt(3);
                Intent mAudioPlayerService = new Intent(getActivity(), AudioPlayerService.class);
                mAudioPlayerService.setAction(AudioPlayerService.ACTION_SET_TRACK);
                getActivity().startService(mAudioPlayerService);
            }
        });

        */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //AudioPlayerService.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // AudioPlayerService.stop();
    }
}


