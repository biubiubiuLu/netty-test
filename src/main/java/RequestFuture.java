import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestFuture {

    public static Map<Long, RequestFuture> futures = new ConcurrentHashMap<>();

    private long id;

    private Object request;

    private Object result;

    private long timeout = 5000;

    public static void addFuture(RequestFuture future) {
        futures.put(future.id, future);
    }

    public Object get() {
        synchronized (this) {
            while (this.result == null) {
                try {
                    this.wait(timeout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this.result;
    }

    public static void received(Response resp){
        RequestFuture future = futures.remove(resp.getId());
        if(future != null){
            future.setResult(resp.getResult());
            synchronized (future){
                future.notify();
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

