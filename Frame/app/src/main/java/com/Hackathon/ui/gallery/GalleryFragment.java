package com.Hackathon.ui.gallery;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Hackathon.CompanyData;
import com.Hackathon.CompanyDataDB;
import com.Hackathon.FileReader;
import com.Hackathon.NewsAdapter;
import com.Hackathon.NewsItem;
import com.Hackathon.R;
import com.Hackathon.StockBar;
import com.Hackathon.databinding.FragmentGalleryBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;

public class GalleryFragment extends Fragment implements View.OnClickListener  {
    // 입력받을 것
    private String companyCode;
    private String companyName;
    private int maxPage = 1;

    // 리사이클러 뷰
    private RecyclerView recyclerView;
    private NewsAdapter recyclerAdapter;
    private ArrayList<NewsItem> items;

    // 라인차트
    private LineChart chart;
    private ArrayList<StockBar> stockItems;

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    // DB 관련 코드
    private CompanyDataDB companyDataDB = null;
    private List<CompanyData> companyDataList;
    private Context mContext = null;

    private Date convertStringToDate(String date) throws ParseException {
        String[] str = date.split("\\.");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < str.length; ++i) {
            sb.append(str[i]);

            if(i != str.length - 1) {
                sb.append('-');
            }
        }

        Date result = new Date(sdf.parse(sb.toString()).getTime());

