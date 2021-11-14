package uk.ac.ucl.comp0010.observer;

class NoObserverDemo {
    public interface Channel {
    }

    public class NewsChannel implements Channel {
        private String news;
    }

    public class NewsAgency {
        private String news;

        public void setNews(String news) {    
            this.news = news;
            // how would channels be notified about the news?
        }
    }
}