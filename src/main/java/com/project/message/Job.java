package com.project.message;

import com.project.model.Product;
import java.util.List;

public class Job {

    String filePath;
    List<Product> products;

    public Job(String path){
        this.filePath = path;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
