public class Span {
    private String span_name;
    private String label;
    private int start_offset;
    private int end_offset;

    public String getSpan_name() {
        return span_name;
    }

    public Span setSpan_name(String span_name) {
        this.span_name = span_name;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Span setLabel(String label) {
        this.label = label;
        return this;
    }

    public int getStart_offset() {
        return start_offset;
    }

    public Span setStart_offset(int start_offset) {
        this.start_offset = start_offset;
        return this;
    }

    public int getEnd_offset() {
        return end_offset;
    }

    public Span setEnd_offset(int end_offset) {
        this.end_offset = end_offset;
        return this;
    }
}
