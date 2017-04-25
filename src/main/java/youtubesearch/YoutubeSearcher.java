package youtubesearch;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;


import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Adair on 04/23/17.
 */


public class YoutubeSearcher {

    private String name;
    private final String API_KEY = "AIzaSyAzNvvBZ8Y-XCSS87W38f8MZuIs-7vPzqI";
    private final String CLIENT_ID = "142787540832-mnonrpc9i69gheuoh99ikd10bhq7k7n6.apps.googleusercontent.com";

    public YoutubeSearcher(String name) {
        this.name = name;
    }

    public String search(){
        YouTubeVideo video = retrieveVideos(name, 1, false, 1000);
        return "https://www.youtube.com/watch?v="+video.id;
    }

    private YouTubeVideo retrieveVideos(String query, int numresults, boolean filter, int timeout) {
        YouTube  youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("discord bot").build();
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(API_KEY);
            search.setQ(query);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(Long.parseLong(""+numresults));
            List<SearchResult> results = search.execute().getItems();
            SearchResult r = results.get(0);
            return new YouTubeVideo(r.getId().getVideoId(),r.getSnippet().getTitle(),r.getSnippet().getChannelTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
