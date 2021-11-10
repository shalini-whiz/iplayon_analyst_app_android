package in.iplayon.analyst.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.iplayon.analyst.R;
import in.iplayon.analyst.activity.SequenceActivity;
import in.iplayon.analyst.adapter.AnalyticsAdapter;
import in.iplayon.analyst.adapter.AnalyticsAdapter1;
import in.iplayon.analyst.adapter.ErrorAnalysisAdapter;
import in.iplayon.analyst.adapter.FourthBallAdapter;
import in.iplayon.analyst.adapter.RallyAdapter;
import in.iplayon.analyst.adapter.ReceiveAdapter;
import in.iplayon.analyst.adapter.ServiceFaultAdapter;
import in.iplayon.analyst.adapter.ServiceLossAdapter;
import in.iplayon.analyst.adapter.ServicePointsAdapter;
import in.iplayon.analyst.adapter.ServiceResponseAdapter;
import in.iplayon.analyst.adapter.StrokeAnalysisAdapter;
import in.iplayon.analyst.adapter.ThirdBallAttackAdapter;
import in.iplayon.analyst.modal.ErrorAnalyzer;
import in.iplayon.analyst.modal.PlayerJson;
import in.iplayon.analyst.modal.Rally;
import in.iplayon.analyst.modal.ShareHistoryPlayerJson;
import in.iplayon.analyst.modal.ShotInfo;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;

public class AnalyzeAnalytics extends Fragment implements AsyncResponse{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BlankFragment.OnFragmentInteractionListener mListener;
    private static final int REQUEST_WRITE_STORAGE = 112;


    ImageView downloadAnalytics;
    JSONObject getPlayerSetDataJSON_Analyze;


    List<PlayerJson> player1JsonAnalyzeList;
    ArrayAdapter<PlayerJson> player1AnalyzeAdapter;
    List<PlayerJson> player2JsonAnalyzeList;
    ArrayAdapter<PlayerJson> player2AnalyzeAdapter;
    List<PlayerJson> registeredPlayers;
    List<ShareHistoryPlayerJson> registeredPlayers1;

    Spinner player1AnalyzeFilter;
    Spinner player2AnalyzeFilter;
    Spinner chooseAnalyticsType;


    SessionManager mSession;
    Util mUtil;
    ExecuteURLTask asyncTask;

    String fetchParam = "";
    String requestType = "";
    String params = "";
    String queryparams = "";
    String methodType = "";

    List<String> player1AnalyzeSet = new ArrayList<String>();
    List<String> player2AnalyzeSet = new ArrayList<String>();
    ArrayAdapter<String> player1AnalyzeAdapterr;
    ArrayAdapter<String> player2AnalyzeAdapterr;
    Spinner timeAnalyzeFilter;
    Spinner sortAnalyzeFilter;


    LinearLayout rallyHeader;
    LinearLayout strokeAnalysisHeaderLayout;
    LinearLayout thirdBallAnalysisHeaderLayout;
    LinearLayout serviceHeaderLayout;
    LinearLayout receiveHeaderLayout;
    TextView  countTitle;


    TextView  handTitle;
    TextView destinationTitle;

    //errors and stroke analysis
    TextView shotFrom;
    TextView shotDestination;
    TextView shotPlayedCount;



    //receive points analysis
    TextView handTitle1;
    TextView destinationTitle1 ;
    TextView winShotTitle1;
    TextView countTitle1 ;

    //rally length sort
    TextView sequenceLen ;
    TextView shotsPlayed;
    TextView winCount;
    TextView lossCount;
    TextView efficiency;

    //third vall sort
    TextView thirdService;
    TextView thirdDest;
    TextView thirdEff;

    ArrayList<JSONObject> sortedArray = new ArrayList<JSONObject>();

    ListView mplayerAnalytics;

    LinearLayout fourthball_analysisLayout ;
    LinearLayout sortLayout;

    LinearLayout strokeAnalysisHeader;
    LinearLayout thirdstrokeAnalysisHeader ;
    ImageView uparrow ;
    ImageView downarrow ;



    //list of adapters
    AnalyticsAdapter shotInfoAdapter;
    AnalyticsAdapter1 shotInfoAdapter1;

    ServicePointsAdapter serviceInfoAdapter;
    ServiceLossAdapter serviceLossInfoAdapter;
    ReceiveAdapter receiveInfoAdapter ;
    RallyAdapter rallyAdapter;
    StrokeAnalysisAdapter strokeAnalysisAdapter;

    ErrorAnalysisAdapter errorAnalysisAdapter;
    ThirdBallAttackAdapter thirdBallAttackAdapter;
    FourthBallAdapter fourthBallAdapter;
    ServiceFaultAdapter serviceFaultAdapter;
    ServiceResponseAdapter serviceResponseAdapter;


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


    TextView offensive_defensive_count;
    TextView offensive_offensive_count;
    TextView defensive_offensive_count;
    TextView defensive_defensive_count;
    TextView offensive_lost_count;
    TextView defensive_lost_count;
    TextView offensive_win_count;
    TextView defensive_win_count;


    JSONArray p6DestinationKeysJsonArr;
    JSONArray p8DestinationKeysJsonArr;
    JSONArray p9DestinationKeysJsonArr;
    JSONArray p14DestinationKeysJsonArr;


