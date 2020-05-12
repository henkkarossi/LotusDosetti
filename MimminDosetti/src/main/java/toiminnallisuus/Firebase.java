package toiminnallisuus;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;


public class Firebase {
	private static final String account = "innovointiprojekti2020-firebase-adminsdk-4qh1l-9887938033.json";
	private static final String url = "https://innovointiprojekti2020.firebaseio.com";
	private static FileInputStream serviceAccount = null;
	private static HttpClient client = HttpClient.newHttpClient();

	public void updateTime() {
		try {
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String updateTime = time.format(formatter);
			DatabaseReference ref = getRef();

			DatabaseReference dosettiRef = ref.child("Dosetti1");
			Map<String, Object> dosettiUpdates = new HashMap<>();
			dosettiUpdates.put("time", updateTime);

			dosettiRef.updateChildrenAsync(dosettiUpdates);
			dosettiRef.updateChildrenAsync(dosettiUpdates).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateStatus(boolean status) {
		try {
			DatabaseReference ref = getRef();

			DatabaseReference dosettiRef = ref.child("Dosetti1");
			Map<String, Object> dosettiUpdates = new HashMap<>();
			dosettiUpdates.put("ok", status);

			dosettiRef.updateChildrenAsync(dosettiUpdates);
			dosettiRef.updateChildrenAsync(dosettiUpdates).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTimeToTake(String time) {
		try {

			DatabaseReference ref = getRef();

			DatabaseReference dosettiRef = ref.child("Dosetti1");
			Map<String, Object> dosettiUpdates = new HashMap<>();
			dosettiUpdates.put("timeToTake", time);

			dosettiRef.updateChildrenAsync(dosettiUpdates);
			dosettiRef.updateChildrenAsync(dosettiUpdates).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTimeToTake() throws Exception {
		Gson gson = new Gson();

        String json = get("https://innovointiprojekti2020.firebaseio.com/dosetit/Dosetti1.json");

        Data data = gson.fromJson(json, Data.class);

        return data.getTimeToTake();
   

	}
	
	public Data getData() throws Exception {
		Gson gson = new Gson();

        String json = get("https://innovointiprojekti2020.firebaseio.com/dosetit/Dosetti1.json");

        Data data = gson.fromJson(json, Data.class);

        return data;
   

	}

	public static String get(String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }
	
	public DatabaseReference getRef() throws IOException {
		serviceAccount = new FileInputStream(account);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl(url).build();

		FirebaseApp.initializeApp(options);

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("dosetit");
		return ref;
	}

	public void close() throws IOException {
		serviceAccount.close();
	}
}
