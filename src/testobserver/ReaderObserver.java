package testobserver;

import com.sun.istack.internal.NotNull;

import java.util.Observable;
import java.util.Observer;

public class ReaderObserver implements Observer {
    @NotNull
    private String name;
    private String article;

    ReaderObserver(String name){
        this.name = name;
    }
    @Override
    public void update(Observable o, Object arg) {
        updateArticle(o);
    }

    private void updateArticle(Observable o) {
        JavaStackObservable javaStackObservable = (JavaStackObservable) o;
        this.article = javaStackObservable.getArticle();
        System.out.printf("我是读者：%s,文章已更新为：%s\n",this.name,this.article);
    }
}
