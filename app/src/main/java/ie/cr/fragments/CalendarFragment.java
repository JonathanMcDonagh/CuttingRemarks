package ie.cr.fragments;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

import ie.cr.R;
import ie.cr.main.CuttingRemarksApp;


public class CalendarFragment extends Fragment {

    private CuttingRemarksApp app;

    private TextView 		calendarTxt;
    private CalendarView cv;
    private Button cb;

    public CalendarFragment() {
        //Required Empty Constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflates the calendar fragment
    //With Assignment two the calendar will have add haircut reminder
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);
        calendarTxt =  v.findViewById(R.id.calendarText);
        //cv = v.findViewById(R.id.calendarView);
        cb = v.findViewById(R.id.calendarBtn);

        return v;


    }

    



}
