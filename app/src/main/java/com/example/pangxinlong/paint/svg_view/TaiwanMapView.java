package com.example.pangxinlong.paint.svg_view;

import com.example.pangxinlong.paint.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by pangxinlong on 2017/9/8.
 */

public class TaiwanMapView extends View {

    private Context mContext;

    private Paint mPaint;

    List<MapBean> mMapBeanList;

    public TaiwanMapView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mMapBeanList = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        initView();
    }


    private void initView() {
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.taiwan_high);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = null;
            try {
                document = documentBuilder.parse(inputStream);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("path");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elementItem = (Element) nodeList.item(i);
                String pathData = elementItem.getAttribute("d");
                Path path = PathParser.createPathFromPathData(pathData);
                MapBean mapBean = new MapBean(path);
                mMapBeanList.add(mapBean);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (MapBean mapBean : mMapBeanList) {
            mapBean.draw(canvas, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (MapBean mapBean : mMapBeanList) {
                    mapBean.isSelect((int)event.getX(), (int)event.getY());
                }
                invalidate();
                break;
        }
        return true;
    }
}
