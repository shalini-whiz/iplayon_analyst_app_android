package in.iplayon.analyst.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.LocalUriFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.io.LineReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import in.iplayon.analyst.R;
import in.iplayon.analyst.adapter.AnalyticsAdapter;
import in.iplayon.analyst.adapter.AnalyticsAdapter1;
import in.iplayon.analyst.adapter.ErrorAnalysisAdapter;
import in.iplayon.analyst.adapter.FourthBallAdapter;
import in.iplayon.analyst.adapter.RallyAdapter;
import in.iplayon.analyst.adapter.ReceiveAdapter;
import in.iplayon.analyst.adapter.RecordShotAdapter;
import in.iplayon.analyst.adapter.RubberAdapter;
import in.iplayon.analyst.adapter.ServiceFaultAdapter;
import in.iplayon.analyst.adapter.ServiceLossAdapter;
import in.iplayon.analyst.adapter.ServicePointsAdapter;
import in.iplayon.analyst.adapter.ServiceResponseAdapter;
import in.iplayon.analyst.adapter.ShareHistoryAdapter;
import in.iplayon.analyst.adapter.StrokeAnalysisAdapter;
import in.iplayon.analyst.adapter.ThirdBallAttackAdapter;
import in.iplayon.analyst.adapter.ViewShotAdapter;
import in.iplayon.analyst.adapter.RecordShotAdapter.OnDataChangeListener;
import in.iplayon.analyst.adapter.ViewStrokeShotAdapter;
import in.iplayon.analyst.modal.ErrorAnalyzer;
import in.iplayon.analyst.modal.PlayerJson;
import in.iplayon.analyst.modal.Rally;
import in.iplayon.analyst.modal.Rubber;
import in.iplayon.analyst.modal.SequenceDetail;
import in.iplayon.analyst.modal.ShareHistoryPlayerJson;
import in.iplayon.analyst.modal.SharedHistoryJson;
import in.iplayon.analyst.modal.Shot;
import in.iplayon.analyst.modal.ShotInfo;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.CircleForm;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SequenceActivity extends  Activity implements AsyncResponse{
	SessionManager mSession;
	Util mUtil;
	String report = "";
	public static String response = "";

	Button analytics_analyze;
	Button analytics_data;
	Button sequence_view;
	List<PlayerJson> contactList;

	ArrayList<Shot> viewEachSequenceShot = new ArrayList<Shot>();
	RecordShotAdapter recordShotAdapter;
	ViewShotAdapter shotAdapter ;
	ArrayList<Shot> shotAdapterList = new ArrayList<Shot>();
	ArrayList<Shot> recordShotAdapterList = new ArrayList<Shot>();
	ArrayList<Rubber> player1FH = new ArrayList<Rubber>();
	ArrayList<Rubber> player1BH = new ArrayList<Rubber>();
	ArrayList<Rubber> player2FH = new ArrayList<Rubber>();
	ArrayList<Rubber> player2BH = new ArrayList<Rubber>();
	JSONObject player2BHObj = new JSONObject();
	JSONObject player2FHObj = new JSONObject();
	RubberAdapter player1FHAdapter;
	RubberAdapter player1BHAdapter;
	RubberAdapter player2FHAdapter;
	RubberAdapter player2BHAdapter;
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
	ListView mListView;
	ListView mStrokesListView;
	ListView viewPlayerSequences;
	TextView sequenceSave;
	List<String> losingStrokes = new ArrayList<String>();
	ProgressBar progressBar;
	AlertDialog mAlertDialog;


	LinearLayout recordSequenceLayout;
	LinearLayout analyticsSequenceLayout;
	LinearLayout sequenceViewLayout;
	public String popupPlayer="";

	Spinner chooseAnalyticsType;
	Spinner chooseReviewType;
	AutoCompleteTextView shareFilter;
	ImageView viewSharedProfile;
	String winner = "";
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

	EditText matchTitle;
	EditText startTime;
	//view related
	Spinner player1ViewFilter;
	Spinner player2ViewFilter;
	Spinner dateFilterView;
	String params = "";
	String requestType = "";
	String methodType = "";
	String urlResponse = "";
	String queryparams = "";
	String fetchParam = "";
	ExecuteURLTask asyncTask;
	JSONObject getPlayerSetDataJSON;
	JSONObject getPlayerSetDataJSON_Analyze;
	JSONObject getAnalyzeReport;
	JSONArray getAnalyzeResponse;


	//analyze related
	List<String> player1AnalyzeSet = new ArrayList<String>();
	List<String> player2AnalyzeSet = new ArrayList<String>();
	Spinner player1AnalyzeFilter;
	Spinner player2AnalyzeFilter;
	Spinner timeAnalyzeFilter;
	Spinner sortAnalyzeFilter;
	ArrayAdapter<String> player1AnalyzeAdapterr;
	ArrayAdapter<String> player2AnalyzeAdapterr;
	ArrayAdapter<String> serviceAdapter ;
	ArrayAdapter<String> strokeAdapter ;
	ArrayList<JSONObject> sortedArray = new ArrayList<JSONObject>();
	ImageView downloadAnalytics;
	ImageView shotDeleteInput;

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

	JSONArray destinationKeysJsonArr;
	JSONArray p6DestinationKeysJsonArr;
	JSONArray p8DestinationKeysJsonArr;
	JSONArray p9DestinationKeysJsonArr;
	JSONArray p14DestinationKeysJsonArr;

	Map<String,String> p6DestinationKeyMap = new HashMap<>();
	Map<String, String> p6DestinationMap = new HashMap<>();
	ArrayList<String> p6DestinationArrayList = new ArrayList<String>();

	Map<String,String> p8DestinationKeyMap = new HashMap<>();
	Map<String, String> p8DestinationMap = new HashMap<>();
	ArrayList<String> p8DestinationArrayList = new ArrayList<String>();

	Map<String,String> p9DestinationKeyMap = new HashMap<>();
	Map<String, String> p9DestinationMap = new HashMap<>();
	ArrayList<String> p9DestinationArrayList = new ArrayList<String>();

	Map<String,String> p14DestinationKeyMap = new HashMap<>();
	Map<String, String> p14DestinationMap = new HashMap<>();
	ArrayList<String> p14DestinationArrayList = new ArrayList<String>();

	public DatePicker datePicker;
	public Calendar calendar;
	public TextView dateView;
	public int year, month, day;

	TextView handTitle ;
	TextView countTitle;
	TextView winShotTitle ;
	TextView destinationTitle;

	AutoCompleteTextView shotHandInput;
	AutoCompleteTextView shotDestinationInput;
	View header;
	LinearLayout spinnerLayout;


	List<HashMap<String, String>> playerList;
	List<PlayerJson> playerJsonList;

	List<PlayerJson> player1JsonAnalyzeList;
	ArrayAdapter<PlayerJson> player1AnalyzeAdapter;
	List<PlayerJson> player2JsonAnalyzeList;
	ArrayAdapter<PlayerJson> player2AnalyzeAdapter;
	List<PlayerJson> registeredPlayers;
	List<ShareHistoryPlayerJson> registeredPlayers1;

	LinearLayout rallyHeader;
	LinearLayout strokeAnalysisHeaderLayout ;
	LinearLayout thirdBallAnalysisHeaderLayout;
	LinearLayout serviceHeaderLayout;
	LinearLayout receiveHeaderLayout;

	ArrayAdapter<PlayerJson> player1ViewAdapter;
	ArrayAdapter<PlayerJson> player2ViewAdapter;
	private static final int REQUEST_WRITE_STORAGE = 112;
	LinearLayout shareUserLayout;


	ImageView player1ServiceIcon;
	ImageView player2ServiceIcon;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sequence);
		mSession = new SessionManager(getApplicationContext());
		mUtil = new Util(getApplicationContext(), this);
		report = getIntent().getExtras().getString("report");
		//response = getIntent().getExtras().getString("response");

		//buttons
		analytics_data = (Button) findViewById(R.id.analytics_data);
		analytics_analyze = (Button) findViewById(R.id.analytics_analyze);
		sequence_view = (Button) findViewById(R.id.sequence_view);
		progressBar = new ProgressBar(SequenceActivity.this);
		spinnerLayout = new LinearLayout(SequenceActivity.this);
		spinnerLayout.setGravity(Gravity.CENTER);
		addContentView(spinnerLayout,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		spinnerLayout.removeAllViews();
		spinnerLayout.addView(progressBar);
		progressBar.setVisibility(View.GONE);

		shareUserLayout = (LinearLayout) findViewById(R.id.shareUserLayout);



		 player1ServiceIcon = (ImageView) findViewById(R.id.player1ServiceIcon);
		 player2ServiceIcon = (ImageView) findViewById(R.id.player2ServiceIcon);

		header = getLayoutInflater().inflate(R.layout.data_view, null);
		shotHandInput = (AutoCompleteTextView) header.findViewById(R.id.shotHandInput);
		//shotHandInput.setMinHeight(200);

		shotDestinationInput = (AutoCompleteTextView) header.findViewById(R.id.shotDestinationInput);
		shotDeleteInput = (ImageView) header.findViewById(R.id.shotDeleteInput);
		player1ViewFilter = (Spinner) findViewById(R.id.player1ViewFilter);
		player2ViewFilter = (Spinner) findViewById(R.id.player2ViewFilter);
		player1AnalyzeFilter = (Spinner) findViewById(R.id.player1AnalyzeFilter);
		player2AnalyzeFilter = (Spinner) findViewById(R.id.player2AnalyzeFilter);

		startTime = (EditText) findViewById(R.id.startTime);
		matchTitle = (EditText) findViewById(R.id.matchTitle);


		p6DestinationKeysJsonArr = new JSONArray();
		p8DestinationKeysJsonArr = new JSONArray();
		p9DestinationKeysJsonArr = new JSONArray();
		p14DestinationKeysJsonArr = new JSONArray();

		if (response != null && response != "")
		{
			JSONParser jsonParser = new JSONParser();
			Object obj;
			try {
				obj = jsonParser.parse(response);
				JSONObject jsonValues = (JSONObject) obj;

				//jsonValues.get("playerNameEntries").toString().split(",");

				if(jsonValues.containsKey("losingStrokes"))
				{
					losingStrokes = (ArrayList<String>) jsonValues.get("losingStrokes");
				}

				if(jsonValues.containsKey("player1SetKeyMap"))
				{
					Gson gson = new Gson();
					Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
					playerJsonList = gson.fromJson(jsonValues.get("player1SetKeyMap").toString(), listType1);
				}

				player1JsonAnalyzeList = new ArrayList<PlayerJson>();
				player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
						R.layout.header_spinner, player1JsonAnalyzeList);
				player2JsonAnalyzeList = new ArrayList<PlayerJson>();
				registeredPlayers = new ArrayList<PlayerJson>();
				registeredPlayers1 = new ArrayList<ShareHistoryPlayerJson>();
				player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
						R.layout.header_spinner, player2JsonAnalyzeList);



				if(jsonValues.get("player1Set") != null  && jsonValues.get("player1Set").toString().length() > 0)
				{
					Gson gson = new Gson();
					Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
					player1JsonAnalyzeList = gson.fromJson(jsonValues.get("player1Set").toString(), listType1);
					player1AnalyzeAdapter.notifyDataSetChanged();
				}

				if(jsonValues.get("player2Set") != null && jsonValues.get("player2Set").toString().length() > 0)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
					player2JsonAnalyzeList = gson1.fromJson(jsonValues.get("player2Set").toString(), listType1);
					player2AnalyzeAdapter.notifyDataSetChanged();
				}

				if(jsonValues.get("registeredPlayers") != null)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
					registeredPlayers = gson1.fromJson(jsonValues.get("registeredPlayers").toString(), listType1);

					Type listType2 = new TypeToken<List<ShareHistoryPlayerJson>>(){}.getType();
					registeredPlayers1  = gson1.fromJson(jsonValues.get("registeredPlayers").toString(), listType2);

				}

				player1AnalyzeFilter.setAdapter(player1AnalyzeAdapter);
				player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);








				JSONArray strokeKeysJsonArr = (JSONArray) jsonValues.get("shortKeysJson");
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

				JSONArray masterDestinationKeysJsonArr = (JSONArray) jsonValues.get("destinationKeysJson");
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

				if(jsonValues.containsKey("p6destinationKeysJson"))
					p6DestinationKeysJsonArr = (JSONArray) jsonValues.get("p6destinationKeysJson");
				if(jsonValues.containsKey("p8destinationKeysJson"))
					p8DestinationKeysJsonArr = (JSONArray) jsonValues.get("p8destinationKeysJson");
				if(jsonValues.containsKey("p9destinationKeysJson"))
					p9DestinationKeysJsonArr = (JSONArray) jsonValues.get("p9destinationKeysJson");
				if(jsonValues.containsKey("p14destinationKeysJson"))
					p14DestinationKeysJsonArr = (JSONArray) jsonValues.get("p14destinationKeysJson");

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


				JSONArray serviceKeysJsonArr = (JSONArray) jsonValues.get("serviceKeysJson");
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
			} catch (Exception e) {

			}

			recordSequenceLayout = (LinearLayout) findViewById(R.id.recordSequenceLayout);
			analyticsSequenceLayout = (LinearLayout) findViewById(R.id.analyticsSequenceLayout);
			sequenceViewLayout = (LinearLayout) findViewById(R.id.sequenceViewLayout);

			if(mSession.ypGetMatchDate() == null)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
				Date date = new Date();
				String currentDate = dateFormat.format(date);
				mSession.ypStoreSessionMatchDate(currentDate);
			}


			//onclick functionalities
			analytics_data.post(new Runnable(){
				@Override
				public void run() {
					analytics_data.performClick();
				}
			});
			analytics_data.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					recordSequenceLayout.setVisibility(View.VISIBLE);
					sequenceViewLayout.setVisibility(View.GONE);
					analyticsSequenceLayout.setVisibility(View.GONE);

					analytics_data.setPaintFlags(analytics_data.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
					analytics_analyze.setPaintFlags( analytics_analyze.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
					sequence_view.setPaintFlags( sequence_view.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

					/*
					analytics_data.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hightlightColor));
					analytics_analyze.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.disabled));
					sequence_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.disabled));
					*/

					player1FHAdapter = new RubberAdapter(getApplicationContext(), SequenceActivity.this,player1FH);
					player1BHAdapter = new RubberAdapter(getApplicationContext(), SequenceActivity.this,player1BH);
					player2FHAdapter = new RubberAdapter(getApplicationContext(), SequenceActivity.this,player2FH);
					player2BHAdapter = new RubberAdapter(getApplicationContext(), SequenceActivity.this,player2BH);



					mListView = (ListView) findViewById(R.id.playerStrokesAppend);
					recordShotAdapter = new RecordShotAdapter(getApplicationContext(), SequenceActivity.this,recordShotAdapterList);
					recordShotAdapterList.clear();
					recordShotAdapter.notifyDataSetChanged();
					mListView.setAdapter(recordShotAdapter);
					final ImageView uparrow = (ImageView) findViewById(R.id.uparrow);
					final ImageView downarrow = (ImageView) findViewById(R.id.downarrow);


					mListView.setOnScrollListener(new OnScrollListener() {
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




					serviceAdapter = new ArrayAdapter<String>(getApplicationContext(),
							R.layout.shot_spinner_item);

					strokeAdapter = new ArrayAdapter<String>(getApplicationContext(),
							R.layout.shot_spinner_item);

					if(mListView.getFooterViewsCount() == 0)
						mListView.addFooterView(header);


					player1Service = (RadioButton) findViewById(R.id.player1Service);
					player2Service = (RadioButton) findViewById(R.id.player2Service);
					player1SetInput = (EditText) findViewById(R.id.player1Set);
					player1Points = (EditText) findViewById(R.id.player1Points);
					player2SetInput = (EditText) findViewById(R.id.player2Set);
					player2Points = (EditText) findViewById(R.id.player2Points);
					player1Details = (ImageView) findViewById(R.id.player1Details);
					player2Details = (ImageView) findViewById(R.id.player2Details);
					partialSequence = (RadioButton) findViewById(R.id.partialSequence);
					completeSequence = (RadioButton) findViewById(R.id.completeSequence);
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
								params= "getPlayerDetails?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID()+"&playerID="+player1Spinner.getTag().toString()+"&playerName="+player1Spinner.getText().toString();

								ExecuteURLTask asyncTask1 = new ExecuteURLTask(getApplicationContext(),SequenceActivity.this,false,methodType);
								asyncTask1.delegate = SequenceActivity.this;
								asyncTask1.execute(params,queryparams,requestType);
							}
							else {
								if(player1Spinner.getText().toString().trim().length() == 0)
									mUtil.toastMessage("Please choose Player",getApplicationContext());
								else
									mUtil.toastMessage("New Player",getApplicationContext());

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
								ExecuteURLTask asyncTask1 = new ExecuteURLTask(getApplicationContext(),SequenceActivity.this, false,methodType);
								asyncTask1.delegate = SequenceActivity.this;
								asyncTask1.execute(params, queryparams, requestType);
							}
							else {
								if(player2Spinner.getText().toString().trim().length() == 0)
									mUtil.toastMessage("Please choose Player",getApplicationContext());
								else
									mUtil.toastMessage("New Player",getApplicationContext());

							}
						}
					});
					ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
							R.layout.shot_spinner_item, destinationArrayList);
					shotDestinationInput.setAdapter(destinationAdapter);
					shotDestinationInput.setThreshold(1);
					//shotDestinationInput.setMinHeight(200);

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

					recordShotAdapter.setOnDataChangeListener(new OnDataChangeListener() {
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
									final RadioButton player1Service = (RadioButton) findViewById(R.id.player1Service);
									final RadioButton player2Service = (RadioButton) findViewById(R.id.player2Service);
									if(!(player1Service.isChecked()) && !(player2Service.isChecked()))
									{
										LayoutInflater inflater = getLayoutInflater();
										final View viewSequenceStrokeLayout = inflater.inflate(R.layout.custom_sequence_service, null);
										final RadioButton customPlayer1Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer1Service);
										final RadioButton customPlayer2Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer2Service);

										AlertDialog.Builder serviceDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

													shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.playerColor));
													shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.playerColor));
												}
												else if(customPlayer2Service.isChecked())
												{
													player2Service.setChecked(true);
													player1Service.setChecked(false);

													player1ServiceIcon.setVisibility(View.GONE);
													player2ServiceIcon.setVisibility(View.VISIBLE);

													shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.opponentColor));
													shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.opponentColor));
												}
												else
												{
													player1Service.setChecked(false);
													player2Service.setChecked(false);

													player1ServiceIcon.setVisibility(View.GONE);
													player2ServiceIcon.setVisibility(View.GONE);
													toastMessage("Service is not been set!!");
													//Toast.makeText(getApplicationContext(),"Service is not been set!!",Toast.LENGTH_SHORT).show();
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
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
									}
									else
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
									}
								}
								else
								{
									if((adapterSize%2)==0)
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
									}
									else
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
									}
								}

							}


							else if(event.getAction() == MotionEvent.ACTION_DOWN && player2Spinner.getText().toString().length() == 0)
							{
								toastMessage("Opponent name to be entered");
								//Toast.makeText(getApplicationContext(),"Opponent name to be entered",Toast.LENGTH_SHORT).show();

							}

							return false;
						}
					});

					shotDestinationInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							in.hideSoftInputFromWindow(arg1.getWindowToken(), 0);

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
									final RadioButton player1Service = (RadioButton) findViewById(R.id.player1Service);
									final RadioButton player2Service = (RadioButton) findViewById(R.id.player2Service);
									if(!(player1Service.isChecked()) && !(player2Service.isChecked()))
									{
										LayoutInflater inflater = getLayoutInflater();
										final View viewSequenceStrokeLayout = inflater.inflate(R.layout.custom_sequence_service, null);
										final RadioButton customPlayer1Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer1Service);
										final RadioButton customPlayer2Service = (RadioButton) viewSequenceStrokeLayout.findViewById(R.id.customPlayer2Service);

										AlertDialog.Builder serviceDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

													shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.playerColor));
													shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.playerColor));
												}
												else if(customPlayer2Service.isChecked())
												{
													player2Service.setChecked(true);
													player1Service.setChecked(false);

													player1ServiceIcon.setVisibility(View.GONE);
													player2ServiceIcon.setVisibility(View.VISIBLE);

													shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.opponentColor));
													shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.opponentColor));
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
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
									}
									else
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
									}
								}
								else
								{
									if((adapterSize%2)==0)
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.opponentColor));
									}
									else
									{
										shotHandInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
										shotDestinationInput.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playerColor));
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



					player1Spinner = (AutoCompleteTextView) findViewById(R.id.player1Spinner);
					player2Spinner = (AutoCompleteTextView) findViewById(R.id.player2Spinner);
					player1Spinner.setText("");
					player2Spinner.setText("");
					player1Spinner.setTag("");
					player2Spinner.setTag("");

					/** progressBar related **/

					final ArrayAdapter<PlayerJson> dataAdapter1 = new ArrayAdapter<PlayerJson>(getApplicationContext(),
							R.layout.header_spinner, playerJsonList);
					player1Spinner.setAdapter(dataAdapter1);
					player1Spinner.setThreshold(1);



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


							mTimePicker = new TimePickerDialog(SequenceActivity.this,R.style.AlertDialogTheme, new TimePickerDialog.OnTimeSetListener() {
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










					//ArrayAdapter<String> dataAdapter2 = create ArrayAdapter<String>(getApplicationContext(),
					//R.layout.header_spinner, player2List);
					final ArrayAdapter<PlayerJson> dataAdapter2 = new ArrayAdapter<PlayerJson>(getApplicationContext(),
							R.layout.header_spinner, playerJsonList);
					player2Spinner.setAdapter(dataAdapter2);
					player2Spinner.setThreshold(1);
					player2Spinner.requestFocus();

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





					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(player2Spinner.getWindowToken(), 0);
					
					/*player1Spinner.setOnItemClickListener(create OnItemClickListener() {
						  @Override
						  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						    in.hideSoftInputFromWindow(player1Spinner.getWindowToken(), 0);
						  }
						});
					
					player2Spinner.setOnItemClickListener(create OnItemClickListener() {
						  @Override
						  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						    in.hideSoftInputFromWindow(player2Spinner.getWindowToken(), 0);
						  }
						});*/



					TextView sequenceReset = (TextView) findViewById(R.id.sequenceReset);
					final ImageView sequenceSettings = (ImageView) findViewById(R.id.sequenceSettings);
					sequenceSave = (TextView) findViewById(R.id.sequenceSave);




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

							AlertDialog.Builder resetDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

									analytics_data.post(new Runnable(){
										@Override
										public void run() {
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


							ExecuteURLTask asyncTask1 = new ExecuteURLTask(getApplicationContext(),methodType);
							asyncTask1.delegate = SequenceActivity.this;
							try {
								asyncTask1.execute(params,queryparams,requestType).get();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}

							Context wrapper = new ContextThemeWrapper(SequenceActivity.this, R.style.PopupMenu);
							//Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
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




							final PopupMenu menu = new PopupMenu(SequenceActivity.this, sequenceSettings);
							menu.getMenu().add(Html.fromHtml("<font color='#434343'>Destination Format ></font>"));
							menu.getMenu().add(Html.fromHtml("<font color='#434343'>Player Settings ></font>"));
							menu.getMenu().add(Html.fromHtml("<font color='#434343'>Match Date</font>"));
							menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								public boolean onMenuItemClick(MenuItem item) {

									if(item.getTitle().toString().equalsIgnoreCase("Destination Format >"))
									{
										Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
										//final PopupMenu submenu = new PopupMenu(wrapper,sequenceSettings);

										final PopupMenu submenu = new PopupMenu(SequenceActivity.this, sequenceSettings);
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
														ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
														ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
														ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
														ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
										Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
										final PopupMenu submenu1 = new PopupMenu(wrapper,sequenceSettings);

										final PopupMenu submenu = new PopupMenu(SequenceActivity.this, sequenceSettings);
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
													final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
													ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(SequenceActivity.this,
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

																asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
																asyncTask.delegate = SequenceActivity.this;
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
													final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
													ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(SequenceActivity.this,
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

																asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
																asyncTask.delegate = SequenceActivity.this;
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
											final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
				}
			});



			/********************* view sequence ***************************/

			sequence_view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View buttonSequenceView) {

					try{

						sequence_view.setPaintFlags(sequence_view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
						analytics_analyze.setPaintFlags( analytics_analyze.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
						analytics_data.setPaintFlags( analytics_data.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

						recordSequenceLayout.setVisibility(View.GONE);
						sequenceViewLayout.setVisibility(View.VISIBLE);
						analyticsSequenceLayout.setVisibility(View.GONE);


						dateFilterView = (Spinner) findViewById(R.id.timeFilter);
						mStrokesListView = (ListView) findViewById(R.id.viewPlayerSequences);
						chooseReviewType = (Spinner) findViewById(R.id.chooseReviewType);
						shareFilter = (AutoCompleteTextView) findViewById(R.id.shareFilter);
						viewSharedProfile = (ImageView) findViewById(R.id.viewSharedProfile);
						final Button shareSubmit = (Button) findViewById(R.id.shareSubmit);


						List<String> reviewList = Arrays.asList(getResources().getStringArray(R.array.seqReviewList));
						ArrayAdapter<String> reviewTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
								R.layout.header_spinner,reviewList);
						chooseReviewType.setAdapter(reviewTypeAdapter);


						registeredPlayers1.add(0,new ShareHistoryPlayerJson("None", ""));


						final ArrayAdapter<PlayerJson> registeredPlayersAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
								R.layout.footer_spinner,registeredPlayers);
						shareFilter.setAdapter(registeredPlayersAdapter);



						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(buttonSequenceView.getWindowToken(), 0);

						if(mUtil.isOnline())
						{
							//progressBar.setVisibility(View.VISIBLE);

							requestType="GET";
							queryparams="";
							methodType="getPlayerSetData";
							params= "getPlayerSetData?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();
							ExecuteURLTask asyncTask1 = new ExecuteURLTask(getApplicationContext(),methodType);
							asyncTask1.delegate = SequenceActivity.this;
							asyncTask1.execute(params,queryparams,requestType);

						}
						else
						{
							toastMessage("No Internet");
						}













						try{
							player1ViewAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player1JsonAnalyzeList);
							player2ViewAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player2JsonAnalyzeList);
							player1ViewFilter.setAdapter(player1ViewAdapter);
							player2ViewFilter.setAdapter(player2ViewAdapter);
						}catch(Exception e){}

						player1ViewFilter.getBackground().clearColorFilter();
						player2ViewFilter.getBackground().clearColorFilter();

						List<String> dateViewFilter = new ArrayList<String>();
						dateViewFilter.add("Any time");
						dateViewFilter.add("Last Day");
						dateViewFilter.add("Last Week");
						dateViewFilter.add("Last Month");
						dateViewFilter.add("Last 2 months");
						dateViewFilter.add("Last Year");

						ArrayAdapter<String> dateFilterViewAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
									LinearLayout reviewPlayerFilters = (LinearLayout) findViewById(R.id.reviewPlayerFilters);
									reviewPlayerFilters.setVisibility(View.GONE);
									Spinner timeFilter = (Spinner) findViewById(R.id.timeFilter);
									timeFilter.setVisibility(View.GONE);

								}
								else
								{
									LinearLayout reviewPlayerFilters = (LinearLayout) findViewById(R.id.reviewPlayerFilters);
									reviewPlayerFilters.setVisibility(View.VISIBLE);
									Spinner timeFilter = (Spinner) findViewById(R.id.timeFilter);
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
						final ArrayAdapter<PlayerJson> sharedToAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
								R.layout.footer_spinner,registeredPlayers);
						shareFilter.setAdapter(sharedToAdapter);



						viewSharedProfile.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								if( mAlertDialog != null && mAlertDialog.isShowing() ) mAlertDialog.dismiss();

								String params= "viewUserProfile?";
								String queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+shareFilter.getTag().toString();
								String requestType = "POST";
								ExecuteURLTask asyncTask = new ExecuteURLTask(getApplicationContext(),"viewUserProfile");
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

															Glide.with(SequenceActivity.this).load(R.drawable.close_red)
																	.crossFade()
																	.thumbnail(0.5f)
																	.bitmapTransform(new CircleForm(SequenceActivity.this))
																	.diskCacheStrategy(DiskCacheStrategy.ALL)
																	.into(closeDialog);


															AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
										AlertDialog.Builder shareDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

												progressBar.setVisibility(View.VISIBLE);

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

												asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
												asyncTask.delegate = SequenceActivity.this;
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
									AlertDialog.Builder shareDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

											asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
											asyncTask.delegate = SequenceActivity.this;
											asyncTask.execute(params,queryparams,requestType);
										}

									});
									shareDialog.show();




								}

							}
						});



					}catch(Exception e) {}
				}



			});

			analytics_analyze.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View buttonSequenceAnalyze) {

					try{

						analytics_analyze.setPaintFlags(sequence_view.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
						sequence_view.setPaintFlags( analytics_analyze.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
						analytics_data.setPaintFlags( analytics_data.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

						recordSequenceLayout.setVisibility(View.GONE);
						sequenceViewLayout.setVisibility(View.GONE);
						analyticsSequenceLayout.setVisibility(View.VISIBLE);


						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(buttonSequenceAnalyze.getWindowToken(), 0);
						downloadAnalytics = (ImageView) findViewById(R.id.downloadAnalytics);

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
							asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
							asyncTask.delegate = SequenceActivity.this;
							asyncTask.execute(params,queryparams,requestType);

						}
						else
						{
							toastMessage("No Internet");
						}



						chooseAnalyticsType = (Spinner) findViewById(R.id.chooseAnalyticsType);
					/*List<String> analyticsList = new ArrayList<String>();
					analyticsList.add("Last Shot Analysis");
					analyticsList.add("Service Points");
					analyticsList.add("Receive Points");
					analyticsList.add("Service Loss");
					analyticsList.add("Service Fault");
					analyticsList.add("Service Response");
					analyticsList.add("Rally Length");
					analyticsList.add("Stroke Analysis");
					analyticsList.add("Errors");
					analyticsList.add("3rd Ball Attack");
					analyticsList.add("4th Ball Shot");*/

						List<String> myArrayList = Arrays.asList(getResources().getStringArray(R.array.sequenceAnalyzeList));
						ArrayAdapter<String> analyticsTypeAdapter = new ArrayAdapter<String>(getApplicationContext(),
								R.layout.header_spinner,myArrayList);
						chooseAnalyticsType.setAdapter(analyticsTypeAdapter);


						rallyHeader = (LinearLayout) findViewById(R.id.rallyHeaderLayout);
						strokeAnalysisHeaderLayout = (LinearLayout) findViewById(R.id.strokeAnalysisHeaderLayout);
						thirdBallAnalysisHeaderLayout = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
						serviceHeaderLayout = (LinearLayout) findViewById(R.id.serviceHeaderLayout);
						receiveHeaderLayout = (LinearLayout) findViewById(R.id.receiveHeaderLayout);
						countTitle = (TextView) findViewById(R.id.countTitle);


						handTitle = (TextView) findViewById(R.id.serviceTitle);
						destinationTitle = (TextView) findViewById(R.id.destinationTitle);

						//errors and stroke analysis
						TextView shotFrom = (TextView) findViewById(R.id.shotFrom);
						TextView shotDestination = (TextView) findViewById(R.id.shotDestination);
						TextView shotPlayedCount = (TextView) findViewById(R.id.shotPlayedCount);



						//receive points analysis
						TextView handTitle1 = (TextView) findViewById(R.id.handTitle1);
						TextView destinationTitle1 = (TextView) findViewById(R.id.destinationTitle1);
						TextView winShotTitle1 = (TextView) findViewById(R.id.winShotTitle1);
						TextView countTitle1 = (TextView) findViewById(R.id.countTitle1);

						//rally length sort
						TextView sequenceLen = (TextView) findViewById(R.id.sequenceLen);
						TextView shotsPlayed = (TextView) findViewById(R.id.shotsPlayed);
						TextView winCount = (TextView) findViewById(R.id.winCount);
						TextView lossCount = (TextView) findViewById(R.id.lossCount);
						TextView efficiency = (TextView) findViewById(R.id.efficiency);

						//third vall sort
						TextView thirdService = (TextView) findViewById(R.id.thirdService);
						TextView thirdDest = (TextView) findViewById(R.id.thirdDest);
						TextView thirdEff = (TextView) findViewById(R.id.thirdEff);

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
									ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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
						player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
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
						player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
								R.layout.header_spinner, player2JsonAnalyzeList);
						player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);

						player2AnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
								player2AnalyzeFilter.setTag(player2AnalyzeAdapter.getItem(pos).getUserId());
								if(getPlayerSetDataJSON_Analyze != null)
								{
									ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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
									ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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

										asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
										asyncTask.delegate = SequenceActivity.this;
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



						timeAnalyzeFilter = (Spinner) findViewById(R.id.analyzeTimeFilter);
						List<String> dateFilter = new ArrayList<String>();
						dateFilter.add("Any time");
						dateFilter.add("Beginning");
						dateFilter.add("Last 3 Months");
						dateFilter.add("Last Month");
						dateFilter.add("Last Week");

						ArrayAdapter<String> dateFilterViewAdapter = new ArrayAdapter<String>(getApplicationContext(),
								R.layout.footer_spinner, dateFilter);
						timeAnalyzeFilter.setAdapter(dateFilterViewAdapter);

						timeAnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

								if(getPlayerSetDataJSON_Analyze != null)
								{
									ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
									mplayerAnalytics.setAdapter(null);
									invokeAnalyticsReport();

								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {

							}

						});

						sortAnalyzeFilter = (Spinner) findViewById(R.id.sortAnalyzeFilter);
						List<String> sortFilter = new ArrayList<String>();
						sortFilter.add("Win Effectiveness");
						sortFilter.add("Loss Percentage");
						sortFilter.add("Stroke Type");
						//sortFilter.add("Destination");




						ArrayAdapter<String> sortAnalyzeFilterAdapter = new ArrayAdapter<String>(getApplicationContext(),
								R.layout.footer_spinner, sortFilter);
						sortAnalyzeFilter.setAdapter(sortAnalyzeFilterAdapter);
						sortAnalyzeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

								if(getPlayerSetDataJSON_Analyze != null)
								{
									if(mUtil.isOnline())
									{
										ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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



					}catch(Exception e){}

				}
			});

		}
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
			Toast.makeText(getApplicationContext(),item.getTitle().toString(),Toast.LENGTH_SHORT).show();
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
				ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
				ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
				ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
				ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(getApplicationContext(),
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
            final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
            ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(SequenceActivity.this,
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

                        asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
                        asyncTask.delegate = SequenceActivity.this;
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
            final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
            ArrayAdapter<String> playerHandOption = new ArrayAdapter<String>(SequenceActivity.this,
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

                        asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
                        asyncTask.delegate = SequenceActivity.this;
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
				final AlertDialog.Builder profileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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


	public void recordSequence()
	{

		sequenceSave.setEnabled(false);
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);

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
					asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
					asyncTask.delegate = SequenceActivity.this;
					try {
						String result = asyncTask.execute(params,queryparams,requestType).get();
						sequenceSave.setEnabled(true);
						if(result != null)
						{
							sequenceSave.setEnabled(true);

							if(result.contains("sequence recorded"))
							{
								toastMessage("Sequence Recorded");

								analytics_data.post(new Runnable(){
									@Override
									public void run() {




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
								});

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

	//url response
	@Override
	public void processFinish(String result,String methodName) {

		progressBar.setVisibility(View.GONE);
		if(result != null)
		{
			try{
				progressBar.setVisibility(View.GONE);
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
				else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getPlayerDetails"))
				{
					JSONParser jsonParser = new JSONParser();
					Object obj;
					try {
						obj = jsonParser.parse(result);
						JSONObject jsonObject = (JSONObject) obj;
						LayoutInflater inflater = getLayoutInflater();
						final View viewSequenceStrokeLayout = inflater.inflate(R.layout.player_details, null);
						TextView dialogTitle = (TextView) viewSequenceStrokeLayout.findViewById(R.id.dialogTitle);
						final TextView playerNameValue = (TextView) viewSequenceStrokeLayout.findViewById(R.id.playerNameValue);
						final TextView playerAffiliationId = (TextView) viewSequenceStrokeLayout.findViewById(R.id.playerAffiliationId);
						final TextView playerAcademy = (TextView) viewSequenceStrokeLayout.findViewById(R.id.playerAcademy);
						final TextView playerAssociation = (TextView) viewSequenceStrokeLayout.findViewById(R.id.playerAssociation);

						AlertDialog.Builder serviceDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
						dialogTitle.setText("Player Profile");
						if(jsonObject.containsKey("userName"))
							playerNameValue.setText(jsonObject.get("userName").toString());
						if(jsonObject.containsKey("affiliationId"))
							playerAffiliationId.setText(jsonObject.get("affiliationId").toString());
						if(jsonObject.containsKey("academy"))
							playerAcademy.setText(jsonObject.get("academy").toString());
						if(jsonObject.containsKey("association"))
							playerAssociation.setText(jsonObject.get("association").toString());
						serviceDialog.setView(viewSequenceStrokeLayout);
						serviceDialog.setCancelable(false);
						serviceDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();


							}
						});
						serviceDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						serviceDialog.show();


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
							player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player2JsonAnalyzeList);
							player2ViewAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player2JsonAnalyzeList);
							player2ViewFilter.setAdapter(player2ViewAdapter);
							player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);
							player2ViewAdapter.notifyDataSetChanged();
							player2AnalyzeAdapter.notifyDataSetChanged();



						}
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
						ShareHistoryAdapter shareHistoryAdapter = new ShareHistoryAdapter(getApplicationContext(), SequenceActivity.this, arrayObj,registeredPlayers1,sharedList);
						mStrokesListView.setAdapter(shareHistoryAdapter);




					} catch (ParseException e) {
						e.printStackTrace();
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
						progressBar.setVisibility(View.GONE);

						AlertDialog.Builder openFileDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
								//pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
				else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getPlayerSetData"))
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
							player1ViewAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player1JsonAnalyzeList);
							player1ViewFilter.setAdapter(player1ViewAdapter);
							player1ViewAdapter.notifyDataSetChanged();


						}
						if(getPlayerSetDataJSON.containsKey("player2Set") && getPlayerSetDataJSON.get("player2Set").toString().length() > 0)
						{
							player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON.get("player2Set").toString(), listType1);
							player2ViewAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player2JsonAnalyzeList);
							for(int j=0;j<=player2JsonAnalyzeList.size();j++)
							{
								//player2JsonAnalyzeList.get(j).getPlayerName());
							}
							player2ViewFilter.setAdapter(player2ViewAdapter);
							player2ViewAdapter.notifyDataSetChanged();


						}






					} catch (ParseException e) {
						e.printStackTrace();
					}



					String player1Name = player1JsonAnalyzeList.get(0).toString();
					String player1ID = player1AnalyzeAdapter.getItem(0).getUserId();
					String player2Name = "All";
					String player2ID = "";

					String dateFilterValue = "Any time";
					requestType = "GET";
					methodType = "viewSequence";
					params = "viewSequence?caller=" + Constants.caller + "&apiKey="
							+ Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
							+ player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&player1ID="+player1ID+"&player2ID="+player2ID;
					asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
					asyncTask.delegate = SequenceActivity.this;
					asyncTask.execute(params,queryparams,requestType);


				}
				else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("getPlayerSetData_Analyze"))
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
					if(getPlayerSetDataJSON_Analyze.containsKey("player1Set"))
					{
						if(getPlayerSetDataJSON_Analyze.get("player1Set") != null && getPlayerSetDataJSON_Analyze.get("player1Set").toString().length() > 0 &&
								getPlayerSetDataJSON_Analyze.get("player2Set") != null && getPlayerSetDataJSON_Analyze.get("player2Set").toString().length() > 0


								)
						{


							Gson gson = new Gson();
							Type listType1 = new TypeToken<List<PlayerJson>>(){}.getType();
							player1JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON_Analyze.get("player1Set").toString(), listType1);
							player1AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
									R.layout.header_spinner, player1JsonAnalyzeList);
							player1AnalyzeFilter.setAdapter(player1AnalyzeAdapter);

							System.out.println("analyst get data .. "+getPlayerSetDataJSON_Analyze.get(mSession.ypGetUserID()));


							if(getPlayerSetDataJSON.containsKey("player2Set") && getPlayerSetDataJSON.get("player2Set").toString().length() > 0)
							{
							    player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON.get("player2Set").toString(), listType1);
							    player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
                                            R.layout.header_spinner, player2JsonAnalyzeList);
							    player2AnalyzeFilter.setAdapter(player2AnalyzeAdapter);
							    player2AnalyzeAdapter.notifyDataSetChanged();
							}


                            if(getPlayerSetDataJSON_Analyze.get(mSession.ypGetUserID()) != null)
                            {
                                player2JsonAnalyzeList = gson.fromJson(getPlayerSetDataJSON_Analyze.get(mSession.ypGetUserID()).toString(), listType1);
                                player2AnalyzeAdapter = new ArrayAdapter<PlayerJson>(getApplicationContext(),
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



					asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
					asyncTask.delegate = SequenceActivity.this;
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


						shotInfoAdapter = new AnalyticsAdapter(getApplicationContext(),SequenceActivity.this,shotInfoList,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
						shotInfoAdapter1 = new AnalyticsAdapter1(getApplicationContext(), SequenceActivity.this, jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
						serviceInfoAdapter = new ServicePointsAdapter(getApplicationContext(),SequenceActivity.this,jsonValues,serviceKeyMap,masterDestinationKeyMap);
						serviceLossInfoAdapter = new ServiceLossAdapter(getApplicationContext(),SequenceActivity.this,jsonValues,serviceKeyMap,masterDestinationKeyMap);
						receiveInfoAdapter = new ReceiveAdapter(getApplicationContext(),SequenceActivity.this,jsonValues,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
						rallyAdapter = new RallyAdapter(getApplicationContext(),SequenceActivity.this,jsonValues);
						strokeAnalysisAdapter = new StrokeAnalysisAdapter(getApplicationContext(),SequenceActivity.this,jsonValues,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);

						errorAnalysisAdapter = new ErrorAnalysisAdapter(getApplicationContext(), SequenceActivity.this, jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);

						thirdBallAttackAdapter = new ThirdBallAttackAdapter(getApplicationContext(),SequenceActivity.this,jsonValues,serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
						fourthBallAdapter = new FourthBallAdapter(getApplicationContext(),SequenceActivity.this, jsonValues);
						serviceFaultAdapter = new ServiceFaultAdapter(getApplicationContext(), SequenceActivity.this, jsonValues, serviceKeyMap, masterDestinationKeyMap);
						serviceResponseAdapter = new ServiceResponseAdapter(getApplicationContext(), SequenceActivity.this, jsonValues, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
						final ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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
							LinearLayout rallyHeader = (LinearLayout) findViewById(R.id.rallyHeaderLayout);
							rallyHeader.setVisibility(View.GONE);
							LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
							strokeAnalysisHeader.setVisibility(View.GONE);
							LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
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
							LinearLayout rallyHeader = (LinearLayout) findViewById(R.id.rallyHeaderLayout);
							rallyHeader.setVisibility(View.VISIBLE);
							mplayerAnalytics.setAdapter(rallyAdapter);
						}
						else if(fetchParam.equalsIgnoreCase("Stroke Analysis"))
						{
							LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.strokeAnalysisHeaderLayout);
							strokeAnalysisHeader.setVisibility(View.VISIBLE);
							mplayerAnalytics.setAdapter(strokeAnalysisAdapter);
						}
						else if(fetchParam.equalsIgnoreCase("Error Analysis"))
						{
							LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.strokeAnalysisHeaderLayout);
							strokeAnalysisHeader.setVisibility(View.VISIBLE);
							mplayerAnalytics.setAdapter(errorAnalysisAdapter);
						}
						else if(fetchParam.equalsIgnoreCase("3rd Ball Attack"))
						{
							LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
							strokeAnalysisHeader.setVisibility(View.VISIBLE);

							mplayerAnalytics.setAdapter(thirdBallAttackAdapter);


						}
						else if(fetchParam.equalsIgnoreCase("4th Ball Shot"))
						{
							mplayerAnalytics.setAdapter(null);
							LinearLayout strokeAnalysisHeader = (LinearLayout) findViewById(R.id.thirdBallAnalysisHeaderLayout);
							strokeAnalysisHeader.setVisibility(View.GONE);
							LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
							fourthball_analysisLayout.setVisibility(View.VISIBLE);
						}






						final ImageView uparrow = (ImageView) findViewById(R.id.analyticsuparrow);
						final ImageView downarrow = (ImageView) findViewById(R.id.analyticsdownarrow);

						mplayerAnalytics.setOnScrollListener(new OnScrollListener() {
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

						if(fetchParam.equalsIgnoreCase("Last shot"))
						{
							sortedArray.clear();
							mplayerAnalytics.setAdapter(shotInfoAdapter1);
							ObjectMapper mapper = new ObjectMapper();
							sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
					   
					    /*
						if(jsonValues.size() > 0)
					    {		    	
							
									 
							
						    for (int i = 0; i < jsonValues.size(); i++) 
						    {
						    	int k=i+1;
						    	JSONObject jsonObj = (JSONObject)jsonValues.get(i);
								//sortedArray.add(jsonObj);
								
								ShotInfo shotInfoObj = create ShotInfo();
								String shortHand = "";
								String shortDestination = "";
								if(jsonObj.containsKey("strokeKey"))
								{
									String serviceKey = jsonObj.get("strokeKey").toString();
							   		int destinationIndex = serviceKey.lastIndexOf("-");
							   		shortHand = serviceKey.substring(0, destinationIndex);
							   		shortDestination = serviceKey.substring(destinationIndex+1, serviceKey.length());
							   
								}
							
						           
						           
								shotInfoObj.setShotHand(shortHand);
								shotInfoObj.setShotDestination(shortDestination);			
								shotInfoObj.setShotPlayed(jsonObj.get("strokesPlayed").toString());
								shotInfoObj.setShotWin(jsonObj.get("win").toString());
								shotInfoObj.setShotLoss(jsonObj.get("loss").toString());
								shotInfoObj.setCombinedShotKey(jsonObj.get("strokeKey").toString());
								if(jsonObj.containsKey("efficiency"))
									shotInfoObj.setShotEfficiency(jsonObj.get("efficiency").toString());
								else 
									shotInfoObj.setShotEfficiency("");
								shotInfoList.add(shotInfoObj);
								shotInfoAdapter.notifyDataSetChanged();					
						    }
					    }*/
						}
						else if(fetchParam.equalsIgnoreCase("Service Points"))
						{
							sortedArray.clear();
							mplayerAnalytics.setAdapter(serviceInfoAdapter);
							ObjectMapper mapper = new ObjectMapper();
							sortedArray = mapper.readValue(jsonValues.toString(),TypeFactory.collectionType(List.class, JSONObject.class));
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
				        /*Gson gson = create Gson();
				        Type collectionType = create TypeToken<ArrayList<JSONObject>>(){}.getType();
				        sortedArray = gson.fromJson(jsonValues.toString(), collectionType);
				         * 
				         * */

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
							TextView offensive_defensive_count = (TextView) findViewById(R.id.offensive_defensive_count);
							TextView offensive_offensive_count = (TextView) findViewById(R.id.offensive_offensive_count);
							TextView defensive_offensive_count = (TextView) findViewById(R.id.defensive_offensive_count);
							TextView defensive_defensive_count = (TextView) findViewById(R.id.defensive_defensive_count);
							TextView offensive_lost_count = (TextView) findViewById(R.id.offensive_lost_count);
							TextView defensive_lost_count = (TextView) findViewById(R.id.defensive_lost_count);
							TextView offensive_win_count = (TextView) findViewById(R.id.offensive_win_count);
							TextView defensive_win_count = (TextView) findViewById(R.id.defensive_win_count);
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
					}
					else
					{

					}
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
								final ViewSequenceAdapter sequenceAdapter = new ViewSequenceAdapter(getApplicationContext(), viewSequence);

								mStrokesListView.setAdapter(sequenceAdapter);
								final ImageView uparrow = (ImageView) findViewById(R.id.reviewuparrow);
								final ImageView downarrow = (ImageView) findViewById(R.id.reviewdownarrow);

								mStrokesListView.setOnScrollListener(new OnScrollListener() {
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

					/*if(jsonObj.get("message").toString().equalsIgnoreCase("sequence deleted"))
					{

						String player1Name = player1ViewFilter.getSelectedItem().toString();
						String player2Name = player2ViewFilter.getSelectedItem().toString();
						String dateFilterValue = dateFilterView.getSelectedItem().toString();
						String player1ID = player1ViewFilter.getTag().toString();
						String player2ID = player2ViewFilter.getTag().toString();

						requestType = "GET";
						methodType = "viewSequence";
						params = "viewSequence?caller=" + Constants.caller + "&apiKey="
								+ Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
								+ player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&player1ID="+player1ID+"&player2ID="+player2ID;

						asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
						asyncTask.delegate = SequenceActivity.this;
						asyncTask.execute(params,queryparams,requestType);


					}
					else
					{
						toastMessage("Could not delete sequence");
					}*/
				}
				else if(requestType.equalsIgnoreCase("POST") && methodName.equalsIgnoreCase("recordPlayerProfile"))
				{
					player1ProfileSettings = false;
					player2ProfileSettings = false;
				}
				else if(requestType.equalsIgnoreCase("POST") && methodName.equalsIgnoreCase("recordPlayerSequence"))
				{
					sequenceSave.setEnabled(true);

					if(result.contains("sequence recorded"))
					{
						toastMessage("Sequence Recorded");

						analytics_data.post(new Runnable(){
							@Override
							public void run() {




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
						});

					}
					else
					{
						//sequenceSave.setEnabled(true);
						toastMessage("Could not record sequence");
					}
				}

				else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("playerProfile"))
				{
					if(result != null)
					{
						JSONParser jsonParser = new JSONParser();
						JSONArray jsonArr = null;
						try {
							jsonArr = (JSONArray) jsonParser.parse(result);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if(jsonArr.size() > 0)
						{


							for(int m=0; m<jsonArr.size();m++)
							{
								JSONObject playerObj = (JSONObject) jsonArr.get(m);
								DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);
								formatter.setTimeZone(TimeZone.getTimeZone("IST"));
								if(playerObj.containsKey("player1Profile"))
								{
									JSONObject player1 = (JSONObject) playerObj.get("player1Profile");

									if(player1.containsKey("playerHand"))
										player1Hand = player1.get("playerHand").toString();
									if(player1.containsKey("foreHandRT"))
									{
										JSONArray foreHandObj = (JSONArray)player1.get("foreHandRT");
										if(foreHandObj.size() > 0)
										{
											player1FH.clear();
											for(int h=0; h< foreHandObj.size();h++)
											{
												JSONObject obj = (JSONObject) foreHandObj.get(h);
												Rubber rubberObj = new Rubber();

												try
												{
													if(obj.containsKey("rubberDate"))
													{
														Date result1 = formatter.parse(obj.get("rubberDate").toString());
														rubberObj.setRubberDate(new SimpleDateFormat("dd/MM/yy").format(result1));

													}
												}catch(Exception e)
												{
													rubberObj.setRubberDate("");

												}

												if(obj.containsKey("rubberType"))
													rubberObj.setRubberType(obj.get("rubberType").toString());
												player1FH.add(rubberObj);
												player1FHAdapter.notifyDataSetChanged();
											}
										}

									}



									if(player1.containsKey("backHandRT"))
									{
										JSONArray backHandObj = (JSONArray)player1.get("backHandRT");
										if(backHandObj.size() > 0)
										{
											player1BH.clear();

											for(int h=0; h< backHandObj.size();h++)
											{
												JSONObject obj = (JSONObject) backHandObj.get(h);
												Rubber rubberObj = new Rubber();
												try
												{
													if(obj.containsKey("rubberDate"))
													{
														Date result1 = formatter.parse(obj.get("rubberDate").toString());
														rubberObj.setRubberDate(new SimpleDateFormat("dd/MM/yy").format(result1));

													}
												}catch(Exception e)
												{
													rubberObj.setRubberDate("");

												}


												if(obj.containsKey("rubberType"))
													rubberObj.setRubberType(obj.get("rubberType").toString());
												player1BH.add(rubberObj);
												player1BHAdapter.notifyDataSetChanged();
											}
										}

									}

								}
								if(playerObj.containsKey("player2Profile"))
								{
									JSONObject player2 = (JSONObject) playerObj.get("player2Profile");
									if(player2.containsKey("playerHand"))
										player2Hand = player2.get("playerHand").toString();
									if(player2.containsKey("foreHandRT"))
									{
										JSONArray foreHandObj = (JSONArray)player2.get("foreHandRT");
										if(foreHandObj.size() > 0)
										{
											player2FH.clear();
											for(int h=0; h< foreHandObj.size();h++)
											{
												JSONObject obj = (JSONObject) foreHandObj.get(h);
												Rubber rubberObj = new Rubber();
												try
												{
													if(obj.containsKey("rubberDate"))
													{
														Date result1 = formatter.parse(obj.get("rubberDate").toString());
														rubberObj.setRubberDate(new SimpleDateFormat("dd/MM/yy").format(result1));

													}
												}catch(Exception e)
												{
													rubberObj.setRubberDate("");

												}


												if(obj.containsKey("rubberType"))
													rubberObj.setRubberType(obj.get("rubberType").toString());
												player2FH.add(rubberObj);
												player2FHAdapter.notifyDataSetChanged();
											}

										}

									}

									if(player2.containsKey("backHandRT"))
									{
										JSONArray backHandObj = (JSONArray)player2.get("backHandRT");
										if(backHandObj.size() > 0)
										{
											player1BH.clear();

											for(int h=0; h< backHandObj.size();h++)
											{
												JSONObject obj = (JSONObject) backHandObj.get(h);
												Rubber rubberObj = new Rubber();
												try
												{
													if(obj.containsKey("rubberDate"))
													{
														Date result1 = formatter.parse(obj.get("rubberDate").toString());
														rubberObj.setRubberDate(new SimpleDateFormat("dd/MM/yy").format(result1));

													}
												}catch(Exception e)
												{
													rubberObj.setRubberDate("");

												}

												if(obj.containsKey("rubberType"))
													rubberObj.setRubberType(obj.get("rubberType").toString());
												player2BH.add(rubberObj);
												player2BHAdapter.notifyDataSetChanged();
											}

										}

									}

								}
							}
						}
					}
				}
				else if(requestType.equalsIgnoreCase("GET") && methodName.equalsIgnoreCase("viewSequence"))
				{

					ArrayList<SequenceDetail> viewSequence = new ArrayList<SequenceDetail>();
					Gson gson = new Gson();
					Type listType = new TypeToken<List<SequenceDetail>>() {}.getType();

					viewSequence = gson.fromJson(result, listType);
					final ViewSequenceAdapter sequenceAdapter = new ViewSequenceAdapter(getApplicationContext(), viewSequence);

					mStrokesListView.setAdapter(sequenceAdapter);
					final ImageView uparrow = (ImageView) findViewById(R.id.reviewuparrow);
					final ImageView downarrow = (ImageView) findViewById(R.id.reviewdownarrow);

					mStrokesListView.setOnScrollListener(new OnScrollListener() {
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

			}catch(Exception e){
			}

		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public void downloadAnalyticsData(){
		int permission = ContextCompat.checkSelfPermission(SequenceActivity.this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {

			if (ActivityCompat.shouldShowRequestPermissionRationale(SequenceActivity.this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
		progressBar.setVisibility(View.VISIBLE);

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




		asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
		asyncTask.delegate = SequenceActivity.this;
		asyncTask.execute(params,queryparams,requestType);
	}

	protected void makeRequest() {
		ActivityCompat.requestPermissions(this,
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
	public void invokeSequenceReview(String player1Name,String player2Name,String dateFilterValue,String player1ID,String player2ID,String playerList)
	{
		if(chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("View Sequence") || chooseReviewType.getSelectedItem().toString().equalsIgnoreCase("Share Sequence"))
		{
			LinearLayout shareHistoryHeaderLayout = (LinearLayout) findViewById(R.id.shareHistoryHeaderLayout);
			shareHistoryHeaderLayout.setVisibility(View.GONE);
			if(mUtil.isOnline())
			{
				requestType = "GET";
				methodType = "viewSequence";
				params = "viewSequence?caller=" + Constants.caller + "&apiKey="
						+ Constants.apiKey + "&userId=" + mSession.ypGetUserID() + "&player1Name="
						+ player1Name+"&player2Name="+player2Name+"&dateFilter="+dateFilterValue+"&player1ID="+player1ID+"&player2ID="+player2ID;

				System.out.println("view sequence params : "+params);
				asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
				asyncTask.delegate = SequenceActivity.this;
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

						asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
						asyncTask.delegate = SequenceActivity.this;
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
			LinearLayout shareHistoryHeaderLayout = (LinearLayout) findViewById(R.id.shareHistoryHeaderLayout);
			shareHistoryHeaderLayout.setVisibility(View.VISIBLE);
			if(mUtil.isOnline())
			{
				requestType = "GET";
				methodType = "shareHistory";
				params = "getShareHistory?caller=" + Constants.caller + "&apiKey="
						+ Constants.apiKey + "&userId=" + mSession.ypGetUserID();

				asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
				asyncTask.delegate = SequenceActivity.this;
				asyncTask.execute(params,queryparams,requestType);

			}
			else
			{
				toastMessage("No Internet");
			}
		}


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
				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
				sortLayout.setVisibility(View.VISIBLE);

				serviceHeaderLayout.setVisibility(View.VISIBLE);
				receiveHeaderLayout.setVisibility(View.GONE);
				rallyHeader.setVisibility(View.GONE);
				strokeAnalysisHeaderLayout.setVisibility(View.GONE);
				thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
				handTitle = (TextView) findViewById(R.id.serviceTitle);
				handTitle.setText("stroke");
				destinationTitle = (TextView) findViewById(R.id.destinationTitle);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
				sortLayout.setVisibility(View.GONE);


				serviceHeaderLayout.setVisibility(View.VISIBLE);
				receiveHeaderLayout.setVisibility(View.GONE);
				rallyHeader.setVisibility(View.GONE);
				strokeAnalysisHeaderLayout.setVisibility(View.GONE);
				thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);

				handTitle = (TextView) findViewById(R.id.serviceTitle);
				handTitle.setText("service");
				destinationTitle = (TextView) findViewById(R.id.destinationTitle);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
				sortLayout.setVisibility(View.GONE);
				handTitle = (TextView) findViewById(R.id.serviceTitle);
				handTitle.setText("service");
				destinationTitle = (TextView) findViewById(R.id.destinationTitle);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
				sortLayout.setVisibility(View.GONE);

				serviceHeaderLayout.setVisibility(View.VISIBLE);
				receiveHeaderLayout.setVisibility(View.GONE);
				rallyHeader.setVisibility(View.GONE);
				strokeAnalysisHeaderLayout.setVisibility(View.GONE);
				thirdBallAnalysisHeaderLayout.setVisibility(View.GONE);
				handTitle = (TextView) findViewById(R.id.serviceTitle);
				handTitle.setText("service");
				destinationTitle = (TextView) findViewById(R.id.destinationTitle);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
				sortLayout.setVisibility(View.GONE);
				handTitle = (TextView) findViewById(R.id.serviceTitle);
				handTitle.setText("service");
				destinationTitle = (TextView) findViewById(R.id.destinationTitle);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
				fourthball_analysisLayout.setVisibility(View.GONE);
				LinearLayout sortLayout = (LinearLayout) findViewById(R.id.sortLayout);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
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

				LinearLayout fourthball_analysisLayout = (LinearLayout) findViewById(R.id.fourthball_analysisLayout);
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



			asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
			asyncTask.delegate = SequenceActivity.this;
			asyncTask.execute(params,queryparams,requestType);
			ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
			mplayerAnalytics.setAdapter(null);
		}
		else if(!(mUtil.isOnline()))
		{
			toastMessage("No Internet");

		}
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

		serviceInfoAdapter = new ServicePointsAdapter(getApplicationContext(),SequenceActivity.this,arrayObj,serviceKeyMap,masterDestinationKeyMap);
		serviceLossInfoAdapter = new ServiceLossAdapter(getApplicationContext(),SequenceActivity.this,arrayObj,serviceKeyMap,masterDestinationKeyMap);
		receiveInfoAdapter = new ReceiveAdapter(getApplicationContext(),SequenceActivity.this,arrayObj,serviceKeyMap,masterDestinationKeyMap,strokeKeyMap);
		rallyAdapter = new RallyAdapter(getApplicationContext(), SequenceActivity.this, arrayObj);
		serviceFaultAdapter = new ServiceFaultAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap);
		serviceResponseAdapter = new ServiceResponseAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
		errorAnalysisAdapter = new ErrorAnalysisAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
		strokeAnalysisAdapter = new StrokeAnalysisAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
		receiveInfoAdapter = new ReceiveAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
		thirdBallAttackAdapter = new ThirdBallAttackAdapter(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);
		shotInfoAdapter1 = new AnalyticsAdapter1(getApplicationContext(), SequenceActivity.this, arrayObj, serviceKeyMap, masterDestinationKeyMap, strokeKeyMap);

		final ListView mplayerAnalytics = (ListView) findViewById(R.id.playerAnalyticsListView);
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


	public void toastMessage(String message)
	{
		//Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

		mUtil.toastMessage(message,getApplicationContext());

	}


	public class ViewSequenceAdapter extends BaseAdapter{
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



			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);

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
					requestType = "POST";
					params= "fetchSequenceRecord?";
					methodType="fetchSequenceRecord";
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
					asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
					try {
						String result = asyncTask.execute(params,paramObject.toString(),requestType).get();
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

												AlertDialog.Builder sequenceViewDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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

												ViewStrokeShotAdapter shotAdapter = new ViewStrokeShotAdapter(getApplicationContext(), sequenceDetail.getStrokesPlayed(), sequenceDetail, serviceKeyMap, strokeKeyMap, masterDestinationKeyMap);
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

					AlertDialog.Builder deleteSequenceDialog = new AlertDialog.Builder(SequenceActivity.this,R.style.AlertDialogTheme);
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
							asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
							asyncTask.delegate = SequenceActivity.this;
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
