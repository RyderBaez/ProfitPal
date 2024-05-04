import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("index")
    private int index;

    @SerializedName("message")
    private Message message;

    @SerializedName("logprobs")
    private Object logprobs;  // Consider defining a specific class if structure is known

    @SerializedName("finish_reason")
    private String finishReason;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Object logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
