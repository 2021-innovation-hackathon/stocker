package com.Hackathon.ui.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Hackathon.NewsAdapter;
import com.Hackathon.NewsItem;
import com.Hackathon.R;
import com.Hackathon.StockBar;
import com.Hackathon.databinding.FragmentGalleryBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment implements View.OnClickListener {
    // 입력받을 것
    private String companyCode;
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

    private void drawChart() {
        // 데이터 봉분을 가져온다.
        Integer page = 1;

        try {
            // 차트를 가져온다.
            chart = (LineChart)getActivity().findViewById(R.id.priceChart);
            ArrayList<Entry> values = new ArrayList<>();

            // 크롤링을 담당하는 함수입니다.
            new Thread() {
                @Override
                public void run() {
                    Integer page = 1;
                    int i = 0;
                    Log.d("테스트 확인 회사 번호 ", companyCode);

                    while(page <= maxPage) {
                        String url = "https://finance.naver.com/item/sise_day.nhn?code="
                                + companyCode + "&page=" + page.toString();
                        Log.d("테스트 확인 URL ", url);

                        Document sourceCode = null;
                        stockItems = new ArrayList<StockBar>();
                        try {
                            // 그 다음에 HTML 소스 페이지를 들고 온다.
                            sourceCode = Jsoup.connect(url).get();

                            Elements newsRowElements = sourceCode.select("table.type2 tr:gt(0)");

                            for(Element row : newsRowElements) {
                                Elements cellList = row.select("td");

                                String strdate = cellList.select(".tah.p10.gray03").text();

                                if(strdate == "") {
                                    continue;
                                }

                                Log.d("strDate ", strdate);
                                String strcurrentStock = cellList.select(".tah.p11").text().split(" ")[0].replace(",", "");

                                Date date = convertStringToDate(strdate);
                                float currentStock = Float.parseFloat(strcurrentStock);

                                values.add(new Entry(i++, currentStock));
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

                            // set data
                            chart.setData(data);

                            page++;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void crawlingNews() {
        // 크롤링을 담당하는 함수입니다.
        new Thread() {
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
        }.start();
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

        recyclerViewInitSetting();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        EditText text = (EditText)getActivity().findViewById(R.id.editNewsId);

        String com = text.getText().toString();
        if(com.length() != 6) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("양식이 잘못되었습니다.");
            builder.setNegativeButton("돌아가기", null);
            builder.create().show();
            return;
        }

        companyCode = com;

        // 기능 시작
        crawlingNews();
        drawChart();
    }
}