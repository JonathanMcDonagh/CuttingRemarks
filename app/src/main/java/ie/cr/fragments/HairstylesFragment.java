package ie.cr.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import ie.cr.R;
import ie.cr.main.CuttingRemarksApp;


public class HairstylesFragment extends Fragment {

    private CuttingRemarksApp app;

    private TextView 		spf, bc, pff;
    private ImageView spfImg,ppfImg, bcImg;

    public HairstylesFragment() {
        //Required empty constructor
    }

    //Instance for hairstyles fragment
    public static HairstylesFragment newInstance() {
        HairstylesFragment fragment = new HairstylesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflates the Hairstyles fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scrollable_hairstyles, container, false);

        spf =  v.findViewById(R.id.sidepartfadetext);
        bc =  v.findViewById(R.id.bowlcuttext);
        pff = v.findViewById(R.id.pointfivefadetext);

        spfImg = v.findViewById(R.id.sidepartfadeimg);
        ppfImg = v.findViewById(R.id.pointfivefadeimg);
        bcImg = v.findViewById(R.id.bowlcutimg);

        return v;
    }

}
