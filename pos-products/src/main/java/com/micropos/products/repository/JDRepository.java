package com.micropos.products.repository;

import com.micropos.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDRepository implements ProductRepository {

    private static class ParseJDSingleton {
        private static final List<Product> products = parseJD("Java");
    }

    @Override
    public Flux<Product> allProducts() {
        return Flux.fromIterable(ParseJDSingleton.products);
    }

    @Override
    public Mono<Product> findProduct(String productId) {
        for (Product p : ParseJDSingleton.products) {
            if (p.getId().equals(productId)) {
                return Mono.just(p);
            }
        }
        return null;
    }

    public static List<Product> parseJD(String keyword) {
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        List<Product> list = new ArrayList<>();

        int retryTime = 20;
        while ((retryTime--) != 0) {
            try { // 如果是获取错误, 就重试(不能返回空列表, 否则在缓存状态下, 一直是空的)
                Document document = Jsoup.parse(new URL(url), 10000);
                Element element = document.getElementById("J_goodsList");
                Elements elements = element.getElementsByTag("li");
                list.clear();

                for (Element el : elements) {
                    String id = el.attr("data-spu");
                    String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
                    String price = el.getElementsByAttribute("data-price").text();
                    String title = el.getElementsByClass("p-name").eq(0).text();
                    if (title.indexOf("，") >= 0)
                        title = title.substring(0, title.indexOf("，"));

                    Product product = new Product(id, title, Double.parseDouble(price), img);
                    list.add(product);
                }
                return list;

            } catch (Exception ignored) {
            }
        }
        return list;
    }
}
