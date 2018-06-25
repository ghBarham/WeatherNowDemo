package com.madfooat.weather.android.demo.app.service;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.madfooat.weather.android.demo.app.R;
import com.madfooat.weather.android.demo.app.application.WeatherApplication;
import com.madfooat.weather.android.demo.app.beans.City;
import com.madfooat.weather.android.demo.app.common.Constant;
import com.madfooat.weather.android.demo.app.exception.DataFetchingException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author gbarham
 */
public class DataService {

    private Gson gson = new Gson();

    public void getGeoLocationData(String URL, final Class<?> dataType, final ArrayAdapter adapter, final List adapterDS, final Dialog loadingDialog, final Spinner spinnerToNotify) {
        WeatherApplication.getHttpClient().get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response == null) {
                        throw new DataFetchingException("response is null", Constant.EXCEPTION_CODE.DATA_API_NOT_RESPONDING);
                    }

                    boolean status = response.getBoolean(Constant.API_RESPONSE_KEYS.SUCCESS_STATUS);
                    if (!status) {
                        throw new DataFetchingException("response status is failed", Constant.EXCEPTION_CODE.DATA_API_RESPONSE_STATUS_FAILED);
                    }

                    // convert response to list of dataType
                    List results = convertFromJson(response, dataType);

                    // update UI
                    updateUIThread(results, adapter, adapterDS, loadingDialog, spinnerToNotify);
                } catch (DataFetchingException exp) {
                    Log.e(Constant.LOG_TAG, exp.getMsg(), exp);
                    notifyClientToRetry(loadingDialog, exp.getCode());
                } catch (Exception e) {
                    Log.e(Constant.LOG_TAG, e.getMessage(), e);
                    notifyClientToRetry(loadingDialog, Constant.EXCEPTION_CODE.UNEXPECTED);
                }
            }
        });
    }

    public void getCityWeather(final Context context, final City city, final TextView weatherHolderTV, final RelativeLayout mainHolderRL) {
        String url = Constant.API_URLS.CITY_WEATHER_URL + city.getLat() + "," + city.getLon();
        WeatherApplication.getHttpClient().get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Object jsonObject = response.get(Constant.API_RESPONSE_KEYS.WEATHER_API_CURRENTLY);
                    if (jsonObject instanceof JSONObject) {
                        String temperature = ((JSONObject) jsonObject).getString(Constant.API_RESPONSE_KEYS.WEATHER_API_TEMPERATUURE);
                        if (temperature != null && !temperature.isEmpty()) {
                            Double fahrenheitValue = Double.valueOf(temperature);
                            Double celsiusValue = Math.floor(fahrenheitValue - 32) * 0.5556;

                            String finalValue = fahrenheitValue.toString() + " F \n" + String.format("%.2f", celsiusValue) + " C";
                            weatherHolderTV.setText(finalValue);

                            if (celsiusValue <= 15) {
                                mainHolderRL.setBackground(context.getResources().getDrawable(R.drawable.cold));
                            } else {
                                mainHolderRL.setBackground(context.getResources().getDrawable(R.drawable.hot));
                            }
                        }
                    }

                } catch (Exception e) {
                    String msg = context.getResources().getString(R.string.weather_loading_failed);
                    Toast.makeText(context, msg + city.getLabel(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * convert to JSON
     *
     * @param response
     * @param dataType
     * @return
     */
    private List convertFromJson(JSONObject response, Class<?> dataType) {
        List results = new ArrayList<>();
        Iterator<?> keys = response.keys();
        while (keys.hasNext()) {
            try {
                String key = (String) keys.next();
                if (response.get(key) instanceof JSONObject) {
                    Object instance = gson.fromJson(((JSONObject) response.get(key)).toString(), dataType);
                    results.add(instance);
                }
            } catch (Exception e) {
                // failed to parse unexpected value, continue
                Log.e(Constant.LOG_TAG, e.getMessage(), e);
            }
        }
        return results;
    }

    /**
     * notify client to retry after error
     *
     * @param loadingDialog
     * @param errorCode
     */
    private void notifyClientToRetry(Dialog loadingDialog, String errorCode) {
        if (loadingDialog == null) {
            return;
        }

        loadingDialog.dismiss();

        //TODO: map msgs to codes and pass it instead of errorCode
        Toast.makeText(loadingDialog.getContext(), errorCode, Toast.LENGTH_LONG).show();

    }

    /**
     * notify adapter and UI thead component with the new data
     *
     * @param newData
     * @param adapter
     * @param adapterDS
     */
    private void updateUIThread(List newData, ArrayAdapter adapter, List adapterDS, Dialog loadingDialog, Spinner spinnerToNotify) {
        if (adapter != null) {
            adapterDS.clear();
            adapterDS.addAll(newData);
            adapter.notifyDataSetChanged();
        }

        if (spinnerToNotify != null) {
            spinnerToNotify.setSelection(0);
        }

        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
