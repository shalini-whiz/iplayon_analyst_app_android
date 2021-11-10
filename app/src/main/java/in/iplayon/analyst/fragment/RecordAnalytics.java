package in.iplayon.analyst.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.LuhnChecksumValidator;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import in.iplayon.analyst.R;
import in.iplayon.analyst.activity.SequenceActivity;
import in.iplayon.analyst.adapter.RecordShotAdapter;
import in.iplayon.analyst.adapter.RubberAdapter;
import in.iplayon.analyst.modal.PlayerJson;
import in.iplayon.analyst.modal.Rubber;
import in.iplayon.analyst.modal.ShareHistoryPlayerJson;
import in.iplayon.analyst.modal.Shot;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.CircleForm;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;

public class RecordAnalytics extends Fragment implements AsyncResponse {
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

    ArrayList<Rubber> player1FH = new ArrayList<Rubber>();
    ArrayList<Rubber> player1BH = new ArrayList<Rubber>();
    ArrayList<Rubber> player2FH = new ArrayList<Rubber>();
    ArrayList<Rubber> player2BH = new ArrayList<Rubber>();


    RubberAdapter player1FHAdapter;
    RubberAdapter player1BHAdapter;
    RubberAdapter player2FHAdapter;
    RubberAdapter player2BHAdapter;

    ListView mListView;
    RecordShotAdapter recordShotAdapter;
    ArrayList<Shot> recordShotAdapterList = new ArrayList<Shot>();
    ArrayAdapter<String> serviceAdapter ;
    ArrayAdapter<String> strokeAdapter ;

    ListView foreHandListView;
    ListView backHandListView;
    String player1Hand = "";
    String player2Hand = "";
    String matchDate = "";

    Boolean player1ProfileSettings = false;
    Boolean player2ProfileSettings = false;

    AutoCompleteTextView player1Spinner;
    AutoCompleteTextView player2Spinner;
    ImageView player1Details;
    ImageView player2Details;
    RadioButton player1Service ;
    RadioButton player2Service ;
    RadioButton partialSequence;
    RadioButton completeSequence;
    EditText player1SetInput;
    EditText player1Points;
    EditText player2SetInput;
    EditText player2Points;
    Spinner serviceType;
    ListView mStrokesListView;
    ListView viewPlayerSequences;
    TextView sequenceSave;
    List<String> losingStrokes = new ArrayList<String>();
    AlertDialog mAlertDialog;
    ImageView player1ServiceIcon;
    ImageView player2ServiceIcon;
    View header;
    AutoCompleteTextView shotHandInput;
    AutoCompleteTextView shotDestinationInput;
    LinearLayout spinnerLayout;
    ImageView shotDeleteInput;
    EditText matchTitle;
    EditText startTime;
    TextView sequenceReset;
    ImageView sequenceSettings;
    String winner = "";
    public String popupPlayer="";

    String requestType= "";
    String params = "";
    String queryparams = "";
    String methodType = "";
    ExecuteURLTask asyncTask;

    ArrayAdapter<PlayerJson> dataAdapter1;
    ArrayAdapter<PlayerJson> dataAdapter2;

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
    List<PlayerJson> playerJsonList;

    LinearLayout recordAnalyticsLayout;



