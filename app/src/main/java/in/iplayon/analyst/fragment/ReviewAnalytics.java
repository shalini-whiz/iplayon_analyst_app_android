package in.iplayon.analyst.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import in.iplayon.analyst.R;
import in.iplayon.analyst.activity.SequenceActivity;
import in.iplayon.analyst.adapter.ShareHistoryAdapter;
import in.iplayon.analyst.adapter.ViewStrokeShotAdapter;
import in.iplayon.analyst.modal.PlayerJson;
import in.iplayon.analyst.modal.SequenceDetail;
import in.iplayon.analyst.modal.ShareHistoryPlayerJson;
import in.iplayon.analyst.modal.SharedHistoryJson;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.CircleForm;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;

public class ReviewAnalytics extends Fragment implements AsyncResponse {
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


    Spinner dateFilterView;
    ListView mStrokesListView;
    Spinner chooseReviewType;
    AutoCompleteTextView shareFilter;
    ImageView viewSharedProfile;

    List<ShareHistoryPlayerJson> registeredPlayers1;

    String requestType = "";
    String queryparams = "";
    String params = "";
    String methodType = "";
    List<PlayerJson> registeredPlayers;

    ArrayAdapter<PlayerJson> player1ViewAdapter;
    ArrayAdapter<PlayerJson> player2ViewAdapter;
    List<PlayerJson> player1JsonAnalyzeList;
    List<PlayerJson> player2JsonAnalyzeList;
    Spinner player1ViewFilter;
    Spinner player2ViewFilter;
    Spinner timeFilter;
    LinearLayout reviewPlayerFilters;

    LinearLayout shareUserLayout;


    ExecuteURLTask asyncTask;

    JSONObject getPlayerSetDataJSON;
    AlertDialog mAlertDialog;

    LinearLayout shareHistoryHeaderLayout;

    ArrayAdapter<PlayerJson> player1AnalyzeAdapter;

    ArrayAdapter<PlayerJson> player2AnalyzeAdapter;

     ImageView uparrow ;
     ImageView downarrow;

    Map<String, String> serviceMap = new HashMap<>();
    ArrayList<String> serviceArrayList = new ArrayList<String>();
    Map<String, String> strokeMap = new HashMap<>();
    ArrayList<String> strokeArrayList = new ArrayList<String>();
    Map<String, String> destinationMap = new HashMap<>();
    ArrayList<String> destinationArrayList = new ArrayList<String>();

    Map<String,String> serviceKeyMap = new HashMap<>();
    Map<String,String> strokeKeyMap = new HashMap<>();
    Map<String,String> destinationKeyMap = new HashMap<>();
    Map<String,String> masterDestinationKeyMap = new HashMap<>();
    Map<String,String> masterDestinationMap = new HashMap<>();
    ArrayList<String> masterDestinationArrayList = new ArrayList<String>();

    JSONArray p6DestinationKeysJsonArr;
    JSONArray p8DestinationKeysJsonArr;
    JSONArray p9DestinationKeysJsonArr;
    JSONArray p14DestinationKeysJsonArr;


    public ReviewAnalytics() {
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
        Bundle args = new Bundle();
        args.putString("report", param1);
        args.putString("response", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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


        View view = inflater.inflate(R.layout.activity_review_analytics, container, false);



        registeredPlayers1 = new ArrayList<ShareHistoryPlayerJson>();
        player1JsonAnalyzeList = new ArrayList<PlayerJson>();

        player2JsonAnalyzeList = new ArrayList<PlayerJson>();
        registeredPlayers = new ArrayList<PlayerJson>();
        registeredPlayers1 = new ArrayList<ShareHistoryPlayerJson>();


        player1ViewFilter = (Spinner) view.findViewById(R.id.player1ViewFilter);
        player2ViewFilter = (Spinner) view.findViewById(R.id.player2ViewFilter);
        dateFilterView = (Spinner) view.findViewById(R.id.timeFilter);
        mStrokesListView = (ListView) view.findViewById(R.id.viewPlayerSequences);
        chooseReviewType = (Spinner) view.findViewById(R.id.chooseReviewType);
        shareFilter = (AutoCompleteTextView) view.findViewById(R.id.shareFilter);
        viewSharedProfile = (ImageView) view.findViewById(R.id.viewSharedProfile);
        final Button shareSubmit = (Button) view.findViewById(R.id.shareSubmit);
        reviewPlayerFilters = (LinearLayout) view.findViewById(R.id.reviewPlayerFilters);
        timeFilter = (Spinner) view.findViewById(R.id.timeFilter);
        shareHistoryHeaderLayout = (LinearLayout) view.findViewById(R.id.shareHistoryHeaderLayout);


        p6DestinationKeysJsonArr = new JSONArray();
        p8DestinationKeysJsonArr = new JSONArray();
        p9DestinationKeysJsonArr = new JSONArray();
        p14DestinationKeysJsonArr = new JSONArray();


        uparrow = (ImageView) view.findViewById(R.id.reviewuparrow);
        downarrow = (ImageView) view.findViewById(R.id.reviewdownarrow);


        player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player1JsonAnalyzeList);

        player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player2JsonAnalyzeList);

        shareUserLayout = (LinearLayout) view.findViewById(R.id.shareUserLayout);

