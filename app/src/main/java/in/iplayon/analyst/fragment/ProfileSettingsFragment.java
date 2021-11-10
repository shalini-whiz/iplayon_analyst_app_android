package in.iplayon.analyst.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import in.iplayon.analyst.R;

import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.CircleForm;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.DataStore;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.NetworkChangeReceiver;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;
import in.iplayon.analyst.util.Validation;

import static com.google.common.collect.FluentIterable.from;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSettingsFragment extends Fragment implements AsyncResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    LinearLayout defaultProfileLayout;
    ImageView userImg;
    TextView userName;
    TextView userMailAddress;


    Util mUtil;
    SessionManager mSession;




    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a create instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A create instance of fragment ProfileSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSettingsFragment newInstance(String param1, String param2) {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();
        //Bundle args = new Bundle();
       // args.putString("key1", param1);
        //args.putString("key2", param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments() != null) {
                String mParam1 = getArguments().getString("key1");
                String mParam2 = getArguments().getString("key2");

                System.out.println("arguments value : "+mParam1+" ... "+mParam2);
            }


        }




        mUtil = new Util(getActivity().getApplicationContext(),getActivity());
        mSession = new SessionManager(getActivity().getApplicationContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {

        }

        View view = inflater.inflate(R.layout.profile_settings, container, false);

        defaultProfileLayout = (LinearLayout) view.findViewById(R.id.defaultProfileLayout);
        userImg = (ImageView) view.findViewById(R.id.userImg);
        userName = (TextView) view.findViewById(R.id.userName);
        userMailAddress = (TextView) view.findViewById(R.id.userMailAddress);








        userName.setText(mSession.ypGetUserName());
        userMailAddress.setText(mSession.ypGetUserMail());
        Glide.with(this).load(R.drawable.user_icon)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleForm(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImg);

        userImg.setColorFilter(ContextCompat.getColor(getContext(), R.color.iconColor), android.graphics.PorterDuff.Mode.MULTIPLY);

        return view;
    }




    public void toastMessage(String message)
    {
        mUtil.toastMessage( message,getContext());
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(String result, String methodName) {
        if(result != null)
        {

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}



