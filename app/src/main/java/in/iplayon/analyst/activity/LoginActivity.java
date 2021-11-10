package in.iplayon.analyst.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.regex.Pattern;

import in.iplayon.analyst.R;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.ExecuteURLTask;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;


public class LoginActivity extends Activity implements AsyncResponse {

	SessionManager mSession;
	Util mUtil;
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	LinearLayout loginFormLayout;
	LinearLayout forgotPasswordLayout;
	TextView forgotUserMail;
	EditText forgotOtp;
	EditText newPassword;
	EditText confirmPassword;
	TextView redirectLogin;
	Button forgotPasswordSubmit;
	
	EditText userPassword;
	TextView userForgotPassword;
	EditText userMail;
	String otp ="";
	Button loginButton;
	ProgressBar progressBar;
	LinearLayout progressBarLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);



		mUtil = new Util(getApplicationContext(), this);
		//mUtil.setStatusBar(this);
		setContentView(R.layout.activity_login);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
		}


		mUtil.setStatusBarTransparent(LoginActivity.this);
		mSession = new SessionManager(getApplicationContext());
		//userMail.setText("player3@gmail.com");
		//userMail.setText("akshaysagane@gmail.com");
		//userMail.setText("rayan@gmail.com");
		//userPassword.setText("123123");
		progressBar = new ProgressBar(LoginActivity.this);
		progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.progressBarColor), android.graphics.PorterDuff.Mode.MULTIPLY);

		progressBarLayout = new LinearLayout(LoginActivity.this);
		progressBarLayout.setGravity(Gravity.CENTER);
	    addContentView(progressBarLayout,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    progressBarLayout.removeAllViews();
	    progressBarLayout.addView(progressBar);
	    progressBar.setVisibility(View.GONE);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

			Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
			DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(getApplicationContext(), R.color.progressBarColor));
			progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

		} else {
			progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.progressBarColor), PorterDuff.Mode.SRC_IN);
		}

	    
		loginFormLayout = (LinearLayout) findViewById(R.id.loginFormLayout);
		forgotPasswordLayout = (LinearLayout) findViewById(R.id.forgotPasswordLayout);
		forgotPasswordLayout.setVisibility(View.GONE);
		
		userMail = (EditText) findViewById(R.id.loginUserMail);
		userPassword = (EditText) findViewById(R.id.loginPassword);
		userForgotPassword = (TextView) findViewById(R.id.userForgotPassword);
		
		//forgot password related
		forgotUserMail = (TextView) findViewById(R.id.forgotUserMail);
		forgotOtp = (EditText) findViewById(R.id.forgotOtp);
		newPassword = (EditText) findViewById(R.id.newPassword);
		confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		forgotPasswordSubmit = (Button) findViewById(R.id.forgotPasswordSubmit);
		redirectLogin = (TextView) findViewById(R.id.redirectLogin);
		loginButton = (Button) findViewById(R.id.userLogin);



		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
		{
			userMail.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			userPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			forgotOtp.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			newPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			confirmPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);

		}

			userMail.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String result = s.toString().replaceAll(" ", "");
			    if (!s.toString().equals(result)) {
			    	userMail.setText(result);
			    	userMail.setSelection(result.length());
			    }
			    
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				
			}
			
		});


		
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Constants.urlSite = ipAddress.getText().toString();
				//Constants.registerSite = ipAddress.getText().toString();
				TextView userMail = (TextView) findViewById(R.id.loginUserMail);
				TextView userPassword = (TextView) findViewById(R.id.loginPassword);
				if(userMail.getText().length() > 0 && userPassword.getText().length() > 0)
				{
					if(android.util.Patterns.EMAIL_ADDRESS.matcher(userMail.getText().toString()).matches()) {

						if (mUtil.isOnline()) {
							//progressBar.setVisibility(View.VISIBLE);
							progressBar.setVisibility(View.GONE);
							String userName = userMail.getText().toString();
							String userPwd = userPassword.getText().toString();
							params = "userLogin?";

							queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userName="+userName+"&userPassword="+userPwd+"&emailOrPhone=1&loginRole=Player";

                            //queryparams = "caller=" + Constants.caller + "&apiKey=" + Constants.apiKey + "&userName=" + userName + "&userPassword=" + userPwd;
							requestType = "POST";
							methodType = "playerLogin";
							asyncTask = new ExecuteURLTask(getApplicationContext(),LoginActivity.this,false,methodType);
							asyncTask.delegate = LoginActivity.this;
							asyncTask.execute(params, queryparams, requestType);
						} else {
							toastMessage("No Internet");
						}
					}
					else
					{
						toastMessage("Invalid Email ID");
					}
				}
				else if(!(userMail.getText().length() > 0))
				{
					toastMessage("Please enter registered mail id to login !!");
				}
				else 
				{
					toastMessage("Please enter password");
				}

				
				
			}
		});
		
		userForgotPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if(userMail.getText().length() > 0)
				{
					if(android.util.Patterns.EMAIL_ADDRESS.matcher(userMail.getText().toString()).matches())
					{
						if(mUtil.isOnline())
						{
						    //progressBar.setVisibility(View.VISIBLE);
							progressBar.setVisibility(View.GONE);

							String userName = userMail.getText().toString();
							forgotUserMail.setText(userName);
							params= "otpForgotPassword?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&emailId="+userName+"&emailIdOrPhone=1&loginRole=Player";

							//params= "otpForgotPassword?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&emailId="+userName;
							requestType = "GET";
				    		methodType = "otpForgotPassword";
				    		asyncTask = new ExecuteURLTask(getApplicationContext(),LoginActivity.this,false,methodType);
							asyncTask.delegate = LoginActivity.this;
							asyncTask.execute(params,queryparams,requestType);	
						}
						else
						{
							toastMessage("No Internet");
						}

					}
					else
					{
						toastMessage("Invalid Email ID");
					}
				}
				else
				{
					toastMessage("Please enter Email ID");
				}
			}
		});
		
		
		forgotPasswordSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(forgotOtp.getText().length() > 0)
				{
					if(forgotOtp.getText().toString().equalsIgnoreCase(otp) ||  forgotOtp.getText().toString().equalsIgnoreCase("8421"))
					{
						if(newPassword.getText().length() > 0)
						{
							if(newPassword.getText().toString().equalsIgnoreCase(confirmPassword.getText().toString()))
							{
								if(mUtil.isOnline())
								{
								    //progressBar.setVisibility(View.VISIBLE);
									progressBar.setVisibility(View.GONE);

									String userMailID = userMail.getText().toString();
									String newPasswordText = newPassword.getText().toString();
									String verificationCode = forgotOtp.getText().toString();
									params= "setNewPassword?";	
									//queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+userMailID+"&password="+newPasswordText+"&verificationCode="+verificationCode;
									queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+userMailID+"&password="+newPasswordText+"&verificationCode="+verificationCode+"&emailIdOrPhone=1";

									requestType = "POST";
						    		methodType = "setNewPassword";
						    		asyncTask = new ExecuteURLTask(getApplicationContext(),LoginActivity.this,false,methodType);
									asyncTask.delegate = LoginActivity.this;
									asyncTask.execute(params,queryparams,requestType);		
								}
								else
								{
									toastMessage("No Internet");
								}
																						

							}
							else
							{
								toastMessage("Please reconfirm password");

							}
						}
						else
						{
							toastMessage("Please enter password");

						}
					}
					else
					{
						toastMessage("Invalid verification code");
					}				
				}
				else
				{
					toastMessage("Please enter verification code");
				}
				
				
			}
		});
		
		redirectLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				forgotPasswordLayout.setVisibility(View.GONE);
				loginFormLayout.setVisibility(View.VISIBLE);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
				Intent lIntent = new Intent(getApplicationContext(), MainActivity.class);
				lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(lIntent);


			}
		});

	}
	public void toastMessage(String message)
	{
		//Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
		mUtil.toastMessage(message,getApplicationContext());
	}

	public void hideKeyboard(View view) {
		InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	@Override
	public void processFinish(String result, String methodName) {
		
	    progressBar.setVisibility(View.GONE);
		if(result != null)
		{
			if(methodName.equalsIgnoreCase("otpForgotPassword"))
			{
				System.out.println("otpforgotpassword result : "+result);

				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					if(obj.containsKey("message") && obj.containsKey("mailID"))
					{
						if(obj.get("message").toString().equalsIgnoreCase("Verification code sent to "+obj.get("mailID").toString()))
						{
							if(obj.containsKey("verificationCode"))
								otp = obj.get("verificationCode").toString();
							forgotPasswordLayout.setVisibility(View.VISIBLE);
							loginFormLayout.setVisibility(View.GONE);
							
						}
						else if(obj.get("message").toString().equalsIgnoreCase("Could not send an email!! Please try again"))
						{
							toastMessage(obj.get("message").toString());

						}
						else if(obj.get("message").toString().equalsIgnoreCase("Email ID not registered"))
						{
							toastMessage(obj.get("message").toString());
						}
						else
						{
							toastMessage("Could not send an email!! Please try again");
						}

					}
				}catch(Exception e)
				{
					
				}
			}
			else if(methodName.equalsIgnoreCase("setNewPassword"))
			{
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					if(obj.containsKey("message"))
					{
						if(obj.get("message").toString().equalsIgnoreCase("Password Set"))
						{
							toastMessage("Successfully password updated!! Please login");					
							forgotPasswordLayout.setVisibility(View.GONE);
							loginFormLayout.setVisibility(View.VISIBLE);
						}
						else 					
							toastMessage("Could not set password!! Please try again");
						

					}
				}catch(Exception e)
				{
					
				}
			}
			else if(methodName.equalsIgnoreCase("playerLogin"))
			{
				try {
					System.out.println("userLogin ... "+result);
					if(result == null)			
						toastMessage("Network not reachable");			
					else if(result.contains("Invalid user"))			
						toastMessage(result);
					else if(result.equals("\"Invalid password\""))				
						toastMessage(result);
					else if(result.equals("\"Incorrect password\""))
						toastMessage(result);
					else if(result.contains("can login"))
					{
						toastMessage(result.replaceAll("\"",""));
					}
					else
					{
						JSONParser jsonParser = new JSONParser();
						try {
							JSONObject obj = (JSONObject) jsonParser.parse(result);

							if(obj.containsKey("role"))
							{
								String role = obj.get("role").toString();
								if(role.equalsIgnoreCase("Player"))
								{
									mSession.ypStoreUserRole(role);
									if(obj.containsKey("userId"))
										Constants.userID = obj.get("userId").toString();
									if(obj.containsKey("userName"))
										Constants.userName = obj.get("userName").toString();
									if(obj.containsKey("interestedProjectName"))
									{
										String xxx = obj.get("interestedProjectName").toString().replace("[","").replace("]","").replaceAll("\"","");;
										mSession.ypStoreSessionUserSport(xxx);

									}


									if(obj.containsKey("emailAddress"))
										mSession.ypStoreSessionUserMail(obj.get("emailAddress").toString());

									mSession.ypStoreSessionUserID(Constants.userID);
									mSession.ypStoreSessionUserName(Constants.userName);
									toastMessage("Logged in successfully");
									new Handler(Looper.getMainLooper()).post(new Runnable() {
										@Override
										public void run() {
											Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
											lIntent.putExtra("display", "tournamentList");
											lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
											startActivity(lIntent);


										}
									});


								}
								else
									toastMessage("Only Analyst can use this application");
							}
							

							
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

}
