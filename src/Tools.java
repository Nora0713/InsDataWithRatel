import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tools {
    private static String datapath = System.getProperty("user.dir") + "/src/datasource";

    public static List<String> readDir(String dirpath) {
        List<String> filepaths = new ArrayList<String>();
        File dir = new File(dirpath);
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith("1.json") || f.getName().endsWith("4.json") || f.getName().endsWith("7.json"))
                filepaths.add(f.getName());
        }
        return filepaths;
    }

    public static String readText(String filepath) throws IOException {
        File file = new File(datapath, filepath);
        System.out.println("readText: "+file.getAbsolutePath());
        if (file.exists()) {
            FileInputStream is = null;
            StringBuilder stringBuilder = null;
            try {
                if (file.length() != 0) {
                    is = new FileInputStream(file);
                    InputStreamReader streamReader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(streamReader);
                    String line;
                    stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        // stringBuilder.append(line);
                        stringBuilder.append(line);
                    }
                    reader.close();
                    is.close();
                } else {
                    System.out.print("file.length() == 0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(stringBuilder);
        } else
            return filepath + ": file not exist";
    }

    public static String readScript(String filepath) throws IOException {
        String ret = readText(filepath);
//        System.out.println(ret);
        return ret;
    }

//    public static List<LabelingRes> readJson(String filepath) {   //gson failed
//        File file = new File(datapath, filepath);
//        System.out.println(file.getAbsolutePath());
//        if (file.exists()) {
//            try {
//                FileInputStream is = null;
//                is = new FileInputStream(file);
//                JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
//                Gson gson = new GsonBuilder().create();
//
//
//                // Read file in stream mode
////                reader.beginArray();
//                reader.beginObject();
//                while (reader.hasNext()) {
//                    // Read data into object model
//                    Res res = gson.fromJson(reader, Res.class);
//                    for(LabelingRes labelingRes:res.getResults()) {
//                        System.out.println(labelingRes.getContent());
//                    }
////                    if (labelingRes.getId() == 0 ) {
////                        System.out.println("Stream mode: " + person);
////                        break;
////                    }
//                    return null;
//                }
//                System.out.println("file not exist");
//                reader.close();
//            } catch (UnsupportedEncodingException ex) {
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        } else
//            return null;
//        return null;
//}


    public static List<LabelingRes> readJson(String filepath) {  //fastJSON success
        try {
            String jsonStr = readText(filepath);
//            System.out.println(jsonStr);
            Res res = JSON.parseObject(jsonStr, Res.class);
//            for (LabelingRes labelingRes : res.getResults()) {
//                System.out.println(labelingRes.getContent());
//            }
            return res.getResults();

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeJson(String filepath, String strJson) throws IOException {
        File file = new File(datapath, filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(strJson);
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(strJson);
            output.close();
        }
    }

    public static void writeJsonTest(String filepath, String strJson) throws IOException {
        try {
            writeJson("reg_output/任国源task1.json","");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static List<String> readDict(String filepath) throws IOException {
        File file = new File(datapath, filepath);
        System.out.println(file.getAbsolutePath());
        List<String> dict = new ArrayList<>();
        if (file.exists()) {
            FileInputStream is = null;
            StringBuilder stringBuilder = null;
            try {
                if (file.length() != 0) {
                    is = new FileInputStream(file);
                    InputStreamReader streamReader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(streamReader);
                    String line;
                    stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        dict.add(line);
                    }
                    reader.close();
                    is.close();
                } else {
                    System.out.print("file.length() == 0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return dict;
        } else
            return dict;
    }

        public static void test() throws IOException {
        List<LabelingRes> labelingResList = readJson("labeling_output/任国源task1.json");
        for (LabelingRes labelingRes : labelingResList) {
            System.out.println(labelingRes.getData_id());
        }


    }

    public static void main(String[] args) {
        try {
            List<String> list = readDict("disease_set_old.txt");
            System.out.println(list.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