    public RecordAnalytics() {
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

        View view = inflater.inflate(R.layout.activity_record_analytics, container, false);

        player1FHAdapter = new RubberAdapter(getContext(), getActivity(),player1FH);
        player1BHAdapter = new RubberAdapter(getContext(), getActivity(),player1BH);
        player2FHAdapter = new RubberAdapter(getContext(), getActivity(),player2FH);
        player2BHAdapter = new RubberAdapter(getContext(), getActivity(),player2BH);


        recordAnalyticsLayout = (LinearLayout) view.findViewById(R.id.recordAnalyticsLayout);

        mListView = (ListView) view.findViewById(R.id.playerStrokesAppend);
        player1Service = (RadioButton) view.findViewById(R.id.player1Service);
        player2Service = (RadioButton) view.findViewById(R.id.player2Service);
        player1SetInput = (EditText) view.findViewById(R.id.player1Set);
        player1Points = (EditText) view.findViewById(R.id.player1Points);
        player2SetInput = (EditText) view.findViewById(R.id.player2Set);
        player2Points = (EditText) view.findViewById(R.id.player2Points);
        player1Details = (ImageView) view.findViewById(R.id.player1Details);
        player2Details = (ImageView) view.findViewById(R.id.player2Details);
        partialSequence = (RadioButton) view.findViewById(R.id.partialSequence);
        completeSequence = (RadioButton) view.findViewById(R.id.completeSequence);
        player1ServiceIcon = (ImageView) view.findViewById(R.id.player1ServiceIcon);
        player2ServiceIcon = (ImageView) view.findViewById(R.id.player2ServiceIcon);

        header = getLayoutInflater().inflate(R.layout.data_view, null);
        shotHandInput = (AutoCompleteTextView) header.findViewById(R.id.shotHandInput);
        shotDestinationInput = (AutoCompleteTextView) header.findViewById(R.id.shotDestinationInput);
        shotDeleteInput = (ImageView) header.findViewById(R.id.shotDeleteInput);

        startTime = (EditText) view.findViewById(R.id.startTime);
        matchTitle = (EditText) view.findViewById(R.id.matchTitle);
        final ImageView uparrow = (ImageView) view.findViewById(R.id.uparrow);
        final ImageView downarrow = (ImageView) view.findViewById(R.id.downarrow);

        p6DestinationKeysJsonArr = new JSONArray();
        p8DestinationKeysJsonArr = new JSONArray();
        p9DestinationKeysJsonArr = new JSONArray();
        p14DestinationKeysJsonArr = new JSONArray();




        recordShotAdapter = new RecordShotAdapter(getContext(), getActivity(),recordShotAdapterList);
        recordShotAdapterList.clear();
        recordShotAdapter.notifyDataSetChanged();
        mListView.setAdapter(recordShotAdapter);

        if(mSession.ypGetMatchDate() == null)
        {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            mSession.ypStoreSessionMatchDate(currentDate);
        }


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                mListView.setSelection(0);
            }
        });

        downarrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mListView.setSelection(recordShotAdapter.getCount() - 3);
            }
        });




        serviceAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.shot_spinner_item);

        strokeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.shot_spinner_item);

        if(mListView.getFooterViewsCount() == 0)
            mListView.addFooterView(header);



        partialSequence.setChecked(false);
        completeSequence.setChecked(true);
        player1Service.setChecked(false);
        player2Service.setChecked(false);

        player1ServiceIcon.setVisibility(View.GONE);
        player2ServiceIcon.setVisibility(View.GONE);


        player1Details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if( (!player1Spinner.getTag().toString().equalsIgnoreCase("")) && (! player1Spinner.getTag().toString().equalsIgnoreCase(null)))
                {
                    requestType="GET";
                    queryparams="";
                    methodType="getPlayerDetails";
                    params= "getPlayerDetails?caller="+ Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerID="+player1Spinner.getTag().toString()+"&playerName="+player1Spinner.getText().toString();

                    ExecuteURLTask asyncTask1 = new ExecuteURLTask(getContext(),getActivity(),false,methodType);
                    asyncTask1.delegate = RecordAnalytics.this;
                    asyncTask1.execute(params,queryparams,requestType);
                }
                else {
                    if(player1Spinner.getText().toString().trim().length() == 0)
                        mUtil.toastMessage("Please choose Player",getContext());
                    else
                        mUtil.toastMessage("New Player",getContext());

                }

            }
        });

        player2Details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(player2Spinner.getTag() == null)
                    player2Spinner.setTag("");

                if( (!player2Spinner.getTag().toString().equalsIgnoreCase("")) && (! player2Spinner.getTag().toString().equalsIgnoreCase(null)))

                {
                    requestType = "GET";
                    queryparams = "";
                    methodType = "getPlayerDetails";
                    params = "getPlayerDetails?caller=" + Constants.caller + "&apiKey=" + Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&playerID=" + player2Spinner.getTag() + "&playerName=" + player2Spinner.getText().toString();
                    ExecuteURLTask asyncTask1 = new ExecuteURLTask(getContext(),getActivity(), false,methodType);
                    asyncTask1.delegate = RecordAnalytics.this;
                    asyncTask1.execute(params, queryparams, requestType);
                }
                else {
                    if(player2Spinner.getText().toString().trim().length() == 0)
                        mUtil.toastMessage("Please choose Player",getContext());
                    else
                        mUtil.toastMessage("New Player",getContext());

                }
            }
        });
        ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.shot_spinner_item, destinationArrayList);
        shotDestinationInput.setAdapter(destinationAdapter);
        shotDestinationInput.setThreshold(1);

        shotDeleteInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                shotHandInput.setText("");
                shotDestinationInput.setText("");
                shotHandInput.setText("");
                shotDestinationInput.setText("");
                shotHandInput.setTag("");
                shotDestinationInput.setTag("");
                shotDestinationInput.setEnabled(true);
                shotHandInput.setEnabled(true);
            }
        });

        recordShotAdapter.setOnDataChangeListener(new RecordShotAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int lSize) {

                if(lSize > 0)
                {
                    Shot shotInfo  = recordShotAdapterList.get(recordShotAdapterList.size()-1);
                    String validDestination = shotInfo.getStrokeDestination();
                    if(losingStrokes.indexOf(validDestination) > -1)
                    {
                        shotDestinationInput.setEnabled(false);
                        shotHandInput.setEnabled(false);
                    }
                    else
                    {
                        shotDestinationInput.setEnabled(true);
                        shotHandInput.setEnabled(true);
                    }
                }
                else
                {
                    shotDestinationInput.setEnabled(true);
                    shotHandInput.setEnabled(true);
                }

            }
        });



        shotHandInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(player2Spinner.getText().toString().length() > 0)
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN && recordShotAdapterList.size() == 0 && player2Spinner.getText().toString().length() > 0)
                    {

                        if(!(player1Service.isChecked()) && !(player2Service.isChecked()))
                        {
                            LayoutInflater inflater = getLayoutInflater();
                            final View viewSequenceStrokeLayout = inflater.inflate(R.layout.custom_sequence_service, null);
                            final RadioButton customPlayer1Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer1Service);
                            final RadioButton customPlayer2Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer2Service);

                            AlertDialog.Builder serviceDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                            //serviceDialog.setTitle("Choose Service By");


                            customPlayer1Service.setText(player1Spinner.getText().toString());
                            customPlayer2Service.setText(player2Spinner.getText().toString());

                            serviceDialog.setView(viewSequenceStrokeLayout);
                            serviceDialog.setCancelable(false);
                            serviceDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(customPlayer1Service.isChecked())
                                    {
                                        player1Service.setChecked(true);
                                        player2Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.VISIBLE);
                                        player2ServiceIcon.setVisibility(View.GONE);

                                        shotHandInput.setTextColor(ContextCompat.getColor(getContext(),R.color.playerColor));
                                        shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(),R.color.playerColor));
                                    }
                                    else if(customPlayer2Service.isChecked())
                                    {
                                        player2Service.setChecked(true);
                                        player1Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.GONE);
                                        player2ServiceIcon.setVisibility(View.VISIBLE);

                                        shotHandInput.setTextColor(ContextCompat.getColor(getContext(),R.color.opponentColor));
                                        shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(),R.color.opponentColor));
                                    }
                                    else
                                    {
                                        player1Service.setChecked(false);
                                        player2Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.GONE);
                                        player2ServiceIcon.setVisibility(View.GONE);
                                        toastMessage("Service is not been set!!");
                                        //Toast.makeText(getContext(),"Service is not been set!!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            serviceDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });


                            serviceDialog.show();
                        }
                    }

                    if(recordShotAdapterList.size() == 0)
                    {
                        serviceAdapter.clear();
                        serviceAdapter.addAll(serviceArrayList);
                        serviceAdapter.notifyDataSetChanged();
                        shotHandInput.setAdapter(null);
                        shotHandInput.setAdapter(serviceAdapter);
                        shotHandInput.setThreshold(1);

                    }
                    else
                    {
                        strokeAdapter.clear();
                        strokeAdapter.addAll(strokeArrayList);
                        strokeAdapter.notifyDataSetChanged();
                        shotHandInput.setAdapter(null);

                        shotHandInput.setAdapter(strokeAdapter);
                        shotHandInput.setThreshold(1);
                        strokeAdapter.notifyDataSetChanged();

                    }

                    int adapterSize = recordShotAdapterList.size();
                    if(player1Service.isChecked())
                    {
                        if((adapterSize%2)==0)
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                        }
                        else
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                        }
                    }
                    else
                    {
                        if((adapterSize%2)==0)
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                        }
                        else
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                        }
                    }

                }


                else if(event.getAction() == MotionEvent.ACTION_DOWN && player2Spinner.getText().toString().length() == 0)
                {
                    toastMessage("Opponent name to be entered");
                    //Toast.makeText(getContext(),"Opponent name to be entered",Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });

        shotDestinationInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shotDestinationInput.setFocusable(true);

                if(player2Spinner.getText().toString().length() > 0)
                {

                    System.out.println("shotDest .. "+destinationArrayList.size()+" ... "+destinationArrayList.toString());
                    System.out.println("shotAction .. "+event.getAction());
                    if (event.getAction() == MotionEvent.ACTION_DOWN && recordShotAdapterList.size() == 0 && player2Spinner.getText().toString().length() > 0)
                    {
                        if(!(player1Service.isChecked()) && !(player2Service.isChecked()))
                        {
                            LayoutInflater inflater = getLayoutInflater();
                            final View viewSequenceStrokeLayout = inflater.inflate(R.layout.custom_sequence_service, null);
                            final RadioButton customPlayer1Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer1Service);
                            final RadioButton customPlayer2Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer2Service);

                            AlertDialog.Builder serviceDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                            //serviceDialog.setTitle("Choose Service By");

                            customPlayer1Service.setText(player1Spinner.getText().toString());
                            customPlayer2Service.setText(player2Spinner.getText().toString());

                            serviceDialog.setView(viewSequenceStrokeLayout);
                            serviceDialog.setCancelable(false);
                            serviceDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(customPlayer1Service.isChecked())
                                    {
                                        player1Service.setChecked(true);
                                        player2Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.VISIBLE);
                                        player2ServiceIcon.setVisibility(View.GONE);

                                        shotHandInput.setTextColor(ContextCompat.getColor(getContext(),R.color.playerColor));
                                        shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(),R.color.playerColor));
                                    }
                                    else if(customPlayer2Service.isChecked())
                                    {
                                        player2Service.setChecked(true);
                                        player1Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.GONE);
                                        player2ServiceIcon.setVisibility(View.VISIBLE);

                                        shotHandInput.setTextColor(ContextCompat.getColor(getContext(),R.color.opponentColor));
                                        shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(),R.color.opponentColor));
                                    }
                                    else
                                    {
                                        player1Service.setChecked(false);
                                        player2Service.setChecked(false);

                                        player1ServiceIcon.setVisibility(View.GONE);
                                        player2ServiceIcon.setVisibility(View.GONE);
                                        toastMessage("Service is not been set!!");
                                    }
                                }
                            });
                            serviceDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            serviceDialog.show();
                        }
                    }

                    int adapterSize = recordShotAdapterList.size();
                    if(player1Service.isChecked())
                    {
                        if((adapterSize%2)==0)
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                        }
                        else
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                        }
                    }
                    else
                    {
                        if((adapterSize%2)==0)
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.opponentColor));
                        }
                        else
                        {
                            shotHandInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                            shotDestinationInput.setTextColor(ContextCompat.getColor(getContext(), R.color.playerColor));
                        }
                    }

                }


                else if(event.getAction() == MotionEvent.ACTION_DOWN && player2Spinner.getText().toString().length() == 0)
                {
                    toastMessage("Opponent name to be entered");
                }


                return false;
            }
        });

        shotHandInput.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                String validDestination = null;
                String validHand = null;
                String validHandName = "";


                if(recordShotAdapterList.size() == 0)
                    validHand = serviceMap.get(shotHandInput.getText().toString());
                else
                    validHand = strokeMap.get(shotHandInput.getText().toString());


                if(shotDestinationInput.getTag() != null )
                {
                    if(shotDestinationInput.getTag().toString().length() > 0)
                        validDestination = destinationMap.get(shotDestinationInput.getTag());
                }


                if(validHand != null)
                {
                    shotHandInput.setTag(shotHandInput.getText().toString());
                    shotHandInput.setText(validHand);
                    shotHandInput.setEnabled(false);
                }

                if(validDestination != null && validHand != null)
                {

                    Shot shotObject = new Shot();
                    int h = recordShotAdapterList.size();
                    if(player1Service.isChecked())
                    {
                        if((h%2)==0)
                            shotObject.setPlayerName(player1Spinner.getText().toString());
                        else
                            shotObject.setPlayerName(player2Spinner.getText().toString());
                    }
                    else
                    {
                        if((h%2)==0)
                            shotObject.setPlayerName(player2Spinner.getText().toString());
                        else
                            shotObject.setPlayerName(player1Spinner.getText().toString());
                    }

                    shotObject.setStrokeHandName(shotHandInput.getTag().toString());
                    shotObject.setStrokeDestinationName(shotDestinationInput.getTag().toString());
                    shotObject.setStrokeHand(validHand);
                    shotObject.setStrokeDestination(validDestination);
                    shotObject.setStatus("current");
                    shotObject.setPlayer1Name(player1Spinner.getText().toString());
                    shotObject.setPlayer2Name(player2Spinner.getText().toString());
                    if(recordShotAdapterList.size() >= 1)
                    {
                        recordShotAdapterList.get(0).setStatus("previous");
                        recordShotAdapterList.get(recordShotAdapterList.size()-1).setStatus("previous");
                    }
                    recordShotAdapterList.add(shotObject);
                    recordShotAdapter.notifyDataSetChanged();

                    shotHandInput.setText("");
                    shotDestinationInput.setText("");
                    shotHandInput.setTag("");
                    shotDestinationInput.setTag("");

                    //if(validDestination.equalsIgnoreCase("NET") || validDestination.equalsIgnoreCase("OUT") || validDestination.equalsIgnoreCase("MISSED") || validDestination.equalsIgnoreCase("BE"))
                    if(losingStrokes.indexOf(validDestination) > -1)
                    {
                        shotDestinationInput.setEnabled(false);
                        shotHandInput.setEnabled(false);

                    }
                    else
                    {
                        shotDestinationInput.setEnabled(true);
                        shotHandInput.setEnabled(true);
                    }

                }

            }
        });

        shotDestinationInput.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                String validDestination = null;
                String validHand = null;


                if(shotHandInput.getTag() != null )
                {
                    if(shotHandInput.getTag().toString().trim().length() > 0)
                    {
                        if(recordShotAdapterList.size() == 0)
                            validHand = serviceMap.get(shotHandInput.getTag().toString());
                        else
                            validHand = strokeMap.get(shotHandInput.getTag().toString());
                    }
                }


                validDestination = destinationMap.get(shotDestinationInput.getText().toString());

                if(validDestination != null)
                {
                    shotDestinationInput.setTag(shotDestinationInput.getText().toString());
                    shotDestinationInput.setText(validDestination);
                    shotDestinationInput.setEnabled(false);

                }
                if(validDestination != null && validHand != null)
                {
                    Shot shotObject = new Shot();
                    int h = recordShotAdapterList.size();
                    if(player1Service.isChecked())
                    {
                        if((h%2)==0)
                            shotObject.setPlayerName(player1Spinner.getText().toString());
                        else
                            shotObject.setPlayerName(player2Spinner.getText().toString());
                    }
                    else
                    {
                        if((h%2)==0)
                            shotObject.setPlayerName(player2Spinner.getText().toString());
                        else
                            shotObject.setPlayerName(player1Spinner.getText().toString());
                    }

                    shotObject.setStrokeHandName(shotHandInput.getTag().toString());
                    shotObject.setStrokeDestinationName(shotDestinationInput.getTag().toString());
                    shotObject.setStrokeHand(validHand);
                    shotObject.setStrokeDestination(validDestination);
                    shotObject.setStatus("current");
                    shotObject.setPlayer1Name(player1Spinner.getText().toString());
                    shotObject.setPlayer2Name(player2Spinner.getText().toString());
                    if(recordShotAdapterList.size() >= 1)
                    {
                        recordShotAdapterList.get(0).setStatus("previous");
                        recordShotAdapterList.get(recordShotAdapterList.size()-1).setStatus("previous");
                    }
                    recordShotAdapterList.add(shotObject);
                    recordShotAdapter.notifyDataSetChanged();
                    shotHandInput.setText("");
                    shotDestinationInput.setText("");
                    shotHandInput.setTag("");
                    shotDestinationInput.setTag("");

                    //if(validDestination.equalsIgnoreCase("NET") || validDestination.equalsIgnoreCase("OUT"))
                    //if(validDestination.equalsIgnoreCase("NET") || validDestination.equalsIgnoreCase("OUT") || validDestination.equalsIgnoreCase("MISSED") || validDestination.equalsIgnoreCase("BE"))
                    if(losingStrokes.indexOf(validDestination) > -1)
                    {
                        shotDestinationInput.setEnabled(false);
                        shotHandInput.setEnabled(false);
                    }
                    else
                    {
                        shotDestinationInput.setEnabled(true);
                        shotHandInput.setEnabled(true);
                    }


                }
            }
        });



        player1Spinner = (AutoCompleteTextView) view.findViewById(R.id.player1Spinner);
        player2Spinner = (AutoCompleteTextView) view.findViewById(R.id.player2Spinner);

        sequenceReset = (TextView) view. findViewById(R.id.sequenceReset);
        sequenceSettings = (ImageView) view.findViewById(R.id.sequenceSettings);
        sequenceSave = (TextView) view.findViewById(R.id.sequenceSave);


        player1Spinner.setText("");
        player2Spinner.setText("");
        player1Spinner.setTag("");
        player2Spinner.setTag("");

        /** progressBar related **/

        dataAdapter1 = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, playerJsonList);
        player1Spinner.setAdapter(dataAdapter1);
        player1Spinner.setThreshold(1);


        dataAdapter2 = new ArrayAdapter<PlayerJson>(getContext(),
                R.layout.header_spinner, playerJsonList);
        player2Spinner.setAdapter(dataAdapter2);
        player2Spinner.setThreshold(1);
        player2Spinner.requestFocus();





        try{

            if(mSession.ypGetPlayer1Name() == null)
            {
                player1Spinner.setText(mSession.ypGetUserName());
                player1Spinner.setTag(mSession.ypGetUserID());
            }
            else if(!mSession.ypGetPlayer1Name().equals(null))
            {
                player1Spinner.setText(mSession.ypGetPlayer1Name());
                player1Spinner.setTag(mSession.ypGetPlayer1ID());
            }


            if (mSession.ypGetPlayer2Name() != null) {
                if(!mSession.ypGetPlayer2Name().equals(null))
                {
                    player2Spinner.setText(mSession.ypGetPlayer2Name());
                    player2Spinner.setTag(mSession.ypGetPlayer2ID());
                }

            }

        }catch(Exception e)
        {
        }


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                //R.style.AlertDialogTheme)


                mTimePicker = new TimePickerDialog(getContext(),R.style.AlertDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        player1Spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                if(playerJsonList != null)
                {
                    player1Spinner.setTag(dataAdapter1.getItem(pos).getUserId());
                }


            }
        });

        player1Spinner.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(playerJsonList != null)
                {
                    int pos = playerJsonList.indexOf(new PlayerJson(s.toString()));
                    if(pos > -1)
                    {
                        player1Spinner.setTag(playerJsonList.get(pos).getUserId());
                    }
                    else
                        player1Spinner.setTag("");
                }




            }
        });










        //ArrayAdapter<String> dataAdapter2 = create ArrayAdapter<String>(getContext(),
        //R.layout.header_spinner, player2List);

        player2Spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                if(playerJsonList != null)
                {
                    player2Spinner.setTag(dataAdapter2.getItem(pos).getUserId());
                }

            }
        });

        player2Spinner.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(playerJsonList != null)
                {
                    int pos = playerJsonList.indexOf(new PlayerJson(s.toString()));
                    if(pos > -1)
                    {
                        player2Spinner.setTag(playerJsonList.get(pos).getUserId());
                    }
                    else
                        player2Spinner.setTag("");
                }


            }
        });





        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(player2Spinner.getWindowToken(), 0);









        sequenceReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if( mAlertDialog != null && mAlertDialog.isShowing() ) mAlertDialog.dismiss();


                LayoutInflater inflater = getLayoutInflater();
                View resetSeqLayout = inflater.inflate(R.layout.confirm_dialog, null);
                TextView dialogTitle = (TextView) resetSeqLayout.findViewById(R.id.dialogTitle);
                TextView dialogMessage = (TextView) resetSeqLayout.findViewById(R.id.dialogMessage);
                dialogTitle.setText("Confirm");
                dialogMessage.setText("Reset whole sequence ?");

                AlertDialog.Builder resetDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                //resetDialog.setTitle("Reset whole sequence ? ");
                resetDialog.setView(resetSeqLayout);

                resetDialog.setCancelable(true);
                resetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                resetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                                player1Spinner.setText(mSession.ypGetUserName());
                                player1Spinner.setTag(mSession.ypGetUserName());

                                player2Spinner.setText("");
                                player2Spinner.setTag("");
                                player1Service.setChecked(false);
                                player2Service.setChecked(false);
                                player1ServiceIcon.setVisibility(View.GONE);
                                player2ServiceIcon.setVisibility(View.GONE);

                                recordShotAdapterList.clear();
                                recordShotAdapter.notifyDataSetChanged();
                                //sequenceSave.setEnabled(true);
                                player1SetInput.setText("");
                                player1Points.setText("");
                                player2SetInput.setText("");
                                player2Points.setText("");
                                shotHandInput.setText("");
                                shotDestinationInput.setText("");
                                shotHandInput.setTag("");
                                shotDestinationInput.setTag("");
                                shotHandInput.setEnabled(true);
                                shotDestinationInput.setEnabled(true);




                    }
                });
                //resetDialog.show();

                mAlertDialog = resetDialog.create();
                mAlertDialog.show();

            }
        });
        sequenceSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                requestType="GET";
                queryparams="";
                methodType="playerProfile";

                params= "fetchPlayerProfile?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&player1Name="+player1Spinner.getText().toString()+"&player2Name="+player2Spinner.getText().toString()+"&player1Id="+player1Spinner.getTag().toString()+"&player2Id="+player2Spinner.getTag().toString();


                ExecuteURLTask asyncTask1 = new ExecuteURLTask(getContext(),methodType);
                asyncTask1.delegate = RecordAnalytics.this;
                try {
                    asyncTask1.execute(params,queryparams,requestType).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Context wrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenu);
                //Context wrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenu);
                //final PopupMenu menu1 = new PopupMenu(wrapper,sequenceSettings);
                final PopupMenu menu1 =new PopupMenu(wrapper, sequenceSettings);
                menu1.getMenuInflater().inflate(R.menu.sequence_menu,menu1.getMenu());
                menu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        sequenceMenu(item,sequenceSettings,menu1);

                        return true;
                    }
                });
                menu1.show();




                final PopupMenu menu = new PopupMenu(getContext(), sequenceSettings);
                menu.getMenu().add(Html.fromHtml("<font color='#434343'>Destination Format ></font>"));
                menu.getMenu().add(Html.fromHtml("<font color='#434343'>Player Settings ></font>"));
                menu.getMenu().add(Html.fromHtml("<font color='#434343'>Match Date</font>"));
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Destination Format >"))
                        {
                            Context wrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenu);
                            //final PopupMenu submenu = new PopupMenu(wrapper,sequenceSettings);

                            final PopupMenu submenu = new PopupMenu(getContext(), sequenceSettings);
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>6 Point</font>"));
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>8 Point</font>"));
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>9 Point</font>"));
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>14 Point</font>"));
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>< Back to Menu</font>"));
                            submenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if(item.getTitle().toString().equalsIgnoreCase("< Back to Menu"))
                                    {
                                        submenu.dismiss();
                                        menu.show();
                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase("6 Point"))
                                    {
                                        mSession.ypStoreSessionDestinationPoint("6 Point");
                                        if (p6DestinationKeysJsonArr.size() > 0)
                                        {
                                            destinationMap.clear();
                                            destinationKeyMap.clear();
                                            destinationArrayList.clear();
                                            for (int i = 0; i < p6DestinationKeysJsonArr.size(); i++)
                                            {
                                                JSONObject obj1 = (JSONObject) p6DestinationKeysJsonArr.get(i);
                                                String destinationName = obj1.get("destinationName").toString();
                                                String destinationCode = obj1.get("destinationShortCode").toString();
                                                destinationMap.put(destinationName, destinationCode);
                                                destinationKeyMap.put(destinationCode,destinationName);
                                                destinationArrayList.add(destinationName);
                                            }
                                            ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                                                    R.layout.shot_spinner_item, destinationArrayList);
                                            shotDestinationInput.setAdapter(destinationAdapter);
                                        }
                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase("8 Point"))
                                    {
                                        mSession.ypStoreSessionDestinationPoint("8 Point");

                                        if (p8DestinationKeysJsonArr.size() > 0)
                                        {
                                            destinationMap.clear();
                                            destinationKeyMap.clear();
                                            destinationArrayList.clear();
                                            for (int i = 0; i < p8DestinationKeysJsonArr.size(); i++)
                                            {
                                                JSONObject obj1 = (JSONObject) p8DestinationKeysJsonArr.get(i);
                                                String destinationName = obj1.get("destinationName").toString();
                                                String destinationCode = obj1.get("destinationShortCode").toString();
                                                destinationMap.put(destinationName, destinationCode);
                                                destinationKeyMap.put(destinationCode,destinationName);
                                                destinationArrayList.add(destinationName);
                                            }
                                            ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                                                    R.layout.shot_spinner_item, destinationArrayList);
                                            shotDestinationInput.setAdapter(destinationAdapter);
                                        }
                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase("9 Point"))
                                    {
                                        mSession.ypStoreSessionDestinationPoint("9 Point");

                                        if (p9DestinationKeysJsonArr.size() > 0)
                                        {
                                            destinationMap.clear();
                                            destinationKeyMap.clear();
                                            destinationArrayList.clear();
                                            for (int i = 0; i < p9DestinationKeysJsonArr.size(); i++)
                                            {
                                                JSONObject obj1 = (JSONObject) p9DestinationKeysJsonArr.get(i);
                                                String destinationName = obj1.get("destinationName").toString();
                                                String destinationCode = obj1.get("destinationShortCode").toString();
                                                destinationMap.put(destinationName, destinationCode);
                                                destinationKeyMap.put(destinationCode,destinationName);
                                                destinationArrayList.add(destinationName);
                                            }
                                            ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                                                    R.layout.shot_spinner_item, destinationArrayList);
                                            shotDestinationInput.setAdapter(destinationAdapter);
                                        }
                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase("14 Point"))
                                    {
                                        mSession.ypStoreSessionDestinationPoint("14 Point");

                                        if (p14DestinationKeysJsonArr.size() > 0)
                                        {
                                            destinationMap.clear();
                                            destinationKeyMap.clear();
                                            destinationArrayList.clear();
                                            for (int i = 0; i < p14DestinationKeysJsonArr.size(); i++)
                                            {
                                                JSONObject obj1 = (JSONObject) p14DestinationKeysJsonArr.get(i);
                                                String destinationName = obj1.get("destinationName").toString();
                                                String destinationCode = obj1.get("destinationShortCode").toString();
                                                destinationMap.put(destinationName, destinationCode);
                                                destinationKeyMap.put(destinationCode,destinationName);
                                                destinationArrayList.add(destinationName);
                                            }
                                            ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                                                    R.layout.shot_spinner_item, destinationArrayList);
                                            shotDestinationInput.setAdapter(destinationAdapter);
                                        }
                                    }
                                    return true;
                                }


                            });
                            submenu.show();

                        }
                        if(item.getTitle().toString().equalsIgnoreCase("Player Settings >"))
                        {
                            Context wrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenu);
                            final PopupMenu submenu1 = new PopupMenu(wrapper,sequenceSettings);

                            final PopupMenu submenu = new PopupMenu(getContext(), sequenceSettings);
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>"+player1Spinner.getText().toString()+"</font>"));
                            if(player2Spinner.getText().toString().length() > 0)
                                submenu.getMenu().add(Html.fromHtml("<font color='#434343'>"+player2Spinner.getText().toString()+"</font>"));
                            submenu.getMenu().add(Html.fromHtml("<font color='#434343'>Back to Menu</font>"));
                            submenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if(item.getTitle().toString().equalsIgnoreCase("< Back to Menu"))
                                    {
                                        submenu.dismiss();
                                        menu.show();
                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase(player1Spinner.getText().toString()))
                                    {
                                        popupPlayer = player1Spinner.getText().toString();
                                        final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View profileSettingsLayout = inflater.inflate(R.layout.player1profile, null);
                                        TextView playerNameValue = (TextView) profileSettingsLayout.findViewById(R.id.playerNameValue);
                                        playerNameValue.setText(popupPlayer);

                                        final ArrayList player1BHArrList = new ArrayList();
                                        final ArrayList player1FHArrList = new ArrayList();

                                        foreHandListView = (ListView) profileSettingsLayout.findViewById(R.id.foreHandListView);
                                        backHandListView = (ListView) profileSettingsLayout.findViewById(R.id.backHandListView);

                                        foreHandListView.setAdapter(player1FHAdapter);
                                        backHandListView.setAdapter(player1BHAdapter);

                                        foreHandListView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                foreHandListView.smoothScrollToPosition(0);
                                            }
                                        });

                                        backHandListView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                backHandListView.smoothScrollToPosition(0);
                                            }
                                        });

                                        final Spinner playerHand = (Spinner) profileSettingsLayout.findViewById(R.id.playerHand);
                                        ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(getContext(),
                                                R.layout.footer_spinner, new ArrayList( Arrays.asList(getResources().getStringArray(R.array.playerHand))));
                                        playerHand.setAdapter(playerHandOption);
                                        if(player1Hand.toString().length()> 0)
                                        {
                                            for (int i=0;i<playerHand.getCount();i++)
                                            {
                                                if (playerHand.getItemAtPosition(i).toString().equalsIgnoreCase(player1Hand))
                                                {
                                                    playerHand.setSelection(i);
                                                    break;
                                                }
                                            }
                                        }


                                        playerHand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                player1Hand = playerHand.getSelectedItem().toString();
                                                player1ProfileSettings = true;

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> arg0) {

                                            }
                                        });


                                        final EditText foreHandRT = (EditText) profileSettingsLayout.findViewById(R.id.foreHandRT);
                                        final EditText foreHandDate = (EditText) profileSettingsLayout.findViewById(R.id.foreHandDate);
                                        ImageView foreHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.foreHandAdd);

                                        final EditText backHandRT = (EditText) profileSettingsLayout.findViewById(R.id.backHandRT);
                                        final EditText backHandDate = (EditText) profileSettingsLayout.findViewById(R.id.backHandDate);
                                        ImageView backHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.backHandAdd);

                                        foreHandAdd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {

                                                String foreHandRTInput = foreHandRT.getText().toString();
                                                String foreHandDateInput = foreHandDate.getText().toString();
                                                if(foreHandRTInput.length() > 0 && foreHandDateInput.length() > 0)
                                                {
                                                    if(foreHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                                    {
                                                        Rubber rubberObj = new Rubber();
                                                        rubberObj.setRubberType(foreHandRTInput);
                                                        rubberObj.setRubberDate(foreHandDateInput);
                                                        player1FH.add(rubberObj);
                                                        Collections.reverse(player1FH);
                                                        player1ProfileSettings = true;
                                                        player1FHAdapter.notifyDataSetChanged();
                                                        foreHandListView.setSelection(player1FH.size()-1);

                                                        foreHandRT.setText("");
                                                        foreHandDate.setText("");
                                                    }
                                                    else
                                                        foreHandDate.setError("Please enter date in a valid format dd/mm/yy");
                                                }
                                            }
                                        });

                                        backHandAdd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {

                                                String backHandRTInput = backHandRT.getText().toString();
                                                String backHandDateInput = backHandDate.getText().toString();
                                                if(backHandRTInput.length() > 0 && backHandDateInput.length() > 0)
                                                {
                                                    if(backHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                                    {
                                                        Rubber rubberObj = new Rubber();
                                                        rubberObj.setRubberType(backHandRTInput);
                                                        rubberObj.setRubberDate(backHandDateInput);
                                                        player1BH.add(rubberObj);
                                                        Collections.reverse(player1BH);
                                                        player1ProfileSettings = true;

                                                        player1BHAdapter.notifyDataSetChanged();
                                                        backHandListView.setSelection(player1BH.size()-1);

                                                        foreHandRT.setText("");
                                                        backHandDate.setText("");
                                                    }
                                                    else
                                                        backHandDate.setError("Please enter date in a valid format dd/mm/yy");
                                                }
                                            }
                                        });


                                        profileDialog.setCancelable(false);

                                        profileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });


                                        profileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if(player1ProfileSettings)
                                                {
                                                    for(int k= 0; k<player1FH.size();k++)
                                                    {
                                                        JSONObject obj1 = new JSONObject();
                                                        obj1.put("rubberDate",player1FH.get(k).getRubberDate());
                                                        obj1.put("rubberType", player1FH.get(k).getRubberType());
                                                        player1FHArrList.add(obj1);
                                                    }

                                                    for(int k= 0; k<player1BH.size();k++)
                                                    {
                                                        JSONObject obj1 = new JSONObject();
                                                        obj1.put("rubberDate",player1BH.get(k).getRubberDate());
                                                        obj1.put("rubberType", player1BH.get(k).getRubberType());
                                                        player1BHArrList.add(obj1);
                                                    }


                                                    JSONObject obj = new JSONObject();
                                                    obj.put("playerName", player1Spinner.getText().toString());
                                                    obj.put("playerId", player1Spinner.getTag().toString());
                                                    obj.put("playerHand",player1Hand);
                                                    obj.put("playerFH", player1FHArrList);
                                                    obj.put("playerBH", player1BHArrList);


                                                    requestType = "POST";
                                                    params= "recordPlayerProfile?";
                                                    methodType="recordPlayerProfile";
                                                    queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerName="+player1Spinner.getText().toString()+"&playerProfile="+obj;

                                                    asyncTask = new ExecuteURLTask(getContext(),methodType);
                                                    asyncTask.delegate = RecordAnalytics.this;
                                                    asyncTask.execute(params,queryparams,requestType);
                                                }
                                                dialog.cancel();


                                            }
                                        });
                                        profileDialog.setView(profileSettingsLayout);
                                        profileDialog.show();


                                    }
                                    else if(item.getTitle().toString().equalsIgnoreCase(player2Spinner.getText().toString()))
                                    {
                                        popupPlayer = player2Spinner.getText().toString();
                                        final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View profileSettingsLayout = inflater.inflate(R.layout.player2profile, null);
                                        TextView playerNameValue = (TextView) profileSettingsLayout.findViewById(R.id.playerNameValue);
                                        playerNameValue.setText(popupPlayer);
                                        final ArrayList player2BHArrList = new ArrayList();
                                        final ArrayList player2FHArrList = new ArrayList();


                                        foreHandListView = (ListView) profileSettingsLayout.findViewById(R.id.foreHandListView);
                                        backHandListView = (ListView) profileSettingsLayout.findViewById(R.id.backHandListView);

                                        foreHandListView.setAdapter(player2FHAdapter);
                                        backHandListView.setAdapter(player2BHAdapter);

                                        final Spinner playerHand = (Spinner) profileSettingsLayout.findViewById(R.id.playerHand);
                                        ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(getContext(),
                                                R.layout.footer_spinner, new ArrayList( Arrays.asList(getResources().getStringArray(R.array.playerHand))));
                                        playerHand.setAdapter(playerHandOption);

                                        if(player2Hand.toString().length()> 0)
                                        {
                                            for (int i=0;i<playerHand.getCount();i++)
                                            {
                                                if (playerHand.getItemAtPosition(i).toString().equalsIgnoreCase(player2Hand))
                                                {
                                                    playerHand.setSelection(i);
                                                    break;
                                                }
                                            }
                                        }
                                        playerHand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                player2Hand = playerHand.getSelectedItem().toString();
                                                player2ProfileSettings = true;

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> arg0) {

                                            }
                                        });

                                        final EditText foreHandRT = (EditText) profileSettingsLayout.findViewById(R.id.foreHandRT);
                                        final EditText foreHandDate = (EditText) profileSettingsLayout.findViewById(R.id.foreHandDate);
                                        ImageView foreHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.foreHandAdd);

                                        final EditText backHandRT = (EditText) profileSettingsLayout.findViewById(R.id.backHandRT);
                                        final EditText backHandDate = (EditText) profileSettingsLayout.findViewById(R.id.backHandDate);
                                        ImageView backHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.backHandAdd);

                                        foreHandAdd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {

                                                String foreHandRTInput = foreHandRT.getText().toString();
                                                String foreHandDateInput = foreHandDate.getText().toString();
                                                if(foreHandRTInput.length() > 0 && foreHandDateInput.length() > 0)
                                                {
                                                    if(foreHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                                    {
                                                        Rubber rubberObj = new Rubber();
                                                        rubberObj.setRubberType(foreHandRTInput);
                                                        rubberObj.setRubberDate(foreHandDateInput);
                                                        player2FH.add(rubberObj);
                                                        Collections.reverse(player2FH);
                                                        player2ProfileSettings = true;
                                                        player2FHAdapter.notifyDataSetChanged();
                                                        foreHandListView.setSelection(player2FH.size()-1);
                                                        foreHandRT.setText("");
                                                        foreHandDate.setText("");
                                                    }
                                                    else
                                                        foreHandDate.setError("Please enter date in a valid format dd/mm/yy");
                                                }
                                            }
                                        });

                                        backHandAdd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {

                                                String backHandRTInput = backHandRT.getText().toString();
                                                String backHandDateInput = backHandDate.getText().toString();
                                                if(backHandRTInput.length() > 0 && backHandDateInput.length() > 0)
                                                {
                                                    if(backHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                                    {
                                                        Rubber rubberObj = new Rubber();
                                                        rubberObj.setRubberType(backHandRTInput);
                                                        rubberObj.setRubberDate(backHandDateInput);
                                                        player2BH.add(rubberObj);
                                                        Collections.reverse(player2BH);
                                                        player2ProfileSettings = true;
                                                        player2BHAdapter.notifyDataSetChanged();
                                                        backHandListView.setSelection(player2BH.size()-1);

                                                        foreHandRT.setText("");
                                                        backHandDate.setText("");
                                                    }
                                                    else
                                                        backHandDate.setError("Please enter date in a valid format dd/mm/yy");
                                                }
                                            }
                                        });


                                        profileDialog.setCancelable(false);

                                        profileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });


                                        profileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if(player2ProfileSettings)
                                                {
                                                    for(int k= 0; k<player2FH.size();k++)
                                                    {
                                                        JSONObject obj1 = new JSONObject();
                                                        obj1.put("rubberDate",player2FH.get(k).getRubberDate());
                                                        obj1.put("rubberType", player2FH.get(k).getRubberType());
                                                        player2FHArrList.add(obj1);

                                                    }

                                                    for(int k= 0; k<player2BH.size();k++)
                                                    {
                                                        JSONObject obj1 = new JSONObject();
                                                        obj1.put("rubberDate",player2BH.get(k).getRubberDate());
                                                        obj1.put("rubberType", player2BH.get(k).getRubberType());
                                                        player2BHArrList.add(obj1);


                                                    }


                                                    JSONObject obj = new JSONObject();
                                                    obj.put("playerName", player2Spinner.getText().toString());
                                                    obj.put("playerId", player2Spinner.getTag().toString());
                                                    obj.put("playerHand",player2Hand);
                                                    obj.put("playerFH", player2FHArrList);
                                                    obj.put("playerBH", player2BHArrList);


                                                    requestType = "POST";
                                                    params= "recordPlayerProfile?";
                                                    methodType="recordPlayerProfile";
                                                    queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerName="+player2Spinner.getText().toString()+"&playerProfile="+obj;

                                                    asyncTask = new ExecuteURLTask(getContext(),methodType);
                                                    asyncTask.delegate = RecordAnalytics.this;
                                                    asyncTask.execute(params,queryparams,requestType);
                                                }


                                                dialog.cancel();
                                            }
                                        });
                                        profileDialog.setView(profileSettingsLayout);
                                        profileDialog.show();


                                    }

                                    return true;
                                }


                            });
                            submenu.show();

                        }
                        else if(item.getTitle().toString().equalsIgnoreCase("Match Date"))
                        {
                            try{
                                final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                LayoutInflater inflater = getLayoutInflater();
                                View matchDateLayout = inflater.inflate(R.layout.match_date, null);
                                final EditText currentMatchDate = (EditText) matchDateLayout.findViewById(R.id.matchDate);
                                if(mSession.ypGetMatchDate() != null)
                                {
                                    currentMatchDate.setText(mSession.ypGetMatchDate());
                                }
                                else
                                {
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                                    Date date = new Date();
                                    String currentDate = dateFormat.format(date);
                                    currentMatchDate.setText(currentDate.toString());

                                }
                                profileDialog.setCancelable(false);
                                profileDialog.setPositiveButton("ok", null);
                                profileDialog.setNegativeButton("Cancel", null);

                                profileDialog.setView(matchDateLayout);

                                final AlertDialog mAlertDialog = profileDialog.create();
                                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                                    @Override
                                    public void onShow(DialogInterface dialog) {

                                        Button cancelBtn = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mAlertDialog.dismiss();

                                            }
                                        });
                                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                        b.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {
                                                if(currentMatchDate.getText().toString().length() > 0)
                                                {
                                                    if(currentMatchDate.getText().toString().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                                    {
                                                        matchDate = currentMatchDate.getText().toString();
                                                        mSession.ypStoreSessionMatchDate(matchDate);
                                                        mAlertDialog.dismiss();

                                                    }
                                                    else
                                                    {
                                                        currentMatchDate.setError("Please enter date in a valid format dd/mm/yy");

                                                    }
                                                }
                                                else
                                                {
                                                    currentMatchDate.setError("Please enter date in a valid format dd/mm/yy");

                                                    //mAlertDialog.dismiss();
                                                }
                                            }
                                        });
                                    }
                                });
                                mAlertDialog.show();



                            }catch(Exception e)
                            {
                            }

                        }


                        return true;
                    }


                });

                //menu.show();










            }
        });

        sequenceSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(recordShotAdapterList.size() > 0 && recordShotAdapterList.get(0).getStrokeHand().toString().length() > 0 && recordShotAdapterList.get(0).getStrokeDestination().toString().length() > 0)
                {
                    if(player2Spinner.getText().toString().length() > 0 && player1Spinner.getText().toString().length() > 0)
                    {
                        recordSequence();
                    }
                    else
                    {
                        if(player2Spinner.getText().toString().length() == 0)
                        {
                            recordSequence();
                        }
                    }
                }
                else
                {
                    toastMessage("Strokes are empty !!");
                }
            }
        });




        return view;
    }


    public void recordSequence()
    {

        sequenceSave.setEnabled(false);
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);

        LayoutInflater inflater = getLayoutInflater();
        View recordSequenceLayout = inflater.inflate(R.layout.record_sequence_dialog, null);
        //TextView recordsequenceTitle = (TextView) recordSequenceLayout.findViewById(R.id.recordsequenceTitle);
        //recordsequenceTitle.setText("Save sequence");

        LinearLayout seqWinLayout = (LinearLayout) recordSequenceLayout.findViewById(R.id.seqWinLayout);


        String player1Name = player1Spinner.getText().toString();
        String player2Name = player2Spinner.getText().toString();

        Shot shot = recordShotAdapterList.get(recordShotAdapterList.size()-1);
        //if(shot.getStrokeDestinationName().equalsIgnoreCase("Net") || shot.getStrokeDestinationName().equalsIgnoreCase("Out"))
        //if(shot.getStrokeDestinationName().equalsIgnoreCase("NET") || shot.getStrokeDestinationName().equalsIgnoreCase("OUT") || shot.getStrokeDestinationName().equalsIgnoreCase("MISSED") || shot.getStrokeDestinationName().equalsIgnoreCase("BE"))


        if(losingStrokes.indexOf(shot.getStrokeDestination()) > -1)
        {

            if(recordShotAdapterList.size() > 1)
            {
                Shot newshot = recordShotAdapterList.get(recordShotAdapterList.size()-2);
                winner = newshot.getPlayerName();

            }
            else if(recordShotAdapterList.size() == 1)
            {
                if(player1Service.isChecked())
                {
                    winner = player2Spinner.getText().toString();

                }
                else
                {
                    winner = player1Spinner.getText().toString();

                }
            }

        }
        else
        {
            winner = shot.getPlayerName();
        }

        TextView winnerInfo = (TextView) recordSequenceLayout.findViewById(R.id.winnerInfo);

        if(completeSequence.isChecked())
        {
            seqWinLayout.setVisibility(View.VISIBLE);
            winnerInfo.setText(winner);

        }
        saveDialog.setCancelable(false);
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sequenceSave.setEnabled(true);

                dialog.cancel();
            }
        });

        saveDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean valid = true;



                String player1Name = player1Spinner.getText().toString();
                String player2Name = player2Spinner.getText().toString();
                String serviceBy = "";


                if(player1Service.isChecked())
                    serviceBy = player1Name;
                else if(player2Service.isChecked())
                    serviceBy = player2Name;



                ArrayList strokesPlayed = new ArrayList();
                List<String> player1Strokes = new ArrayList<String>();
                List<String> player2Strokes = new ArrayList<String>();
                List<String> combinedStrokes = new ArrayList<String>();

                JSONObject obj = new JSONObject();
                obj.put("player1Name",player1Name);
                obj.put("player1ID", player1Spinner.getTag().toString());
                obj.put("player2Name",player2Name);
                obj.put("player2ID", player2Spinner.getTag().toString());
                obj.put("serviceBy", serviceBy.trim());
                obj.put("matchDate",mSession.ypGetMatchDate());
                obj.put("matchTitle",matchTitle.getText().toString());
                obj.put("startTime",startTime.getText().toString());

                JSONObject playerASet = new JSONObject();
                JSONObject playerBSet = new JSONObject();
                playerASet.put("set",player1SetInput.getText().toString());
                playerASet.put("points",player1Points.getText().toString());
                playerBSet.put("set",player2SetInput.getText().toString());
                playerBSet.put("points",player2Points.getText().toString());
                obj.put("playerA", playerASet);
                obj.put("playerB", playerBSet);
                if(completeSequence.isChecked())
                    obj.put("rallyType","complete");
                else
                    obj.put("rallyType","partial");



                HashMap<String,String> hm = new HashMap<String,String>();
                HashMap<String,String> p2AnalyticsSummary = new HashMap<String,String>();
                if(recordShotAdapterList.size() > 0)
                {
                    obj.put("serviceHand", recordShotAdapterList.get(0).getStrokeHand().trim());
                    obj.put("serviceDestination", recordShotAdapterList.get(0).getStrokeDestination().trim());
                    obj.put("serviceKey", recordShotAdapterList.get(0).getStrokeHand().trim()+"-"+recordShotAdapterList.get(0).getStrokeDestination().trim());


                    for(int h=0; h< recordShotAdapterList.size();h++)
                    {
                        if(h == 2)
                            obj.put("thirdBall",recordShotAdapterList.get(2).getStrokeHand().trim());
                        if(h == 3)
                            obj.put("fourthBall",recordShotAdapterList.get(3).getStrokeHand().trim());

                        String strokeHand = recordShotAdapterList.get(h).getStrokeHand().trim();
                        String strokeDestination = recordShotAdapterList.get(h).getStrokeDestination().trim();
                        String strokeHandName = recordShotAdapterList.get(h).getStrokeHandName().trim();
                        String strokeDestinationName = recordShotAdapterList.get(h).getStrokeDestinationName().trim();
                        String strokeHandValid = null;
                        if(h == 0)
                            strokeHandValid = serviceMap.get(strokeHandName);
                        else
                            strokeHandValid = strokeMap.get(strokeHandName);

                        String strokeDestinationValid = destinationMap.get(strokeDestinationName);

                        if(strokeHandValid == null || strokeDestinationValid == null)
                            valid = false;

                        if(strokeHand.length() > 0 && strokeDestination.length() > 0)
                        {
                            if(player1Service.isChecked())
                            {
                                if((h%2)==0)
                                {
                                    recordShotAdapterList.get(h).setPlayerName(player1Spinner.getText().toString());
                                }
                                else
                                {
                                    recordShotAdapterList.get(h).setPlayerName(player2Spinner.getText().toString());
                                }

                            }
                            else
                            {
                                if((h%2)==0)
                                {
                                    recordShotAdapterList.get(h).setPlayerName(player2Spinner.getText().toString());
                                }
                                else
                                {
                                    recordShotAdapterList.get(h).setPlayerName(player1Spinner.getText().toString());
                                }
                            }

                            JSONObject obj1 = new JSONObject();
							/* if(player1Service.isChecked())
							{
								if((h%2)==0)
								{
									obj1.put("strokePlayed", "1");
								}
								else
								{
									obj1.put("strokePlayed", "2");
								}
							}
							else
							{
								if((h%2)==0)
								{
									obj1.put("strokePlayed", "2");
								}
								else
								{
									obj1.put("strokePlayed", "1");
								}
							}*/
                            obj1.put("strokePlayed",recordShotAdapterList.get(h).getPlayerName().trim());
                            obj1.put("strokeHand", strokeHand);
                            obj1.put("strokeDestination",strokeDestination);
                            if(h == 0)
                                obj1.put("previousDestination", "");
                            else
                                obj1.put("previousDestination",recordShotAdapterList.get(h-1).getStrokeDestination().trim());
                            combinedStrokes.add(strokeHand+"-"+strokeDestination);

                            if(recordShotAdapterList.get(h).getPlayerName().trim().equalsIgnoreCase(player1Name.trim()))
                            {
                                String keyString = strokeHand+"-"+strokeDestination;
                                if( h != 0)
                                    player1Strokes.add(strokeHand+"-"+strokeDestination);
                                if(hm.containsKey(keyString))
                                {
                                    String valueString = hm.get(keyString);
                                    Integer temp = Integer.parseInt(valueString) + 1;
                                    hm.put(keyString, temp.toString());
                                }
                                else
                                {
                                    hm.put(keyString, "1");
                                }
                                obj.put("p1lastShot",strokeHand+"-"+strokeDestination);

                            }
                            else
                            {
                                String keyString = strokeHand+"-"+strokeDestination;
                                if( h != 0)
                                    player2Strokes.add(strokeHand+"-"+strokeDestination);
                                if(p2AnalyticsSummary.containsKey(keyString))
                                {
                                    String valueString = p2AnalyticsSummary.get(keyString);
                                    Integer temp = Integer.parseInt(valueString) + 1;
                                    p2AnalyticsSummary.put(keyString, temp.toString());
                                }
                                else
                                {
                                    p2AnalyticsSummary.put(keyString, "1");
                                }

                                obj.put("p2lastShot",strokeHand+"-"+strokeDestination);

                            }
                            obj.put("lastShot", strokeHand+"-"+strokeDestination);
                            //obj.put("lastShotHand",strokeHand);
                            //obj.put("lastShotDestination",strokeDestination);
                            //obj.put("lastShotBy",recordShotAdapterList.get(h).getPlayerName().trim());



                            strokesPlayed.add(obj1);

                        }
                    }
                }

                Shot shot = recordShotAdapterList.get(recordShotAdapterList.size()-1);

                obj.put("winner", winner.trim());
                obj.put("playedStrokes",hm);
                obj.put("p2AnalyticsSummary", p2AnalyticsSummary);
                obj.put("strokesPlayed",strokesPlayed);
                obj.put("player1Strokes", player1Strokes);
                obj.put("player2Strokes", player2Strokes);
                obj.put("combinedStrokes",combinedStrokes);

                if(valid)
                {
                    mSession.ypStoreSessionPlayer1Name(player1Spinner.getText().toString());
                    mSession.ypStoreSessionPlayer1ID(player1Spinner.getTag().toString());
                    mSession.ypStoreSessionPlayer2Name(player2Spinner.getText().toString());
                    mSession.ypStoreSessionPlayer2ID(player2Spinner.getTag().toString());

                    requestType = "POST";
                    params= "recordPlayerSequence?";
                    methodType="recordPlayerSequence";
                    queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerSequence="+obj;

                    System.out.println("queryParams .. "+queryparams);
                    asyncTask = new ExecuteURLTask(getContext(),methodType);
                    asyncTask.delegate = RecordAnalytics.this;
                    try {
                        String result = asyncTask.execute(params,queryparams,requestType).get();
                        sequenceSave.setEnabled(true);
                        if(result != null)
                        {
                            sequenceSave.setEnabled(true);

                            if(result.contains("sequence recorded"))
                            {
                                toastMessage("Sequence Recorded");






                                        player1Spinner.setText(mSession.ypGetPlayer1Name());
                                        player1Spinner.setTag(mSession.ypGetPlayer1ID());
                                        player2Spinner.setText(mSession.ypGetPlayer2Name());
                                        player2Spinner.setTag(mSession.ypGetPlayer2ID());
                                        player1Service.setChecked(false);
                                        player2Service.setChecked(false);
                                        player1ServiceIcon.setVisibility(View.GONE);
                                        player2ServiceIcon.setVisibility(View.GONE);

                                        recordShotAdapterList.clear();
                                        recordShotAdapter.notifyDataSetChanged();
                                        player1SetInput.setText("");
                                        player1Points.setText("");
                                        player2SetInput.setText("");
                                        player2Points.setText("");
                                        //sequenceSave.setEnabled(true);

                                        shotDeleteInput.post(new Runnable(){
                                            @Override
                                            public void run() {
                                                shotDeleteInput.performClick();

                                            }});


                            }
                            else
                            {
                                //sequenceSave.setEnabled(true);
                                toastMessage("Could not record sequence");
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    dialog.cancel();
                    toastMessage("Invalid strokes");
                    sequenceSave.setEnabled(true);


                }


            }
        });
        saveDialog.setView(recordSequenceLayout);
        saveDialog.show();
    }

    private void sequenceMenu(MenuItem item, ImageView sequenceSettings, final PopupMenu menu) {

        if(item.getTitle().toString().equalsIgnoreCase("Back to Menu"))
        {
		    /*
		    Toast.makeText(getApplicationContext(),item.getTitle().toString(),Toast.LENGTH_SHORT).show();
		    System.out.println("submneu .. "+menu.getMenu().findItem(R.id.menu1).getSubMenu());
		    menu.getMenu().findItem(R.id.menu1).getSubMenu().setGroupVisible(R.id.group1,false);
			menu.getMenu().findItem(R.id.menu2).getSubMenu().setGroupVisible(R.id.group2,false);
            menu.getMenu().findItem(R.id.menu3).setVisible(true);
            */

            //menu.show();
//			menu.getMenu().findItem(R.id.menu1).setVisible(true);

			/*SubMenu sub1 = menu.getMenu().findItem(R.id.menu1).getSubMenu();
			sub1.clearHeader();

			SubMenu sub = menu.getMenu().findItem(R.id.menu2).getSubMenu();
			sub.clearHeader();
*/
            //menu.show();

        }
        else if(item.getTitle().toString().equalsIgnoreCase("6 Point"))
        {
            Toast.makeText(getContext(),item.getTitle().toString(),Toast.LENGTH_SHORT).show();
            mSession.ypStoreSessionDestinationPoint("6 Point");
            if (p6DestinationKeysJsonArr.size() > 0)
            {
                destinationMap.clear();
                destinationKeyMap.clear();
                destinationArrayList.clear();
                for (int i = 0; i < p6DestinationKeysJsonArr.size(); i++)
                {
                    JSONObject obj1 = (JSONObject) p6DestinationKeysJsonArr.get(i);
                    String destinationName = obj1.get("destinationName").toString();
                    String destinationCode = obj1.get("destinationShortCode").toString();
                    destinationMap.put(destinationName, destinationCode);
                    destinationKeyMap.put(destinationCode,destinationName);
                    destinationArrayList.add(destinationName);
                }
                ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.shot_spinner_item, destinationArrayList);
                shotDestinationInput.setAdapter(destinationAdapter);
            }
        }
        else if(item.getTitle().toString().equalsIgnoreCase("8 Point"))
        {
            mSession.ypStoreSessionDestinationPoint("8 Point");

            if (p8DestinationKeysJsonArr.size() > 0)
            {
                destinationMap.clear();
                destinationKeyMap.clear();
                destinationArrayList.clear();
                for (int i = 0; i < p8DestinationKeysJsonArr.size(); i++)
                {
                    JSONObject obj1 = (JSONObject) p8DestinationKeysJsonArr.get(i);
                    String destinationName = obj1.get("destinationName").toString();
                    String destinationCode = obj1.get("destinationShortCode").toString();
                    destinationMap.put(destinationName, destinationCode);
                    destinationKeyMap.put(destinationCode,destinationName);
                    destinationArrayList.add(destinationName);
                }
                ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.shot_spinner_item, destinationArrayList);
                shotDestinationInput.setAdapter(destinationAdapter);
            }
        }
        else if(item.getTitle().toString().equalsIgnoreCase("9 Point"))
        {
            mSession.ypStoreSessionDestinationPoint("9 Point");

            if (p9DestinationKeysJsonArr.size() > 0)
            {
                destinationMap.clear();
                destinationKeyMap.clear();
                destinationArrayList.clear();
                for (int i = 0; i < p9DestinationKeysJsonArr.size(); i++)
                {
                    JSONObject obj1 = (JSONObject) p9DestinationKeysJsonArr.get(i);
                    String destinationName = obj1.get("destinationName").toString();
                    String destinationCode = obj1.get("destinationShortCode").toString();
                    destinationMap.put(destinationName, destinationCode);
                    destinationKeyMap.put(destinationCode,destinationName);
                    destinationArrayList.add(destinationName);
                }
                ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.shot_spinner_item, destinationArrayList);
                shotDestinationInput.setAdapter(destinationAdapter);
            }
        }
        else if(item.getTitle().toString().equalsIgnoreCase("14 Point"))
        {
            mSession.ypStoreSessionDestinationPoint("14 Point");

            if (p14DestinationKeysJsonArr.size() > 0)
            {
                destinationMap.clear();
                destinationKeyMap.clear();
                destinationArrayList.clear();
                for (int i = 0; i < p14DestinationKeysJsonArr.size(); i++)
                {
                    JSONObject obj1 = (JSONObject) p14DestinationKeysJsonArr.get(i);
                    String destinationName = obj1.get("destinationName").toString();
                    String destinationCode = obj1.get("destinationShortCode").toString();
                    destinationMap.put(destinationName, destinationCode);
                    destinationKeyMap.put(destinationCode,destinationName);
                    destinationArrayList.add(destinationName);
                }
                ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getContext(),
                        R.layout.shot_spinner_item, destinationArrayList);
                shotDestinationInput.setAdapter(destinationAdapter);
            }
        }
        else if(item.getTitle().toString().equalsIgnoreCase("Player Settings"))
        {
            item.getSubMenu().getItem(0).setTitle(player1Spinner.getText().toString());
            if(player2Spinner.getText().toString().length() > 0)
            {
                item.getSubMenu().getItem(1).setVisible(true);
                item.getSubMenu().getItem(1).setTitle(player2Spinner.getText().toString());
            }
            else
                item.getSubMenu().getItem(1).setVisible(false);
        }
        else if(item.getTitle().toString().equalsIgnoreCase(player1Spinner.getText().toString()))
        {
            popupPlayer = player1Spinner.getText().toString();
            final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            LayoutInflater inflater = getLayoutInflater();
            View profileSettingsLayout = inflater.inflate(R.layout.player1profile, null);
            TextView playerNameValue = (TextView) profileSettingsLayout.findViewById(R.id.playerNameValue);
            playerNameValue.setText(popupPlayer);

            final ArrayList player1BHArrList = new ArrayList();
            final ArrayList player1FHArrList = new ArrayList();

            foreHandListView = (ListView) profileSettingsLayout.findViewById(R.id.foreHandListView);
            backHandListView = (ListView) profileSettingsLayout.findViewById(R.id.backHandListView);

            foreHandListView.setAdapter(player1FHAdapter);
            backHandListView.setAdapter(player1BHAdapter);

            foreHandListView.post(new Runnable() {
                @Override
                public void run() {
                    foreHandListView.smoothScrollToPosition(0);
                }
            });

            backHandListView.post(new Runnable() {
                @Override
                public void run() {
                    backHandListView.smoothScrollToPosition(0);
                }
            });

            final Spinner playerHand = (Spinner) profileSettingsLayout.findViewById(R.id.playerHand);
            ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(getContext(),
                    R.layout.footer_spinner, new ArrayList( Arrays.asList(getResources().getStringArray(R.array.playerHand))));
            playerHand.setAdapter(playerHandOption);
            if(player1Hand.toString().length()> 0)
            {
                for (int i=0;i<playerHand.getCount();i++)
                {
                    if (playerHand.getItemAtPosition(i).toString().equalsIgnoreCase(player1Hand))
                    {
                        playerHand.setSelection(i);
                        break;
                    }
                }
            }


            playerHand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    player1Hand = playerHand.getSelectedItem().toString();
                    player1ProfileSettings = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });


            final EditText foreHandRT = (EditText) profileSettingsLayout.findViewById(R.id.foreHandRT);
            final EditText foreHandDate = (EditText) profileSettingsLayout.findViewById(R.id.foreHandDate);
            ImageView foreHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.foreHandAdd);

            final EditText backHandRT = (EditText) profileSettingsLayout.findViewById(R.id.backHandRT);
            final EditText backHandDate = (EditText) profileSettingsLayout.findViewById(R.id.backHandDate);
            ImageView backHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.backHandAdd);

            foreHandAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    String foreHandRTInput = foreHandRT.getText().toString();
                    String foreHandDateInput = foreHandDate.getText().toString();
                    if(foreHandRTInput.length() > 0 && foreHandDateInput.length() > 0)
                    {
                        if(foreHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                        {
                            Rubber rubberObj = new Rubber();
                            rubberObj.setRubberType(foreHandRTInput);
                            rubberObj.setRubberDate(foreHandDateInput);
                            player1FH.add(rubberObj);
                            Collections.reverse(player1FH);
                            player1ProfileSettings = true;
                            player1FHAdapter.notifyDataSetChanged();
                            foreHandListView.setSelection(player1FH.size()-1);

                            foreHandRT.setText("");
                            foreHandDate.setText("");
                        }
                        else
                            foreHandDate.setError("Please enter date in a valid format dd/mm/yy");
                    }
                }
            });

            backHandAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    String backHandRTInput = backHandRT.getText().toString();
                    String backHandDateInput = backHandDate.getText().toString();
                    if(backHandRTInput.length() > 0 && backHandDateInput.length() > 0)
                    {
                        if(backHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                        {
                            Rubber rubberObj = new Rubber();
                            rubberObj.setRubberType(backHandRTInput);
                            rubberObj.setRubberDate(backHandDateInput);
                            player1BH.add(rubberObj);
                            Collections.reverse(player1BH);
                            player1ProfileSettings = true;

                            player1BHAdapter.notifyDataSetChanged();
                            backHandListView.setSelection(player1BH.size()-1);

                            foreHandRT.setText("");
                            backHandDate.setText("");
                        }
                        else
                            backHandDate.setError("Please enter date in a valid format dd/mm/yy");
                    }
                }
            });


            profileDialog.setCancelable(false);

            profileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            profileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(player1ProfileSettings)
                    {
                        for(int k= 0; k<player1FH.size();k++)
                        {
                            JSONObject obj1 = new JSONObject();
                            obj1.put("rubberDate",player1FH.get(k).getRubberDate());
                            obj1.put("rubberType", player1FH.get(k).getRubberType());
                            player1FHArrList.add(obj1);
                        }

                        for(int k= 0; k<player1BH.size();k++)
                        {
                            JSONObject obj1 = new JSONObject();
                            obj1.put("rubberDate",player1BH.get(k).getRubberDate());
                            obj1.put("rubberType", player1BH.get(k).getRubberType());
                            player1BHArrList.add(obj1);
                        }


                        JSONObject obj = new JSONObject();
                        obj.put("playerName", player1Spinner.getText().toString());
                        obj.put("playerId", player1Spinner.getTag().toString());
                        obj.put("playerHand",player1Hand);
                        obj.put("playerFH", player1FHArrList);
                        obj.put("playerBH", player1BHArrList);


                        requestType = "POST";
                        params= "recordPlayerProfile?";
                        methodType="recordPlayerProfile";
                        queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerName="+player1Spinner.getText().toString()+"&playerProfile="+obj;

                        asyncTask = new ExecuteURLTask(getContext(),methodType);
                        asyncTask.delegate = RecordAnalytics.this;
                        asyncTask.execute(params,queryparams,requestType);
                    }
                    dialog.cancel();


                }
            });
            profileDialog.setView(profileSettingsLayout);
            profileDialog.show();


        }
        else if(item.getTitle().toString().equalsIgnoreCase(player2Spinner.getText().toString()))
        {
            popupPlayer = player2Spinner.getText().toString();
            final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
            LayoutInflater inflater = getLayoutInflater();
            View profileSettingsLayout = inflater.inflate(R.layout.player2profile, null);
            TextView playerNameValue = (TextView) profileSettingsLayout.findViewById(R.id.playerNameValue);
            playerNameValue.setText(popupPlayer);
            final ArrayList player2BHArrList = new ArrayList();
            final ArrayList player2FHArrList = new ArrayList();


            foreHandListView = (ListView) profileSettingsLayout.findViewById(R.id.foreHandListView);
            backHandListView = (ListView) profileSettingsLayout.findViewById(R.id.backHandListView);

            foreHandListView.setAdapter(player2FHAdapter);
            backHandListView.setAdapter(player2BHAdapter);

            final Spinner playerHand = (Spinner) profileSettingsLayout.findViewById(R.id.playerHand);
            ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(getContext(),
                    R.layout.footer_spinner, new ArrayList( Arrays.asList(getResources().getStringArray(R.array.playerHand))));
            playerHand.setAdapter(playerHandOption);

            if(player2Hand.toString().length()> 0)
            {
                for (int i=0;i<playerHand.getCount();i++)
                {
                    if (playerHand.getItemAtPosition(i).toString().equalsIgnoreCase(player2Hand))
                    {
                        playerHand.setSelection(i);
                        break;
                    }
                }
            }
            playerHand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    player2Hand = playerHand.getSelectedItem().toString();
                    player2ProfileSettings = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });

            final EditText foreHandRT = (EditText) profileSettingsLayout.findViewById(R.id.foreHandRT);
            final EditText foreHandDate = (EditText) profileSettingsLayout.findViewById(R.id.foreHandDate);
            ImageView foreHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.foreHandAdd);

            final EditText backHandRT = (EditText) profileSettingsLayout.findViewById(R.id.backHandRT);
            final EditText backHandDate = (EditText) profileSettingsLayout.findViewById(R.id.backHandDate);
            ImageView backHandAdd = (ImageView) profileSettingsLayout.findViewById(R.id.backHandAdd);

            foreHandAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    String foreHandRTInput = foreHandRT.getText().toString();
                    String foreHandDateInput = foreHandDate.getText().toString();
                    if(foreHandRTInput.length() > 0 && foreHandDateInput.length() > 0)
                    {
                        if(foreHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                        {
                            Rubber rubberObj = new Rubber();
                            rubberObj.setRubberType(foreHandRTInput);
                            rubberObj.setRubberDate(foreHandDateInput);
                            player2FH.add(rubberObj);
                            Collections.reverse(player2FH);
                            player2ProfileSettings = true;
                            player2FHAdapter.notifyDataSetChanged();
                            foreHandListView.setSelection(player2FH.size()-1);
                            foreHandRT.setText("");
                            foreHandDate.setText("");
                        }
                        else
                            foreHandDate.setError("Please enter date in a valid format dd/mm/yy");
                    }
                }
            });

            backHandAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    String backHandRTInput = backHandRT.getText().toString();
                    String backHandDateInput = backHandDate.getText().toString();
                    if(backHandRTInput.length() > 0 && backHandDateInput.length() > 0)
                    {
                        if(backHandDateInput.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                        {
                            Rubber rubberObj = new Rubber();
                            rubberObj.setRubberType(backHandRTInput);
                            rubberObj.setRubberDate(backHandDateInput);
                            player2BH.add(rubberObj);
                            Collections.reverse(player2BH);
                            player2ProfileSettings = true;
                            player2BHAdapter.notifyDataSetChanged();
                            backHandListView.setSelection(player2BH.size()-1);

                            foreHandRT.setText("");
                            backHandDate.setText("");
                        }
                        else
                            backHandDate.setError("Please enter date in a valid format dd/mm/yy");
                    }
                }
            });


            profileDialog.setCancelable(false);

            profileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            profileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(player2ProfileSettings)
                    {
                        for(int k= 0; k<player2FH.size();k++)
                        {
                            JSONObject obj1 = new JSONObject();
                            obj1.put("rubberDate",player2FH.get(k).getRubberDate());
                            obj1.put("rubberType", player2FH.get(k).getRubberType());
                            player2FHArrList.add(obj1);

                        }

                        for(int k= 0; k<player2BH.size();k++)
                        {
                            JSONObject obj1 = new JSONObject();
                            obj1.put("rubberDate",player2BH.get(k).getRubberDate());
                            obj1.put("rubberType", player2BH.get(k).getRubberType());
                            player2BHArrList.add(obj1);


                        }


                        JSONObject obj = new JSONObject();
                        obj.put("playerName", player2Spinner.getText().toString());
                        obj.put("playerId", player2Spinner.getTag().toString());
                        obj.put("playerHand",player2Hand);
                        obj.put("playerFH", player2FHArrList);
                        obj.put("playerBH", player2BHArrList);


                        requestType = "POST";
                        params= "recordPlayerProfile?";
                        methodType="recordPlayerProfile";
                        queryparams="caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerName="+player2Spinner.getText().toString()+"&playerProfile="+obj;

                        asyncTask = new ExecuteURLTask(getContext(),methodType);
                        asyncTask.delegate = RecordAnalytics.this;
                        asyncTask.execute(params,queryparams,requestType);
                    }


                    dialog.cancel();
                }
            });
            profileDialog.setView(profileSettingsLayout);
            profileDialog.show();


        }
        else if(item.getTitle().toString().equalsIgnoreCase("Match Date"))
        {
            try{
                final AlertDialog.Builder profileDialog = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                LayoutInflater inflater = getLayoutInflater();
                View matchDateLayout = inflater.inflate(R.layout.match_date, null);
                final EditText currentMatchDate = (EditText) matchDateLayout.findViewById(R.id.matchDate);
                if(mSession.ypGetMatchDate() != null)
                {
                    currentMatchDate.setText(mSession.ypGetMatchDate());
                }
                else
                {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    Date date = new Date();
                    String currentDate = dateFormat.format(date);
                    currentMatchDate.setText(currentDate.toString());

                }
                profileDialog.setCancelable(false);
                profileDialog.setPositiveButton("ok", null);
                profileDialog.setNegativeButton("Cancel", null);

                profileDialog.setView(matchDateLayout);

                final AlertDialog mAlertDialog = profileDialog.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {


                        Button cancelBtn = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();

                            }
                        });
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                if(currentMatchDate.getText().toString().length() > 0)
                                {
                                    if(currentMatchDate.getText().toString().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{2})"))
                                    {
                                        matchDate = currentMatchDate.getText().toString();
                                        mSession.ypStoreSessionMatchDate(matchDate);
                                        mAlertDialog.dismiss();

                                    }
                                    else
                                    {
                                        currentMatchDate.setError("Please enter date in a valid format dd/mm/yy");

                                    }
                                }
                                else
                                {
                                    currentMatchDate.setError("Please enter date in a valid format dd/mm/yy");

                                    //mAlertDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();



            }catch(Exception e)
            {
            }

        }
    }


    public void toastMessage(String message)
    {
        mUtil.toastMessage( message,getContext());
    }
    @Override
    public void onResume() {

        super.onResume();
        refreshData();
    }

    public void refreshData(){

        if(getContext() != null && getActivity() != null)
        {
            if(mUtil.isOnline())
            {
                methodType = "getStrokesData";
                params = "getStrokesData?caller=" + Constants.caller + "&apiKey=" + Constants.apiKey + "&userId=" + mSession.ypGetUserID();
                requestType = "GET";
                asyncTask = new ExecuteURLTask(getContext(), "getStrokesData");
                asyncTask.delegate = RecordAnalytics.this;
                asyncTask.execute(params, "", requestType);
            }
            else
            {
                mUtil.toastMessage("No Internet",getContext());
            }
        }

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
            if(methodName.equalsIgnoreCase("getStrokesData"))
            {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                    if (jsonObject.containsKey("message"))
                    {
                        if (jsonObject.get("message").toString().equalsIgnoreCase("Access forbidden")) {
                            if(mUtil != null)
                                mUtil.toastMessage(jsonObject.get("message").toString(), getContext());

                        }
                        recordAnalyticsLayout.setVisibility(View.GONE);

                    }
                    else
                    {
                        recordAnalyticsLayout.setVisibility(View.VISIBLE);
                        if(jsonObject.containsKey("losingStrokes"))
                        {
                            losingStrokes = (ArrayList<String>) jsonObject.get("losingStrokes");
                        }

                        if(jsonObject.containsKey("player1SetKeyMap"))
                        {
                            Gson gson = new Gson();
                            Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
                            playerJsonList = gson.fromJson(jsonObject.get("player1SetKeyMap").toString(), listType1);
                        }

                        dataAdapter1 = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, playerJsonList);
                        player1Spinner.setAdapter(dataAdapter1);
                        player1Spinner.setThreshold(1);

                        dataAdapter2 = new ArrayAdapter<PlayerJson>(getContext(),
                                R.layout.header_spinner, playerJsonList);
                        player2Spinner.setAdapter(dataAdapter2);
                        player2Spinner.setThreshold(1);

                        if(jsonObject.containsKey("shortKeysJson"))
                        {
                            JSONArray strokeKeysJsonArr = (JSONArray) jsonObject.get("shortKeysJson");
                            if (strokeKeysJsonArr.size() > 0) {
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

                        if(jsonObject.containsKey("destinationKeysJson"))
                        {
                            JSONArray masterDestinationKeysJsonArr = (JSONArray) jsonObject.get("destinationKeysJson");
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


                        if(jsonObject.containsKey("p6destinationKeysJson"))
                            p6DestinationKeysJsonArr = (JSONArray) jsonObject.get("p6destinationKeysJson");
                        if(jsonObject.containsKey("p8destinationKeysJson"))
                            p8DestinationKeysJsonArr = (JSONArray) jsonObject.get("p8destinationKeysJson");
                        if(jsonObject.containsKey("p9destinationKeysJson"))
                            p9DestinationKeysJsonArr = (JSONArray) jsonObject.get("p9destinationKeysJson");
                        if(jsonObject.containsKey("p14destinationKeysJson"))
                            p14DestinationKeysJsonArr = (JSONArray) jsonObject.get("p14destinationKeysJson");

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

                        if(jsonObject.containsKey("serviceKeysJson"))
                        {
                            JSONArray serviceKeysJsonArr = (JSONArray) jsonObject.get("serviceKeysJson");
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
}



