package in.iplayon.analyst.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;

import in.iplayon.analyst.R;
import in.iplayon.analyst.util.AsyncResponse;
import in.iplayon.analyst.util.Constants;
import in.iplayon.analyst.util.SessionManager;
import in.iplayon.analyst.util.Util;


public class ExecuteURLTask extends AsyncTask<String, String, String> {
	public AsyncResponse delegate = null;
	String lUserName;
	String lPassword;
	String urlResponse;
	String params;
	String queryParams;
	String requestType;
	SessionManager mSession;
	Context mContext;
	BufferedReader in = null;
	Boolean mJsonType = false;
	String mMethodName = "";
	private Util mUtil;
	ProgressDialog dialog;
	Activity mActivity;


	public ExecuteURLTask(Context lContext,String lMethodName) {
		this.mContext = lContext;
		this.mMethodName = lMethodName;
		mSession = new SessionManager(this.mContext);
		this.mUtil = new Util(lContext);
	}

	public ExecuteURLTask(Context lContext, Activity lActivity, String lMethodName) {
		this.mContext = lContext;
		this.mActivity = lActivity;
		this.mMethodName = lMethodName;
		mSession = new SessionManager(this.mContext);
		this.mUtil = new Util(lContext);
		this.dialog = new ProgressDialog(mActivity, R.style.progressBarStyle);

	}

	public ExecuteURLTask(Context lContext, Activity lActivity, Boolean jsonType, String lMethodName) {
		this.mContext = lContext;
		this.mActivity = lActivity;
		this.mJsonType = jsonType;
		this.mMethodName = lMethodName;
		mSession = new SessionManager(this.mContext);
		this.mUtil = new Util(lContext);
		this.dialog = new ProgressDialog(mActivity, R.style.progressBarStyle);

	}

	protected void onPreExecute() {
		if(this.dialog != null && mActivity != null)
		{
			// this.dialog.setMessage("Please wait");
			this.dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
			this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			this.dialog.setIndeterminate(true);
			this.dialog.setCancelable(false);
			this.dialog.show();
		}
	}


	protected String doInBackground(String... arg0) {

		if (!isCancelled())
		{
			params = arg0[0];
			queryParams = arg0[1];
			requestType = arg0[2];
			URL url;
			HttpURLConnection urlConnection = null;
			try {
				System.setProperty("http.keepAlive", "false");
				if (mUtil.isOnline()) {
					url = new URL(Constants.urlSite + "" + params);
					URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
					url = uri.toURL();
					urlConnection = (HttpURLConnection) url.openConnection();
					//urlConnection.setReadTimeout(5000);
					if (requestType.equalsIgnoreCase("GET")) {
						urlConnection.setRequestMethod("GET");

					} else {
						urlConnection.setRequestMethod("POST");
						urlConnection.addRequestProperty("Content-Type", "application/" + "POST");
						if (queryParams != "") {
							if (mJsonType)
								urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

							else {
								urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
							}
							urlConnection.setRequestProperty("Content-Length", Integer.toString(queryParams.getBytes().length));
							urlConnection.setRequestProperty("Content-Language", "en-US");
							urlConnection.setUseCaches(false);
							urlConnection.setDoInput(true);
							urlConnection.setDoOutput(true);
							urlConnection.getOutputStream().write(queryParams.getBytes());

						}
					}

					int responseCode = urlConnection.getResponseCode();

					if (responseCode == HttpURLConnection.HTTP_OK) {

						InputStream responseStream = new BufferedInputStream(urlConnection.getInputStream());

						in = new BufferedReader(new InputStreamReader(responseStream));
						String inputLine;
						StringBuffer response = new StringBuffer();

						if(isCancelled())
						{
							//cancelTask = true; // (OPTIONAL) THIS IS AN ADDITIONAL CHECK I HAVE USED. THINK OF IT AS A FAIL SAFE.
							return urlResponse;

						}
						else
						{
							while ((! isCancelled()) && (inputLine = in.readLine()) != null)
							{
								response.append(inputLine); }

						}






						urlResponse = response.toString();


						responseStream.close();

					}
				} else {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
							// mUtil.toastMessage("No Internet", mContext);


						}
					});
				}


			} catch (final NoRouteToHostException e) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
						// mUtil.toastMessage(e.getMessage(), mContext);


					}
				});

			} catch (final java.net.UnknownHostException e) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
						//mUtil.toastMessage(e.getMessage(), mContext);


					}
				});

			} catch (SocketTimeoutException e) {

				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
						// mUtil.toastMessage("Connection timed out. !!", mContext);


					}
				});
			} catch (ConnectException e) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(mContext, "Could not connect", Toast.LENGTH_SHORT).show();
						//mUtil.toastMessage("Could not connect !!", mContext);

					}
				});


			} catch (final Exception e) {
				e.printStackTrace();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(mContext, "Connection timed out.", Toast.LENGTH_SHORT).show();
						// mUtil.toastMessage(e.getMessage(), mContext);


					}
				});

			} finally {
				if (urlConnection != null)
					urlConnection.disconnect();
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}


			}
			return urlResponse;

		}
		else
			return  null;

	}

	protected void onPostExecute(String result) {
		if (dialog != null && dialog.isShowing())
		{
			dialog.dismiss();
		}

		if (delegate != null)
			delegate.processFinish(urlResponse, mMethodName);
	}

	@Override
	protected void onCancelled() {
		//dialog.hide(); /*hide the progressbar dialog here...*/

		super.onCancelled();
	}




}