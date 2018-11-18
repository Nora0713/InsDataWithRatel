import scala.collection.LinearSeq;

import java.util.List;

public class LabelingRes {
    private int data_id;
    private String data_set_name;
    private String content;
    private List<Span> spans;

    public int getData_id() {
        return data_id;
    }

    public LabelingRes setData_id(int data_id) {
        this.data_id = data_id;
        return this;
    }

    public String getData_set_name() {
        return data_set_name;
    }

    public LabelingRes setData_set_name(String data_set_name) {
        this.data_set_name = data_set_name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public LabelingRes setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Span> getSpans() {
        return spans;
    }

    public LabelingRes setSpans(List<Span> spans) {
        this.spans = spans;
        return this;
    }
}
