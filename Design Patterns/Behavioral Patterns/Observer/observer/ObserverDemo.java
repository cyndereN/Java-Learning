package uk.ac.ucl.comp0010.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverDemo {
    public interface Channel {
        public void update(Object o);
    }

    public class NewsChannel implements Channel {
        private String news;

        public String getNews() {
            return news;
        }

        public void update(Object news) {
            this.news = (String) news;
            //publish news
        }
    }

    public class NewsAgency {
        private String news;
        private List<Channel> channels = new ArrayList<>();

        public void attach(Channel channel) {
            this.channels.add(channel);
        }

        public void detach(Channel channel) {
            this.channels.remove(channel);
        }

        public void setNews(String news) {    
            this.news = news;         
            for (Channel channel: this.channels) {
                channel.update(this.news);
            }
        }
    }
}

@Test
public void testObserver() throws Exception {
    ObserverDemo d = new ObserverDemo();
    ObserverDemo.NewsAgency agency = d.new NewsAgency();
    
    ObserverDemo.Channel channel1 = d.new NewsChannel();
    ObserverDemo.Channel channel2 = d.new NewsChannel();
    agency.attach(channel1);
    agency.attach(channel2);

    agency.setNews("news");
}