    public AnalyzeAnalytics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mUtil = new Util(getActivity().getApplicationContext(),getActivity());
        mSession = new SessionManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_analyze_analytics, container, false);

        downloadAnalytics = (ImageView) view.findViewById(R.id.downloadAnalytics);


            System.out.println("mSession.ypGetUserID() .. "+mSession.ypGetUserID());
        player1JsonAnalyzeList = new ArrayList<PlayerJson>();
        player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player1JsonAnalyzeList);
        player2JsonAnalyzeList = new ArrayList<PlayerJson>();
        registeredPlayers = new ArrayList<PlayerJson>();
        registeredPlayers1 = new ArrayList<ShareHistoryPlayerJson>();
        player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player2JsonAnalyzeList);



        player1AnalyzeFilter = (Spinner) view.findViewById(R.id.player1AnalyzeFilter);
        player2AnalyzeFilter = (Spinner) view.findViewById(R.id.player2AnalyzeFilter);


        downloadAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                downloadAnalyticsData();

            }
        });

        if(mUtil.isOnline())
        {
            requestType="GET";
            queryparams="";
            methodType="getPlayerSetData_Analyze";
            params= "getPlayerSetData?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();
            asyncTask = new ExecuteURLTask(getContext(),methodType);
            asyncTask.delegate = AnalyzeAnalytics.this;
            asyncTask.execute(params,queryparams,requestType);

        }
        else
        {
            toastMessage("No Internet");
        }


        chooseAnalyticsType = (Spinner) view.findViewById(R.id.chooseAnalyticsType);


        List<String> myArrayList = Arrays.asList(getResources().getStringArray(R.array.sequenceAnalyzeList));
        ArrayAdapter<String> analyticsTypeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.header_spinner,myArrayList);
        chooseAnalyticsType.setAdapter(analyticsTypeAdapter);


        rallyHeader = (LinearLayout) view.findViewById(R.id.rallyHeaderLayout);
        strokeAnalysisHeaderLayout = (LinearLayout)  view.findViewById(R.id.strokeAnalysisHeaderLayout);
        thirdBallAnalysisHeaderLayout = (LinearLayout)  view.findViewById(R.id.thirdBallAnalysisHeaderLayout);
        serviceHeaderLayout = (LinearLayout)  view.findViewById(R.id.serviceHeaderLayout);
        receiveHeaderLayout = (LinearLayout)  view.findViewById(R.id.receiveHeaderLayout);
        countTitle = (TextView)  view.findViewById(R.id.countTitle);


        handTitle = (TextView)  view.findViewById(R.id.serviceTitle);
        destinationTitle = (TextView)  view.findViewById(R.id.destinationTitle);

        //errors and stroke analysis
        shotFrom = (TextView)  view.findViewById(R.id.shotFrom);
        shotDestination = (TextView)  view.findViewById(R.id.shotDestination);
        shotPlayedCount = (TextView)  view.findViewById(R.id.shotPlayedCount);

        mplayerAnalytics = (ListView) view.findViewById(R.id.playerAnalyticsListView);


        //receive points analysis
        handTitle1 = (TextView)  view.findViewById(R.id.handTitle1);
        destinationTitle1 = (TextView)  view.findViewById(R.id.destinationTitle1);
        winShotTitle1 = (TextView)  view.findViewById(R.id.winShotTitle1);
        countTitle1 = (TextView)  view.findViewById(R.id.countTitle1);

        //rally length sort
        sequenceLen = (TextView)  view.findViewById(R.id.sequenceLen);
        shotsPlayed = (TextView)  view.findViewById(R.id.shotsPlayed);
        winCount = (TextView)  view.findViewById(R.id.winCount);
        lossCount = (TextView)  view.findViewById(R.id.lossCount);
        efficiency = (TextView)  view.findViewById(R.id.efficiency);

        //third vall sort
        thirdService = (TextView)  view.findViewById(R.id.thirdService);
        thirdDest = (TextView)  view.findViewById(R.id.thirdDest);
        thirdEff = (TextView)  view.findViewById(R.id.thirdEff);


        timeAnalyzeFilter = (Spinner) view.findViewById(R.id.analyzeTimeFilter);
        sortAnalyzeFilter = (Spinner) view.findViewById(R.id.sortAnalyzeFilter);

        fourthball_analysisLayout = (LinearLayout) view.findViewById(R.id.fourthball_analysisLayout);
        sortLayout = (LinearLayout) view.findViewById(R.id.sortLayout);

        rallyHeader = (LinearLayout) view.findViewById(R.id.rallyHeaderLayout);
        strokeAnalysisHeader = (LinearLayout) view.findViewById(R.id.strokeAnalysisHeaderLayout);
        thirdstrokeAnalysisHeader = (LinearLayout) view.findViewById(R.id.strokeAnalysisHeaderLayout);
        uparrow = (ImageView) view.findViewById(R.id.analyticsuparrow);
        downarrow = (ImageView) view.findViewById(R.id.analyticsdownarrow);


        offensive_defensive_count = (TextView) view.findViewById(R.id.offensive_defensive_count);
        offensive_offensive_count = (TextView) view.findViewById(R.id.offensive_offensive_count);
        defensive_offensive_count = (TextView) view.findViewById(R.id.defensive_offensive_count);
        defensive_defensive_count = (TextView) view.findViewById(R.id.defensive_defensive_count);
        offensive_lost_count = (TextView) view.findViewById(R.id.offensive_lost_count);
        defensive_lost_count = (TextView) view.findViewById(R.id.defensive_lost_count);
        offensive_win_count = (TextView) view.findViewById(R.id.offensive_win_count);
        defensive_win_count = (TextView) view.findViewById(R.id.defensive_win_count);


        p6DestinationKeysJsonArr = new JSONArray();
        p8DestinationKeysJsonArr = new JSONArray();
        p9DestinationKeysJsonArr = new JSONArray();
        p14DestinationKeysJsonArr = new JSONArray();



        thirdService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sortJsonArray("3rd Ball Attack","serviceHand","string");
                sortedReport("3rd Ball Attack",sortedArray);
            }
        });
        thirdDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sortJsonArray("3rd Ball Attack","serviceDestination","string");
                sortedReport("3rd Ball Attack",sortedArray);
            }
        });
        thirdEff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sortJsonArray("3rd Ball Attack","totalEfficiency","integer");
                sortedReport("3rd Ball Attack",sortedArray);
            }
        });



        sequenceLen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                sortJsonArray("Rally Length","sequenceLen","integer");
                sortedReport("Rally Length",sortedArray);
            }
        });

        shotsPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sortJsonArray("Rally Length","played","integer");
                sortedReport("Rally Length",sortedArray);
            }
        });

        winCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sortJsonArray("Rally Length","winCount","integer");
                sortedReport("Rally Length",sortedArray);
            }
        });

        lossCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sortJsonArray("Rally Length","lossCount","integer");
                sortedReport("Rally Length",sortedArray);
            }
        });

        efficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sortJsonArray("Rally Length","efficiency","integer");
                sortedReport("Rally Length",sortedArray);
            }
        });


        countTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Points") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Loss") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Receive Points"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"win","integer");
                    sortedReport(sortArrayKey,sortedArray);
                }
                else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Response"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"totalPlayed","integer");
                    sortedReport(sortArrayKey,sortedArray);
                }
                else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Last Shot Analysis"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"strokesPlayed","integer");
                    sortedReport(sortArrayKey,sortedArray);
                }
            }
        });



        handTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                sortJsonArray(sortArrayKey,"serviceHand","string");
                sortedReport(sortArrayKey,sortedArray);
            }
        });

        destinationTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                sortJsonArray(sortArrayKey,"serviceDestination","string");
                sortedReport(sortArrayKey,sortedArray);
            }
        });

        winShotTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                sortJsonArray(sortArrayKey,"strokeHand","string");
                sortedReport(sortArrayKey,sortedArray);
            }
        });

        countTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                sortJsonArray(sortArrayKey,"win","integer");
                sortedReport(sortArrayKey,sortedArray);
            }
        });


        shotFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Errors") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Stroke Analysis") )
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"fromDestination","string");
                    sortedReport(sortArrayKey,sortedArray);
                }
            }
        });

        shotDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Errors") ||  chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Stroke Analysis"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"strokeHand","string");
                    sortedReport(sortArrayKey,sortedArray);
                }
            }
        });

        shotPlayedCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Errors") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Stroke Analysis"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"totalPlayed","integer");
                    sortedReport(sortArrayKey,sortedArray);
                }
            }
        });
        handTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Points") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Loss") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Fault") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Response"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"serviceHand","string");
                    sortedReport(sortArrayKey,sortedArray);
                }
                else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Last Shot Analysis"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"strokeKey","string");
                    sortedReport(sortArrayKey,sortedArray);
                }

            }
        });

        destinationTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Points") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Loss") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Fault") || chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Response"))
                {
                    String sortArrayKey =  chooseAnalyticsType.getSelectedItem().toString();
                    sortJsonArray(sortArrayKey,"serviceDestination","string");
                    sortedReport(sortArrayKey,sortedArray);
                }
            }
        });

        chooseAnalyticsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Receive Points"))
                {
                    serviceHeaderLayout.setVisibility(View.GONE);
                    receiveHeaderLayout.setVisibility(View.VISIBLE);
                    rallyHeader.setVisibility(View.GONE);
                    strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                    thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
                }


                if(getPlayerSetDataJSON_Analyze != null)
                {
                    mplayerAnalytics.setAdapter(null);
                    invokeAnalyticsReport();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });



        Collections.sort(player1AnalyzeSet, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player1JsonAnalyzeList);
        player1AnalyzeFilter.setAdapter(player1AnalyzeAdapter);

        try{
            if (!mSession.ypGetUserName().equals(null)) {
                //int spinnerPosition = player1AnalyzeAdapter.getPosition(mSession.ypGetUserName());
                //player1AnalyzeFilter.setSelection(spinnerPosition);
            }
        }catch(Exception e)
        {
        }


        Collections.sort(player2AnalyzeSet, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, player2JsonAnalyzeList);
        player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);

        player2AnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                player2AnalyzeFilter.setTag(player2AnalyzeAdapter.getItem(pos).getUserId());
                if(getPlayerSetDataJSON_Analyze != null)
                {
                    mplayerAnalytics.setAdapter(null);
                    invokeAnalyticsReport();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        player1AnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                player1AnalyzeFilter.setTag(player1AnalyzeAdapter.getItem(pos).getUserId());
                String selectedItem = player1AnalyzeFilter.getSelectedItem().toString();
                if(getPlayerSetDataJSON_Analyze != null)
                {
                    mplayerAnalytics.setAdapter(null);
                    invokeAnalyticsReport();
                    //code to be done
                    if(mUtil.isOnline())
                    {
                        requestType = "GET";
                        methodType = "getVsPlayerList";
                        params = "getVsPlayerList?caller=" + Constants.caller + "&apiKey="
                                + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&loggerSeqId="
                                +player1AnalyzeAdapter.getItem(pos).getUserId() ;

                        asyncTask = new ExecuteURLTask(getContext(),methodType);
                        asyncTask.delegate = AnalyzeAnalytics.this;
                        asyncTask.execute(params,queryparams,requestType);
                    }
                    else
                    {
                        toastMessage("No Internet");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });




        List<String> dateFilter = new ArrayList<String>();
        dateFilter.add("Any time");
        dateFilter.add("Beginning");
        dateFilter.add("Last 3 Months");
        dateFilter.add("Last Month");
        dateFilter.add("Last Week");

        ArrayAdapter<String> dateFilterViewAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.footer_spinner, dateFilter);
        timeAnalyzeFilter.setAdapter(dateFilterViewAdapter);

        timeAnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(getPlayerSetDataJSON_Analyze != null)
                {
                    mplayerAnalytics.setAdapter(null);
                    invokeAnalyticsReport();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        List<String> sortFilter = new ArrayList<String>();
        sortFilter.add("Win Effectiveness");
        sortFilter.add("Loss Percentage");
        sortFilter.add("Stroke Type");
        //sortFilter.add("Destination");




        ArrayAdapter<String> sortAnalyzeFilterAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.footer_spinner, sortFilter);
        sortAnalyzeFilter.setAdapter(sortAnalyzeFilterAdapter);
        sortAnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(getPlayerSetDataJSON_Analyze != null)
                {
                    if(mUtil.isOnline())
                    {
                        mplayerAnalytics.setAdapter(null);
                        invokeAnalyticsReport();
                    }
                    else
                    {
                        toastMessage("No Internet");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });





        return view;

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
        if (context instanceof BlankFragment.OnFragmentInteractionListener) {
            mListener = (BlankFragment.OnFragmentInteractionListener) context;
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

    public void sortJsonArray(String reportName,String key,final String paramType)
    {
        final String searchKey = key;
        if(paramType.equalsIgnoreCase("string"))
        {
            Collections.sort(sortedArray, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject lhs, JSONObject rhs) {
                    String s1 = lhs.get(searchKey).toString();
                    String s2 = rhs.get(searchKey).toString();
                    return s1.compareToIgnoreCase(s2);
                }
            });
        }
        else
        {
            Collections.sort(sortedArray, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject lhs, JSONObject rhs) {


                    float valA =  Float.parseFloat(lhs.get(searchKey).toString());
                    float valB = Float.parseFloat(rhs.get(searchKey).toString());
                    int result = 0;
                    if(valA > valB)
                        result =  -1;
                    if(valA < valB)
                        result =  1;
                    return result;
                }
            });
        }

    }
    public void sortedReport(String key,ArrayList<JSONObject> sortedArray)
    {
        Gson gson = new Gson();
        String listString = gson.toJson(sortedArray,new TypeToken<ArrayList<JSONObject>>() {}.getType());
        Object object=null;
        JSONArray arrayObj=null;
        JSONParser jsonParser=new JSONParser();
        try {
            object=jsonParser.parse(listString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrayObj=(JSONArray) object;

        serviceInfoAdapter = new ServicePointsAdapter(getContext(),getActivity(),arrayObj,serviceKeyMap,masterDestinationKeyMap);
        serviceLossInfoAdapter = new ServiceLossAdapter(getContext(),getActivity(),arrayObj,serviceKeyMap,masterDestinationKeyMap);
        receiveInfoAdapter = new ReceiveAdapter(getContext(),getActivity(),arrayObj,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
        rallyAdapter = new RallyAdapter(getContext(), getActivity(), arrayObj);
        serviceFaultAdapter = new ServiceFaultAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap);
        serviceResponseAdapter = new ServiceResponseAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
        errorAnalysisAdapter = new ErrorAnalysisAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
        strokeAnalysisAdapter = new StrokeAnalysisAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
        receiveInfoAdapter = new ReceiveAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
        thirdBallAttackAdapter = new ThirdBallAttackAdapter(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
        shotInfoAdapter1 = new AnalyticsAdapter1(getContext(), getActivity(), arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);

        mplayerAnalytics.setAdapter(null);

        if(key.equalsIgnoreCase("Rally Length"))
            mplayerAnalytics.setAdapter(rallyAdapter);
        else if(key.equalsIgnoreCase("Service Points"))
            mplayerAnalytics.setAdapter(serviceInfoAdapter);
        else if(key.equalsIgnoreCase("Service Loss"))
            mplayerAnalytics.setAdapter(serviceLossInfoAdapter);
        else if(key.equalsIgnoreCase("Service Fault"))
            mplayerAnalytics.setAdapter(serviceFaultAdapter);
        else if(key.equalsIgnoreCase("Service Response"))
            mplayerAnalytics.setAdapter(serviceResponseAdapter);
        else if(key.equalsIgnoreCase("Receive Points"))
            mplayerAnalytics.setAdapter(receiveInfoAdapter);
        else if(key.equalsIgnoreCase("Errors"))
            mplayerAnalytics.setAdapter(errorAnalysisAdapter);
        else if(key.equalsIgnoreCase("Stroke Analysis"))
            mplayerAnalytics.setAdapter(strokeAnalysisAdapter);
        else if(key.equalsIgnoreCase("3rd Ball Attack"))
            mplayerAnalytics.setAdapter(thirdBallAttackAdapter);
        else if(key.equalsIgnoreCase("Last Shot Analysis"))
            mplayerAnalytics.setAdapter(shotInfoAdapter1);



    }

    public void invokeAnalyticsReport()
    {
        if(mUtil.isOnline() && 	player1AnalyzeFilter.getSelectedItem() != null && player2AnalyzeFilter.getSelectedItem() != null)
        {
            String player1Name = player1AnalyzeFilter.getSelectedItem().toString();
            String player2Name = player2AnalyzeFilter.getSelectedItem().toString();
            String dateFilterValue = timeAnalyzeFilter.getSelectedItem().toString();
            String sortValue = sortAnalyzeFilter.getSelectedItem().toString();
            String player1ID = "";
            String player2ID = "";
            if(player1AnalyzeFilter.getTag() == null)
                player1ID = player1AnalyzeAdapter.getItem(0).getUserId();
            else
                player1ID = player1AnalyzeFilter.getTag().toString();

            if(player2AnalyzeFilter.getTag() != null)
                player2ID = player2AnalyzeFilter.getTag().toString();


            requestType = "GET";
            methodType = "summarizedSequence";
            if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Last Shot Analysis"))
            {



                fourthball_analysisLayout.setVisibility(View.GONE);
                sortLayout.setVisibility(View.VISIBLE);

                serviceHeaderLayout.setVisibility(View.VISIBLE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
                handTitle.setText("stroke");
                destinationTitle.setText("destination");
                handTitle.setPaintFlags(handTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));
                destinationTitle.setPaintFlags(destinationTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));


                fetchParam = "Last shot";
                params = "fetchSummarizedSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Points"))
            {

                fourthball_analysisLayout.setVisibility(View.GONE);
                sortLayout.setVisibility(View.GONE);


                serviceHeaderLayout.setVisibility(View.VISIBLE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

                //handTitle = (TextView) findViewById(R.id.serviceTitle);
                handTitle.setText("service");
                //destinationTitle = (TextView) findViewById(R.id.destinationTitle);
                destinationTitle.setText("destination");
                handTitle.setPaintFlags(handTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));
                destinationTitle.setPaintFlags(destinationTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));

                fetchParam = "Service Points";
                params = "fetchSummarizedServiceSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Loss"))
            {

              //  LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);
                //LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
                sortLayout.setVisibility(View.GONE);
                //handTitle = (TextView) findViewById(R.id.serviceTitle);
                handTitle.setText("service");
               // destinationTitle = (TextView) findViewById(R.id.destinationTitle);
                destinationTitle.setText("destination");
                handTitle.setPaintFlags(handTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));
                destinationTitle.setPaintFlags(destinationTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));

                serviceHeaderLayout.setVisibility(View.VISIBLE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

                fetchParam = "Service Loss";
                params = "fetchSummarizedServiceLossSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Fault"))
            {

                //LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);
                //LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
                sortLayout.setVisibility(View.GONE);

                serviceHeaderLayout.setVisibility(View.VISIBLE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
               // handTitle = (TextView) findViewById(R.id.serviceTitle);
                handTitle.setText("service");
                //destinationTitle = (TextView) findViewById(R.id.destinationTitle);
                destinationTitle.setText("error");
                handTitle.setPaintFlags(handTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));
                destinationTitle.setPaintFlags(destinationTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));

                fetchParam = "Service Fault";
                params = "fetchSummarizedServiceFaultSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }

            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Service Response"))
            {

                //LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);
               // LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
                sortLayout.setVisibility(View.GONE);
               // handTitle = (TextView) findViewById(R.id.serviceTitle);
                handTitle.setText("service");
               // destinationTitle = (TextView) findViewById(R.id.destinationTitle);
                destinationTitle.setText("destination");
                handTitle.setPaintFlags(handTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));
                destinationTitle.setPaintFlags(destinationTitle.getPaintFlags() | (Paint.UNDERLINE_TEXT_FLAG));

                serviceHeaderLayout.setVisibility(View.VISIBLE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

                fetchParam = "Service Response";
                params = "fetchServiceResponseAnalysis?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }

            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Receive Points"))
            {

               // LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);
                //LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
                sortLayout.setVisibility(View.GONE);

                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.VISIBLE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
                fetchParam = "Receive Points";
                params = "fetchSummarizedReceiverSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Rally Length"))
            {
                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.VISIBLE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

                fetchParam = "Rally Length";
                params = "fetchRallyAnalysis?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Stroke Analysis"))
            {

               // LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);

                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.VISIBLE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);


                fetchParam = "Stroke Analysis";
                params = "fetchStrokeAnalysis?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("Errors"))
            {

               // LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);

                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.VISIBLE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);


                fetchParam = "Error Analysis";
                params = "fetchErrorAnalysis?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }

            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("3rd Ball Attack"))
            {

                //LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.GONE);
                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.VISIBLE);

                fetchParam = "3rd Ball Attack";
                params = "fetch3BallAttack?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }
            else if(chooseAnalyticsType.getSelectedItem().toString().equalsIgnoreCase("4th Ball Shot"))
            {

               // LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                fourthball_analysisLayout.setVisibility(View.VISIBLE);

                serviceHeaderLayout.setVisibility(View.GONE);
                receiveHeaderLayout.setVisibility(View.GONE);
                rallyHeader.setVisibility(View.GONE);
                strokeAnalysisHeaderLayout.setVisibility(View.GONE);
                thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

                fetchParam = "4th Ball Shot";
                params = "fetch4BallShot?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID+"&analysisType=App";
            }



            asyncTask = new ExecuteURLTask(getContext(),methodType);
            asyncTask.delegate = AnalyzeAnalytics.this;
            asyncTask.execute(params,queryparams,requestType);
            //ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
            mplayerAnalytics.setAdapter(null);
        }
        else if(!(mUtil.isOnline()))
        {
            toastMessage("No Internet");

        }
    }
    @Override
    public void processFinish(String result, String methodName) {
        if(result != null)
        {
            if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getPlayerSetData_Analyze"))
            {
                JSONParser jsonParser = new JSONParser();
                Object obj;
                try {
                    obj = jsonParser.parse(result);
                    getPlayerSetDataJSON_Analyze = (JSONObject) obj;

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(getPlayerSetDataJSON_Analyze.containsKey("matchStrokes"))
                {

                    JSONObject matchStrokes = (JSONObject) getPlayerSetDataJSON_Analyze.get("matchStrokes");


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



                if(getPlayerSetDataJSON_Analyze.containsKey("player1Set"))
                {
                    if(getPlayerSetDataJSON_Analyze.get("player1Set") != null && getPlayerSetDataJSON_Analyze.get("player1Set").toString().length() > 0 &&
                            getPlayerSetDataJSON_Analyze.get("player2Set") != null && getPlayerSetDataJSON_Analyze.get("player2Set").toString().length() > 0


                            )
                    {


                        Gson gson = new Gson();
                        Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
                        player1JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON_Analyze.get("player1Set").toString(), listType1);
                        player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player1JsonAnalyzeList);
                        player1AnalyzeFilter.setAdapter(player1AnalyzeAdapter);



                        player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON_Analyze.get("player2Set").toString(), listType1);
                        player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player2JsonAnalyzeList);
                        player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);




                        if(getPlayerSetDataJSON_Analyze.get(mSession.ypGetUserID()) != null)
                        {

                            player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON_Analyze.get(mSession.ypGetUserID()).toString(), listType1);
                            player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                    R.layout.header_spinner, player2JsonAnalyzeList);
                            player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);
                        }





                        player1AnalyzeAdapter.notifyDataSetChanged();
                        player2AnalyzeAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    player1AnalyzeSet = new ArrayList<String>();
                    player2AnalyzeSet = new ArrayList<String>();
                    player1AnalyzeSet.add(0,mSession.ypGetUserName());
                    player2AnalyzeSet.add(0,"All");
                    player2AnalyzeSet.add(1,"All R/H");
                    player2AnalyzeSet.add(2,"All L/H");
                    player1AnalyzeAdapterr.notifyDataSetChanged();
                    player2AnalyzeAdapterr.notifyDataSetChanged();
                }



                String player1Name = player1AnalyzeFilter.getSelectedItem().toString();
                String player2Name = player2AnalyzeFilter.getSelectedItem().toString();
                String dateFilterValue = timeAnalyzeFilter.getSelectedItem().toString();
                String sortValue = sortAnalyzeFilter.getSelectedItem().toString();
                String chooseAnalyticsType = "Last Shot Analysis";
                requestType = "GET";
                methodType = "summarizedSequence";
                fetchParam = "Last shot";
                sortAnalyzeFilter.setEnabled(true);

                params = "fetchSummarizedSequence?caller=" + Constants.caller + "&apiKey="
                        + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                        + mSession.ypGetUserName()+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue;



                asyncTask = new ExecuteURLTask(getContext(),methodType);
                asyncTask.delegate = AnalyzeAnalytics.this;
                asyncTask.execute(params,queryparams,requestType);









            }

            else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("summarizedSequence"))
            {
                if(result != null)
                {
                    JSONParser jsonParser = new JSONParser();
                    Object obj;
                    JSONArray jsonValues = null;
                    try {
                        obj = jsonParser.parse(result);
                        jsonValues = (JSONArray) obj;

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }



                    ArrayList<ShotInfo> shotInfoList = new ArrayList<ShotInfo>();
                    ArrayList<Rally> rallyList = new ArrayList<Rally>();
                    ArrayList<ErrorAnalyzer> errorList = new ArrayList<ErrorAnalyzer>();


                    shotInfoAdapter = new AnalyticsAdapter(getContext(),getActivity(),shotInfoList,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
                    shotInfoAdapter1 = new AnalyticsAdapter1(getContext(), getActivity(), jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
                    serviceInfoAdapter = new ServicePointsAdapter(getContext(),getActivity(),jsonValues,serviceKeyMap,masterDestinationKeyMap);
                    serviceLossInfoAdapter = new ServiceLossAdapter(getContext(),getActivity(),jsonValues,serviceKeyMap,masterDestinationKeyMap);
                    receiveInfoAdapter = new ReceiveAdapter(getContext(),getActivity(),jsonValues,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
                    rallyAdapter = new RallyAdapter(getContext(),getActivity(),jsonValues);
                    strokeAnalysisAdapter = new StrokeAnalysisAdapter(getContext(),getActivity(),jsonValues,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);

                    errorAnalysisAdapter = new ErrorAnalysisAdapter(getContext(), getActivity(), jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);

                    thirdBallAttackAdapter = new ThirdBallAttackAdapter(getContext(),getActivity(),jsonValues,serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
                    fourthBallAdapter = new FourthBallAdapter(getContext(),getActivity(), jsonValues);
                    serviceFaultAdapter = new ServiceFaultAdapter(getContext(), getActivity(), jsonValues, serviceKeyMap, masterDestinationKeyMap);
                    serviceResponseAdapter = new ServiceResponseAdapter(getContext(), getActivity(), jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
                    mplayerAnalytics.setAdapter(null);

                    if(fetchParam.equalsIgnoreCase("Last shot"))
                    {
                        //mplayerAnalytics.setAdapter(shotInfoAdapter);
                        mplayerAnalytics.setAdapter(shotInfoAdapter1);

                    }
                    else if(fetchParam.equalsIgnoreCase("Service Points"))
                        mplayerAnalytics.setAdapter(serviceInfoAdapter);
                    else if(fetchParam.equalsIgnoreCase("Service Loss"))
                        mplayerAnalytics.setAdapter(serviceLossInfoAdapter);
                    else if(fetchParam.equalsIgnoreCase("Service Fault"))
                    {

                        mplayerAnalytics.setAdapter(serviceFaultAdapter);
                        //LinearLayout rallyHeader = (LinearLayout) findViewById(R.id.rallyHeaderLayout);
                        rallyHeader.setVisibility(View.GONE);
                        //LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
                        thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
                        //LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
                        fourthball_analysisLayout.setVisibility(View.GONE);
                    }
                    else if(fetchParam.equalsIgnoreCase("Service Response"))
                    {
                        mplayerAnalytics.setAdapter(serviceResponseAdapter);
                    }
                    else if(fetchParam.equalsIgnoreCase("Receive Points"))
                        mplayerAnalytics.setAdapter(receiveInfoAdapter);
                    else if(fetchParam.equalsIgnoreCase("Rally Length"))
                    {
                        rallyHeader.setVisibility(View.VISIBLE);
                        mplayerAnalytics.setAdapter(rallyAdapter);
                    }
                    else if(fetchParam.equalsIgnoreCase("Stroke Analysis"))
                    {


                        strokeAnalysisHeader.setVisibility(View.VISIBLE);
                        mplayerAnalytics.setAdapter(strokeAnalysisAdapter);
                    }
                    else if(fetchParam.equalsIgnoreCase("Error Analysis"))
                    {
                        strokeAnalysisHeader.setVisibility(View.VISIBLE);
                        mplayerAnalytics.setAdapter(errorAnalysisAdapter);
                    }
                    else if(fetchParam.equalsIgnoreCase("3rd Ball Attack"))
                    {
                        //LinearLayout thirdstrokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
                        thirdstrokeAnalysisHeader.setVisibility(View.VISIBLE);

                        mplayerAnalytics.setAdapter(thirdBallAttackAdapter);


                    }
                    else if(fetchParam.equalsIgnoreCase("4th Ball Shot"))
                    {
                        mplayerAnalytics.setAdapter(null);
                       // LinearLayout thirdstrokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
                        thirdstrokeAnalysisHeader.setVisibility(View.GONE);
                        fourthball_analysisLayout.setVisibility(View.VISIBLE);
                    }








                    mplayerAnalytics.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                            mplayerAnalytics.setSelection(0);
                        }
                    });

                    downarrow.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            if(fetchParam.equalsIgnoreCase("Last shot"))
                                mplayerAnalytics.setSelection(shotInfoAdapter1.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Service Points"))
                                mplayerAnalytics.setSelection(serviceInfoAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Service Loss"))
                                mplayerAnalytics.setSelection(serviceLossInfoAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Service Fault"))
                                mplayerAnalytics.setSelection(serviceFaultAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Receive Points"))
                                mplayerAnalytics.setSelection(receiveInfoAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Rally Length"))
                                mplayerAnalytics.setSelection(rallyAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Stroke Analysis"))
                                mplayerAnalytics.setSelection(strokeAnalysisAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Error Analysis"))
                                mplayerAnalytics.setSelection(errorAnalysisAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("3rd Ball Attack"))
                                mplayerAnalytics.setSelection(thirdBallAttackAdapter.getCount() - 1);
                            else if(fetchParam.equalsIgnoreCase("Service Response"))
                                mplayerAnalytics.setSelection(serviceResponseAdapter.getCount() - 1);







                        }
                    });

                    try {

                    if(fetchParam.equalsIgnoreCase("Last shot"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(shotInfoAdapter1);
                        ObjectMapper mapper = new ObjectMapper();

                            sortedArray = mapper.readValue(jsonValues.toString(), TypeFactory.collectionType(List.class, JSONObject.class));



                    }
                    else if(fetchParam.equalsIgnoreCase("Service Points"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(serviceInfoAdapter);
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(fetchParam.equalsIgnoreCase("Service Loss"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(serviceLossInfoAdapter);
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                    }
                    else if(fetchParam.equalsIgnoreCase("Service Fault"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(serviceFaultAdapter);
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                    }
                    else if(fetchParam.equalsIgnoreCase("Service Response"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(serviceResponseAdapter);
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                    }
                    else if(fetchParam.equalsIgnoreCase("Receive Points"))
                    {
                        sortedArray.clear();
                        mplayerAnalytics.setAdapter(receiveInfoAdapter);
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                    }
                    else if(fetchParam.equalsIgnoreCase("Rally Length"))
                    {
                        mplayerAnalytics.setAdapter(rallyAdapter);
                        sortedArray.clear();

                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));

                    }
                    else if(fetchParam.equalsIgnoreCase("Stroke Analysis"))
                    {
                        mplayerAnalytics.setAdapter(strokeAnalysisAdapter);
                        sortedArray.clear();
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));

                    }
                    else if(fetchParam.equalsIgnoreCase("Error Analysis"))
                    {
                        mplayerAnalytics.setAdapter(errorAnalysisAdapter);
                        sortedArray.clear();
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));

                    }
                    else if(fetchParam.equalsIgnoreCase("3rd Ball Attack"))
                    {
                        mplayerAnalytics.setAdapter(thirdBallAttackAdapter );
                        sortedArray.clear();
                        ObjectMapper mapper = new ObjectMapper();
                        sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
                    }
                    else if(fetchParam.equalsIgnoreCase("4th Ball Shot"))
                    {
                        mplayerAnalytics.setAdapter(null);

                        offensive_defensive_count.setText("0");
                        offensive_offensive_count.setText("0");
                        defensive_offensive_count.setText("0");
                        defensive_defensive_count.setText("0");
                        offensive_lost_count.setText("0");
                        defensive_lost_count.setText("0");
                        offensive_win_count.setText("0");
                        defensive_win_count.setText("0");

                        if(jsonValues.size() > 0)
                        {
                            for (int i = 0; i < jsonValues.size(); i++)
                            {
                                JSONObject jsonObj = (JSONObject)jsonValues.get(i);
                                if(jsonObj.containsKey("strokeHand1") && jsonObj.containsKey("strokeHand2"))
                                {
                                    String strokeHand1 = jsonObj.get("strokeHand1").toString();
                                    String strokeHand2 = jsonObj.get("strokeHand2").toString();

                                    if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("defensive"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            offensive_defensive_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("offensive"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            offensive_offensive_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("offensive"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            defensive_offensive_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("defensive"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            defensive_defensive_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("lost"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            offensive_lost_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("lost"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            defensive_lost_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("win"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            offensive_win_count.setText(jsonObj.get("count").toString());
                                    }
                                    else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("win"))
                                    {
                                        if(jsonObj.containsKey("count"))
                                            defensive_win_count.setText(jsonObj.get("count").toString());
                                    }
                                }
                            }
                        }

                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {

                }
            }


            else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("downloadAnalytics"))
            {
                try
                {

                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    //final File dwldsPath = create File(getApplicationContext().getFilesDir(), "Analytics.pdf");
                    final File dwldsPath = new File(path+"/"+ "Analytics" + ".pdf");
                    if ( dwldsPath.exists() == false )
                    {
                        dwldsPath.createNewFile();
                    }
                    byte[] pdfAsBytes = Base64.decode(result, 0);
                    FileOutputStream os;
                    os = new FileOutputStream(dwldsPath, false);
                    os.write(pdfAsBytes);
                    os.flush();
                    os.close();
                    //progressBar.setVisibility(View.GONE);

                    AlertDialog.Builder openFileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                    openFileDialog.setTitle("Open file ? ");
                    openFileDialog.setCancelable(false);
                    openFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    openFileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Uri path = Uri.fromFile(dwldsPath);
                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            pdfOpenintent.setDataAndType(path, "application/pdf");
                            try {
                                  startActivity(pdfOpenintent);
                            }
                            catch (ActivityNotFoundException e) {

                            }
                        }

                    });
                    openFileDialog.show();


                }catch(Exception e)
                {
                    toastMessage(e.getMessage());

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

                        player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, player2JsonAnalyzeList);
                        player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);
                        player2AnalyzeAdapter.notifyDataSetChanged();



                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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

    public void downloadAnalyticsData(){
        int permission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setMessage("Permission to access the SD-CARD is required for this app to Download PDF.")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();
            }
        }
       // progressBar.setVisibility(View.VISIBLE);

        String player1Name = player1AnalyzeFilter.getSelectedItem().toString();
        String player2Name = player2AnalyzeFilter.getSelectedItem().toString();
        String dateFilterValue = timeAnalyzeFilter.getSelectedItem().toString();
        String sortValue = sortAnalyzeFilter.getSelectedItem().toString();
        String player1ID = "";
        String player2ID = "";
        if(player1AnalyzeFilter.getTag() == null)
            player1ID = player1AnalyzeAdapter.getItem(0).getUserId();
        else
            player1ID = player1AnalyzeFilter.getTag().toString();

        if(player2AnalyzeFilter.getTag() != null)
            player2ID = player2AnalyzeFilter.getTag().toString();

        fetchParam = "downloadAnalytics";
        requestType = "GET";
        methodType = "downloadAnalytics";
        params = "downloadAnalytics?caller=" + Constants.caller + "&apiKey="
                + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
                + player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&sortFilterValue="+sortValue+"&player1ID="+player1ID+"&player2ID="+player2ID;




        asyncTask = new ExecuteURLTask(getContext(),methodType);
        asyncTask.delegate = AnalyzeAnalytics.this;
        asyncTask.execute(params,queryparams,requestType);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    toastMessage("Permission has been denied by user");

                } else {
                    toastMessage("Permission has been granted by user");



                }
                return;
            }
        }
    }

    public void toastMessage(String message)
    {
        //Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

        mUtil.toastMessage(message,getContext());

    }

}
