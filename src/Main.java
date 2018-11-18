import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.mininglamp.ratel.RatelSDK;
import com.mininglamp.ratel.SDKUtils.Annotation;
import com.mininglamp.ratel.SDKUtils.Offset;
import com.mininglamp.ratel.SDKUtils.RatelResult;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.tools.java.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static Tools tools = new Tools();
    private static String datapath = System.getProperty("user.dir") + "/src/datasource";


    public static LabelingRes run_ratel(LabelingRes labelingRes, String scriptContent, String documentContent, String key) {
        LabelingRes ret = new LabelingRes();
        ret.setContent(labelingRes.getContent())
                .setData_id(labelingRes.getData_id())
                .setData_set_name(labelingRes.getData_set_name());
        List<Span> spans = new ArrayList<>();
        try {

            RatelSDK ratelSDK = new RatelSDK();
            ratelSDK.initializeAnalysisEngineInput(scriptContent);
            RatelResult result = ratelSDK.processInput(documentContent);

            List<Annotation> list = result.getType(key);
            if (list == null) list = Collections.emptyList();
            for (Annotation anno : list) {
                Span span = new Span();
                String offset = anno.offset().offsetToString();
                System.out.println(offset);
                String[] offsetList = offset.split(" ");
                span.setSpan_name(anno.context())
                        .setLabel(anno.typeName())
                        .setEnd_offset(Integer.parseInt(offsetList[0]))
                        .setEnd_offset(Integer.parseInt(offsetList[1]));
                if (span.getLabel() != null) {
                    spans.add(span);
                }
//                String typename = anno.typeName();
                String context = anno.context();
//                Offset offset = anno.offset();
//                String ratelType = anno.getRatelType();
//                HashMap<String, List<Annotation>> features = anno.getFeatures();
//                List<String> rules = anno.getRules();
//                System.out.println(typename);
                System.out.println(context);
//                System.out.println(offset.begin());
//                System.out.println(offset.end());

            }
            ret.setSpans(spans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static LabelingRes run_ratel_scripts(LabelingRes labelingRes, List<String> scripts, String documentContent, String key) {
        LabelingRes ret = new LabelingRes();
        ret.setContent(labelingRes.getContent())
                .setData_id(labelingRes.getData_id())
                .setData_set_name(labelingRes.getData_set_name());
        List<Span> spans = new ArrayList<>();
        try {

            for (String scriptContent : scripts) {
                RatelSDK ratelSDK = new RatelSDK();
//            String scriptContent = "DECLARE panda;\"大?熊猫\"->panda;" +
//                    "DECLARE activity;\"睡觉|玩耍\"->activity;" +
//                    "DECLARE panda_activity;(panda  # activity){-> panda_activity};";
//            String documentContent = "熊猫宝宝在睡觉。\n大象在玩耍。";
                ratelSDK.initializeAnalysisEngineInput(scriptContent);
                RatelResult result = ratelSDK.processInput(documentContent);

                List<Annotation> list = result.getType(key);
                if (list == null) list = Collections.emptyList();
                for (Annotation anno : list) {
                    Span span = new Span();
                    String offset = anno.offset().offsetToString();
                    System.out.println(offset);
                    String[] offsetList = offset.split(" ");
                    span.setSpan_name(anno.context())
                            .setLabel(anno.typeName())
                            .setEnd_offset(Integer.parseInt(offsetList[0]))
                            .setEnd_offset(Integer.parseInt(offsetList[1]));
                    if (span.getLabel() != null) {
                        spans.add(span);
                    }
//                String typename = anno.typeName();
                    String context = anno.context();
//                Offset offset = anno.offset();
//                String ratelType = anno.getRatelType();
//                HashMap<String, List<Annotation>> features = anno.getFeatures();
//                List<String> rules = anno.getRules();
//                System.out.println(typename);
                    System.out.println(context);
//                System.out.println(offset.begin());
//                System.out.println(offset.end());

                }
            }
            ret.setSpans(spans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static void run_ratel_with_dict(String scriptContent, String documentContent, String key, String
            dictname, List<String> dict) {
        try {
            RatelSDK ratelSDK = new RatelSDK();
            HashMap<String, List<String>> hashMap = new HashMap<>();
            hashMap.put(dictname, dict);
            ratelSDK.setResources(hashMap);
            ratelSDK.initializeAnalysisEngineInput(scriptContent);
            RatelResult result = ratelSDK.processInput(documentContent);
            List<Annotation> list = result.getType(key);
            for (Annotation anno : list) {
                String typename = anno.typeName();
                String context = anno.context();
                Offset offset = anno.offset();
                String ratelType = anno.getRatelType();
                HashMap<String, List<Annotation>> features = anno.getFeatures();
                List<String> rules = anno.getRules();
                System.out.println(typename);
                System.out.println(context);
                System.out.println(offset.offsetToString() + ratelType);
                System.out.println(offset.offsetToString() + ratelType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws IOException {
        String scriptContent = tools.readScript("ratelScripts/test_ratel.txt");
        String documentContent = "熊猫宝宝在睡觉。\n大象在玩耍。";//readText("labeling_output/任国源task1.json");
//        run_ratel(scriptContent,documentContent,"panda");
    }

    public static void run_age_range(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String age_range_script = tools.readScript("ratelScripts/age_range");
            String disclaimer_content = tools.readScript("ratelScripts/disclaimer_content");
            String insurance_duty = tools.readScript("ratelScripts/insurance_duty");
            String insurance_period_length = tools.readScript("ratelScripts/insurance_period_length");
            List<String> scriptList = new ArrayList<>();
            scriptList.add(age_range_script);
            scriptList.add(disclaimer_content);
            scriptList.add(insurance_duty);
            scriptList.add(insurance_period_length);
//            System.out.println(scriptContent);
            String documentContent = labelingRes.getContent();
//            System.out.println(documentContent);
            LabelingRes resItem = run_ratel_scripts(labelingRes, scriptList, documentContent, "age_range");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        String jsonString = JSON.toJSONString(res);
        tools.writeJson("reg_output/" + filename, jsonString);

    }

    public static void run_insurance_duty(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/insurance_duty");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "insurance_duty");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        String jsonString = JSON.toJSONString(res);
        tools.writeJson("test.json", jsonString);
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static void run_all() throws IOException {
        List<String> dirs = tools.readDir(datapath + "/labeling_output");
        for (String filename : dirs) {
            System.out.println(filename);
            run_age_range(filename);
        }
    }

    public static void test_run_all() throws IOException {
        List<String> dirs = tools.readDir(datapath + "/labeling_output");
        run_age_range("任国源task1.json");
    }


    public static void main(String[] args) {
        try {
            run_insurance_duty("任国源task1.json");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
