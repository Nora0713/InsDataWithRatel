import com.alibaba.fastjson.JSON;
import com.mininglamp.ratel.RatelSDK;
import com.mininglamp.ratel.SDKUtils.Annotation;
import com.mininglamp.ratel.SDKUtils.RatelResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RetalHelper {
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
                        .setStart_offset(Integer.parseInt(offsetList[0]))
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
                            .setStart_offset(Integer.parseInt(offsetList[0]))
                            .setEnd_offset(Integer.parseInt(offsetList[1]));
                    System.out.println(offsetList[0]+" "+
                            offsetList[1]);
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

    public static LabelingRes run_ratel_with_dict(LabelingRes labelingRes, String scriptContent, String documentContent, String key) {
        LabelingRes ret = new LabelingRes();
        ret.setContent(labelingRes.getContent())
                .setData_id(labelingRes.getData_id())
                .setData_set_name(labelingRes.getData_set_name());
        List<Span> spans = new ArrayList<>();
        try {
            RatelSDK ratelSDK = new RatelSDK();

            HashMap<String, List<String>> hashMap = new HashMap<>();
            hashMap.put("disease_set", tools.readDict("disease_set.txt"));
            ratelSDK.setResources(hashMap);

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
                        .setStart_offset(Integer.parseInt(offsetList[0]))
                        .setEnd_offset(Integer.parseInt(offsetList[1]));
                if (span.getLabel() != null) {
                    spans.add(span);
                }
                String context = anno.context();
                System.out.println(context);

            }
            ret.setSpans(spans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

//    public static void run_ratel_with_dict(String scriptContent, String documentContent, String key, String
//            dictname, List<String> dict) {
//        try {
//            RatelSDK ratelSDK = new RatelSDK();
//            HashMap<String, List<String>> hashMap = new HashMap<>();
//            hashMap.put(dictname, dict);
//            ratelSDK.setResources(hashMap);
//            ratelSDK.initializeAnalysisEngineInput(scriptContent);
//            RatelResult result = ratelSDK.processInput(documentContent);
//            List<Annotation> list = result.getType(key);
//            for (Annotation anno : list) {
//                String typename = anno.typeName();
//                String context = anno.context();
//                Offset offset = anno.offset();
//                String ratelType = anno.getRatelType();
//                HashMap<String, List<Annotation>> features = anno.getFeatures();
//                List<String> rules = anno.getRules();
//                System.out.println(typename);
//                System.out.println(context);
//                System.out.println(offset.offsetToString() + ratelType);
//                System.out.println(offset.offsetToString() + ratelType);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void test() throws IOException {
        String scriptContent = tools.readScript("ratelScripts/test_ratel.txt");
        String documentContent = "熊猫宝宝在睡觉。\n大象在玩耍。";//readText("labeling_output/任国源task1.json");
//        run_ratel(scriptContent,documentContent,"panda");
    }

    public static void run_scripts(String filename) throws IOException {
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

    public static Res run_age_range(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/age_range");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "被保险人年龄要求");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_insurance_duty(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/insurance_duty");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "保险责任的选择");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_insurance_period_length(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/insurance_period_length");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "保险期间长度");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_contractual_liability_termination(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/contractual_liability_termination");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "赔付后合同责任终止");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_disclaimer_content(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/disclaimer_content");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel(labelingRes, insurance_duty, documentContent, "保险责任免除");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_disease_set(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("labeling_output/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/disease_set");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel_with_dict(labelingRes, insurance_duty, documentContent, "疾病集合");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_disease_set_single(String filename) throws IOException {
        Res res = new Res();
        List<LabelingRes> result_content = new ArrayList<>();
        List<LabelingRes> labelingResList = tools.readJson("disease_pkg/" + filename);
//        List<LabelingRes> labelingResList = tools.readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            String insurance_duty = tools.readScript("ratelScripts/disease_set");
            String documentContent = labelingRes.getContent();
            LabelingRes resItem = run_ratel_with_dict(labelingRes, insurance_duty, documentContent, "疾病集合");
            result_content.add(resItem);
        }
        res.setResults(result_content);
        return res;
//        tools.writeJson("reg_output/" + filename, jsonString);
    }

    public static Res run_all_scripts(String filename) throws IOException {
        List<Res> resList = new ArrayList<>();
        Res firseRes = run_age_range(filename);

        resList.add(firseRes);
        resList.add(run_contractual_liability_termination(filename));
        resList.add(run_disclaimer_content(filename));
        resList.add(run_disease_set(filename));
        resList.add(run_insurance_duty(filename));
        resList.add(run_insurance_period_length(filename));
        int n = firseRes.getResults().size();
        for (int i = 0 ;i<n;i++) {
            List<Span> spans = firseRes.getResults().get(i).getSpans();
            spans.addAll(resList.get(1).getResults().get(i).getSpans());
            spans.addAll(resList.get(2).getResults().get(i).getSpans());
            spans.addAll(resList.get(3).getResults().get(i).getSpans());
            spans.addAll(resList.get(4).getResults().get(i).getSpans());
            spans.addAll(resList.get(5).getResults().get(i).getSpans());
            firseRes.getResults().get(i).setSpans(spans);
        }
        return firseRes;
    }

    public static void run_all() throws IOException {
        List<String> dirs = tools.readDir(datapath + "/labeling_output");
        for (String filename : dirs) {
            System.out.println(filename);
            Res res = run_all_scripts(filename);
            String jsonString = JSON.toJSONString(res);
            tools.writeJson("reg_output/" + filename, jsonString);
        }
    }

    public static void test_run_one(String filename) throws IOException {
        System.out.println(filename);
        Res res = run_disease_set_single(filename);
        String jsonString = JSON.toJSONString(res);
        tools.writeJson( filename, jsonString);
    }


    public static void main(String[] args) {
        try {
            test_run_one("all_output.json");
//            run_all();
        } catch (IOException ie) {
            ie.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
