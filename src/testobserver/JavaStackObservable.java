package testobserver;

import java.util.Observable;

public class JavaStackObservable extends Observable {
    private String article;

    public String getArticle() {
        return article;
    }

    public void publish(String article){
        this.article = article;
        this.setChanged();
        this.notifyObservers();
    }
}
