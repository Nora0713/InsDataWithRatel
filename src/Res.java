import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Res {
    @JSONField(name="result")
    private List<LabelingRes> results;

    public List<LabelingRes> getResults() {
        return results;
    }

    public Res setResults(List<LabelingRes> results) {
        this.results = results;
        return this;
    }
}
