package com.project.controller;

import com.project.model.Product;
import com.project.service.ProductPersistenceService;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@EnableWebMvc
@ManagedBean
@ViewScoped
public class ProductSearchController {

    @Autowired
    ProductPersistenceService productPersistenceService;

    String productName;

    List products;

    Product selectedProduct;

    private BarChartModel barModel;

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    ProductSearchController(){
        products = new LinkedList<Product>();
    }

    public void setProductPriceChart (){

        barModel = initBarModel();
        barModel.setTitle("Fiyat Degisim Grafigi");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Fiyat");
        xAxis.setMin(0);
        xAxis.setMax(750);

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Tarih");
    }

    private BarChartModel initBarModel(){
        BarChartModel model = new HorizontalBarChartModel();
        ChartSeries priceSeries = new ChartSeries();
        priceSeries.setLabel(selectedProduct.getTitle());
        String [] dates = selectedProduct.getDates().split(",");
        String [] prices = selectedProduct.getPrices().split(",");

        for(int i = 0 ; i < dates.length;i++){
            priceSeries.set(utcToDateString(Long.valueOf(dates[i])),Float.valueOf(prices[i]));
        }

        model.addSeries(priceSeries);
        return model;
    }

    public String utcToDateString(long utcTime){

        Date date = new java.util.Date(utcTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dateString = String.valueOf(calendar.get(Calendar.DATE));
        String montString = String.valueOf(calendar.get(Calendar.MONTH));
        String yearString = String.valueOf(calendar.get(Calendar.YEAR));

        return dateString+"/"+montString+"/"+yearString;
    }

    public void queryProducts(){
        products = productPersistenceService.queryProductsByTitle(productName);
    }


    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts(){
        return products;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