        List<String> reviewList = Arrays.asList(getResources().getStringArray(R.array.seqReviewList));
        ArrayAdapter<String> reviewTypeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.header_spinner,reviewList);
        chooseReviewType.setAdapter(reviewTypeAdapter);


        registeredPlayers1.add(0,new ShareHistoryPlayerJson("None", ""));
        registeredPlayers = new ArrayList<PlayerJson>();


        final ArrayAdapter<PlayerJson> registeredPlayersAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.footer_spinner,registeredPlayers);
        shareFilter.setAdapter(registeredPlayersAdapter);




        if(mUtil.isOnline())
        {
            //progressBar.setVisibility(View.VISIBLE);

            requestType="GET";
            queryparams="";
            methodType="getPlayerSetData";
            params= "getPlayerSetData?caller="+ Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();
            ExecuteURLTask asyncTask1 = new ExecuteURLTask(getContext(),methodType);
            asyncTask1.delegate = ReviewAnalytics.this;
            asyncTask1.execute(params,queryparams,requestType);

        }
        else
        {
            toastMessage("No Internet");
        }














            player1ViewAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                    R.layout.header_spinner, player1JsonAnalyzeList);
            player2ViewAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                    R.layout.header_spinner, player2JsonAnalyzeList);
            player1ViewFilter.setAdapter(player1ViewAdapter);
            player2ViewFilter.setAdapter(player2ViewAdapter);


        player1ViewFilter.getBackground().clearColorFilter();
        player2ViewFilter.getBackground().clearColorFilter();

        List<String> dateViewFilter = new ArrayList<String>();
        dateViewFilter.add("Any time");
        dateViewFilter.add("Last Day");
        dateViewFilter.add("Last Week");
        dateViewFilter.add("Last Month");
        dateViewFilter.add("Last 2 months");
        dateViewFilter.add("Last Year");

        ArrayAdapter<String> dateFilterViewAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.footer_spinner, dateViewFilter);
        dateFilterView.setAdapter(dateFilterViewAdapter);


        chooseReviewType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                if(chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("Share Sequence"))
                {
                    shareFilter.setVisibility(View.VISIBLE);
                    shareSubmit.setVisibility(View.VISIBLE);
                    shareUserLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    shareFilter.setVisibility(View.GONE);
                    shareSubmit.setVisibility(View.GONE);
                    shareUserLayout.setVisibility(View.GONE);
                }
                if(chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("Share History"))
                {
                    reviewPlayerFilters.setVisibility(View.GONE);
                    timeFilter.setVisibility(View.GONE);

                }
                else
                {
                    reviewPlayerFilters.setVisibility(View.VISIBLE);
                    timeFilter.setVisibility(View.VISIBLE);
                }

                if(getPlayerSetDataJSON != null && player1ViewFilter.getSelectedItem() != null && player2ViewFilter.getSelectedItem() != null)
                {

                    String player1Name = player1ViewFilter.getSelectedItem().toString();
                    String player2Name = player2ViewFilter.getSelectedItem().toString();
                    String dateFilterValue = dateFilterView.getSelectedItem().toString();
                    String player1ID = "";
                    String player2ID = "";
                    if(player1ViewFilter.getTag() != null)
                        player1ID = player1ViewFilter.getTag().toString();
                    else
                        player1ID = player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId();
                    if(player2ViewFilter.getTag() != null)
                        player2ID = player2ViewFilter.getTag().toString();
                    mStrokesListView.setAdapter(null);
                    invokeSequenceReview(player1Name,player2Name,dateFilterValue,player1ID,player2ID,"");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        player1ViewFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {



                if(getPlayerSetDataJSON != null && player1ViewFilter.getSelectedItem() != null && player2ViewFilter.getSelectedItem() != null)
                {
                    String selectedItem = player1ViewFilter.getSelectedItem().toString();
                    player1ViewFilter.setTag(player1ViewAdapter.getItem(pos).getUserId());
                    String player1Name = player1ViewFilter.getSelectedItem().toString();
                    String player2Name = "All";
                    String dateFilterValue = dateFilterView.getSelectedItem().toString();
                    String player1ID = player1ViewFilter.getTag().toString();
                    String player2ID = "";
                    mStrokesListView.setAdapter(null);

                    invokeSequenceReview(player1Name,player2Name,dateFilterValue,player1ID,player2ID,"getPlayerList");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        player2ViewFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                if(getPlayerSetDataJSON != null && player1ViewFilter.getSelectedItem() != null && player2ViewFilter.getSelectedItem() != null)
                {
                    player2ViewFilter.setTag(player2ViewAdapter.getItem(player2ViewFilter.getSelectedItemPosition()).getUserId());
                    String player1Name = player1ViewFilter.getSelectedItem().toString();
                    String player2Name = player2ViewFilter.getSelectedItem().toString();
                    String dateFilterValue = dateFilterView.getSelectedItem().toString();
                    String player1ID= "";
                    if(player1ViewFilter.getTag() != null)
                        player1ID = player1ViewFilter.getTag().toString();
                    else
                        player1ID = player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId();

                    String player2ID = player2ViewFilter.getTag().toString();
                    mStrokesListView.setAdapter(null);

                    invokeSequenceReview(player1Name,player2Name,dateFilterValue,player1ID,player2ID,"");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        shareFilter.setAdapter(null);
        registeredPlayers.add(0,new PlayerJson("None", ""));
        final ArrayAdapter<PlayerJson> sharedToAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.footer_spinner,registeredPlayers);
        shareFilter.setAdapter(sharedToAdapter);



        viewSharedProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mAlertDialog != null && mAlertDialog.isShowing() ) mAlertDialog.dismiss();

                String params= "viewUserProfile?";
                String queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+shareFilter.getTag().toString();
                String requestType = "POST";
                ExecuteURLTask asyncTask = new ExecuteURLTask(getContext(),"viewUserProfile");
                //asyncTask.delegate = (AsyncResponse) mActivity;

                try
                {
                    String result = asyncTask.execute(params,queryparams,requestType).get();
                    if(result != null)
                    {
                        JSONParser parser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) parser.parse(result);
                        if(jsonObject.containsKey("status"))
                        {
                            if(jsonObject.get("status").toString().equalsIgnoreCase("success"))
                            {
                                if(jsonObject.containsKey("response"))
                                {
                                    JSONObject playerObject;
                                    try {
                                        playerObject = (JSONObject) parser.parse(jsonObject.get("response").toString());
                                        if(playerObject != null)
                                        {
                                            LayoutInflater inflater = getLayoutInflater();
                                            View profileSettingsLayout = inflater.inflate(R.layout.player_complete_profile, null);

                                            ImageView closeDialog = (ImageView) profileSettingsLayout.findViewById(R.id.closeDialog);

                                            Glide.with(getActivity()).load(R.drawable.close_red)
                                                    .crossFade()
                                                    .thumbnail(0.5f)
                                                    .bitmapTransform(new CircleForm(getContext()))
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .into(closeDialog);


                                            AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                            profileDialog.setView(profileSettingsLayout);

                                            profileDialog.setCancelable(false);
                                            //profileDialog.setPositiveButton("OK", null);

                                            mAlertDialog = profileDialog.create();
                                            TextView playerName = (TextView) profileSettingsLayout.findViewById(R.id.playerName);
                                            TextView playerGender = (TextView) profileSettingsLayout.findViewById(R.id.playerGender);
                                            TextView playerCity = (TextView) profileSettingsLayout.findViewById(R.id.playerCity);
                                            TextView playerState = (TextView) profileSettingsLayout.findViewById(R.id.playerState);
                                            TextView interestedSport = (TextView) profileSettingsLayout.findViewById(R.id.interestedSport);

                                            if(playerObject.containsKey("userName"))
                                                playerName.setText(playerObject.get("userName").toString());

                                            if(playerObject.containsKey("gender"))
                                                playerGender.setText(playerObject.get("gender").toString());

                                            if(playerObject.containsKey("city"))
                                                playerCity.setText(playerObject.get("city").toString());

                                            if(playerObject.containsKey("domainName"))
                                                playerState.setText(playerObject.get("domainName").toString());

                                            if(playerObject.containsKey("interestedSport"))
                                            {
                                                String xxx = playerObject.get("interestedSport").toString().replace("[","").replace("]","").replaceAll("\"","");;
                                                interestedSport.setText(xxx);

                                            }

                                            mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                                                @Override
                                                public void onShow(DialogInterface dialog) {

                                                    Button posBtn = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                                                    posBtn.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View view) {

                                                            if(mAlertDialog != null)
                                                                mAlertDialog.dismiss();
                                                        }
                                                    });


                                                }
                                            });

                                            closeDialog.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(mAlertDialog != null)
                                                        mAlertDialog.dismiss();
                                                }
                                            });
                                            mAlertDialog.show();

                                            mAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        }

                                    } catch (org.json.simple.parser.ParseException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }


                                }

                            }

                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        });
        shareFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                shareFilter.setTag(sharedToAdapter.getItem(pos).getUserId());
                viewSharedProfile.setVisibility(View.VISIBLE);
            }
        });

        shareFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int pos = registeredPlayers.indexOf(new PlayerJson(s.toString()));
                if(pos > -1)
                {
                    shareFilter.setTag(registeredPlayers.get(pos).getUserId());
                    viewSharedProfile.setVisibility(View.VISIBLE);
                }
                else
                {
                    shareFilter.setTag("");
                    viewSharedProfile.setVisibility(View.GONE);
                }
            }
        });

        dateFilterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(getPlayerSetDataJSON != null && player1ViewFilter.getSelectedItem() != null && player2ViewFilter.getSelectedItem() != null)
                {
                    String player1Name = player1ViewFilter.getSelectedItem().toString();
                    String player2Name = player2ViewFilter.getSelectedItem().toString();
                    String dateFilterValue = dateFilterView.getSelectedItem().toString();
                    String player1ID = "";
                    String player2ID = "";
                    if(player1ViewFilter.getTag() != null)
                        player1ID = player1ViewFilter.getTag().toString();
                    else
                        player1ID = player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId();
                    if(player2ViewFilter.getTag() != null)
                        player2ID = player2ViewFilter.getTag().toString();
                    mStrokesListView.setAdapter(null);
                    invokeSequenceReview(player1Name,player2Name,dateFilterValue,player1ID,player2ID,"");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        shareSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if(mStrokesListView.getAdapter().getCount() == 0)
                {
                    toastMessage("Empty sequences!! Cannot be shared");

                }

                else if(shareFilter.getText().toString().length() == 0)
                {
                    toastMessage("Please choose valid player from the list");

                }
                else if(shareFilter.getText().toString().equalsIgnoreCase("None"))
                {
                    toastMessage("Not shared to anyone!!");
                }

                else if(shareFilter.getTag() == null)
                {
                    toastMessage("Please choose valid player from the list");
                }
                else if(shareFilter.getTag() != null)
                {
                    int pos = registeredPlayers.indexOf(new PlayerJson(shareFilter.getText().toString()));
                    if(pos > -1)
                    {
                        AlertDialog.Builder shareDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                        shareDialog.setTitle("Share sequence ? ");
                        String sharedName = shareFilter.getText().toString();
                        String shareMessage = "Share is an irreversible operation. The sequences listed in the parent screen here will be shared to "+sharedName+". Confirm?";
                        shareDialog.setMessage(shareMessage);
                        shareDialog.setCancelable(false);
                        shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        shareDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                               // progressBar.setVisibility(View.VISIBLE);

                                String player1Name = player1ViewFilter.getSelectedItem().toString();
                                String player2Name = player2ViewFilter.getSelectedItem().toString();
                                String dateFilterValue = dateFilterView.getSelectedItem().toString();
                                String player1ID = "";
                                String player2ID = "";
                                String sharedId = shareFilter.getTag().toString();
                                if(player1ViewFilter.getTag() != null)
                                    player1ID = player1ViewFilter.getTag().toString();
                                else
                                    player1ID = player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId();
                                if(player2ViewFilter.getTag() != null)
                                    player2ID = player2ViewFilter.getTag().toString();

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("loggerId", mSession.ypGetUserID());
                                jsonObject.put("sharedId", sharedId);
                                jsonObject.put("player1Name", player1Name);
                                jsonObject.put("player2Name", player2Name);
                                jsonObject.put("player1ID", player1ID);
                                jsonObject.put("player2ID", player2ID);

                                requestType = "POST";
                                methodType = "shareSequence";
                                params = "shareSequence?";
                                queryparams = "caller=" + Constants.caller + "&apiKey="
                                        + Constants.apiKey + "&data="
                                        +jsonObject ;

                                asyncTask = new ExecuteURLTask(getContext(),methodType);
                                asyncTask.delegate = ReviewAnalytics.this;
                                asyncTask.execute(params,queryparams,requestType);
                            }

                        });
                        shareDialog.show();
                    }
                    else
                    {
                        toastMessage("Please choose valid player from the list");

                    }
                }
                else
                {
                    AlertDialog.Builder shareDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                    shareDialog.setTitle("Share sequence ? ");
                    String sharedName = shareFilter.getText().toString();
                    String shareMessage = "Share is an irreversible operation. The sequences listed in the parent screen here will be shared to "+sharedName+". Confirm?";
                    shareDialog.setMessage(shareMessage);
                    shareDialog.setCancelable(false);
                    shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    shareDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            String player1Name = player1ViewFilter.getSelectedItem().toString();
                            String player2Name = player2ViewFilter.getSelectedItem().toString();
                            String dateFilterValue = dateFilterView.getSelectedItem().toString();
                            String player1ID = "";
                            String player2ID = "";
                            String sharedId = shareFilter.getTag().toString();
                            if(player1ViewFilter.getTag() != null)
                                player1ID = player1ViewFilter.getTag().toString();
                            else
                                player1ID = player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId();
                            if(player2ViewFilter.getTag() != null)
                                player2ID = player2ViewFilter.getTag().toString();

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("loggerId", mSession.ypGetUserID());
                            jsonObject.put("sharedId", sharedId);
                            jsonObject.put("player1Name", player1Name);
                            jsonObject.put("player2Name", player2Name);
                            jsonObject.put("player1ID", player1ID);
                            jsonObject.put("player2ID", player2ID);

                            requestType = "POST";
                            methodType = "shareSequence";
                            params = "shareSequence?";
                            queryparams = "caller=" + Constants.caller + "&apiKey="
                                    + Constants.apiKey + "&data="
                                    +jsonObject ;

                            asyncTask = new ExecuteURLTask(getContext(),methodType);
                            asyncTask.delegate = ReviewAnalytics.this;
                            asyncTask.execute(params,queryparams,requestType);
                        }

                    });
                    shareDialog.show();




                }

            }
        });



        return view;
    }

    public void invokeSequenceReview(String player1Name,String player2Name,String dateFilterValue,String player1ID,String player2ID,String playerList)
    {
        if(chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("View Sequence") || chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("Share Sequence"))
        {
            shareHistoryHeaderLayout.setVisibility(View.GONE);
            if(mUtil.isOnline())
            {
                requestType = "GET";
                methodType = "viewSequence";
                params = "viewSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&player1ID="+player1ID+"&player2ID="+player2ID;

                System.out.println("view sequence params : "+params);
                asyncTask = new ExecuteURLTask(getContext(),methodType);
                asyncTask.delegate = ReviewAnalytics.this;
                asyncTask.execute(params,queryparams,requestType);

                if(playerList.equalsIgnoreCase("getPlayerList"))
                {
                    if(mUtil.isOnline())
                    {
                        requestType = "GET";
                        methodType = "getVsPlayerList";
                        params = "getVsPlayerList?caller=" + Constants.caller + "&apiKey="
                                + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&loggerSeqId="
                                +player1ID ;

                        asyncTask = new ExecuteURLTask(getContext(),methodType);
                        asyncTask.delegate = ReviewAnalytics.this;
                        asyncTask.execute(params,queryparams,requestType);
                    }
                    else
                    {
                        toastMessage("No Internet");
                    }
                }
            }
            else
            {
                toastMessage("No Internet");
            }
        }

        else if(chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("Share History"))
        {
            shareHistoryHeaderLayout.setVisibility(View.VISIBLE);
            if(mUtil.isOnline())
            {
                requestType = "GET";
                methodType = "shareHistory";
                params = "getShareHistory?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID();

                asyncTask = new ExecuteURLTask(getContext(),methodType);
                asyncTask.delegate = ReviewAnalytics.this;
                asyncTask.execute(params,queryparams,requestType);

            }
            else
            {
                toastMessage("No Internet");
            }
        }


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

        System.out.println("result .. "+result);
        if(result != null)
        {
            if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getPlayerSetData"))
            {
                JSONParser jsonParser = new JSONParser();
                Object obj;
                try {
                    obj = jsonParser.parse(result);
                    getPlayerSetDataJSON = (JSONObject) obj;

                    player1JsonAnalyzeList = new ArrayList<PlayerJson>();
                    player2JsonAnalyzeList = new ArrayList<PlayerJson>();
                    Gson gson = new Gson();
                    Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();

                    if(getPlayerSetDataJSON.get("player1Set") != null && getPlayerSetDataJSON.get("player1Set").toString().length() > 0)
                    {

                        player1JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON.get("player1Set").toString(), listType1);
                        player1ViewAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player1JsonAnalyzeList);
                        player1ViewFilter.setAdapter(player1ViewAdapter);
                        player1ViewAdapter.notifyDataSetChanged();


                    }
                    if(getPlayerSetDataJSON.containsKey("player2Set") && getPlayerSetDataJSON.get("player2Set").toString().length() > 0)
                    {
                        player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON.get("player2Set").toString(), listType1);
                        player2ViewAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player2JsonAnalyzeList);
                        for(int j=0;j<=player2JsonAnalyzeList.size();j++)
                        {
                            //player2JsonAnalyzeList.get(j).getPlayerName());
                        }
                        player2ViewFilter.setAdapter(player2ViewAdapter);
                        player2ViewAdapter.notifyDataSetChanged();


                    }

                    if(getPlayerSetDataJSON.containsKey("matchStrokes"))
                    {

                        JSONObject matchStrokes = (JSONObject) getPlayerSetDataJSON.get("matchStrokes");

                        if(matchStrokes.get("registeredPlayers") != null)
                        {
                            registeredPlayers = gson.fromJson(matchStrokes.get("registeredPlayers").toString(), listType1);

                            Type listType2 = new TypeToken<List<ShareHistoryPlayerJson>>(){}.getType();
                            registeredPlayers1  = gson.fromJson(matchStrokes.get("registeredPlayers").toString(), listType2);

                        }

                        if(matchStrokes.containsKey("serviceKeysJson"))
                        {
                            JSONArray serviceKeysJsonArr = (JSONArray) matchStrokes.get("serviceKeysJson");
                            if (serviceKeysJsonArr.size() > 0) {
                                for (int i = 0; i < serviceKeysJsonArr.size(); i++) {
                                    JSONObject obj1 = (JSONObject) serviceKeysJsonArr.get(i);
                                    String serviceName = obj1.get("serviceName").toString();
                                    String serviceCode = obj1.get("serviceShortCode").toString();
                                    serviceMap.put(serviceName, serviceCode);
                                    serviceKeyMap.put(serviceCode, serviceName);
                                    serviceArrayList.add(serviceName);
                                }
                            }

                        }

                        if(matchStrokes.containsKey("shortKeysJson"))
                        {
                            JSONArray strokeKeysJsonArr = (JSONArray) matchStrokes.get("shortKeysJson");
                            if (strokeKeysJsonArr.size() > 0)
                            {
                                for (int i = 0; i < strokeKeysJsonArr.size(); i++) {
                                    JSONObject obj1 = (JSONObject) strokeKeysJsonArr.get(i);
                                    String strokeName = obj1.get("strokeName").toString();
                                    String strokeCode = obj1.get("strokeShortCode").toString();
                                    strokeMap.put(strokeName, strokeCode);
                                    strokeArrayList.add(strokeName);
                                    strokeKeyMap.put(strokeCode, strokeName);

                                }
                            }
                        }

                        if(matchStrokes.containsKey("shortKeysJson"))
                        {
                            JSONArray masterDestinationKeysJsonArr = (JSONArray) matchStrokes.get("destinationKeysJson");
                            if (masterDestinationKeysJsonArr.size() > 0) {
                                for (int i = 0; i < masterDestinationKeysJsonArr.size(); i++) {
                                    JSONObject obj1 = (JSONObject) masterDestinationKeysJsonArr.get(i);
                                    String destinationName = obj1.get("destinationName").toString();
                                    String destinationCode = obj1.get("destinationShortCode").toString();
                                    masterDestinationMap.put(destinationName, destinationCode);
                                    masterDestinationKeyMap.put(destinationCode,destinationName);
                                    masterDestinationArrayList.add(destinationName);
                                }
                            }
                        }



                        if(matchStrokes.containsKey("p6destinationKeysJson"))
                            p6DestinationKeysJsonArr = (JSONArray) matchStrokes.get("p6destinationKeysJson");
                        if(matchStrokes.containsKey("p8destinationKeysJson"))
                            p8DestinationKeysJsonArr = (JSONArray) matchStrokes.get("p8destinationKeysJson");
                        if(matchStrokes.containsKey("p9destinationKeysJson"))
                            p9DestinationKeysJsonArr = (JSONArray) matchStrokes.get("p9destinationKeysJson");
                        if(matchStrokes.containsKey("p14destinationKeysJson"))
                            p14DestinationKeysJsonArr = (JSONArray) matchStrokes.get("p14destinationKeysJson");

                        if(mSession.ypGetDestinationPoint() != null)
                        {
                            if(mSession.ypGetDestinationPoint().equalsIgnoreCase("6 Point"))
                            {
                                if (p6DestinationKeysJsonArr.size() > 0) {
                                    for (int i = 0; i < p6DestinationKeysJsonArr.size(); i++) {
                                        JSONObject obj1 = (JSONObject) p6DestinationKeysJsonArr.get(i);
                                        String destinationName = obj1.get("destinationName").toString();
                                        String destinationCode = obj1.get("destinationShortCode").toString();
                                        destinationMap.put(destinationName, destinationCode);
                                        destinationKeyMap.put(destinationCode,destinationName);
                                        destinationArrayList.add(destinationName);
                                    }
                                }
                            }
                            else if(mSession.ypGetDestinationPoint().equalsIgnoreCase("8 Point"))
                            {
                                if (p8DestinationKeysJsonArr.size() > 0) {
                                    for (int i = 0; i < p8DestinationKeysJsonArr.size(); i++) {
                                        JSONObject obj1 = (JSONObject) p8DestinationKeysJsonArr.get(i);
                                        String destinationName = obj1.get("destinationName").toString();
                                        String destinationCode = obj1.get("destinationShortCode").toString();
                                        destinationMap.put(destinationName, destinationCode);
                                        destinationKeyMap.put(destinationCode,destinationName);
                                        destinationArrayList.add(destinationName);
                                    }
                                }
                            }
                            else if(mSession.ypGetDestinationPoint().equalsIgnoreCase("9 Point"))
                            {
                                if (p9DestinationKeysJsonArr.size() > 0) {
                                    for (int i = 0; i < p9DestinationKeysJsonArr.size(); i++) {
                                        JSONObject obj1 = (JSONObject) p9DestinationKeysJsonArr.get(i);
                                        String destinationName = obj1.get("destinationName").toString();
                                        String destinationCode = obj1.get("destinationShortCode").toString();
                                        destinationMap.put(destinationName, destinationCode);
                                        destinationKeyMap.put(destinationCode,destinationName);
                                        destinationArrayList.add(destinationName);
                                    }
                                }
                            }
                            else if(mSession.ypGetDestinationPoint().equalsIgnoreCase("14 Point"))
                            {
                                if (p14DestinationKeysJsonArr.size() > 0) {
                                    for (int i = 0; i < p14DestinationKeysJsonArr.size(); i++) {
                                        JSONObject obj1 = (JSONObject) p14DestinationKeysJsonArr.get(i);
                                        String destinationName = obj1.get("destinationName").toString();
                                        String destinationCode = obj1.get("destinationShortCode").toString();
                                        destinationMap.put(destinationName, destinationCode);
                                        destinationKeyMap.put(destinationCode,destinationName);
                                        destinationArrayList.add(destinationName);
                                    }
                                }
                            }

                        }
                        else
                        {
                            if (p6DestinationKeysJsonArr.size() > 0) {
                                for (int i = 0; i < p6DestinationKeysJsonArr.size(); i++) {
                                    JSONObject obj1 = (JSONObject) p6DestinationKeysJsonArr.get(i);
                                    String destinationName = obj1.get("destinationName").toString();
                                    String destinationCode = obj1.get("destinationShortCode").toString();
                                    destinationMap.put(destinationName, destinationCode);
                                    destinationKeyMap.put(destinationCode,destinationName);
                                    destinationArrayList.add(destinationName);
                                }
                            }
                        }

                    }


                    System.out.println("json list .. "+player1JsonAnalyzeList.size());
                    String player1Name = player1JsonAnalyzeList.get(0).toString();
                    String player1ID = player1ViewAdapter.getItem(0).getUserId();
                    String player2Name = "All";
                    String player2ID = "";

                    String dateFilterValue = "Any time";
                    requestType = "GET";
                    methodType = "viewSequence";
                    params = "viewSequence?caller=" + Constants.caller + "&apiKey="
                            + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                            + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&player1ID="+player1ID+"&player2ID="+player2ID;
                    asyncTask = new ExecuteURLTask(getContext(),methodType);
                    asyncTask.delegate = ReviewAnalytics.this;
                    asyncTask.execute(params,queryparams,requestType);



                } catch (ParseException e) {
                    e.printStackTrace();
                }





            }

            else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("shareHistory"))
            {
                System.out.println("shareHistory data .. "+result);
                JSONParser jsonParser = new JSONParser();
                Object obj;
                try {
                    obj = jsonParser.parse(result);
                    JSONArray arrayObj = (JSONArray) obj;
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<SharedHistoryJson>>(){}.getType();
                    ArrayList<SharedHistoryJson> sharedList = gson.fromJson(result, listType);
                    ShareHistoryAdapter shareHistoryAdapter = new ShareHistoryAdapter(getContext(), getActivity(), arrayObj,registeredPlayers1,sharedList);
                    mStrokesListView.setAdapter(shareHistoryAdapter);




                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getVsPlayerList"))
            {
                JSONParser jsonParser = new JSONParser();
                Object obj;
                try {
                    obj = jsonParser.parse(result);
                    JSONObject response = (JSONObject) obj;

                    player1JsonAnalyzeList = new ArrayList<PlayerJson>();
                    player2JsonAnalyzeList = new ArrayList<PlayerJson>();
                    Gson gson = new Gson();
                    Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();

                    if(response.containsKey("player2Set"))
                    {
                        player2JsonAnalyzeList = gson.fromJson(response.get("player2Set").toString(), listType1);

                        player2ViewAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player2JsonAnalyzeList);
                        player2ViewFilter.setAdapter(player2ViewAdapter);
                        player2ViewAdapter.notifyDataSetChanged();



                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("viewSequence"))
            {

                ArrayList<SequenceDetail> viewSequence = new ArrayList<SequenceDetail>();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<SequenceDetail>>() {}.getType();

                viewSequence = gson.fromJson(result, listType);
                final ViewSequenceAdapter sequenceAdapter = new ViewSequenceAdapter(getContext(), viewSequence);

                mStrokesListView.setAdapter(sequenceAdapter);


                mStrokesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    private int mLastFirstVisibleItem;
                    private int mLastVisibleItem;

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {

                        mLastVisibleItem = firstVisibleItem + visibleItemCount;

                        if(mLastFirstVisibleItem<firstVisibleItem)
                        {
                            uparrow.setVisibility(View.VISIBLE);
                            downarrow.setVisibility(View.GONE);

                        }
                        if(mLastFirstVisibleItem>firstVisibleItem)
                        {
                            downarrow.setVisibility(View.VISIBLE);
                            uparrow.setVisibility(View.GONE);

                        }
                        mLastFirstVisibleItem=firstVisibleItem;

                    }
                });
                uparrow.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        mStrokesListView.setSelection(0);
                    }
                });

                downarrow.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        mStrokesListView.setSelection(sequenceAdapter.getCount() - 1);

                    }
                });




            }


            else if(requestType.equalsIgnoreCase("POST") && methodName.equalsIgnoreCase("deleteSequence"))
            {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = null;
                try {
                    jsonObj = (JSONObject) jsonParser.parse(result);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(jsonObj.containsKey("status"))
                {
                    if(jsonObj.get("status").toString().equalsIgnoreCase("success"))
                    {
                        if(jsonObj.containsKey("message"))
                            toastMessage("Sequence deleted !!");
                        if(jsonObj.containsKey("data"))
                        {
                            ArrayList<SequenceDetail> viewSequence = new ArrayList<SequenceDetail>();
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<SequenceDetail>>() {}.getType();

                            viewSequence = gson.fromJson(jsonObj.get("data").toString(), listType);
                            final ViewSequenceAdapter sequenceAdapter = new ViewSequenceAdapter(getContext(), viewSequence);

                            mStrokesListView.setAdapter(sequenceAdapter);

                            mStrokesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                private int mLastFirstVisibleItem;
                                private int mLastVisibleItem;

                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem,
                                                     int visibleItemCount, int totalItemCount) {

                                    mLastVisibleItem = firstVisibleItem + visibleItemCount;

                                    if(mLastFirstVisibleItem<firstVisibleItem)
                                    {
                                        uparrow.setVisibility(View.VISIBLE);
                                        downarrow.setVisibility(View.GONE);

                                    }
                                    if(mLastFirstVisibleItem>firstVisibleItem)
                                    {
                                        downarrow.setVisibility(View.VISIBLE);
                                        uparrow.setVisibility(View.GONE);

                                    }
                                    mLastFirstVisibleItem=firstVisibleItem;

                                }
                            });
                            uparrow.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    mStrokesListView.setSelection(0);
                                }
                            });

                            downarrow.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    // TODO Auto-generated method stub
                                    mStrokesListView.setSelection(sequenceAdapter.getCount() - 1);

                                }
                            });

                        }

                    }
                    else if(jsonObj.get("status").toString().equalsIgnoreCase("failure"))
                        if(jsonObj.containsKey("message"))
                            toastMessage(jsonObj.get("message").toString());
                }
                else
                    toastMessage("Could not delete sequence!!");

            }

            if(requestType.equalsIgnoreCase("POST") && methodName.equalsIgnoreCase("shareSequence"))
            {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = null;
                try {
                    jsonObj = (JSONObject) jsonParser.parse(result);
                    if(jsonObj.get("message").toString().equalsIgnoreCase("sequence shared"))
                    {
                        mStrokesListView.setAdapter(null);
                        toastMessage("Sequence shared !!");
                    }
                    else
                    {
                        toastMessage("Could not share sequence");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    toastMessage("Could not share sequence");

                }

            }


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


    public class ViewSequenceAdapter extends BaseAdapter {
        private Context mContext;
        private List<SequenceDetail> mSequenceList;

        public ViewSequenceAdapter(Context lContext, ArrayList<SequenceDetail> lMessageList) {
            this.mContext = lContext;
            this.mSequenceList = lMessageList;
        }

        @Override
        public int getCount() {
            return mSequenceList.size();
        }

        @Override
        public Object getItem(int lPosition) {
            return mSequenceList.get(lPosition);
        }

        @Override
        public long getItemId(int lPosition) {
            return lPosition;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView( int lPosition, View lConvertView, ViewGroup lParent) {


            LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            lConvertView = lInflater.inflate(R.layout.sequence_list,null);
            final SequenceDetail lMessageObj = mSequenceList.get(lPosition);

            LinearLayout customSequenceLayout = (LinearLayout) lConvertView.findViewById(R.id.customSequenceLayout);
            LinearLayout sequenceLayout = (LinearLayout) lConvertView.findViewById(R.id.sequenceLayout);


            if(lPosition%2 == 0)
                customSequenceLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
            else
                customSequenceLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));



            TextView sequencePlayer = (TextView) lConvertView.findViewById(R.id.sequencePlayer);
            sequencePlayer.setText(lMessageObj.getPlayer1Name());


            TextView sequenceOpponent = (TextView) lConvertView.findViewById(R.id.sequenceOpponent);
            sequenceOpponent.setText(lMessageObj.getPlayer2Name());



            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

            try {
                if(lMessageObj.getSequenceRecordDate() != null)
                {
                    Date result1 = formatter.parse(lMessageObj.getSequenceRecordDate());
                    TextView sequenceDate = (TextView) lConvertView.findViewById(R.id.sequenceDate);
                    sequenceDate.setText(new SimpleDateFormat("ddMMMyy").format(result1));
                }


            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


            final ImageView sequenceDelete = (ImageView) lConvertView.findViewById(R.id.sequenceDelete);



            sequenceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String requestType = "POST";
                    String params= "fetchSequenceRecord?";
                    String methodType="fetchSequenceRecord";
                    String player1Name = lMessageObj.getPlayer1Name();
                    String player2Name = lMessageObj.getPlayer2Name();
                    String player1Id = lMessageObj.getPlayer1Id();
                    String player2Id = lMessageObj.getPlayer2Id();
                    JSONObject dataObject = new JSONObject();
                    //dataObject.put("player1Name",player1Name);
                    //dataObject.put("player2Name",player2Name);
                    dataObject.put("player1Id",player1Id);
                    dataObject.put("player2Id",player2Id);
                    dataObject.put("sequenceDate",lMessageObj.getSequenceRecordDate());
                    dataObject.put("selectedPlayer1Id",player1ViewAdapter.getItem(player1ViewFilter.getSelectedItemPosition()).getUserId());

                    JSONObject paramObject = new JSONObject();
                    paramObject.put("caller",Constants.caller);
                    paramObject.put("apiKey",Constants.apiKey);
                    paramObject.put("userId",mSession.ypGetUserID());
                    paramObject.put("data",dataObject);


                    queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"" +
                            "&userId="+mSession.ypGetUserID()+"&data="+dataObject;
                    asyncTask = new ExecuteURLTask(getContext(),getActivity(),true,methodType);
                    try {
                        String result = asyncTask.execute(params,paramObject.toString(),requestType).get();

                        System.out.println("clicked here result .. "+result);

                        if(result != null)
                        {
                            JSONParser jsonParser = new JSONParser();
                            try {
                                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                                if(jsonObject.containsKey("status"))
                                {
                                    if(jsonObject.get("status").toString().equalsIgnoreCase("success"))
                                    {
                                        if(jsonObject.containsKey("data"))
                                        {
                                            Gson gson = new Gson();
                                            Type listType = new TypeToken<SequenceDetail>(){}.getType();
                                            //groupJsonList = create ArrayList<GroupJson>();
                                            SequenceDetail sequenceDetail = gson.fromJson(jsonObject.get("data").toString(), listType);

                                            if(sequenceDetail.getPlayer1Id() != null && sequenceDetail.getPlayer2Id() != null)
                                            {
                                                LayoutInflater inflater = getLayoutInflater();
                                                View viewSequenceStrokeLayout = inflater.inflate(R.layout.view_each_sequence, null);

                                                AlertDialog.Builder sequenceViewDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                                TextView player1Value = (TextView) viewSequenceStrokeLayout.findViewById(R.id.player1Value);
                                                ImageView player1ServiceImg = (ImageView) viewSequenceStrokeLayout.findViewById(R.id.player1ServiceImg);
                                                TextView player2Value = (TextView) viewSequenceStrokeLayout.findViewById(R.id.player2Value);
                                                ImageView player2ServiceImg = (ImageView) viewSequenceStrokeLayout.findViewById(R.id.player2ServiceImg);
                                                ImageView player1Img = (ImageView) viewSequenceStrokeLayout.findViewById(R.id.player1Img);
                                                ImageView player2Img = (ImageView) viewSequenceStrokeLayout.findViewById(R.id.player2Img);
                                                TextView dialogOk = (TextView) viewSequenceStrokeLayout.findViewById(R.id.dialogOk);
                                                TextView player1Points = (TextView) viewSequenceStrokeLayout.findViewById(R.id.player1Points);
                                                TextView player2Points = (TextView) viewSequenceStrokeLayout.findViewById(R.id.player2Points);
                                                final ListView mListView = (ListView) viewSequenceStrokeLayout.findViewById(R.id.viewStrokes);

                                                player1Value.setText(sequenceDetail.getPlayer1Name());
                                                player2Value.setText(sequenceDetail.getPlayer2Name());


                                                if (sequenceDetail.getPlayer1Id().equalsIgnoreCase(sequenceDetail.getServiceBy())) {
                                                    player1ServiceImg.setVisibility(View.VISIBLE);
                                                    player2ServiceImg.setVisibility(View.INVISIBLE);

                                                } else {
                                                    player1ServiceImg.setVisibility(View.INVISIBLE);
                                                    player2ServiceImg.setVisibility(View.VISIBLE);
                                                }


                                                if (sequenceDetail.getPlayer1Id().equalsIgnoreCase(sequenceDetail.getWinner())) {
                                                    player1Img.setVisibility(View.VISIBLE);
                                                    player2Img.setVisibility(View.INVISIBLE);

                                                } else {
                                                    player1Img.setVisibility(View.INVISIBLE);
                                                    player2Img.setVisibility(View.VISIBLE);
                                                }


                                                player1Points.setText(sequenceDetail.getPlayerA().getSet() + "/" + sequenceDetail.getPlayerA().getPoints());
                                                player2Points.setText(sequenceDetail.getPlayerB().getSet() + "/" + sequenceDetail.getPlayerB().getPoints());

                                                ViewStrokeShotAdapter shotAdapter = new ViewStrokeShotAdapter(getContext(), sequenceDetail.getStrokesPlayed(), sequenceDetail, serviceKeyMap, strokeKeyMap, masterDestinationKeyMap);
                                                mListView.setAdapter(shotAdapter);


                                                sequenceViewDialog.setView(viewSequenceStrokeLayout);
                                                sequenceViewDialog.setCancelable(false);

                                                final AlertDialog eulaDialog = sequenceViewDialog.create();
                                                eulaDialog.show();
                                                WindowManager.LayoutParams lp = eulaDialog.getWindow().getAttributes();
                                                lp.dimAmount = 0.0F;
                                                eulaDialog.getWindow().setAttributes(lp);

                                                dialogOk.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        eulaDialog.cancel();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            });

            sequenceDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder deleteSequenceDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                    LayoutInflater inflater = getLayoutInflater();
                    View customLayout = inflater.inflate(R.layout.confirm_dialog, null);
                    TextView dialogTitle = (TextView) customLayout.findViewById(R.id.dialogTitle);
                    TextView dialogMessage = (TextView) customLayout.findViewById(R.id.dialogMessage);
                    dialogTitle.setText("Confirm");
                    dialogMessage.setText("Delete sequence ?");
                    deleteSequenceDialog.setView(customLayout);

                    //deleteSequenceDialog.setTitle("Delete sequence ? ");
                    deleteSequenceDialog.setCancelable(false);
                    deleteSequenceDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    deleteSequenceDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            requestType = "POST";
                            params= "deleteSequence?";
                            methodType="deleteSequence";
                            String player1Name = lMessageObj.getPlayer1Name();
                            String player2Name = lMessageObj.getPlayer2Name();
                            String player1Id = lMessageObj.getPlayer1Id();
                            String player2Id = lMessageObj.getPlayer2Id();



                            JSONObject viewSeqObj = new JSONObject();
                            viewSeqObj.put("player1Name",player1ViewFilter.getSelectedItem().toString());
                            viewSeqObj.put("player2Name",player2ViewFilter.getSelectedItem().toString());
                            viewSeqObj.put("dateFilterValue",dateFilterView.getSelectedItem().toString());
                            viewSeqObj.put("player1ID",player1ViewFilter.getTag().toString());
                            viewSeqObj.put("player2ID",player2ViewFilter.getTag().toString());




                            queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"" +
                                    "&sequenceDate="+lMessageObj.getSequenceRecordDate()+
                                    "&player1Name="+player1Name+"&player2Name="+player2Name+"&player1Id="+player1Id+"&player2Id="+player2Id+"&viewSeqParam="+viewSeqObj;
                            asyncTask = new ExecuteURLTask(getContext(),methodType);
                            asyncTask.delegate = ReviewAnalytics.this;
                            asyncTask.execute(params,queryparams,requestType);

                        }

                    });
                    deleteSequenceDialog.show();
                }
            });



            return lConvertView;
        }


    }

}



