import java.io.*;

public class Sample {
    private final static String inputCsvFile = "C:\\Users\\MGT社員\\Desktop\\マスキングデータ\\20230705_mgt_test.employee_history.csv";
    private final static String outputCsvFile = "C:\\Users\\MGT社員\\Desktop\\マスキングデータ\\20230705_mgt_test.employee_history_sample.csv";

    public static void main(String[] args) {

        File fileInput = new File(inputCsvFile);
        File fileOutput = new File(outputCsvFile);
        String csvSplitBy = ",";
        int count = 0;

        try (//文字コードUTF-8を指定してファイルを読み込む
             FileInputStream input = new FileInputStream(fileInput);
             InputStreamReader streamInput = new InputStreamReader(input, "UTF-8");
             BufferedReader bufferInput = new BufferedReader(streamInput);

             FileOutputStream out = new FileOutputStream(fileOutput);
             OutputStreamWriter streamOutput = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter bufferOut = new BufferedWriter(streamOutput)) {

            String str;

            //ファイルの最終行まで読み込む
            while ((str = bufferInput.readLine()) != null) {

                byte[] b = str.getBytes();

                str = new String(b, "UTF-8");

                String[] data = str.split(csvSplitBy);

                if (count < 1) {
                    count++;
                } else {
                    data[8] = rename(data[8]);
//                    data[3] = rename(data[3]);
                }

                for (int i = 0; i < data.length; i++) {
                    bufferOut.write(data[i]);
                    if (i != data.length - 1) {
                        bufferOut.write(",");
                    }
                }

                bufferOut.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("終わりました。");
        }
    }

    private static String rename(String name) {
        StringBuilder replacedStringBuilder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {

            char c = name.charAt(i);
            if (i == 0 || i == name.length() - 1) {
                replacedStringBuilder.append(c);
                continue;
            }

            if (i % 2 != 0) {
                // 奇数番目の文字を置換（ここでは "*" に置換）
                c = '＊';
            }

            replacedStringBuilder.append(c);
        }
        return replacedStringBuilder.toString();
    }
}