        return result;
    }
    //리사이클러뷰 초기화 셋팅
    public void recyclerViewInitSetting(){
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        /* initiate adapter */
        recyclerAdapter = new NewsAdapter();

        if(recyclerView == null) {
            System.out.println("이게 왜 널이죠");
        }

        /* initiate recyclerview */
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(items == null) {
            items = new ArrayList<NewsItem>();
        }

        recyclerAdapter.setFriendList(items);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = (Button)getActivity().findViewById(R.id.btnNewsId);
        btn.setOnClickListener(this);
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        TextView textView = (TextView)getActivity().findViewById(R.id.txtDiaryRedirect);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 여기서 일기 데이터를 조회
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("아직 만드는 중이에요..");
                builder.setNegativeButton("돌아가기", null);
                builder.create().show();
                return;
            }
        });

        EditText et = (EditText)getActivity().findViewById(R.id.editNewsId);
        et.setText("");

        class InsertRunnable implements Runnable {
            @Override
            public void run() {
                try {
                    companyDataDB = CompanyDataDB.getInstance(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Thread t = new Thread(new InsertRunnable());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerViewInitSetting();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot ();

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            getActivity().setTheme(R.style.Theme_AppCompat_DayNight);
        } else {
            getActivity().setTheme(R.style.Theme_AppCompat);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view){
        EditText text = (EditText) getActivity().findViewById(R.id.editNewsId);

        String com = text.getText().toString();
        if (com.length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("양식이 잘못되었습니다.");
            builder.setNegativeButton("돌아가기", null);
            builder.create().show();
            return;
        }

        companyName = com;

        class InsertRunnable implements Runnable {
            @Override
            public void run() {
                try {
                    if(companyCode == null) {
                        companyCode = CompanyDataDB.getInstance(getActivity()).companyDao().getCompanyCode(companyName);
                    }



                    System.out.println(companyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        class NewsCrawling implements Runnable {
            @Override
            public void run() {
                Integer page = 1;

                Log.d("테스트 확인 회사 번호 ", companyCode);
                while(page <= maxPage) {
                    // 먼저 url을 들고 온다.
                    String url = "https://finance.naver.com/item/news_news.nhn?code="
                            + companyCode + "&page=" + page.toString();
                    Log.d("테스트 확인 URL ", url);

                    Document sourceCode = null;
                    items = new ArrayList<NewsItem>();
                    try {
                        // 그 다음에 HTML 소스 페이지를 들고 온다.
                        sourceCode = Jsoup.connect(url).get();

                        Elements newsRowElements = sourceCode.select("table.type5 tr:gt(0)");

                        // 만약 뉴스가 없다면 이것을 실행한다.
                        if(newsRowElements.size() == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("관련 뉴스가 없습니다.");
                                    builder.setNegativeButton("돌아가기", null);
                                    builder.create().show();
                                }
                            });
                            return;
                        }

                        for(Element row : newsRowElements) {
                            Elements cellList = row.select("td");

                            String newUrl = "https://finance.naver.com" + cellList.select(".title").select(".tit").attr("href");
                            // Log.d("테스트 확인 뉴스 url ", newUrl);

                            // 사진 들고 오기 위한 용도
                            Document contentDoc = Jsoup.connect(newUrl).get();

                            // 데이터 넣기
                            String title = cellList.select(".title").text();
                            String content = contentDoc.select("#news_read").get(0).text().substring(0, 30);
                            String date = cellList.select(".date").text();

                            // Log.d("테스트 확인 뉴스 companyCode ", companyCode);
                            // Log.d("테스트 확인 뉴스 title ", title);
                            // Log.d("테스트 확인 뉴스 newUrl ", newUrl);
                            // Log.d("테스트 확인 뉴스 content ", content);
                            Log.d("테스트 확인 뉴스 date ", date);

                            NewsItem item = new NewsItem(companyCode, title, content, newUrl, date);

                            items.add(item);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewInitSetting();
                            }
                        });
                        page++;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }

        class DrawChart implements Runnable {
            // 데이터 봉분을 가져온다.
            Integer page = 1;

            @Override
            public void run() {
                // 차트를 가져온다.
                chart = (LineChart)getActivity().findViewById(R.id.priceChart);
                ArrayList<Entry> values = new ArrayList<>();

                Integer page = 1;
                int i = 0;
                Log.d("테스트 확인 회사 번호 ", companyCode);

                // 회사명 들고오기
                try {
                } catch(Exception e) {
                    e.printStackTrace();
                }

                while(page <= maxPage) {
                    String url = "https://finance.naver.com/item/sise_day.nhn?code="
                            + companyCode + "&page=" + page.toString();
                    Log.d("테스트 확인 URL ", url);

                    Document sourceCode = null;
                    stockItems = new ArrayList<StockBar>();


                    try {
                        // 그 다음에 HTML 소스 페이지를 들고 온다.
                        sourceCode = Jsoup.connect(url).get();

                        Elements stockRowElements = sourceCode.select("table.type2 tr:gt(0)");

                        if(stockRowElements.size() == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("관련 데이터가 없습니다.");
                                    builder.setNegativeButton("돌아가기", null);
                                    builder.create().show();
                                }
                            });
                            return;
                        }

                        final ArrayList<String> xLabel = new ArrayList<>();

                        for(Element row : stockRowElements) {
                            Elements cellList = row.select("td");

                            String strdate = cellList.select(".tah.p10.gray03").text();

                            if(strdate == "") {
                                continue;
                            }

                            Log.d("strDate ", strdate);
                            String strcurrentStock = cellList.select(".tah.p11").text()
                                    .split(" ")[0].replace(",", "");

                            Date date = convertStringToDate(strdate);
                            System.out.println("date long : " + date.getTime());
                            float currentStock = Float.parseFloat(strcurrentStock);

                            values.add(new Entry(i++, currentStock));
                            xLabel.add(Long.toString(date.getTime()));
                        }


                        LineDataSet set1;
                        set1 = new LineDataSet(values, companyCode);

                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1); // add the data sets

                        // create a data object with the data sets
                        LineData data = new LineData(dataSets);

                        // black lines and points
                        set1.setColor(Color.BLACK);
                        set1.setCircleColor(Color.BLACK);

                        // 회사 명 가져오기
                        String url2 = "https://finance.naver.com/item/sise.nhn?code=" + companyCode;
                        Document title = Jsoup.connect(url2).get();
                        companyName = title.select(".wrap_company").get(0).text().split(" ")[0];

                        // set data
                        chart.setData(data);
                        chart.setVisibleXRangeMaximum(6);
                        chart.getDescription().setText(companyName + "의 주식");
                        chart.moveViewToX(0);

                        chart.setOnChartValueSelectedListener(
                                new OnChartValueSelectedListener() {
                                    @Override
                                    public void onValueSelected(Entry e, Highlight h) {
                                        // long emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis(Long.parseLong(xLabel.get((int)value)));
                                        long emissionsMilliSince1970Time = Long.parseLong(xLabel.get((int)e.getX()));

                                        // Show time in local version
                                        Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
                                        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        System.out.println("dateTimeFormat.format(timeMilliseconds)" + dateTimeFormat.format(timeMilliseconds));

                                        Log.d("Entry selected", e.toString());
                                        System.out.println("date : " + dateTimeFormat.format(timeMilliseconds) + " , Stock Value : " + (int)e.getY());
                                    }

                                    @Override
                                    public void onNothingSelected() {
                                        Log.d("Nothing selected", "Nothing selected.");
                                    }
                                }
                        );

                        XAxis xAxis = chart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(
                                new IndexAxisValueFormatter() {
                                    @Override
                                    public String getFormattedValue(float value) {
                                        // long emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis(Long.parseLong(xLabel.get((int)value)));
                                        long emissionsMilliSince1970Time = Long.parseLong(xLabel.get((int)value));

                                        // Show time in local version
                                        Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
                                        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        System.out.println("dateTimeFormat.format(timeMilliseconds)" + dateTimeFormat.format(timeMilliseconds));

                                        return dateTimeFormat.format(timeMilliseconds);
                                    }
                                }
                        );

                        YAxis yAxis = chart.getAxisRight();

                        switch (AppCompatDelegate.getDefaultNightMode()) {
                            case AppCompatDelegate.MODE_NIGHT_YES:
                                set1.setCircleColor(Color.WHITE);
                                set1.setColor(Color.GRAY);
                                xAxis.setTextColor(Color.WHITE);
                                yAxis.setTextColor(Color.WHITE);
                                break;
                            case AppCompatDelegate.MODE_NIGHT_NO:
                                set1.setCircleColor(Color.BLACK);
                                set1.setColor(Color.GRAY);
                                xAxis.setTextColor(Color.BLACK);
                                yAxis.setTextColor(Color.BLACK);
                                break;
                        }

                        page++;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t1 = new Thread(insertRunnable);

        NewsCrawling newsCrawling = new NewsCrawling();
        Thread t2 = new Thread(newsCrawling);

        DrawChart drawChart = new DrawChart();
        Thread t3 = new Thread(drawChart);

        try {
            t1.start();
            t1.join();

            if(companyCode == null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("없는 회사입니다..");
                        builder.setNegativeButton("돌아가기", null);
                        builder.create().show();
                    }
                });
                return;
            }

            t2.start();
            t2.join();

            t3.start();
            t3.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}